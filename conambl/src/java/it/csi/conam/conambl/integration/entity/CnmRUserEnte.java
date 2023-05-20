/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_r_user_ente database table.
 * 
 */
@Entity
@Table(name="cnm_r_user_ente")
@NamedQuery(name="CnmRUserEnte.findAll", query="SELECT c FROM CnmRUserEnte c")
public class CnmRUserEnte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user_ente")
	private long idUserEnte;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDEnte
	@ManyToOne
	@JoinColumn(name="id_ente")
	private CnmDEnte cnmDEnte;

	//bi-directional many-to-one association to CnmDUtilizzo
	@ManyToOne
	@JoinColumn(name="id_utilizzo")
	private CnmDUtilizzo cnmDUtilizzo;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user")
	private CnmTUser cnmTUser;

	public CnmRUserEnte() {
	}

	public long getIdUserEnte() {
		return this.idUserEnte;
	}

	public void setIdUserEnte(long idUserEnte) {
		this.idUserEnte = idUserEnte;
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

	public CnmDEnte getCnmDEnte() {
		return this.cnmDEnte;
	}

	public void setCnmDEnte(CnmDEnte cnmDEnte) {
		this.cnmDEnte = cnmDEnte;
	}

	public CnmDUtilizzo getCnmDUtilizzo() {
		return this.cnmDUtilizzo;
	}

	public void setCnmDUtilizzo(CnmDUtilizzo cnmDUtilizzo) {
		this.cnmDUtilizzo = cnmDUtilizzo;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

}
