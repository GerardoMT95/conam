/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo;

public class ExceptionVO extends ParentVO {

	private static final long serialVersionUID = -2517870193254779853L;

	public ExceptionVO(String codice, String message, String type) {
		super();
		this.codice = codice;
		this.message = message;
		this.type = type;
	}

	private String codice;
	private String message;
	private String type;

	public String getCodice() {
		return codice;
	}

	public String getMessage() {
		return message;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
