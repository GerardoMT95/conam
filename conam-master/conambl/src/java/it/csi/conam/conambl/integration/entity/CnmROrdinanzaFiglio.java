/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name="cnm_r_ordinanza_figlio")
@NamedQuery(name="CnmROrdinanzaFiglio.findAll", query="SELECT c FROM CnmROrdinanzaFiglio c")
public class CnmROrdinanzaFiglio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmROrdinanzaFiglioPK id;

	//bi-directional many-to-one association to CnmTOrdinanza
	@ManyToOne
	@JoinColumn(name="id_ordinanza", insertable = false, updatable = false)
	private CnmTOrdinanza cnmTOrdinanza;

	//bi-directional many-to-one association to CnmTOrdinanza
	@ManyToOne
	@JoinColumn(name="id_ordinanza_figlio", insertable = false, updatable = false)
	private CnmTOrdinanza cnmTOrdinanzaFiglio;

	public CnmROrdinanzaFiglio() {
	}

	public CnmROrdinanzaFiglioPK getId() {
		return id;
	}

	public void setId(CnmROrdinanzaFiglioPK id) {
		this.id = id;
	}

	public CnmTOrdinanza getCnmTOrdinanza() {
		return cnmTOrdinanza;
	}

	public void setCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		this.cnmTOrdinanza = cnmTOrdinanza;
	}

	public CnmTOrdinanza getCnmTOrdinanzaFiglio() {
		return cnmTOrdinanzaFiglio;
	}

	public void setCnmTOrdinanzaFiglio(CnmTOrdinanza cnmTOrdinanzaFiglio) {
		this.cnmTOrdinanzaFiglio = cnmTOrdinanzaFiglio;
	}

	
	
}
