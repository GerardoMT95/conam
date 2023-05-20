/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_t_persona database table.
 * 
 */
@Entity
@Table(name="cnm_t_persona")
@NamedQuery(name="CnmTPersona.findAll", query="SELECT c FROM CnmTPersona c")
public class CnmTPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona")
	private Integer idPersona;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nascita")
	private Date dataNascita;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="denom_comune_nascita_estero")
	private String denomComuneNascitaEstero;

	private String qualifica;

	private String sesso;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune_nascita")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione_nascita")
	private CnmDNazione cnmDNazione;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmTSoggetto
	@OneToMany(mappedBy="cnmTPersona")
	private List<CnmTSoggetto> cnmTSoggettos;

	public CnmTPersona() {
	}

	public Integer getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getDenomComuneNascitaEstero() {
		return this.denomComuneNascitaEstero;
	}

	public void setDenomComuneNascitaEstero(String denomComuneNascitaEstero) {
		this.denomComuneNascitaEstero = denomComuneNascitaEstero;
	}

	public String getQualifica() {
		return this.qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public CnmDComune getCnmDComune() {
		return this.cnmDComune;
	}

	public void setCnmDComune(CnmDComune cnmDComune) {
		this.cnmDComune = cnmDComune;
	}

	public CnmDNazione getCnmDNazione() {
		return this.cnmDNazione;
	}

	public void setCnmDNazione(CnmDNazione cnmDNazione) {
		this.cnmDNazione = cnmDNazione;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public List<CnmTSoggetto> getCnmTSoggettos() {
		return this.cnmTSoggettos;
	}

	public void setCnmTSoggettos(List<CnmTSoggetto> cnmTSoggettos) {
		this.cnmTSoggettos = cnmTSoggettos;
	}

	public CnmTSoggetto addCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		getCnmTSoggettos().add(cnmTSoggetto);
		cnmTSoggetto.setCnmTPersona(this);

		return cnmTSoggetto;
	}

	public CnmTSoggetto removeCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		getCnmTSoggettos().remove(cnmTSoggetto);
		cnmTSoggetto.setCnmTPersona(null);

		return cnmTSoggetto;
	}

}
