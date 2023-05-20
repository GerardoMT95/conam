/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_r_use_case_ruolo database table.
 * 
 */
@Entity
@Table(name="cnm_r_use_case_ruolo")
@NamedQuery(name="CnmRUseCaseRuolo.findAll", query="SELECT c FROM CnmRUseCaseRuolo c")
public class CnmRUseCaseRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_use_case_ruolo")
	private long idUseCaseRuolo;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDRuolo
	@ManyToOne
	@JoinColumn(name="id_ruolo")
	private CnmDRuolo cnmDRuolo;

	//bi-directional many-to-one association to CnmDUseCase
	@ManyToOne
	@JoinColumn(name="id_use_case")
	private CnmDUseCase cnmDUseCase;

	public CnmRUseCaseRuolo() {
	}

	public long getIdUseCaseRuolo() {
		return this.idUseCaseRuolo;
	}

	public void setIdUseCaseRuolo(long idUseCaseRuolo) {
		this.idUseCaseRuolo = idUseCaseRuolo;
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

	public CnmDRuolo getCnmDRuolo() {
		return this.cnmDRuolo;
	}

	public void setCnmDRuolo(CnmDRuolo cnmDRuolo) {
		this.cnmDRuolo = cnmDRuolo;
	}

	public CnmDUseCase getCnmDUseCase() {
		return this.cnmDUseCase;
	}

	public void setCnmDUseCase(CnmDUseCase cnmDUseCase) {
		this.cnmDUseCase = cnmDUseCase;
	}

}
