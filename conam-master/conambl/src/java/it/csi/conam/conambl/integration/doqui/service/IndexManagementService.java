/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.beans.Context;
import it.csi.conam.conambl.integration.doqui.bean.IndexManagementPojo;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;

/**
 * @author giuganino
 * Interfaccia per la gestione dei servizi index/mtom
 */
public interface IndexManagementService {
		
	/**
	 * Servizio di salvataggio del documento attraverso index/mtom
	 * @param IndexManagementPojo pojo contenente le informazioni per il salvataggio del documento
	 * @return String uid del documento uploadato
	 * @throws IntegrationException
	 */
	public String salvaDocumento(IndexManagementPojo pojo) throws IntegrationException;
	
	
	/**
	 * Servizio di salvataggio di un documento logico(vuoto)
	 * @param pojo
	 * @return
	 * @throws IntegrationException
	 */
	public String salvaDocumentoLogico(IndexManagementPojo pojo) throws IntegrationException;
	
	/**
	 * Servizio di eliminazione di un documento da index
	 * @param IndexManagementPojo pojo contenente l'id del documento da eliminare (valore della sequence staccata da stadoc)
	 * @throws IntegrationException
	 */
	public void eliminaDocumento(IndexManagementPojo pojo) throws IntegrationException;
	
	/**
	 * Servizio per il download del documento. La chiamata � sempre via WS
	 * @param IndexManagementPojo pojo contenente l'id del documento da scaricare
	 * @return byte[] il documento 
	 * @throws IntegrationException
	 */
	public byte[] scaricaDocumento(IndexManagementPojo pojo) throws IntegrationException;
	
	
	/**
	 * Recupera il mime type associato all'estensione del file indicato
	 * @param fileName
	 * @return String
	 * @throws IntegrationException
	 */
	public String getMimeType(String fileName) throws IntegrationException;
	
	
	
	
	
	// 20200612_LC
	
	/**
	 * Crea un Content partendo dall'IndexManagementPojo
	 * @param IndexManagementPojo pojo contenente le info necessarie
	 * @return Content
	 * @throws IntegrationException
	 */
	public Content convertToContent(IndexManagementPojo pojo) throws IntegrationException;
	
	/**
	 * Crea un OperationContext partendo dall'IndexManagementPojo
	 * @param IndexManagementPojo pojo contenente le info necessarie
	 * @return OperationContext
	 * @throws IntegrationException
	 */
	public OperationContext convertToOperationContext(IndexManagementPojo pojo) throws IntegrationException;		

	/**
	 * Crea un Context stadoc partendo dall'IndexManagementPojo
	 * @param IndexManagementPojo pojo contenente le info necessarie
	 * @return Content
	 * @throws IntegrationException
	 */
	public Context convertToContext(IndexManagementPojo pojo) throws IntegrationException;

	/**
	 * Controlla se le firme sul file sono valide partendo dall'IndexManagementPojo
	 * @param IndexManagementPojo pojo contenente le info necessarie
	 * @throws IntegrationException
	 */
	public void controllaFirmaDocumento(IndexManagementPojo pojo) throws IntegrationException;
	
}
