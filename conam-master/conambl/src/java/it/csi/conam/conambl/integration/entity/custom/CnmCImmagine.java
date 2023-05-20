/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity.custom;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the cnm_c_immagine database table.
 * 
 */
@Entity
@Table(name = "cnm_c_immagine")
@NamedQuery(name = "CnmCImmagine.findAll", query = "SELECT c FROM CnmCImmagine c")
public class CnmCImmagine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_immagine")
	private long idImmagine;

	@Column(name = "cod_immagine")
	private String codImmagine;

	@Column(name = "desc_immagine")
	private String descImmagine;

	private byte[] immagine;

	// bi-directional many-to-many association to CnmCReport
	@ManyToMany(mappedBy = "cnmCImmagines")
	private List<CnmCReport> cnmCReports;

	public CnmCImmagine() {
	}

	public long getIdImmagine() {
		return this.idImmagine;
	}

	public void setIdImmagine(long idImmagine) {
		this.idImmagine = idImmagine;
	}

	public String getCodImmagine() {
		return this.codImmagine;
	}

	public void setCodImmagine(String codImmagine) {
		this.codImmagine = codImmagine;
	}

	public String getDescImmagine() {
		return this.descImmagine;
	}

	public void setDescImmagine(String descImmagine) {
		this.descImmagine = descImmagine;
	}

	public byte[] getImmagine() {
		return this.immagine;
	}

	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}

	public List<CnmCReport> getCnmCReports() {
		return this.cnmCReports;
	}

	public void setCnmCReports(List<CnmCReport> cnmCReports) {
		this.cnmCReports = cnmCReports;
	}

}
