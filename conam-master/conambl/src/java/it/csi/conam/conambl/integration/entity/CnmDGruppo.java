/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_gruppo database table.
 * 
 */
@Entity
@Table(name="cnm_d_gruppo")
@NamedQuery(name="CnmDGruppo.findAll", query="SELECT c FROM CnmDGruppo c")
public class CnmDGruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_gruppo")
	private long idGruppo;

	@Column(name="desc_gruppo")
	private String descGruppo;

	//bi-directional many-to-one association to CnmTCalendario
	@OneToMany(mappedBy="cnmDGruppo")
	private List<CnmTCalendario> cnmTCalendarios;

	//bi-directional many-to-one association to CnmTUser
	@OneToMany(mappedBy="cnmDGruppo")
	private List<CnmTUser> cnmTUsers;

	public CnmDGruppo() {
	}

	public long getIdGruppo() {
		return this.idGruppo;
	}

	public void setIdGruppo(long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public String getDescGruppo() {
		return this.descGruppo;
	}

	public void setDescGruppo(String descGruppo) {
		this.descGruppo = descGruppo;
	}

	public List<CnmTCalendario> getCnmTCalendarios() {
		return this.cnmTCalendarios;
	}

	public void setCnmTCalendarios(List<CnmTCalendario> cnmTCalendarios) {
		this.cnmTCalendarios = cnmTCalendarios;
	}

	public CnmTCalendario addCnmTCalendario(CnmTCalendario cnmTCalendario) {
		getCnmTCalendarios().add(cnmTCalendario);
		cnmTCalendario.setCnmDGruppo(this);

		return cnmTCalendario;
	}

	public CnmTCalendario removeCnmTCalendario(CnmTCalendario cnmTCalendario) {
		getCnmTCalendarios().remove(cnmTCalendario);
		cnmTCalendario.setCnmDGruppo(null);

		return cnmTCalendario;
	}

	public List<CnmTUser> getCnmTUsers() {
		return this.cnmTUsers;
	}

	public void setCnmTUsers(List<CnmTUser> cnmTUsers) {
		this.cnmTUsers = cnmTUsers;
	}

	public CnmTUser addCnmTUser(CnmTUser cnmTUser) {
		getCnmTUsers().add(cnmTUser);
		cnmTUser.setCnmDGruppo(this);

		return cnmTUser;
	}

	public CnmTUser removeCnmTUser(CnmTUser cnmTUser) {
		getCnmTUsers().remove(cnmTUser);
		cnmTUser.setCnmDGruppo(null);

		return cnmTUser;
	}

}
