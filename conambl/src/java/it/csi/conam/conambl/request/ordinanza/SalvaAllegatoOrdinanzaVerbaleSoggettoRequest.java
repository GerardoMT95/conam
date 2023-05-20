/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAllegatoOrdinanzaVerbaleSoggettoRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private List<Integer> idOrdinanzaVerbaleSoggetto;

	public List<Integer> getIdOrdinanzaVerbaleSoggetto() {
		return idOrdinanzaVerbaleSoggetto;
	}

	public void setIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggetto) {
		this.idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
