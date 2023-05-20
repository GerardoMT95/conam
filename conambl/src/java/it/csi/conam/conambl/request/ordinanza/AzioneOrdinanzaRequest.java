/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.vo.ParentVO;

public class AzioneOrdinanzaRequest extends ParentVO {

	private static final long serialVersionUID = -2058040095154843776L;

	private Integer id;
	private String tipoDocumento;

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

}
