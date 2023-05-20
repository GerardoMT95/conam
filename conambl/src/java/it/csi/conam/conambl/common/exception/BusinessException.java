/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2631969419398877860L;

	private String codice;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public BusinessException(String codice) {
		super();
		this.codice = codice;
	}

}
