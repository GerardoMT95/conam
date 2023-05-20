/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_s_stato_verbale database table.
 * 
 */
@Entity
@Table(name="cnm_s_stato_verbale")
@NamedQuery(name="CnmSStatoVerbale.findAll", query="SELECT c FROM CnmSStatoVerbale c")
public class CnmSStatoVerbale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_stato_verbale")
	private Integer idSStatoVerbale;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmDStatoVerbale
	@ManyToOne
	@JoinColumn(name="id_stato_verbale")
	private CnmDStatoVerbale cnmDStatoVerbale;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	//bi-directional many-to-one association to CnmTVerbale
	@ManyToOne
	@JoinColumn(name="id_verbale")
	private CnmTVerbale cnmTVerbale;

	public CnmSStatoVerbale() {
	}

	public Integer getIdSStatoVerbale() {
		return this.idSStatoVerbale;
	}

	public void setIdSStatoVerbale(Integer idSStatoVerbale) {
		this.idSStatoVerbale = idSStatoVerbale;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public CnmDStatoVerbale getCnmDStatoVerbale() {
		return this.cnmDStatoVerbale;
	}

	public void setCnmDStatoVerbale(CnmDStatoVerbale cnmDStatoVerbale) {
		this.cnmDStatoVerbale = cnmDStatoVerbale;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

	public CnmTVerbale getCnmTVerbale() {
		return this.cnmTVerbale;
	}

	public void setCnmTVerbale(CnmTVerbale cnmTVerbale) {
		this.cnmTVerbale = cnmTVerbale;
	}

}
