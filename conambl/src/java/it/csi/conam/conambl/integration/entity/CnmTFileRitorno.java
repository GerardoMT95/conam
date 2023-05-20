/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the cnm_t_file_ritorno database table.
 * 
 */
@Entity
@Table(name = "cnm_t_file_ritorno")
@NamedQuery(name = "CnmTFileRitorno.findAll", query = "SELECT f FROM CnmTFileRitorno f")
public class CnmTFileRitorno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_file_ritorno")
	private Integer idFileRitorno;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "nome_file")
	private String nomeFile;

	// bi-directional many-to-one association to CnmTRecordRitorno
	@OneToMany(mappedBy = "cnmTFileRitorno")
	private List<CnmTRecordRitorno> cnmTRecordRitornos;

	public CnmTFileRitorno() {
	}

	public Integer getIdFileRitorno() {
		return this.idFileRitorno;
	}

	public void setIdFileRitorno(Integer idFileRitorno) {
		this.idFileRitorno = idFileRitorno;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public List<CnmTRecordRitorno> getCnmTRecordRitornos() {
		return this.cnmTRecordRitornos;
	}

	public void setCnmTRecordRitornos(List<CnmTRecordRitorno> cnmTRecordRitornos) {
		this.cnmTRecordRitornos = cnmTRecordRitornos;
	}

	public CnmTRecordRitorno addCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().add(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmTFileRitorno(this);

		return cnmTRecordRitorno;
	}

	public CnmTRecordRitorno removeCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().remove(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmTFileRitorno(null);

		return cnmTRecordRitorno;
	}

}
