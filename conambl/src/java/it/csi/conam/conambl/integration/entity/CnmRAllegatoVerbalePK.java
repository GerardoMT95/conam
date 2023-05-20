/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_allegato_verbale database table.
 * 
 */
@Embeddable
public class CnmRAllegatoVerbalePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_allegato", insertable=false, updatable=false)
	private Integer idAllegato;

	@Column(name="id_verbale", insertable=false, updatable=false)
	private Integer idVerbale;

	public CnmRAllegatoVerbalePK() {
	}
	public Integer getIdAllegato() {
		return this.idAllegato;
	}
	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}
	public Integer getIdVerbale() {
		return this.idVerbale;
	}
	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRAllegatoVerbalePK)) {
			return false;
		}
		CnmRAllegatoVerbalePK castOther = (CnmRAllegatoVerbalePK)other;
		return 
			this.idAllegato.equals(castOther.idAllegato)
			&& this.idVerbale.equals(castOther.idVerbale);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAllegato.hashCode();
		hash = hash * prime + this.idVerbale.hashCode();
		
		return hash;
	}
}
