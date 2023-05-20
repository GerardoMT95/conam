/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_allegato_verb_sog database table.
 * 
 */
@Entity
@Table(name="cnm_r_allegato_verb_sog")
@NamedQuery(name="CnmRAllegatoVerbSog.findAll", query="SELECT c FROM CnmRAllegatoVerbSog c")
public class CnmRAllegatoVerbSog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRAllegatoVerbSogPK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmRVerbaleSoggetto
	@ManyToOne
	@JoinColumn(name="id_verbale_soggetto", insertable = false, updatable = false)
	private CnmRVerbaleSoggetto cnmRVerbaleSoggetto;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato", insertable = false, updatable = false)
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	public CnmRAllegatoVerbSog() {
	}

	public CnmRAllegatoVerbSogPK getId() {
		return this.id;
	}

	public void setId(CnmRAllegatoVerbSogPK id) {
		this.id = id;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public CnmRVerbaleSoggetto getCnmRVerbaleSoggetto() {
		return this.cnmRVerbaleSoggetto;
	}

	public void setCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		this.cnmRVerbaleSoggetto = cnmRVerbaleSoggetto;
	}

	public CnmTAllegato getCnmTAllegato() {
		return this.cnmTAllegato;
	}

	public void setCnmTAllegato(CnmTAllegato cnmTAllegato) {
		this.cnmTAllegato = cnmTAllegato;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

}
