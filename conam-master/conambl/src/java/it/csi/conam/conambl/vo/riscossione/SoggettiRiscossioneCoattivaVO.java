/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.riscossione;
/**
 *  @author riccardo.bova
 *  @date 13 mar 2019
 */

import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SoggettiRiscossioneCoattivaVO extends SoggettoVO {

	private static final long serialVersionUID = -5601536788676398374L;

	private String numeroDeterminazione;
	private StatoOrdinanzaVO statoOrdinanza;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNotifica;
	private ImportoResponse importoPianoResponse;
	private ImportoResponse importoSollecitoResponse;
	private ImportoResponse importoOrdinanzaResponse;
	private BigDecimal importoSanzione;
	private BigDecimal importoSpeseNotifica;
	private BigDecimal importoSpeseLegali;
	private BigDecimal importoRiscosso;
	private StatoRiscossioneVO statoRiscossione;
	private Integer idRiscossione;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataDecorrenzaInteressi;
	private boolean superatoIlMassimo;
	private boolean enableInvioMassivo;

	public String getNumeroDeterminazione() {
		return numeroDeterminazione;
	}

	public StatoOrdinanzaVO getStatoOrdinanza() {
		return statoOrdinanza;
	}

	public void setNumeroDeterminazione(String numeroDeterminazione) {
		this.numeroDeterminazione = numeroDeterminazione;
	}

	public void setStatoOrdinanza(StatoOrdinanzaVO statoOrdinanza) {
		this.statoOrdinanza = statoOrdinanza;
	}

	public LocalDate getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(LocalDate dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public ImportoResponse getImportoPianoResponse() {
		return importoPianoResponse;
	}

	public ImportoResponse getImportoSollecitoResponse() {
		return importoSollecitoResponse;
	}

	public ImportoResponse getImportoOrdinanzaResponse() {
		return importoOrdinanzaResponse;
	}

	public void setImportoPianoResponse(ImportoResponse importoPianoResponse) {
		this.importoPianoResponse = importoPianoResponse;
	}

	public void setImportoSollecitoResponse(ImportoResponse importoSollecitoResponse) {
		this.importoSollecitoResponse = importoSollecitoResponse;
	}

	public void setImportoOrdinanzaResponse(ImportoResponse importoOrdinanzaResponse) {
		this.importoOrdinanzaResponse = importoOrdinanzaResponse;
	}

	public StatoRiscossioneVO getStatoRiscossione() {
		return statoRiscossione;
	}

	public void setStatoRiscossione(StatoRiscossioneVO statoRiscossione) {
		this.statoRiscossione = statoRiscossione;
	}

	public BigDecimal getImportoRiscosso() {
		return importoRiscosso;
	}

	public void setImportoRiscosso(BigDecimal importoRiscosso) {
		this.importoRiscosso = importoRiscosso;
	}

	public Integer getIdRiscossione() {
		return idRiscossione;
	}

	public void setIdRiscossione(Integer idRiscossione) {
		this.idRiscossione = idRiscossione;
	}

	public BigDecimal getImportoSanzione() {
		return importoSanzione;
	}

	public void setImportoSanzione(BigDecimal importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public BigDecimal getImportoSpeseLegali() {
		return importoSpeseLegali;
	}

	public void setImportoSpeseLegali(BigDecimal importoSpeseLegali) {
		this.importoSpeseLegali = importoSpeseLegali;
	}

	public LocalDate getDataDecorrenzaInteressi() {
		return dataDecorrenzaInteressi;
	}

	public void setDataDecorrenzaInteressi(LocalDate dataDecorrenzaInteressi) {
		this.dataDecorrenzaInteressi = dataDecorrenzaInteressi;
	}

	public boolean isSuperatoIlMassimo() {
		return superatoIlMassimo;
	}

	public void setSuperatoIlMassimo(boolean superatoIlMassimo) {
		this.superatoIlMassimo = superatoIlMassimo;
	}

	public boolean isEnableInvioMassivo() {
		return enableInvioMassivo;
	}

	public void setEnableInvioMassivo(boolean enableInvioMassivo) {
		this.enableInvioMassivo = enableInvioMassivo;
	}

}
