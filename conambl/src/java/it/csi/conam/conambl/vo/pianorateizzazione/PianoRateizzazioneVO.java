/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.pianorateizzazione;

import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PianoRateizzazioneVO extends MinPianoRateizzazioneVO {

	private static final long serialVersionUID = 4412725612151392854L;

	private BigDecimal numeroRate;
	private BigDecimal importoSanzione;
	private BigDecimal importoSpeseProcessuali;
	private BigDecimal importoSpeseNotifica;
	private BigDecimal importoMaggiorazione;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataScadenzaPrimaRata;
	private List<RataVO> rate;
	private Boolean isLetteraCreata;
	private Boolean isBollettiniEnable;
	private Boolean isRichiestaBollettiniCreata;
	private Boolean isNotificaCreata;
	private Boolean isNotificaEnable;

	public BigDecimal getNumeroRate() {
		return numeroRate;
	}

	public BigDecimal getImportoSanzione() {
		return importoSanzione;
	}

	public BigDecimal getImportoSpeseProcessuali() {
		return importoSpeseProcessuali;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}

	public void setNumeroRate(BigDecimal numeroRate) {
		this.numeroRate = numeroRate;
	}

	public void setImportoSanzione(BigDecimal importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	public void setImportoSpeseProcessuali(BigDecimal importoSpeseProcessuali) {
		this.importoSpeseProcessuali = importoSpeseProcessuali;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public List<RataVO> getRate() {
		return rate;
	}

	public void setRate(List<RataVO> rate) {
		this.rate = rate;
	}

	public LocalDate getDataScadenzaPrimaRata() {
		return dataScadenzaPrimaRata;
	}

	public void setDataScadenzaPrimaRata(LocalDate dataScadenzaPrimaRata) {
		this.dataScadenzaPrimaRata = dataScadenzaPrimaRata;
	}

	public Boolean getIsLetteraCreata() {
		return isLetteraCreata;
	}

	public void setIsLetteraCreata(Boolean isLetteraCreata) {
		this.isLetteraCreata = isLetteraCreata;
	}

	public Boolean getIsBollettiniEnable() {
		return isBollettiniEnable;
	}

	public void setIsBollettiniEnable(Boolean isBollettiniEnable) {
		this.isBollettiniEnable = isBollettiniEnable;
	}

	public Boolean getIsNotificaCreata() {
		return isNotificaCreata;
	}

	public void setIsNotificaCreata(Boolean isNotificaCreata) {
		this.isNotificaCreata = isNotificaCreata;
	}

	public Boolean getIsNotificaEnable() {
		return isNotificaEnable;
	}

	public void setIsNotificaEnable(Boolean isNotificaEnable) {
		this.isNotificaEnable = isNotificaEnable;
	}

	public Boolean getIsRichiestaBollettiniCreata() {
		return isRichiestaBollettiniCreata;
	}

	public void setIsRichiestaBollettiniCreata(Boolean isRichiestaBollettiniCreata) {
		this.isRichiestaBollettiniCreata = isRichiestaBollettiniCreata;
	}
	
	public BigDecimal getImportoMaggiorazione() {
		return importoMaggiorazione;
	}

	public void setImportoMaggiorazione(BigDecimal importoMaggiorazione) {
		this.importoMaggiorazione = importoMaggiorazione;
	}
}
