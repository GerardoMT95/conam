/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_utilizzo_allegato database table.
 * 
 */
@Entity
@Table(name="cnm_d_utilizzo_allegato")
@NamedQuery(name="CnmDUtilizzoAllegato.findAll", query="SELECT c FROM CnmDUtilizzoAllegato c")
public class CnmDUtilizzoAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utilizzo_allegato")
	private long idUtilizzoAllegato;

	@Column(name="desc_utilizzo_allegato")
	private String descUtilizzoAllegato;

	//bi-directional many-to-one association to CnmDTipoAllegato
	@OneToMany(mappedBy="cnmDUtilizzoAllegato")
	private List<CnmDTipoAllegato> cnmDTipoAllegatos;

	public CnmDUtilizzoAllegato() {
	}

	public long getIdUtilizzoAllegato() {
		return this.idUtilizzoAllegato;
	}

	public void setIdUtilizzoAllegato(long idUtilizzoAllegato) {
		this.idUtilizzoAllegato = idUtilizzoAllegato;
	}

	public String getDescUtilizzoAllegato() {
		return this.descUtilizzoAllegato;
	}

	public void setDescUtilizzoAllegato(String descUtilizzoAllegato) {
		this.descUtilizzoAllegato = descUtilizzoAllegato;
	}

	public List<CnmDTipoAllegato> getCnmDTipoAllegatos() {
		return this.cnmDTipoAllegatos;
	}

	public void setCnmDTipoAllegatos(List<CnmDTipoAllegato> cnmDTipoAllegatos) {
		this.cnmDTipoAllegatos = cnmDTipoAllegatos;
	}

	public CnmDTipoAllegato addCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		getCnmDTipoAllegatos().add(cnmDTipoAllegato);
		cnmDTipoAllegato.setCnmDUtilizzoAllegato(this);

		return cnmDTipoAllegato;
	}

	public CnmDTipoAllegato removeCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		getCnmDTipoAllegatos().remove(cnmDTipoAllegato);
		cnmDTipoAllegato.setCnmDUtilizzoAllegato(null);

		return cnmDTipoAllegato;
	}

}
