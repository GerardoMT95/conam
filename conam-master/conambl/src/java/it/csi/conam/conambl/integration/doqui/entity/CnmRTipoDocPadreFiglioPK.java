/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

 
 // 20200608_LC
 
@Embeddable
public class CnmRTipoDocPadreFiglioPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id_tipo_doc_padre", insertable=false, updatable=false)
	private long idTipoDocPadre;

	@Column(name="id_tipo_doc_figlio", insertable=false, updatable=false)
	private long idTipoDocFiglio;

	public CnmRTipoDocPadreFiglioPK() {
	}
	public long getIdTipoDocPadre() {
		return this.idTipoDocPadre;
	}
	public void setIdTipoDocPadre(long idTipoDocPadre) {
		this.idTipoDocPadre = idTipoDocPadre;
	}
	public long getIdTipoDocFiglio() {
		return this.idTipoDocFiglio;
	}
	public void setIdTipoDocFiglio(long idTipoDocFiglio) {
		this.idTipoDocFiglio = idTipoDocFiglio;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTipoDocFiglio ^ (idTipoDocFiglio >>> 32));
		result = prime * result + (int) (idTipoDocPadre ^ (idTipoDocPadre >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CnmRTipoDocPadreFiglioPK other = (CnmRTipoDocPadreFiglioPK) obj;
		if (idTipoDocFiglio != other.idTipoDocFiglio)
			return false;
		if (idTipoDocPadre != other.idTipoDocPadre)
			return false;
		return true;
	}


	
	

}
