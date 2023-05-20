/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cnm_d_tipo_record database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_record")
@NamedQuery(name="CnmDTipoRecord.findAll", query="SELECT c FROM CnmDTipoRecord c")
public class CnmDTipoRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_record")
	private long idTipoRecord;

	@Column(name="desc_tipo_record")
	private String descTipoRecord;

	@Column(name="nome_tabella_specifica")
	private String nomeTabellaSpecifica;

	public CnmDTipoRecord() {
	}

	public long getIdTipoRecord() {
		return this.idTipoRecord;
	}

	public void setIdTipoRecord(long idTipoRecord) {
		this.idTipoRecord = idTipoRecord;
	}

	public String getDescTipoRecord() {
		return this.descTipoRecord;
	}

	public void setDescTipoRecord(String descTipoRecord) {
		this.descTipoRecord = descTipoRecord;
	}

	public String getNomeTabellaSpecifica() {
		return this.nomeTabellaSpecifica;
	}

	public void setNomeTabellaSpecifica(String nomeTabellaSpecifica) {
		this.nomeTabellaSpecifica = nomeTabellaSpecifica;
	}

}
