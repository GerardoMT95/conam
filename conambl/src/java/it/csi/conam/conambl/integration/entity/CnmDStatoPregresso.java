/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_pregresso database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_pregresso")
@NamedQuery(name="CnmDStatoPregresso.findAll", query="SELECT c FROM CnmDStatoPregresso c")
public class CnmDStatoPregresso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_pregresso")
	private long idStatoPregresso;

	@Column(name="desc_stato_pregresso")
	private String descStatoPregresso;

	//bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy="cnmDStatoPregresso")
	private List<CnmTVerbale> cnmTVerbales;

	public CnmDStatoPregresso() {
	}
	
	public long getIdStatoPregresso() {
		return idStatoPregresso;
	}

	public void setIdStatoPregresso(long idStatoPregresso) {
		this.idStatoPregresso = idStatoPregresso;
	}

	public String getDescStatoPregresso() {
		return descStatoPregresso;
	}

	public void setDescStatoPregresso(String descStatoPregresso) {
		this.descStatoPregresso = descStatoPregresso;
	}

	public List<CnmTVerbale> getCnmTVerbales() {
		return this.cnmTVerbales;
	}

	public void setCnmTVerbales(List<CnmTVerbale> cnmTVerbales) {
		this.cnmTVerbales = cnmTVerbales;
	}

	public CnmTVerbale addCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().add(cnmTVerbale);
		cnmTVerbale.setCnmDStatoPregresso(this);

		return cnmTVerbale;
	}

	public CnmTVerbale removeCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().remove(cnmTVerbale);
		cnmTVerbale.setCnmDStatoPregresso(null);

		return cnmTVerbale;
	}

}
