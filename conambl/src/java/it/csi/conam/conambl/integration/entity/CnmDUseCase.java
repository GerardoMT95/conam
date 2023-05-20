/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_use_case database table.
 * 
 */
@Entity
@Table(name="cnm_d_use_case")
@NamedQuery(name="CnmDUseCase.findAll", query="SELECT c FROM CnmDUseCase c")
public class CnmDUseCase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_use_case")
	private long idUseCase;

	@Column(name="cod_use_case")
	private String codUseCase;

	@Column(name="desc_use_case")
	private String descUseCase;

	//bi-directional many-to-one association to CnmRUseCaseRuolo
	@OneToMany(mappedBy="cnmDUseCase")
	private List<CnmRUseCaseRuolo> cnmRUseCaseRuolos;

	public CnmDUseCase() {
	}

	public long getIdUseCase() {
		return this.idUseCase;
	}

	public void setIdUseCase(long idUseCase) {
		this.idUseCase = idUseCase;
	}

	public String getCodUseCase() {
		return this.codUseCase;
	}

	public void setCodUseCase(String codUseCase) {
		this.codUseCase = codUseCase;
	}

	public String getDescUseCase() {
		return this.descUseCase;
	}

	public void setDescUseCase(String descUseCase) {
		this.descUseCase = descUseCase;
	}

	public List<CnmRUseCaseRuolo> getCnmRUseCaseRuolos() {
		return this.cnmRUseCaseRuolos;
	}

	public void setCnmRUseCaseRuolos(List<CnmRUseCaseRuolo> cnmRUseCaseRuolos) {
		this.cnmRUseCaseRuolos = cnmRUseCaseRuolos;
	}

	public CnmRUseCaseRuolo addCnmRUseCaseRuolo(CnmRUseCaseRuolo cnmRUseCaseRuolo) {
		getCnmRUseCaseRuolos().add(cnmRUseCaseRuolo);
		cnmRUseCaseRuolo.setCnmDUseCase(this);

		return cnmRUseCaseRuolo;
	}

	public CnmRUseCaseRuolo removeCnmRUseCaseRuolo(CnmRUseCaseRuolo cnmRUseCaseRuolo) {
		getCnmRUseCaseRuolos().remove(cnmRUseCaseRuolo);
		cnmRUseCaseRuolo.setCnmDUseCase(null);

		return cnmRUseCaseRuolo;
	}

}
