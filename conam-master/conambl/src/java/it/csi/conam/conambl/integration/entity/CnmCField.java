/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_c_field database table.
 * 
 */
@Entity
@Table(name="cnm_c_field")
@NamedQuery(name="CnmCField.findAll", query="SELECT c FROM CnmCField c")
public class CnmCField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_field")
	private long idField;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	private String label;

	@Column(name="max_length")
	private BigDecimal maxLength;

	private BigDecimal ordine;

	private Boolean required;

	private BigDecimal riga;

	private BigDecimal scale;

	//bi-directional many-to-one association to CnmCFieldType
	@ManyToOne
	@JoinColumn(name="id_field_type")
	private CnmCFieldType cnmCFieldType;

	//bi-directional many-to-one association to CnmDElenco
	@ManyToOne
	@JoinColumn(name="id_elenco")
	private CnmDElenco cnmDElenco;

	//bi-directional many-to-one association to CnmDTipoAllegato
	@ManyToOne
	@JoinColumn(name="id_tipo_allegato")
	private CnmDTipoAllegato cnmDTipoAllegato;

	//bi-directional many-to-one association to CnmTAllegatoField
	@OneToMany(mappedBy="cnmCField")
	private List<CnmTAllegatoField> cnmTAllegatoFields;

	public CnmCField() {
	}

	public long getIdField() {
		return this.idField;
	}

	public void setIdField(long idField) {
		this.idField = idField;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(BigDecimal maxLength) {
		this.maxLength = maxLength;
	}

	public BigDecimal getOrdine() {
		return this.ordine;
	}

	public void setOrdine(BigDecimal ordine) {
		this.ordine = ordine;
	}

	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public BigDecimal getRiga() {
		return this.riga;
	}

	public void setRiga(BigDecimal riga) {
		this.riga = riga;
	}

	public BigDecimal getScale() {
		return this.scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public CnmCFieldType getCnmCFieldType() {
		return this.cnmCFieldType;
	}

	public void setCnmCFieldType(CnmCFieldType cnmCFieldType) {
		this.cnmCFieldType = cnmCFieldType;
	}

	public CnmDElenco getCnmDElenco() {
		return this.cnmDElenco;
	}

	public void setCnmDElenco(CnmDElenco cnmDElenco) {
		this.cnmDElenco = cnmDElenco;
	}

	public CnmDTipoAllegato getCnmDTipoAllegato() {
		return this.cnmDTipoAllegato;
	}

	public void setCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		this.cnmDTipoAllegato = cnmDTipoAllegato;
	}

	public List<CnmTAllegatoField> getCnmTAllegatoFields() {
		return this.cnmTAllegatoFields;
	}

	public void setCnmTAllegatoFields(List<CnmTAllegatoField> cnmTAllegatoFields) {
		this.cnmTAllegatoFields = cnmTAllegatoFields;
	}

	public CnmTAllegatoField addCnmTAllegatoField(CnmTAllegatoField cnmTAllegatoField) {
		getCnmTAllegatoFields().add(cnmTAllegatoField);
		cnmTAllegatoField.setCnmCField(this);

		return cnmTAllegatoField;
	}

	public CnmTAllegatoField removeCnmTAllegatoField(CnmTAllegatoField cnmTAllegatoField) {
		getCnmTAllegatoFields().remove(cnmTAllegatoField);
		cnmTAllegatoField.setCnmCField(null);

		return cnmTAllegatoField;
	}

}
