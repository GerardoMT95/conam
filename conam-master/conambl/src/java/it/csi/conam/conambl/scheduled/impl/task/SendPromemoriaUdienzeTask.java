/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl.task;


import it.csi.conam.conambl.scheduled.EmailScheduledService;
import it.csi.conam.conambl.scheduled.NodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 20200623_LC
@Service
public class SendPromemoriaUdienzeTask implements Runnable {

	protected static Logger logger = Logger.getLogger(SendPromemoriaUdienzeTask.class);
	
	@Autowired
	private EmailScheduledService emailScheduledService;

	@Autowired
	private NodeService nodeService;
	
	
    @Override
    public void run() {
		boolean isLeader = nodeService.isLeader();

		if (!isLeader)
			return;

		logger.info("STARTED SendPromemoriaUdienzeTask on thread " + Thread.currentThread().getName());
		try {
			emailScheduledService.sendAllPromemoriaUdienze();
		} catch (Throwable e) {
			logger.info("ENDED SendPromemoriaUdienzeTask on thread " + Thread.currentThread().getName() + " width error: [" + e.getMessage() + "]");
			e.printStackTrace();
		}finally {
			logger.info("ENDED SendPromemoriaUdienzeTask on thread " + Thread.currentThread().getName());
		}
		// -

    }
}
