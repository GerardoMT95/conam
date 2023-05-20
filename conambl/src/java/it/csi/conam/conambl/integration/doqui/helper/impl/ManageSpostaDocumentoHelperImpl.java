/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.RequestSpostaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.beans.Soggetto;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.SoggettoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.entity.*;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.SpostaDocumentoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageRicercaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.helper.ManageSpostaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.service.ActaManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageSpostaDocumentoHelperImpl extends CommonManageDocumentoHelperImpl implements ManageSpostaDocumentoHelper {
	// 20200626_LC

	// 20200709_LC gestiti in costanti
//	private static final String TIPO_DOCUMENTO_ACTA = "CONAM_ACTA";
//	private static final String TIPO_DOCUMENTO_CONAM_1 = "CONAM_A1";
//	private static final String TIPO_DOCUMENTO_CONAM_2 = "CONAM_A2";

	private static Logger log = Logger.getLogger(ManageSpostaDocumentoHelperImpl.class);

	@Autowired
	private ActaManagementService	actaManagementService;
	
	// 20200703_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;	
	

	// 20200708_LC
	@Autowired
	private ManageRicercaDocumentoHelper manageRicercaDocumentoHelper;

	// 20200708_LC
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	
	@Autowired
	private UtilsDoqui utilsDoqui;
	
	
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private UtilsVerbale utilsVerbale;
	

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseSpostaDocumento spostaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException  {
		
		String method = "spostaDocumento";
		log.debug(method + ". BEGIN");
		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		List<String> idToTraceList = new ArrayList<String>();
		String rootActa = request.getRootActa();
		String parolaChiaveFolderTemp = request.getParolaChiaveFolderTemp();
		
		// 20200730_LC
		String soggettoActaFruitore = request.getSoggettoActa();
		

		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder            = " + request.getFolder());
			log.debug(method + ". numeroProtocollo   = " + request.getNumeroProtocollo());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new SpostaDocumentoException("Request non valorizzata");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SpostaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new SpostaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SpostaDocumentoException("Folder partenza non presente");
			if(StringUtils.isBlank(request.getNumeroProtocollo())) throw new SpostaDocumentoException("Folder partenza non presente");



			
			
			
			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_SPOSTAMENTO_PROTOCOLLAZIONE);
			

			// DOCUMENTO qui crea solo il documento master (quello protocollato)

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}
			
			CnmTDocumento cnmTDocumento = new CnmTDocumento();
//			docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());	
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 

			if(request.getMetadati() != null)
			{
//				cnmTDocumento.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());	// 20200720_LC in caso di multitipo contiene già "... N allegati ..."
//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}
			

			
			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			// 20201007 PP - non setto la parola chiave poiche' sara' impostata con quella restituita da acta
			// cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
			CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			

			//RICHIESTA DOCUMENTO

			// 20200610_LC
			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);

			//POJO UTENTE
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			
			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());

			//POJO DOCUMENTO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC

			
			// 20200730_LC
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			
			SoggettoActa soggettoActa = new SoggettoActa();
			if (Soggetto.TOPOLOGIA_SOGGETTO_MITTENTE.equals(request.getSoggetto().getTipologia()))
			{
				soggettoActa.setMittente(true);
				documentoElettronicoActa.setAutore(request.getSoggetto().getCognome());
				//TODO Da verificare la denominazione
				log.debug(method + ". cnmDTipoDocumento.getDescFormadocEntrata(): " + cnmDTipoDocumento.getDescFormadocEntrata());
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocEntrata());
			}
			else
			{
				soggettoActa.setMittente(false);
				documentoElettronicoActa.setAutore(""); //REQUISITO CHIDERE A PAOLO
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());

			}
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			soggettoActa.setCognome(request.getSoggetto().getCognome());
			soggettoActa.setNome(request.getSoggetto().getNome());
			if (request.getSoggetto().getDenominazione() != null)
				soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());

			documentoElettronicoActa.setSoggettoActa(soggettoActa);
			

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
			}

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}
		
	
		KeyDocumentoActa spostaResponse = actaManagementService.spostaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp);
		String idClassificazioneNew = spostaResponse.getObjectIdClassificazione();
		log.debug(method + ". idClassificazioneNew =\n " + idClassificazioneNew);

		if (idClassificazioneNew != null)
		{

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

			
			List<DocumentoProtocollatoVO> listaNuoviDocumenti = manageRicercaDocumentoHelper.ricercaDocumentoProtocollato(request.getNumeroProtocollo(),request.getCodiceFruitore());
			// per ogni elemento della lista diverso dal master (ossia che non ha registrazioneId) crea nuovo record in CnmTDocumento
			
			if(listaNuoviDocumenti!=null && !listaNuoviDocumenti.isEmpty()) {

				for (DocumentoProtocollatoVO documentoNuovo : listaNuoviDocumenti) {
					
					// 20201124_LC salva l'objectIdDOcumento per tracciarlo al livello superiore
					idToTraceList.add(documentoNuovo.getObjectIdDocumento());
					
					// 20200708_LC trova l'allegato che corrisponde al documento appena creato tramite l'objectId_spostamento_acta, aggiornarne gli id_acta
					List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareCompleteList = cnmTAllegatoRepository.findByObjectidSpostamentoActa(documentoNuovo.getObjectIdDocumento());
					
					//20201022_ET aggiungo filtro per aggiornare solo gli allegati legati al verbale in lavorazione
					// 20210420_LC se idVerbale è NULL, allora è un documento temporaneo (senza verbale) e non aggiorna la lista
					List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareList = new ArrayList<CnmTAllegato>();
					if (request.getIdVerbale() != null) {
						cnmTAllegatoProtocollatoDaAggiornareList = utilsVerbale.filtraAllegatiPerVerbale(Integer.parseInt(request.getIdVerbale()), cnmTAllegatoProtocollatoDaAggiornareCompleteList);
					} else {
						cnmTAllegatoProtocollatoDaAggiornareList = cnmTAllegatoProtocollatoDaAggiornareCompleteList;
					}
				
					if (StringUtils.isBlank(documentoNuovo.getRegistrazioneId())){	// solo allegati (non hanno registrazioneId)
					
						// nuovo CnmTDOcumento
						// corrispondenza tra nuovo documento ed allegato già esistente (col campo objecctid_spostamento_acta in allegato)
						// per ogni nuovo documento andrà modiicato parole chiave su acta = idDocumento appena creato (con la updatePropertiesParolaChiaveDocumento)
						

					
						// 20200722_LC crea nuovi documenti solo se sono tra quelli già associati al verbale come allegati (anche se poi su acta ce li sposta tutti)
						if (!cnmTAllegatoProtocollatoDaAggiornareList.isEmpty()) {

						CnmTDocumento cnmTDocumentoAllegato = new CnmTDocumento();
						cnmTDocumentoAllegato.setFolder(request.getFolder());	
						//cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumento);	
						
						// tipo documento
						// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
						boolean isDocSigned = false;
						if (request.getDocumento() != null && request.getDocumento().getNomeFile() != null && request.getDocumento().getNomeFile().toUpperCase().endsWith(".P7M"))
							isDocSigned = true;
						
						String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
						long tipoDocumento = cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getIdTipoAllegato();	// 20200720_LC dovrebbe andar bene sempre il 27, verificare (controdeduzione è 29), uno qualsiasi
						if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
								|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()
								|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

						} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
						}
						CnmDTipoDocumento cnmDTipoDocumentoAllegato = getTipoDocumento(tipoDoc);
						cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumentoAllegato);
						
						
						cnmTDocumentoAllegato.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
						cnmTDocumentoAllegato.setCnmTUser2(cnmTUser);		// 20200702_LC

						String entitaFruitore = null;
						if(request.getMetadati() != null)
						{
							
							// 20200804_LC se ha più tipi
							String prefix = request.getIdVerbale() != null ? request.getIdVerbale() : "TEMP"; // in caso di documento temporaneo (senza verbale)
							if (cnmTAllegatoProtocollatoDaAggiornareList.size()==1) {
								entitaFruitore = prefix + " - " + cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
							} else {
								//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";			
								entitaFruitore = prefix +  " - ";
								for (CnmTAllegato all:cnmTAllegatoProtocollatoDaAggiornareList) {
									entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
								}
								entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
							}
							
							cnmTDocumentoAllegato.setIdentificativoEntitaFruitore(entitaFruitore);	// è dievrso da quellod el master
						}

						cnmTDocumentoAllegato = getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);
						// 20201007 PP - Se il doc su acta ha la parola chiave, allora la salvo sul db -	JIRA 100
						if(!StringUtils.isEmpty(documentoNuovo.getParolaChiave())) {
							cnmTDocumentoAllegato.setIdentificativoArchiviazione(documentoNuovo.getParolaChiave());
							// cnmTDocumentoAllegato.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumentoAllegato.getIdDocumento())));	// 20200713_LC
						}
						
				
						CnmTDocumento cnmTDocumentoAllegatoN =getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);	
						
						if(log.isDebugEnabled()){
							log.debug(method + ". inserito documento           = " + cnmTDocumentoAllegatoN);
						}
						
						
						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumentoAllegatoN.getIdDocumento(), documentoNuovo.getDocumentoUUID(), null,
								  null, null,  documentoNuovo.getClassificazioneId(), documentoNuovo.getObjectIdDocumento(), null);
					
											

						// aggiorna allegato corrispondente al nuovo documento
						
						
						
						// 20200720_LC in caso di documento contenente più allegati è una lista
						for (CnmTAllegato cnmTAllegatoDaAggiornare:cnmTAllegatoProtocollatoDaAggiornareList) {
							cnmTAllegatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumentoAllegato.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumento.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoDaAggiornare);					
						}

						
						// 20200721_LC aggiorna properties su Acta - parola chiave + oggetto
						// 20201007 PP - Commentato per non fare update della parolaChiave	-	JIRA 100
						// actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoAllegatoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200713_LC
						
						}
						
						
					} else {	// il master (già protocollato) va solo aggiornato con idRegistrazione, classificazione_id e protocollo_fornitore
						
						

						response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
						response.setProtocollo(request.getNumeroProtocollo());
						response.setIndiceClassificazione(documentoNuovo.getClassificazione());
						
						// 20210702_LC Jira 149 il nuovo CnmTDocumento per il master viene fatto in ogni caso (anche per documenti temporanei senza idVerbale); i suoi attributi vanno quindi aggiornati anche se cnmTAllegatoProtocollatoDaAggiornareList is empty
						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumento.getIdDocumento(), documentoNuovo.getDocumentoUUID(), request.getNumeroProtocollo(),
								idClassificazioneNew, documentoNuovo.getRegistrazioneId(), idClassificazioneNew, documentoNuovo.getObjectIdDocumento(), null);

						// se doc temporaneo (Scritti difensivi), setta idEntitaFruitore: TEMP ...
						if (request.getIdVerbale() == null) {
							cnmTDocumentoN.setIdentificativoEntitaFruitore("TEMP - Scritti difensivi");
							cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumentoN);							
						}
						
			
						// 20200720_LC in caso di documento contenente più allegati è una lista
						String entitaFruitore = null;				
						if (!cnmTAllegatoProtocollatoDaAggiornareList.isEmpty()) {
													
							// 20200804_LC gestione caso rapporto di trsmissione != vecchio master
							String prefix = request.getIdVerbale() != null ? request.getIdVerbale() : "TEMP"; // in caso di documento temporaneo (senza verbale)
							if (cnmTAllegatoProtocollatoDaAggiornareList.size()==1) {
								entitaFruitore = prefix + " - " + cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
							} else {
								//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";										
								entitaFruitore = prefix +  " - ";
								for (CnmTAllegato all:cnmTAllegatoProtocollatoDaAggiornareList) {
									entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
								}
								entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
							}
							
							cnmTDocumentoN.setIdentificativoEntitaFruitore(entitaFruitore);
							cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumentoN);					
														
//							changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumento.getIdDocumento(), documentoNuovo.getDocumentoUUID(), request.getNumeroProtocollo(),
//									idClassificazioneNew, documentoNuovo.getRegistrazioneId(), idClassificazioneNew, documentoNuovo.getObjectIdDocumento(), null);
											
							
						for (CnmTAllegato cnmTAllegatoProtocollatoDaAggiornare:cnmTAllegatoProtocollatoDaAggiornareList) {
							cnmTAllegatoProtocollatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumento.getIdDocumento())));
							// cnmTAllegatoProtocollatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumento.getIdDocumento())));
							cnmTAllegatoProtocollatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoProtocollatoDaAggiornare);						
						}
						
			
						// 20200721_LC aggiorna properties su Acta - parola chiave + oggetto
						// 20201007 PP - Commentato per non fare update della parolaChiave	-	JIRA 100
						// actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200713_LC

						
						}
						
						

						
						
					}

				
				}
			}			
			
		}
		else
			containsError = true;
			
		// 20201124_LC
		response.setIdFolder(spostaResponse.getIdFolderCreated());
		response.setObjectIdDocumentoToTraceList(idToTraceList);
		
		return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	

	
	
	
	
	// 20200728_LC
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseSpostaDocumento copiaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException  {
		
		String method = "copiaDocumento";
		log.debug(method + ". BEGIN");
		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		List<String> idToTraceList = new ArrayList<String>();
		String rootActa = request.getRootActa();
		String parolaChiaveFolderTemp = request.getParolaChiaveFolderTemp();
		
		// 20200730_LC
		String soggettoActaFruitore = request.getSoggettoActa();

		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder            = " + request.getFolder());
			log.debug(method + ". numeroProtocollo   = " + request.getNumeroProtocollo());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new SpostaDocumentoException("Request non valorizzata");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SpostaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new SpostaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SpostaDocumentoException("Folder partenza non presente");
			if(StringUtils.isBlank(request.getNumeroProtocollo())) throw new SpostaDocumentoException("Folder partenza non presente");



			
			
			
			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_COPIA_PROTOCOLLAZIONE);
			

			// DOCUMENTO qui crea solo il documento master (quello protocollato)

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}
			
			CnmTDocumento cnmTDocumento = new CnmTDocumento();
//			docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());	
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 

			if(request.getMetadati() != null)
			{
//				cnmTDocumento.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());	// 20200720_LC in caso di multitipo contiene già "... N allegati ..."
//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}
			

			
			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			// 20201007 PP - non setto la parola chiave poiche' sara' impostata con quella restituita da acta
			// cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
			CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			

			//RICHIESTA DOCUMENTO

			// 20200610_LC
			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);

			//POJO UTENTE
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			
			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());

			//POJO DOCUMENTO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC

			// 20200730_LC
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			
			SoggettoActa soggettoActa = new SoggettoActa();
			if (Soggetto.TOPOLOGIA_SOGGETTO_MITTENTE.equals(request.getSoggetto().getTipologia()))
			{
				soggettoActa.setMittente(true);
				documentoElettronicoActa.setAutore(request.getSoggetto().getCognome());
				//TODO Da verificare la denominazione
				log.debug(method + ". cnmDTipoDocumento.getDescFormadocEntrata(): " + cnmDTipoDocumento.getDescFormadocEntrata());
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocEntrata());
			}
			else
			{
				soggettoActa.setMittente(false);
				documentoElettronicoActa.setAutore(""); //REQUISITO CHIDERE A PAOLO
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());

			}
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			soggettoActa.setCognome(request.getSoggetto().getCognome());
			soggettoActa.setNome(request.getSoggetto().getNome());
			if (request.getSoggetto().getDenominazione() != null)
				soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());

			documentoElettronicoActa.setSoggettoActa(soggettoActa);
			

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
			}

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}
		
	
		KeyDocumentoActa copiaResponse = actaManagementService.copiaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp);
		String idClassificazioneNew = copiaResponse.getObjectIdClassificazione();
		log.debug(method + ". idClassificazioneNew =\n " + XmlSerializer.objectToXml(idClassificazioneNew));

		if (idClassificazioneNew != null)
		{

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

			
			List<DocumentoProtocollatoVO> listaNuoviDocumenti = manageRicercaDocumentoHelper.ricercaDocumentoProtocollato(request.getNumeroProtocollo(),request.getCodiceFruitore());
			// per ogni elemento della lista diverso dal master (ossia che non ha registrazioneId) crea nuovo record in CnmTDocumento
			
			if(listaNuoviDocumenti!=null && !listaNuoviDocumenti.isEmpty()) {

				for (DocumentoProtocollatoVO documentoNuovo : listaNuoviDocumenti) {
					
					// 20201124_LC salva l'objectIdDOcumento per tracciarlo al livello superiore
					idToTraceList.add(documentoNuovo.getObjectIdDocumento());
					
					// 20200708_LC trova l'allegato che corrisponde al documento appena creato tramite l'objectId_spostamento_acta, aggiornarne gli id_acta
					List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareCompleteList = cnmTAllegatoRepository.findByObjectidSpostamentoActa(documentoNuovo.getObjectIdDocumento());
					//20201022_ET aggiungo filtro per aggiornare solo gli allegati legati al verbale in lavorazione
					// 20210420_LC se idVerbale è NULL, allora è un documento temporaneo (senza verbale) e non aggiorna la lista
					List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareList = new ArrayList<CnmTAllegato>();
					if (request.getIdVerbale() != null) {
						cnmTAllegatoProtocollatoDaAggiornareList = utilsVerbale.filtraAllegatiPerVerbale(Integer.parseInt(request.getIdVerbale()), cnmTAllegatoProtocollatoDaAggiornareCompleteList);
					} else {
						cnmTAllegatoProtocollatoDaAggiornareList = cnmTAllegatoProtocollatoDaAggiornareCompleteList;
					}
					
				
					if (StringUtils.isBlank(documentoNuovo.getRegistrazioneId())){	// solo allegati (non hanno registrazioneId)
					
						// nuovo CnmTDOcumento
						// corrispondenza tra nuovo documento ed allegato già esistente (col campo objecctid_spostamento_acta in allegato)
						// per ogni nuovo documento andrà modiicato parole chiave su acta = idDocumento appena creato (con la updatePropertiesParolaChiaveDocumento)
						

					
						// 20200722_LC crea nuovi documenti solo se sono tra quelli già associati al verbale come allegati (anche se poi su acta ce li sposta tutti)
						if (!cnmTAllegatoProtocollatoDaAggiornareList.isEmpty()) {

						CnmTDocumento cnmTDocumentoAllegato = new CnmTDocumento();
						cnmTDocumentoAllegato.setFolder(request.getFolder());	
						//cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumento);	
						
						// tipo documento
						// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
						boolean isDocSigned = false;
						if (request.getDocumento() != null && request.getDocumento().getNomeFile() != null && request.getDocumento().getNomeFile().toUpperCase().endsWith(".P7M"))
							isDocSigned = true;
						
						String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
						long tipoDocumento = cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getIdTipoAllegato();	// 20200720_LC dovrebbe andar bene sempre il 27, verificare (controdeduzione è 29), uno qualsiasi
						if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId()
								|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()
								|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

						} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
						}
						CnmDTipoDocumento cnmDTipoDocumentoAllegato = getTipoDocumento(tipoDoc);
						cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumentoAllegato);
						
						
						cnmTDocumentoAllegato.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
						cnmTDocumentoAllegato.setCnmTUser2(cnmTUser);		// 20200702_LC

						String entitaFruitore = null;
						if(request.getMetadati() != null)
						{
							
							// 20200804_LC se ha più tipi
							String prefix = request.getIdVerbale() != null ? request.getIdVerbale() : "TEMP"; // in caso di documento temporaneo (senza verbale)
							if (cnmTAllegatoProtocollatoDaAggiornareList.size()==1) {
								entitaFruitore = prefix + " - " + cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
							} else {
								//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";										
								entitaFruitore = prefix +  " - ";
								for (CnmTAllegato all:cnmTAllegatoProtocollatoDaAggiornareList) {
									entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
								}
								entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
							}
							
							cnmTDocumentoAllegato.setIdentificativoEntitaFruitore(entitaFruitore);	// è dievrso da quellod el master
						}

						cnmTDocumentoAllegato = getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);
						// 20201007 PP - non setto la parola chiave poiche' sara' impostata con quella restituita da acta
						// cnmTDocumentoAllegato.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumentoAllegato.getIdDocumento())));	// 20200713_LC
						
				
						CnmTDocumento cnmTDocumentoAllegatoN =getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);	
						
						if(log.isDebugEnabled()){
							log.debug(method + ". inserito documento           = " + cnmTDocumentoAllegatoN);
						}
						
						
							

						// aggiorna allegato corrispondente al nuovo documento
						
						
						
						// 20200720_LC in caso di documento contenente più allegati è una lista
						for (CnmTAllegato cnmTAllegatoDaAggiornare:cnmTAllegatoProtocollatoDaAggiornareList) {
							cnmTAllegatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumentoAllegato.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumento.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoDaAggiornare);					
						}

						//  20200804_LC non aggiorna parola chiave ed oggetto, ma deve impostare la parola chiave del nuovo doc uguale a quella del vecchio
						//actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoAllegatoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200713_LC
						String parolaChiave = actaManagementService.getParolaChiaveByObjectIdDocumento(documentoNuovo.getObjectIdDocumento(), utenteActa);
	
						// per copia aggiorna anche la parola chiave (identificativoArchiviazione)
						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumentoAllegatoN.getIdDocumento(), documentoNuovo.getDocumentoUUID(), null,
								  null, null,  documentoNuovo.getClassificazioneId(), documentoNuovo.getObjectIdDocumento(), parolaChiave);
					
				
						
						}
						
						
					} else {	// il master (già protocollato) va solo aggiornato con idRegistrazione, classificazione_id e protocollo_fornitore
						
						

						response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
						response.setProtocollo(request.getNumeroProtocollo());
						response.setIndiceClassificazione(documentoNuovo.getClassificazione());
						

				
						// 20200720_LC in caso di documento contenente più allegati è una lista
						String entitaFruitore = null;
						if (!cnmTAllegatoProtocollatoDaAggiornareList.isEmpty()) {
							
							
							
						// 20200804_LC gestione caso rapporto di trsmissione != vecchio master
							String prefix = request.getIdVerbale() != null ? request.getIdVerbale() : "TEMP"; // in caso di documento temporaneo (senza verbale)
						if (cnmTAllegatoProtocollatoDaAggiornareList.size()==1) {
							entitaFruitore = prefix + " - " + cnmTAllegatoProtocollatoDaAggiornareList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
						} else {
							//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";										
							entitaFruitore = prefix +  " - ";
							for (CnmTAllegato all:cnmTAllegatoProtocollatoDaAggiornareList) {
								entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
							}
							entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
						}
						
						cnmTDocumentoN.setIdentificativoEntitaFruitore(entitaFruitore);
						cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumentoN);	
						

							
						for (CnmTAllegato cnmTAllegatoProtocollatoDaAggiornare:cnmTAllegatoProtocollatoDaAggiornareList) {
							cnmTAllegatoProtocollatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumento.getIdDocumento())));
							// cnmTAllegatoProtocollatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumento.getIdDocumento())));
							cnmTAllegatoProtocollatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoProtocollatoDaAggiornare);						
						}
						
				

						
						//  20200804_LC non aggiorna parola chiave ed oggetto, ma deve impostare la parola chiave del nuovo doc uguale a quella del vecchio
						//actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200804_LC descrizione con entitaFruitore corretta
						String parolaChiave = actaManagementService.getParolaChiaveByObjectIdDocumento(documentoNuovo.getObjectIdDocumento(), utenteActa);
						
						// per copia aggiorna anche la parola chiave (identificativoArchiviazione)
						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumento.getIdDocumento(), documentoNuovo.getDocumentoUUID(), request.getNumeroProtocollo(),
								idClassificazioneNew, documentoNuovo.getRegistrazioneId(), idClassificazioneNew, documentoNuovo.getObjectIdDocumento(), parolaChiave);

						}

						
						
					}

				
				}
			}			
			
		}
		else
			containsError = true;
			

		// 20201124_LC
		response.setIdFolder(copiaResponse.getIdFolderCreated());
		response.setObjectIdDocumentoToTraceList(idToTraceList);
		
		return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)
	public ResponseSpostaDocumento salvaAllegatoGiaPresenteNelFascicoloActa(DocumentoProtocollatoVO doc, CnmDTipoAllegato cnmDTipoAllegato, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser, List<CnmTAllegato> allegatiGiaSalvati) throws SpostaDocumentoException {
		String method = "salvaAllegatoGiaPresenteNelFascicoloActa";
		log.debug(method + ". BEGIN");

		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		boolean containsError = false;
		try {
//			String idEntitaFruitoreStart=cnmTVerbale.getIdVerbale() +  " - ";
			if(allegatiGiaSalvati == null || allegatiGiaSalvati.isEmpty()) throw new SpostaDocumentoException("allegatiGiaSalvati non valorizzata");
			List<Integer> idActaList = new ArrayList<Integer>();
			for(CnmTAllegato allegatoSalvato:allegatiGiaSalvati) {
				if(StringUtils.isNotBlank(allegatoSalvato.getIdActa()))
					idActaList.add(Integer.parseInt(allegatoSalvato.getIdActa()));
			}
//			List<CnmTDocumento> cnmTDocumentoList = getCnmTDocumentoRepository().findByObjectiddocumentoAndIdentificativoEntitaFruitoreStartsWith(doc.getObjectIdDocumento(), idEntitaFruitoreStart);
			List<CnmTDocumento> cnmTDocumentoList = null;
			if (idActaList != null && !idActaList.isEmpty() && idActaList.size() > 0)
				cnmTDocumentoList = getCnmTDocumentoRepository().findByObjectiddocumentoAndIdDocumentoIn(doc.getObjectIdDocumento(), idActaList);
			
			CnmTDocumento cnmTDocumento = null;
			if(cnmTDocumentoList!=null && !cnmTDocumentoList.isEmpty()) {
				log.debug(method + ". insert gia' presente in cnmTDocumento per verbale=" + cnmTVerbale.getIdVerbale() + " e objectId=" + doc.getObjectIdDocumento());
				cnmTDocumento = cnmTDocumentoList.get(0); //prendo il 1 perche' in teoria ce ne dovrebbe essere solo 1
				if(cnmTDocumentoList.size()>1) {
					log.warn(method + ". trovati " + cnmTDocumentoList.size() + " record sulla cnmTDocumento per verbale=" + cnmTVerbale.getIdVerbale() + " e objectId=" + doc.getObjectIdDocumento()); 
				}
				cnmTDocumento.setIdentificativoEntitaFruitore(cnmTDocumento.getIdentificativoEntitaFruitore().concat("; ").concat(cnmDTipoAllegato.getDescTipoAllegato()));
				cnmTDocumento.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));   
				cnmTDocumento.setCnmTUser1(cnmTUser); 
			}else {
				cnmTDocumento = new CnmTDocumento();
				cnmTDocumento.setFolder(utilsDoqui.createOrGetfolder(cnmTVerbale));    
				cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTDocumento.setCnmTUser2(cnmTUser);        
				cnmTDocumento.setIdentificativoEntitaFruitore(utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegato));    
				cnmTDocumento.setIdentificativoFornitore(doc.getDocumentoUUID());
//				cnmTDocumento.setProtocolloFornitore(doc.getNumProtocolloMaster()); //prendo questo perche' sicuramente e' un allegato, il master sarebbe gia' presente in cnmTDocumento quindi sarei entrata nella if sopra
//				cnmTDocumento.setClassificazioneId(doc.getClassificazioneId());
				cnmTDocumento.setRegistrazioneId(doc.getRegistrazioneId());
				cnmTDocumento.setObjectidclassificazione(doc.getClassificazioneId());
				cnmTDocumento.setObjectiddocumento(doc.getObjectIdDocumento());
				cnmTDocumento.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));  
				cnmTDocumento.setIdentificativoArchiviazione(doc.getParolaChiave());	
				
				// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
				boolean isDocSigned = false;
				if (doc != null && doc.getFilename() != null && doc.getFilename().toUpperCase().endsWith(".P7M"))
					isDocSigned = true;
					
				String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
				long idTipoAll = cnmDTipoAllegato.getIdTipoAllegato();
				if (idTipoAll == TipoAllegato.CONTRODEDUZIONE.getId() || idTipoAll == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
						|| idTipoAll == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId() || idTipoAll == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()
				        || idTipoAll == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || idTipoAll == TipoAllegato.COMPARSA_ALLEGATO.getId() || idTipoAll == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
				    tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
				} else if (idTipoAll == TipoAllegato.LETTERA_ORDINANZA.getId() || idTipoAll == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				        || idTipoAll == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || idTipoAll == TipoAllegato.LETTERA_SOLLECITO.getId()
						|| idTipoAll == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
				    tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;			
				}
				
				cnmTDocumento.setCnmDTipoDocumento(getTipoDocumento(tipoDoc));
			}
			CnmTDocumento cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumento); 
			response.setIdDocumento(String.valueOf(cnmTDocumentoN.getIdDocumento()));
			response.setProtocollo(cnmTDocumentoN.getProtocolloFornitore());
//			response.setIndiceClassificazione(cnmTDocumentoN.getClassificazioneId());			
			
			// se la parola chiave non e' mai stata aggiornata faccio l'aggiornamento e salvo anche sul DB
			/* 20201006_ET CONAM-100 visto che il doc e' gia stato protocollato non vado a modificare la parola chiave
			if(StringUtils.isBlank(doc.getParolaChiave())) {
				
				// recupero fruitore
				CnmTFruitore cnmTFruitore = getFruitore();
				
				//tipo fornitore
				CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
				//POJO UTENTE
				UtenteActa utenteActa = new UtenteActa();
				utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
				utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
				utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
				utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
				utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
				utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
				utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
				
				actaManagementService.aggiornaPropertiesActaPostSpostamento(doc.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())), "", utenteActa);	// 20200713_LC

				cnmTDocumentoN.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())));
				cnmTDocumentoN.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTDocumento.setCnmTUser1(cnmTUser);
				getCnmTDocumentoRepository().save(cnmTDocumentoN);
			}*/
			return response;
			
		} catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		} 
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
}
