/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_allegato database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_allegato")
@NamedQuery(name="CnmDStatoAllegato.findAll", query="SELECT c FROM CnmDStatoAllegato c")
public class CnmDStatoAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_allegato")
	private long idStatoAllegato;

	@Column(name="desc_stato_allegato")
	private String descStatoAllegato;

	//bi-directional many-to-one association to CnmTAllegato
	@OneToMany(mappedBy="cnmDStatoAllegato")
	private List<CnmTAllegato> cnmTAllegatos;

	public CnmDStatoAllegato() {
	}

	public long getIdStatoAllegato() {
		return this.idStatoAllegato;
	}

	public void setIdStatoAllegato(long idStatoAllegato) {
		this.idStatoAllegato = idStatoAllegato;
	}

	public String getDescStatoAllegato() {
		return this.descStatoAllegato;
	}

	public void setDescStatoAllegato(String descStatoAllegato) {
		this.descStatoAllegato = descStatoAllegato;
	}

	public List<CnmTAllegato> getCnmTAllegatos() {
		return this.cnmTAllegatos;
	}

	public void setCnmTAllegatos(List<CnmTAllegato> cnmTAllegatos) {
		this.cnmTAllegatos = cnmTAllegatos;
	}

	public CnmTAllegato addCnmTAllegato(CnmTAllegato cnmTAllegato) {
		getCnmTAllegatos().add(cnmTAllegato);
		cnmTAllegato.setCnmDStatoAllegato(this);

		return cnmTAllegato;
	}

	public CnmTAllegato removeCnmTAllegato(CnmTAllegato cnmTAllegato) {
		getCnmTAllegatos().remove(cnmTAllegato);
		cnmTAllegato.setCnmDStatoAllegato(null);

		return cnmTAllegato;
	}

}
