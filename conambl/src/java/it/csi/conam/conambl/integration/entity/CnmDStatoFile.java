/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cnm_d_stato_file database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_file")
@NamedQuery(name="CnmDStatoFile.findAll", query="SELECT c FROM CnmDStatoFile c")
public class CnmDStatoFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_file")
	private long idStatoFile;

	@Column(name="desc_stato_file")
	private String descStatoFile;

	public CnmDStatoFile() {
	}

	public long getIdStatoFile() {
		return this.idStatoFile;
	}

	public void setIdStatoFile(long idStatoFile) {
		this.idStatoFile = idStatoFile;
	}

	public String getDescStatoFile() {
		return this.descStatoFile;
	}

	public void setDescStatoFile(String descStatoFile) {
		this.descStatoFile = descStatoFile;
	}

}
