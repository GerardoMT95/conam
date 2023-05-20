/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the dt_limiti_amministrativi database table.
 * 
 */
@Entity
@Table(name="dt_limiti_amministrativi")
@NamedQuery(name="DtLimitiAmministrativi.findAll", query="SELECT d FROM DtLimitiAmministrativi d")
public class DtLimitiAmministrativi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String record;

	public DtLimitiAmministrativi() {
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

}
