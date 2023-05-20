/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the cnm_t_record_ritorno database table.
 * 
 */
@Entity
@Table(name="cnm_t_record_ritorno")
@NamedQuery(name = "CnmTRecordRitorno.findAll", query = "SELECT c FROM CnmTRecordRitorno c")
public class CnmTRecordRitorno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record_ritorno")
	private Integer idRecordRitorno;

	@Column(name="chiave_info_correttiva")
	private String chiaveInfoCorrettiva;

	@Column(name="chiave_info_da_annullare")
	private String chiaveInfoDaAnnullare;

	@Column(name="cod_ente_creditore")
	private BigDecimal codEnteCreditore;

	@Column(name="cod_entrata")
	private String codEntrata;

	@Column(name="codice_fiscale")
	private String codiceFiscale;

	@Column(name="importo_carico")
	private BigDecimal importoCarico;

	private String record;

	@Column(name="tipo_cod_entrata")
	private String tipoCodEntrata;

	@Column(name="tipo_evento")
	private String tipoEvento;

	//bi-directional many-to-one association to CnmDStatoRecord
	@ManyToOne
	@JoinColumn(name="id_stato_record")
	private CnmDStatoRecord cnmDStatoRecord;

	//bi-directional many-to-one association to CnmDTipoRecord
	@ManyToOne
	@JoinColumn(name="id_tipo_record")
	private CnmDTipoRecord cnmDTipoRecord;

	//bi-directional many-to-one association to CnmTFileRitorno
	@ManyToOne
	@JoinColumn(name="id_file_ritorno")
	private CnmTFileRitorno cnmTFileRitorno;

	//bi-directional many-to-one association to CnmTRecord
	@ManyToOne
	@JoinColumn(name="id_record")
	private CnmTRecord cnmTRecord;

	public CnmTRecordRitorno() {
	}

	public Integer getIdRecordRitorno() {
		return this.idRecordRitorno;
	}

	public void setIdRecordRitorno(Integer idRecordRitorno) {
		this.idRecordRitorno = idRecordRitorno;
	}

	public String getChiaveInfoCorrettiva() {
		return this.chiaveInfoCorrettiva;
	}

	public void setChiaveInfoCorrettiva(String chiaveInfoCorrettiva) {
		this.chiaveInfoCorrettiva = chiaveInfoCorrettiva;
	}

	public String getChiaveInfoDaAnnullare() {
		return this.chiaveInfoDaAnnullare;
	}

	public void setChiaveInfoDaAnnullare(String chiaveInfoDaAnnullare) {
		this.chiaveInfoDaAnnullare = chiaveInfoDaAnnullare;
	}

	public BigDecimal getCodEnteCreditore() {
		return this.codEnteCreditore;
	}

	public void setCodEnteCreditore(BigDecimal codEnteCreditore) {
		this.codEnteCreditore = codEnteCreditore;
	}

	public String getCodEntrata() {
		return this.codEntrata;
	}

	public void setCodEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public BigDecimal getImportoCarico() {
		return this.importoCarico;
	}

	public void setImportoCarico(BigDecimal importoCarico) {
		this.importoCarico = importoCarico;
	}

	public String getRecord() {
		return this.record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getTipoCodEntrata() {
		return this.tipoCodEntrata;
	}

	public void setTipoCodEntrata(String tipoCodEntrata) {
		this.tipoCodEntrata = tipoCodEntrata;
	}

	public String getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public CnmDStatoRecord getCnmDStatoRecord() {
		return this.cnmDStatoRecord;
	}

	public void setCnmDStatoRecord(CnmDStatoRecord cnmDStatoRecord) {
		this.cnmDStatoRecord = cnmDStatoRecord;
	}

	public CnmDTipoRecord getCnmDTipoRecord() {
		return this.cnmDTipoRecord;
	}

	public void setCnmDTipoRecord(CnmDTipoRecord cnmDTipoRecord) {
		this.cnmDTipoRecord = cnmDTipoRecord;
	}

	public CnmTFileRitorno getCnmTFileRitorno() {
		return this.cnmTFileRitorno;
	}

	public void setCnmTFileRitorno(CnmTFileRitorno cnmTFileRitorno) {
		this.cnmTFileRitorno = cnmTFileRitorno;
	}

	public CnmTRecord getCnmTRecord() {
		return this.cnmTRecord;
	}

	public void setCnmTRecord(CnmTRecord cnmTRecord) {
		this.cnmTRecord = cnmTRecord;
	}

}
