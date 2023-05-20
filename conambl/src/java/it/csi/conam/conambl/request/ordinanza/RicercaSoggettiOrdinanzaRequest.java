/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSentenzaVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public class RicercaSoggettiOrdinanzaRequest extends ParentVO {

	private static final long serialVersionUID = 1L;

	private String numeroDeterminazione;
	private String numeroProtocolloSentenza;
	private StatoSentenzaVO statoSentenza;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataSentenzaDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataSentenzaA;
	private Boolean ordinanzaProtocollata;
	private Boolean ordinanzeSollecitate;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNotificaDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNotificaA;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataCreazioneDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataCreazioneA;
	private String tipoRicerca;

	private Boolean statoManualeDiCompetenza;
	
	public Boolean getStatoManualeDiCompetenza() {
		return statoManualeDiCompetenza;
	}

	public void setStatoManualeDiCompetenza(Boolean statoManualeDiCompetenza) {
		this.statoManualeDiCompetenza = statoManualeDiCompetenza;
		if (datiVerbale == null) datiVerbale = new DatiVerbaleRequest();
		datiVerbale.setStatoManualeDiCompetenza(statoManualeDiCompetenza);
	}
	
	//LUCIO ROSADINI - AGGIUNTI DATI FILTRAGGIO DEL VERBALE -- 2021/03/25
	private DatiVerbaleRequest datiVerbale = null;

	public LocalDate getDataNotificaDa() {
		return dataNotificaDa;
	}

	public void setDataNotificaDa(LocalDate dataNotificaDa) {
		this.dataNotificaDa = dataNotificaDa;
	}

	public LocalDate getDataNotificaA() {
		return dataNotificaA;
	}

	public void setDataNotificaA(LocalDate dataNotificaA) {
		this.dataNotificaA = dataNotificaA;
	}

	public LocalDate getDataCreazioneDa() {
		return dataCreazioneDa;
	}

	public void setDataCreazioneDa(LocalDate dataCreazioneDa) {
		this.dataCreazioneDa = dataCreazioneDa;
	}

	public LocalDate getDataCreazioneA() {
		return dataCreazioneA;
	}

	public void setDataCreazioneA(LocalDate dataCreazioneA) {
		this.dataCreazioneA = dataCreazioneA;
	}

	public String getNumeroDeterminazione() {
		return numeroDeterminazione;
	}

	public String getNumeroProtocolloSentenza() {
		return numeroProtocolloSentenza;
	}

	public void setNumeroDeterminazione(String numeroDeterminazione) {
		this.numeroDeterminazione = numeroDeterminazione;
	}

	public void setNumeroProtocolloSentenza(String numeroProtocolloSentenza) {
		this.numeroProtocolloSentenza = numeroProtocolloSentenza;
	}

	public Boolean getOrdinanzaProtocollata() {
		return ordinanzaProtocollata;
	}

	public void setOrdinanzaProtocollata(Boolean ordinanzaProtocollata) {
		this.ordinanzaProtocollata = ordinanzaProtocollata;
	}

	public Boolean getOrdinanzeSollecitate() {
		return ordinanzeSollecitate;
	}

	public void setOrdinanzeSollecitate(Boolean ordinanzeSollecitate) {
		this.ordinanzeSollecitate = ordinanzeSollecitate;
	}

	public StatoSentenzaVO getStatoSentenza() {
		return statoSentenza;
	}

	public void setStatoSentenza(StatoSentenzaVO statoSentenza) {
		this.statoSentenza = statoSentenza;
	}

	public LocalDate getDataSentenzaDa() {
		return dataSentenzaDa;
	}

	public void setDataSentenzaDa(LocalDate dataSentenzaDa) {
		this.dataSentenzaDa = dataSentenzaDa;
	}

	public LocalDate getDataSentenzaA() {
		return dataSentenzaA;
	}

	public void setDataSentenzaA(LocalDate dataSentenzaA) {
		this.dataSentenzaA = dataSentenzaA;
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}
	
	public DatiVerbaleRequest getDatiVerbale() {
		return datiVerbale;
	}

	public void setDatiVerbale(DatiVerbaleRequest datiVerbale) {
		this.datiVerbale = datiVerbale;
	}

}
