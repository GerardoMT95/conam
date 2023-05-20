/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

import java.math.BigDecimal;
import java.util.Date;

public class DatiSentenzaPregressiResponse extends ParentVO {

	
	// Numero disposizione - Identificativo Soggetto - Cognome Nome/Ragione Sociale - Posizione - Data disposizione - Importo a titolo di sanzione
	private static final long serialVersionUID = 1L;

	// Codice Fiscale / Partita IVA
	private String identificativoSoggetto;
	
	// Cognome Nome/Ragione Sociale
	private String nome;
	
	// Posizione
	private String posizione;

	// Data disposizione
	private Date dataDisposizione;
	
	// Importo a titolo di sanzione
	private BigDecimal importoTitoloSanzione;

	public String getIdentificativoSoggetto() {
		return identificativoSoggetto;
	}

	public void setIdentificativoSoggetto(String identificativoSoggetto) {
		this.identificativoSoggetto = identificativoSoggetto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPosizione() {
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}

	public Date getDataDisposizione() {
		return dataDisposizione;
	}

	public void setDataDisposizione(Date dataDisposizione) {
		this.dataDisposizione = dataDisposizione;
	}

	public BigDecimal getImportoTitoloSanzione() {
		return importoTitoloSanzione;
	}

	public void setImportoTitoloSanzione(BigDecimal importoTitoloSanzione) {
		this.importoTitoloSanzione = importoTitoloSanzione;
	}


}
