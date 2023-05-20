/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.template;

import it.csi.conam.conambl.vo.ParentVO;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
public class DatiTemplateCompilatiVO extends ParentVO {

	private static final long serialVersionUID = -8153081670459225437L;

	private String studioAvvocato;
	private String indirizzoAvvocato;
	private String comuneAvvocato;
	private String mailAvvocato;
	private String oggetto;
	private String descrizione;
	private String articolo;

	private String scadenzaPagamento;
	private String riferimentoNormativo;

	public String processiVerbali;
	public String nomeAvvocato;
	public String indirizzo;
	public String oraInizio;
	public String anno;
	public String mese;
	public String giorno;
	public String dichiara;
	
	// 20210312_LC
	private DatiTemplateCompilatiLetAnnVO datiLetteraAnnullamento;
	
	// 20210401_LC
	private String indirizzoOrganoAccertatoreRiga1;
	private String indirizzoOrganoAccertatoreRiga2;
	private String indirizzoOrganoAccertatoreRiga3;	
	private String testoLibero;	
	private String oggettoLettera;
	private String corpoLettera;
	
	
	
	public String getStudioAvvocato() {
		return studioAvvocato;
	}

	public String getIndirizzoAvvocato() {
		return indirizzoAvvocato;
	}

	public String getComuneAvvocato() {
		return comuneAvvocato;
	}

	public String getMailAvvocato() {
		return mailAvvocato;
	}

	public String getOggetto() {
		return oggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setStudioAvvocato(String studioAvvocato) {
		this.studioAvvocato = studioAvvocato;
	}

	public void setIndirizzoAvvocato(String indirizzoAvvocato) {
		this.indirizzoAvvocato = indirizzoAvvocato;
	}

	public void setComuneAvvocato(String comuneAvvocato) {
		this.comuneAvvocato = comuneAvvocato;
	}

	public void setMailAvvocato(String mailAvvocato) {
		this.mailAvvocato = mailAvvocato;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getArticolo() {
		return articolo;
	}

	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}

	public String getScadenzaPagamento() {
		return scadenzaPagamento;
	}

	public void setScadenzaPagamento(String scadenzaPagamento) {
		this.scadenzaPagamento = scadenzaPagamento;
	}

	public String getRiferimentoNormativo() {
		return riferimentoNormativo;
	}

	public void setRiferimentoNormativo(String riferimentoNormativo) {
		this.riferimentoNormativo = riferimentoNormativo;
	}

	public String getProcessiVerbali() {
		return processiVerbali;
	}

	public String getNomeAvvocato() {
		return nomeAvvocato;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getOraInizio() {
		return oraInizio;
	}

	public String getAnno() {
		return anno;
	}

	public String getMese() {
		return mese;
	}

	public String getGiorno() {
		return giorno;
	}

	public String getDichiara() {
		return dichiara;
	}

	public void setProcessiVerbali(String processiVerbali) {
		this.processiVerbali = processiVerbali;
	}

	public void setNomeAvvocato(String nomeAvvocato) {
		this.nomeAvvocato = nomeAvvocato;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public void setDichiara(String dichiara) {
		this.dichiara = dichiara;
	}

	public DatiTemplateCompilatiLetAnnVO getDatiLetteraAnnullamento() {
		return datiLetteraAnnullamento;
	}

	public void setDatiLetteraAnnullamento(DatiTemplateCompilatiLetAnnVO datiLetteraAnnullamento) {
		this.datiLetteraAnnullamento = datiLetteraAnnullamento;
	}

	public String getIndirizzoOrganoAccertatoreRiga1() {
		return indirizzoOrganoAccertatoreRiga1;
	}

	public void setIndirizzoOrganoAccertatoreRiga1(String indirizzoOrganoAccertatoreRiga1) {
		this.indirizzoOrganoAccertatoreRiga1 = indirizzoOrganoAccertatoreRiga1;
	}

	public String getIndirizzoOrganoAccertatoreRiga2() {
		return indirizzoOrganoAccertatoreRiga2;
	}

	public void setIndirizzoOrganoAccertatoreRiga2(String indirizzoOrganoAccertatoreRiga2) {
		this.indirizzoOrganoAccertatoreRiga2 = indirizzoOrganoAccertatoreRiga2;
	}

	public String getIndirizzoOrganoAccertatoreRiga3() {
		return indirizzoOrganoAccertatoreRiga3;
	}

	public void setIndirizzoOrganoAccertatoreRiga3(String indirizzoOrganoAccertatoreRiga3) {
		this.indirizzoOrganoAccertatoreRiga3 = indirizzoOrganoAccertatoreRiga3;
	}

	public String getTestoLibero() {
		return testoLibero;
	}

	public void setTestoLibero(String testoLibero) {
		this.testoLibero = testoLibero;
	}

	public String getOggettoLettera() {
		return oggettoLettera;
	}

	public void setOggettoLettera(String oggettoLettera) {
		this.oggettoLettera = oggettoLettera;
	}

	public String getCorpoLettera() {
		return corpoLettera;
	}

	public void setCorpoLettera(String corpoLettera) {
		this.corpoLettera = corpoLettera;
	}

	
	
}
