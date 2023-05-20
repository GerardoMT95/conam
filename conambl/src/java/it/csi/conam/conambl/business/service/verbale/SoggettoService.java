/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

import java.util.List;

public interface SoggettoService {

	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	List<SoggettoVO> ricercaSoggetti(MinSoggettoVO soggetto, UserDetails userDetails);
	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/
	
	SoggettoVO getSoggettoById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario);
	SoggettoPregressiVO getSoggettoPregressiById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario);
	SoggettoVO getSoggettoByIdAndIdVerbale(Integer id, Integer idVerbale, UserDetails userDetails, boolean b);

	List<SoggettoVO> ricercaSoggettiRelata(Integer idVerbale, UserDetails userDetails);

}
