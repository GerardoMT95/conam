/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;

public interface StoricizzazioneVerbaleService {

	void storicizzaStatoVerbale(CnmTVerbale cnmTVerbale, CnmTUser cnmTUser);

}
