/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.riscossione;

import it.csi.conam.conambl.request.ParentRequest;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public class RicercaStoricaSoggettiRiscossioneCoattivaRequest extends ParentRequest {

	private static final long serialVersionUID = -2747296176578020333L;

	private String codiceFiscaleFisico;
	private String codiceFiscaleGiuridico;
	private String numeroDeterminazioneOrdinanza;

	public String getCodiceFiscaleFisico() {
		return codiceFiscaleFisico;
	}

	public String getCodiceFiscaleGiuridico() {
		return codiceFiscaleGiuridico;
	}

	public String getNumeroDeterminazioneOrdinanza() {
		return numeroDeterminazioneOrdinanza;
	}

	public void setCodiceFiscaleFisico(String codiceFiscaleFisico) {
		this.codiceFiscaleFisico = codiceFiscaleFisico;
	}

	public void setCodiceFiscaleGiuridico(String codiceFiscaleGiuridico) {
		this.codiceFiscaleGiuridico = codiceFiscaleGiuridico;
	}

	public void setNumeroDeterminazioneOrdinanza(String numeroDeterminazioneOrdinanza) {
		this.numeroDeterminazioneOrdinanza = numeroDeterminazioneOrdinanza;
	}

}
