/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_c_field_type database table.
 * 
 */
@Entity
@Table(name="cnm_c_field_type")
@NamedQuery(name="CnmCFieldType.findAll", query="SELECT c FROM CnmCFieldType c")
public class CnmCFieldType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_field_type")
	private long idFieldType;

	@Column(name="desc_field_type")
	private String descFieldType;

	//bi-directional many-to-one association to CnmCField
	@OneToMany(mappedBy="cnmCFieldType")
	private List<CnmCField> cnmCFields;

	public CnmCFieldType() {
	}

	public long getIdFieldType() {
		return this.idFieldType;
	}

	public void setIdFieldType(long idFieldType) {
		this.idFieldType = idFieldType;
	}

	public String getDescFieldType() {
		return this.descFieldType;
	}

	public void setDescFieldType(String descFieldType) {
		this.descFieldType = descFieldType;
	}

	public List<CnmCField> getCnmCFields() {
		return this.cnmCFields;
	}

	public void setCnmCFields(List<CnmCField> cnmCFields) {
		this.cnmCFields = cnmCFields;
	}

	public CnmCField addCnmCField(CnmCField cnmCField) {
		getCnmCFields().add(cnmCField);
		cnmCField.setCnmCFieldType(this);

		return cnmCField;
	}

	public CnmCField removeCnmCField(CnmCField cnmCField) {
		getCnmCFields().remove(cnmCField);
		cnmCField.setCnmCFieldType(null);

		return cnmCField;
	}

}
