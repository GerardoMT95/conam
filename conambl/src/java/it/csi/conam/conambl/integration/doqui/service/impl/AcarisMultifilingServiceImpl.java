/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;

import it.csi.conam.conambl.integration.doqui.DoquiServiceFactory;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.service.AcarisMultifilingService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.doqui.acta.acaris.multifilingservice.AcarisException;
import it.doqui.acta.acaris.multifilingservice.MultifilingServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.common.ItemType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.VarargsType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//20200728_LC
@Service
public class AcarisMultifilingServiceImpl extends CommonManagementServiceImpl implements AcarisMultifilingService
{
	
//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".integration";	    
//	private static Logger log = Logger.getLogger(LOGGER_PREFIX);	
	private static Logger log = Logger.getLogger(AcarisMultifilingServiceImpl.class);	

	private MultifilingServicePort multifilingService;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	private String pdFile;

	private MultifilingServicePort getMultifilingService(boolean forceLoading) throws Exception{
		String method = "getMultifilingService";
		log.debug(method + ". BEGIN");
		try{
			
			multifilingService = acarisServiceFactory.getAcarisService().getMultifilingServicePort();
			log.info(method + ". AcarisMultifilingServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return multifilingService;
	}
	
	@SuppressWarnings("unused")
	private MultifilingServicePort getMultifilingService() throws Exception{
		return getMultifilingService(false);
	}
	

	
	public String getPdFile() 
	{
		return pdFile;
	}

	public void setPdFile(String pdFile)
	{
		this.pdFile = pdFile;
	}
	
	
	
	public void init(){
		String method = "init";
		
		try{
			super.init();
			if(log.isDebugEnabled()) 
				log.debug(method + ". pdFile= " + getPdFile());
			
			getMultifilingService(true);
		}
		catch(Exception e){
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
	}



	
	
	
	

	// 20200618_LC
	public ObjectIdType aggiungiClassificazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType sourceClassificazioneId, ObjectIdType destinationFolderId, boolean isRichiestaOffline) throws IntegrationException {
		String method = "spostaDocumento";

		// id nuova classificazione
		ObjectIdType classificazioneId = null;

		try {		
		
		//	properties	
		// -----------------------------------------------------------------------------------------------------
		
		// verificare questi parametri (non si valorizza la nuovaClassificazione)			
		List<ItemType>	properties = new ArrayList<ItemType>();
		ItemType prop1 = null;
		prop1 = new ItemType();
		prop1.setKey("addConAllegati");
		prop1.setValue("true");
		ItemType prop2 = null;
		prop2 = new ItemType();
		prop2.setKey("offlineAddRequest");
		prop2.setValue(Boolean.toString(isRichiestaOffline));
		properties.add(prop1);
		properties.add(prop2);
		properties.toArray(new ItemType[0]);
		
		
		
			
		
//		ItemType[] props = new ItemType[2];
//		// -
//		props[0].setKey("addConAllegati");
//		props[0].setValue("true");
//		// -
//		props[1].setKey("offlineAddRequest");
//		props[1].setValue(Boolean.toString(isRichiestaOffline));				// come nella moveDocument
//		// -
		

		VarargsType params = new VarargsType();		
		params.setItems(properties.toArray(new ItemType[0]));
		
		// -----------------------------------------------------------------------------------------------------
		
		
		

			
			// chiamata tramite WS
			classificazioneId = acarisServiceFactory.getAcarisService().getMultifilingServicePort().aggiungiClassificazione(repositoryId, principalId, sourceClassificazioneId, destinationFolderId, params);
			
			
			if(classificazioneId == null){
				throw new IntegrationException("Non e' stato possibile copiare il documento, idNuovaClassificazione is null");
			}
			
			if(log.isDebugEnabled()){
				log.debug(method + ". copiato documento, nuova classificazione Id\n " + XmlSerializer.objectToXml(classificazioneId));
			}
			
		}
		catch (AcarisException acEx){
			log.error(method + ". Si e' verificato un errore in fase di copia del documento " + acEx.getMessage());
			if(acEx.getFaultInfo() != null) {
				log.error(method + ". acEx.getFaultInfo().getErrorCode()     =  " + acEx.getFaultInfo().getErrorCode());
				log.error(method + ". acEx.getFaultInfo().getPropertyName()  = " + acEx.getFaultInfo().getPropertyName());
				log.error(method + ". acEx.getFaultInfo().getObjectId()      = " + acEx.getFaultInfo().getObjectId());
				log.error(method + ". acEx.getFaultInfo().getExceptionType() = " + acEx.getFaultInfo().getExceptionType());
				log.error(method + ". acEx.getFaultInfo().getClassName()     = " + acEx.getFaultInfo().getClassName());
				log.error(method + ". acEx.getFaultInfo().getTechnicalInfo() = " + acEx.getFaultInfo().getTechnicalInfo());	
			}
			throw new IntegrationException("Impossibile copiare il documento ", acEx);
		}
		catch (Exception e) {
			log.error(method + ". Exception = " + e.getMessage());
			throw new IntegrationException("Impossibile copiare il documento ", e);
		}
		
		return classificazioneId;
	}


	

}

