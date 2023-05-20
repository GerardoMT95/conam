/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/*
 * 
 * id_tipo_documento integer NOT NULL, -- Modello osservazione...
  cod_tipo_documento character varying(10),
  descr_tipo_documento character varying(50),
  root character varying(50),
  id_vital_record_code_type integer,
  id_stato_efficacia integer,
  desc_formadoc_entrata character varying(30),
  desc_formadoc_uscita character varying(30),
  id_struttura_acta_root integer,
  id_struttura_acta_folder integer,
  cartaceo boolean NOT NULL,
 * */
@Entity
@Table(name="cnm_d_tipo_documento")
@NamedQuery(name="CnmDTipoDocumento.findAll", query="SELECT c FROM CnmDTipoDocumento c")
public class CnmDTipoDocumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_documento")
	private long idTipoDocumento;
	
	@Column(name="cod_tipo_documento")
	private String codTipoDocumento;
	
	@Column(name="descr_tipo_documento")
	private String descrTipoDocumento;
	
	@Column(name="root")
	private String root;
	
	@Column(name="id_vital_record_code_type")
	private Integer idVitalRecordCodeType;
	
	@Column(name="id_stato_efficacia")
	private Integer idStatoEfficacia;
	
	@Column(name="desc_formadoc_entrata")
	private String descFormadocEntrata;
	
	@Column(name="desc_formadoc_uscita")
	private String descFormadocUscita;
	

	// 20200731_LC
	@Column(name="collocazione_cartacea")
	private String collocazioneCartacea;
	
	
	
	// 20200630_LC 
//	@Column(name="id_struttura_acta_root")
//	private Integer idStrutturaActaRoot;
//	
//	@Column(name="id_struttura_acta_folder")
//	private Integer idStrutturaActaFolder;
	
	// bi-directional many-to-one association to CnmDStrutturaActa
	@ManyToOne
	@JoinColumn(name = "id_struttura_acta_root")
	private CnmDStrutturaActa cnmDStrutturaActaRoot;
	
	// bi-directional many-to-one association to CnmDStrutturaActa
	@ManyToOne
	@JoinColumn(name = "id_struttura_acta_folder")
	private CnmDStrutturaActa cnmDStrutturaActaFolder;	
	
	
	@Column(name="cartaceo")
	private Boolean cartaceo;
	
	
	// 20200630_LC
	//bi-directional many-to-one association to CnmTDocumento
	@OneToMany(mappedBy="cnmDTipoDocumento")
	private List<CnmTDocumento> cnmTDocumentos;

	public long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getCodTipoDocumento() {
		return codTipoDocumento;
	}

	public void setCodTipoDocumento(String codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}

	public String getDescrTipoDocumento() {
		return descrTipoDocumento;
	}

	public void setDescrTipoDocumento(String descrTipoDocumento) {
		this.descrTipoDocumento = descrTipoDocumento;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Integer getIdVitalRecordCodeType() {
		return idVitalRecordCodeType;
	}

	public void setIdVitalRecordCodeType(Integer idVitalRecordCodeType) {
		this.idVitalRecordCodeType = idVitalRecordCodeType;
	}

	public Integer getIdStatoEfficacia() {
		return idStatoEfficacia;
	}

	public void setIdStatoEfficacia(Integer idStatoEfficacia) {
		this.idStatoEfficacia = idStatoEfficacia;
	}

	public String getDescFormadocEntrata() {
		return descFormadocEntrata;
	}

	public void setDescFormadocEntrata(String descFormadocEntrata) {
		this.descFormadocEntrata = descFormadocEntrata;
	}

	public String getDescFormadocUscita() {
		return descFormadocUscita;
	}

	public void setDescFormadocUscita(String descFormadocUscita) {
		this.descFormadocUscita = descFormadocUscita;
	}

	// 20200630_LC
//	public Integer getIdStrutturaActaRoot() {
//		return idStrutturaActaRoot;
//	}
//
//	public void setIdStrutturaActaRoot(Integer idStrutturaActaRoot) {
//		this.idStrutturaActaRoot = idStrutturaActaRoot;
//	}
//
//	public Integer getIdStrutturaActaFolder() {
//		return idStrutturaActaFolder;
//	}
//
//	public void setIdStrutturaActaFolder(Integer idStrutturaActaFolder) {
//		this.idStrutturaActaFolder = idStrutturaActaFolder;
//	}

	
	public CnmDStrutturaActa getCnmDStrutturaActaRoot() {
		return this.cnmDStrutturaActaRoot;
	}

	public void setCnmDStrutturaActaRoot(CnmDStrutturaActa cnmDStrutturaActaRoot) {
		this.cnmDStrutturaActaRoot = cnmDStrutturaActaRoot;
	}
	
	public CnmDStrutturaActa getCnmDStrutturaActaFolder() {
		return this.cnmDStrutturaActaFolder;
	}

	public void setCnmDStrutturaActaFolder(CnmDStrutturaActa cnmDStrutturaActaFolder) {
		this.cnmDStrutturaActaFolder = cnmDStrutturaActaFolder;
	}
	
	
	
	
	
	
	
	

	public Boolean getCartaceo() {
		return cartaceo;
	}

	public void setCartaceo(Boolean cartaceo) {
		this.cartaceo = cartaceo;
	}
	
	
	// 20200630_LC
	
	public List<CnmTDocumento> getCnmTDocumentos() {
		return this.cnmTDocumentos;
	}

	public void setCnmTDocumentos(List<CnmTDocumento> cnmTDocumentos) {
		this.cnmTDocumentos = cnmTDocumentos;
	}
	
	
	
	
	

	public String getCollocazioneCartacea() {
		return collocazioneCartacea;
	}

	public void setCollocazioneCartacea(String collocazioneCartacea) {
		this.collocazioneCartacea = collocazioneCartacea;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
