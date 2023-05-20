/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_tipo_allegato database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_allegato")
@NamedQuery(name="CnmDTipoAllegato.findAll", query="SELECT c FROM CnmDTipoAllegato c")
public class CnmDTipoAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_allegato")
	private long idTipoAllegato;

	@Column(name="desc_tipo_allegato")
	private String descTipoAllegato;
	
	// 20200714_LC
	@Column(name="index_type")
	private String indexType;	

	
	
	
	//bi-directional many-to-one association to CnmCField
	@OneToMany(mappedBy="cnmDTipoAllegato")
	private List<CnmCField> cnmCFields;

	//bi-directional many-to-one association to CnmDCategoriaAllegato
	@ManyToOne
	@JoinColumn(name="id_categoria_allegato")
	private CnmDCategoriaAllegato cnmDCategoriaAllegato;

	//bi-directional many-to-many association to CnmDStatoOrdinanza
	@ManyToMany
	@JoinTable(
		name="cnm_r_tipo_allegato_so"
		, joinColumns={
			@JoinColumn(name="id_tipo_allegato")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_stato_ordinanza")
			}
		)
	private List<CnmDStatoOrdinanza> cnmDStatoOrdinanzas;

	//bi-directional many-to-many association to CnmDStatoVerbale
	@ManyToMany
	@JoinTable(
		name="cnm_r_tipo_allegato_sv"
		, joinColumns={
			@JoinColumn(name="id_tipo_allegato")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_stato_verbale")
			}
		)
	private List<CnmDStatoVerbale> cnmDStatoVerbales;

	//bi-directional many-to-one association to CnmDUtilizzoAllegato
	@ManyToOne
	@JoinColumn(name="id_utilizzo_allegato")
	private CnmDUtilizzoAllegato cnmDUtilizzoAllegato;

	//bi-directional many-to-one association to CnmTAllegato
	@OneToMany(mappedBy="cnmDTipoAllegato")
	private List<CnmTAllegato> cnmTAllegatos;

	public CnmDTipoAllegato() {
	}

	public long getIdTipoAllegato() {
		return this.idTipoAllegato;
	}

	public void setIdTipoAllegato(long idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}

	public String getDescTipoAllegato() {
		return this.descTipoAllegato;
	}

	public void setDescTipoAllegato(String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}

	public List<CnmCField> getCnmCFields() {
		return this.cnmCFields;
	}

	public void setCnmCFields(List<CnmCField> cnmCFields) {
		this.cnmCFields = cnmCFields;
	}

	public CnmCField addCnmCField(CnmCField cnmCField) {
		getCnmCFields().add(cnmCField);
		cnmCField.setCnmDTipoAllegato(this);

		return cnmCField;
	}

	public CnmCField removeCnmCField(CnmCField cnmCField) {
		getCnmCFields().remove(cnmCField);
		cnmCField.setCnmDTipoAllegato(null);

		return cnmCField;
	}

	public CnmDCategoriaAllegato getCnmDCategoriaAllegato() {
		return this.cnmDCategoriaAllegato;
	}

	public void setCnmDCategoriaAllegato(CnmDCategoriaAllegato cnmDCategoriaAllegato) {
		this.cnmDCategoriaAllegato = cnmDCategoriaAllegato;
	}

	public List<CnmDStatoOrdinanza> getCnmDStatoOrdinanzas() {
		return this.cnmDStatoOrdinanzas;
	}

	public void setCnmDStatoOrdinanzas(List<CnmDStatoOrdinanza> cnmDStatoOrdinanzas) {
		this.cnmDStatoOrdinanzas = cnmDStatoOrdinanzas;
	}

	public List<CnmDStatoVerbale> getCnmDStatoVerbales() {
		return this.cnmDStatoVerbales;
	}

	public void setCnmDStatoVerbales(List<CnmDStatoVerbale> cnmDStatoVerbales) {
		this.cnmDStatoVerbales = cnmDStatoVerbales;
	}

	public CnmDUtilizzoAllegato getCnmDUtilizzoAllegato() {
		return this.cnmDUtilizzoAllegato;
	}

	public void setCnmDUtilizzoAllegato(CnmDUtilizzoAllegato cnmDUtilizzoAllegato) {
		this.cnmDUtilizzoAllegato = cnmDUtilizzoAllegato;
	}

	public List<CnmTAllegato> getCnmTAllegatos() {
		return this.cnmTAllegatos;
	}

	public void setCnmTAllegatos(List<CnmTAllegato> cnmTAllegatos) {
		this.cnmTAllegatos = cnmTAllegatos;
	}

	public CnmTAllegato addCnmTAllegato(CnmTAllegato cnmTAllegato) {
		getCnmTAllegatos().add(cnmTAllegato);
		cnmTAllegato.setCnmDTipoAllegato(this);

		return cnmTAllegato;
	}

	public CnmTAllegato removeCnmTAllegato(CnmTAllegato cnmTAllegato) {
		getCnmTAllegatos().remove(cnmTAllegato);
		cnmTAllegato.setCnmDTipoAllegato(null);

		return cnmTAllegato;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	
	
	
	
}
