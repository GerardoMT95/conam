/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.leggi;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class RicercaLeggeProntuarioRequest extends ParentRequest {

	private static final long serialVersionUID = 3903528110898228996L;

	@NotNull(message = "REQCON01")
	private EnteVO enteLegge;
	@NotNull(message = "REQCON02")
	public AmbitoVO ambito;

	public EnteVO getEnteLegge() {
		return enteLegge;
	}

	public AmbitoVO getAmbito() {
		return ambito;
	}

	public void setEnteLegge(EnteVO enteLegge) {
		this.enteLegge = enteLegge;
	}

	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
