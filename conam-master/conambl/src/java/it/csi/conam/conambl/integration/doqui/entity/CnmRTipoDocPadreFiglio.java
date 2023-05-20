/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;

 
 // 20200608_LC
 
@Entity
@Table(name="cnm_r_tipo_doc_padre_figlio")
@NamedQuery(name="CnmRTipoDocPadreFiglio.findAll", query="SELECT rpf FROM CnmRTipoDocPadreFiglio rpf")
public class CnmRTipoDocPadreFiglio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRTipoDocPadreFiglioPK id;
	
	// ---

	@ManyToOne
	@JoinColumn(name="id_tipo_doc_padre", insertable = false, updatable = false)
	private CnmDTipoDocumento tipoDocPadre;

	@ManyToOne
	@JoinColumn(name="id_tipo_doc_figlio", insertable = false, updatable = false)
	private CnmDTipoDocumento tipoDocFiglio;


	// ---
	
	public CnmRTipoDocPadreFiglio() {
	}
	
	// ---

	public CnmRTipoDocPadreFiglioPK getId() {
		return this.id;
	}

	public void setId(CnmRTipoDocPadreFiglioPK id) {
		this.id = id;
	}

	public CnmDTipoDocumento getTipoDocPadre() {
		return tipoDocPadre;
	}

	public void setTipoDocPadre(CnmDTipoDocumento tipoDocPadre) {
		this.tipoDocPadre = tipoDocPadre;
	}

	public CnmDTipoDocumento getTipoDocFiglio() {
		return tipoDocFiglio;
	}

	public void setTipoDocFiglio(CnmDTipoDocumento tipoDocFiglio) {
		this.tipoDocFiglio = tipoDocFiglio;
	}
	
	// ---

	
	
	

}
