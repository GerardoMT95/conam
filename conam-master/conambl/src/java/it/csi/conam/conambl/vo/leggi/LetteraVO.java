/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class LetteraVO extends SelectVO {

	private static final long serialVersionUID = 8011990771663997222L;

	private BigDecimal sanzioneMinima;
	private BigDecimal sanzioneMassima;
	private BigDecimal importoMisuraRidotta;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate scadenzaImporti;
	private String descrizioneIllecito;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataFineValidita;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataInizioValidita;

	public LocalDate getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(LocalDate dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public LocalDate getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(LocalDate dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public BigDecimal getSanzioneMinima() {
		return sanzioneMinima;
	}

	public BigDecimal getSanzioneMassima() {
		return sanzioneMassima;
	}

	public BigDecimal getImportoMisuraRidotta() {
		return importoMisuraRidotta;
	}

	public LocalDate getScadenzaImporti() {
		return scadenzaImporti;
	}

	public String getDescrizioneIllecito() {
		return descrizioneIllecito;
	}

	public void setSanzioneMinima(BigDecimal sanzioneMinima) {
		this.sanzioneMinima = sanzioneMinima;
	}

	public void setSanzioneMassima(BigDecimal sanzioneMassima) {
		this.sanzioneMassima = sanzioneMassima;
	}

	public void setImportoMisuraRidotta(BigDecimal importoMisuraRidotta) {
		this.importoMisuraRidotta = importoMisuraRidotta;
	}

	public void setScadenzaImporti(LocalDate scadenzaImporti) {
		this.scadenzaImporti = scadenzaImporti;
	}

	public void setDescrizioneIllecito(String descrizioneIllecito) {
		this.descrizioneIllecito = descrizioneIllecito;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
