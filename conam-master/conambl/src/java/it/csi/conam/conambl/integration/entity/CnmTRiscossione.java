/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cnm_t_riscossione database table.
 * 
 */
@Entity
@Table(name="cnm_t_riscossione")
@NamedQuery(name="CnmTRiscossione.findAll", query="SELECT c FROM CnmTRiscossione c")
public class CnmTRiscossione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_riscossione")
	private Integer idRiscossione;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="importo_riscosso")
	private BigDecimal importoRiscosso;

	@Column(name="importo_sanzione")
	private BigDecimal importoSanzione;

	@Column(name="importo_spese_legali")
	private BigDecimal importoSpeseLegali;

	@Column(name="importo_spese_notifica")
	private BigDecimal importoSpeseNotifica;

	//bi-directional many-to-one association to CnmTRecord
	@OneToMany(mappedBy="cnmTRiscossione")
	private List<CnmTRecord> cnmTRecords;

	//bi-directional many-to-one association to CnmDStatoRiscossione
	@ManyToOne
	@JoinColumn(name="id_stato_riscossione")
	private CnmDStatoRiscossione cnmDStatoRiscossione;

	//bi-directional one-to-one association to CnmROrdinanzaVerbSog
	@OneToOne
	@JoinColumn(name="id_ordinanza_verb_sog")
	private CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	public CnmTRiscossione() {
	}

	public Integer getIdRiscossione() {
		return this.idRiscossione;
	}

	public void setIdRiscossione(Integer idRiscossione) {
		this.idRiscossione = idRiscossione;
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

	public BigDecimal getImportoRiscosso() {
		return this.importoRiscosso;
	}

	public void setImportoRiscosso(BigDecimal importoRiscosso) {
		this.importoRiscosso = importoRiscosso;
	}

	public BigDecimal getImportoSanzione() {
		return this.importoSanzione;
	}

	public void setImportoSanzione(BigDecimal importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	public BigDecimal getImportoSpeseLegali() {
		return this.importoSpeseLegali;
	}

	public void setImportoSpeseLegali(BigDecimal importoSpeseLegali) {
		this.importoSpeseLegali = importoSpeseLegali;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return this.importoSpeseNotifica;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public List<CnmTRecord> getCnmTRecords() {
		return this.cnmTRecords;
	}

	public void setCnmTRecords(List<CnmTRecord> cnmTRecords) {
		this.cnmTRecords = cnmTRecords;
	}

	public CnmTRecord addCnmTRecord(CnmTRecord cnmTRecord) {
		getCnmTRecords().add(cnmTRecord);
		cnmTRecord.setCnmTRiscossione(this);

		return cnmTRecord;
	}

	public CnmTRecord removeCnmTRecord(CnmTRecord cnmTRecord) {
		getCnmTRecords().remove(cnmTRecord);
		cnmTRecord.setCnmTRiscossione(null);

		return cnmTRecord;
	}

	public CnmDStatoRiscossione getCnmDStatoRiscossione() {
		return this.cnmDStatoRiscossione;
	}

	public void setCnmDStatoRiscossione(CnmDStatoRiscossione cnmDStatoRiscossione) {
		this.cnmDStatoRiscossione = cnmDStatoRiscossione;
	}

	public CnmROrdinanzaVerbSog getCnmROrdinanzaVerbSog() {
		return this.cnmROrdinanzaVerbSog;
	}

	public void setCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		this.cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSog;
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

}
