/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_s_stato_ordinanza database table.
 * 
 */
@Entity
@Table(name="cnm_s_stato_ordinanza")
@NamedQuery(name="CnmSStatoOrdinanza.findAll", query="SELECT c FROM CnmSStatoOrdinanza c")
public class CnmSStatoOrdinanza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_stato_ordinanza")
	private Integer idSStatoOrdinanza;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmDStatoOrdinanza
	@ManyToOne
	@JoinColumn(name="id_stato_ordinanza")
	private CnmDStatoOrdinanza cnmDStatoOrdinanza;

	//bi-directional many-to-one association to CnmTOrdinanza
	@ManyToOne
	@JoinColumn(name="id_ordinanza")
	private CnmTOrdinanza cnmTOrdinanza;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	public CnmSStatoOrdinanza() {
	}

	public Integer getIdSStatoOrdinanza() {
		return this.idSStatoOrdinanza;
	}

	public void setIdSStatoOrdinanza(Integer idSStatoOrdinanza) {
		this.idSStatoOrdinanza = idSStatoOrdinanza;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public CnmDStatoOrdinanza getCnmDStatoOrdinanza() {
		return this.cnmDStatoOrdinanza;
	}

	public void setCnmDStatoOrdinanza(CnmDStatoOrdinanza cnmDStatoOrdinanza) {
		this.cnmDStatoOrdinanza = cnmDStatoOrdinanza;
	}

	public CnmTOrdinanza getCnmTOrdinanza() {
		return this.cnmTOrdinanza;
	}

	public void setCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		this.cnmTOrdinanza = cnmTOrdinanza;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

}
