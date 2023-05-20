/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_riscossione database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_riscossione")
@NamedQuery(name="CnmDStatoRiscossione.findAll", query="SELECT c FROM CnmDStatoRiscossione c")
public class CnmDStatoRiscossione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_riscossione")
	private long idStatoRiscossione;

	@Column(name="desc_stato_riscossione")
	private String descStatoRiscossione;

	//bi-directional many-to-one association to CnmTRiscossione
	@OneToMany(mappedBy="cnmDStatoRiscossione")
	private List<CnmTRiscossione> cnmTRiscossiones;

	public CnmDStatoRiscossione() {
	}

	public long getIdStatoRiscossione() {
		return this.idStatoRiscossione;
	}

	public void setIdStatoRiscossione(long idStatoRiscossione) {
		this.idStatoRiscossione = idStatoRiscossione;
	}

	public String getDescStatoRiscossione() {
		return this.descStatoRiscossione;
	}

	public void setDescStatoRiscossione(String descStatoRiscossione) {
		this.descStatoRiscossione = descStatoRiscossione;
	}

	public List<CnmTRiscossione> getCnmTRiscossiones() {
		return this.cnmTRiscossiones;
	}

	public void setCnmTRiscossiones(List<CnmTRiscossione> cnmTRiscossiones) {
		this.cnmTRiscossiones = cnmTRiscossiones;
	}

	public CnmTRiscossione addCnmTRiscossione(CnmTRiscossione cnmTRiscossione) {
		getCnmTRiscossiones().add(cnmTRiscossione);
		cnmTRiscossione.setCnmDStatoRiscossione(this);

		return cnmTRiscossione;
	}

	public CnmTRiscossione removeCnmTRiscossione(CnmTRiscossione cnmTRiscossione) {
		getCnmTRiscossiones().remove(cnmTRiscossione);
		cnmTRiscossione.setCnmDStatoRiscossione(null);

		return cnmTRiscossione;
	}

}
