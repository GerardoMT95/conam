/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_allegato_ord_verb_sog database table.
 * 
 */
@Embeddable
public class CnmRAllegatoOrdVerbSogPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_allegato", insertable=false, updatable=false)
	private Integer idAllegato;

	@Column(name="id_ordinanza_verb_sog", insertable=false, updatable=false)
	private Integer idOrdinanzaVerbSog;

	public CnmRAllegatoOrdVerbSogPK() {
	}
	public Integer getIdAllegato() {
		return this.idAllegato;
	}
	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}
	public Integer getIdOrdinanzaVerbSog() {
		return this.idOrdinanzaVerbSog;
	}
	public void setIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog) {
		this.idOrdinanzaVerbSog = idOrdinanzaVerbSog;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRAllegatoOrdVerbSogPK)) {
			return false;
		}
		CnmRAllegatoOrdVerbSogPK castOther = (CnmRAllegatoOrdVerbSogPK)other;
		return 
			this.idAllegato.equals(castOther.idAllegato)
			&& this.idOrdinanzaVerbSog.equals(castOther.idOrdinanzaVerbSog);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAllegato.hashCode();
		hash = hash * prime + this.idOrdinanzaVerbSog.hashCode();
		
		return hash;
	}
}
