/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.scrittodifensivo;

import it.csi.conam.conambl.business.service.scrittodifensivo.UtilsScrittoDifensivoService;
import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import it.csi.conam.conambl.integration.repositories.CnmTScrittoDifensivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsScrittoDifensivoServiceImpl implements UtilsScrittoDifensivoService {

    @Autowired
    private CnmTScrittoDifensivoRepository cnmTScrittoDifensivoRepository;

    @Override
    public CnmTScrittoDifensivo validateAndGetCnmTScrittoDifensivo(Integer idScrittoDifensivo) {
        if (idScrittoDifensivo == null) throw new IllegalArgumentException("idScrittoDifensivo is null");       
        CnmTScrittoDifensivo cnmTScrittoDifensivo = cnmTScrittoDifensivoRepository.findOne(idScrittoDifensivo);
        if (cnmTScrittoDifensivo == null) throw new IllegalArgumentException("id non valido ");
        return cnmTScrittoDifensivo;
    }
}
