/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SoggettoDispatcher {

	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	List<SoggettoVO> ricercaSoggetti(MinSoggettoVO soggetto, UserDetails userDetails);
	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	List<SoggettoVO> ricercaSoggetti(SoggettoRequest soggetto, UserDetails userDetails);
	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoVO getSoggettoById(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoPregressiVO getSoggettoPregressiById(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoVO getSoggettoByIdAndIdVerbale(Integer id, Integer idVerbale, UserDetails utente);
	

}
