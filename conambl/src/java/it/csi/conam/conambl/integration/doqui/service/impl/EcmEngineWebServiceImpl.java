/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;

import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.DoquiServiceFactory;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.service.EcmEngineWebService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.FileFormatInfo;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Mimetype;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.NodeResponse;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.SearchParams;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.SearchResponse;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.EnvelopedContent;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.Signature;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.VerifyReport;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class EcmEngineWebServiceImpl extends CommonManagementServiceImpl implements EcmEngineWebService {
	
//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".integration";	    
//	private static Logger LOG = Logger.getLogger(LOGGER_PREFIX);	
	private static Logger LOG = Logger.getLogger(EcmEngineWebServiceImpl.class);	

	private EcmEngineWebServiceDelegate indexService;
	

	//private EcmEngineSecurityBusinessInterface indexSecService;

	@Autowired
	private DoquiServiceFactory indexServiceFactory;
	
	
	
	
	
	public void setIndexService(EcmEngineWebServiceDelegate indexService) {
		this.indexService = indexService;
	}

	public void init(){
		try {getIndexService();} catch (Exception e) {}
	}
	
	private EcmEngineWebServiceDelegate getIndexService() throws Exception {
		final String method = "init";
		LOG.debug(method + " BEGIN");
		try {
			
			if (indexService == null) {
				String endpoint = indexServiceFactory.getIndexServiceEndpointUrl();
				LOG.info(method + ". endpoint= " + endpoint);
				if(StringUtils.isBlank(endpoint)) {
					throw new Exception("Index endpoint is blank");
				}
				indexService = new EcmEngineWebServiceDelegateServiceLocator().getEcmEngineManagement(new URL(endpoint));
				
			}
			LOG.info(method + ". EcmEngineWebService loaded correctly");	
			
		}
		catch(Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new Exception("Impossibile chiamare flaianag");
		}
		return indexService;
	}

	@Override
	public boolean testResource() throws IntegrationException {
		final String method = "testResource";
		try {


			return  getIndexService().testResources();
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
	}

	@Override
	public String getMimeType(Mimetype mimeType) throws IntegrationException {
		final String method = "getMimeType";
		try {
			Mimetype[] mime = getIndexService().getMimetype(mimeType);
			if(mime!=null && mime.length>0){
				return mime[0].getMimetype();
			}
			else{
				return DoquiConstants.MIMETYPE_DEFAULT;
			}
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
	}

	@Override
	public String luceneSearchNoMetadata(SearchParams params, OperationContext operationContext) throws IntegrationException {
		final String method = "luceneSearchNoMetadata";
		String uid = null;
		try {
			String luceneQuery = "PATH:\""+params.getXPathQuery()+"\"";
			LOG.info(method + ". lucene query= " +luceneQuery);
			//params.setLuceneQuery("PATH:\""+params.getXPathQuery()+"\"");
			params.setLuceneQuery(luceneQuery);
			NodeResponse response = getIndexService().luceneSearchNoMetadata(params, operationContext);
			if(response != null &&  response.getTotalResults() > 0 ){
				uid = response.getNodeArray()[0].getUid();
				LOG.info(method + ". Folder  " + uid + " exists");
			}

		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		return uid;
	}

	@Override
	public Node createContent(Node node, Content content, OperationContext operationContext) throws IntegrationException {
		final String method = "createContent";
		Node result = null;
		try {
			result = getIndexService().createContent(node, content, operationContext);
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public byte[] retrieveContentData(Node node, Content content, OperationContext operationContext) throws IntegrationException {
		final String method = "retrieveContentData";
		try {

			FileFormatInfo [] ffi =  getIndexService().getFileFormatInfo(node, content, operationContext);
			LOG.info(method + " ffi\n " + XmlSerializer.objectToXml(ffi));
			

			return getIndexService().retrieveContentData(node, content, operationContext);
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}

	}

	
	
	// 20200612_LC
	@Override
	public String luceneSearch(SearchParams params, OperationContext operationContext) throws IntegrationException {
		final String method = "luceneSearch";
		String uid = null;
		try {
			String luceneQuery = "PATH:\""+params.getXPathQuery()+"\"";
			LOG.info(method + ". lucene query= " +luceneQuery);
			//params.setLuceneQuery("PATH:\""+params.getXPathQuery()+"\"");
			params.setLuceneQuery(luceneQuery);
			SearchResponse response = getIndexService().luceneSearch(params, operationContext);
			if(response != null &&  response.getTotalResults() > 0 ){
				// uid = response.getNodeArray()[0].getUid();
				uid = response.getResultContentArray()[0].getUid();	// 
				LOG.info(method + ". Folder  " + uid + " exists");
			}

		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		return uid;
	}
	
	
	@Override
	public Node deleteContent(Node node, OperationContext operationContext) throws IntegrationException {
		final String method = "deleteContent";
		Node result = null;
		try {
			getIndexService().deleteContent(node, operationContext);	// TODO verificare
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		
		return result;
	}


	@Override
	public VerifyReport verifyDocument(EnvelopedContent EnvelopedContent, OperationContext operationContext) throws IntegrationException {
		final String method = "verifyDocument";
		VerifyReport result = null;
		try {

			if(LOG.isDebugEnabled()){
				LOG.debug(method + ". EnvelopedContent\n " + XmlSerializer.objectToXml(EnvelopedContent));
				LOG.debug(method + ". OperationContext\n " + XmlSerializer.objectToXml(operationContext));
			}
			result = getIndexService().verifyDocument(EnvelopedContent, operationContext);	// TODO verificare
			
			if(result.getErrorCode() != 0) {
				throw new IntegrationException("Errore nel processo di verifica delle firme!");
			}else {
				for(Signature sign : result.getSignature()) {
					if(sign.getErrorCode() != 0) {
						throw new IntegrationException("Sul documento sono presenti firme non valide!");
					}
				}
			}
		} catch (Exception e) {
			LOG.error(method + ". Exception " + e);
			if(e instanceof IntegrationException) {
				throw (IntegrationException)e;
			}
			throw new IntegrationException(e.getMessage(), e);
		}
		
		return result;
	}


	
}
