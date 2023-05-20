/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cnm_t_societa database table.
 * 
 */
@Entity
@Table(name="cnm_t_societa")
@NamedQuery(name="CnmTSocieta.findAll", query="SELECT c FROM CnmTSocieta c")
public class CnmTSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_societa")
	private Integer idSocieta;

	@Column(name="cap_sede")
	private String capSede;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="denom_comune_sede_estero")
	private String denomComuneSedeEstero;

	@Column(name="indirizzo_sede")
	private String indirizzoSede;

	@Column(name="numero_civico_sede")
	private String numeroCivicoSede;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune_sede")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione_sede")
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
	@OneToMany(mappedBy="cnmTSocieta")
	private List<CnmTSoggetto> cnmTSoggettos;

	public CnmTSocieta() {
	}

	public Integer getIdSocieta() {
		return this.idSocieta;
	}

	public void setIdSocieta(Integer idSocieta) {
		this.idSocieta = idSocieta;
	}

	public String getCapSede() {
		return this.capSede;
	}

	public void setCapSede(String capSede) {
		this.capSede = capSede;
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

	public String getDenomComuneSedeEstero() {
		return this.denomComuneSedeEstero;
	}

	public void setDenomComuneSedeEstero(String denomComuneSedeEstero) {
		this.denomComuneSedeEstero = denomComuneSedeEstero;
	}

	public String getIndirizzoSede() {
		return this.indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public String getNumeroCivicoSede() {
		return this.numeroCivicoSede;
	}

	public void setNumeroCivicoSede(String numeroCivicoSede) {
		this.numeroCivicoSede = numeroCivicoSede;
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
		cnmTSoggetto.setCnmTSocieta(this);

		return cnmTSoggetto;
	}

	public CnmTSoggetto removeCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		getCnmTSoggettos().remove(cnmTSoggetto);
		cnmTSoggetto.setCnmTSocieta(null);

		return cnmTSoggetto;
	}

}
