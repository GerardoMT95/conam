/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.*;
import it.csi.conam.conambl.integration.doqui.entity.*;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.ProtocollaDocumentoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.helper.ManageProtocollaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.service.ActaManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.DocumentUtils;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumMimeTypeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ManageProtocollaDocumentoHelperImpl  extends CommonManageDocumentoHelperImpl implements ManageProtocollaDocumentoHelper
{

//	private static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";
//	private static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(ManageProtocollaDocumentoHelperImpl.class);	

	@Autowired
	private ActaManagementService	actaManagementService;
	
	// 20200610_LC
	@Autowired
	private ManageDocumentoHelper manageDocumentoHelper;

	// 20200630_LC
	@Autowired
	private UtilsDate utilsDate;
	
	// 20200702_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

//	public ActaManagementService getActaManagementService() {
//		return actaManagementService;
//	}
//	public void setActaManagementService(ActaManagementService actaManagementService) {
//		this.actaManagementService = actaManagementService;
//	}


	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseProtocollaDocumento protocollaDocumentoLogico(RequestProtocollaDocumentoLogico request) throws ProtocollaDocumentoException 
	{
		String method = "protocollaDocumentoLogico";
		log.debug(method + ". BEGIN");
		ResponseProtocollaDocumento response =  new ResponseProtocollaDocumento();
		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder           = " + request.getFolder());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");
			if(request.getSoggetto() == null) throw new ProtocollaDocumentoException("Soggetto non valorizzato");
			if(request.getSoggetto().getTipologia() == null) throw new ProtocollaDocumentoException("Tipologia soggetto non valorizzato");

			if (!isCognomeNomeODenominazioneValidata(request.getSoggetto()))
				throw new ProtocollaDocumentoException("Cognome, Nome o Denominazione soggetto non valorizzato");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new ProtocollaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new ProtocollaDocumentoException("Folder non presente");

			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE);

			
			// 20200731_LC nuovo metadato collocazioneCartacea
			request.setCollocazioneCartacea(cnmDTipoDocumento.getCollocazioneCartacea());

			// DOCUMENTO

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
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}

			
			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
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

			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());

			//POJO
			DocumentoActa documentoActa = new DocumentoActa();
			documentoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoActa.setFolder(request.getFolder());
			documentoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			documentoActa.setDocumentoCartaceo(cnmDTipoDocumento.getCartaceo());
			
			// 20200731_LC (già presente)
			documentoActa.setCollocazioneCartacea(request.getCollocazioneCartacea());


			if(StringUtils.isNotBlank(request.getApplicativoAlimentante()))
				documentoActa.setApplicativoAlimentante(request.getApplicativoAlimentante());
			else
				documentoActa.setApplicativoAlimentante(cnmTFruitore.getDescrFruitore());


			//documentoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getIdStrutturaActaRoot());
			//documentoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getIdStrutturaActaFolder());
			documentoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC

			if(request.getMetadati() != null)
			{
				MetadatiActa metadatiActa = new MetadatiActa();
				metadatiActa.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				metadatiActa.setIdEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
				metadatiActa.setTarga(request.getMetadati().getTarga());
				metadatiActa.setNumeroRegistrazionePrecedente(request.getNumeroRegistrazionePrecedente());
				metadatiActa.setAnnoRegistrazionePrecedente(request.getAnnoRegistrazionePrecedente());		
				metadatiActa.setScrittore(request.getScrittore());
				metadatiActa.setDestinatarioFisico(request.getDestinatarioFisico());
				metadatiActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
				metadatiActa.setDescrizioneTipoLettera(request.getDescrizioneTipoLettera());
				documentoActa.setMetadatiActa(metadatiActa);
			}


			SoggettoActa soggettoActa = new SoggettoActa();

			if (Soggetto.TOPOLOGIA_SOGGETTO_MITTENTE.equals(request.getSoggetto().getTipologia())){
				soggettoActa.setMittente(true);
				documentoActa.setAutore(request.getSoggetto().getCognome());
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocEntrata());
			}
			else{
				soggettoActa.setMittente(false);
				documentoActa.setAutore(""); //REQUISITO CHIDERE A PAOLO
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());

			}
			soggettoActa.setCognome(request.getSoggetto().getCognome());
			soggettoActa.setNome(request.getSoggetto().getNome());
			if (request.getSoggetto().getDenominazione() != null)
				soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());

			documentoActa.setSoggettoActa(soggettoActa);

			if(request.getNumeroAllegati() != null)
				documentoActa.setNumeroAllegati(request.getNumeroAllegati());

			// originatore
			documentoActa.setOriginatore(request.getOriginatore());

			// soggetto produttore
			documentoActa.setSoggettoProduttore(request.getSoggettoProduttore());

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoActa));
			}

			// 20201006_LC save anticipato
			//	CnmTDocumento cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumento);
			//	cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			KeyDocumentoActa keyDocumentoActa = actaManagementService.protocollaDocumentoLogico(documentoActa, utenteActa);
			log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(keyDocumentoActa));

			if (keyDocumentoActa != null)
			{
				changeRiferimentiFornitoreDocumentoConKeyActa(cnmTDocumento.getIdDocumento(), keyDocumentoActa.getUUIDDocumento(), keyDocumentoActa.getNumeroProtocollo(),
						keyDocumentoActa.getClassificazioneId(), keyDocumentoActa.getRegistrazioneId(), keyDocumentoActa.getObjectIdClassificazione(), keyDocumentoActa.getObjectIdDocumento());

				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

				//response.setIdDocumento(keyDocumentoActa.getParolaChiave());
				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));	// 20200713_LC
				response.setProtocollo(keyDocumentoActa.getNumeroProtocollo());
				response.setIndiceClassificazione(keyDocumentoActa.getIndiceClassificazione());
				response.setIdFolder(keyDocumentoActa.getIdFolderCreated());	// 20201123_LC
			}
			else
				containsError = true;

			return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		finally
		{
			//			if(containsError)
			//			{
			//				if(docTRichiestaPk != null)
			//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
			//				else
			//				{
			//					log.debug(method + ". essuna richiesta inserita: non è possibile aggiornare la richiesta");
			//				}
			//			}
			log.info(method + ". END");
		}
	}

	private boolean isCognomeNomeODenominazioneValidata(Soggetto soggetto)
	{
		boolean result = false;
		if ((soggetto.getCognome() != null) && (soggetto.getNome() != null))
			result = true;
		if (soggetto.getDenominazione() != null)
			result = true;

		return result;
	}


	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseProtocollaDocumento protocollaDocumentoFisico(RequestProtocollaDocumentoFisico request/*, String rootActa, String soggettoActaFruitore, boolean isProtocollazioneInUscitaSenzaDocumento*/) throws ProtocollaDocumentoException
	{
		String method = "protocollaDocumentoFisico";
		log.debug(method + ". BEGIN");

		ResponseProtocollaDocumento response =  new ResponseProtocollaDocumento();
		boolean containsError = false;


		String rootActa = request.getRootActa();
		String soggettoActaFruitore = request.getSoggettoActa();
		boolean isProtocollazioneInUscitaSenzaDocumento = request.isProtocollazioneInUscitaSenzaDocumento();
		String parolaChiaveFolderTemp = request.getParolaChiaveFolderTemp();


		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder           = " + request.getFolder());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");
			if(request.getSoggetto() == null) throw new ProtocollaDocumentoException("Soggetto non valorizzato");
			if(request.getSoggetto().getTipologia() == null) throw new ProtocollaDocumentoException("Tipologia soggetto non valorizzato");
			if(request.getMimeType() == null) throw new ProtocollaDocumentoException("MimeType non valorizzato");

			try
			{
				EnumMimeTypeType.fromValue(request.getMimeType());

			}
			catch (IllegalArgumentException e) 
			{
				throw new ProtocollaDocumentoException("MimeType non corretto");
			}

			if (!isCognomeNomeODenominazioneValidata(request.getSoggetto()))
				throw new ProtocollaDocumentoException("Cognome, Nome o Denominazione soggetto non valorizzato");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new ProtocollaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new ProtocollaDocumentoException("Folder non presente");
			
			//20200728_ET spostato tutto il blocco perche finche non recupero il doc da index non posso sapere se e' firmato
			if(StringUtils.isBlank(request.getDocumento().getNomeFile()) || request.getDocumento().getFile() == null) {
				RequestRicercaAllegato requestRicercaAllegato = new RequestRicercaAllegato();
				requestRicercaAllegato.setIdDocumento(request.getDocumento().getIdDocumento()); 
				requestRicercaAllegato.setCodiceFruitore(request.getCodiceFruitore());
				
				String idDocumento = request.getDocumento().getIdDocumento();
				int num = request.getDocumento().getNumeroAllegati();
				String nomeFile = request.getDocumento().getNomeFile();
				request.setDocumento(manageDocumentoHelper.ricercaAllegato(requestRicercaAllegato).getDocumento());	
				request.getDocumento().setIdDocumento(idDocumento);
				request.getDocumento().setNumeroAllegati(num);
				request.getDocumento().setNomeFile(nomeFile);
				
				boolean isDocSigned = DocumentUtils.isDocumentSigned(request.getDocumento().getFile(), nomeFile);
				
				String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
				if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_1) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
				} else if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_2) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
				}
				request.setTipoDocumento(tipoDoc);
			}
			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE_FISICA);

			
			// 20200731_LC nuovo metadato collocazioneCartacea
			//request.setCollocazioneCartacea(cnmDTipoDocumento.getCollocazioneCartacea());

			//JIRA - Gestione Notifica
			request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);
			
			// DOCUMENTO

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
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}
			
			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
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

			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());

			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());

			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			//POJO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setDocumentoCartaceo(cnmDTipoDocumento.getCartaceo());
			
			// 20200731_LC
			documentoElettronicoActa.setCollocazioneCartacea(request.getCollocazioneCartacea());

			// 20210421_LC
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			if(request.getApplicativoAlimentante() != null)
				documentoElettronicoActa.setApplicativoAlimentante(request.getApplicativoAlimentante());
			else
				documentoElettronicoActa.setApplicativoAlimentante(cnmTFruitore.getDescrFruitore());

			

			documentoElettronicoActa.setStream(request.getDocumento().getFile());
			documentoElettronicoActa.setNomeFile(request.getDocumento().getNomeFile());
			documentoElettronicoActa.setFileXSL(request.getDocumento().getFileXSL());
			documentoElettronicoActa.setNomeFileXSL(request.getDocumento().getNomeFileXSL());
			documentoElettronicoActa.setDescrizione(cnmDTipoDocumento.getDescrTipoDocumento());
			documentoElettronicoActa.setMimeType(request.getMimeType());
			documentoElettronicoActa.setNumeroAllegati(request.getDocumento().getNumeroAllegati());

			//documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getIdStrutturaActaRoot());
			//documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getIdStrutturaActaFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC


			if(request.getMetadati() != null)
			{
				MetadatiActa metadatiActa = new MetadatiActa();
				metadatiActa.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				metadatiActa.setIdEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
				metadatiActa.setTarga(request.getMetadati().getTarga());
				metadatiActa.setNumeroRegistrazionePrecedente(request.getNumeroRegistrazionePrecedente());
				metadatiActa.setAnnoRegistrazionePrecedente(request.getAnnoRegistrazionePrecedente());
				metadatiActa.setScrittore(request.getScrittore());
				metadatiActa.setDestinatarioFisico(request.getDestinatarioFisico());
				metadatiActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
				metadatiActa.setDescrizioneTipoLettera(request.getDescrizioneTipoLettera());
				documentoElettronicoActa.setMetadatiActa(metadatiActa);
			}


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


			documentoElettronicoActa.setAutoreFisico(request.getAutoreFisico());
			documentoElettronicoActa.setAutoreGiuridico(request.getAutoreGiuridico());
			documentoElettronicoActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
			documentoElettronicoActa.setMittentiEsterni(request.getMittentiEsterni());
			documentoElettronicoActa.setOriginatore(request.getOriginatore());
			documentoElettronicoActa.setDestinatarioFisico(request.getDestinatarioFisico());
			

			// 20211014_LC Jira CONAM-140
	        if (request.getDataCronica() != null) documentoElettronicoActa.setDataCronica(request.getDataCronica());
	        if (request.getDataTopica() != null) documentoElettronicoActa.setDataTopica(request.getDataTopica());
			


			if(log.isDebugEnabled()){
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoElettronicoActa));
			}

			// 20201006_LC save anticipato
			//	CnmTDocumento docTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			//	cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			KeyDocumentoActa keyDocumentoActa = actaManagementService.protocollaDocumentoFisico(documentoElettronicoActa, utenteActa, isProtocollazioneInUscitaSenzaDocumento, parolaChiaveFolderTemp);


			if(log.isDebugEnabled()){
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(keyDocumentoActa));
			}

			if (keyDocumentoActa != null)
			{
				changeRiferimentiFornitoreDocumentoConKeyActa(cnmTDocumento.getIdDocumento(), keyDocumentoActa.getUUIDDocumento(), keyDocumentoActa.getNumeroProtocollo(),
						keyDocumentoActa.getClassificazioneId(), keyDocumentoActa.getRegistrazioneId(),  keyDocumentoActa.getObjectIdClassificazione(), keyDocumentoActa.getObjectIdDocumento());

				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

				//response.setIdDocumento(keyDocumentoActa.getParolaChiave());	
				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));	// 20200713_LC
				response.setProtocollo(keyDocumentoActa.getNumeroProtocollo());
				response.setObjectIdDocumento(String.valueOf(cnmTDocumento.getObjectiddocumento()));	// 20201123_LC
				response.setIdFolder(keyDocumentoActa.getIdFolderCreated());	// 20201123_LC
			}
			else
				containsError = true;

			return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		finally
		{
			//if(containsError)
			//			{
			//				if(docTRichiestaPk != null)
			//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
			//				else
			//				{
			//					log.debug(method + ". essuna richiesta inserita: non è possibile aggiornare la richiesta");
			//				}
			//			}
			log.info(method + ". END");
		}
	}

	

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseProtocollaDocumento protocollaDocumentoFisicoEsistente(RequestProtocollaDocumentoFisico request, Integer idDocumentoEsistente/*, String rootActa, String soggettoActaFruitore, boolean isProtocollazioneInUscitaSenzaDocumento*/) throws ProtocollaDocumentoException
	{
		String method = "protocollaDocumentoFisico";
		log.debug(method + ". BEGIN");

		ResponseProtocollaDocumento response =  new ResponseProtocollaDocumento();
		boolean containsError = false;


		String rootActa = request.getRootActa();
		String soggettoActaFruitore = request.getSoggettoActa();
		boolean isProtocollazioneInUscitaSenzaDocumento = request.isProtocollazioneInUscitaSenzaDocumento();


		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder           = " + request.getFolder());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");
			if(request.getSoggetto() == null) throw new ProtocollaDocumentoException("Soggetto non valorizzato");
			if(request.getSoggetto().getTipologia() == null) throw new ProtocollaDocumentoException("Tipologia soggetto non valorizzato");
			if(request.getMimeType() == null) throw new ProtocollaDocumentoException("MimeType non valorizzato");

			try
			{
				EnumMimeTypeType.fromValue(request.getMimeType());

			}
			catch (IllegalArgumentException e) 
			{
				throw new ProtocollaDocumentoException("MimeType non corretto");
			}

			if (!isCognomeNomeODenominazioneValidata(request.getSoggetto()))
				throw new ProtocollaDocumentoException("Cognome, Nome o Denominazione soggetto non valorizzato");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
//			if(StringUtils.isBlank(request.getTipoDocumento())) throw new ProtocollaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new ProtocollaDocumentoException("Folder non presente");
			
			//20200728_ET spostato tutto il blocco perche finche non recupero il doc da index non posso sapere se e' firmato
//			if(StringUtils.isBlank(request.getDocumento().getNomeFile()) || request.getDocumento().getFile() == null) {
//				RequestRicercaAllegato requestRicercaAllegato = new RequestRicercaAllegato();
//				requestRicercaAllegato.setIdDocumento(request.getDocumento().getIdDocumento()); 
//				requestRicercaAllegato.setCodiceFruitore(request.getCodiceFruitore());
//				
//				String idDocumento = request.getDocumento().getIdDocumento();
//				int num = request.getDocumento().getNumeroAllegati();
//				String nomeFile = request.getDocumento().getNomeFile();
//				request.setDocumento(manageDocumentoHelper.ricercaAllegato(requestRicercaAllegato).getDocumento());	
//				request.getDocumento().setIdDocumento(idDocumento);
//				request.getDocumento().setNumeroAllegati(num);
//				request.getDocumento().setNomeFile(nomeFile);
//				
//				boolean isDocSigned = DocumentUtils.isDocumentSigned(request.getDocumento().getFile(), nomeFile);
//				
//				String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
//				if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_1) {
//					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
//				} else if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_2) {
//					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
//				}
//				request.setTipoDocumento(tipoDoc);
//			}
			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
//			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE_FISICA);

			CnmTDocumento cnmTDocumento = getCnmTDocumentoRepository().findOne(idDocumentoEsistente);//new CnmTDocumento();
			
			CnmDTipoDocumento cnmDTipoDocumento =  cnmTDocumento.getCnmDTipoDocumento();
//			
			
			// 20200731_LC nuovo metadato collocazioneCartacea
			request.setCollocazioneCartacea(cnmDTipoDocumento.getCollocazioneCartacea());

			//JIRA - Gestione Notifica
			request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);
			
			// DOCUMENTO

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}
			
//			docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
//			cnmTDocumento.setFolder(request.getFolder());
//			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
//			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
//			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 
//
//			if(request.getMetadati() != null)
//			{
////				cnmTDocumento.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
//				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
////				cnmTDocumento.setTarga(request.getMetadati().getTarga());
//			}
//			
//			// 20201006_LC save anticipato
//			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
//			cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
//			CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
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

			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());

			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());

			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			//POJO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setDocumentoCartaceo(cnmDTipoDocumento.getCartaceo());
			
			// 20200731_LC
			documentoElettronicoActa.setCollocazioneCartacea(request.getCollocazioneCartacea());


			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			if(request.getApplicativoAlimentante() != null)
				documentoElettronicoActa.setApplicativoAlimentante(request.getApplicativoAlimentante());
			else
				documentoElettronicoActa.setApplicativoAlimentante(cnmTFruitore.getDescrFruitore());

			

//			documentoElettronicoActa.setStream(request.getDocumento().getFile());
//			documentoElettronicoActa.setNomeFile(request.getDocumento().getNomeFile());
//			documentoElettronicoActa.setFileXSL(request.getDocumento().getFileXSL());
//			documentoElettronicoActa.setNomeFileXSL(request.getDocumento().getNomeFileXSL());
			documentoElettronicoActa.setDescrizione(cnmDTipoDocumento.getDescrTipoDocumento());
//			documentoElettronicoActa.setMimeType(request.getMimeType());
//			documentoElettronicoActa.setNumeroAllegati(request.getDocumento().getNumeroAllegati());

//			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getIdStrutturaActaRoot());
//			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getIdStrutturaActaFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC


			if(request.getMetadati() != null)
			{
				MetadatiActa metadatiActa = new MetadatiActa();
				metadatiActa.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				metadatiActa.setIdEntitaFruitore(request.getMetadati().getIdEntitaFruitore());
				metadatiActa.setTarga(request.getMetadati().getTarga());
				metadatiActa.setNumeroRegistrazionePrecedente(request.getNumeroRegistrazionePrecedente());
				metadatiActa.setAnnoRegistrazionePrecedente(request.getAnnoRegistrazionePrecedente());
				metadatiActa.setScrittore(request.getScrittore());
				metadatiActa.setDestinatarioFisico(request.getDestinatarioFisico());
				metadatiActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
				metadatiActa.setDescrizioneTipoLettera(request.getDescrizioneTipoLettera());
				documentoElettronicoActa.setMetadatiActa(metadatiActa);
			}


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


			documentoElettronicoActa.setAutoreFisico(request.getAutoreFisico());
			documentoElettronicoActa.setAutoreGiuridico(request.getAutoreGiuridico());
			documentoElettronicoActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
			documentoElettronicoActa.setMittentiEsterni(request.getMittentiEsterni());
			documentoElettronicoActa.setOriginatore(request.getOriginatore());
			documentoElettronicoActa.setDestinatarioFisico(request.getDestinatarioFisico());
			


			if(log.isDebugEnabled()){
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoElettronicoActa));
			}

			// 20201006_LC save anticipato
			//	CnmTDocumento docTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			//	cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumento);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			KeyDocumentoActa keyDocumentoActa = actaManagementService.protocollaDocumentoFisicoEsistente(documentoElettronicoActa, utenteActa, isProtocollazioneInUscitaSenzaDocumento, cnmTDocumento.getObjectidclassificazione(), cnmTDocumento.getObjectiddocumento());


			if(log.isDebugEnabled()){
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(keyDocumentoActa));
			}

			if (keyDocumentoActa != null)
			{
				changeRiferimentiFornitoreDocumentoConKeyActa(cnmTDocumento.getIdDocumento(), keyDocumentoActa.getUUIDDocumento(), keyDocumentoActa.getNumeroProtocollo(),
						keyDocumentoActa.getClassificazioneId(), keyDocumentoActa.getRegistrazioneId(),  keyDocumentoActa.getObjectIdClassificazione(), keyDocumentoActa.getObjectIdDocumento());

				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

				//response.setIdDocumento(keyDocumentoActa.getParolaChiave());	
				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));	// 20200713_LC
				response.setProtocollo(keyDocumentoActa.getNumeroProtocollo());
				response.setObjectIdDocumento(String.valueOf(cnmTDocumento.getObjectiddocumento()));	// 20201123_LC
				response.setIdFolder(keyDocumentoActa.getIdFolderCreated());	// 20201123_LC
			}
			else
				containsError = true;

			return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		finally
		{
			//if(containsError)
			//			{
			//				if(docTRichiestaPk != null)
			//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
			//				else
			//				{
			//					log.debug(method + ". essuna richiesta inserita: non è possibile aggiornare la richiesta");
			//				}
			//			}
			log.info(method + ". END");
		}
	}

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseAssociaDocumentoFisico associaDocumentoFisico(RequestAssociaDocumentoFisico request) throws ProtocollaDocumentoException
	{
		String method = "associaDocumentoFisico";
		log.debug(method + ". BEGIN");
		ResponseAssociaDocumentoFisico response =  new ResponseAssociaDocumentoFisico();
		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". idDocumento  = " + request.getIdDocumento());
		}
		CnmTRichiesta docTRichiestaPk = null;
		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new ProtocollaDocumentoException("IDdocumento non presente");
			if(StringUtils.isBlank(request.getDocumento().getNomeFile())) throw new ProtocollaDocumentoException("Nome File non presente");
			if(request.getDocumento().getFile() == null) throw new ProtocollaDocumentoException("File non presente");


			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();

			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdDocumento(request.getIdDocumento());

			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA	// 20200610_LC inutile? (e senza id!)
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_ARCHIVIAZIONE);

			cnmDTipoDocumento.getDescrTipoDocumento();
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			//utenteActa.setIdStatoDiEfficacia(new Integer(8)); //TODO da mettere sul data base
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			//POJO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			if(request.getApplicativoAlimentante() != null)
				documentoElettronicoActa.setApplicativoAlimentante(request.getApplicativoAlimentante());
			else
				documentoElettronicoActa.setApplicativoAlimentante(cnmTFruitore.getDescrFruitore());

			if(request.getAutoreGiuridico() != null)
				documentoElettronicoActa.setAutoreGiuridico(request.getAutoreGiuridico());
			//			if(request.getAutoreFisico() != null)
			//				documentoElettronicoActa.setAutoreFisico(request.getAutoreFisico());
			//			if(request.getScrittore() != null)
			//				documentoElettronicoActa.setScrittore(request.getScrittore());
			//			if(request.getOriginatore() != null)
			//				documentoElettronicoActa.setOriginatore(request.getOriginatore());
			//			if(request.getSoggettoProduttore() != null)
			//				documentoElettronicoActa.setSoggettoProduttore(request.getSoggettoProduttore());
			//			if(request.getDataTopica() != null)
			//				documentoElettronicoActa.setDataTopica(request.getDataTopica());

			documentoElettronicoActa.setIdDocumento(request.getIdDocumento());

			CnmTDocumento cnmTDocumento = getRiferimentiFornitoreDocumento(Integer.parseInt(documentoElettronicoActa.getIdDocumento()));
			documentoElettronicoActa.setClassificazioneId(cnmTDocumento.getClassificazioneId());
			documentoElettronicoActa.setRegistrazioneId(cnmTDocumento.getRegistrazioneId());
			documentoElettronicoActa.setDescrizione(cnmDTipoDocumento.getDescrTipoDocumento());
			documentoElettronicoActa.setNomeFile(request.getDocumento().getNomeFile());
			documentoElettronicoActa.setStream(request.getDocumento().getFile());
			documentoElettronicoActa.setDocumentoCartaceo(cnmDTipoDocumento.getCartaceo());


			actaManagementService.associaDocumentoFisico(documentoElettronicoActa, utenteActa);

			log.debug(method + ". archiviaDocumentoLogico");

			return response;

		}
		catch (FruitoreException e) {
			log.error(method + ". FruitoreException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		} catch (IntegrationException e) {
			log.error(method + ". IntegrationException: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		catch (Exception e) {
			log.error(method + ". Exception: " + e);
			throw new ProtocollaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}

	/*
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	

	public ResponseProtocollaDocumento protocollaDocumentoFisicoConam(RequestProtocollaDocumentoFisicoConam request) throws ProtocollaDocumentoException {

		//questo mdd fa schifo quindi invece che fare il downcast faccio il remapping  a mano 
		RequestProtocollaDocumentoFisico requestProtocollaDocumentoFisico=new RequestProtocollaDocumentoFisico();
		requestProtocollaDocumentoFisico.setAnnoRegistrazionePrecedente(request.getAnnoRegistrazionePrecedente());
		requestProtocollaDocumentoFisico.setCodiceFruitore(request.getCodiceFruitore());
		requestProtocollaDocumentoFisico.setDescrizioneTipoLettera(request.getDescrizioneTipoLettera());
		requestProtocollaDocumentoFisico.setDestinatarioFisico(request.getDestinatarioFisico());
		requestProtocollaDocumentoFisico.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
		requestProtocollaDocumentoFisico.setDocumento(request.getDocumento());
		requestProtocollaDocumentoFisico.setFolder(request.getFolder());
		requestProtocollaDocumentoFisico.setMetadati(request.getMetadati());
		requestProtocollaDocumentoFisico.setMimeType(request.getMimeType());
		requestProtocollaDocumentoFisico.setNumeroRegistrazionePrecedente(request.getNumeroRegistrazionePrecedente());
		requestProtocollaDocumentoFisico.setScrittore(request.getScrittore());
		requestProtocollaDocumentoFisico.setSoggetto(request.getSoggetto());
		requestProtocollaDocumentoFisico.setTipoDocumento(request.getTipoDocumento());
		requestProtocollaDocumentoFisico.setAutoreFisico(request.getAutoreFisico());



		return protocollaDocumentoFisico(requestProtocollaDocumentoFisico,request.getRootActa(),request.getSoggettoActa(),true);
	}
	 */
}
