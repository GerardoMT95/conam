/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class TipologiaAllegabiliPregressiRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer id;
	private String tipoDocumento;
	private boolean aggiungiCategoriaEmail;

	public Integer getId() {
		return id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isAggiungiCategoriaEmail() {
		return aggiungiCategoriaEmail;
	}

	public void setAggiungiCategoriaEmail(boolean aggiungiCategoriaEmail) {
		this.aggiungiCategoriaEmail = aggiungiCategoriaEmail;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
