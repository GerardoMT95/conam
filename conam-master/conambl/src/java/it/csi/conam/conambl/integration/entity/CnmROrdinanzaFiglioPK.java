/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class CnmROrdinanzaFiglioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_ordinanza", insertable=false, updatable=false)
	private Integer idOrdinanza;

	@Column(name="id_ordinanza_figlio", insertable=false, updatable=false)
	private Integer idOrdinanzaFiglio;

	public CnmROrdinanzaFiglioPK() {
	}
	
	

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}



	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}



	public Integer getIdOrdinanzaFiglio() {
		return idOrdinanzaFiglio;
	}



	public void setIdOrdinanzaFiglio(Integer idOrdinanzaFiglio) {
		this.idOrdinanzaFiglio = idOrdinanzaFiglio;
	}



	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CnmROrdinanzaFiglioPK)) {
			return false;
		}
		CnmROrdinanzaFiglioPK castOther = (CnmROrdinanzaFiglioPK)other;
		return 
			this.idOrdinanzaFiglio.equals(castOther.idOrdinanzaFiglio)
			&& this.idOrdinanza.equals(castOther.idOrdinanza);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idOrdinanzaFiglio.hashCode();
		hash = hash * prime + this.idOrdinanza.hashCode();
		
		return hash;
	}
}
