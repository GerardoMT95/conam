/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_allegato_ordinanza database table.
 * 
 */
@Embeddable
public class CnmRAllegatoOrdinanzaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_allegato", insertable=false, updatable=false)
	private Integer idAllegato;

	@Column(name="id_ordinanza", insertable=false, updatable=false)
	private Integer idOrdinanza;

	public CnmRAllegatoOrdinanzaPK() {
	}
	public Integer getIdAllegato() {
		return this.idAllegato;
	}
	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}
	public Integer getIdOrdinanza() {
		return this.idOrdinanza;
	}
	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRAllegatoOrdinanzaPK)) {
			return false;
		}
		CnmRAllegatoOrdinanzaPK castOther = (CnmRAllegatoOrdinanzaPK)other;
		return 
			this.idAllegato.equals(castOther.idAllegato)
			&& this.idOrdinanza.equals(castOther.idOrdinanza);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAllegato.hashCode();
		hash = hash * prime + this.idOrdinanza.hashCode();
		
		return hash;
	}
}
