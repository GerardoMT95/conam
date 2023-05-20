/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.doqui.exception.ProtocollaDocumentoException;

public interface ManageProtocollaDocumentoHelper extends CommonManageDocumentoHelper {
	
	
	
	public ResponseProtocollaDocumento protocollaDocumentoLogico(RequestProtocollaDocumentoLogico request) throws ProtocollaDocumentoException;
	public ResponseProtocollaDocumento protocollaDocumentoFisico(RequestProtocollaDocumentoFisico request/*,String rootActa, String soggettoActa, boolean isProtocollazioneInUscitaSenzaDocumento*/) throws ProtocollaDocumentoException;
	
	public ResponseProtocollaDocumento protocollaDocumentoFisicoEsistente(RequestProtocollaDocumentoFisico request, Integer idDocumentoEsistente) throws ProtocollaDocumentoException;

	public ResponseAssociaDocumentoFisico associaDocumentoFisico(RequestAssociaDocumentoFisico request) throws ProtocollaDocumentoException;
	
	//public ResponseProtocollaDocumento protocollaDocumentoFisicoConam(RequestProtocollaDocumentoFisico request)throws ProtocollaDocumentoException;
	
}
