/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.pianorateizzazione;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RataVO extends ParentVO {

	private static final long serialVersionUID = -1884711893948912055L;

	private Integer idRata;
	private String iuv;
	private String codiceAvviso;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataPagamento;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataScadenza;
	private BigDecimal importoRata;
	private BigDecimal importoPagato;
	private BigDecimal numeroRata;
	private StatoRataVO stato;
	private Boolean isEditEnable;
	private Boolean isRiconciliaEnable;
	private String identificativoSoggetto;
	private Integer idOrdinanzaVerbaleSoggetto;


    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataFineValidita;
	
	
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public Integer getIdRata() {
		return idRata;
	}

	public String getIuv() {
		return iuv;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public BigDecimal getImportoRata() {
		return importoRata;
	}

	public BigDecimal getNumeroRata() {
		return numeroRata;
	}

	public StatoRataVO getStato() {
		return stato;
	}

	public void setIdRata(Integer idRata) {
		this.idRata = idRata;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public void setImportoRata(BigDecimal importoRata) {
		this.importoRata = importoRata;
	}

	public void setNumeroRata(BigDecimal numeroRata) {
		this.numeroRata = numeroRata;
	}

	public void setStato(StatoRataVO stato) {
		this.stato = stato;
	}

	public Boolean getIsEditEnable() {
		return isEditEnable;
	}

	public void setIsEditEnable(Boolean isEditEnable) {
		this.isEditEnable = isEditEnable;
	}

	public Boolean getIsRiconciliaEnable() {
		return isRiconciliaEnable;
	}

	public void setIsRiconciliaEnable(Boolean isRiconciliaEnable) {
		this.isRiconciliaEnable = isRiconciliaEnable;
	}

	public String getIdentificativoSoggetto() {
		return identificativoSoggetto;
	}

	public void setIdentificativoSoggetto(String identificativoSoggetto) {
		this.identificativoSoggetto = identificativoSoggetto;
	}

	public Integer getIdOrdinanzaVerbaleSoggetto() {
		return idOrdinanzaVerbaleSoggetto;
	}

	public void setIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		this.idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
	}	

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}
	public LocalDate getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(LocalDate dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
