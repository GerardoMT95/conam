/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.ProntuarioVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface ProntuarioDispatcher {

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PRONTUARIO)
	List<ProntuarioVO> ricercaLeggeProntuario(RicercaLeggeProntuarioRequest ricercaLeggeProntuarioRequest);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PRONTUARIO)
	ProntuarioVO salvaLeggeProntuario(ProntuarioVO prontuario, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<AmbitoVO> getAmbiti();

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PRONTUARIO)
	void salvaAmbito(AmbitoVO ambito, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PRONTUARIO)
	void eliminaLeggeProntuario(Long idLettera, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<AmbitoVO> getAmbitiEliminabili();

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PRONTUARIO)
	void eliminaAmbito(Integer idAmbito, UserDetails utente);

}
