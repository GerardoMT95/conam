/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the dt_stati_esteri_istat database table.
 * 
 */
@Entity
@Table(name="dt_stati_esteri_istat")
@NamedQuery(name="DtStatiEsteriIstat.findAll", query="SELECT d FROM DtStatiEsteriIstat d")
public class DtStatiEsteriIstat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String record;

	public DtStatiEsteriIstat() {
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

}
