/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the cnm_t_calendario database table.
 * 
 */
@Entity
@Table(name="cnm_t_calendario")
@NamedQuery(name="CnmTCalendario.findAll", query="SELECT c FROM CnmTCalendario c")
public class CnmTCalendario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_calendario")
	private Integer idCalendario;

	@Column(name="cap_udienza")
	private String capUdienza;

	@Column(name="cognome_funzionario_sanzion")
	private String cognomeFunzionarioSanzion;

	@Column(name="cognome_giudice")
	private String cognomeGiudice;

	private String colore;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="fine_udienza")
	private Timestamp fineUdienza;

	@Column(name="indirizzo_udienza")
	private String indirizzoUdienza;

	@Column(name="inizio_udienza")
	private Timestamp inizioUdienza;

	@Column(name="nome_funzionario_sanzion")
	private String nomeFunzionarioSanzion;

	@Column(name="nome_giudice")
	private String nomeGiudice;

	@Column(name="numero_civico_udienza")
	private String numeroCivicoUdienza;

	private String tribunale;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune_udienza")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDGruppo
	@ManyToOne
	@JoinColumn(name="id_gruppo")
	private CnmDGruppo cnmDGruppo;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

//
//	//bi-directional many-to-one association to CnmTAllegatoField
//	@OneToMany(mappedBy="cnmTMailInviata")
//	private List<CnmTMailInviata> CnmTMailInviata;
	

	//Event Sheduler Params
	@Column(name="note")
	private String note;

	@Column(name="email_giudice")
	private String emailGiudice;

	@Column(name="data_invio_promemoria_udienza")
	private Date dataPromemoriaUdienza;
	
	@Column(name="data_invio_promemoria_documentazione")
	private Date dataPromemoriaDocumentazione;
	
	public CnmTCalendario() {
	}

	public Integer getIdCalendario() {
		return this.idCalendario;
	}

	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}

	public String getCapUdienza() {
		return this.capUdienza;
	}

	public void setCapUdienza(String capUdienza) {
		this.capUdienza = capUdienza;
	}

	public String getCognomeFunzionarioSanzion() {
		return this.cognomeFunzionarioSanzion;
	}

	public void setCognomeFunzionarioSanzion(String cognomeFunzionarioSanzion) {
		this.cognomeFunzionarioSanzion = cognomeFunzionarioSanzion;
	}

	public String getCognomeGiudice() {
		return this.cognomeGiudice;
	}

	public void setCognomeGiudice(String cognomeGiudice) {
		this.cognomeGiudice = cognomeGiudice;
	}

	public String getColore() {
		return this.colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
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

	public Timestamp getFineUdienza() {
		return this.fineUdienza;
	}

	public void setFineUdienza(Timestamp fineUdienza) {
		this.fineUdienza = fineUdienza;
	}

	public String getIndirizzoUdienza() {
		return this.indirizzoUdienza;
	}

	public void setIndirizzoUdienza(String indirizzoUdienza) {
		this.indirizzoUdienza = indirizzoUdienza;
	}

	public Timestamp getInizioUdienza() {
		return this.inizioUdienza;
	}

	public void setInizioUdienza(Timestamp inizioUdienza) {
		this.inizioUdienza = inizioUdienza;
	}

	public String getNomeFunzionarioSanzion() {
		return this.nomeFunzionarioSanzion;
	}

	public void setNomeFunzionarioSanzion(String nomeFunzionarioSanzion) {
		this.nomeFunzionarioSanzion = nomeFunzionarioSanzion;
	}

	public String getNomeGiudice() {
		return this.nomeGiudice;
	}

	public void setNomeGiudice(String nomeGiudice) {
		this.nomeGiudice = nomeGiudice;
	}

	public String getNumeroCivicoUdienza() {
		return this.numeroCivicoUdienza;
	}

	public void setNumeroCivicoUdienza(String numeroCivicoUdienza) {
		this.numeroCivicoUdienza = numeroCivicoUdienza;
	}

	public String getTribunale() {
		return this.tribunale;
	}

	public void setTribunale(String tribunale) {
		this.tribunale = tribunale;
	}

	public CnmDComune getCnmDComune() {
		return this.cnmDComune;
	}

	public void setCnmDComune(CnmDComune cnmDComune) {
		this.cnmDComune = cnmDComune;
	}

	public CnmDGruppo getCnmDGruppo() {
		return this.cnmDGruppo;
	}

	public void setCnmDGruppo(CnmDGruppo cnmDGruppo) {
		this.cnmDGruppo = cnmDGruppo;
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


	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEmailGiudice() {
		return emailGiudice;
	}

	public void setEmailGiudice(String emailGiudice) {
		this.emailGiudice = emailGiudice;
	}
	
//	public List<CnmTMailInviata> getCnmTMailInviata() {
//		return CnmTMailInviata;
//	}
//
//	public void setCnmTMailInviata(List<CnmTMailInviata> cnmTMailInviata) {
//		CnmTMailInviata = cnmTMailInviata;
//	}
	
	public Date getDataPromemoriaUdienza() {
		return dataPromemoriaUdienza;
	}

	public void setDataPromemoriaUdienza(Date dataPromemoriaUdienza) {
		this.dataPromemoriaUdienza = dataPromemoriaUdienza;
	}

	public Date getDataPromemoriaDocumentazione() {
		return dataPromemoriaDocumentazione;
	}

	public void setDataPromemoriaDocumentazione(Date dataPromemoriaDocumentazione) {
		this.dataPromemoriaDocumentazione = dataPromemoriaDocumentazione;
	}
}
