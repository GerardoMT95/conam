/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDateTime;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class MinVerbaleVO extends ParentVO {

	private static final long serialVersionUID = -884217214905682332L;

	private Long id;
	private String numeroProtocollo;
	private String numero;
	private EnteVO enteAccertatore;
	private StatoVerbaleVO stato;
	private String user;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataCaricamento;
	private Boolean modificabile;
	//Messaggio conferma per stato manuale
	private StatoManualeVO statoManuale;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataOraAccertamento;
	
	public Long getId() {
		return id;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumero() {
		return numero;
	}

	public EnteVO getEnteAccertatore() {
		return enteAccertatore;
	}

	public StatoVerbaleVO getStato() {
		return stato;
	}

	public String getUser() {
		return user;
	}

	public LocalDateTime getDataCaricamento() {
		return dataCaricamento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setEnteAccertatore(EnteVO enteAccertatore) {
		this.enteAccertatore = enteAccertatore;
	}

	public void setStato(StatoVerbaleVO stato) {
		this.stato = stato;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setDataCaricamento(LocalDateTime dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Boolean getModificabile() {
		return modificabile;
	}

	public void setModificabile(Boolean modificabile) {
		this.modificabile = modificabile;
	}

	public StatoManualeVO getStatoManuale() {
		return statoManuale;
	}

	public void setStatoManuale(StatoManualeVO statoManuale) {
		this.statoManuale = statoManuale;
	}

	public LocalDateTime getDataOraAccertamento() {
		return dataOraAccertamento;
	}

	public void setDataOraAccertamento(LocalDateTime dataOraAccertamento) {
		this.dataOraAccertamento = dataOraAccertamento;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
