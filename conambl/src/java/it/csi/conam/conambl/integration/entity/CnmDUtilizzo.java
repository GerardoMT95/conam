/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_utilizzo database table.
 * 
 */
@Entity
@Table(name="cnm_d_utilizzo")
@NamedQuery(name="CnmDUtilizzo.findAll", query="SELECT c FROM CnmDUtilizzo c")
public class CnmDUtilizzo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utilizzo")
	private long idUtilizzo;

	@Column(name="desc_utilizzo")
	private String descUtilizzo;

	//bi-directional many-to-one association to CnmRUserEnte
	@OneToMany(mappedBy="cnmDUtilizzo")
	private List<CnmRUserEnte> cnmRUserEntes;

	public CnmDUtilizzo() {
	}

	public long getIdUtilizzo() {
		return this.idUtilizzo;
	}

	public void setIdUtilizzo(long idUtilizzo) {
		this.idUtilizzo = idUtilizzo;
	}

	public String getDescUtilizzo() {
		return this.descUtilizzo;
	}

	public void setDescUtilizzo(String descUtilizzo) {
		this.descUtilizzo = descUtilizzo;
	}

	public List<CnmRUserEnte> getCnmRUserEntes() {
		return this.cnmRUserEntes;
	}

	public void setCnmRUserEntes(List<CnmRUserEnte> cnmRUserEntes) {
		this.cnmRUserEntes = cnmRUserEntes;
	}

	public CnmRUserEnte addCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().add(cnmRUserEnte);
		cnmRUserEnte.setCnmDUtilizzo(this);

		return cnmRUserEnte;
	}

	public CnmRUserEnte removeCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().remove(cnmRUserEnte);
		cnmRUserEnte.setCnmDUtilizzo(null);

		return cnmRUserEnte;
	}

}
