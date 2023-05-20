/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_t_scritto_difensivo database table.
 * 
 */
@Entity
@Table(name="cnm_t_scritto_difensivo")
@NamedQuery(name="CnmTScrittoDifensivo.findAll", query="SELECT c FROM CnmTScrittoDifensivo c")
public class CnmTScrittoDifensivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_scritto_difensivo")
	private Integer idScrittoDifensivo;

	@Column(name = "nome_file")
	private String nomeFile;

	@Column(name = "num_verbale_accertamento")
	private String numVerbaleAccertamento;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cognome")
	private String cognome;

	@Column(name = "ragione_sociale")
	private String ragioneSociale;
	
	@Column(name = "numero_protocollo")
	private String numeroProtocollo;
	
	@Column(name="flag_associato")
	private boolean flagAssociato;
	
	// 20210526_LC
	@Column(name="objectid_acta")
	private String objectidActa;
	
	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato")
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmDAmbito
	@ManyToOne
	@JoinColumn(name="id_ambito")
	private CnmDAmbito cnmDAmbito;

	//bi-directional many-to-one association to CnmDEnte
	@ManyToOne
	@JoinColumn(name="id_ente")
	private CnmDEnte cnmDEnte;

	//bi-directional many-to-one association to CnmDModalitaCaricamento
	@ManyToOne
	@JoinColumn(name="id_modalita_caricamento")
	private CnmDModalitaCaricamento cnmDModalitaCaricamento;



	@Column(name = "data_ora_protocollo")
	private Timestamp dataOraProtocollo;
	

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;



	//bi-directional many-to-one association to CnmRScrittoIllecito
//	@OneToMany(mappedBy="cnmTScrittoDifensivo")
//	private List<CnmRScrittoIllecito> cnmRScrittoIllecitos;
	
	
	
	
	// campi del riferimento normativo non in FK
	@ManyToOne
	@JoinColumn(name="id_norma")
	private CnmDNorma cnmDNorma;
	@ManyToOne
	@JoinColumn(name="id_articolo")
	private CnmDArticolo cnmDArticolo;
	@ManyToOne
	@JoinColumn(name="id_comma")
	private CnmDComma cnmDComma;
	@ManyToOne
	@JoinColumn(name="id_lettera")
	private CnmDLettera	 cnmDLettera;
	
	



	public Integer getIdScrittoDifensivo() {
		return idScrittoDifensivo;
	}

	public void setIdScrittoDifensivo(Integer idScrittoDifensivo) {
		this.idScrittoDifensivo = idScrittoDifensivo;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public CnmTAllegato getCnmTAllegato() {
		return cnmTAllegato;
	}

	public void setCnmTAllegato(CnmTAllegato cnmTAllegato) {
		this.cnmTAllegato = cnmTAllegato;
	}

	public CnmDAmbito getCnmDAmbito() {
		return cnmDAmbito;
	}

	public void setCnmDAmbito(CnmDAmbito cnmDAmbito) {
		this.cnmDAmbito = cnmDAmbito;
	}

	public Timestamp getDataOraInsert() {
		return dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public CnmTUser getCnmTUser1() {
		return cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public String getNumVerbaleAccertamento() {
		return numVerbaleAccertamento;
	}

	public void setNumVerbaleAccertamento(String numVerbaleAccertamento) {
		this.numVerbaleAccertamento = numVerbaleAccertamento;
	}

//	public List<CnmRScrittoIllecito> getCnmRScrittoIllecitos() {
//		return cnmRScrittoIllecitos;
//	}
//
//	public void setCnmRScrittoIllecitos(List<CnmRScrittoIllecito> cnmRScrittoIllecitos) {
//		this.cnmRScrittoIllecitos = cnmRScrittoIllecitos;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
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

	public boolean isFlagAssociato() {
		return flagAssociato;
	}

	public void setFlagAssociato(boolean flagAssociato) {
		this.flagAssociato = flagAssociato;
	}

	public CnmDEnte getCnmDEnte() {
		return cnmDEnte;
	}

	public void setCnmDEnte(CnmDEnte cnmDEnte) {
		this.cnmDEnte = cnmDEnte;
	}

	public Timestamp getDataOraProtocollo() {
		return dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public CnmDNorma getCnmDNorma() {
		return cnmDNorma;
	}

	public void setCnmDNorma(CnmDNorma cnmDNorma) {
		this.cnmDNorma = cnmDNorma;
	}

	public CnmDArticolo getCnmDArticolo() {
		return cnmDArticolo;
	}

	public void setCnmDArticolo(CnmDArticolo cnmDArticolo) {
		this.cnmDArticolo = cnmDArticolo;
	}

	public CnmDComma getCnmDComma() {
		return cnmDComma;
	}

	public void setCnmDComma(CnmDComma cnmDComma) {
		this.cnmDComma = cnmDComma;
	}

	public CnmDLettera getCnmDLettera() {
		return cnmDLettera;
	}

	public void setCnmDLettera(CnmDLettera cnmDLettera) {
		this.cnmDLettera = cnmDLettera;
	}

	public CnmDModalitaCaricamento getCnmDModalitaCaricamento() {
		return cnmDModalitaCaricamento;
	}

	public void setCnmDModalitaCaricamento(CnmDModalitaCaricamento cnmDModalitaCaricamento) {
		this.cnmDModalitaCaricamento = cnmDModalitaCaricamento;
	}

	public String getObjectidActa() {
		return objectidActa;
	}

	public void setObjectidActa(String objectidActa) {
		this.objectidActa = objectidActa;
	}





}
