/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.config.Config;
import it.csi.conam.conambl.integration.entity.CnmCEmail;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.repositories.CnmCEmailRepository;
import it.csi.conam.conambl.scheduled.NodeService;
import it.csi.conam.conambl.scheduled.ScheduledService;
import it.csi.conam.conambl.scheduled.SorisScheduledService;
import it.csi.conam.conambl.scheduled.impl.task.SendPromemoriaDocumentazioneTask;
import it.csi.conam.conambl.scheduled.impl.task.SendPromemoriaUdienzeTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class ScheduledServiceImpl implements ScheduledService {

	protected static Logger logger = Logger.getLogger(ScheduledServiceImpl.class);

	/*@Autowired
	private VerbaleScheduledService verbaleScheduledService;
	@Autowired
	private OrdinanzaScheduledService ordinanzaScheduledService;*/
	@Autowired
	private SorisScheduledService sorisScheduledService;
	
	@Autowired
	private Config config;

	@Autowired
	private NodeService nodeService;

	// 20200623_LC
	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;
	
	@Autowired
	private SendToActaTask sendToActaTask;
	@Autowired
	private SendPromemoriaUdienzeTask sendPromemoriaUdienzeTask;
	@Autowired
	private SendPromemoriaDocumentazioneTask sendPromemoriaDocumentazioneTask;
	
	
	
	private ThreadPoolTaskScheduler taskScheduler;
	//SendPromemoriaUdienzeTask

	@Autowired
	private CnmCEmailRepository cnmCEmailRepository;

	private void addPromemoriaToTask(Boolean isUdienze) {
		Long enabledParamID;
		Long cronParamID;
		Runnable task;
		if (isUdienze) {
			enabledParamID = Constants.ID_MAIL_UDIENZA_ENABLE;
			cronParamID = Constants.ID_MAIL_UDIENZA_CRON;
			task = sendPromemoriaUdienzeTask;
		}else {
			enabledParamID = Constants.ID_MAIL_DOCUMENTAZIONE_ENABLE;
			cronParamID = Constants.ID_MAIL_DOCUMENTAZIONE_CRON;
			task = sendPromemoriaDocumentazioneTask;
		}
		
		CnmCEmail enabledParam = cnmCEmailRepository.findByParamId(enabledParamID);
		Boolean enabled = Boolean.parseBoolean(enabledParam.getValue());
        if (enabled) {
			CnmCEmail cronParam = cnmCEmailRepository.findByParamId(cronParamID);
			String cron = cronParam.getValue();
			try {
				taskScheduler.schedule(task, new CronTrigger(cron));
			}
			catch(Exception e) {
				logger.error("Error Starting " + task.getClass(), e);
			}
        }
	}
	
	@PostConstruct
    public void threadPoolTaskScheduler(){		
        
        // 20200626_LC - retrieve fixedRate and PoolSize from DB
        long fixedRate = Long.parseLong(utilsCnmCProprietaService.getProprieta(PropKey.SCHEDULED_TOACTA_FIXED_RATE));
        int poolSize = Integer.parseInt(utilsCnmCProprietaService.getProprieta(PropKey.SCHEDULED_TOACTA_POOL_SIZE));

		taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(poolSize);
        taskScheduler.setThreadNamePrefix("TaskScheduler_");        
        taskScheduler.initialize();
        
        taskScheduler.scheduleAtFixedRate(sendToActaTask, fixedRate);
        
		//RECUPERO IL VALORE DEI PARAMETRI DA DB
        addPromemoriaToTask(true);
        addPromemoriaToTask(false);
        //SCHEDULAZIONE EMail
    }	

	
	// metodo da cancellare, anche dalla interface, task gestito tramite scheduler
//	@Override 
//	@Scheduled(fixedRate  = 60000)
//	public void sendAllegatiToActa() {
//		if (!config.getSendAllegatiToActa())
//			return;
//
//		boolean isLeader = nodeService.isLeader();
//
//		if (!isLeader)
//			return;
//
//		verbaleScheduledService.sendAllegatiInCodaToActa();
//		ordinanzaScheduledService.sendAllegatiInCodaToActa();
//	}

	
	
	@Override
	@Scheduled(cron = "0 0 2 * * *")
	public void soris() {
		if (!config.getSorisSpostaDoc())
			return;

		boolean isLeader = nodeService.isLeader();

		if (!isLeader)
			return;

		sorisScheduledService.elaboraStatoDellaRiscossione();
	}

	@Override
	@Scheduled(cron = "0 0/5 * * * ?")
	public void executeSystemNodePing() {
		if (!config.getSendAllegatiToActa())
			return;
		logger.info("START executeSystemNodePing");
		nodeService.pingNode();
		logger.info("END executeSystemNodePing");
	}

	@Override
	@Scheduled(cron = "0 0/10 * * * ?")
	public void executeLeaderResolution() {
		if (!config.getSendAllegatiToActa())
			return;
		logger.info("START executeLeaderResolution");
		nodeService.checkLeaderShip();
		logger.info("END executeLeaderResolution");
	}

}
