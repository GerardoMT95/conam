/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_allegato_ordinanza database table.
 * 
 */
@Entity
@Table(name="cnm_r_allegato_ordinanza")
@NamedQuery(name="CnmRAllegatoOrdinanza.findAll", query="SELECT c FROM CnmRAllegatoOrdinanza c")
public class CnmRAllegatoOrdinanza implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRAllegatoOrdinanzaPK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato", insertable = false, updatable = false)
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTOrdinanza
	@ManyToOne
	@JoinColumn(name="id_ordinanza", insertable = false, updatable = false)
	private CnmTOrdinanza cnmTOrdinanza;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	public CnmRAllegatoOrdinanza() {
	}

	public CnmRAllegatoOrdinanzaPK getId() {
		return this.id;
	}

	public void setId(CnmRAllegatoOrdinanzaPK id) {
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
