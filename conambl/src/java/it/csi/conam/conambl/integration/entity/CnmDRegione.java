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
 * The persistent class for the cnm_d_regione database table.
 * 
 */
@Entity
@Table(name="cnm_d_regione")
@NamedQuery(name="CnmDRegione.findAll", query="SELECT c FROM CnmDRegione c")
public class CnmDRegione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_regione")
	private long idRegione;

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

	//bi-directional many-to-one association to CnmDProvincia
	@OneToMany(mappedBy="cnmDRegione")
	private List<CnmDProvincia> cnmDProvincias;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione")
	private CnmDNazione cnmDNazione;

	//bi-directional many-to-one association to CnmSProvincia
	@OneToMany(mappedBy="cnmDRegione")
	private List<CnmSProvincia> cnmSProvincias;

	//bi-directional many-to-one association to CnmSRegione
	@OneToMany(mappedBy="cnmDRegione")
	private List<CnmSRegione> cnmSRegiones;

	public CnmDRegione() {
	}

	public long getIdRegione() {
		return this.idRegione;
	}

	public void setIdRegione(long idRegione) {
		this.idRegione = idRegione;
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

	public List<CnmDProvincia> getCnmDProvincias() {
		return this.cnmDProvincias;
	}

	public void setCnmDProvincias(List<CnmDProvincia> cnmDProvincias) {
		this.cnmDProvincias = cnmDProvincias;
	}

	public CnmDProvincia addCnmDProvincia(CnmDProvincia cnmDProvincia) {
		getCnmDProvincias().add(cnmDProvincia);
		cnmDProvincia.setCnmDRegione(this);

		return cnmDProvincia;
	}

	public CnmDProvincia removeCnmDProvincia(CnmDProvincia cnmDProvincia) {
		getCnmDProvincias().remove(cnmDProvincia);
		cnmDProvincia.setCnmDRegione(null);

		return cnmDProvincia;
	}

	public CnmDNazione getCnmDNazione() {
		return this.cnmDNazione;
	}

	public void setCnmDNazione(CnmDNazione cnmDNazione) {
		this.cnmDNazione = cnmDNazione;
	}

	public List<CnmSProvincia> getCnmSProvincias() {
		return this.cnmSProvincias;
	}

	public void setCnmSProvincias(List<CnmSProvincia> cnmSProvincias) {
		this.cnmSProvincias = cnmSProvincias;
	}

	public CnmSProvincia addCnmSProvincia(CnmSProvincia cnmSProvincia) {
		getCnmSProvincias().add(cnmSProvincia);
		cnmSProvincia.setCnmDRegione(this);

		return cnmSProvincia;
	}

	public CnmSProvincia removeCnmSProvincia(CnmSProvincia cnmSProvincia) {
		getCnmSProvincias().remove(cnmSProvincia);
		cnmSProvincia.setCnmDRegione(null);

		return cnmSProvincia;
	}

	public List<CnmSRegione> getCnmSRegiones() {
		return this.cnmSRegiones;
	}

	public void setCnmSRegiones(List<CnmSRegione> cnmSRegiones) {
		this.cnmSRegiones = cnmSRegiones;
	}

	public CnmSRegione addCnmSRegione(CnmSRegione cnmSRegione) {
		getCnmSRegiones().add(cnmSRegione);
		cnmSRegione.setCnmDRegione(this);

		return cnmSRegione;
	}

	public CnmSRegione removeCnmSRegione(CnmSRegione cnmSRegione) {
		getCnmSRegiones().remove(cnmSRegione);
		cnmSRegione.setCnmDRegione(null);

		return cnmSRegione;
	}

}
