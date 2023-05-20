/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.template;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
public class DatiTemplateVO extends ParentVO {

	private static final long serialVersionUID = -8153081670459225437L;

	// template rateizzazione
	private List<SoggettoVO> listaSoggetti;
	private BigDecimal importoTotale;
	private BigDecimal numeroRate;
	private Boolean scadenzaDefinita;
	private BigDecimal importoPrimaRata;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate scadenzaPrimaRata;
	private BigDecimal importoAltreRate;
	private BigDecimal importoUltimaRata;
	private String dirigente;
	private String funzionario;	
	private String mailSettoreTributi;	

	// header
	private String numeroProtocollo;
	private String classificazione;
	private String fn;

	private String nome;
	
	// 20210308_LC ordinanza annullata
	private MinOrdinanzaVO ordinanza;
	private MinOrdinanzaVO ordinanzaAnnullata;
	
	//20210312_LC
	private String direzione;
	private String settore;
	private String oggettoLettera;
	private String corpoLettera;
	private String salutiLettera;
	private String dirigenteLettera;
	private String inizialiLettera;
	private String sedeEnte;
	private String infoOrganoAccertatore;
	
	
	
	
	

	public BigDecimal getImportoTotale() {
		return importoTotale;
	}

	public BigDecimal getNumeroRate() {
		return numeroRate;
	}

	public Boolean getScadenzaDefinita() {
		return scadenzaDefinita;
	}

	public BigDecimal getImportoPrimaRata() {
		return importoPrimaRata;
	}

	public LocalDate getScadenzaPrimaRata() {
		return scadenzaPrimaRata;
	}

	public BigDecimal getImportoAltreRate() {
		return importoAltreRate;
	}

	public BigDecimal getImportoUltimaRata() {
		return importoUltimaRata;
	}

	public String getDirigente() {
		return dirigente;
	}

	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}

	public void setNumeroRate(BigDecimal numeroRate) {
		this.numeroRate = numeroRate;
	}

	public void setScadenzaDefinita(Boolean scadenzaDefinita) {
		this.scadenzaDefinita = scadenzaDefinita;
	}

	public void setImportoPrimaRata(BigDecimal importoPrimaRata) {
		this.importoPrimaRata = importoPrimaRata;
	}

	public void setScadenzaPrimaRata(LocalDate scadenzaPrimaRata) {
		this.scadenzaPrimaRata = scadenzaPrimaRata;
	}

	public void setImportoAltreRate(BigDecimal importoAltreRate) {
		this.importoAltreRate = importoAltreRate;
	}

	public void setImportoUltimaRata(BigDecimal importoUltimaRata) {
		this.importoUltimaRata = importoUltimaRata;
	}

	public void setDirigente(String dirigente) {
		this.dirigente = dirigente;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public List<SoggettoVO> getListaSoggetti() {
		return listaSoggetti;
	}

	public void setListaSoggetti(List<SoggettoVO> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFunzionario() {
		return funzionario;
	}

	public void setFunzionario(String funzionario) {
		this.funzionario = funzionario;
	}
	
	public String getMailSettoreTributi() {
		return mailSettoreTributi;
	}

	public void setMailSettoreTributi(String mailSettoreTributi) {
		this.mailSettoreTributi = mailSettoreTributi;
	}

	public MinOrdinanzaVO getOrdinanzaAnnullata() {
		return ordinanzaAnnullata;
	}

	public void setOrdinanzaAnnullata(MinOrdinanzaVO ordinanzaAnnullata) {
		this.ordinanzaAnnullata = ordinanzaAnnullata;
	}

	public MinOrdinanzaVO getOrdinanza() {
		return ordinanza;
	}

	public void setOrdinanza(MinOrdinanzaVO ordinanza) {
		this.ordinanza = ordinanza;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public String getSettore() {
		return settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getDirigenteLettera() {
		return dirigenteLettera;
	}

	public void setDirigenteLettera(String dirigenteLettera) {
		this.dirigenteLettera = dirigenteLettera;
	}

	public String getSedeEnte() {
		return sedeEnte;
	}

	public void setSedeEnte(String sedeEnte) {
		this.sedeEnte = sedeEnte;
	}

	public String getOggettoLettera() {
		return oggettoLettera;
	}

	public void setOggettoLettera(String oggettoLettera) {
		this.oggettoLettera = oggettoLettera;
	}

	public String getInfoOrganoAccertatore() {
		return infoOrganoAccertatore;
	}

	public void setInfoOrganoAccertatore(String infoOrganoAccertatore) {
		this.infoOrganoAccertatore = infoOrganoAccertatore;
	}

	public String getInizialiLettera() {
		return inizialiLettera;
	}

	public void setInizialiLettera(String inizialiLettera) {
		this.inizialiLettera = inizialiLettera;
	}

	public String getCorpoLettera() {
		return corpoLettera;
	}

	public void setCorpoLettera(String corpoLettera) {
		this.corpoLettera = corpoLettera;
	}

	public String getSalutiLettera() {
		return salutiLettera;
	}

	public void setSalutiLettera(String salutiLettera) {
		this.salutiLettera = salutiLettera;
	}

	
	
	
	
	
}
