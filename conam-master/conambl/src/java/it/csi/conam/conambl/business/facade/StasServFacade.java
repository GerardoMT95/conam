/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade;

import it.csi.gmscore.dto.Anagrafica;
import it.csi.gmscore.dto.ModuloRicercaPF;
import it.csi.gmscore.dto.SoggettoImpresa;
import it.csi.gmscore.dto.SoggettoPF;

/**
 * @author riccardo.bova
 * @date 24 ott 2018
 */
public interface StasServFacade {

	Anagrafica[] ricercaSoggettoCF(String cf, String token);

	SoggettoPF[] ricercaPersonaFisicaCompleta(ModuloRicercaPF arg0, String token);

	SoggettoImpresa[] ricercaImpresa(String denominazione, String token);

	Anagrafica ricercaSoggettoID(Long id);

}
