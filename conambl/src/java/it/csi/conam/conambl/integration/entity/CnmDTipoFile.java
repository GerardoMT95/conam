/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cnm_d_tipo_file database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_file")
@NamedQuery(name="CnmDTipoFile.findAll", query="SELECT c FROM CnmDTipoFile c")
public class CnmDTipoFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_file")
	private long idTipoFile;

	@Column(name="desc_tipo_file")
	private String descTipoFile;

	public CnmDTipoFile() {
	}

	public long getIdTipoFile() {
		return this.idTipoFile;
	}

	public void setIdTipoFile(long idTipoFile) {
		this.idTipoFile = idTipoFile;
	}

	public String getDescTipoFile() {
		return this.descTipoFile;
	}

	public void setDescTipoFile(String descTipoFile) {
		this.descTipoFile = descTipoFile;
	}

}
