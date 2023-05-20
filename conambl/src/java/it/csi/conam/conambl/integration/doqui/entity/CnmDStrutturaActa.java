/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="cnm_d_struttura_acta")
@NamedQuery(name="CnmDStrutturaActa.findAll", query="SELECT c FROM CnmDStrutturaActa c")
public class CnmDStrutturaActa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_struttura_acta")
	private Integer idStrutturaActa;
	
	@Column(name="cod_struttura_acta")
	private String codStrutturaActa;
	
	@Column(name="descr_struttura_acta")
	private String descrStrutturaActa;
	
	// 20200630_LC
	//bi-directional many-to-one association to CnmDTipoDocumentoStrutturaRoot
	@OneToMany(mappedBy="cnmDStrutturaActaRoot")
	private List<CnmDTipoDocumento> cnmDTipoDocumentosStrutturaRoot;

	// 20200630_LC
	//bi-directional many-to-one association to CnmDTipoDocumentoStrutturaFolder
	@OneToMany(mappedBy="cnmDStrutturaActaFolder")
	private List<CnmDTipoDocumento> cnmDTipoDocumentosStrutturaFolder;
	
	
	public Integer getIdStrutturaActa() {
		return idStrutturaActa;
	}

	public void setIdStrutturaActa(Integer idStrutturaActa) {
		this.idStrutturaActa = idStrutturaActa;
	}

	public String getCodStrutturaActa() {
		return codStrutturaActa;
	}

	public void setCodStrutturaActa(String codStrutturaActa) {
		this.codStrutturaActa = codStrutturaActa;
	}

	public String getDescrStrutturaActa() {
		return descrStrutturaActa;
	}

	
	public void setDescrStrutturaActa(String descrStrutturaActa) {
		this.descrStrutturaActa = descrStrutturaActa;
	}
	
	
	
	// 20200630_LC


	public List<CnmDTipoDocumento> getCnmDTipoDocumentosStrutturaRoot() {
		return this.cnmDTipoDocumentosStrutturaRoot;
	}

	public void setCnmDTipoDocumentosStrutturaRoot(List<CnmDTipoDocumento> cnmDTipoDocumentosStrutturaRoot) {
		this.cnmDTipoDocumentosStrutturaRoot = cnmDTipoDocumentosStrutturaRoot;
	}
	


	public List<CnmDTipoDocumento> getCnmDTipoDocumentosStrutturaFolder() {
		return this.cnmDTipoDocumentosStrutturaFolder;
	}

	public void setCnmDTipoDocumentosStrutturaFolder(List<CnmDTipoDocumento> cnmDTipoDocumentosStrutturaFolder) {
		this.cnmDTipoDocumentosStrutturaFolder = cnmDTipoDocumentosStrutturaFolder;
	}
	
	
	
	
	
	}
	


