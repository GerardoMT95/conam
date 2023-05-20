/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;

import it.csi.conam.conambl.vo.ParentVO;

import java.math.BigDecimal;

public class ConfigAllegatoVO extends ParentVO {

	private static final long serialVersionUID = 5173577585514839659L;

	private Long idTipo;
	private String label;
	private FieldTypeVO fieldType;
	private BigDecimal maxLength;
	private BigDecimal scale;
	private Boolean required;
	private BigDecimal riga;
	private Long idFonteElenco;
	private BigDecimal ordine;
	private Long idField;

	public BigDecimal getOrdine() {
		return ordine;
	}

	public Long getIdFonteElenco() {
		return idFonteElenco;
	}

	public void setIdFonteElenco(Long idFonteElenco) {
		this.idFonteElenco = idFonteElenco;
	}

	public void setOrdine(BigDecimal ordine) {
		this.ordine = ordine;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public FieldTypeVO getFieldType() {
		return fieldType;
	}

	public BigDecimal getMaxLength() {
		return maxLength;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public void setFieldType(FieldTypeVO fieldType) {
		this.fieldType = fieldType;
	}

	public void setMaxLength(BigDecimal maxLength) {
		this.maxLength = maxLength;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public BigDecimal getRiga() {
		return riga;
	}

	public void setRiga(BigDecimal riga) {
		this.riga = riga;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getIdField() {
		return idField;
	}

	public void setIdField(Long idfield) {
		this.idField = idfield;
	}

}
