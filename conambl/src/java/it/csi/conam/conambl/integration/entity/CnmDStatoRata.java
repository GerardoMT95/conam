/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_rata database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_rata")
@NamedQuery(name="CnmDStatoRata.findAll", query="SELECT c FROM CnmDStatoRata c")
public class CnmDStatoRata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_rata")
	private long idStatoRata;

	@Column(name="desc_stato_rata")
	private String descStatoRata;

	//bi-directional many-to-one association to CnmRSoggRata
	@OneToMany(mappedBy="cnmDStatoRata")
	private List<CnmRSoggRata> cnmRSoggRatas;

	public CnmDStatoRata() {
	}

	public long getIdStatoRata() {
		return this.idStatoRata;
	}

	public void setIdStatoRata(long idStatoRata) {
		this.idStatoRata = idStatoRata;
	}

	public String getDescStatoRata() {
		return this.descStatoRata;
	}

	public void setDescStatoRata(String descStatoRata) {
		this.descStatoRata = descStatoRata;
	}

	public List<CnmRSoggRata> getCnmRSoggRatas() {
		return this.cnmRSoggRatas;
	}

	public void setCnmRSoggRatas(List<CnmRSoggRata> cnmRSoggRatas) {
		this.cnmRSoggRatas = cnmRSoggRatas;
	}

	public CnmRSoggRata addCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().add(cnmRSoggRata);
		cnmRSoggRata.setCnmDStatoRata(this);

		return cnmRSoggRata;
	}

	public CnmRSoggRata removeCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().remove(cnmRSoggRata);
		cnmRSoggRata.setCnmDStatoRata(null);

		return cnmRSoggRata;
	}

}
