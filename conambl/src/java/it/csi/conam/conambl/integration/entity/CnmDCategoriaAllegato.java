/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_categoria_allegato database table.
 * 
 */
@Entity
@Table(name="cnm_d_categoria_allegato")
@NamedQuery(name="CnmDCategoriaAllegato.findAll", query="SELECT c FROM CnmDCategoriaAllegato c")
public class CnmDCategoriaAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria_allegato")
	private long idCategoriaAllegato;

	@Column(name="desc_categoria_allegato")
	private String descCategoriaAllegato;

	//bi-directional many-to-one association to CnmDTipoAllegato
	@OneToMany(mappedBy="cnmDCategoriaAllegato")
	private List<CnmDTipoAllegato> cnmDTipoAllegatos;

	public CnmDCategoriaAllegato() {
	}

	public long getIdCategoriaAllegato() {
		return this.idCategoriaAllegato;
	}

	public void setIdCategoriaAllegato(long idCategoriaAllegato) {
		this.idCategoriaAllegato = idCategoriaAllegato;
	}

	public String getDescCategoriaAllegato() {
		return this.descCategoriaAllegato;
	}

	public void setDescCategoriaAllegato(String descCategoriaAllegato) {
		this.descCategoriaAllegato = descCategoriaAllegato;
	}

	public List<CnmDTipoAllegato> getCnmDTipoAllegatos() {
		return this.cnmDTipoAllegatos;
	}

	public void setCnmDTipoAllegatos(List<CnmDTipoAllegato> cnmDTipoAllegatos) {
		this.cnmDTipoAllegatos = cnmDTipoAllegatos;
	}

	public CnmDTipoAllegato addCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		getCnmDTipoAllegatos().add(cnmDTipoAllegato);
		cnmDTipoAllegato.setCnmDCategoriaAllegato(this);

		return cnmDTipoAllegato;
	}

	public CnmDTipoAllegato removeCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		getCnmDTipoAllegatos().remove(cnmDTipoAllegato);
		cnmDTipoAllegato.setCnmDCategoriaAllegato(null);

		return cnmDTipoAllegato;
	}

}
