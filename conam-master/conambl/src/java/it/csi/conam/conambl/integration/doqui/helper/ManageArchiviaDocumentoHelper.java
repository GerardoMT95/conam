/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoFisico;
import it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoLogico;
import it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento;
import it.csi.conam.conambl.integration.doqui.exception.ArchiviaDocumentoException;

public interface ManageArchiviaDocumentoHelper  extends CommonManageDocumentoHelper{
	
	
	/**
	 * 
	 * @param RequestArchiviaDocumentoLogico
	 * @return ResponseArchiviaDocumento
	 * @throws ArchiviaDocumentoException
	 */
	public ResponseArchiviaDocumento archiviaDocumentoLogico(RequestArchiviaDocumentoLogico request) throws ArchiviaDocumentoException;
	/**
	 * 
	 * @param RequestArchiviaDocumentoFisico
	 * @return ResponseArchiviaDocumento
	 * @throws ArchiviaDocumentoException
	 */
	public ResponseArchiviaDocumento archiviaDocumentoFisico(RequestArchiviaDocumentoFisico request, String fruitoreActa) throws ArchiviaDocumentoException;
	

}
