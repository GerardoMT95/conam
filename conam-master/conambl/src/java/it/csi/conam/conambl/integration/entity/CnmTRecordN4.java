/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the cnm_t_record_n4 database table.
 * 
 */
@Entity
@Table(name="cnm_t_record_n4")
@NamedQuery(name="CnmTRecordN4.findAll", query="SELECT c FROM CnmTRecordN4 c")
public class CnmTRecordN4 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record_n4")
	private Integer idRecordN4;

	@Column(name="anno_tributo")
	private BigDecimal annoTributo;

	@Column(name="data_decorrenza_interessi")
	private String dataDecorrenzaInteressi;

	private BigDecimal imposta;

	private String titolo;

	//bi-directional many-to-one association to CnmDTipoTributo
	@ManyToOne
	@JoinColumn(name="id_tipo_tributo")
	private CnmDTipoTributo cnmDTipoTributo;

	//bi-directional one-to-one association to CnmTRecord
	@OneToOne
	@JoinColumn(name="id_record")
	private CnmTRecord cnmTRecord;

	public CnmTRecordN4() {
	}

	public Integer getIdRecordN4() {
		return this.idRecordN4;
	}

	public void setIdRecordN4(Integer idRecordN4) {
		this.idRecordN4 = idRecordN4;
	}

	public BigDecimal getAnnoTributo() {
		return this.annoTributo;
	}

	public void setAnnoTributo(BigDecimal annoTributo) {
		this.annoTributo = annoTributo;
	}

	public String getDataDecorrenzaInteressi() {
		return this.dataDecorrenzaInteressi;
	}

	public void setDataDecorrenzaInteressi(String dataDecorrenzaInteressi) {
		this.dataDecorrenzaInteressi = dataDecorrenzaInteressi;
	}

	public BigDecimal getImposta() {
		return this.imposta;
	}

	public void setImposta(BigDecimal imposta) {
		this.imposta = imposta;
	}

	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public CnmDTipoTributo getCnmDTipoTributo() {
		return this.cnmDTipoTributo;
	}

	public void setCnmDTipoTributo(CnmDTipoTributo cnmDTipoTributo) {
		this.cnmDTipoTributo = cnmDTipoTributo;
	}

	public CnmTRecord getCnmTRecord() {
		return this.cnmTRecord;
	}

	public void setCnmTRecord(CnmTRecord cnmTRecord) {
		this.cnmTRecord = cnmTRecord;
	}

}
