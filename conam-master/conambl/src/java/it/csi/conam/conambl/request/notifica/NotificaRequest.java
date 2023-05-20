/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.notifica;

import it.csi.conam.conambl.request.ParentRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class NotificaRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Integer idPiano;
	private Integer idOrdinanza;
	private Integer idSollecito;

	public Integer getIdPiano() {
		return idPiano;
	}

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}

	public Integer getIdSollecito() {
		return idSollecito;
	}

	public void setIdPiano(Integer idPiano) {
		this.idPiano = idPiano;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
