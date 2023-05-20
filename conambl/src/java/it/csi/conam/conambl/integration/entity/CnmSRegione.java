/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_s_regione database table.
 * 
 */
@Entity
@Table(name="cnm_s_regione")
@NamedQuery(name="CnmSRegione.findAll", query="SELECT c FROM CnmSRegione c")
public class CnmSRegione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_regione")
	private long idSRegione;

	@Column(name="cod_regione")
	private String codRegione;

	@Column(name="denom_regione")
	private String denomRegione;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione")
	private CnmDNazione cnmDNazione;

	//bi-directional many-to-one association to CnmDRegione
	@ManyToOne
	@JoinColumn(name="id_regione")
	private CnmDRegione cnmDRegione;

	public CnmSRegione() {
	}

	public long getIdSRegione() {
		return this.idSRegione;
	}

	public void setIdSRegione(long idSRegione) {
		this.idSRegione = idSRegione;
	}

	public String getCodRegione() {
		return this.codRegione;
	}

	public void setCodRegione(String codRegione) {
		this.codRegione = codRegione;
	}

	public String getDenomRegione() {
		return this.denomRegione;
	}

	public void setDenomRegione(String denomRegione) {
		this.denomRegione = denomRegione;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public CnmDNazione getCnmDNazione() {
		return this.cnmDNazione;
	}

	public void setCnmDNazione(CnmDNazione cnmDNazione) {
		this.cnmDNazione = cnmDNazione;
	}

	public CnmDRegione getCnmDRegione() {
		return this.cnmDRegione;
	}

	public void setCnmDRegione(CnmDRegione cnmDRegione) {
		this.cnmDRegione = cnmDRegione;
	}

}
