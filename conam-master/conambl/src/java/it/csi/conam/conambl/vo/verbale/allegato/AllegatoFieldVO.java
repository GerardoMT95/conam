/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;
/**
 *  @author riccardo.bova
 *  @date 21 nov 2018
 */

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AllegatoFieldVO extends ParentVO {

	private static final long serialVersionUID = -7114931718179718857L;

	private Boolean booleanValue;
	private BigDecimal numberValue;
	private String stringValue;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dateValue;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dateTimeValue;
	private FieldTypeVO fieldType;
	private Long idField;

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public BigDecimal getNumberValue() {
		return numberValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public FieldTypeVO getFieldType() {
		return fieldType;
	}

	public Long getIdField() {
		return idField;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void setNumberValue(BigDecimal numberValue) {
		this.numberValue = numberValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public void setFieldType(FieldTypeVO fieldType) {
		this.fieldType = fieldType;
	}

	public void setIdField(Long idField) {
		this.idField = idField;
	}

	public LocalDate getDateValue() {
		return dateValue;
	}

	public LocalDateTime getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateValue(LocalDate dateValue) {
		this.dateValue = dateValue;
	}

	public void setDateTimeValue(LocalDateTime dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
