/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.pianorateizzazione;

import it.csi.conam.conambl.integration.entity.CnmTPianoRate;

public interface UtilsPianoRateizzazioneService {
    CnmTPianoRate validateAndGetCnmTPianoRate(Integer idPiano) ;
}
