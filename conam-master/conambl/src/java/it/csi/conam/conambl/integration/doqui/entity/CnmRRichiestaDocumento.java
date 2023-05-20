/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;

 
 // 20200608_LC
 
@Entity
@Table(name="cnm_r_richiesta_documento")
@NamedQuery(name="CnmRRichiestaDocumento.findAll", query="SELECT rrd FROM CnmRRichiestaDocumento rrd")
public class CnmRRichiestaDocumento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRRichiestaDocumentoPK id;
	
	// ---

	@ManyToOne
	@JoinColumn(name="id_richiesta", insertable = false, updatable = false)
	private CnmTRichiesta cnmTRichiesta;

	@ManyToOne
	@JoinColumn(name="id_documento", insertable = false, updatable = false)
	private CnmTDocumento cnmTDocumento;


	// ---
	
	public CnmRRichiestaDocumento() {
	}
	
	// ---


	public CnmRRichiestaDocumentoPK getId() {
		return this.id;
	}

	public void setId(CnmRRichiestaDocumentoPK id) {
		this.id = id;
	}
	
	// ---

	public CnmTRichiesta getCnmTRichiesta() {
		return this.cnmTRichiesta;
	}

	public void setCnmTRichiesta(CnmTRichiesta cnmTRichiesta) {
		this.cnmTRichiesta = cnmTRichiesta;
	}

	public CnmTDocumento getCnmTDocumento() {
		return this.cnmTDocumento;
	}

	public void setCnmTDocumento(CnmTDocumento cnmTDocumento) {
		this.cnmTDocumento = cnmTDocumento;
	}


}
