/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.vo.ParentVO;

public class SalvaStatoOrdinanzaRequest extends ParentVO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2012066589340718526L;
	
	private Integer id;
	private Long idStato;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}

	
}
