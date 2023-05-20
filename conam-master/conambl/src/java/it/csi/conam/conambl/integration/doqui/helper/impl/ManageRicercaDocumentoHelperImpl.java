/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import it.csi.conam.conambl.integration.beans.Documento;
import it.csi.conam.conambl.integration.beans.RequestRicercaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumentoMultiplo;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoProtocollatoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.entity.*;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.ProtocollaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoNoDocElettronicoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageRicercaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.service.ActaManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageRicercaDocumentoHelperImpl  extends CommonManageDocumentoHelperImpl implements ManageRicercaDocumentoHelper
{

//	private static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";
//	private static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(ManageRicercaDocumentoHelperImpl.class);	

	@Autowired
	private ActaManagementService	actaManagementService;
	
	
//	public ActaManagementService getActaManagementService() {
//		return actaManagementService;
//	}
//	public void setActaManagementService(ActaManagementService actaManagementService) {
//		this.actaManagementService = actaManagementService;
//	}
	
	

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseRicercaDocumentoMultiplo ricercaDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException 
	{
		String method = "ricercaDocumento";
		log.debug(method + ". BEGIN");
		ResponseRicercaDocumentoMultiplo response =  new ResponseRicercaDocumentoMultiplo();	// 20200803_LC
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
			if(request == null) throw new RicercaDocumentoException("Request non valorizzata");
	
			
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new RicercaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new RicercaDocumentoException("IDdocumento non presente");
		
			
			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();
			
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdDocumento(request.getIdDocumento());
			
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
			
			// RICHIESTA	// 20200610_LC inutile? (e senza id!)
			CnmTRichiesta cnmTRichiestaDto = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE);
			
			
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(docTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(docTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(docTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(docTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			

			if(StringUtils.isNotBlank(request.getRootActa()))
				utenteActa.setRootActa(request.getRootActa());
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			//POJO
			DocumentoActa documentoActa = new DocumentoActa();
			documentoActa.setFruitore(docTFruitore.getDescrFruitore());
		

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoActa));
			}
					
			if(log.isDebugEnabled())
			{
				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
			}
			
			// 20200717_lc
			//String parolaChiave= DoquiConstants.PREFIX_PAROLA_CHIAVE+request.getIdDocumento();	
			
			// JIRA-87
			// 20200807_LC per i doc copiati la parola chiave potrebbe non essere semplicemente uguale a CONAM_" + idActa (ma uguale a quella del documento di origine)
			// per cui per sicurezza qui la prende da identificativoArchiviazione del CnmTDocumento che ha id = idActa (idDocumento della request)
			CnmTDocumento cnmTDocumentoOrigine = getCnmTDocumentoRepository().findOne(Integer.parseInt(request.getIdDocumento()));

			String parolaChiave = null;
			if (cnmTDocumentoOrigine!=null) {
				parolaChiave = cnmTDocumentoOrigine.getIdentificativoArchiviazione();				
			}
			else {
				throw new RicercaDocumentoException("Documento non presente");
			}
			
			// 20201007 PP - controllo se la parolaChiave e' valorizzata, per i doc spostati potrebbe non esserlo e in quel caso effettuo la ricerca per ObjectId
			List<DocumentoElettronicoActa> documentoElettronicoActaList = new ArrayList<DocumentoElettronicoActa>();
			if(!StringUtils.isEmpty(parolaChiave)) {
				documentoElettronicoActaList = actaManagementService.ricercaDocumentoByParolaChiave(parolaChiave, utenteActa);
			}else {
				documentoElettronicoActaList = actaManagementService.ricercaDocumentoByObjectIdDocumento(cnmTDocumentoOrigine.getObjectiddocumento(), utenteActa);
			}
			
			response.setSottoDocumenti(new ArrayList<Documento>());
			
			// 20200803_LC gestire documento multiplo
			for (DocumentoElettronicoActa docActa:documentoElettronicoActaList) {

				Documento documento = new Documento();
				documento.setFile(docActa.getStream());
				documento.setNomeFile(docActa.getNomeFile());
				documento.setObjectIdDocumentoFisico(docActa.getIdDocFisico());	// 20200827_LC
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(documento));

				response.getSottoDocumenti().add(documento);
				//response.setDocumento(documento);				
			}
			
			return response;
			
		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		finally
		{
//			if(containsError)
//			{
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else
//				{
//					log.debug(method + ". nessuna richiesta inserita: non è possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}
	}
	
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public List<DocumentoProtocollatoVO> ricercaDocumentoProtocollato(String numProtocollo, String codiceFruitore) throws RicercaDocumentoException 
	{
		String method = "ricercaDocumentoProtocollato";
		log.debug(method + ". BEGIN");
		List<DocumentoProtocollatoVO> response = null;
		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + codiceFruitore);
			log.debug(method + ". numProtocollo  = " + numProtocollo);
		}
		CnmTRichiesta docTRichiestaPk = null;
		
		try
		{
			// validazioni 	
				
			if(StringUtils.isBlank(codiceFruitore)) throw new RicercaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(numProtocollo)) throw new RicercaDocumentoException("numProtocollo non presente");
		
			
			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();
			
			// tipo documento
//			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdArchiviazione(request.getIdDocumento());
			
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
			
			// RICHIESTA	// 20200610_LC inutile? (e senza id!)
			CnmTRichiesta cnmTRichiestaDto = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE);
			
			
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(docTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(docTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(docTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(docTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			

//			if(StringUtils.isNotBlank(request.getRootActa()))
//				utenteActa.setRootActa(request.getRootActa());
//			else
//				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
//			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			//POJO
//			DocumentoActa documentoActa = new DocumentoActa();
//			documentoActa.setFruitore(docTFruitore.getDescrFruitore());
//		
			
		

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
			}
					
//			if(log.isDebugEnabled())
//			{
//				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
//			}
			
			List<DocumentoProtocollatoActa> documentoProtocollatoActaList = actaManagementService.ricercaDocumentoProtocollato(numProtocollo, utenteActa);
			
			if(documentoProtocollatoActaList!=null && !documentoProtocollatoActaList.isEmpty()) {
				response = new ArrayList<DocumentoProtocollatoVO>();
				//20200923_ET variabili aggiunte per gestire correttamente i parametri del messaggio SAVEDOC01
				String filenameMaster=null;
				boolean filenameMasterToSet=false;
				for (DocumentoProtocollatoActa documentoProtocollatoActa : documentoProtocollatoActaList) {
					DocumentoProtocollatoVO docProtocollato = new DocumentoProtocollatoVO();
					docProtocollato.setFile(documentoProtocollatoActa.getStream());
					docProtocollato.setFilename(documentoProtocollatoActa.getNomeFile());
					docProtocollato.setObjectIdDocumento(documentoProtocollatoActa.getIdDocumento());
					docProtocollato.setClassificazione(documentoProtocollatoActa.getClassificazioneEstesa());
					docProtocollato.setClassificazioneId(documentoProtocollatoActa.getClassificazioneId());
					docProtocollato.setRegistrazioneId(documentoProtocollatoActa.getRegistrazioneId());	// 20200707_LC
					docProtocollato.setFolderId(documentoProtocollatoActa.getFolderId());	// 20200707_LC
					docProtocollato.setDataOraProtocollo(documentoProtocollatoActa.getDataProtocollo());
					
					// 20200722_LC solo se è il "master"
					if (documentoProtocollatoActa.getRegistrazioneId()!=null) {
						docProtocollato.setNumProtocollo(numProtocollo);
						//20200923_ET aggiunto per gestire correttamente i parametri del messaggio SAVEDOC01
						filenameMaster=documentoProtocollatoActa.getNomeFile();
					} else {
						docProtocollato.setNumProtocolloMaster(numProtocollo);
						//20200923_ET aggiunto per gestire correttamente i parametri del messaggio SAVEDOC01
						if(StringUtils.isNotBlank(filenameMaster)) {
							docProtocollato.setFilenameMaster(filenameMaster);
						}else {
							filenameMasterToSet=true;
						}
					}
								
					
					// 20200708_LC
					docProtocollato.setIdActa(null);	
					docProtocollato.setIdActaMaster(null);
					
					// 20200711
					docProtocollato.setParolaChiave(documentoProtocollatoActa.getParolaChiave());
					docProtocollato.setDocumentoUUID(documentoProtocollatoActa.getUiidDocumento());
					
					// 20200825_LC
					docProtocollato.setFilenamesDocMultiplo(documentoProtocollatoActa.getFilenamesDocMultiplo());
					
					response.add(docProtocollato);
				}
				//20200923_ET aggiunto per gestire correttamente i parametri del messaggio SAVEDOC01
				if(filenameMasterToSet) {
					if(StringUtils.isNotBlank(filenameMaster)) {
						log.debug(method + ". devo settare il filename del master per il protocollo  = " + numProtocollo);
						for(DocumentoProtocollatoVO doc: response) {
							if(StringUtils.isBlank(doc.getRegistrazioneId())) 
								doc.setFilenameMaster(filenameMaster);
						}
					} else {
						log.warn(method + ". non e' stato possibile recuperare il filename del master per il protocollo  = " + numProtocollo);
					}
				}
			}
			
			return response;
			
		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			if(e.getItsOriginalException()!= null && e.getItsOriginalException() instanceof RicercaDocumentoNoDocElettronicoException) {
				throw new RicercaDocumentoException(e.getMessage(), "RicercaDocumentoNoDocElettronicoException", e.getItsOriginalException().getMessage());
			}
			throw new RicercaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		finally
		{
//			if(containsError)
//			{
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else
//				{
//					log.debug(method + ". nessuna richiesta inserita: non è possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}
	}



	// 20200717_LC
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException 
	{
		String method = "ricercaDocumentoByObjectIdDocumento";
		log.debug(method + ". BEGIN");
		ResponseRicercaDocumentoMultiplo response =  new ResponseRicercaDocumentoMultiplo();	// 20200803_LC
		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". objectId  = " + request.getObjectIdDocumento());
		}
		CnmTRichiesta docTRichiestaPk = null;
		
		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");
	
			
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getObjectIdDocumento())) throw new ProtocollaDocumentoException("ObjectId non presente");
		
			
			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();
			
			// tipo documento
			//CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdArchiviazione(request.getIdDocumento());
			
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
			
			// RICHIESTA	// 20200610_LC inutile? (e senza id!)
			CnmTRichiesta cnmTRichiestaDto = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE);
			
			
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(docTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(docTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(docTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(docTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			

//			if(StringUtils.isNotBlank(request.getRootActa()))
//				utenteActa.setRootActa(request.getRootActa());
//			else
//				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
//			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			
			
			//POJO
			DocumentoActa documentoActa = new DocumentoActa();
			documentoActa.setFruitore(docTFruitore.getDescrFruitore());
		

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoActa));
			}
					
			if(log.isDebugEnabled())
			{
				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
			}
			
			List<DocumentoElettronicoActa> documentoElettronicoActaList = actaManagementService.ricercaDocumentoByObjectIdDocumento(request.getObjectIdDocumento(), utenteActa);

			response.setSottoDocumenti(new ArrayList<Documento>());
			
			// 20200803_LC gestire documento multiplo 
			for (DocumentoElettronicoActa docActa:documentoElettronicoActaList) {
				Documento documento = new Documento();
				documento.setFile(docActa.getStream());
				documento.setNomeFile(docActa.getNomeFile());
				documento.setObjectIdDocumentoFisico(docActa.getIdDocFisico());	// 20200827_LC
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(documento));
				response.getSottoDocumenti().add(documento);
				//response.setDocumento(documento);				
			}
			
			return response;
			
		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		finally
		{
//			if(containsError)
//			{
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else
//				{
//					log.debug(method + ". nessuna richiesta inserita: non è possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	
	// 20200825_LC
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumentoFisico(RequestRicercaDocumento request) throws RicercaDocumentoException 
	{
		String method = "ricercaDocumentoByObjectIdDocumentoFisico";
		log.debug(method + ". BEGIN");
		ResponseRicercaDocumentoMultiplo response =  new ResponseRicercaDocumentoMultiplo();	// 20200803_LC
		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". objectId  = " + request.getObjectIdDocumentoFisico());
		}
		CnmTRichiesta docTRichiestaPk = null;
		
		try
		{
			// validazioni 	
			if(request == null) throw new ProtocollaDocumentoException("Request non valorizzata");
	
			
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new ProtocollaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getObjectIdDocumentoFisico())) throw new ProtocollaDocumentoException("ObjectId non presente");
		
			
			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();
			
			// tipo documento
			//CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdArchiviazione(request.getIdDocumento());
			
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
			
			// RICHIESTA	// 20200610_LC inutile? (e senza id!)
			CnmTRichiesta cnmTRichiestaDto = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE);
			
			
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(docTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(docTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(docTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(docTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			

//			if(StringUtils.isNotBlank(request.getRootActa()))
//				utenteActa.setRootActa(request.getRootActa());
//			else
//				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
//			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
//			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			
			
			//POJO
			DocumentoActa documentoActa = new DocumentoActa();
			documentoActa.setFruitore(docTFruitore.getDescrFruitore());
		

			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoActa));
			}
					
			if(log.isDebugEnabled())
			{
				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
			}
			
			List<DocumentoElettronicoActa> documentoElettronicoActaList = actaManagementService.ricercaDocumentoByObjectIdDocumentoFisico(request.getObjectIdDocumentoFisico(), utenteActa);
	
			response.setSottoDocumenti(new ArrayList<Documento>());
			
			// 20200803_LC gestire documento multiplo (qui è sempre solo 1)
			for (DocumentoElettronicoActa docActa:documentoElettronicoActaList) {
				Documento documento = new Documento();
				documento.setFile(docActa.getStream());
				documento.setNomeFile(docActa.getNomeFile());
				documento.setObjectIdDocumentoFisico(docActa.getIdDocFisico());	// 20200827_LC
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(documento));
				response.getSottoDocumenti().add(documento);
				//response.setDocumento(documento);				
			}
			
			return response;
			
		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		} catch (IntegrationException e) 
		{
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new RicercaDocumentoException(e.getMessage());
		}
		finally
		{
//			if(containsError)
//			{
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else
//				{
//					log.debug(method + ". nessuna richiesta inserita: non è possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}
	}
	
	
	
	


}


