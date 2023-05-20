/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.beans.RequestAggiungiAllegato;
import it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato;
import it.csi.conam.conambl.integration.doqui.exception.AggiungiAllegatoException;

public interface ManageAggiungiAllegatoHelper extends CommonManageDocumentoHelper {
	
	public ResponseAggiungiAllegato aggiungiAllegato(RequestAggiungiAllegato request/*,String rootActa, String soggettoActaFruitore*/) throws AggiungiAllegatoException;

	//public ResponseAggiungiAllegato aggiungiAllegatoConam(RequestAggiungiAllegatoConam request) throws AggiungiAllegatoException;;
	
}
