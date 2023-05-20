/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_allegato_verbale database table.
 * 
 */
@Entity
@Table(name="cnm_r_allegato_verbale")
@NamedQuery(name="CnmRAllegatoVerbale.findAll", query="SELECT c FROM CnmRAllegatoVerbale c")
public class CnmRAllegatoVerbale implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRAllegatoVerbalePK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato", insertable = false, updatable = false)
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	//bi-directional many-to-one association to CnmTVerbale
	@ManyToOne
	@JoinColumn(name="id_verbale", insertable = false, updatable = false)
	private CnmTVerbale cnmTVerbale;

	public CnmRAllegatoVerbale() {
	}

	public CnmRAllegatoVerbalePK getId() {
		return this.id;
	}

	public void setId(CnmRAllegatoVerbalePK id) {
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
