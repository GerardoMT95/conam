/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.request.ParentRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAzioneRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer id;
	private Integer idFunzionario;
	private Long idAzione;

	public Integer getId() {
		return id;
	}

	public Integer getIdFunzionario() {
		return idFunzionario;
	}

	public Long getIdAzione() {
		return idAzione;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdFunzionario(Integer idFunzionario) {
		this.idFunzionario = idFunzionario;
	}

	public void setIdAzione(Long idAzione) {
		this.idAzione = idAzione;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
