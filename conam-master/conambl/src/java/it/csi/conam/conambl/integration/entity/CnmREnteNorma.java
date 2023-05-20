/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_r_ente_norma database table.
 * 
 */
@Entity
@Table(name="cnm_r_ente_norma")
@NamedQuery(name="CnmREnteNorma.findAll", query="SELECT c FROM CnmREnteNorma c")
public class CnmREnteNorma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ente_norma")
	private Integer idEnteNorma;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	private Boolean eliminato;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDArticolo
	@OneToMany(mappedBy="cnmREnteNorma")
	private List<CnmDArticolo> cnmDArticolos;

	//bi-directional many-to-one association to CnmDEnte
	@ManyToOne
	@JoinColumn(name="id_ente")
	private CnmDEnte cnmDEnte;

	//bi-directional many-to-one association to CnmDNorma
	@ManyToOne
	@JoinColumn(name="id_norma")
	private CnmDNorma cnmDNorma;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	public CnmREnteNorma() {
	}

	public Integer getIdEnteNorma() {
		return this.idEnteNorma;
	}

	public void setIdEnteNorma(Integer idEnteNorma) {
		this.idEnteNorma = idEnteNorma;
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

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public List<CnmDArticolo> getCnmDArticolos() {
		return this.cnmDArticolos;
	}

	public void setCnmDArticolos(List<CnmDArticolo> cnmDArticolos) {
		this.cnmDArticolos = cnmDArticolos;
	}

	public CnmDArticolo addCnmDArticolo(CnmDArticolo cnmDArticolo) {
		getCnmDArticolos().add(cnmDArticolo);
		cnmDArticolo.setCnmREnteNorma(this);

		return cnmDArticolo;
	}

	public CnmDArticolo removeCnmDArticolo(CnmDArticolo cnmDArticolo) {
		getCnmDArticolos().remove(cnmDArticolo);
		cnmDArticolo.setCnmREnteNorma(null);

		return cnmDArticolo;
	}

	public CnmDEnte getCnmDEnte() {
		return this.cnmDEnte;
	}

	public void setCnmDEnte(CnmDEnte cnmDEnte) {
		this.cnmDEnte = cnmDEnte;
	}

	public CnmDNorma getCnmDNorma() {
		return this.cnmDNorma;
	}

	public void setCnmDNorma(CnmDNorma cnmDNorma) {
		this.cnmDNorma = cnmDNorma;
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

}
