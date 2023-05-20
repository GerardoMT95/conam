/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAllegatoVerbaleRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer idVerbale;
	private Integer idVerbaleAudizione;

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public Integer getIdVerbaleAudizione() {
		return idVerbaleAudizione;
	}

	public void setIdVerbaleAudizione(Integer idVerbaleAudizione) {
		this.idVerbaleAudizione = idVerbaleAudizione;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
