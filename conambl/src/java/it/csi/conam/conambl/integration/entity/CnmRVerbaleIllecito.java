/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_verbale_illecito database table.
 * 
 */
@Entity
@Table(name="cnm_r_verbale_illecito")
@NamedQuery(name="CnmRVerbaleIllecito.findAll", query="SELECT c FROM CnmRVerbaleIllecito c")
public class CnmRVerbaleIllecito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_verbale_illecito")
	private Integer idVerbaleIllecito;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	//bi-directional many-to-one association to CnmDLettera
	@ManyToOne
	@JoinColumn(name="id_lettera")
	private CnmDLettera cnmDLettera;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmTVerbale
	@ManyToOne
	@JoinColumn(name="id_verbale")
	private CnmTVerbale cnmTVerbale;

	public CnmRVerbaleIllecito() {
	}

	public Integer getIdVerbaleIllecito() {
		return this.idVerbaleIllecito;
	}

	public void setIdVerbaleIllecito(Integer idVerbaleIllecito) {
		this.idVerbaleIllecito = idVerbaleIllecito;
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

	public CnmDLettera getCnmDLettera() {
		return this.cnmDLettera;
	}

	public void setCnmDLettera(CnmDLettera cnmDLettera) {
		this.cnmDLettera = cnmDLettera;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public CnmTVerbale getCnmTVerbale() {
		return this.cnmTVerbale;
	}

	public void setCnmTVerbale(CnmTVerbale cnmTVerbale) {
		this.cnmTVerbale = cnmTVerbale;
	}

}
