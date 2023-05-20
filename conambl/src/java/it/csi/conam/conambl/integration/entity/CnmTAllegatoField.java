/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the cnm_t_allegato_field database table.
 * 
 */
@Entity
@Table(name="cnm_t_allegato_field")
@NamedQuery(name="CnmTAllegatoField.findAll", query="SELECT c FROM CnmTAllegatoField c")
public class CnmTAllegatoField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_allegato_field")
	private Integer idAllegatoField;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="valore_boolean")
	private Boolean valoreBoolean;

	@Temporal(TemporalType.DATE)
	@Column(name="valore_data")
	private Date valoreData;

	@Column(name="valore_data_ora")
	private Timestamp valoreDataOra;

	@Column(name="valore_number")
	private BigDecimal valoreNumber;

	@Column(name="valore_string")
	private String valoreString;

	//bi-directional many-to-one association to CnmCField
	@ManyToOne
	@JoinColumn(name="id_field")
	private CnmCField cnmCField;

	//bi-directional many-to-one association to CnmTAllegato
	@ManyToOne
	@JoinColumn(name="id_allegato")
	private CnmTAllegato cnmTAllegato;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	public CnmTAllegatoField() {
	}

	public Integer getIdAllegatoField() {
		return this.idAllegatoField;
	}

	public void setIdAllegatoField(Integer idAllegatoField) {
		this.idAllegatoField = idAllegatoField;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public Boolean getValoreBoolean() {
		return this.valoreBoolean;
	}

	public void setValoreBoolean(Boolean valoreBoolean) {
		this.valoreBoolean = valoreBoolean;
	}

	public Date getValoreData() {
		return this.valoreData;
	}

	public void setValoreData(Date valoreData) {
		this.valoreData = valoreData;
	}

	public Timestamp getValoreDataOra() {
		return this.valoreDataOra;
	}

	public void setValoreDataOra(Timestamp valoreDataOra) {
		this.valoreDataOra = valoreDataOra;
	}

	public BigDecimal getValoreNumber() {
		return this.valoreNumber;
	}

	public void setValoreNumber(BigDecimal valoreNumber) {
		this.valoreNumber = valoreNumber;
	}

	public String getValoreString() {
		return this.valoreString;
	}

	public void setValoreString(String valoreString) {
		this.valoreString = valoreString;
	}

	public CnmCField getCnmCField() {
		return this.cnmCField;
	}

	public void setCnmCField(CnmCField cnmCField) {
		this.cnmCField = cnmCField;
	}

	public CnmTAllegato getCnmTAllegato() {
		return this.cnmTAllegato;
	}

	public void setCnmTAllegato(CnmTAllegato cnmTAllegato) {
		this.cnmTAllegato = cnmTAllegato;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

}
