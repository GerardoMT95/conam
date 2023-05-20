/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_d_lettera database table.
 * 
 */
@Entity
@Table(name="cnm_d_lettera")
@NamedQuery(name="CnmDLettera.findAll", query="SELECT c FROM CnmDLettera c")
public class CnmDLettera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lettera")
	private Integer idLettera;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="desc_illecito")
	private String descIllecito;

	private Boolean eliminato;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}

	@Column(name="importo_ridotto")
	private BigDecimal importoRidotto;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	private String lettera;

	@Column(name="sanzione_massima")
	private BigDecimal sanzioneMassima;

	@Column(name="sanzione_minima")
	private BigDecimal sanzioneMinima;

	@Temporal(TemporalType.DATE)
	@Column(name="scadenza_importi")
	private Date scadenzaImporti;

	//bi-directional many-to-one association to CnmDComma
	@ManyToOne
	@JoinColumn(name="id_comma")
	private CnmDComma cnmDComma;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmRVerbaleIllecito
	@OneToMany(mappedBy="cnmDLettera")
	private List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos;

	//bi-directional many-to-one association to CnmRScrittoIllecito
//	@OneToMany(mappedBy="cnmDLettera")
//	private List<CnmRScrittoIllecito> cnmRScrittoIllecitos;
	
	
	// bi-directional many-to-one association to cnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmDLettera")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;
	

	public CnmDLettera() {
	}

	public Integer getIdLettera() {
		return this.idLettera;
	}

	public void setIdLettera(Integer idLettera) {
		this.idLettera = idLettera;
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

	public String getDescIllecito() {
		return this.descIllecito;
	}

	public void setDescIllecito(String descIllecito) {
		this.descIllecito = descIllecito;
	}

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public BigDecimal getImportoRidotto() {
		return this.importoRidotto;
	}

	public void setImportoRidotto(BigDecimal importoRidotto) {
		this.importoRidotto = importoRidotto;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public String getLettera() {
		return this.lettera;
	}

	public void setLettera(String lettera) {
		this.lettera = lettera;
	}

	public BigDecimal getSanzioneMassima() {
		return this.sanzioneMassima;
	}

	public void setSanzioneMassima(BigDecimal sanzioneMassima) {
		this.sanzioneMassima = sanzioneMassima;
	}

	public BigDecimal getSanzioneMinima() {
		return this.sanzioneMinima;
	}

	public void setSanzioneMinima(BigDecimal sanzioneMinima) {
		this.sanzioneMinima = sanzioneMinima;
	}

	public Date getScadenzaImporti() {
		return this.scadenzaImporti;
	}

	public void setScadenzaImporti(Date scadenzaImporti) {
		this.scadenzaImporti = scadenzaImporti;
	}

	public CnmDComma getCnmDComma() {
		return this.cnmDComma;
	}

	public void setCnmDComma(CnmDComma cnmDComma) {
		this.cnmDComma = cnmDComma;
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

	public List<CnmRVerbaleIllecito> getCnmRVerbaleIllecitos() {
		return this.cnmRVerbaleIllecitos;
	}

	public void setCnmRVerbaleIllecitos(List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos) {
		this.cnmRVerbaleIllecitos = cnmRVerbaleIllecitos;
	}

	public CnmRVerbaleIllecito addCnmRVerbaleIllecito(CnmRVerbaleIllecito cnmRVerbaleIllecito) {
		getCnmRVerbaleIllecitos().add(cnmRVerbaleIllecito);
		cnmRVerbaleIllecito.setCnmDLettera(this);

		return cnmRVerbaleIllecito;
	}

	public CnmRVerbaleIllecito removeCnmRVerbaleIllecito(CnmRVerbaleIllecito cnmRVerbaleIllecito) {
		getCnmRVerbaleIllecitos().remove(cnmRVerbaleIllecito);
		cnmRVerbaleIllecito.setCnmDLettera(null);

		return cnmRVerbaleIllecito;
	}

//	public List<CnmRScrittoIllecito> getCnmRScrittoIllecitos() {
//		return cnmRScrittoIllecitos;
//	}
//
//	public void setCnmRScrittoIllecitos(List<CnmRScrittoIllecito> cnmRScrittoIllecitos) {
//		this.cnmRScrittoIllecitos = cnmRScrittoIllecitos;
//	}



	
}
