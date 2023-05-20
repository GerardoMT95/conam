/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cnm_c_parametro database table.
 * 
 */
@Entity
@Table(name="cnm_c_parametro")
@NamedQuery(name="CnmCParametro.findAll", query="SELECT c FROM CnmCParametro c")
public class CnmCParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parametro")
	private long idParametro;

	@Column(name="desc_parametro")
	private String descParametro;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	@Column(name="valore_boolean")
	private Boolean valoreBoolean;

	@Temporal(TemporalType.DATE)
	@Column(name="valore_date")
	private Date valoreDate;

	@Column(name="valore_number")
	private BigDecimal valoreNumber;

	@Column(name="valore_string")
	private String valoreString;

	public CnmCParametro() {
	}

	public long getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(long idParametro) {
		this.idParametro = idParametro;
	}

	public String getDescParametro() {
		return this.descParametro;
	}

	public void setDescParametro(String descParametro) {
		this.descParametro = descParametro;
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

	public Boolean getValoreBoolean() {
		return this.valoreBoolean;
	}

	public void setValoreBoolean(Boolean valoreBoolean) {
		this.valoreBoolean = valoreBoolean;
	}

	public Date getValoreDate() {
		return this.valoreDate;
	}

	public void setValoreDate(Date valoreDate) {
		this.valoreDate = valoreDate;
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

}
