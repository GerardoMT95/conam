/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_allegato_sollecito database table.
 * 
 */
@Embeddable
public class CnmRAllegatoSollecitoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_allegato", insertable=false, updatable=false)
	private Integer idAllegato;

	@Column(name="id_sollecito", insertable=false, updatable=false)
	private Integer idSollecito;

	public CnmRAllegatoSollecitoPK() {
	}
	public Integer getIdAllegato() {
		return this.idAllegato;
	}
	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}
	public Integer getIdSollecito() {
		return this.idSollecito;
	}
	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRAllegatoSollecitoPK)) {
			return false;
		}
		CnmRAllegatoSollecitoPK castOther = (CnmRAllegatoSollecitoPK)other;
		return 
			this.idAllegato.equals(castOther.idAllegato)
			&& this.idSollecito.equals(castOther.idSollecito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAllegato.hashCode();
		hash = hash * prime + this.idSollecito.hashCode();
		
		return hash;
	}
}
