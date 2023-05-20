/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.beans.RequestRicercaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumentoMultiplo;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoException;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

import java.util.List;

public interface ManageRicercaDocumentoHelper  extends CommonManageDocumentoHelper{
	
	
	/**
	 * 
	 * @param  RequestRicercaDocumento
	 * @return ResponseRicercaDocumento
	 * @throws RicercaDocumentoException
	 */
	public ResponseRicercaDocumentoMultiplo ricercaDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException;
	
	public List<DocumentoProtocollatoVO> ricercaDocumentoProtocollato(String numProtocollo, String codiceFruitore) throws RicercaDocumentoException;
	
	// 20200717_LC
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException;
	
	// 20200825_LC
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumentoFisico(RequestRicercaDocumento request) throws RicercaDocumentoException;

}
