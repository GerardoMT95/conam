/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.riscossione;

import java.io.Serializable;

public class InvioMassivoVO implements Serializable {

	private static final long serialVersionUID = -3007370490422347412L;
	private String esito;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

}
