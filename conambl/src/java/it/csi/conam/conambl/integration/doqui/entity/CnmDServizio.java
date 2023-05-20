/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="cnm_d_servizio")
@NamedQuery(name="CnmDServizio.findAll", query="SELECT c FROM CnmDServizio c")
public class CnmDServizio implements Serializable {
// 20200630_LC
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_servizio")
	private long idServizio;
	
	@Column(name="cod_servizio")
	private String codServizio;
	
	@Column(name="descr_servizio")
	private String descrServizio;
	
	
	
	// bi-directional many-to-one association to CnmDTipoFornitore
	@ManyToOne
	@JoinColumn(name = "id_tipo_fornitore")
	private CnmDTipoFornitore cnmDTipoFornitore;
	
	// bi-directional many-to-one association to CnmDTipoOperazione
	@ManyToOne
	@JoinColumn(name = "id_tipo_operazione")
	private CnmDTipoOperazione cnmDTipoOperazione;	
	//	---
	


	//bi-directional many-to-one association to CnmTRichiesta
	@OneToMany(mappedBy="cnmDServizio")
	private List<CnmTRichiesta> cnmTRichiestas;

	
	public CnmDTipoFornitore getCnmDTipoFornitore() {
		return cnmDTipoFornitore;
	}

	public void setCnmDTipoFornitore(CnmDTipoFornitore cnmDTipoFornitore) {
		this.cnmDTipoFornitore = cnmDTipoFornitore;
	}

	public CnmDTipoOperazione getCnmDTipoOperazione() {
		return cnmDTipoOperazione;
	}

	public void setCnmDTipoOperazione(CnmDTipoOperazione cnmDTipoOperazione) {
		this.cnmDTipoOperazione = cnmDTipoOperazione;
	}

	public List<CnmTRichiesta> getCnmTRichiestas() {
		return this.cnmTRichiestas;
	}

	public void setCnmTRichiestas(List<CnmTRichiesta> cnmTRichiestas) {
		this.cnmTRichiestas = cnmTRichiestas;
	}
	
	
	// --

	public long getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(long idServizio) {
		this.idServizio = idServizio;
	}

	public String getCodServizio() {
		return codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public String getDescrServizio() {
		return descrServizio;
	}

	public void setDescrServizio(String descrServizio) {
		this.descrServizio = descrServizio;
	}
	


	
	
	}
	


