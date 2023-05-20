/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.ProntuarioService;
import it.csi.conam.conambl.dispatcher.ProntuarioDispatcher;
import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.ProntuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
@Component
public class ProntuarioDispatcherImpl implements ProntuarioDispatcher {

	@Autowired
	private ProntuarioService prontuarioService;

	@Override
	public List<ProntuarioVO> ricercaLeggeProntuario(RicercaLeggeProntuarioRequest ricercaLeggeProntuarioRequest) {
		return prontuarioService.ricercaLeggeProntuario(ricercaLeggeProntuarioRequest);
	}

	@Override
	public ProntuarioVO salvaLeggeProntuario(ProntuarioVO prontuario, UserDetails userDetails) {
		return prontuarioService.salvaLeggeProntuario(prontuario, userDetails);
	}

	@Override
	public void eliminaLeggeProntuario(Long idLettera, UserDetails userDetails) {
		prontuarioService.eliminaLeggeProntuario(idLettera, userDetails);
	}

	@Override
	public List<AmbitoVO> getAmbiti() {
		return prontuarioService.getAmbiti();
	}

	@Override
	public void salvaAmbito(AmbitoVO ambito, UserDetails userDetails) {
		prontuarioService.salvaAmbito(ambito, userDetails);

	}

	@Override
	public List<AmbitoVO> getAmbitiEliminabili() {
		return prontuarioService.getAmbitiEliminabili();
	}

	@Override
	public void eliminaAmbito(Integer idAmbito, UserDetails userDetails) {
		prontuarioService.eliminaAmbito(idAmbito, userDetails);
	}

}
