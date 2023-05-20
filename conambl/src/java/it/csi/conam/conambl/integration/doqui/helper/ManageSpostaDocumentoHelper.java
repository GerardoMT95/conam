/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;


import it.csi.conam.conambl.integration.beans.RequestSpostaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.doqui.exception.SpostaDocumentoException;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

import java.util.List;

public interface ManageSpostaDocumentoHelper  extends CommonManageDocumentoHelper{
	// 20200626_LC
	
	public ResponseSpostaDocumento spostaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException;
	
	// 20200728_LC
	public ResponseSpostaDocumento copiaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException;
	
	//20200917_ET
	public ResponseSpostaDocumento salvaAllegatoGiaPresenteNelFascicoloActa(DocumentoProtocollatoVO doc, CnmDTipoAllegato cnmDTipoAllegato, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser, List<CnmTAllegato> allegatiGiaSalvati) throws SpostaDocumentoException;
}
