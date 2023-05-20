/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_sollecito database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_sollecito")
@NamedQuery(name="CnmDStatoSollecito.findAll", query="SELECT c FROM CnmDStatoSollecito c")
public class CnmDStatoSollecito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_sollecito")
	private long idStatoSollecito;

	@Column(name="desc_stato_sollecito")
	private String descStatoSollecito;

	//bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy="cnmDStatoSollecito")
	private List<CnmTSollecito> cnmTSollecitos;

	public CnmDStatoSollecito() {
	}

	public long getIdStatoSollecito() {
		return this.idStatoSollecito;
	}

	public void setIdStatoSollecito(long idStatoSollecito) {
		this.idStatoSollecito = idStatoSollecito;
	}

	public String getDescStatoSollecito() {
		return this.descStatoSollecito;
	}

	public void setDescStatoSollecito(String descStatoSollecito) {
		this.descStatoSollecito = descStatoSollecito;
	}

	public List<CnmTSollecito> getCnmTSollecitos() {
		return this.cnmTSollecitos;
	}

	public void setCnmTSollecitos(List<CnmTSollecito> cnmTSollecitos) {
		this.cnmTSollecitos = cnmTSollecitos;
	}

	public CnmTSollecito addCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().add(cnmTSollecito);
		cnmTSollecito.setCnmDStatoSollecito(this);

		return cnmTSollecito;
	}

	public CnmTSollecito removeCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().remove(cnmTSollecito);
		cnmTSollecito.setCnmDStatoSollecito(null);

		return cnmTSollecito;
	}

}
