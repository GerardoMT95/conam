/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_elenco database table.
 * 
 */
@Entity
@Table(name="cnm_d_elenco")
@NamedQuery(name="CnmDElenco.findAll", query="SELECT c FROM CnmDElenco c")
public class CnmDElenco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_elenco")
	private long idElenco;

	@Column(name="desc_elenco")
	private String descElenco;

	//bi-directional many-to-one association to CnmCField
	@OneToMany(mappedBy="cnmDElenco")
	private List<CnmCField> cnmCFields;

	public CnmDElenco() {
	}

	public long getIdElenco() {
		return this.idElenco;
	}

	public void setIdElenco(long idElenco) {
		this.idElenco = idElenco;
	}

	public String getDescElenco() {
		return this.descElenco;
	}

	public void setDescElenco(String descElenco) {
		this.descElenco = descElenco;
	}

	public List<CnmCField> getCnmCFields() {
		return this.cnmCFields;
	}

	public void setCnmCFields(List<CnmCField> cnmCFields) {
		this.cnmCFields = cnmCFields;
	}

	public CnmCField addCnmCField(CnmCField cnmCField) {
		getCnmCFields().add(cnmCField);
		cnmCField.setCnmDElenco(this);

		return cnmCField;
	}

	public CnmCField removeCnmCField(CnmCField cnmCField) {
		getCnmCFields().remove(cnmCField);
		cnmCField.setCnmDElenco(null);

		return cnmCField;
	}

}
