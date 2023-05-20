/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the cnm_d_stato_record database table.
 * 
 */
@Entity
@Table(name = "cnm_d_stato_record")
@NamedQuery(name = "CnmDStatoRecord.findAll", query = "SELECT c FROM CnmDStatoRecord c")
public class CnmDStatoRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_stato_record")
	private long idStatoRecord;

	@Column(name = "desc_stato_record")
	private String descStatoRecord;

	// bi-directional many-to-one association to CnmTRecordRitorno
	@OneToMany(mappedBy = "cnmDStatoRecord")
	private List<CnmTRecordRitorno> cnmTRecordRitornos;

	public CnmDStatoRecord() {
	}

	public long getIdStatoRecord() {
		return this.idStatoRecord;
	}

	public void setIdStatoRecord(long idStatoRecord) {
		this.idStatoRecord = idStatoRecord;
	}

	public String getDescStatoRecord() {
		return this.descStatoRecord;
	}

	public void setDescStatoRecord(String descStatoRecord) {
		this.descStatoRecord = descStatoRecord;
	}

	public List<CnmTRecordRitorno> getCnmTRecordRitornos() {
		return this.cnmTRecordRitornos;
	}

	public void setCnmTRecordRitornos(List<CnmTRecordRitorno> cnmTRecordRitornos) {
		this.cnmTRecordRitornos = cnmTRecordRitornos;
	}

	public CnmTRecordRitorno addCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().add(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmDStatoRecord(this);

		return cnmTRecordRitorno;
	}

	public CnmTRecordRitorno removeCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().remove(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmDStatoRecord(null);

		return cnmTRecordRitorno;
	}

}
