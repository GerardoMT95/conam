/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.vo.leggi.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface RiferimentiNormativiDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<AmbitoVO> getAmbitiByIdEnte(Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<NormaVO> getNormeByIdAmbitoAndIdEnte(Integer idAmbito, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<LetteraVO> letteraByIdComma(Long idComma, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<CommaVO> commaByIdArticolo(Long idArticolo, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ArticoloVO> getArticoliByIdNormaAndIdEnte(Long idNorma, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

}
