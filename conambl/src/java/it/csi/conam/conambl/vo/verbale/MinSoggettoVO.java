/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class MinSoggettoVO extends ParentVO {

	private static final long serialVersionUID = -842251040551281391L;

	private Integer id;
	private String cognome;
	private String nome;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNascita;
	private RegioneVO regioneNascita;
	private ComuneVO comuneNascita;
	private ProvinciaVO provinciaNascita;
	private NazioneVO nazioneNascita;
	private Boolean nazioneNascitaEstera; // nato al estero
	private String denomComuneNascitaEstero;

	private Boolean isNazioneNascitaFromStas;
	private Boolean isComuneNascitaEsteroFromStas;
	private Boolean isRegioneNascitaFromStas;

	private Boolean personaFisica;

	private String sesso;

	private String codiceFiscale;
	private String ragioneSociale;
	private String partitaIva;

	public MinSoggettoVO() {
		super();
	}

	public MinSoggettoVO(MinSoggettoVO minSoggetto) {
		this.id = minSoggetto.getId();
		this.cognome = minSoggetto.getCognome();
		this.nome = minSoggetto.getNome();
		this.dataNascita = minSoggetto.getDataNascita();
		this.regioneNascita = minSoggetto.getRegioneNascita();
		this.comuneNascita = minSoggetto.getComuneNascita();
		this.provinciaNascita = minSoggetto.getProvinciaNascita();
		this.nazioneNascita = minSoggetto.getNazioneNascita();
		this.nazioneNascitaEstera = minSoggetto.getNazioneNascitaEstera();
		this.personaFisica = minSoggetto.getPersonaFisica();
		this.sesso = minSoggetto.getSesso();
		this.codiceFiscale = minSoggetto.getCodiceFiscale();
		this.ragioneSociale = minSoggetto.getRagioneSociale();
		this.partitaIva = minSoggetto.getPartitaIva();
	}

	public MinSoggettoVO(SoggettoRequest sog) {
		this.cognome = sog.getCognome();
		this.nome = sog.getNome();
		this.personaFisica = sog.getPersonaFisica();
		if (sog.getPersonaFisica() != null && sog.getPersonaFisica())
			this.codiceFiscale = sog.getCodiceFiscale();
		else
			this.codiceFiscale = sog.getCodiceFiscalePersGiuridica();
		this.ragioneSociale = sog.getRagioneSociale();
		this.partitaIva = sog.getPartitaIva();
		this.dataNascita = sog.getDataNascita();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public RegioneVO getRegioneNascita() {
		return regioneNascita;
	}

	public void setRegioneNascita(RegioneVO regioneNascita) {
		this.regioneNascita = regioneNascita;
	}

	public ComuneVO getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(ComuneVO comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public ProvinciaVO getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(ProvinciaVO provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public NazioneVO getNazioneNascita() {
		return nazioneNascita;
	}

	public void setNazioneNascita(NazioneVO nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public Boolean getNazioneNascitaEstera() {
		return nazioneNascitaEstera;
	}

	public Boolean getPersonaFisica() {
		return personaFisica;
	}

	public void setNazioneNascitaEstera(Boolean nazioneNascitaEstera) {
		this.nazioneNascitaEstera = nazioneNascitaEstera;
	}

	public void setPersonaFisica(Boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getDenomComuneNascitaEstero() {
		return denomComuneNascitaEstero;
	}

	public void setDenomComuneNascitaEstero(String denomComuneNascitaEstero) {
		this.denomComuneNascitaEstero = denomComuneNascitaEstero;
	}

	public Boolean getIsNazioneNascitaFromStas() {
		return isNazioneNascitaFromStas;
	}

	public void setIsNazioneNascitaFromStas(Boolean isNazioneNascitaFromStas) {
		this.isNazioneNascitaFromStas = isNazioneNascitaFromStas;
	}

	public Boolean getIsComuneNascitaEsteroFromStas() {
		return isComuneNascitaEsteroFromStas;
	}

	public void setIsComuneNascitaEsteroFromStas(Boolean isComuneNascitaEsteroFromStas) {
		this.isComuneNascitaEsteroFromStas = isComuneNascitaEsteroFromStas;
	}

	public Boolean getIsRegioneNascitaFromStas() {
		return isRegioneNascitaFromStas;
	}

	public void setIsRegioneNascitaFromStas(Boolean isRegioneFromStas) {
		this.isRegioneNascitaFromStas = isRegioneFromStas;
	}
}
