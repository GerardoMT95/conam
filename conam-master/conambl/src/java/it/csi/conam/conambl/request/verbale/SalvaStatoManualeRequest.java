/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.request.ParentRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lucio Rosadini
 */
public class SalvaStatoManualeRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer id;
	private Integer idStatoManuale;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdStatoManuale() {
		return idStatoManuale;
	}

	public void setIdStatoManuale(Integer idStatoManuale) {
		this.idStatoManuale = idStatoManuale;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
