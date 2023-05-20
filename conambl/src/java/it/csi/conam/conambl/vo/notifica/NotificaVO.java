/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.notifica;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 21 feb 2019
 */
public class NotificaVO extends ParentVO {

	private static final long serialVersionUID = -6021048389629447286L;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public LocalDate dataNotifica;
	public BigDecimal importoSpeseNotifica;
	public BigDecimal numeroRaccomandata;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public LocalDate dataSpedizione;
	public Boolean notificata;
	public ModalitaNotificaVO modalita;
	public Integer idOrdinanza;
	public Integer idPiano;
	public Integer idSollecito;
	public boolean pregresso;

	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}

	public BigDecimal getNumeroRaccomandata() {
		return numeroRaccomandata;
	}

	public LocalDate getDataSpedizione() {
		return dataSpedizione;
	}

	public Boolean getNotificata() {
		return notificata;
	}

	public ModalitaNotificaVO getModalita() {
		return modalita;
	}

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public void setNumeroRaccomandata(BigDecimal numeroRaccomandata) {
		this.numeroRaccomandata = numeroRaccomandata;
	}

	public void setDataSpedizione(LocalDate dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}

	public void setNotificata(Boolean notificata) {
		this.notificata = notificata;
	}

	public void setModalita(ModalitaNotificaVO modalita) {
		this.modalita = modalita;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	public LocalDate getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(LocalDate dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public Integer getIdPiano() {
		return idPiano;
	}

	public void setIdPiano(Integer idPiano) {
		this.idPiano = idPiano;
	}

	public Integer getIdSollecito() {
		return idSollecito;
	}

	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}

	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}

}
