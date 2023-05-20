/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;
import java.util.List;

public class RicercaOrdinanzaRequest extends ParentVO {

	private static final long serialVersionUID = -2058040095154843776L;

	private String numeroDeterminazione;
	private String numeroVerbale;
	private List<StatoOrdinanzaVO> statoOrdinanza;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataOrdinanzaDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataOrdinanzaA;
	private Boolean ordinanzaProtocollata;
	private List<SoggettoRequest> soggettoVerbale;
	private String tipoRicerca;
	private Boolean pregresso;
	private Boolean annullamento;
	private Boolean statoManualeDiCompetenza;

	//LUCIO ROSADINI - AGGIUNTI DATI FILTRAGGIO DEL VERBALE -- 2021/03/25
	private DatiVerbaleRequest datiVerbale = null;
	
	// 20210524_LC acconti
	private Boolean perAcconto;

	public String getNumeroDeterminazione() {
		return numeroDeterminazione;
	}

	public void setNumeroDeterminazione(String numeroDeterminazione) {
		this.numeroDeterminazione = numeroDeterminazione;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public List<SoggettoRequest> getSoggettoVerbale() {
		return soggettoVerbale;
	}

	public void setSoggettoVerbale(List<SoggettoRequest> soggettoVerbale) {
		this.soggettoVerbale = soggettoVerbale;
	}

	public Boolean getOrdinanzaProtocollata() {
		return ordinanzaProtocollata;
	}

	public void setOrdinanzaProtocollata(Boolean ordinanzaProtocollata) {
		this.ordinanzaProtocollata = ordinanzaProtocollata;
	}

	public List<StatoOrdinanzaVO> getStatoOrdinanza() {
		return statoOrdinanza;
	}

	public void setStatoOrdinanza(List<StatoOrdinanzaVO> statoOrdinanza) {
		this.statoOrdinanza = statoOrdinanza;
	}

	public LocalDate getDataOrdinanzaDa() {
		return dataOrdinanzaDa;
	}

	public void setDataOrdinanzaDa(LocalDate dataOrdinanzaDa) {
		this.dataOrdinanzaDa = dataOrdinanzaDa;
	}

	public LocalDate getDataOrdinanzaA() {
		return dataOrdinanzaA;
	}

	public void setDataOrdinanzaA(LocalDate dataOrdinanzaA) {
		this.dataOrdinanzaA = dataOrdinanzaA;
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

	public Boolean getPregresso() {
		return pregresso;
	}

	public void setPregresso(Boolean pregresso) {
		this.pregresso = pregresso;
	}
	public Boolean getAnnullamento() {
		return annullamento;
	}

	public void setAnnullamento(Boolean annullamento) {
		this.annullamento = annullamento;
	}


	public DatiVerbaleRequest getDatiVerbale() {
		return datiVerbale;
	}

	public void setDatiVerbale(DatiVerbaleRequest datiVerbale) {
		this.datiVerbale = datiVerbale;
	}

	public Boolean getStatoManualeDiCompetenza() {
		return statoManualeDiCompetenza;
	}

	public void setStatoManualeDiCompetenza(Boolean statoManualeDiCompetenza) {
		this.statoManualeDiCompetenza = statoManualeDiCompetenza;
		if (datiVerbale == null) datiVerbale = new DatiVerbaleRequest();
		datiVerbale.setStatoManualeDiCompetenza(statoManualeDiCompetenza);
	}


	public Boolean getPerAcconto() {
		return perAcconto;
	}

	public void setPerAcconto(Boolean perAcconto) {
		this.perAcconto = perAcconto;
	}
}
