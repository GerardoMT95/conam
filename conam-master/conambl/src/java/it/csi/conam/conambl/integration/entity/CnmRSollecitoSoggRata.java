/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_r_sollecito_sogg_rata database table.
 * 
 */
@Entity
@Table(name="cnm_r_sollecito_sogg_rata")
@NamedQuery(name="CnmRSollecitoSoggRata.findAll", query="SELECT c FROM CnmRSollecitoSoggRata c")
public class CnmRSollecitoSoggRata implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRSollecitoSoggRataPK id;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	//bi-directional many-to-one association to CnmTSollecito
	@ManyToOne
	@JoinColumn(name="id_sollecito", insertable = false, updatable = false)
	private CnmTSollecito cnmTSollecito;

	//bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@ManyToOne
	@JoinColumn(name="id_ordinanza_verb_sog", insertable = false, updatable = false)
	private CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog;

	//bi-directional many-to-one association to CnmTRata
	@ManyToOne
	@JoinColumn(name="id_rata", insertable = false, updatable = false)
	private CnmTRata cnmTRata;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser2;

	public CnmRSollecitoSoggRata() {
	}

	public CnmRSollecitoSoggRataPK getId() {
		return id;
	}

	public void setId(CnmRSollecitoSoggRataPK id) {
		this.id = id;
	}

	public Timestamp getDataOraInsert() {
		return dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public CnmTSollecito getCnmTSollecito() {
		return cnmTSollecito;
	}

	public void setCnmTSollecito(CnmTSollecito cnmTSollecito) {
		this.cnmTSollecito = cnmTSollecito;
	}

	public CnmROrdinanzaVerbSog getCnmROrdinanzaVerbSog() {
		return cnmROrdinanzaVerbSog;
	}

	public void setCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		this.cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSog;
	}

	public CnmTRata getCnmTRata() {
		return cnmTRata;
	}

	public void setCnmTRata(CnmTRata cnmTRata) {
		this.cnmTRata = cnmTRata;
	}

	public CnmTUser getCnmTUser1() {
		return cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}
	
	
	
}
