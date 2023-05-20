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
 * The persistent class for the cnm_t_file database table.
 * 
 */
@Entity
@Table(name="cnm_t_file")
@NamedQuery(name="CnmTFile.findAll", query="SELECT c FROM CnmTFile c")
public class CnmTFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_file")
	private Integer idFile;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="file_intero")
	private String fileIntero;

	@Column(name="nome_file")
	private String nomeFile;

	private String versione;

	//bi-directional many-to-one association to CnmDStatoFile
	@ManyToOne
	@JoinColumn(name="id_stato_file")
	private CnmDStatoFile cnmDStatoFile;

	//bi-directional many-to-one association to CnmDTipoFile
	@ManyToOne
	@JoinColumn(name="id_tipo_file")
	private CnmDTipoFile cnmDTipoFile;

	//bi-directional one-to-one association to CnmTRiscossione
	@OneToOne
	@JoinColumn(name="id_file")
	private CnmTRiscossione cnmTRiscossione;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	//bi-directional many-to-one association to CnmTRecord
	@OneToMany(mappedBy="cnmTFile")
	private List<CnmTRecord> cnmTRecords;

	public CnmTFile() {
	}

	public Integer getIdFile() {
		return this.idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public String getFileIntero() {
		return this.fileIntero;
	}

	public void setFileIntero(String fileIntero) {
		this.fileIntero = fileIntero;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getVersione() {
		return this.versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public CnmDStatoFile getCnmDStatoFile() {
		return this.cnmDStatoFile;
	}

	public void setCnmDStatoFile(CnmDStatoFile cnmDStatoFile) {
		this.cnmDStatoFile = cnmDStatoFile;
	}

	public CnmDTipoFile getCnmDTipoFile() {
		return this.cnmDTipoFile;
	}

	public void setCnmDTipoFile(CnmDTipoFile cnmDTipoFile) {
		this.cnmDTipoFile = cnmDTipoFile;
	}

	public CnmTRiscossione getCnmTRiscossione() {
		return this.cnmTRiscossione;
	}

	public void setCnmTRiscossione(CnmTRiscossione cnmTRiscossione) {
		this.cnmTRiscossione = cnmTRiscossione;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

	public List<CnmTRecord> getCnmTRecords() {
		return this.cnmTRecords;
	}

	public void setCnmTRecords(List<CnmTRecord> cnmTRecords) {
		this.cnmTRecords = cnmTRecords;
	}

	public CnmTRecord addCnmTRecord(CnmTRecord cnmTRecord) {
		getCnmTRecords().add(cnmTRecord);
		cnmTRecord.setCnmTFile(this);

		return cnmTRecord;
	}

	public CnmTRecord removeCnmTRecord(CnmTRecord cnmTRecord) {
		getCnmTRecords().remove(cnmTRecord);
		cnmTRecord.setCnmTFile(null);

		return cnmTRecord;
	}

}
