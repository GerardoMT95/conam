/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.RicercaStoricaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.SalvaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.riscossione.InvioMassivoVO;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RiscossioneDispatcher {

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	List<SoggettiRiscossioneCoattivaVO> ricercaOrdinanzaSoggetti(RicercaSoggettiRiscossioneCoattivaRequest request);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	List<SoggettiRiscossioneCoattivaVO> aggiungiInListaRiscossione(List<Integer> request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	List<SoggettiRiscossioneCoattivaVO> getSoggettiRiscossione(boolean isBozza, Long idStato);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	List<SelectVO> getstatiRiscossioneCoattiva();

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	void deleteSoggettoRiscossione(Integer idRiscossione);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	SoggettiRiscossioneCoattivaVO salvaSoggettoRiscossione(SalvaSoggettiRiscossioneCoattivaRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	List<SoggettiRiscossioneCoattivaVO> getSoggettiStoriciRiscossione(RicercaStoricaSoggettiRiscossioneCoattivaRequest request);

	@PreAuthorize(value = AuthorizationRoles.RISCOSSIONE_COATTIVA)
	InvioMassivoVO invioSoggettiInRiscossione(UserDetails userDetails);

}
