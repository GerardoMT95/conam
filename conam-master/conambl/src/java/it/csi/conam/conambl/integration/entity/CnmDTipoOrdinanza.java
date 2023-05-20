/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_tipo_ordinanza database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_ordinanza")
@NamedQuery(name="CnmDTipoOrdinanza.findAll", query="SELECT c FROM CnmDTipoOrdinanza c")
public class CnmDTipoOrdinanza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_ordinanza")
	private long idTipoOrdinanza;

	@Column(name="desc_tipo_ordinanza")
	private String descTipoOrdinanza;

	//bi-directional many-to-one association to CnmTOrdinanza
	@OneToMany(mappedBy="cnmDTipoOrdinanza")
	private List<CnmTOrdinanza> cnmTOrdinanzas;

	public CnmDTipoOrdinanza() {
	}

	public long getIdTipoOrdinanza() {
		return this.idTipoOrdinanza;
	}

	public void setIdTipoOrdinanza(long idTipoOrdinanza) {
		this.idTipoOrdinanza = idTipoOrdinanza;
	}

	public String getDescTipoOrdinanza() {
		return this.descTipoOrdinanza;
	}

	public void setDescTipoOrdinanza(String descTipoOrdinanza) {
		this.descTipoOrdinanza = descTipoOrdinanza;
	}

	public List<CnmTOrdinanza> getCnmTOrdinanzas() {
		return this.cnmTOrdinanzas;
	}

	public void setCnmTOrdinanzas(List<CnmTOrdinanza> cnmTOrdinanzas) {
		this.cnmTOrdinanzas = cnmTOrdinanzas;
	}

	public CnmTOrdinanza addCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		getCnmTOrdinanzas().add(cnmTOrdinanza);
		cnmTOrdinanza.setCnmDTipoOrdinanza(this);

		return cnmTOrdinanza;
	}

	public CnmTOrdinanza removeCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		getCnmTOrdinanzas().remove(cnmTOrdinanza);
		cnmTOrdinanza.setCnmDTipoOrdinanza(null);

		return cnmTOrdinanza;
	}

}
