/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

import java.math.BigDecimal;
import java.util.Date;

// 20201125_LC
public class RicevutaPagamentoResponse extends ParentVO {

	private static final long serialVersionUID = 1L;

	private String identificativoSoggetto;
	private String nomeCognomeRagioneSociale;	
	
	private BigDecimal importoPagato;
	private	Date dataPagamento;
	private	String contoCorrenteVersamento;
	


	public String getIdentificativoSoggetto() {
		return identificativoSoggetto;
	}
	public void setIdentificativoSoggetto(String identificativoSoggetto) {
		this.identificativoSoggetto = identificativoSoggetto;
	}
	public String getNomeCognomeRagioneSociale() {
		return nomeCognomeRagioneSociale;
	}
	public void setNomeCognomeRagioneSociale(String nomeCognomeRagioneSociale) {
		this.nomeCognomeRagioneSociale = nomeCognomeRagioneSociale;
	}
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}
	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getContoCorrenteVersamento() {
		return contoCorrenteVersamento;
	}
	public void setContoCorrenteVersamento(String contoCorrenteVersamento) {
		this.contoCorrenteVersamento = contoCorrenteVersamento;
	}
	



}
