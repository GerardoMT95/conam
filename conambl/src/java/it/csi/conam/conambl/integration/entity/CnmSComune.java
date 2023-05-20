/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cnm_s_comune database table.
 * 
 */
@Entity
@Table(name="cnm_s_comune")
@NamedQuery(name="CnmSComune.findAll", query="SELECT c FROM CnmSComune c")
public class CnmSComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_comune")
	private long idSComune;

	@Column(name="cod_belfiore_comune")
	private String codBelfioreComune;

	@Column(name="cod_istat_comune")
	private String codIstatComune;

	@Column(name="denom_comune")
	private String denomComune;

	@Column(name="dt_id_comune")
	private BigDecimal dtIdComune;

	@Column(name="dt_id_comune_next")
	private BigDecimal dtIdComuneNext;

	@Column(name="dt_id_comune_prev")
	private BigDecimal dtIdComunePrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private CnmDProvincia cnmDProvincia;

	public CnmSComune() {
	}

	public long getIdSComune() {
		return this.idSComune;
	}

	public void setIdSComune(long idSComune) {
		this.idSComune = idSComune;
	}

	public String getCodBelfioreComune() {
		return this.codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public String getCodIstatComune() {
		return this.codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getDenomComune() {
		return this.denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public BigDecimal getDtIdComune() {
		return this.dtIdComune;
	}

	public void setDtIdComune(BigDecimal dtIdComune) {
		this.dtIdComune = dtIdComune;
	}

	public BigDecimal getDtIdComuneNext() {
		return this.dtIdComuneNext;
	}

	public void setDtIdComuneNext(BigDecimal dtIdComuneNext) {
		this.dtIdComuneNext = dtIdComuneNext;
	}

	public BigDecimal getDtIdComunePrev() {
		return this.dtIdComunePrev;
	}

	public void setDtIdComunePrev(BigDecimal dtIdComunePrev) {
		this.dtIdComunePrev = dtIdComunePrev;
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

	public CnmDComune getCnmDComune() {
		return this.cnmDComune;
	}

	public void setCnmDComune(CnmDComune cnmDComune) {
		this.cnmDComune = cnmDComune;
	}

	public CnmDProvincia getCnmDProvincia() {
		return this.cnmDProvincia;
	}

	public void setCnmDProvincia(CnmDProvincia cnmDProvincia) {
		this.cnmDProvincia = cnmDProvincia;
	}

}
