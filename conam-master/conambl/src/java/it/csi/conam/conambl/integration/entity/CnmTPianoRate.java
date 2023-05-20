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
 * The persistent class for the cnm_t_piano_rate database table.
 * 
 */
@Entity
@Table(name="cnm_t_piano_rate")
@NamedQuery(name="CnmTPianoRate.findAll", query="SELECT c FROM CnmTPianoRate c")
public class CnmTPianoRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_piano_rate")
	private Integer idPianoRate;

	@Column(name="cod_messaggio_epay")
	private String codMessaggioEpay;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_protocollo")
	private Timestamp dataOraProtocollo;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="id_index")
	private String idIndex;

	@Column(name="importo_sanzione")
	private BigDecimal importoSanzione;

	@Column(name="importo_spese_notifica")
	private BigDecimal importoSpeseNotifica;

	@Column(name="importo_spese_processuali")
	private BigDecimal importoSpeseProcessuali;

	@Column(name="numero_protocollo")
	private String numeroProtocollo;

	@Column(name="numero_rate")
	private BigDecimal numeroRate;
	
	@Column(name="importo_maggiorazione")
	private BigDecimal importoMaggiorazione;
	
	// 20201117 PP
	@Column(name="flag_documento_pregresso")
	private boolean flagDocumentoPregresso;

	//bi-directional many-to-one association to CnmRAllegatoPianoRate
	@OneToMany(mappedBy="cnmTPianoRate")
	private List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates;

	//bi-directional many-to-one association to CnmDStatoPianoRate
	@ManyToOne
	@JoinColumn(name="id_stato_piano_rate")
	private CnmDStatoPianoRate cnmDStatoPianoRate;

	//bi-directional many-to-many association to CnmTNotifica
	@ManyToMany
	@JoinTable(
		name="cnm_r_notifica_piano_rate"
		, joinColumns={
			@JoinColumn(name="id_piano_rate")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_notifica")
			}
		)
	private List<CnmTNotifica> cnmTNotificas;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmTRata
	@OneToMany(mappedBy="cnmTPianoRate")
	private List<CnmTRata> cnmTRatas;
	


	public CnmTPianoRate() {
	}

	public Integer getIdPianoRate() {
		return this.idPianoRate;
	}

	public void setIdPianoRate(Integer idPianoRate) {
		this.idPianoRate = idPianoRate;
	}

	public String getCodMessaggioEpay() {
		return this.codMessaggioEpay;
	}

	public void setCodMessaggioEpay(String codMessaggioEpay) {
		this.codMessaggioEpay = codMessaggioEpay;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraProtocollo() {
		return this.dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getIdIndex() {
		return this.idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}

	public BigDecimal getImportoSanzione() {
		return this.importoSanzione;
	}

	public void setImportoSanzione(BigDecimal importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return this.importoSpeseNotifica;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public BigDecimal getImportoSpeseProcessuali() {
		return this.importoSpeseProcessuali;
	}

	public void setImportoSpeseProcessuali(BigDecimal importoSpeseProcessuali) {
		this.importoSpeseProcessuali = importoSpeseProcessuali;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public BigDecimal getNumeroRate() {
		return this.numeroRate;
	}

	public void setNumeroRate(BigDecimal numeroRate) {
		this.numeroRate = numeroRate;
	}

	public List<CnmRAllegatoPianoRate> getCnmRAllegatoPianoRates() {
		return this.cnmRAllegatoPianoRates;
	}

	public void setCnmRAllegatoPianoRates(List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates) {
		this.cnmRAllegatoPianoRates = cnmRAllegatoPianoRates;
	}

	public CnmRAllegatoPianoRate addCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().add(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTPianoRate(this);

		return cnmRAllegatoPianoRate;
	}

	public CnmRAllegatoPianoRate removeCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().remove(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTPianoRate(null);

		return cnmRAllegatoPianoRate;
	}

	public CnmDStatoPianoRate getCnmDStatoPianoRate() {
		return this.cnmDStatoPianoRate;
	}

	public void setCnmDStatoPianoRate(CnmDStatoPianoRate cnmDStatoPianoRate) {
		this.cnmDStatoPianoRate = cnmDStatoPianoRate;
	}

	public List<CnmTNotifica> getCnmTNotificas() {
		return this.cnmTNotificas;
	}

	public void setCnmTNotificas(List<CnmTNotifica> cnmTNotificas) {
		this.cnmTNotificas = cnmTNotificas;
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

	public List<CnmTRata> getCnmTRatas() {
		return this.cnmTRatas;
	}

	public void setCnmTRatas(List<CnmTRata> cnmTRatas) {
		this.cnmTRatas = cnmTRatas;
	}

	public CnmTRata addCnmTRata(CnmTRata cnmTRata) {
		getCnmTRatas().add(cnmTRata);
		cnmTRata.setCnmTPianoRate(this);

		return cnmTRata;
	}

	public CnmTRata removeCnmTRata(CnmTRata cnmTRata) {
		getCnmTRatas().remove(cnmTRata);
		cnmTRata.setCnmTPianoRate(null);

		return cnmTRata;
	}
	
	public BigDecimal getImportoMaggiorazione() {
		return importoMaggiorazione;
	}

	public void setImportoMaggiorazione(BigDecimal importoMaggiorazione) {
		this.importoMaggiorazione = importoMaggiorazione;
	}

	public boolean isFlagDocumentoPregresso() {
		return flagDocumentoPregresso;
	}

	public void setFlagDocumentoPregresso(boolean flagDocumentoPregresso) {
		this.flagDocumentoPregresso = flagDocumentoPregresso;
	}

}
