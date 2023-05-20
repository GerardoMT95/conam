/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.ordinanza;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MinOrdinanzaVO extends ParentVO {

	private static final long serialVersionUID = 852151050709076476L;

	private Long id;
	private TipoOrdinanzaVO tipo;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataOrdinanza;
	private StatoOrdinanzaVO stato;
	private String numeroProtocollo;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataProtocollo;
	private String numDeterminazione;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataDeterminazione;
	private BigDecimal importoOrdinanza;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataScadenza;
	private boolean superatoIlMassimo;
	private boolean pregresso;
	private String dettaglioOrdinanzaAnnullata;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataFineValidita;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoOrdinanzaVO getTipo() {
		return tipo;
	}

	public void setTipo(TipoOrdinanzaVO tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataOrdinanza() {
		return dataOrdinanza;
	}

	public void setDataOrdinanza(LocalDate dataOrdinanza) {
		this.dataOrdinanza = dataOrdinanza;
	}

	public StatoOrdinanzaVO getStato() {
		return stato;
	}

	public void setStato(StatoOrdinanzaVO stato) {
		this.stato = stato;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numProtocollo) {
		this.numeroProtocollo = numProtocollo;
	}

	public LocalDateTime getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(LocalDateTime dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getNumDeterminazione() {
		return numDeterminazione;
	}

	public void setNumDeterminazione(String numDeterminazione) {
		this.numDeterminazione = numDeterminazione;
	}

	public LocalDate getDataDeterminazione() {
		return dataDeterminazione;
	}

	public void setDataDeterminazione(LocalDate dataDeterminazione) {
		this.dataDeterminazione = dataDeterminazione;
	}

	public BigDecimal getImportoOrdinanza() {
		return importoOrdinanza;
	}

	public void setImportoOrdinanza(BigDecimal importoOrdinanza) {
		this.importoOrdinanza = importoOrdinanza;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public boolean isSuperatoIlMassimo() {
		return superatoIlMassimo;
	}

	public void setSuperatoIlMassimo(boolean superatoIlMassimo) {
		this.superatoIlMassimo = superatoIlMassimo;
	}

	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}

	public String getDettaglioOrdinanzaAnnullata() {
		return dettaglioOrdinanzaAnnullata;
	}

	public void setDettaglioOrdinanzaAnnullata(String dettaglioOrdinanzaAnnullata) {
		this.dettaglioOrdinanzaAnnullata = dettaglioOrdinanzaAnnullata;
	}

	public LocalDate getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(LocalDate dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
