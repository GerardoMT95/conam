/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;

import it.csi.conam.conambl.integration.doqui.DoquiServiceFactory;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.service.AcarisRelationshipService;
import it.csi.csi.wrapper.CSIException;
import it.doqui.acta.acaris.relationshipsservice.AcarisException;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipDirectionType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.RelationshipPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.common.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcarisRelationshipServiceImpl extends CommonManagementServiceImpl implements AcarisRelationshipService
{
	
//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".integration";	    
//	private static Logger log = Logger.getLogger(LOGGER_PREFIX);	
	private static Logger log = Logger.getLogger(AcarisRelationshipServiceImpl.class);	

	private RelationshipsServicePort relationshipService;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	private String pdFile;

	private RelationshipsServicePort getRelationshipService(boolean forceLoading) throws Exception{
		String method = "getRelationshipService";
		log.debug(method + ". BEGIN");
		try{
			relationshipService = acarisServiceFactory.getAcarisService().getRelationshipsServicePort();
			log.info(method + ". AcarisRelationshipServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return relationshipService;
	}
	
	private RelationshipsServicePort getRelationshipService() throws Exception{
		return getRelationshipService(false);
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
			
			getRelationshipService(true);
		}
		catch(Exception e){
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
	}
	
	public List<ObjectIdType> recuperaIdContentStream(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType documentoId) throws IntegrationException
	{
		String method = "recuperaIdContentStream";
		log.debug(method + ". BEGIN");
		RelationshipPropertiesType[] relationshipPropertiesType = null;
		List<ObjectIdType> contentStreamId = null;	// 20200803_LC
		
		try 
		{
			relationshipPropertiesType = getRelationshipService().getObjectRelationships(repositoryId, principalId, documentoId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, getPropertyFilterAll());
			
			if(relationshipPropertiesType !=null && relationshipPropertiesType.length >0) {
	
				//RelationshipPropertiesType response = relationshipPropertiesType[0];
				
				contentStreamId = new ArrayList<ObjectIdType>();
								
				// 20200803_LC gestione contenuto fisico >1
				for(int i=0; i < relationshipPropertiesType.length; i++) {
					RelationshipPropertiesType response = relationshipPropertiesType[i];
				
				if(response != null)
				{
					
					log.debug(method + ". response.getObjectId() = " + response.getObjectId());
					log.debug(method + ". response.getObjectId() = " + response.getTargetType());
					log.debug(method + ". response.getObjectId() = " + response.getTargetId());
					log.debug(method + ". response.getObjectId() = " + response.getSourceId());
					log.debug(method + ". response.getObjectId() = " + response.getSourceType());
					
					// 20200803_LC
					// contentStreamId = response.getTargetId();
					contentStreamId.add(response.getTargetId());
				}
				
				}
			}		
		} 
		catch (AcarisException acEx) 
		{
			log.error(method + ". acEx.getMessage() = " + acEx.getMessage());
			log.error(method + ". acEx.getFaultInfo().getErrorCode() =  " + acEx.getFaultInfo().getErrorCode());
			log.error(method + ". acEx.getFaultInfo().getPropertyName() = " + acEx.getFaultInfo().getPropertyName());
			log.error(method + ". acEx.getFaultInfo().getObjectId() = " + acEx.getFaultInfo().getObjectId());
			log.error(method + ". acEx.getFaultInfo().getExceptionType() = " + acEx.getFaultInfo().getExceptionType());
			log.error(method + ". acEx.getFaultInfo().getClassName() = " + acEx.getFaultInfo().getClassName());
			log.error(method + ". acEx.getFaultInfo().getTechnicalInfo = " + acEx.getFaultInfo().getTechnicalInfo());
			throw new IntegrationException("AcarisException ", acEx);
		} 
		catch (Exception e) 
		{
			log.error(method + ". e.getMessage() = " + e.getStackTrace());
			throw new IntegrationException("Exception ", e);
		}
		return contentStreamId;
	}

	public static PropertyFilterType getPropertyFilterAll()
	{
		return getPropertyFilter(EnumPropertyFilter.ALL, null, null, null);
	}
	
	private static PropertyFilterType getPropertyFilter(EnumPropertyFilter type, String[] className, String[] propertyName, PropertyFilterType prevFilter )
	{
		PropertyFilterType filter = null;
		if(type != null)
		{
			if(type.value().equals(EnumPropertyFilter.LIST.value()))
			{
				filter = (prevFilter != null) ? prevFilter : new PropertyFilterType();
				filter.setFilterType(type);
				List<QueryNameType> properties = new ArrayList<QueryNameType>();
				QueryNameType property = null;
				if(className.length == propertyName.length)
				{
					if(	prevFilter != null && 
						prevFilter.getFilterType().value().equals(EnumPropertyFilter.LIST.value()) && 
						prevFilter.getPropertyListLength() > 0)
					{
						for (int j = 0; j < prevFilter.getPropertyListLength(); j++) 
						{
							properties.add(prevFilter.getPropertyList(j));
						}
					}
					for (int i = 0; i < propertyName.length; i++) 
					{
						property = new QueryNameType();
						property.setClassName(className[i]);
						property.setPropertyName(propertyName[i]);
						properties.add(property);
					}
					filter.setPropertyList(properties.toArray(new QueryNameType[0]));
				}
				else return null;
				
			}
			else
			{
				filter = new PropertyFilterType();
				filter.setFilterType(type);
			}
		}
		return filter;
	}
	
	public boolean isAlive() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean testResources() throws CSIException {
		// TODO Auto-generated method stub
		return false;
	}

	


	

}

