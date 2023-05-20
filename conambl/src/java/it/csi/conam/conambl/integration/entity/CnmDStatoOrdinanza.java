/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_ordinanza database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_ordinanza")
@NamedQuery(name="CnmDStatoOrdinanza.findAll", query="SELECT c FROM CnmDStatoOrdinanza c")
public class CnmDStatoOrdinanza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_ordinanza")
	private long idStatoOrdinanza;

	@Column(name="desc_stato_ordinanza")
	private String descStatoOrdinanza;

	//bi-directional many-to-many association to CnmDTipoAllegato
	@ManyToMany(mappedBy="cnmDStatoOrdinanzas")
	private List<CnmDTipoAllegato> cnmDTipoAllegatos;

	//bi-directional many-to-one association to CnmSStatoOrdinanza
	@OneToMany(mappedBy="cnmDStatoOrdinanza")
	private List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas;

	//bi-directional many-to-one association to CnmTOrdinanza
	@OneToMany(mappedBy="cnmDStatoOrdinanza")
	private List<CnmTOrdinanza> cnmTOrdinanzas;

	public CnmDStatoOrdinanza() {
	}

	public long getIdStatoOrdinanza() {
		return this.idStatoOrdinanza;
	}

	public void setIdStatoOrdinanza(long idStatoOrdinanza) {
		this.idStatoOrdinanza = idStatoOrdinanza;
	}

	public String getDescStatoOrdinanza() {
		return this.descStatoOrdinanza;
	}

	public void setDescStatoOrdinanza(String descStatoOrdinanza) {
		this.descStatoOrdinanza = descStatoOrdinanza;
	}

	public List<CnmDTipoAllegato> getCnmDTipoAllegatos() {
		return this.cnmDTipoAllegatos;
	}

	public void setCnmDTipoAllegatos(List<CnmDTipoAllegato> cnmDTipoAllegatos) {
		this.cnmDTipoAllegatos = cnmDTipoAllegatos;
	}

	public List<CnmSStatoOrdinanza> getCnmSStatoOrdinanzas() {
		return this.cnmSStatoOrdinanzas;
	}

	public void setCnmSStatoOrdinanzas(List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas) {
		this.cnmSStatoOrdinanzas = cnmSStatoOrdinanzas;
	}

	public CnmSStatoOrdinanza addCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().add(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmDStatoOrdinanza(this);

		return cnmSStatoOrdinanza;
	}

	public CnmSStatoOrdinanza removeCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().remove(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmDStatoOrdinanza(null);

		return cnmSStatoOrdinanza;
	}

	public List<CnmTOrdinanza> getCnmTOrdinanzas() {
		return this.cnmTOrdinanzas;
	}

	public void setCnmTOrdinanzas(List<CnmTOrdinanza> cnmTOrdinanzas) {
		this.cnmTOrdinanzas = cnmTOrdinanzas;
	}

	public CnmTOrdinanza addCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		getCnmTOrdinanzas().add(cnmTOrdinanza);
		cnmTOrdinanza.setCnmDStatoOrdinanza(this);

		return cnmTOrdinanza;
	}

	public CnmTOrdinanza removeCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		getCnmTOrdinanzas().remove(cnmTOrdinanza);
		cnmTOrdinanza.setCnmDStatoOrdinanza(null);

		return cnmTOrdinanza;
	}

}
