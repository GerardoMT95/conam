/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;


import it.csi.conam.conambl.integration.beans.Context;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.IndexManagementPojo;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.service.EcmEngineWebService;
import it.csi.conam.conambl.integration.doqui.service.IndexManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Mimetype;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Property;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.SearchParams;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.EnvelopedContent;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.VerifyReport;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
public class IndexManagementServiceImpl extends CommonManagementServiceImpl implements IndexManagementService {

//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".integration";
//	private static Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(IndexManagementServiceImpl.class);
	

	// 20201006_LC INDEX WEB SERVICE
	
	@Autowired
	private EcmEngineWebService  ecmEngineWebService;
	
	
	public EcmEngineWebService getEcmEngineWebService() {
		return ecmEngineWebService;
	}

	public void setEcmEngineWebService(EcmEngineWebService ecmEngineWebService) {
		this.ecmEngineWebService = ecmEngineWebService;
	}

	@PostConstruct
	public void init() {
		getEcmEngineWebService().init();
	}

	public String searchFolder(SearchParams params, String folderName, String uidParent, IndexManagementPojo pojo) throws IntegrationException{
		String method = "searchFolder";
		String uidFolder = null;	// 20200612_LC
		log.debug(method + ". BEGIN");
		params.setLuceneQuery("PATH:\""+params.getXPathQuery()+"\"");
		if(log.isDebugEnabled()){
			log.debug(method + ". uidParent          = "  + uidParent);
			log.debug(method + ". folderName         = "  + folderName);
			log.debug(method + ". params  limit      = "  + params.getLimit());
			log.debug(method + ". params  xPathQuery = "  + params.getXPathQuery());
			log.debug(method + ". params  luceneQuery = "  + params.getLuceneQuery());
		}
		String uid = null;
		try{
			
			OperationContext ctx = convertToOperationContext(pojo);
			
			// NodeResponse response = getSearchService().luceneSearchNoMetadata(params, ctx);
			uidFolder = ecmEngineWebService.luceneSearchNoMetadata(params, ctx);	// 20200612_LC
			
			
			if(StringUtils.isBlank(uidFolder)){
				if(uidParent!=null){
					uid = createFolder(folderName, uidParent, pojo); 
					log.info(method + ". Folder non trovata, creata con uid "+uid);
				}
			}else{
				uid = uidFolder;
				log.debug(method + ". Folder exists uid "+uid);
			}
			
			
		} catch (Exception e) {
				log.error(method + ". " + e.getMessage());
				log.error(method + ". Exception: " + e);
				throw new IntegrationException("Exception ", e);	
		}
		finally {
			log.debug(method + ". END");
		}
		

		return uid;
	}

	// 20200612_LC
	// public SearchResponse luceneSearch(SearchParams params, OperationContext oc)
	public String luceneSearch(SearchParams params, OperationContext oc)
			throws IntegrationException {
		String method = "luceneSearch";
		log.debug(method + ". BEGIN");
		String uid = null;
		try{
			uid = ecmEngineWebService.luceneSearch(params, oc);
		}
		catch (Exception e){
			log.error(method + ". e.getMessage() = " + e.getMessage());
			throw new IntegrationException("Exception ", e);
		}
		finally	{
			log.debug(method + ". END");
		}	
		return uid;
	}

	public String getFolder(IndexManagementPojo pojo) throws IntegrationException {
		String method = "getFolder";
		log.debug(method + ". BEGIN");

		// 20200703_LC nuova struttura Index a 3 livelli (root - folderTipoDoc - fodlerAnno)
		// nella TDocumento ci sarà come folder il tipo di documento ma senza anno alla fine

		String uidPrimoLivello = null;	
		String uidSecondoLivello = null;
		String uidTerzoLivello = null;


		String folderNamePrimoLivello = pojo.getDocumentRoot();
		String folderNameSecondoLivello = pojo.getFolder();
		String folderNameTerzoLivello = String.valueOf(LocalDate.now().getYear());

		try{

			SearchParams params = new SearchParams();
			params.setLimit(1);

			StringBuilder query = new StringBuilder();

			
			
			//1 livello 
			query.append(DoquiConstants.INDEX_ROOT);
			query.append(DoquiConstants.INDEX_SUFFIX);
			query.append(folderNamePrimoLivello);					// root (conam)

			params.setXPathQuery(query.toString());
			log.debug(method + ". x path first level= " + query.toString());

			uidPrimoLivello = searchFolder(params, folderNamePrimoLivello, null, pojo); //ricerca la root se non lo trova la crea
			log.debug(method + ". uidRoot " + uidPrimoLivello);

			
			
			
			
			//2 livello 
			query.append(DoquiConstants.INDEX_SUFFIX);
			query.append(folderNameSecondoLivello);				// tipo documento 

			params.setXPathQuery(query.toString());
			log.debug(method + ". x path second level " + query.toString());

			uidSecondoLivello = searchFolder(params, folderNameSecondoLivello, uidPrimoLivello, pojo); //ricerca la root se non lo trova la crea
			log.debug(method + ". uidFolder " + uidSecondoLivello);

			
			
			

		
			
			//3 livello 
			query.append(DoquiConstants.INDEX_SUFFIX);
			query.append(folderNameTerzoLivello);				// anno

			params.setXPathQuery(query.toString());
			log.debug(method + ". x path second level " + query.toString());

			uidTerzoLivello = searchFolder(params, folderNameTerzoLivello, uidSecondoLivello, pojo); //ricerca la root se non lo trova la crea
			log.debug(method + ". uidFolder " + uidTerzoLivello);
			


			
			
		}catch (Exception e){
			log.error(method + ". Exception" + e.getMessage());
			throw new IntegrationException("Exception ", e);
		}
		finally	{
			log.debug(method + ". END");
		}		
		
		// 20200701_LC
		return uidTerzoLivello;
	}


	// 20201026 PP - controlla se le firme sul doc sono valide
	public void controllaFirmaDocumento (IndexManagementPojo pojo) throws IntegrationException {

		OperationContext ctx = convertToOperationContext(pojo);
		EnvelopedContent envelopedContent = convertToEnvelopedContent(pojo);
		VerifyReport verifyReport = ecmEngineWebService.verifyDocument(envelopedContent, ctx);
		if (verifyReport !=null) {
			
		}
		// esaminare il verifyReport, in caso di firme non valide rilanciare IntegrationException
		
		
	}
	
	// 20200612_LC
	public String salvaDocumento (IndexManagementPojo pojo) throws IntegrationException {
		String method = "salvaDocumento";
		String uid = null;

		if(log.isDebugEnabled()){
			log.debug(method + ". saving file= " + pojo.getNomeFile());
		}

		try{

			

			log.debug(method + ". getting folder...");
			String s = getFolder(pojo);
			log.debug(method + ". got " + s + " folder");

			// Node n = new Node(s); //cerca la directory via ioc indicata dal fruitore sul cm del fruitore, se non � presente la crea

			// devo ancora recuperare il myme type
			String mimeType = getMimeType(pojo.getNomeFile());
			pojo.setMimeType(mimeType);
			
			log.debug(method + ". mimeType " + mimeType);

			log.debug(method + ". WS");		// PA/PD
			
			// INDEX convertToOperationContext
			OperationContext ctx = convertToOperationContext(pojo);
			Content c = convertToContent(pojo);

			if(log.isDebugEnabled()){
				log.debug(method + ". IndexManagementPojo\n " + XmlSerializer.objectToXml(pojo));
				log.debug(method + ". OperationContext\n " + XmlSerializer.objectToXml(ctx));
				log.debug(method + ". Content\n " + XmlSerializer.objectToXml(c));
			}

			
			// 20200612_LC create content
//			Node nodoFile = getManagementService().createContent(n, c, ctx); 
//			uid = nodoFile.getUid(); 
			uid = createContent(s, c, ctx);
			

			log.debug(method + ". uid= " + uid);

		}
		catch (Exception e) {
			log.error(method + ". Exception: " + e);
			log.error(method + ". " + e.getMessage());
			throw new IntegrationException(e.getMessage(), e);
		}
		return uid;
	}
	

	
	// 20200612_LC
	public void eliminaDocumento(IndexManagementPojo pojo)
			throws IntegrationException {

		String method = "eliminaDocumento";
		//String uid = null;

		if(log.isDebugEnabled()){
			log.debug(method + ". id documento da eliminare = " + pojo.getIdDocumento());
		}

		// OperationContext oc = null;

		try{
			log.debug(method + ". elimina documento");

			// 20200616_LC luceneSearch not needed
			
			//StringBuilder luceneQuery = new StringBuilder();

			//luceneQuery.append("@stadoc\\:identificativoDocumentoArchiviato:\""); 
			//luceneQuery.append(pojo.getIdDocumento());
			//luceneQuery.append("\"");

			//SearchParams params = new SearchParams();
			//params.setLimit(0);
			//params.setLuceneQuery(luceneQuery.toString());


			OperationContext oc = convertToOperationContext(pojo);

			// 20200612_LC
			// SearchResponse response = luceneSearch(params, oc);
			
			//uid = luceneSearch(params, oc);


			//if(uid!=null){
			//	 ecmEngineWebService.deleteContent(new Node(uid), oc);
			//}
			
			
			// 20200615_LC
			if(pojo.getIdentificativoFornitore()!=null){								
				ecmEngineWebService.deleteContent(new Node(pojo.getIdentificativoFornitore()), oc);		
			}	

			log.debug(method + ". documento eliminato ");
		}
		catch (Exception e) {
			log.error(method + ". Exception: " + e);
			throw new IntegrationException(e.getMessage(), e);
		}

	}	


	

	// 20200612_LC
	public byte[] scaricaDocumento(IndexManagementPojo pojo)
			throws IntegrationException {

		String method = "scaricaDocumento";
		//String uid = null;
		//Attachment attach = null;
		byte[] dwFile = null;

		if(log.isDebugEnabled()){
			log.debug(method + ". id documento da scaricare = " + pojo.getIdDocumento());
		}

		try{
			log.debug(method + ". scarica documento");

			// 20200616_LC luceneSearch not needed
			
			//StringBuilder luceneQuery = new StringBuilder();

			// 20200615_LC
//			luceneQuery.append("@stadoc\\:identificativoDocumentoArchiviato:\""); //TODO STADOC da parametrizzare
//			luceneQuery.append(pojo.getIdDocumento());
//			luceneQuery.append("\"");
			//luceneQuery.append(DoquiConstants.ROOT);
			//luceneQuery.append(DoquiConstants.SUFFIX);
			//luceneQuery.append(pojo.getDocumentRoot());	
			//luceneQuery.append(DoquiConstants.SUFFIX);
			//luceneQuery.append(pojo.getFolder());	
			//luceneQuery.append("/");
			//luceneQuery.append(pojo.getIdDocumento());
			
			//SearchParams params = new SearchParams();
			//params.setLimit(0);
			//params.setLuceneQuery(luceneQuery.toString());
			//params.setXPathQuery(luceneQuery.toString());	// 20200615_LC
			
			OperationContext oc = convertToOperationContext(pojo);
			Content c = convertToContent(pojo);		// 20200612_LC
			// c.setContentPropertyPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_NAME);	// 20200615_LC
			  
			// 20200612_LC
			// SearchResponse response = luceneSearch(params, oc);
			
			//uid = luceneSearch(params, oc);
			
			
			//if(uid!=null){
			//	 DWed = ecmEngineWebService.retrieveContentData(new Node(uid), c, oc);
			//}	
			
			// 20200615_LC
			if(pojo.getIdentificativoFornitore()!=null){								
				dwFile = ecmEngineWebService.retrieveContentData(new Node(pojo.getIdentificativoFornitore()), c, oc);			
			}		
			


			log.debug(method + ". documento scaricato ");
		}
		catch (Exception e) {
			log.warn(method + ". Exception: ", e);
			throw new IntegrationException(e.getMessage(), e);
		}

		return dwFile;
	}
	

	/* util */
	

	// 20201026 PP
	public EnvelopedContent convertToEnvelopedContent(IndexManagementPojo v) throws IntegrationException{

		final String method = "convertToEnvelopedContent";
		EnvelopedContent envelopedContent = null;
		try{
			envelopedContent = new EnvelopedContent();
			envelopedContent.setData(v.getFile());
			envelopedContent.setStore(false);
		}
		catch(Exception e) {
			log.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage());
		}
		return envelopedContent;
		
	}


	// 20200612_LC
	private String createContent(String uidFolder, Content content, OperationContext operationContext) throws IntegrationException{

		final String method = "createContent";
		String uid = null;
		try{
			Node node = ecmEngineWebService.createContent(new Node(uidFolder), content, operationContext);
			if(node != null)
				uid = node.getUid();
		}
		catch(Exception e) {
			log.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage());
		}
		return uid;
		
	}

	// 20200612_LC
	public String getMimeType(String fileName) throws IntegrationException {
		String method = "getMimeType";
		log.debug(method + ". BEGIN");

		String estensione = StringUtils.substringAfterLast(fileName, ".");
		Mimetype mt = new Mimetype();
		mt.setFileExtension(estensione);
//		Mimetype[] mime = null;
		try{
			String sMimeType = ecmEngineWebService.getMimeType(mt);
			return sMimeType;
//			if(mime!=null && mime.length>0){
//				return mime[0].getMimetype();
//			}
//			else{
//				return IndexConstants.MIMETYPE_DEFAULT;
//			}

		}
		catch (Exception e){
			log.error(method + ". e.getMessage() = " + e.getMessage());
			throw new IntegrationException("Exception ", e);
		}		
		finally	{
			log.debug(method + ". END");
		}
	}

	// 20200612_LC
	public String createFolder(String folderName, String uidParent,IndexManagementPojo pojo) throws IntegrationException {

		String method = "createFolder";
		log.debug(method + ". BEGIN");

		//log.info(method + ". INT " + managementService);
		log.debug(method + ". uidParent " + uidParent);
		log.debug(method + ". folderName " + folderName);

		String uidFolder = null;
		try {

			log.debug(method + ". calling management service...");

			// 20200612_LC
			//uidFolder = getManagementService().createContent(new Node(uidParent), getContentFolder(folderName), operationContextAdapterImpl.convertFrom(pojo)).getUid();
			uidFolder = createContent(uidParent, getContentFolder(folderName), convertToOperationContext(pojo));

			log.debug(method + ". created uidFolder " + uidFolder);

		} catch (Exception e){
			log.error(method + ". e.getMessage() = " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		finally {
			
			log.debug(method + ". END");
		}

		
		return uidFolder;
	}

	// 20200612_LC
	private Content getContentFolder(String folderName){
		String method = "getContentFolder";
		log.debug(method + ". BEGIN");
		log.debug(method + ". folderName -> " + folderName);

		Content myFolder = new Content();
		myFolder.setPrefixedName(DoquiConstants.INDEX_DEFAULT_PREFIX+folderName);
		myFolder.setParentAssocTypePrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_CONTAINS);
		myFolder.setModelPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_MODEL);
		myFolder.setTypePrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_FOLDER);

		Property p = new Property();
		p.setPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_NAME_SHORT);
		p.setDataType("text");
		p.setValues(new String [] {folderName });
		myFolder.setProperties(new Property[]{p});

		log.debug(method + ". END");
		return myFolder;
	}

	

	
	
	// 20200612_LC 
	public String salvaDocumentoLogico(IndexManagementPojo pojo) throws IntegrationException {
		String method = "salvaDocumentoLogico";
		if(log.isDebugEnabled()){
			log.debug(method + ". creazione file vuoto....");
		}
		pojo.setFile(new byte[0]);
		return salvaDocumento(pojo);
		
	}


	
	
	
	// ---------------
	
	
	
	// 20200615_LC
	public Content convertToContent(IndexManagementPojo v) throws IntegrationException {
			
			// 20200703_LC - 20200716_LC NUOVA CONFIGURAZIONE INDEX
		
			Content c = new Content();
			
			c.setParentAssocTypePrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_CONTAINS);			
					
			c.setEncoding(DoquiConstants.INDEX_ENCODING);
			c.setContent(v.getFile());
			c.setMimeType(v.getMimeType());

			// --
			
			// nome del custom model (da DB)
			c.setModelPrefixedName(v.getCustomModel());

			// anche con custom model e più types figli di un type generico ci va cm:content
			c.setContentPropertyPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_NAME);	
			
			// prefisso conam: invece di cm:		
			c.setPrefixedName(DoquiConstants.PREFIX_CONAM_INDEX_MODEL+ v.getIdDocumento() + "_" + v.getNomeFile());
			
			// tipo specifico dell allegato (da DB)
			c.setTypePrefixedName(DoquiConstants.PREFIX_CONAM_INDEX_MODEL+v.getIndexType()); 
			
			// --
				
			// METADATI CORRETTI PER IL NUOVO MODELLO INDEX				
			Property[] result = new Property[3];
			
			result[0] = new Property();
			result[0].setDataType(DoquiConstants.INDEX_TYPE_TEXT);
			result[0].setPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_NAME_SHORT); 
			result[0].setValues(new String[]{v.getIdDocumento() + "_" + v.getNomeFile()});
			
			result[1] = new Property();
			result[1].setDataType(DoquiConstants.INDEX_TYPE_TEXT);
			result[1].setPrefixedName(DoquiConstants.PREFIX_CONAM_INDEX_MODEL+"identificativoDocumentoArchiviato"); 
			result[1].setValues(new String[]{v.getIdDocumento()});
						
			result[2] = new Property();
			result[2].setDataType(DoquiConstants.INDEX_TYPE_TEXT);
			result[2].setPrefixedName(DoquiConstants.PREFIX_CONAM_INDEX_MODEL+"tipoDocumentoArchiviato"); 
			result[2].setValues(new String[]{v.getIndexType()});
			
			
			c.setProperties(result); 
			
			return c;
		}
	
	
	public OperationContext convertToOperationContext(IndexManagementPojo v) throws IntegrationException {
		
		OperationContext ctx = new OperationContext();
		ctx.setUsername(v.getUsr());
		ctx.setPassword(v.getPsw());
		ctx.setNomeFisico(v.getUtenteApplicativo());
		ctx.setFruitore(v.getFruitore()); 
		ctx.setRepository(v.getRepostory());
		
		return ctx;
	}
	
	
	public Context convertToContext(IndexManagementPojo v) throws IntegrationException {
		
		Context ctx = new Context();
		ctx.setUsername(v.getUsr());
		ctx.setPassword(v.getPsw());
		ctx.setNomeFisico(v.getUtenteApplicativo());
		ctx.setFruitore(v.getFruitore()); 
		ctx.setRepository(v.getRepostory());
		ctx.setPrefixedName(DoquiConstants.INDEX_CONTENT_PREFIX_NAME);
		
		return ctx;
	}



}
