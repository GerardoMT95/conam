/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the cnm_t_record_n2 database table.
 * 
 */
@Entity
@Table(name="cnm_t_record_n2")
@NamedQuery(name="CnmTRecordN2.findAll", query="SELECT c FROM CnmTRecordN2 c")
public class CnmTRecordN2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record_n2")
	private Integer idRecordN2;

	private String cap;

	@Column(name="cod_belfiore_comune")
	private String codBelfioreComune;

	@Column(name="cod_belfiore_nascita")
	private String codBelfioreNascita;

	private String cognome;

	@Column(name="data_nascita")
	private String dataNascita;

	@Column(name="denom_societa")
	private String denomSocieta;

	@Column(name="identificativo_soggetto")
	private String identificativoSoggetto;

	private String indirizzo;

	@Column(name="lettera_num_civico")
	private String letteraNumCivico;

	@Column(name="localita_frazione")
	private String localitaFrazione;

	private String nome;

	@Column(name="num_civico")
	private BigDecimal numCivico;

	@Column(name="presenza_obbligato")
	private String presenzaObbligato;

	private String sesso;

	private BigDecimal societa;

	//bi-directional one-to-one association to CnmTRecord
	@OneToOne
	@JoinColumn(name="id_record")
	private CnmTRecord cnmTRecord;

	public CnmTRecordN2() {
	}

	public Integer getIdRecordN2() {
		return this.idRecordN2;
	}

	public void setIdRecordN2(Integer idRecordN2) {
		this.idRecordN2 = idRecordN2;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodBelfioreComune() {
		return this.codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public String getCodBelfioreNascita() {
		return this.codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDenomSocieta() {
		return this.denomSocieta;
	}

	public void setDenomSocieta(String denomSocieta) {
		this.denomSocieta = denomSocieta;
	}

	public String getIdentificativoSoggetto() {
		return this.identificativoSoggetto;
	}

	public void setIdentificativoSoggetto(String identificativoSoggetto) {
		this.identificativoSoggetto = identificativoSoggetto;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getLetteraNumCivico() {
		return this.letteraNumCivico;
	}

	public void setLetteraNumCivico(String letteraNumCivico) {
		this.letteraNumCivico = letteraNumCivico;
	}

	public String getLocalitaFrazione() {
		return this.localitaFrazione;
	}

	public void setLocalitaFrazione(String localitaFrazione) {
		this.localitaFrazione = localitaFrazione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNumCivico() {
		return this.numCivico;
	}

	public void setNumCivico(BigDecimal numCivico) {
		this.numCivico = numCivico;
	}

	public String getPresenzaObbligato() {
		return this.presenzaObbligato;
	}

	public void setPresenzaObbligato(String presenzaObbligato) {
		this.presenzaObbligato = presenzaObbligato;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public BigDecimal getSocieta() {
		return this.societa;
	}

	public void setSocieta(BigDecimal societa) {
		this.societa = societa;
	}

	public CnmTRecord getCnmTRecord() {
		return this.cnmTRecord;
	}

	public void setCnmTRecord(CnmTRecord cnmTRecord) {
		this.cnmTRecord = cnmTRecord;
	}

}
