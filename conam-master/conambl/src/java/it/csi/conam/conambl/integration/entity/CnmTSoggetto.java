/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the cnm_t_soggetto database table.
 * 
 */
@Entity
@Table(name = "cnm_t_soggetto")
@NamedQuery(name = "CnmTSoggetto.findAll", query = "SELECT c FROM CnmTSoggetto c")
public class CnmTSoggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_soggetto")
	private Integer idSoggetto;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

	@Column(name = "codice_fiscale_giuridico")
	private String codiceFiscaleGiuridico;

	private String cognome;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name = "id_stas")
	private BigDecimal idStas;

	private String nome;

	@Column(name = "partita_iva")
	private String partitaIva;

	@Column(name = "ragione_sociale")
	private String ragioneSociale;

	// bi-directional many-to-one association to CnmRVerbaleSoggetto
	@OneToMany(mappedBy = "cnmTSoggetto")
	private List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos;

	// bi-directional many-to-one association to CnmTPersona
	@ManyToOne
	@JoinColumn(name = "id_persona")
	private CnmTPersona cnmTPersona;

	// bi-directional many-to-one association to CnmTSocieta
	@ManyToOne
	@JoinColumn(name = "id_societa")
	private CnmTSocieta cnmTSocieta;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;

	// bi-directional many-to-one association to CnmTResidenza
	@OneToMany(mappedBy = "cnmTSoggetto")
	private List<CnmTResidenza> cnmTResidenzas;

	@OneToMany(mappedBy = "cnmTSoggetto")
	private List<CnmTAcconto> cnmTAccontos;
	
	public CnmTSoggetto() {
	}

	public Integer getIdSoggetto() {
		return this.idSoggetto;
	}

	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceFiscaleGiuridico() {
		return this.codiceFiscaleGiuridico;
	}

	public void setCodiceFiscaleGiuridico(String codiceFiscaleGiuridico) {
		this.codiceFiscaleGiuridico = codiceFiscaleGiuridico;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public BigDecimal getIdStas() {
		return this.idStas;
	}

	public void setIdStas(BigDecimal idStas) {
		this.idStas = idStas;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public List<CnmRVerbaleSoggetto> getCnmRVerbaleSoggettos() {
		return this.cnmRVerbaleSoggettos;
	}

	public void setCnmRVerbaleSoggettos(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos) {
		this.cnmRVerbaleSoggettos = cnmRVerbaleSoggettos;
	}

	public CnmRVerbaleSoggetto addCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().add(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTSoggetto(this);

		return cnmRVerbaleSoggetto;
	}

	public CnmRVerbaleSoggetto removeCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().remove(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTSoggetto(null);

		return cnmRVerbaleSoggetto;
	}

	public CnmTPersona getCnmTPersona() {
		return this.cnmTPersona;
	}

	public void setCnmTPersona(CnmTPersona cnmTPersona) {
		this.cnmTPersona = cnmTPersona;
	}

	public CnmTSocieta getCnmTSocieta() {
		return this.cnmTSocieta;
	}

	public void setCnmTSocieta(CnmTSocieta cnmTSocieta) {
		this.cnmTSocieta = cnmTSocieta;
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

	public List<CnmTResidenza> getCnmTResidenzas() {
		return this.cnmTResidenzas;
	}

	public void setCnmTResidenzas(List<CnmTResidenza> cnmTResidenzas) {
		this.cnmTResidenzas = cnmTResidenzas;
	}

	public CnmTResidenza addCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().add(cnmTResidenza);
		cnmTResidenza.setCnmTSoggetto(this);

		return cnmTResidenza;
	}

	public CnmTResidenza removeCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().remove(cnmTResidenza);
		cnmTResidenza.setCnmTSoggetto(null);

		return cnmTResidenza;
	}

	@Override
	public String toString() {
		return "CnmTSoggetto [idSoggetto=" + idSoggetto + ", codiceFiscale=" + codiceFiscale + ", partitaIva=" + partitaIva + "]";
	}


}
