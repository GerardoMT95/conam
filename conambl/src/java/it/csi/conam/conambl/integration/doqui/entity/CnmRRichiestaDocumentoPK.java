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
public class CnmRRichiestaDocumentoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id_richiesta", insertable=false, updatable=false)
	private Integer idRichiesta;

	@Column(name="id_documento", insertable=false, updatable=false)
	private Integer idDocumento;

	public CnmRRichiestaDocumentoPK() {
	}
	public Integer getIdRichiesta() {
		return this.idRichiesta;
	}
	public void setIdRichiesta(Integer idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public Integer getIdDocumento() {
		return this.idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idDocumento ^ (idDocumento >>> 32));
		result = prime * result + (int) (idRichiesta ^ (idRichiesta >>> 32));
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
		CnmRRichiestaDocumentoPK other = (CnmRRichiestaDocumentoPK) obj;
		if (idDocumento != other.idDocumento)
			return false;
		if (idRichiesta != other.idRichiesta)
			return false;
		return true;
	}



	
}
