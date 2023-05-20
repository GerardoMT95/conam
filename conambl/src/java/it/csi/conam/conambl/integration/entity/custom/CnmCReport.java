/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity.custom;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the cnm_c_report database table.
 * 
 */
@Entity
@Table(name = "cnm_c_report")
@NamedQuery(name = "CnmCReport.findAll", query = "SELECT c FROM CnmCReport c")
public class CnmCReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_report")
	private long idReport;

	@Column(name = "cod_report")
	private String codReport;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name = "desc_report")
	private String descReport;

	private byte[] jasper;

	@ColumnTransformer(read = "jrxml", write = "xml(?)")
	@Column(nullable = false, columnDefinition = "XMLType")
	private String jrxml;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser;

	// bi-directional many-to-many association to CnmCImmagine
	@ManyToMany
	@JoinTable(name = "cnm_c_immagine_report", joinColumns = { @JoinColumn(name = "id_report") }, inverseJoinColumns = { @JoinColumn(name = "id_immagine") })
	private List<CnmCImmagine> cnmCImmagines;

	public CnmCReport() {
	}

	public long getIdReport() {
		return this.idReport;
	}

	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}

	public String getCodReport() {
		return this.codReport;
	}

	public void setCodReport(String codReport) {
		this.codReport = codReport;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getDescReport() {
		return this.descReport;
	}

	public void setDescReport(String descReport) {
		this.descReport = descReport;
	}

	public byte[] getJasper() {
		return this.jasper;
	}

	public void setJasper(byte[] jasper) {
		this.jasper = jasper;
	}

	public String getJrxml() {
		return this.jrxml;
	}

	public void setJrxml(String jrxml) {
		this.jrxml = jrxml;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

	public List<CnmCImmagine> getCnmCImmagines() {
		return this.cnmCImmagines;
	}

	public void setCnmCImmagines(List<CnmCImmagine> cnmCImmagines) {
		this.cnmCImmagines = cnmCImmagines;
	}

}
