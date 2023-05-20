/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTDocumentoRepository;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.CnmDStatoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.repositories.CnmDStatoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.scheduled.OrdinanzaScheduledService;
import it.csi.conam.conambl.util.UtilsTipoAllegato;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class OrdinanzaScheduledServiceImpl implements OrdinanzaScheduledService {

	private static final int DAY_BEFORE = 2;

	protected static Logger logger = Logger.getLogger(OrdinanzaScheduledServiceImpl.class);

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private StadocServiceFacade stadocServiceFacade;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private AllegatoScheduledService allegatoScheduledService;
	
	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;
	
	// 20200622_LC
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	//
	@Autowired
	private DoquiServiceFacade doquiServiceFacade;
	
	// 20200711_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmTDocumentoRepository cnmTDocumentoRepository;

	// 20200625_LC
	private boolean isDoquiDirect() {
		 return Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.IS_DOQUI_DIRECT));
	}
	
	
	@Override
	public void sendAllegatiInCodaToActa() {
		if(isDoquiDirect()) {
			sendAllegatiInCodaToActa_Doqui();
		}else {
			sendAllegatiInCodaToActa_Stadoc();
		}
	}
	
	@SuppressWarnings("static-access")
	public void sendAllegatiInCodaToActa_Stadoc() {

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		List<CnmTOrdinanza> cnmTOrdinanzas = cnmTOrdinanzaRepository.findCnmTOrdinanzaAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		if (cnmTOrdinanzas.isEmpty())
			return;

		logger.info("Send allegati Ordinanza To Acta START");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzas.get(0);
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		// recupero allegati
		List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoOrdinanzaRepository.findCnmTAllegatosByCnmTOrdinanza(cnmTOrdinanza);
		// check master
		CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.LETTERA_ORDINANZA)).orNull();
		if (cnmTAllegatoMaster == null) {
			logger.info("nessun allegato master trovato!");
			return;
		}
		if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO) {
			logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
			return;
		}
		// aggiorno in spostamento
		cnmTAllegatos = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatos);

		CnmDStatoAllegato avviospostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
			if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
				logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: " + cnmTAllegato.getNomeFile());
				ResponseAggiungiAllegato responseAggiungiAllegato = null;
				try {

					responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
							utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTOrdinanza),
							utilsDoqui.getRootActa(cnmTOrdinanza), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), stadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null);
				} catch (Exception e) {
					logger.error("Non riesco ad aggiungere l'allegato al master", e);
					cnmTAllegato.setCnmDStatoAllegato(avviospostamentoActa);
					cnmTAllegato.setDataOraUpdate(now);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
				}
				if (responseAggiungiAllegato != null) {
					logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

					String idIndex = cnmTAllegato.getIdIndex();
					cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
					cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
					cnmTAllegato.setIdIndex(null);
					cnmTAllegato.setIdActaMaster(cnmTAllegatoMaster.getIdActa());
					cnmTAllegato.setDataOraUpdate(now);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

					try {
						stadocServiceFacade.eliminaDocumentoIndex(idIndex);
					} catch (Exception e) {
						logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
					}
				}
			}
		}
	}

	
	
	
	
	// 20200625_LC
	public void sendAllegatiInCodaToActa_Doqui() {
		

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
	    
		List<CnmTOrdinanza> cnmTOrdinanzas = cnmTOrdinanzaRepository.findCnmTOrdinanzaAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		List<CnmTOrdinanza> cnmTOrdinanzasSog = cnmTOrdinanzaRepository.findCnmTOrdinanzaSogAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		cnmTOrdinanzas.addAll(cnmTOrdinanzasSog);
		
		if (cnmTOrdinanzas.isEmpty())
			return;

		logger.info("Send allegati Ordinanza To Acta START");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzas.get(0);

		
		
		
		
		
		
		

		// tutti gli allegati dell'ordinanza (compresi quelli di OrdinanzSoggetto, considerare se è il caso di cambiare struttura)
		List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoOrdinanzaRepository.findCnmTAllegatosByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmTAllegato> cnmTAllegatosOrdSog = cnmRAllegatoOrdVerbSogRepository.findCnmTAllegatosByCnmTOrdinanza(cnmTOrdinanza);
		cnmTAllegatos.addAll(cnmTAllegatosOrdSog);
		
		
		// master 
		manageLetteraOrdinanza(cnmTAllegatos, cnmTOrdinanza);
		manageIstanzaRateizzazione(cnmTAllegatos, cnmTOrdinanza);
		

		
	}

	
	private void manageIstanzaRateizzazione(List<CnmTAllegato> cnmTAllegatos, CnmTOrdinanza cnmTOrdinanza) {


		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		String operationToTrace = null;
		
		CnmTAllegato cnmTAllegatoMasterIstanza = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.ISTANZA_RATEIZZAZIONE)).orNull();
		
		// GESTIONE ISTANZA (38): se è presente l'istanza e non è ancora protocollata, allora aggiungere i suoi allegati e protocollare (tutto insieme)	
		if (cnmTAllegatoMasterIstanza != null && cnmTAllegatoMasterIstanza.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_NON_PROTOCOLLARE) {
			logger.info("Trovato allegato master (istanza) in stato non protocollato");			

			// recupera allegati solo quelli del tipo giusto (38)
			List<CnmTAllegato> cnmTAllegatoList = cnmTAllegatoRepository.findAllegatiIstanzaOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			
			// aggiorno in spostamento
			cnmTAllegatoList = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatoList);

			CnmDStatoAllegato avviospostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
			for (CnmTAllegato cnmTAllegato : cnmTAllegatoList) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {

						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMasterIstanza.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTOrdinanza),
								utilsDoqui.getRootActa(cnmTOrdinanza), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avviospostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setIdActaMaster(cnmTAllegatoMasterIstanza.getIdActa());
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
//							20200729_ET modificato per JIRA http://jiraprod.csi.it:8083/browse/CONAM-78
//								stadocServiceFacade.eliminaDocumentoIndex(idIndex);
								doquiServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}

					
						// 20200622_LC traccia job spostamento
						String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().lastIndexOf(".")+1);
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
						
						// 20201120_LC	traccia inserimento allegato su Acta
						operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseAggiungiAllegato.getObjectIdDocumento(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
							
					}
					
				
				}
				
				
			}
			
			
			// PROTOCOLLAZIONE ALLEGATO MASTER GIA ESISTENTE
			// se tutti i cnmTAllegatoList hanno idActa != null, idActaMaster!=null, e idActaMaster == idACta di cnmTAllegatoMasterIstanza
			// si fa la protocollazione
			if (protocollazioneDaAvviare(cnmTAllegatoList, cnmTAllegatoMasterIstanza)) {				
				CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
				ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMasterIstanza, cnmTUser);
				
				// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
				String numProtocollo = responseProtocollaEsistente.getProtocollo();
				for (CnmTAllegato cnmTAllegato : cnmTAllegatoList) {
					cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
					cnmTAllegato.setNumeroProtocollo(numProtocollo);
					cnmTAllegato.setDataOraProtocollo(now);
					cnmTAllegato.setCnmTUser1(cnmTUser);
					cnmTAllegato.setDataOraUpdate(now);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);	// fa il semplice save
					CnmTDocumento cnmTDocumento = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegato.getIdActa()));
					if (cnmTDocumento != null) {
						cnmTDocumento.setProtocolloFornitore(numProtocollo);
						cnmTDocumento.setCnmTUser1(cnmTUser);
						cnmTDocumento.setDataOraUpdate(now);
						cnmTDocumentoRepository.save(cnmTDocumento);
					}
				}				
			}
			
			
			
			
		} else {
			logger.info("Nessun allegato master trovato nello stato corretto");
		}

		
	}


	private void manageLetteraOrdinanza(List<CnmTAllegato> cnmTAllegatos, CnmTOrdinanza cnmTOrdinanza) {

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		String operationToTrace = null;
		
		CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.LETTERA_ORDINANZA)).orNull();
		// GESTIONE ORDINANZE (11,12,34,35): se è presente la lettera già protocollata, allora bisogna spostare in acta il suo allegato (ordinanza)
		if (cnmTAllegatoMaster != null && cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_NON_PROTOCOLLARE) {
			logger.info("Trovato allegato master (lettera) in stato protocollato");

			// recupero allegati solo quelli del tipo giusto (11,12,34,35)
			List<CnmTAllegato> cnmTAllegatoList = cnmTAllegatoRepository.findAllegatiLetteraOrdinanza(cnmTOrdinanza.getIdOrdinanza());	
			
			// aggiorno in spostamento
			cnmTAllegatoList = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatoList);

			CnmDStatoAllegato avviospostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
			for (CnmTAllegato cnmTAllegato : cnmTAllegatoList) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {

						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTOrdinanza),
								utilsDoqui.getRootActa(cnmTOrdinanza), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avviospostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setIdActaMaster(cnmTAllegatoMaster.getIdActa());
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
//									20200729_ET modificato per JIRA http://jiraprod.csi.it:8083/browse/CONAM-78
//										stadocServiceFacade.eliminaDocumentoIndex(idIndex);
								doquiServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}

					
						// 20200622_LC traccia job spostamento
						String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().lastIndexOf(".")+1);
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
						
						// 20201120_LC	traccia inserimento allegato su Acta
						operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseAggiungiAllegato.getObjectIdDocumento(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
							
					}
					
				
				}
			}

			
			// 20210831 PP - CR_107 PROTOCOLLAZIONE ALLEGATO MASTER GIA ESISTENTE
			// se tutti i cnmTAllegatoList hanno idActa != null, idActaMaster!=null, e idActaMaster == idACta di cnmTAllegatoMasterIstanza
			// si fa la protocollazione
			if (protocollazioneDaAvviare(cnmTAllegatoList, cnmTAllegatoMaster)
					&& cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_NON_PROTOCOLLARE) {				
				CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
				ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMaster, cnmTUser);
				
				// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
				String numProtocollo = responseProtocollaEsistente.getProtocollo();
				for (CnmTAllegato cnmTAllegato : cnmTAllegatoList) {
					cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
					cnmTAllegato.setNumeroProtocollo(numProtocollo);
					cnmTAllegato.setDataOraProtocollo(now);
					cnmTAllegato.setCnmTUser1(cnmTUser);
					cnmTAllegato.setDataOraUpdate(now);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);	// fa il semplice save
					CnmTDocumento cnmTDocumento = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegato.getIdActa()));
					if (cnmTDocumento != null) {
						cnmTDocumento.setProtocolloFornitore(numProtocollo);
						cnmTDocumento.setCnmTUser1(cnmTUser);
						cnmTDocumento.setDataOraUpdate(now);
						cnmTDocumentoRepository.save(cnmTDocumento);
					}
				}				
			}
		
		} 
				
				
				
				
	}


	private boolean protocollazioneDaAvviare(List<CnmTAllegato> allegati, CnmTAllegato master) {
		
		// se almeno uno degli allegati all'istanza ha idActa=null, idActaMaster=null, e idActa=!idActaDelMster, allora non parte la protocollazione
		
		if (allegati != null && !allegati.isEmpty()) {
			
			for(CnmTAllegato all : allegati) {
				if(!(all.getIdActaMaster() != null  && all.getIdActaMaster().length() != 0 && 
						all.getIdActa() != null && all.getIdActa().length() != 0 && 
						all.getIdActaMaster().equalsIgnoreCase(master.getIdActa()))) {
					return false;
				}
			}
			
		} else {
			return false;
		}

		return true;
	
	}	
	
	
	
	
	
	
	
	
	
}
