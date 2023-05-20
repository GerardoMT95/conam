/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.doqui.bean.DocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.*;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;



public interface AcarisObjectService {	
	
	
	public ObjectIdType getIdentificatoreTramiteParolaChiave(ObjectIdType repositoryId, PrincipalIdType principalId, QueryableObjectType target, String parolaChiave, ObjectIdType parentNodeId) throws IntegrationException;
	
	/**
	 * 
	 * @param repositoryId
	 * @param principalId
	 * @param parentId
	 * @param vitalRecordCodeType
	 * @param documentoActa
	 * @param utenteActa
	 * @return
	 * @throws IntegrationException
	 */
	public ObjectIdType creaFascicolo(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType parentId, VitalRecordCodeType vitalRecordCodeType, DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	
	/**
	 * 
	 * @param repositoryId
	 * @param principalId
	 * @param parentId
	 * @param vitalRecordCodeType
	 * @param documentoActa
	 * @param utenteActa
	 * @return
	 * @throws IntegrationException
	 */
	public ObjectIdType creaVolume(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType parentId, VitalRecordCodeType vitalRecordCodeType, DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	/**
	 * 
	 * @param repositoryId
	 * @param principalId
	 * @param parentId
	 * @param vitalRecordCodeType
	 * @param documentoActa
	 * @param utenteActa
	 * @return
	 * @throws IntegrationException
	 */
	public ObjectIdType creaDossier(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType parentId, VitalRecordCodeType vitalRecordCodeType, DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	
	public String getUUIDDocumento(ObjectIdType repositoryId, PrincipalIdType principalId, QueryableObjectType target, String parolaChiave) throws IntegrationException; 
	public AcarisContentStreamType[] getContentStream(ObjectIdType repositoryId, ObjectIdType documentoId, PrincipalIdType principalId) throws IntegrationException;
	public Integer getStatoDiEfficacia(ObjectIdType repositoryId, PrincipalIdType principalId, Integer idStatoDiEfficacia) throws IntegrationException;
	public Integer getIdFormaDocumentaria(ObjectIdType repositoryId, PrincipalIdType principalId, UtenteActa utenteActa) throws IntegrationException;
	public void updatePropertiesDocumento(ObjectIdType repositoryId, ObjectIdType objectId, PrincipalIdType principalId, String oggetto) throws  IntegrationException;
	public void updatePropertiesOggettoDocumentoConProtocollo(ObjectIdType repositoryId, ObjectIdType objectIdClassificazione, ObjectIdType objectIdDocumento,PrincipalIdType principalId, String numeroProtocollo) throws  IntegrationException;	// 20200721_LC added "ConProtocollo"
	public String getIdIndiceClassificazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectIdClassificazione) throws IntegrationException;
	
	public void init();
	
	
	
	
	// 20200618_LC
	public ObjectIdType moveActaDocument(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType associativeObjectId, ObjectIdType sourceFolderId, ObjectIdType destinationFolderId, boolean isConAllegatiOrProtocollato) throws IntegrationException;
	
	public PagingResponseType getDocumentiTramiteProtocollo(ObjectIdType repositoryId, PrincipalIdType principalId, QueryableObjectType target, String numProtocollo, ObjectIdType parentNodeId) throws IntegrationException;
	
	public String getIndiceClassificazioneEstesa(ObjectIdType repositoryId, PrincipalIdType principalId, String objectIdClassificazione) throws IntegrationException;
	
	// 20200708_LC
	public void updatePropertiesParolaChiaveDocumento(ObjectIdType repositoryId, ObjectIdType objectIdDocumento,PrincipalIdType principalId, String newParolaChiave) throws  IntegrationException;

	// 20200721_LC
	public void updatePropertiesOggettoDocumento(ObjectIdType repositoryId, ObjectIdType objectIdDocumento,PrincipalIdType principalId, String newOggetto) throws  IntegrationException;

	// 20200711_LC
	public String getParolaChiaveDocumento(ObjectIdType repositoryId, PrincipalIdType principalId , ObjectIdType objectIdDocumento) throws  IntegrationException;
	
	// 20200722_LC
	public String getUUIDDocumentoByObjectIdDocumento(ObjectIdType repositoryId, PrincipalIdType principalId, QueryableObjectType target, String objectIdDocumento) throws IntegrationException;  

	// 20211019_LC
	public Integer getNumeroAllegatiPresenti(ObjectIdType repositoryId, PrincipalIdType principalId, String objectIdClassificazione) throws  IntegrationException;
	
}
