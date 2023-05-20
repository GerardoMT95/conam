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
 * The persistent class for the cnm_t_record database table.
 * 
 */
@Entity
@Table(name="cnm_t_record")
@NamedQuery(name="CnmTRecord.findAll", query="SELECT c FROM CnmTRecord c")
public class CnmTRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record")
	private Integer idRecord;

	@Column(name="codice_partita")
	private String codicePartita;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	private Integer ordine;

	//bi-directional many-to-one association to CnmDTipoRecord
	@ManyToOne
	@JoinColumn(name="id_tipo_record")
	private CnmDTipoRecord cnmDTipoRecord;

	//bi-directional many-to-one association to CnmTFile
	@ManyToOne
	@JoinColumn(name="id_file")
	private CnmTFile cnmTFile;

	//bi-directional many-to-one association to CnmTRiscossione
	@ManyToOne
	@JoinColumn(name="id_riscossione")
	private CnmTRiscossione cnmTRiscossione;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	//bi-directional one-to-one association to CnmTRecordN2
	@OneToOne(mappedBy="cnmTRecord")
	private CnmTRecordN2 cnmTRecordN2;

	//bi-directional one-to-one association to CnmTRecordN3
	@OneToOne(mappedBy="cnmTRecord")
	private CnmTRecordN3 cnmTRecordN3;

	//bi-directional one-to-one association to CnmTRecordN4
	@OneToOne(mappedBy="cnmTRecord")
	private CnmTRecordN4 cnmTRecordN4;

	//bi-directional many-to-one association to CnmTRecordRitorno
	@OneToMany(mappedBy="cnmTRecord")
	private List<CnmTRecordRitorno> cnmTRecordRitornos;

	public CnmTRecord() {
	}

	public Integer getIdRecord() {
		return this.idRecord;
	}

	public void setIdRecord(Integer idRecord) {
		this.idRecord = idRecord;
	}

	public String getCodicePartita() {
		return this.codicePartita;
	}

	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Integer getOrdine() {
		return this.ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public CnmDTipoRecord getCnmDTipoRecord() {
		return this.cnmDTipoRecord;
	}

	public void setCnmDTipoRecord(CnmDTipoRecord cnmDTipoRecord) {
		this.cnmDTipoRecord = cnmDTipoRecord;
	}

	public CnmTFile getCnmTFile() {
		return this.cnmTFile;
	}

	public void setCnmTFile(CnmTFile cnmTFile) {
		this.cnmTFile = cnmTFile;
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

	public CnmTRecordN2 getCnmTRecordN2() {
		return this.cnmTRecordN2;
	}

	public void setCnmTRecordN2(CnmTRecordN2 cnmTRecordN2) {
		this.cnmTRecordN2 = cnmTRecordN2;
	}

	public CnmTRecordN3 getCnmTRecordN3() {
		return this.cnmTRecordN3;
	}

	public void setCnmTRecordN3(CnmTRecordN3 cnmTRecordN3) {
		this.cnmTRecordN3 = cnmTRecordN3;
	}

	public CnmTRecordN4 getCnmTRecordN4() {
		return this.cnmTRecordN4;
	}

	public void setCnmTRecordN4(CnmTRecordN4 cnmTRecordN4) {
		this.cnmTRecordN4 = cnmTRecordN4;
	}

	public List<CnmTRecordRitorno> getCnmTRecordRitornos() {
		return this.cnmTRecordRitornos;
	}

	public void setCnmTRecordRitornos(List<CnmTRecordRitorno> cnmTRecordRitornos) {
		this.cnmTRecordRitornos = cnmTRecordRitornos;
	}

	public CnmTRecordRitorno addCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().add(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmTRecord(this);

		return cnmTRecordRitorno;
	}

	public CnmTRecordRitorno removeCnmTRecordRitorno(CnmTRecordRitorno cnmTRecordRitorno) {
		getCnmTRecordRitornos().remove(cnmTRecordRitorno);
		cnmTRecordRitorno.setCnmTRecord(null);

		return cnmTRecordRitorno;
	}

}
