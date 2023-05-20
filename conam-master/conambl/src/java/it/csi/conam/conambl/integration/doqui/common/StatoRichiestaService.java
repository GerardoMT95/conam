/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.common;

import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;

public interface StatoRichiestaService {

	// 20200630_LC 
	void changeStatoRichiesta(Integer idRichiesta, long newStatoRichiesta);

	CnmTRichiesta insertRichiesta(CnmTRichiesta docTRichiestaDto);

	CnmTRichiesta getRichiesta(Integer idRichiesta) throws FruitoreException;

}
