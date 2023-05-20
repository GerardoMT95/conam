/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.epay;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.epay.to.InserisciListaDiCaricoRequest;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 31 gen 2019
 */
public interface EPayWsInputMapper {

	InserisciListaDiCaricoRequest mapRateSoggettoToWsMapper(List<CnmRSoggRata> rate);

	InserisciListaDiCaricoRequest mapRataSoggettiToWsMapper(List<CnmROrdinanzaVerbSog> soggettiRata);

	InserisciListaDiCaricoRequest mapRataSollecitoToWsMapper(CnmTSollecito cnmTSollecito);
}
