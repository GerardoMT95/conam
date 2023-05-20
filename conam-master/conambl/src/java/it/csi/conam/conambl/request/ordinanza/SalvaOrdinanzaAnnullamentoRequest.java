/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

// 20210304_LC lotto2scenario7
public class SalvaOrdinanzaAnnullamentoRequest extends SalvaOrdinanzaRequest {
	
	private static final long serialVersionUID = 4143597988324009928L;

	private String idOrdinanzaDaAnnullare;

	public String getIdOrdinanzaDaAnnullare() {
		return idOrdinanzaDaAnnullare;
	}

	public void setIdOrdinanzaDaAnnullare(String idOrdinanzaDaAnnullare) {
		this.idOrdinanzaDaAnnullare = idOrdinanzaDaAnnullare;
	}

}
