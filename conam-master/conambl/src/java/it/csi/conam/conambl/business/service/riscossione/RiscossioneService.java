/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.riscossione;

import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.RicercaStoricaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.SalvaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.riscossione.InvioMassivoVO;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public interface RiscossioneService {

	List<SoggettiRiscossioneCoattivaVO> ricercaOrdinanzaSoggetti(RicercaSoggettiRiscossioneCoattivaRequest request);

	List<SoggettiRiscossioneCoattivaVO> getSoggettiRiscossione(boolean isBozza, Long idStato);

	List<SelectVO> getstatiRiscossioneCoattiva();

	List<SoggettiRiscossioneCoattivaVO> aggiungiInListaRiscossione(List<Integer> request, UserDetails userDetails);

	void deleteSoggettoRiscossione(Integer idRiscossione);

	SoggettiRiscossioneCoattivaVO salvaSoggettoRiscossione(SalvaSoggettiRiscossioneCoattivaRequest request, UserDetails userDetails);

	List<SoggettiRiscossioneCoattivaVO> getSoggettiStoriciRiscossione(RicercaStoricaSoggettiRiscossioneCoattivaRequest request);

	InvioMassivoVO invioSoggettiInRiscossione(UserDetails userDetails);

}
