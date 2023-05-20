/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_allegato_sollecito database table.
 * 
 */
@Entity
@Table(name="cnm_r_allegato_sollecito")
@NamedQuery(name="CnmRAllegatoSollecito.findAll", query="SELECT c FROM CnmRAllegatoSollecito c")
public class CnmRAllegatoSollecito implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRAllegatoSollecitoPK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato", insertable = false, updatable = false)
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTSollecito
	@ManyToOne
	@JoinColumn(name="id_sollecito", insertable = false, updatable = false)
	private CnmTSollecito cnmTSollecito;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	public CnmRAllegatoSollecito() {
	}

	public CnmRAllegatoSollecitoPK getId() {
		return this.id;
	}

	public void setId(CnmRAllegatoSollecitoPK id) {
		this.id = id;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public CnmTAllegato getCnmTAllegato() {
		return this.cnmTAllegato;
	}

	public void setCnmTAllegato(CnmTAllegato cnmTAllegato) {
		this.cnmTAllegato = cnmTAllegato;
	}

	public CnmTSollecito getCnmTSollecito() {
		return this.cnmTSollecito;
	}

	public void setCnmTSollecito(CnmTSollecito cnmTSollecito) {
		this.cnmTSollecito = cnmTSollecito;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

}
