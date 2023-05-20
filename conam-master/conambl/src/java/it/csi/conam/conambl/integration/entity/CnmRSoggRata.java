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


/**
 * The persistent class for the cnm_r_sogg_rata database table.
 * 
 */
@Entity
@Table(name="cnm_r_sogg_rata")
@NamedQuery(name="CnmRSoggRata.findAll", query="SELECT c FROM CnmRSoggRata c")
public class CnmRSoggRata implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CnmRSoggRataPK id;

	@Column(name="cod_avviso")
	private String codAvviso;

	@Column(name="cod_esito_lista_carico")
	private String codEsitoListaCarico;

	@Column(name="cod_iuv")
	private String codIuv;

	@Column(name="cod_posizione_debitoria")
	private String codPosizioneDebitoria;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name="data_pagamento")
	private Date dataPagamento;

	@Column(name="importo_pagato")
	private BigDecimal importoPagato;

	//bi-directional many-to-one association to CnmDStatoRata
	@ManyToOne
	@JoinColumn(name="id_stato_rata")
	private CnmDStatoRata cnmDStatoRata;

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

	public CnmRSoggRata() {
	}

	public CnmRSoggRataPK getId() {
		return this.id;
	}

	public void setId(CnmRSoggRataPK id) {
		this.id = id;
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

	public CnmDStatoRata getCnmDStatoRata() {
		return this.cnmDStatoRata;
	}

	public void setCnmDStatoRata(CnmDStatoRata cnmDStatoRata) {
		this.cnmDStatoRata = cnmDStatoRata;
	}

	public CnmROrdinanzaVerbSog getCnmROrdinanzaVerbSog() {
		return this.cnmROrdinanzaVerbSog;
	}

	public void setCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		this.cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSog;
	}

	public CnmTRata getCnmTRata() {
		return this.cnmTRata;
	}

	public void setCnmTRata(CnmTRata cnmTRata) {
		this.cnmTRata = cnmTRata;
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
