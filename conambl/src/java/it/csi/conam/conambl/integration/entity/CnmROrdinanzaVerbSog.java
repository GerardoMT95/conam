/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cnm_r_ordinanza_verb_sog database table.
 * 
 */
@Entity
@Table(name = "cnm_r_ordinanza_verb_sog")
@NamedQuery(name = "CnmROrdinanzaVerbSog.findAll", query = "SELECT c FROM CnmROrdinanzaVerbSog c")
public class CnmROrdinanzaVerbSog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ordinanza_verb_sog")
	private Integer idOrdinanzaVerbSog;

	@Column(name = "cod_avviso")
	private String codAvviso;

	@Column(name = "cod_esito_lista_carico")
	private String codEsitoListaCarico;

	@Column(name = "cod_iuv")
	private String codIuv;

	@Column(name = "cod_posizione_debitoria")
	private String codPosizioneDebitoria;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_pagamento")
	private Date dataPagamento;

	@Column(name = "importo_pagato")
	private BigDecimal importoPagato;

	// bi-directional many-to-one association to CnmRAllegatoOrdVerbSog
	@OneToMany(mappedBy = "cnmROrdinanzaVerbSog")
	private List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs;

	// bi-directional many-to-one association to CnmDStatoOrdVerbSog
	@ManyToOne
	@JoinColumn(name = "id_stato_ord_verb_sog")
	private CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog;

	// bi-directional many-to-one association to CnmRVerbaleSoggetto
	@ManyToOne
	@JoinColumn(name = "id_verbale_soggetto")
	private CnmRVerbaleSoggetto cnmRVerbaleSoggetto;

	// bi-directional many-to-one association to CnmTOrdinanza
	@ManyToOne
	@JoinColumn(name = "id_ordinanza")
	private CnmTOrdinanza cnmTOrdinanza;
	
	// bi-directional many-to-one association to CnmRSollecitoSoggRata
	@OneToMany(mappedBy = "cnmROrdinanzaVerbSog")
	private List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRatas;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;

	// bi-directional many-to-one association to CnmRSoggRata
	@OneToMany(mappedBy = "cnmROrdinanzaVerbSog")
	private List<CnmRSoggRata> cnmRSoggRatas;

	// bi-directional one-to-one association to CnmTRiscossione
	@OneToOne(mappedBy = "cnmROrdinanzaVerbSog")
	private CnmTRiscossione cnmTRiscossione;

	// bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy = "cnmROrdinanzaVerbSog")
	private List<CnmTSollecito> cnmTSollecitos;

	public CnmROrdinanzaVerbSog() {
	}

	public Integer getIdOrdinanzaVerbSog() {
		return this.idOrdinanzaVerbSog;
	}

	public void setIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog) {
		this.idOrdinanzaVerbSog = idOrdinanzaVerbSog;
	}

	public String getCodAvviso() {
		return this.codAvviso;
	}

	public void setCodAvviso(String codAvviso) {
		this.codAvviso = codAvviso;
	}

	public String getCodEsitoListaCarico() {
		return this.codEsitoListaCarico;
	}

	public void setCodEsitoListaCarico(String codEsitoListaCarico) {
		this.codEsitoListaCarico = codEsitoListaCarico;
	}

	public String getCodIuv() {
		return this.codIuv;
	}

	public void setCodIuv(String codIuv) {
		this.codIuv = codIuv;
	}

	public String getCodPosizioneDebitoria() {
		return this.codPosizioneDebitoria;
	}

	public void setCodPosizioneDebitoria(String codPosizioneDebitoria) {
		this.codPosizioneDebitoria = codPosizioneDebitoria;
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

	public Date getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getImportoPagato() {
		return this.importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public List<CnmRAllegatoOrdVerbSog> getCnmRAllegatoOrdVerbSogs() {
		return this.cnmRAllegatoOrdVerbSogs;
	}

	public void setCnmRAllegatoOrdVerbSogs(List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs) {
		this.cnmRAllegatoOrdVerbSogs = cnmRAllegatoOrdVerbSogs;
	}

	public CnmRAllegatoOrdVerbSog addCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().add(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmROrdinanzaVerbSog(this);

		return cnmRAllegatoOrdVerbSog;
	}

	public CnmRAllegatoOrdVerbSog removeCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().remove(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmROrdinanzaVerbSog(null);

		return cnmRAllegatoOrdVerbSog;
	}

	public CnmDStatoOrdVerbSog getCnmDStatoOrdVerbSog() {
		return this.cnmDStatoOrdVerbSog;
	}

	public void setCnmDStatoOrdVerbSog(CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog) {
		this.cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSog;
	}

	public CnmRVerbaleSoggetto getCnmRVerbaleSoggetto() {
		return this.cnmRVerbaleSoggetto;
	}

	public void setCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		this.cnmRVerbaleSoggetto = cnmRVerbaleSoggetto;
	}

	public CnmTOrdinanza getCnmTOrdinanza() {
		return this.cnmTOrdinanza;
	}

	public void setCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		this.cnmTOrdinanza = cnmTOrdinanza;
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

	public List<CnmRSoggRata> getCnmRSoggRatas() {
		return this.cnmRSoggRatas;
	}

	public void setCnmRSoggRatas(List<CnmRSoggRata> cnmRSoggRatas) {
		this.cnmRSoggRatas = cnmRSoggRatas;
	}

	public CnmRSoggRata addCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().add(cnmRSoggRata);
		cnmRSoggRata.setCnmROrdinanzaVerbSog(this);

		return cnmRSoggRata;
	}

	public CnmRSoggRata removeCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().remove(cnmRSoggRata);
		cnmRSoggRata.setCnmROrdinanzaVerbSog(null);

		return cnmRSoggRata;
	}

	public CnmTRiscossione getCnmTRiscossione() {
		return this.cnmTRiscossione;
	}

	public void setCnmTRiscossione(CnmTRiscossione cnmTRiscossione) {
		this.cnmTRiscossione = cnmTRiscossione;
	}

	public List<CnmTSollecito> getCnmTSollecitos() {
		return this.cnmTSollecitos;
	}

	public void setCnmTSollecitos(List<CnmTSollecito> cnmTSollecitos) {
		this.cnmTSollecitos = cnmTSollecitos;
	}

	public CnmTSollecito addCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().add(cnmTSollecito);
		cnmTSollecito.setCnmROrdinanzaVerbSog(this);

		return cnmTSollecito;
	}

	public CnmTSollecito removeCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().remove(cnmTSollecito);
		cnmTSollecito.setCnmROrdinanzaVerbSog(null);

		return cnmTSollecito;
	}

}
