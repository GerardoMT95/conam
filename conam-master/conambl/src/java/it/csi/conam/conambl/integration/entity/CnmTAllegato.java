/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the cnm_t_allegato database table.
 * 
 */
@Entity
@Table(name = "cnm_t_allegato")
@NamedQuery(name="CnmTAllegato.findAll", query="SELECT c FROM CnmTAllegato c")
public class CnmTAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_allegato")
	private Integer idAllegato;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_protocollo")
	private Timestamp dataOraProtocollo;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name = "id_acta")
	private String idActa;

	@Column(name = "id_acta_master")
	private String idActaMaster;

	@Column(name = "id_index")
	private String idIndex;

	@Column(name = "nome_file")
	private String nomeFile;

	@Column(name = "numero_protocollo")
	private String numeroProtocollo;
		
	// 20200708_LC
	@Column(name="objectid_spostamento_acta")
	private String objectidSpostamentoActa;

	// 20201001_ET
	@Column(name="flag_recuperato_pec")
	private boolean flagRecuperatoPec;
		
	// 20201019_ET
	@Column(name="flag_documento_pregresso")
	private boolean flagDocumentoPregresso;

	// bi-directional many-to-one association to CnmRAllegatoOrdVerbSog
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs;

	// bi-directional many-to-one association to CnmRAllegatoOrdinanza
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas;

	// bi-directional many-to-one association to CnmRAllegatoPianoRate
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates;

	// bi-directional many-to-one association to CnmRAllegatoSollecito
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos;

	// bi-directional many-to-one association to CnmRAllegatoVerbSog
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs;

	// bi-directional many-to-one association to CnmRAllegatoVerbale
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmRAllegatoVerbale> cnmRAllegatoVerbales;

	// bi-directional many-to-one association to CnmDStatoAllegato
	@ManyToOne
	@JoinColumn(name = "id_stato_allegato")
	private CnmDStatoAllegato cnmDStatoAllegato;

	// bi-directional many-to-one association to CnmDTipoAllegato
	@ManyToOne
	@JoinColumn(name = "id_tipo_allegato")
	private CnmDTipoAllegato cnmDTipoAllegato;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;

	// bi-directional many-to-one association to CnmTAllegatoField
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmTAllegatoField> cnmTAllegatoFields;
	
	// bi-directional many-to-one association to CnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;
	
	// bi-directional many-to-one association to cnmTAcconto
	@OneToMany(mappedBy = "cnmTAllegato")
	private List<CnmTAcconto> cnmTAcconto;
	
	public CnmTAllegato() {
	}

	public Integer getIdAllegato() {
		return this.idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraProtocollo() {
		return this.dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getIdActa() {
		return this.idActa;
	}

	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}

	public String getIdActaMaster() {
		return this.idActaMaster;
	}

	public void setIdActaMaster(String idActaMaster) {
		this.idActaMaster = idActaMaster;
	}

	public String getIdIndex() {
		return this.idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public List<CnmRAllegatoOrdVerbSog> getCnmRAllegatoOrdVerbSogs() {
		return this.cnmRAllegatoOrdVerbSogs;
	}

	public void setCnmRAllegatoOrdVerbSogs(List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs) {
		this.cnmRAllegatoOrdVerbSogs = cnmRAllegatoOrdVerbSogs;
	}

	public CnmRAllegatoOrdVerbSog addCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().add(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmTAllegato(this);

		return cnmRAllegatoOrdVerbSog;
	}

	public CnmRAllegatoOrdVerbSog removeCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().remove(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmTAllegato(null);

		return cnmRAllegatoOrdVerbSog;
	}

	public List<CnmRAllegatoOrdinanza> getCnmRAllegatoOrdinanzas() {
		return this.cnmRAllegatoOrdinanzas;
	}

	public void setCnmRAllegatoOrdinanzas(List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas) {
		this.cnmRAllegatoOrdinanzas = cnmRAllegatoOrdinanzas;
	}

	public CnmRAllegatoOrdinanza addCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().add(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTAllegato(this);

		return cnmRAllegatoOrdinanza;
	}

	public CnmRAllegatoOrdinanza removeCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().remove(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTAllegato(null);

		return cnmRAllegatoOrdinanza;
	}

	public List<CnmRAllegatoPianoRate> getCnmRAllegatoPianoRates() {
		return this.cnmRAllegatoPianoRates;
	}

	public void setCnmRAllegatoPianoRates(List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates) {
		this.cnmRAllegatoPianoRates = cnmRAllegatoPianoRates;
	}

	public CnmRAllegatoPianoRate addCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().add(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTAllegato(this);

		return cnmRAllegatoPianoRate;
	}

	public CnmRAllegatoPianoRate removeCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().remove(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTAllegato(null);

		return cnmRAllegatoPianoRate;
	}

	public List<CnmRAllegatoSollecito> getCnmRAllegatoSollecitos() {
		return this.cnmRAllegatoSollecitos;
	}

	public void setCnmRAllegatoSollecitos(List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos) {
		this.cnmRAllegatoSollecitos = cnmRAllegatoSollecitos;
	}

	public CnmRAllegatoSollecito addCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().add(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTAllegato(this);

		return cnmRAllegatoSollecito;
	}

	public CnmRAllegatoSollecito removeCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().remove(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTAllegato(null);

		return cnmRAllegatoSollecito;
	}

	public List<CnmRAllegatoVerbSog> getCnmRAllegatoVerbSogs() {
		return this.cnmRAllegatoVerbSogs;
	}

	public void setCnmRAllegatoVerbSogs(List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs) {
		this.cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogs;
	}

	public CnmRAllegatoVerbSog addCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().add(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmTAllegato(this);

		return cnmRAllegatoVerbSog;
	}

	public CnmRAllegatoVerbSog removeCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().remove(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmTAllegato(null);

		return cnmRAllegatoVerbSog;
	}

	public List<CnmRAllegatoVerbale> getCnmRAllegatoVerbales() {
		return this.cnmRAllegatoVerbales;
	}

	public void setCnmRAllegatoVerbales(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales) {
		this.cnmRAllegatoVerbales = cnmRAllegatoVerbales;
	}

	public CnmRAllegatoVerbale addCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().add(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTAllegato(this);

		return cnmRAllegatoVerbale;
	}

	public CnmRAllegatoVerbale removeCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().remove(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTAllegato(null);

		return cnmRAllegatoVerbale;
	}

	public CnmDStatoAllegato getCnmDStatoAllegato() {
		return this.cnmDStatoAllegato;
	}

	public void setCnmDStatoAllegato(CnmDStatoAllegato cnmDStatoAllegato) {
		this.cnmDStatoAllegato = cnmDStatoAllegato;
	}

	public CnmDTipoAllegato getCnmDTipoAllegato() {
		return this.cnmDTipoAllegato;
	}

	public void setCnmDTipoAllegato(CnmDTipoAllegato cnmDTipoAllegato) {
		this.cnmDTipoAllegato = cnmDTipoAllegato;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public List<CnmTAllegatoField> getCnmTAllegatoFields() {
		return this.cnmTAllegatoFields;
	}

	public void setCnmTAllegatoFields(List<CnmTAllegatoField> cnmTAllegatoFields) {
		this.cnmTAllegatoFields = cnmTAllegatoFields;
	}

	public CnmTAllegatoField addCnmTAllegatoField(CnmTAllegatoField cnmTAllegatoField) {
		getCnmTAllegatoFields().add(cnmTAllegatoField);
		cnmTAllegatoField.setCnmTAllegato(this);

		return cnmTAllegatoField;
	}

	public CnmTAllegatoField removeCnmTAllegatoField(CnmTAllegatoField cnmTAllegatoField) {
		getCnmTAllegatoFields().remove(cnmTAllegatoField);
		cnmTAllegatoField.setCnmTAllegato(null);

		return cnmTAllegatoField;
	}

	public String getObjectidSpostamentoActa() {
		return objectidSpostamentoActa;
	}

	public void setObjectidSpostamentoActa(String objectidSpostamentoActa) {
		this.objectidSpostamentoActa = objectidSpostamentoActa;
	}

	public boolean isFlagRecuperatoPec() {
		return flagRecuperatoPec;
	}

	public void setFlagRecuperatoPec(boolean flagRecuperatoPec) {
		this.flagRecuperatoPec = flagRecuperatoPec;
	}

	public boolean isFlagDocumentoPregresso() {
		return flagDocumentoPregresso;
	}

	public void setFlagDocumentoPregresso(boolean flagDocumentoPregresso) {
		this.flagDocumentoPregresso = flagDocumentoPregresso;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}


	
}
