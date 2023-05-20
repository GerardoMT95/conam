/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.pianorateizzazione;

import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.repositories.CnmTPianoRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsPianoRateizzazioneServiceImpl implements UtilsPianoRateizzazioneService {

    @Autowired
    private CnmTPianoRateRepository cnmTPianoRateRepository;

    @Override
    public CnmTPianoRate validateAndGetCnmTPianoRate(Integer idPiano) {
        if (idPiano == null)
            throw new IllegalArgumentException("idPiano = null");
        CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(idPiano);
        if (cnmTPianoRate == null)
            throw new IllegalArgumentException("id non valido ");

        return cnmTPianoRate;
    }
}
