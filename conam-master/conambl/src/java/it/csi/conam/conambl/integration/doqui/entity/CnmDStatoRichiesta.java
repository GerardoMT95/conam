/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="cnm_d_stato_richiesta")
@NamedQuery(name="CnmDStatoRichiesta.findAll", query="SELECT c FROM CnmDStatoRichiesta c")
public class CnmDStatoRichiesta implements Serializable {
// 20200630_LC
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_richiesta")
	private long idStatoRichiesta;
	
	@Column(name="cod_stato_richiesta")
	private String codStatoRichiesta;
	
	@Column(name="descr_stato_richiesta")
	private String descrStatoRichiesta;
	


	//bi-directional many-to-one association to CnmTRichiesta
	@OneToMany(mappedBy="cnmDStatoRichiesta")
	private List<CnmTRichiesta> cnmTRichiestas;

	
	public List<CnmTRichiesta> getCnmTRichiestas() {
		return this.cnmTRichiestas;
	}

	public void setCnmTRichiestas(List<CnmTRichiesta> cnmTRichiestas) {
		this.cnmTRichiestas = cnmTRichiestas;
	}
	
	
	// --

	public long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}

	public void setIdStatoRichiesta(long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}

	public String getCodStatoRichiesta() {
		return codStatoRichiesta;
	}

	public void setCodStatoRichiesta(String codStatoRichiesta) {
		this.codStatoRichiesta = codStatoRichiesta;
	}

	public String getDescrStatoRichiesta() {
		return descrStatoRichiesta;
	}

	public void setDescrStatoRichiesta(String descrStatoRichiesta) {
		this.descrStatoRichiesta = descrStatoRichiesta;
	}
	


	
	
	}
	


