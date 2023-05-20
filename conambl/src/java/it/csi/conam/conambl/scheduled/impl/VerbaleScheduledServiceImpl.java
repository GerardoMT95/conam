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
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbalePK;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.repositories.CnmDStatoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.scheduled.VerbaleScheduledService;
import it.csi.conam.conambl.util.UtilsTipoAllegato;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class VerbaleScheduledServiceImpl implements VerbaleScheduledService {

	protected static Logger logger = Logger.getLogger(VerbaleScheduledServiceImpl.class);

	private static final int DAY_BEFORE = 2;
	
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private StadocServiceFacade stadocServiceFacade;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private AllegatoScheduledService allegatoScheduledService;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Autowired
	private DoquiServiceFacade doquiServiceFacade;

	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;

	// 20200622_LC
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	
	// 20200711_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Autowired
	private CommonAllegatoService commonAllegatoService;

	@Autowired
	private CnmTDocumentoRepository cnmTDocumentoRepository;

	
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

	public void sendAllegatiInCodaToActa_Stadoc() {
		

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato(timestamp);
		if (cnmTVerbales.isEmpty() && (allegati == null || allegati.isEmpty())) {
			logger.info("Nessun Allegato o verbale trovato da spostare in ACTA");
			return;
		}

		logger.info("Send allegati To Acta START");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);

		if (!(allegati == null || allegati.isEmpty())) {

			// aggiorno in spostamento
			allegati = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(allegati);

			for (CnmTAllegato cnmTAllegato : allegati) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());

					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: "  + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;					
					try {
						responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null);
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							stadocServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}

					}

					CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
					if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
						logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
						CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
						logger.info("id verbale Master :: " + master.getIdVerbale());

						CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
						CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
						cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
						cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
						cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
						cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
						cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
					}
				}
								
			}

		}

		if (!cnmTVerbales.isEmpty()) {
			CnmTVerbale cnmTVerbale = cnmTVerbales.get(0);

			// faccio un verbale alla volta
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoVerbaleRepository.findCnmTAllegatosByCnmTVerbale(cnmTVerbale);
			// check master
			CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE)).orNull();

			if (cnmTAllegatoMaster == null) {
				logger.info("nessun allegato master trovato!");
				return;

			}

			if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO || cnmTVerbale.getNumeroProtocollo() == null) {
				logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
				return;
			}

			// aggiorno in spostamento
			cnmTAllegatos = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatos);

			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome allegato: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {
						responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO, null);
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
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
	}
	

	public void sendAllegatiInCodaToActa_Doqui() {
		

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		if(cnmTVerbales.isEmpty()) {
			cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbalePending();
		}
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato(timestamp);
		if (cnmTVerbales.isEmpty() && (allegati == null || allegati.isEmpty())) {
			logger.info("Nessun Allegato o verbale trovato da spostare in ACTA");
			return;
		}

		logger.info("Send allegati To Acta START");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		String operationToTrace = null;


		//2021901 PP - CR_107
		manageComparsa(allegati, avvioSpostamentoActa);
		

		if (!cnmTVerbales.isEmpty()) {

			CnmTVerbale cnmTVerbale = cnmTVerbales.get(0);
			
			// faccio un verbale alla volta
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoVerbaleRepository.findCnmTAllegatosByCnmTVerbale(cnmTVerbale);
			// cerco relata di notifica

			List<CnmTAllegato> allegatiRelata = cnmTAllegatoRepository.findAllegatiRelataAvviaSpostamento(cnmTVerbale.getIdVerbale());
			cnmTAllegatos.addAll(allegatiRelata);
			
			// check master
			CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE)).orNull();

			if (cnmTAllegatoMaster == null) {
				logger.info("nessun allegato master trovato!");
				return;

			}

			// 20210805 PP - elimino questo controllo poiche' il master è ancora da protocollare e il numero di protocollo non è stato ancora riportato sul doc
//			if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO || cnmTVerbale.getNumeroProtocollo() == null) {
//				logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
//				return;
//			}

			// aggiorno in spostamento
			cnmTAllegatos = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatos);

			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome allegato: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {
						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
						
						
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
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
			
			// 20210805 PP - proseguo con la protocollazione del master del verbale
			CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMaster, cnmTUser);
			
			// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
			String numProtocollo = responseProtocollaEsistente.getProtocollo();
			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if(cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato()!=Constants.STATO_ALLEGATO_NON_PROTOCOLLARE
						&& cnmTAllegato.getNumeroProtocollo() == null) {
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


	private void manageComparsa(List<CnmTAllegato> allegati, CnmDStatoAllegato avvioSpostamentoActa) {

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		String operationToTrace = null;
		
		if (!(allegati == null || allegati.isEmpty())) {

			// aggiorno in spostamento
			allegati = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(allegati);
			
			for (CnmTAllegato cnmTAllegato : allegati) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
					CnmTAllegato cnmTAllegatoMaster = cnmTAllegatoRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
					
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: "  + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;					
					try {
						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							doquiServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}
						
					
						// 20200622_LC traccia job spostamento
						String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().lastIndexOf(".")+1);
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(),className+"."+ Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						// 20201120_LC	traccia inserimento allegato su Acta
						operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseAggiungiAllegato.getObjectIdDocumento(), className+"."+ Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

					}

					CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
					if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
						logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
						CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
						logger.info("id verbale Master :: " + master.getIdVerbale());

						CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
						CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
						cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
						cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
						cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
						cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
						cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
						
						
					}

					// 20210901 PP - CR_107
					// PROTOCOLLAZIONE ALLEGATO MASTER GIA ESISTENTE
					// se tutti i cnmTAllegatoList hanno idActa != null, idActaMaster!=null, e idActaMaster == idACta di cnmTAllegatoMasterIstanza
					// si fa la protocollazione
					if (protocollazioneDaAvviare(allegati, cnmTAllegatoMaster)) {				
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMaster, cnmTUser);
						
						// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
						String numProtocollo = responseProtocollaEsistente.getProtocollo();
						for (CnmTAllegato cnmTAllegatoUpdate : allegati) {
							if(cnmTAllegatoUpdate.getIdActaMaster().equalsIgnoreCase(cnmTAllegatoMaster.getIdActa())){
								cnmTAllegatoUpdate.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
								cnmTAllegatoUpdate.setNumeroProtocollo(numProtocollo);
								cnmTAllegatoUpdate.setDataOraProtocollo(now);
								cnmTAllegatoUpdate.setCnmTUser1(cnmTUser);
								cnmTAllegatoUpdate.setDataOraUpdate(now);
								allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegatoUpdate);	// fa il semplice save
								CnmTDocumento cnmTDocumento = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegatoUpdate.getIdActa()));
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
	
	
	@SuppressWarnings("unused")
	private boolean masterProtocollabile(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales, CnmRAllegatoVerbale cnmRAllegatoVerbaleMaster) {
		
		if(cnmRAllegatoVerbaleMaster!= null) {
			for(CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbales) {
				if(cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster() != null && cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster().equalsIgnoreCase(cnmRAllegatoVerbaleMaster.getCnmTAllegato().getIdActa())
						&& (cnmRAllegatoVerbale.getCnmTAllegato().getIdActa() == null || cnmRAllegatoVerbale.getCnmTAllegato().getIdActa().length() == 0)) {
					return false;
				}
			}
		}else {
			return false;
		}
		
		return true;
	}
	
	
	
	
}
