/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;

import java.util.List;

public interface UtilsOrdinanza {
    CnmTOrdinanza validateAndGetCnmTOrdinanza(Integer idOrdinanza) ;
    
    boolean gestisciOrdinanzaComePregressa(Integer idOrdinanza);
    
    boolean soggettiVerbaleCompletiDiOrdinanza(CnmTVerbale cnmTVerbale);

    void isCFValid(CnmTOrdinanza cnmTOrdinanza) throws BusinessException;
    void isCFValid(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList) throws BusinessException;
    void isCFValidBySoggRata(List<CnmRSoggRata> cnmRSoggRataList) throws BusinessException;
}
