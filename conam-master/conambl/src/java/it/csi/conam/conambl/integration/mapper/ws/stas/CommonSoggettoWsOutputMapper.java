/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.stas;

import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.IndirizzoNazionale;
import it.csi.gmscore.dto.Luogo;
import it.csi.gmscore.dto.LuogoNazionale;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface CommonSoggettoWsOutputMapper {

	void mapResidenza(SoggettoVO soggetto, IndirizzoNazionale indirizzoResidenza);

	void mapLuogoNascita(SoggettoVO soggetto, LuogoNazionale luogoNascita);

	void mapLuogoNascita(SoggettoVO soggetto, Luogo luogoNascita);

	void mapLuogoOrigineEstero(SoggettoVO soggetto, Luogo luogoOrigine);

}
