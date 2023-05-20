/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="cnm_d_tipo_operazione")
@NamedQuery(name="CnmDTipoOperazione.findAll", query="SELECT c FROM CnmDTipoOperazione c")
public class CnmDTipoOperazione implements Serializable {
// 20200630_LC
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_operazione")
	private long idTipoOperazione;
	
	@Column(name="cod_tipo_operazione")
	private String codTipoOperazione;
	
	@Column(name="descr_tipo_operazione")
	private String descrTipoOperazione;
	


	//bi-directional many-to-one association to CnmDServizio
	@OneToMany(mappedBy="cnmDTipoOperazione")
	private List<CnmDServizio> cnmDServizios;

	
	public List<CnmDServizio> getCnmDServizios() {
		return this.cnmDServizios;
	}

	public void setCnmDServizios(List<CnmDServizio> cnmDServizios) {
		this.cnmDServizios = cnmDServizios;
	}
	
	
	// --

	public long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(long idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

	public String getCodTipoOperazione() {
		return codTipoOperazione;
	}

	public void setCodTipoOperazione(String codTipoOperazione) {
		this.codTipoOperazione = codTipoOperazione;
	}

	public String getDescrTipoOperazione() {
		return descrTipoOperazione;
	}

	public void setDescrTipoOperazione(String descrTipoOperazione) {
		this.descrTipoOperazione = descrTipoOperazione;
	}
	


	
	
	}
	


