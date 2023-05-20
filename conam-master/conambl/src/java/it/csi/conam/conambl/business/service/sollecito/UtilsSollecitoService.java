/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.sollecito;

import it.csi.conam.conambl.integration.entity.CnmTSollecito;

public interface UtilsSollecitoService {

	CnmTSollecito validateAndGetCnmTSollecito(Integer idSollecito);
}
