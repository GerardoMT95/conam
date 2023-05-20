/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.calendario;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDateTime;

/**
 * @author riccardo.bova
 * @date 26 set 2018
 */
public class CalendarEventVO extends ParentVO {

	private static final long serialVersionUID = -7225547603738208565L;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataInizio;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataFine;
	private String tribunale;
	private String nomeGiudice;
	private String cognomeGiudice;
	private String nomeFunzionarioSanzionatore;
	private String cognomeFunzionarioSanzionatore;
	private RegioneVO regione;
	private ProvinciaVO provincia;
	private ComuneVO comune;
	private String cap;
	private String via;
	private String civico;
	private String color;
	private String note;
	private Integer id;
	private String codiceFiscaleProprietario;
	private Boolean sendPromemoriaUdienza;
	private Boolean sendPromemoriaDocumentazione;
	private String emailGiudice;
	private Integer documentazioneAdvanceDay;
	private Integer udienzaAdvanceDay;
	
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public String getTribunale() {
		return tribunale;
	}

	public void setTribunale(String tribunale) {
		this.tribunale = tribunale;
	}

	public String getNomeGiudice() {
		return nomeGiudice;
	}

	public void setNomeGiudice(String nomeGiudice) {
		this.nomeGiudice = nomeGiudice;
	}

	public String getCognomeGiudice() {
		return cognomeGiudice;
	}

	public void setCognomeGiudice(String cognomeGiudice) {
		this.cognomeGiudice = cognomeGiudice;
	}

	public String getNomeFunzionarioSanzionatore() {
		return nomeFunzionarioSanzionatore;
	}

	public void setNomeFunzionarioSanzionatore(String nomeFunzionarioSanzionatore) {
		this.nomeFunzionarioSanzionatore = nomeFunzionarioSanzionatore;
	}

	public String getCognomeFunzionarioSanzionatore() {
		return cognomeFunzionarioSanzionatore;
	}

	public void setCognomeFunzionarioSanzionatore(String cognomeFunzionarioSanzionatore) {
		this.cognomeFunzionarioSanzionatore = cognomeFunzionarioSanzionatore;
	}

	public RegioneVO getRegione() {
		return regione;
	}

	public void setRegione(RegioneVO regione) {
		this.regione = regione;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public ComuneVO getComune() {
		return comune;
	}

	public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCodiceFiscaleProprietario() {
		return codiceFiscaleProprietario;
	}

	public void setCodiceFiscaleProprietario(String codiceFiscaleProprietario) {
		this.codiceFiscaleProprietario = codiceFiscaleProprietario;
	}

	public Boolean getSendPromemoriaUdienza() {
		return sendPromemoriaUdienza;
	}

	public void setSendPromemoriaUdienza(Boolean sendPromemoriaUdienza) {
		this.sendPromemoriaUdienza = sendPromemoriaUdienza;
	}

	public Boolean getSendPromemoriaDocumentazione() {
		return sendPromemoriaDocumentazione;
	}

	public void setSendPromemoriaDocumentazione(Boolean sendPromemoriaDocumentazione) {
		this.sendPromemoriaDocumentazione = sendPromemoriaDocumentazione;
	}

	public String getEmailGiudice() {
		return emailGiudice;
	}

	public void setEmailGiudice(String emailGiudice) {
		this.emailGiudice = emailGiudice;
	}
	public Integer getDocumentazioneAdvanceDay() {
		return documentazioneAdvanceDay;
	}

	public void setDocumentazioneAdvanceDay(Integer documentazioneAdvanceDay) {
		this.documentazioneAdvanceDay = documentazioneAdvanceDay;
	}

	public Integer getUdienzaAdvanceDay() {
		return udienzaAdvanceDay;
	}

	public void setUdienzaAdvanceDay(Integer udienzaAdvanceDay) {
		this.udienzaAdvanceDay = udienzaAdvanceDay;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
