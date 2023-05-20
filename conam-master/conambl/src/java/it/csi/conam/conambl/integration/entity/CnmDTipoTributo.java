/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_tipo_tributo database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_tributo")
@NamedQuery(name="CnmDTipoTributo.findAll", query="SELECT c FROM CnmDTipoTributo c")
public class CnmDTipoTributo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_tributo")
	private long idTipoTributo;

	@Column(name="cod_tipo_tributo")
	private String codTipoTributo;

	@Column(name="desc_tipo_tributo")
	private String descTipoTributo;

	//bi-directional many-to-one association to CnmTRecordN4
	@OneToMany(mappedBy="cnmDTipoTributo")
	private List<CnmTRecordN4> cnmTRecordN4s;

	public CnmDTipoTributo() {
	}

	public long getIdTipoTributo() {
		return this.idTipoTributo;
	}

	public void setIdTipoTributo(long idTipoTributo) {
		this.idTipoTributo = idTipoTributo;
	}

	public String getCodTipoTributo() {
		return this.codTipoTributo;
	}

	public void setCodTipoTributo(String codTipoTributo) {
		this.codTipoTributo = codTipoTributo;
	}

	public String getDescTipoTributo() {
		return this.descTipoTributo;
	}

	public void setDescTipoTributo(String descTipoTributo) {
		this.descTipoTributo = descTipoTributo;
	}

	public List<CnmTRecordN4> getCnmTRecordN4s() {
		return this.cnmTRecordN4s;
	}

	public void setCnmTRecordN4s(List<CnmTRecordN4> cnmTRecordN4s) {
		this.cnmTRecordN4s = cnmTRecordN4s;
	}

	public CnmTRecordN4 addCnmTRecordN4(CnmTRecordN4 cnmTRecordN4) {
		getCnmTRecordN4s().add(cnmTRecordN4);
		cnmTRecordN4.setCnmDTipoTributo(this);

		return cnmTRecordN4;
	}

	public CnmTRecordN4 removeCnmTRecordN4(CnmTRecordN4 cnmTRecordN4) {
		getCnmTRecordN4s().remove(cnmTRecordN4);
		cnmTRecordN4.setCnmDTipoTributo(null);

		return cnmTRecordN4;
	}

}
