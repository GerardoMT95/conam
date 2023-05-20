/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAllegatoOrdinanzaRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer idOrdinanza;

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
