/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_allegato_piano_rate database table.
 * 
 */
@Entity
@Table(name="cnm_r_allegato_piano_rate")
@NamedQuery(name="CnmRAllegatoPianoRate.findAll", query="SELECT c FROM CnmRAllegatoPianoRate c")
public class CnmRAllegatoPianoRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRAllegatoPianoRatePK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato", insertable = false, updatable = false)
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTPianoRate
	@ManyToOne
	@JoinColumn(name="id_piano_rate", insertable = false, updatable = false)
	private CnmTPianoRate cnmTPianoRate;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	public CnmRAllegatoPianoRate() {
	}

	public CnmRAllegatoPianoRatePK getId() {
		return this.id;
	}

	public void setId(CnmRAllegatoPianoRatePK id) {
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

	public CnmTPianoRate getCnmTPianoRate() {
		return this.cnmTPianoRate;
	}

	public void setCnmTPianoRate(CnmTPianoRate cnmTPianoRate) {
		this.cnmTPianoRate = cnmTPianoRate;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

}
