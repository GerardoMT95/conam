/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.riscossione.RiscossioneService;
import it.csi.conam.conambl.dispatcher.RiscossioneDispatcher;
import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.RicercaStoricaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.SalvaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.riscossione.InvioMassivoVO;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiscossioneDispatcherImpl implements RiscossioneDispatcher {

	@Autowired
	private RiscossioneService riscossioneService;

	@Override
	public List<SoggettiRiscossioneCoattivaVO> ricercaOrdinanzaSoggetti(RicercaSoggettiRiscossioneCoattivaRequest request) {
		return riscossioneService.ricercaOrdinanzaSoggetti(request);
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> aggiungiInListaRiscossione(List<Integer> request, UserDetails userDetails) {
		return riscossioneService.aggiungiInListaRiscossione(request, userDetails);
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> getSoggettiRiscossione(boolean isBozza, Long idStato) {
		return riscossioneService.getSoggettiRiscossione(isBozza, idStato);
	}

	@Override
	public List<SelectVO> getstatiRiscossioneCoattiva() {
		return riscossioneService.getstatiRiscossioneCoattiva();
	}

	@Override
	public void deleteSoggettoRiscossione(Integer idRiscossione) {
		riscossioneService.deleteSoggettoRiscossione(idRiscossione);
	}

	@Override
	public SoggettiRiscossioneCoattivaVO salvaSoggettoRiscossione(SalvaSoggettiRiscossioneCoattivaRequest request, UserDetails userDetails) {
		return riscossioneService.salvaSoggettoRiscossione(request, userDetails);
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> getSoggettiStoriciRiscossione(RicercaStoricaSoggettiRiscossioneCoattivaRequest request) {
		return riscossioneService.getSoggettiStoriciRiscossione(request);
	}

	@Override
	public InvioMassivoVO invioSoggettiInRiscossione(UserDetails userDetails) {
		return riscossioneService.invioSoggettiInRiscossione(userDetails);
	}

}
