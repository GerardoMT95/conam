/**
 * 
 */
package it.csi.conam.conambl.integration.doqui.helper.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.Context;
import it.csi.conam.conambl.integration.beans.Documento;
import it.csi.conam.conambl.integration.beans.RequestCambiaStatoRichiesta;
import it.csi.conam.conambl.integration.beans.RequestEliminaDocumento;
import it.csi.conam.conambl.integration.beans.RequestRecuperaRiferimentoDocumentoFisico;
import it.csi.conam.conambl.integration.beans.RequestRicercaAllegato;
import it.csi.conam.conambl.integration.beans.RequestSalvaDocumento;
import it.csi.conam.conambl.integration.beans.RequestSalvaDocumentoLogico;
import it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta;
import it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico;
import it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.IndexManagementPojo;
import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumentoPK;
import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTFruitore;
import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import it.csi.conam.conambl.integration.doqui.exception.CambiaStatoRichiestaException;
import it.csi.conam.conambl.integration.doqui.exception.EliminaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.RecuperaRiferimentoDocumentoFisicoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaAllegatoException;
import it.csi.conam.conambl.integration.doqui.exception.SalvaDocumentoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;

@Service
public class ManageDocumentoHelperImpl extends CommonManageDocumentoHelperImpl implements ManageDocumentoHelper {

//	private static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";
//	private static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(ManageDocumentoHelperImpl.class);

	private static final String ERROR_MESSAGE_GENERIC =          "Errore in fase di salvataggio documento. ";
	private static final String ERROR_MESSAGE_WRONG_FILE_NAME = "Il nome del file contiene caratteri non ammessi, quali lettere accentate o caratteri speciali. Si prega di verificare. ";


	@Autowired
	private DoquiServiceFacade doquiServiceFacade;

	// 20200630_LC
	@Autowired
	private UtilsDate utilsDate;
	
	// 20200702_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseSalvaDocumento salvaDocumento(RequestSalvaDocumento request) throws SalvaDocumentoException {
		String method = "salvaDocumento";
		ResponseSalvaDocumento response = new ResponseSalvaDocumento();
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder           = " + request.getFolder());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());
		}


		CnmTRichiesta cnmTRichiestaN = null;

		try{

			// validazioni 

			if(request == null) throw new SalvaDocumentoException("Request non valorizzata");
			if(request.getDocumento() == null) throw new SalvaDocumentoException("Documento non valorizzato");
			if(request.getDocumento().getFile() == null) throw new SalvaDocumentoException("File non valorizzato");
			if(request.getDocumento().getNomeFile() == null) throw new SalvaDocumentoException("Nome file non valorizzato");
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SalvaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SalvaDocumentoException("Folder non presente");


			// charset
			String charSet = System.getProperty("file.encoding");
			log.debug(method + ". charSet   = " + charSet);

// JIRA: STAGES-299
//			if(!isAsciiPrintable(request.getDocumento().getNomeFile()) ){
//				throw new SalvaDocumentoException(ERROR_MESSAGE_WRONG_FILE_NAME);
//			}


			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();	//(request.getCodiceFruitore(), SERVIZIO_INSERIMENTO_GENERICO);	// 20200609_LC

			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_INSERIMENTO_GENERICO);

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
			//docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC

			if(request.getMetadati() != null){
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

			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			// 20200609_LC
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);	


			IndexManagementPojo imp = createIndexManagement(request.getFolder(), request.getDocumento().getFile(), request.getDocumento().getNomeFile(),
					request.getMetadati(), cnmTDocumento, cnmTFruitore, cnmDTipoDocumento, request.getIndexType());

			if(log.isDebugEnabled()){
				log.debug(method + ". Index management pojo =\n " + XmlSerializer.objectToXml(imp));
				log.debug(method + ". metadati =\n " + XmlSerializer.objectToXml(request.getMetadati()));
			}

			// 20201006_LC save anticipato
			//CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);		
			// cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);	//	.insert(docRRichiestaDocumento);	// 20200609_L

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			String uid = getIndexManagementService().salvaDocumento(imp);

			log.debug(method + ". saved file " + uid);


			changeRiferimentiFornitoreDocumento(cnmTDocumento.getIdDocumento(), uid, null);
			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

			response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));	// 20200713_LC

		}
		catch (SalvaDocumentoException e) {
			containsError = true;
			log.error(method + ". SalvaDocumentoException: " + e, e);
			throw new SalvaDocumentoException(e.getMessage());
		}
		catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new SalvaDocumentoException(e.getMessage(), e);
		} catch (IntegrationException e) {
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new SalvaDocumentoException(e.getMessage(), e);
		}
		catch (Exception e) {
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SalvaDocumentoException(ERROR_MESSAGE_GENERIC, e);
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". essuna richiesta inserita: non e possibile aggiornare la richiesta");
//				}
//			}
		}
		return response;
	}

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseEliminaDocumento eliminaDocumento(RequestEliminaDocumento request) throws EliminaDocumentoException {

		String method = "eliminaDocumento";
		ResponseEliminaDocumento response = new ResponseEliminaDocumento();
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". idDocument       = " + request.getIdDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;
		Long idDocumentoArchiviazione = null;
		try{

			// validazioni 

			if(request == null) throw new SalvaDocumentoException("Request non valorizzata");
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SalvaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new SalvaDocumentoException("Id documento non presente");

			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();	//(request.getCodiceFruitore(), SERVIZIO_CANCELLAZIONE_GENERICO);	// 20200609_LC

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CANCELLAZIONE_GENERICO);

			// DOCUMENTO
//			List<CnmTDocumento> docList = getCnmTDocumentoRepository().findByIdArchiviazione(request.getIdDocumento());			
//			CnmTDocumento cnmTDocumento = (docList.size() == 0) ? null : docList.get(0);
			
			CnmTDocumento cnmTDocumento = getCnmTDocumentoRepository().findOne(Integer.parseInt(request.getIdDocumento()));	// 20200714_LC
			
			if(cnmTDocumento == null) throw new SalvaDocumentoException("Documento non presente in anagrafica");
			
			// 20200609_LC
//			CnmTDocumento docTDocumento = getCnmTDocumentoRepository().findById(Long.parseLong(request.getIdDocumento()));	
//			if(docTDocumento == null) throw new SalvaDocumentoException("Documento non presente in anagrafica");


			// 20201006_LC save anticipato
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			
			
			//RICHIESTA DOCUMENTO

			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			// 20200609_LC
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);		


			// 20200629_LC
			//IndexManagementPojo imp = new IndexManagementPojo();
			IndexManagementPojo imp = createIndexManagement(null, null, null, null, cnmTDocumento, cnmTFruitore, null, null);


			//imp.setFolder(request.getFolder());
//			imp.setFruitore(request.getCodiceFruitore());	// 20200629_LC
//			imp.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());

//			imp.setUsr(docTFruitore.getUsernameIndex());
//			imp.setPsw(docTFruitore.getPasswordIndex());
//			imp.setRepostory(docTFruitore.getRepositoryIndex());

			//imp.setDocumentRoot(docDTipoDocumentoDto.getRoot());

			//imp.setTipoDocumento(request.getTipoDocumento());
//			imp.setUtenteApplicativo(docTFruitore.getDescrFruitore());
			//imp.setFile(request.getDocumento().getFile());
			//imp.setNomeFile(request.getDocumento().getNomeFile());

			// 20200615_LC
//			imp.setIdentificativoFornitore(cnmTDocumento.getIdentificativoFornitore());


			if(log.isDebugEnabled()){
				log.debug(method + ". Index management pojo =\n " + XmlSerializer.objectToXml(imp));

			}

			// 20201006_LC save anticipato
			//cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);	//	.insert(docRRichiestaDocumento);	// 20200609_L
			
			if(log.isDebugEnabled()){

				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}


			getIndexManagementService().eliminaDocumento(imp);

			log.debug(method + ". file deleted!");

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);



		}
		catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new EliminaDocumentoException(e.getMessage());
		} catch (IntegrationException e) {
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new EliminaDocumentoException(e.getMessage());
		}
		catch (Exception e) {
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new EliminaDocumentoException(e.getMessage());
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". essuna richiesta inserita: non e' possibile aggiornare la richiesta");
//				}
//			}
		}

		return response;
	}



	/*
	 * (non-Javadoc)
	 * @see it.csi.stacore.stadoc.business.stadoc.helper.ManageDocumentoHelper#salvaDocumentoLogico(it.csi.stacore.stadoc.dto.stadoc.RequestSalvaDocumentoLogico)
	 */
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseSalvaDocumentoLogico salvaDocumentoLogico( RequestSalvaDocumentoLogico request) throws SalvaDocumentoException {
		// 20200630_LC never used
		String method = "salvaDocumento";
		ResponseSalvaDocumentoLogico response = new ResponseSalvaDocumentoLogico();
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder           = " + request.getFolder());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());
		}


		CnmTRichiesta cnmTRichiestaN = null;

		try{

			// validazioni 

			if(request == null) throw new SalvaDocumentoException("Request non valorizzata");
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SalvaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SalvaDocumentoException("Folder non presente");
			if(StringUtils.isBlank(request.getNomeFile())) throw new SalvaDocumentoException("Nome file non presente");

			// charset
			String charSet = System.getProperty("file.encoding");
			log.debug(method + ". charSet   = " + charSet);


			if(!isAsciiPrintable(request.getNomeFile()) ){
				throw new SalvaDocumentoException(ERROR_MESSAGE_WRONG_FILE_NAME);
			}


			// recupero fruitore
			CnmTFruitore docTFruitore = getFruitore();	//(request.getCodiceFruitore(), SERVIZIO_INSERIMENTO_GENERICO);	// 20200609_LC

			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(docTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_INSERIMENTO_GENERICO);

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
			//docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 

			if(request.getMetadati() != null){
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

			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			// 20200609_LC
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);		


			IndexManagementPojo imp = createIndexManagement(request.getFolder(), null, request.getNomeFile(),
					request.getMetadati(), cnmTDocumento, docTFruitore, cnmDTipoDocumento, request.getIndexType());

			if(log.isDebugEnabled()){
				log.debug(method + ". Index management pojo =\n " + XmlSerializer.objectToXml(imp));
				log.debug(method + ". metadati =\n " + XmlSerializer.objectToXml(request.getMetadati()));
			}
			
			// 20201006_LC save anticipato
			// CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);			
			// cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);	//	.insert(docRRichiestaDocumento);	// 20200609_LC

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			String uid = getIndexManagementService().salvaDocumentoLogico(imp);

			log.debug(method + ". saved file " + uid);


			changeRiferimentiFornitoreDocumento(cnmTDocumento.getIdDocumento(), uid, null);
			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_IN_ELAB);

			Context ctx = getIndexManagementService().convertToContext(imp);

			response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));	// 20200713_LC
			response.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			response.setUuid(uid);
			response.setCxt(ctx);

			if(log.isDebugEnabled()){
				log.debug(method + ". response\n " + XmlSerializer.objectToXml(response));
			}


		}
		catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new SalvaDocumentoException(e.getMessage(), e);
		} catch (IntegrationException e) {
			containsError = true;
			log.error(method + ". IntegrationException: " + e);
			throw new SalvaDocumentoException(e.getMessage(), e);
		}
		catch (Exception e) {
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SalvaDocumentoException(ERROR_MESSAGE_GENERIC, e);
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". essuna richiesta inserita: non e possibile aggiornare la richiesta");
//				}
//			}
		}

		return response;

	}



	@SuppressWarnings({ "unused", "static-access" })
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseCambiaStatoRichiesta cambiaStatoRichiesta(RequestCambiaStatoRichiesta request) throws CambiaStatoRichiestaException {
		String method = "cambiaStatoRichiesta";
		//log.debug(method + ". BEGIN");
		ResponseCambiaStatoRichiesta response = new ResponseCambiaStatoRichiesta();
		if(log.isDebugEnabled()){
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". Id Richiesta     = " + request.getIdRichiesta());
			log.debug(method + ". IdDocumento      = " + request.getIdDocumento());		
			log.debug(method + ". stato richiesta  = " + request.getStatoRichiesta());		
		}
		try{		
			Long idDocumento = null;
			// validazioni 

			if(request == null) throw new CambiaStatoRichiestaException("Request non valorizzata");
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new CambiaStatoRichiestaException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new CambiaStatoRichiestaException("IdDocumento non presente");
			if(StringUtils.isBlank(request.getStatoRichiesta())) throw new CambiaStatoRichiestaException("Stato Richiesta non valorizzata");
			if(request.getIdRichiesta() == null) throw new CambiaStatoRichiestaException("IdRichiesta non valorizzata");
			try{
				idDocumento = Long.parseLong(request.getIdDocumento());
			}
			catch(Exception e){
				throw new CambiaStatoRichiestaException("Formato id documento non valido");
			}

			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();	//(request.getCodiceFruitore(), SERVIZIO_MODIFICA_STATO_RICHIESTA);	// 20200609_LC


			// recupero RICHIESTA
			CnmTRichiesta cnmTRichiesta = getStatoRichiestaService().getRichiesta(Integer.valueOf(request.getIdRichiesta().intValue()));			// 20200610_LC


			//RICHIESTA DOCUMENTO: verifico che il documento sia associato alla richiesta
//			CnmRRichiestaDocumentoPK docRRichiestaDocumentoPk = new CnmRRichiestaDocumentoPK(request.getIdRichiesta(), idDocumento);					// 20200609_LC
//			CnmRRichiestaDocumento docRRichiestaDocumentoCfr0 = getCnmRRichiestaDocumentoRepository().findByPrimaryKey(docRRichiestaDocumentoPk);		// 20200609_LC
			CnmRRichiestaDocumento cnmRRichiestaDocumentoCfr = getCnmRRichiestaDocumentoRepository().findByIdRichiestaAndIdDocumento(request.getIdRichiesta(), idDocumento);

			if(cnmRRichiestaDocumentoCfr == null){
				throw new CambiaStatoRichiestaException("documento non associato alla richiesta");
			}



			if (request.STATO_RICHIESTA_OK.equals(request.getStatoRichiesta())){
				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);
			}
			else if (request.STATO_RICHIESTA_KO.equals(request.getStatoRichiesta())){
				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_ERRATA);
			}
			else{
				log.error(method + ". Nuovo stato " + request.getStatoRichiesta() + " non riconosciuto!");
				throw new CambiaStatoRichiestaException("Nuovo stato non definito");
			}

		}
		catch (FruitoreException e){	
			log.error(method + ". FruitoreException: " + e);
			throw new CambiaStatoRichiestaException(e.getMessage());
		}
		catch (Exception e) {
			log.error(method + ". Exception: " + e);
			throw new CambiaStatoRichiestaException(e.getMessage());
		}
		finally{
			//log.debug(method + ". END");
		}

		return response;
	}


	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseRecuperaRiferimentoDocumentoFisico recuperaRiferimentoDocumentoFisico(RequestRecuperaRiferimentoDocumentoFisico request) throws RecuperaRiferimentoDocumentoFisicoException {
		String method = "recuperaRiferimentoDocumentoFisico";
		ResponseRecuperaRiferimentoDocumentoFisico response = new ResponseRecuperaRiferimentoDocumentoFisico();
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". idDocumento      = " + request.getIdDocumento());
		}

		CnmTRichiesta cnmTRichiestaN = null;
		Integer idDocumento = null;
		try{

			// validazioni 

			if(request == null) throw new SalvaDocumentoException("Request non valorizzata");
			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new RecuperaRiferimentoDocumentoFisicoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new RecuperaRiferimentoDocumentoFisicoException("Riferimento documento non presente");

			try{

				idDocumento = Integer.parseInt(request.getIdDocumento());
			}
			catch(NumberFormatException e){
				log.error(method + ". NumberFormatException " + e);
				throw new SalvaDocumentoException("Formato documento non corretto");
			}

			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();	//(request.getCodiceFruitore(), SERVIZIO_CONSULTAZIONE_GENERICO);	// 20200609_LC

			CnmTDocumento pk = new CnmTDocumento();

			pk.setIdDocumento(idDocumento);

			CnmTDocumento cnmTDocumento = getCnmTDocumentoRepository().findOne(idDocumento);
			//CnmTDocumento docTDocumento = getCnmTDocumentoRepository().findByPrimaryKey(pk);	// 20200609_LC

			if(cnmTDocumento == null)  throw new RecuperaRiferimentoDocumentoFisicoException("Documento " + request.getIdDocumento() + " non presente");


			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_CONSULTAZIONE_GENERICO);

			// 20201006_LC save anticipato
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			
			//RICHIESTA DOCUMENTO

			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			// 20200609_LC
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);	

			// 20201006_LC save anticipato
			// cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);	//	.insert(docRRichiestaDocumento);	// 20200609_LC

			if(log.isDebugEnabled()){
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);
			}

			IndexManagementPojo imp = createIndexManagement(null, null, null, null, cnmTDocumento, cnmTFruitore, null, null);

			Context ctx = getIndexManagementService().convertToContext(imp);		

			response.setUuid(cnmTDocumento.getIdentificativoFornitore());
			response.setCxt(ctx);
			response.setIdRichiesta(cnmTRichiesta.getIdRichiesta());

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_IN_ELAB);


		}
		catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". FruitoreException: " + e);
			throw new RecuperaRiferimentoDocumentoFisicoException(e.getMessage());
		} 
		catch (Exception e) {
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new RecuperaRiferimentoDocumentoFisicoException(e.getMessage());
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					getStatoRichiestaService().changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". nessuna richiesta inserita: non e' possibile aggiornare la richiesta");
//				}
//			}
		}

		return response;
	}
		
	@SuppressWarnings("unused")
	public ResponseRicercaAllegato ricercaAllegato(RequestRicercaAllegato request) throws RicercaAllegatoException
	{
		
		String method = "ricercaAllegato";
		ResponseRicercaAllegato response = new ResponseRicercaAllegato();
		
		log.debug(method + ". BEGIN");
		
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". idDocumento      = " + request.getIdDocumento());
		}

		CnmTRichiesta cnmTRichiestaN = null;
		Integer idDocumento = null;
		try{

			// validazioni 

			if(request == null) throw new RicercaAllegatoException("Request non valorizzata");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new RicercaAllegatoException("Id documento non presente");

			try{

				idDocumento = Integer.parseInt(request.getIdDocumento());
			}
			catch(NumberFormatException e){
				log.error(method + ". NumberFormatException " + e);
				throw new RicercaAllegatoException("Formato id documento non corretto");
			}

			//recupero fruitore con una query fatta ad hoc getFruitoreByIdArchiviazione
			CnmTFruitore cnmTFruitore = getFruitore();		// ByIdArchiviazione(request.getIdDocumento());		// 20200609_LC
			
			// recupero fruitore generico dal codice fruitore passato nella request
			CnmTFruitore docTFruitoreInInput = getFruitore();	//(request.getCodiceFruitore(),  SERVIZIO_RICERCA_ALLEGATO_INDEX);	// 20200609_LC
//
			// 20200615_LC
			//CnmTDocumento pk = new CnmTDocumento();
			//pk.setIdDocumento(idDocumento);

			CnmTDocumento cnmTDocumento = getCnmTDocumentoRepository().findOne(idDocumento);
			//CnmTDocumento docTDocumento = getCnmTDocumentoRepository().findByPrimaryKey(pk);	// 20200609_LC

			if(cnmTDocumento == null)  throw new RicercaAllegatoException("Documento " + request.getIdDocumento() + " non presente");
			
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdDocumento(request.getIdDocumento());

			// DOBBIAMO INSERIRE UNA NUOVA RICHIESTA SU POSTGRES??? NELLA RICERCA DOCUMENTO SU ACTA NON VIENE INSERITA NESSUNA RICHIESTA
			// RICHIESTA
			//DocTRichiestaDto docTRichiestaDto = createDocTRichiestaDto(docTFruitoreDtoInInput.getIdFruitore(), SERVIZIO_RICERCA_ALLEGATO_INDEX);

			//RICHIESTA DOCUMENTO

//			DocRRichiestaDocumentoDto docRRichiestaDocumento = new DocRRichiestaDocumentoDto();
//			docRRichiestaDocumento.setIdDocumento(docTDocumento.getIdDocumento());
//			docRRichiestaDocumento.setIdRichiesta(docTRichiestaDto.getIdRichiesta());
//
//			docTRichiestaPk = getDocTRichiestaDao().insert(docTRichiestaDto);
//			DocRRichiestaDocumentoPk docRRichiestaDocumentoPk = getDocRRichiestaDocumentoDao().insert(docRRichiestaDocumento);

//			if(log.isDebugEnabled()){
//				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
//				log.debug(method + ". inserita richiesta documento = " + docRRichiestaDocumentoPk);
//			}

			IndexManagementPojo imp = createIndexManagement(cnmTDocumento.getFolder(), null, null, null, cnmTDocumento, cnmTFruitore, cnmDTipoDocumento, request.getIndexType());
			
			// 20200612_LC
			// Attachment attachment = getIndexManagementService().scaricaDocumento(imp);
			byte[] downloadedFile = getIndexManagementService().scaricaDocumento(imp);
			
			
			if(log.isDebugEnabled()){
				log.debug(method + ". Index management pojo =\n " + XmlSerializer.objectToXml(imp));
				// log.debug(method + ". attachment           = " + XmlSerializer.objectToXml(attachment));
				log.debug(method + ". attachment           = " + XmlSerializer.objectToXml(downloadedFile));
			}
	

			
			
			if(downloadedFile!=null){
				Documento documento = new Documento();
				documento.setFile(downloadedFile);
//				documento.setNomeFile(imp.getNomeFile());
				documento.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
				response.setDocumento(documento);
//				response.setMimeType(getIndexManagementService().getMimeType(imp.getNomeFile()));
				response.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());			
			} else {
				throw new RicercaAllegatoException("Nessun allegato con identificativoArchiviazione: " + cnmTDocumento.getIdentificativoArchiviazione() + " trovato su index");
			}
				

			//changeStatoRichiesta(docTRichiestaDto.getIdRichiesta(), STATO_RICHIESTA_IN_ELAB);


		} catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaAllegatoException(e.getMessage());
		} catch (IntegrationException e) {
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". IntegrationException: " + e);
			throw new RicercaAllegatoException(e.getMessage());
		} catch (Exception e) {
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". Exception: " + e);
			throw new RicercaAllegatoException(e.getMessage());
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". nessuna richiesta inserita: non e' possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}

		return response;
	}
	

	// 20200807_LC questo metodo non è mai invocato (e non dovrebbe stare qui)
	@SuppressWarnings("unused")
	public ResponseRicercaAllegato ricercaAllegatoActa(RequestRicercaAllegato request) throws RicercaAllegatoException
	{
		
		String method = "ricercaAllegato";
		ResponseRicercaAllegato response = new ResponseRicercaAllegato();
		
		log.debug(method + ". BEGIN");
		
		boolean containsError = false;

		if(log.isDebugEnabled()){
			log.debug(method + ". idDocumento      = " + request.getIdDocumento());
		}

		CnmTRichiesta cnmTRichiestaN = null;
		Integer idDocumento = null;
		try{

			// validazioni 

			if(request == null) throw new RicercaAllegatoException("Request non valorizzata");
			if(StringUtils.isBlank(request.getIdDocumento())) throw new RicercaAllegatoException("Id documento non presente");

			try{

				idDocumento = Integer.parseInt(request.getIdDocumento());
			}
			catch(NumberFormatException e){
				log.error(method + ". NumberFormatException " + e);
				throw new RicercaAllegatoException("Formato id documento non corretto");
			}

			//recupero fruitore con una query fatta ad hoc getFruitoreByIdArchiviazione
			CnmTFruitore cnmTFruitore = getFruitore();		// ByIdArchiviazione(request.getIdDocumento());		// 20200609_LC
			
			// recupero fruitore generico dal codice fruitore passato nella request
			CnmTFruitore docTFruitoreInInput = getFruitore();	//(request.getCodiceFruitore(),  SERVIZIO_RICERCA_ALLEGATO_INDEX);	// 20200609_LC
//
			// 20200615_LC
			//CnmTDocumento pk = new CnmTDocumento();
			//pk.setIdDocumento(idDocumento);

			CnmTDocumento cnmTDocumento = getCnmTDocumentoRepository().findOne(idDocumento);
			//CnmTDocumento docTDocumento = getCnmTDocumentoRepository().findByPrimaryKey(pk);	// 20200609_LC

			if(cnmTDocumento == null)  throw new RicercaAllegatoException("Documento " + request.getIdDocumento() + " non presente");
			
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumentoByIdDocumento(request.getIdDocumento());

			// DOBBIAMO INSERIRE UNA NUOVA RICHIESTA SU POSTGRES??? NELLA RICERCA DOCUMENTO SU ACTA NON VIENE INSERITA NESSUNA RICHIESTA
			// RICHIESTA
			//DocTRichiestaDto docTRichiestaDto = createDocTRichiestaDto(docTFruitoreDtoInInput.getIdFruitore(), SERVIZIO_RICERCA_ALLEGATO_INDEX);

			//RICHIESTA DOCUMENTO

//			DocRRichiestaDocumentoDto docRRichiestaDocumento = new DocRRichiestaDocumentoDto();
//			docRRichiestaDocumento.setIdDocumento(docTDocumento.getIdDocumento());
//			docRRichiestaDocumento.setIdRichiesta(docTRichiestaDto.getIdRichiesta());
//
//			docTRichiestaPk = getDocTRichiestaDao().insert(docTRichiestaDto);
//			DocRRichiestaDocumentoPk docRRichiestaDocumentoPk = getDocRRichiestaDocumentoDao().insert(docRRichiestaDocumento);

//			if(log.isDebugEnabled()){
//				log.debug(method + ". inserita richiesta           = " + docTRichiestaPk);
//				log.debug(method + ". inserita richiesta documento = " + docRRichiestaDocumentoPk);
//			}

//			IndexManagementPojo imp = createIndexManagement(cnmTDocumento.getFolder(), cnmTFruitore.getCodFruitore(), null, null, null, cnmTDocumento, cnmTFruitore, cnmDTipoDocumento);
			
			// 20200612_LC
			// Attachment attachment = getIndexManagementService().scaricaDocumento(imp);
//			byte[] downloadedFile = getIndexManagementService().scaricaDocumento(imp);
			
			// effettuo il download del documento
			ResponseRicercaDocumento responseRicercaDocumento = doquiServiceFacade.recuperaDocumentoActa(request.getIdDocumento());
			if (!(responseRicercaDocumento != null && responseRicercaDocumento.getDocumento() != null && responseRicercaDocumento.getDocumento().getFile() != null))
				throw new RuntimeException("documento non trovato su acta");
//			
			byte[] downloadedFile = responseRicercaDocumento.getDocumento().getFile();
			
			if(log.isDebugEnabled()){
				// log.debug(method + ". attachment           = " + XmlSerializer.objectToXml(attachment));
				log.debug(method + ". attachment           = " + XmlSerializer.objectToXml(downloadedFile));
			}
	

			
			
			if(downloadedFile!=null){
				Documento documento = new Documento();
				documento.setFile(downloadedFile);
				documento.setNomeFile(responseRicercaDocumento.getDocumento().getNomeFile());
				documento.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
				response.setDocumento(documento);
				response.setMimeType(getIndexManagementService().getMimeType(responseRicercaDocumento.getDocumento().getNomeFile()));
				response.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());			
			} else {
				throw new RicercaAllegatoException("Nessun allegato con identificativoArchiviazione: " + cnmTDocumento.getIdentificativoArchiviazione() + " trovato su index");
			}
				

			//changeStatoRichiesta(docTRichiestaDto.getIdRichiesta(), STATO_RICHIESTA_IN_ELAB);


		} catch (FruitoreException e) {
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new RicercaAllegatoException(e.getMessage());
		} catch (Exception e) {
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". Exception: " + e);
			throw new RicercaAllegatoException(e.getMessage());
		}
		finally{
//			if(containsError){
//				if(docTRichiestaPk != null)
//					changeStatoRichiesta(docTRichiestaPk.getIdRichiesta(), STATO_RICHIESTA_ERRATA);
//				else{
//					log.debug(method + ". nessuna richiesta inserita: non e' possibile aggiornare la richiesta");
//				}
//			}
			log.debug(method + ". END");
		}

		return response;
	}
		
		
}

