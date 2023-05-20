/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SoggettoRequest extends ParentRequest {
	private static final long serialVersionUID = -2341281722644132513L;
	private Boolean personaFisica;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNascita;

	private String ragioneSociale;
	private String partitaIva;
	private String codiceFiscalePersGiuridica;
	private String tipoSoggetto;// C or T

	public Boolean getPersonaFisica() {
		return personaFisica;
	}

	public String getCodiceFiscalePersGiuridica() {
		return codiceFiscalePersGiuridica;
	}

	public void setCodiceFiscalePersGiuridica(String codiceFiscalePersGiuridica) {
		this.codiceFiscalePersGiuridica = codiceFiscalePersGiuridica;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setPersonaFisica(Boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
