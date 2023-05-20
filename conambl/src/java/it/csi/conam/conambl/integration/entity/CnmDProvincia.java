/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_d_provincia database table.
 * 
 */
@Entity
@Table(name="cnm_d_provincia")
@NamedQuery(name="CnmDProvincia.findAll", query="SELECT c FROM CnmDProvincia c")
public class CnmDProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_provincia")
	private long idProvincia;

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

	//bi-directional many-to-one association to CnmDComune
	@OneToMany(mappedBy="cnmDProvincia")
	private List<CnmDComune> cnmDComunes;

	//bi-directional many-to-one association to CnmDRegione
	@ManyToOne
	@JoinColumn(name="id_regione")
	private CnmDRegione cnmDRegione;

	//bi-directional many-to-one association to CnmSComune
	@OneToMany(mappedBy="cnmDProvincia")
	private List<CnmSComune> cnmSComunes;

	//bi-directional many-to-one association to CnmSProvincia
	@OneToMany(mappedBy="cnmDProvincia")
	private List<CnmSProvincia> cnmSProvincias;

	public CnmDProvincia() {
	}

	public long getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
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

	public List<CnmDComune> getCnmDComunes() {
		return this.cnmDComunes;
	}

	public void setCnmDComunes(List<CnmDComune> cnmDComunes) {
		this.cnmDComunes = cnmDComunes;
	}

	public CnmDComune addCnmDComune(CnmDComune cnmDComune) {
		getCnmDComunes().add(cnmDComune);
		cnmDComune.setCnmDProvincia(this);

		return cnmDComune;
	}

	public CnmDComune removeCnmDComune(CnmDComune cnmDComune) {
		getCnmDComunes().remove(cnmDComune);
		cnmDComune.setCnmDProvincia(null);

		return cnmDComune;
	}

	public CnmDRegione getCnmDRegione() {
		return this.cnmDRegione;
	}

	public void setCnmDRegione(CnmDRegione cnmDRegione) {
		this.cnmDRegione = cnmDRegione;
	}

	public List<CnmSComune> getCnmSComunes() {
		return this.cnmSComunes;
	}

	public void setCnmSComunes(List<CnmSComune> cnmSComunes) {
		this.cnmSComunes = cnmSComunes;
	}

	public CnmSComune addCnmSComune(CnmSComune cnmSComune) {
		getCnmSComunes().add(cnmSComune);
		cnmSComune.setCnmDProvincia(this);

		return cnmSComune;
	}

	public CnmSComune removeCnmSComune(CnmSComune cnmSComune) {
		getCnmSComunes().remove(cnmSComune);
		cnmSComune.setCnmDProvincia(null);

		return cnmSComune;
	}

	public List<CnmSProvincia> getCnmSProvincias() {
		return this.cnmSProvincias;
	}

	public void setCnmSProvincias(List<CnmSProvincia> cnmSProvincias) {
		this.cnmSProvincias = cnmSProvincias;
	}

	public CnmSProvincia addCnmSProvincia(CnmSProvincia cnmSProvincia) {
		getCnmSProvincias().add(cnmSProvincia);
		cnmSProvincia.setCnmDProvincia(this);

		return cnmSProvincia;
	}

	public CnmSProvincia removeCnmSProvincia(CnmSProvincia cnmSProvincia) {
		getCnmSProvincias().remove(cnmSProvincia);
		cnmSProvincia.setCnmDProvincia(null);

		return cnmSProvincia;
	}

}
