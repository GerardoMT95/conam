/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_d_comune database table.
 * 
 */
@Entity
@Table(name="cnm_d_comune")
@NamedQuery(name="CnmDComune.findAll", query="SELECT c FROM CnmDComune c")
public class CnmDComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_comune")
	private long idComune;

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

	//bi-directional many-to-one association to CnmDProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private CnmDProvincia cnmDProvincia;

	//bi-directional many-to-one association to CnmSComune
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmSComune> cnmSComunes;

	//bi-directional many-to-one association to CnmTCalendario
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmTCalendario> cnmTCalendarios;

	//bi-directional many-to-one association to CnmTPersona
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmTPersona> cnmTPersonas;

	//bi-directional many-to-one association to CnmTSocieta
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmTSocieta> cnmTSocietas;

	//bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmTVerbale> cnmTVerbales;

	//bi-directional many-to-one association to CnmTResidenza
	@OneToMany(mappedBy="cnmDComune")
	private List<CnmTResidenza> cnmTResidenzas;

	public CnmDComune() {
	}

	public long getIdComune() {
		return this.idComune;
	}

	public void setIdComune(long idComune) {
		this.idComune = idComune;
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

	public CnmDProvincia getCnmDProvincia() {
		return this.cnmDProvincia;
	}

	public void setCnmDProvincia(CnmDProvincia cnmDProvincia) {
		this.cnmDProvincia = cnmDProvincia;
	}

	public List<CnmSComune> getCnmSComunes() {
		return this.cnmSComunes;
	}

	public void setCnmSComunes(List<CnmSComune> cnmSComunes) {
		this.cnmSComunes = cnmSComunes;
	}

	public CnmSComune addCnmSComune(CnmSComune cnmSComune) {
		getCnmSComunes().add(cnmSComune);
		cnmSComune.setCnmDComune(this);

		return cnmSComune;
	}

	public CnmSComune removeCnmSComune(CnmSComune cnmSComune) {
		getCnmSComunes().remove(cnmSComune);
		cnmSComune.setCnmDComune(null);

		return cnmSComune;
	}

	public List<CnmTCalendario> getCnmTCalendarios() {
		return this.cnmTCalendarios;
	}

	public void setCnmTCalendarios(List<CnmTCalendario> cnmTCalendarios) {
		this.cnmTCalendarios = cnmTCalendarios;
	}

	public CnmTCalendario addCnmTCalendario(CnmTCalendario cnmTCalendario) {
		getCnmTCalendarios().add(cnmTCalendario);
		cnmTCalendario.setCnmDComune(this);

		return cnmTCalendario;
	}

	public CnmTCalendario removeCnmTCalendario(CnmTCalendario cnmTCalendario) {
		getCnmTCalendarios().remove(cnmTCalendario);
		cnmTCalendario.setCnmDComune(null);

		return cnmTCalendario;
	}

	public List<CnmTPersona> getCnmTPersonas() {
		return this.cnmTPersonas;
	}

	public void setCnmTPersonas(List<CnmTPersona> cnmTPersonas) {
		this.cnmTPersonas = cnmTPersonas;
	}

	public CnmTPersona addCnmTPersona(CnmTPersona cnmTPersona) {
		getCnmTPersonas().add(cnmTPersona);
		cnmTPersona.setCnmDComune(this);

		return cnmTPersona;
	}

	public CnmTPersona removeCnmTPersona(CnmTPersona cnmTPersona) {
		getCnmTPersonas().remove(cnmTPersona);
		cnmTPersona.setCnmDComune(null);

		return cnmTPersona;
	}

	public List<CnmTSocieta> getCnmTSocietas() {
		return this.cnmTSocietas;
	}

	public void setCnmTSocietas(List<CnmTSocieta> cnmTSocietas) {
		this.cnmTSocietas = cnmTSocietas;
	}

	public CnmTSocieta addCnmTSocieta(CnmTSocieta cnmTSocieta) {
		getCnmTSocietas().add(cnmTSocieta);
		cnmTSocieta.setCnmDComune(this);

		return cnmTSocieta;
	}

	public CnmTSocieta removeCnmTSocieta(CnmTSocieta cnmTSocieta) {
		getCnmTSocietas().remove(cnmTSocieta);
		cnmTSocieta.setCnmDComune(null);

		return cnmTSocieta;
	}

	public List<CnmTVerbale> getCnmTVerbales() {
		return this.cnmTVerbales;
	}

	public void setCnmTVerbales(List<CnmTVerbale> cnmTVerbales) {
		this.cnmTVerbales = cnmTVerbales;
	}

	public CnmTVerbale addCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().add(cnmTVerbale);
		cnmTVerbale.setCnmDComune(this);

		return cnmTVerbale;
	}

	public CnmTVerbale removeCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().remove(cnmTVerbale);
		cnmTVerbale.setCnmDComune(null);

		return cnmTVerbale;
	}

	public List<CnmTResidenza> getCnmTResidenzas() {
		return this.cnmTResidenzas;
	}

	public void setCnmTResidenzas(List<CnmTResidenza> cnmTResidenzas) {
		this.cnmTResidenzas = cnmTResidenzas;
	}

	public CnmTResidenza addCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().add(cnmTResidenza);
		cnmTResidenza.setCnmDComune(this);

		return cnmTResidenza;
	}

	public CnmTResidenza removeCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().remove(cnmTResidenza);
		cnmTResidenza.setCnmDComune(null);

		return cnmTResidenza;
	}

}
