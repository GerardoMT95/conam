/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.ProntuarioVO;

import java.util.List;

public interface ProntuarioService {

	List<ProntuarioVO> ricercaLeggeProntuario(RicercaLeggeProntuarioRequest ricercaLeggeProntuarioRequest);

	ProntuarioVO salvaLeggeProntuario(ProntuarioVO prontuario, UserDetails userDetails);

	void eliminaLeggeProntuario(Long idLettera, UserDetails userDetails);

	List<AmbitoVO> getAmbiti();

	void salvaAmbito(AmbitoVO ambito, UserDetails userDetails);

	List<AmbitoVO> getAmbitiEliminabili();

	void eliminaAmbito(Integer idAmbito, UserDetails userDetails);

}
