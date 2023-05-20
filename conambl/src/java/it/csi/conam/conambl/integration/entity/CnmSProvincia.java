/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_s_provincia database table.
 * 
 */
@Entity
@Table(name="cnm_s_provincia")
@NamedQuery(name="CnmSProvincia.findAll", query="SELECT c FROM CnmSProvincia c")
public class CnmSProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_provincia")
	private long idSProvincia;

	@Column(name="cod_provincia")
	private String codProvincia;

	@Column(name="denom_provincia")
	private String denomProvincia;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	@Column(name="sigla_provincia")
	private String siglaProvincia;

	//bi-directional many-to-one association to CnmDProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private CnmDProvincia cnmDProvincia;

	//bi-directional many-to-one association to CnmDRegione
	@ManyToOne
	@JoinColumn(name="id_regione")
	private CnmDRegione cnmDRegione;

	public CnmSProvincia() {
	}

	public long getIdSProvincia() {
		return this.idSProvincia;
	}

	public void setIdSProvincia(long idSProvincia) {
		this.idSProvincia = idSProvincia;
	}

	public String getCodProvincia() {
		return this.codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getDenomProvincia() {
		return this.denomProvincia;
	}

	public void setDenomProvincia(String denomProvincia) {
		this.denomProvincia = denomProvincia;
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

	public String getSiglaProvincia() {
		return this.siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public CnmDProvincia getCnmDProvincia() {
		return this.cnmDProvincia;
	}

	public void setCnmDProvincia(CnmDProvincia cnmDProvincia) {
		this.cnmDProvincia = cnmDProvincia;
	}

	public CnmDRegione getCnmDRegione() {
		return this.cnmDRegione;
	}

	public void setCnmDRegione(CnmDRegione cnmDRegione) {
		this.cnmDRegione = cnmDRegione;
	}

}
