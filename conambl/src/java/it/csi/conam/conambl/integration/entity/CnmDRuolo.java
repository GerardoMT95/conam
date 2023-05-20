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
 * The persistent class for the cnm_d_ruolo database table.
 * 
 */
@Entity
@Table(name="cnm_d_ruolo")
@NamedQuery(name="CnmDRuolo.findAll", query="SELECT c FROM CnmDRuolo c")
public class CnmDRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ruolo")
	private long idRuolo;

	@Column(name="desc_ruolo")
	private String descRuolo;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmRUseCaseRuolo
	@OneToMany(mappedBy="cnmDRuolo")
	private List<CnmRUseCaseRuolo> cnmRUseCaseRuolos;

	//bi-directional many-to-one association to CnmTUser
	@OneToMany(mappedBy="cnmDRuolo")
	private List<CnmTUser> cnmTUsers;

	public CnmDRuolo() {
	}

	public long getIdRuolo() {
		return this.idRuolo;
	}

	public void setIdRuolo(long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescRuolo() {
		return this.descRuolo;
	}

	public void setDescRuolo(String descRuolo) {
		this.descRuolo = descRuolo;
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

	public List<CnmRUseCaseRuolo> getCnmRUseCaseRuolos() {
		return this.cnmRUseCaseRuolos;
	}

	public void setCnmRUseCaseRuolos(List<CnmRUseCaseRuolo> cnmRUseCaseRuolos) {
		this.cnmRUseCaseRuolos = cnmRUseCaseRuolos;
	}

	public CnmRUseCaseRuolo addCnmRUseCaseRuolo(CnmRUseCaseRuolo cnmRUseCaseRuolo) {
		getCnmRUseCaseRuolos().add(cnmRUseCaseRuolo);
		cnmRUseCaseRuolo.setCnmDRuolo(this);

		return cnmRUseCaseRuolo;
	}

	public CnmRUseCaseRuolo removeCnmRUseCaseRuolo(CnmRUseCaseRuolo cnmRUseCaseRuolo) {
		getCnmRUseCaseRuolos().remove(cnmRUseCaseRuolo);
		cnmRUseCaseRuolo.setCnmDRuolo(null);

		return cnmRUseCaseRuolo;
	}

	public List<CnmTUser> getCnmTUsers() {
		return this.cnmTUsers;
	}

	public void setCnmTUsers(List<CnmTUser> cnmTUsers) {
		this.cnmTUsers = cnmTUsers;
	}

	public CnmTUser addCnmTUser(CnmTUser cnmTUser) {
		getCnmTUsers().add(cnmTUser);
		cnmTUser.setCnmDRuolo(this);

		return cnmTUser;
	}

	public CnmTUser removeCnmTUser(CnmTUser cnmTUser) {
		getCnmTUsers().remove(cnmTUser);
		cnmTUser.setCnmDRuolo(null);

		return cnmTUser;
	}

}
