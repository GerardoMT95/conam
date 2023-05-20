/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_t_residenza database table.
 * 
 */
@Entity
@Table(name="cnm_t_residenza")
@NamedQuery(name="CnmTResidenza.findAll", query="SELECT c FROM CnmTResidenza c")
public class CnmTResidenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_residenza")
	private Integer idResidenza;

	@Column(name="cap_residenza")
	private String capResidenza;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="denom_comune_residenza_estero")
	private String denomComuneResidenzaEstero;

	@Column(name="indirizzo_residenza")
	private String indirizzoResidenza;

	@Column(name="numero_civico_residenza")
	private String numeroCivicoResidenza;

	@Column(name="residenza_estera")
	private Boolean residenzaEstera;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune_residenza")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione_residenza")
	private CnmDNazione cnmDNazione;

	//bi-directional many-to-one association to CnmTSoggetto
	@ManyToOne
	@JoinColumn(name="id_soggetto")
	private CnmTSoggetto cnmTSoggetto;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;
	
	// 20200910 PP - inserisco il riferimento al verbale, poiche' per i fascicoli pregressi un soggetto 
	//               potrebbe avere una resisdenza diversa da quella STAS. Quindi permetto la modifica alla residenza e ne 
	//               una nuova legandola al verbale. 
	//               Questo causa la modifica alla unique sulla tabella che includera' anche id_verbale, quindi la residenza 
	//               sara' unica per suggetto sul singolo verbale.
	@Column(name="id_verbale")
	private Integer idVerbale = 0;

	public CnmTResidenza() {
	}

	public Integer getIdResidenza() {
		return this.idResidenza;
	}

	public void setIdResidenza(Integer idResidenza) {
		this.idResidenza = idResidenza;
	}

	public String getCapResidenza() {
		return this.capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
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

	public String getDenomComuneResidenzaEstero() {
		return this.denomComuneResidenzaEstero;
	}

	public void setDenomComuneResidenzaEstero(String denomComuneResidenzaEstero) {
		this.denomComuneResidenzaEstero = denomComuneResidenzaEstero;
	}

	public String getIndirizzoResidenza() {
		return this.indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getNumeroCivicoResidenza() {
		return this.numeroCivicoResidenza;
	}

	public void setNumeroCivicoResidenza(String numeroCivicoResidenza) {
		this.numeroCivicoResidenza = numeroCivicoResidenza;
	}

	public Boolean getResidenzaEstera() {
		return this.residenzaEstera;
	}

	public void setResidenzaEstera(Boolean residenzaEstera) {
		this.residenzaEstera = residenzaEstera;
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

	public CnmTSoggetto getCnmTSoggetto() {
		return this.cnmTSoggetto;
	}

	public void setCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		this.cnmTSoggetto = cnmTSoggetto;
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

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

}
