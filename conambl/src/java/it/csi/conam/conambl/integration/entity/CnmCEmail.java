/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;
/**
 * @author Lucio Rosadini
 *
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cnm_c_batch_email")
@NamedQuery(name="CnmCEmail.findAll", query="SELECT c FROM CnmCEmail c")
public class CnmCEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parametro_email")
	private long idEmailParam;
	
	@Column(name="nome_parametro_email")
	private String emailParamName;

	@Column(name="valore_parametro_email")
	private String emailParamValue;

	@Column(name="descrizione_parametro_email")
	private String emailParamDescription;
	

	public long getId() {
		return this.idEmailParam;
	}

	public void setId(long idEmailParam) {
		this.idEmailParam = idEmailParam;
	}

	public String getName() {
		return this.emailParamName;
	}

	public void setName(String emailParamName) {
		this.emailParamName = emailParamName;
	}

	public String getValue() {
		return this.emailParamValue;
	}

	public void setValue(String emailParamValue) {
		this.emailParamValue = emailParamValue;
	}

	public String getDescription() {
		return this.emailParamDescription;
	}

	public void setDescription(String emailParamDescription) {
		this.emailParamName = emailParamDescription;
	}
	
}
