/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.scrittodifensivo;

import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;

public interface UtilsScrittoDifensivoService {
    CnmTScrittoDifensivo validateAndGetCnmTScrittoDifensivo(Integer idScrittoDifensivo) ;
}
