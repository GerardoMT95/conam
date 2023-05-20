/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_tipo_sollecito database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_sollecito")
@NamedQuery(name="CnmDTipoSollecito.findAll", query="SELECT c FROM CnmDTipoSollecito c")
public class CnmDTipoSollecito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_sollecito")
	private long idTipoSollecito;

	@Column(name="desc_tipo_sollecito")
	private String descTipoSollecito;

	//bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy="cnmDTipoSollecito")
	private List<CnmTSollecito> cnmTSollecitos;

	public CnmDTipoSollecito() {
	}

	public long getIdTipoSollecito() {
		return this.idTipoSollecito;
	}

	public void setIdTipoSollecito(long idTipoSollecito) {
		this.idTipoSollecito = idTipoSollecito;
	}

	public String getDescTipoSollecito() {
		return this.descTipoSollecito;
	}

	public void setDescTipoSollecito(String descTipoSollecito) {
		this.descTipoSollecito = descTipoSollecito;
	}

	public List<CnmTSollecito> getCnmTSollecitos() {
		return this.cnmTSollecitos;
	}

	public void setCnmTSollecitos(List<CnmTSollecito> cnmTSollecitos) {
		this.cnmTSollecitos = cnmTSollecitos;
	}

	public CnmTSollecito addCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().add(cnmTSollecito);
		cnmTSollecito.setCnmDTipoSollecito(this);

		return cnmTSollecito;
	}

	public CnmTSollecito removeCnmTSollecito(CnmTSollecito cnmTSollecito) {
		getCnmTSollecitos().remove(cnmTSollecito);
		cnmTSollecito.setCnmDTipoSollecito(null);

		return cnmTSollecito;
	}

}
