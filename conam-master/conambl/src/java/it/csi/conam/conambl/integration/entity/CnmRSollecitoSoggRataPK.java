/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the cnm_r_sollecito_sogg_rata database table.
 * 
 */
@Embeddable
public class CnmRSollecitoSoggRataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_sollecito", insertable=false, updatable=false)
	private Integer idSollecito;

	@Column(name="id_ordinanza_verb_sog", insertable=false, updatable=false)
	private Integer idOrdinanzaVerbSog;

	@Column(name="id_rata", insertable=false, updatable=false)
	private Integer idRata;

	public CnmRSollecitoSoggRataPK() {
	}
	public Integer getIdSollecito() {
		return this.idSollecito;
	}
	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}
	public Integer getIdOrdinanzaVerbSog() {
		return this.idOrdinanzaVerbSog;
	}
	public void setIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog) {
		this.idOrdinanzaVerbSog = idOrdinanzaVerbSog;
	}
	public Integer getIdRata() {
		return this.idRata;
	}
	public void setIdRata(Integer idRata) {
		this.idRata = idRata;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmRSollecitoSoggRataPK)) {
			return false;
		}
		CnmRSollecitoSoggRataPK castOther = (CnmRSollecitoSoggRataPK)other;
		return 
			this.idSollecito.equals(castOther.idSollecito)
			&& this.idOrdinanzaVerbSog.equals(castOther.idOrdinanzaVerbSog)
			&& this.idRata.equals(castOther.idRata);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSollecito.hashCode();
		hash = hash * prime + this.idOrdinanzaVerbSog.hashCode();
		hash = hash * prime + this.idRata.hashCode();
		
		return hash;
	}
}
