/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;


import it.csi.conam.conambl.common.config.Config;
import it.csi.conam.conambl.scheduled.NodeService;
import it.csi.conam.conambl.scheduled.OrdinanzaScheduledService;
import it.csi.conam.conambl.scheduled.VerbaleScheduledService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 20200623_LC
@Service
public class SendToActaTask implements Runnable {

	protected static Logger logger = Logger.getLogger(SendToActaTask.class);

	@Autowired
	private Config config;

	@Autowired
	private NodeService nodeService;
	
	@Autowired
	private VerbaleScheduledService verbaleScheduledService;
	
	@Autowired
	private OrdinanzaScheduledService ordinanzaScheduledService;
	

     
    @Override
    public void run() {
    	
		if (!config.getSendAllegatiToActa())
			return;

		boolean isLeader = nodeService.isLeader();

		if (!isLeader)
			return;


		logger.info("STARTED SendToActaTask on thread " + Thread.currentThread().getName());
		// -
		verbaleScheduledService.sendAllegatiInCodaToActa();
		ordinanzaScheduledService.sendAllegatiInCodaToActa();
		// -
		logger.info("ENDED SendToActaTask on thread " + Thread.currentThread().getName());

    }
}
