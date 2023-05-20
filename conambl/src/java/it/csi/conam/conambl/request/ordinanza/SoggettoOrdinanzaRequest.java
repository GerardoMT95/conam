/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.ParentRequest;

/**
 * @author riccardo.bova
 * @date 07 feb 2019
 */
public class SoggettoOrdinanzaRequest extends ParentRequest {

	private static final long serialVersionUID = 2548443639858454235L;

	private Integer idVerbaleSoggetto;
	private Long idTipoOrdinanza;

	public Integer getIdVerbaleSoggetto() {
		return idVerbaleSoggetto;
	}

	public Long getIdTipoOrdinanza() {
		return idTipoOrdinanza;
	}

	public void setIdVerbaleSoggetto(Integer idVerbaleSoggetto) {
		this.idVerbaleSoggetto = idVerbaleSoggetto;
	}

	public void setIdTipoOrdinanza(Long idTipoOrdinanza) {
		this.idTipoOrdinanza = idTipoOrdinanza;
	}

}
