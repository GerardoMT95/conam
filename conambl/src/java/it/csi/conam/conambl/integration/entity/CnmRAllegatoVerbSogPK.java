/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_allegato_verb_sog database table.
 * 
 */
@Embeddable
public class CnmRAllegatoVerbSogPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_allegato", insertable=false, updatable=false)
	private Integer idAllegato;

	@Column(name="id_verbale_soggetto", insertable=false, updatable=false)
	private Integer idVerbaleSoggetto;

	public CnmRAllegatoVerbSogPK() {
	}
	public Integer getIdAllegato() {
		return this.idAllegato;
	}
	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}
	public Integer getIdVerbaleSoggetto() {
		return this.idVerbaleSoggetto;
	}
	public void setIdVerbaleSoggetto(Integer idVerbaleSoggetto) {
		this.idVerbaleSoggetto = idVerbaleSoggetto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRAllegatoVerbSogPK)) {
			return false;
		}
		CnmRAllegatoVerbSogPK castOther = (CnmRAllegatoVerbSogPK)other;
		return 
			this.idAllegato.equals(castOther.idAllegato)
			&& this.idVerbaleSoggetto.equals(castOther.idVerbaleSoggetto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAllegato.hashCode();
		hash = hash * prime + this.idVerbaleSoggetto.hashCode();
		
		return hash;
	}
}
