/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * id_tipo_fornitore integer NOT NULL,
  cod_tipo_fornitore character varying(20),
  descr_tipo_fornitore character varying(20),
  repository character varying(100),
  application_key character varying(100),
  cod_ente character varying(20),
 */

@Entity
@Table(name="cnm_d_tipo_fornitore")
@NamedQuery(name="CnmDTipoFornitore.findAll", query="SELECT c FROM CnmDTipoFornitore c")
public class CnmDTipoFornitore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_fornitore")
	private long idTipoFornitore;
	
	@Column(name="cod_tipo_fornitore")
	private String codTipoFornitore;
	
	@Column(name="descr_Tipo_Fornitore")
	private String descrTipoFornitore;
	
	@Column(name="repository")
	private String repository;
	
	@Column(name="application_key")
	private String applicationKey;
	
	@Column(name="cod_ente")
	private String codEnte;

	// 20200630_LC
	//bi-directional many-to-one association to CnmDServizio
	@OneToMany(mappedBy="cnmDTipoFornitore")
	private List<CnmDServizio> cnmDServizios;
	
	public List<CnmDServizio> getCnmDServizios() {
		return this.cnmDServizios;
	}

	public void setCnmDServizios(List<CnmDServizio> cnmDServizios) {
		this.cnmDServizios = cnmDServizios;
	}
	
	// --
	
	
	
	
	public long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(long idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}

	public String getCodTipoFornitore() {
		return codTipoFornitore;
	}

	public void setCodTipoFornitore(String codTipoFornitore) {
		this.codTipoFornitore = codTipoFornitore;
	}

	public String getDescrTipoFornitore() {
		return descrTipoFornitore;
	}

	public void setDescrTipoFornitore(String descrTipoFornitore) {
		this.descrTipoFornitore = descrTipoFornitore;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getApplicationKey() {
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	

}


