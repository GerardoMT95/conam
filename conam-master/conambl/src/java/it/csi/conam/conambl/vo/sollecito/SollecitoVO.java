/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.sollecito;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 12 feb 2019
 */
public class SollecitoVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Integer idSollecito;
	private String numeroProtocollo;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataScadenza;
	private BigDecimal importoSollecitato;
	private BigDecimal maggiorazione;
	private StatoSollecitoVO statoSollecito;
	private Integer idSoggettoOrdinanza;
	public Boolean bollettinoDaCreare;
	public Boolean downloadBollettiniEnable;
	public Boolean isNotificaCreata;
	public BigDecimal importoPagato;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public LocalDate dataPagamento;
	public Boolean isRiconciliaEnable;
	public Boolean isCreatoDalloUserCorrente;
	public String importoSollecitatoString;
	public String maggiorazioneString;
	private SoggettoVO soggetto;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataFineValidita;
	private TipoSollecitoVO tipoSollecito;
	
	

	public String getImportoSollecitatoString() {
		return importoSollecitatoString;
	}

	public void setImportoSollecitatoString(String importoSollecitatoString) {
		this.importoSollecitatoString = importoSollecitatoString;
	}

	public String getMaggiorazioneString() {
		return maggiorazioneString;
	}

	public void setMaggiorazioneString(String maggiorazioneString) {
		this.maggiorazioneString = maggiorazioneString;
	}

	public Boolean getIsCreatoDalloUserCorrente() {
		return isCreatoDalloUserCorrente;
	}

	public void setIsCreatoDalloUserCorrente(Boolean isCreatoDalloUserCorrente) {
		this.isCreatoDalloUserCorrente = isCreatoDalloUserCorrente;
	}

	public Integer getIdSollecito() {
		return idSollecito;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public BigDecimal getImportoSollecitato() {
		return importoSollecitato;
	}

	public BigDecimal getMaggiorazione() {
		return maggiorazione;
	}

	public StatoSollecitoVO getStatoSollecito() {
		return statoSollecito;
	}

	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setImportoSollecitato(BigDecimal importoSollecitato) {
		this.importoSollecitato = importoSollecitato;
	}

	public void setMaggiorazione(BigDecimal maggiorazione) {
		this.maggiorazione = maggiorazione;
	}

	public void setStatoSollecito(StatoSollecitoVO statoSollecito) {
		this.statoSollecito = statoSollecito;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Integer getIdSoggettoOrdinanza() {
		return idSoggettoOrdinanza;
	}

	public void setIdSoggettoOrdinanza(Integer idSoggettoOrdinanza) {
		this.idSoggettoOrdinanza = idSoggettoOrdinanza;
	}

	public Boolean getBollettinoDaCreare() {
		return bollettinoDaCreare;
	}

	public void setBollettinoDaCreare(Boolean bollettinoDaCreare) {
		this.bollettinoDaCreare = bollettinoDaCreare;
	}

	public Boolean getIsNotificaCreata() {
		return isNotificaCreata;
	}

	public void setIsNotificaCreata(Boolean isNotificaCreata) {
		this.isNotificaCreata = isNotificaCreata;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public Boolean getIsRiconciliaEnable() {
		return isRiconciliaEnable;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public void setIsRiconciliaEnable(Boolean isRiconciliaEnable) {
		this.isRiconciliaEnable = isRiconciliaEnable;
	}

	public Boolean getDownloadBollettiniEnable() {
		return downloadBollettiniEnable;
	}

	public void setDownloadBollettiniEnable(Boolean downloadBollettiniEnable) {
		this.downloadBollettiniEnable = downloadBollettiniEnable;
	}

	public SoggettoVO getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(SoggettoVO soggetto) {
		this.soggetto = soggetto;
	}

	public LocalDate getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(LocalDate dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public TipoSollecitoVO getTipoSollecito() {
		return tipoSollecito;
	}

	public void setTipoSollecito(TipoSollecitoVO tipoSollecito) {
		this.tipoSollecito = tipoSollecito;
	}
	
	

}
