/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

public class AzioneOrdinanzaPregressiResponse extends AzioneOrdinanzaResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean dettaglioPianoEnable;
	private Boolean dettaglioSollecitoEnable;
	private Boolean dettaglioDispGiudiceEnable;
	private Boolean dettaglioRicevutaPagamentoEnable;
	
	public Boolean getDettaglioPianoEnable() {
		return dettaglioPianoEnable;
	}
	public void setDettaglioPianoEnable(Boolean dettaglioPianoEnable) {
		this.dettaglioPianoEnable = dettaglioPianoEnable;
	}
	public Boolean getDettaglioSollecitoEnable() {
		return dettaglioSollecitoEnable;
	}
	public void setDettaglioSollecitoEnable(Boolean dettaglioSollecitoEnable) {
		this.dettaglioSollecitoEnable = dettaglioSollecitoEnable;
	}
	public Boolean getDettaglioDispGiudiceEnable() {
		return dettaglioDispGiudiceEnable;
	}
	public void setDettaglioDispGiudiceEnable(Boolean dettaglioDispGiudiceEnable) {
		this.dettaglioDispGiudiceEnable = dettaglioDispGiudiceEnable;
	}
	public Boolean getDettaglioRicevutaPagamentoEnable() {
		return dettaglioRicevutaPagamentoEnable;
	}
	public void setDettaglioRicevutaPagamentoEnable(Boolean dettaglioRicevutaPagamentoEnable) {
		this.dettaglioRicevutaPagamentoEnable = dettaglioRicevutaPagamentoEnable;
	}

	
}
