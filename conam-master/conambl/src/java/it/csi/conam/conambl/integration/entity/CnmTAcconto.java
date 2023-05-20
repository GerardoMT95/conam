/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cnm_t_acconto")
@NamedQuery(name="CnmTAcconto.findAll", query="SELECT c FROM CnmTAcconto c")
public class CnmTAcconto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_acconto")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_pagamento_acconto")
	private Date dataPagamento;

	@Column(name = "importo_acconto")
	private BigDecimal importo;
	
	@ManyToOne
	@JoinColumn(name = "id_ordinanza")
	private CnmTOrdinanza cnmTOrdinanza;
	
	@ManyToOne
	@JoinColumn(name = "id_soggetto")
	private CnmTSoggetto cnmTSoggetto;

	@Column(name = "conto_corrente_versamento")
	private String contoCorrenteVersamento;

	@ManyToOne
	@JoinColumn(name="id_allegato")
	private CnmTAllegato cnmTAllegato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public CnmTOrdinanza getCnmTOrdinanza() {
		return cnmTOrdinanza;
	}

	public void setCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		this.cnmTOrdinanza = cnmTOrdinanza;
	}

	public String getContoCorrenteVersamento() {
		return contoCorrenteVersamento;
	}

	public void setContoCorrenteVersamento(String contoCorrenteVersamento) {
		this.contoCorrenteVersamento = contoCorrenteVersamento;
	}
	
	public CnmTAllegato getCnmTAllegato() {
		return cnmTAllegato;
	}

	public void setCnmTAllegato(CnmTAllegato cnmTAllegato) {
		this.cnmTAllegato = cnmTAllegato;
	}
	
	public CnmTSoggetto getCnmTSoggetto() {
		return cnmTSoggetto;
	}

	public void setCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		this.cnmTSoggetto = cnmTSoggetto;
	}
}
