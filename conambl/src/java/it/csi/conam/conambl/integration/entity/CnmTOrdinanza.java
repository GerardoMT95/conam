/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cnm_t_ordinanza database table.
 * 
 */
@Entity
@Table(name = "cnm_t_ordinanza")
@NamedQuery(name = "CnmTOrdinanza.findAll", query = "SELECT c FROM CnmTOrdinanza c")
public class CnmTOrdinanza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ordinanza")
	private Integer idOrdinanza;

	@Column(name = "cod_messaggio_epay")
	private String codMessaggioEpay;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_determinazione")
	private Date dataDeterminazione;

	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_protocollo")
	private Timestamp dataOraProtocollo;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_ordinanza")
	private Date dataOrdinanza;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_scadenza_ordinanza")
	private Date dataScadenzaOrdinanza;

	@Column(name = "importo_ordinanza")
	private BigDecimal importoOrdinanza;

	@Column(name = "num_determinazione")
	private String numDeterminazione;

	@Column(name = "numero_protocollo")
	private String numeroProtocollo;

	// 20201105_ET
	@Column(name="flag_documento_pregresso")
	private boolean flagDocumentoPregresso;

	// bi-directional many-to-one association to CnmRAllegatoOrdinanza
	@OneToMany(mappedBy = "cnmTOrdinanza")
	private List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas;

	// bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@OneToMany(mappedBy = "cnmTOrdinanza")
	private List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs;

	// bi-directional many-to-one association to CnmSStatoOrdinanza
	@OneToMany(mappedBy = "cnmTOrdinanza")
	private List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas;

	// bi-directional many-to-one association to CnmDStatoOrdinanza
	@ManyToOne
	@JoinColumn(name = "id_stato_ordinanza")
	private CnmDStatoOrdinanza cnmDStatoOrdinanza;

	// bi-directional many-to-one association to CnmDTipoOrdinanza
	@ManyToOne
	@JoinColumn(name = "id_tipo_ordinanza")
	private CnmDTipoOrdinanza cnmDTipoOrdinanza;

	// bi-directional many-to-many association to CnmTNotifica
	@ManyToMany
	@JoinTable(name = "cnm_r_notifica_ordinanza", joinColumns = { @JoinColumn(name = "id_ordinanza") }, inverseJoinColumns = { @JoinColumn(name = "id_notifica") })
	private List<CnmTNotifica> cnmTNotificas;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;

	/*LUCIO - 2021/04/19 - Gestione pagamenti definiti in autonomia (Scenario 8)*/
	// bi-directional many-to-one association to CnmTAcconto
	@OneToMany(mappedBy = "cnmTOrdinanza")
	private List<CnmTAcconto> cnmTAccontos;
	/*LUCIO - 2021/04/19 - FINE Gestione pagamenti definiti in autonomia (Scenario 8)*/

	/*LUCIO - 2021/04/30 - Modulare le scadenze di pagamento (Scenario B)*/
	@Temporal(TemporalType.DATE)
	@Column(name="data_fine_validita")
	private Date dataFineValidita;
	/*LUCIO - 2021/04/19 - FINE Modulare le scadenze di pagamento (Scenario B)*/

	public CnmTOrdinanza() {
	}

	public Integer getIdOrdinanza() {
		return this.idOrdinanza;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	public String getCodMessaggioEpay() {
		return this.codMessaggioEpay;
	}

	public void setCodMessaggioEpay(String codMessaggioEpay) {
		this.codMessaggioEpay = codMessaggioEpay;
	}

	public Date getDataDeterminazione() {
		return this.dataDeterminazione;
	}

	public void setDataDeterminazione(Date dataDeterminazione) {
		this.dataDeterminazione = dataDeterminazione;
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

	public Date getDataOrdinanza() {
		return this.dataOrdinanza;
	}

	public void setDataOrdinanza(Date dataOrdinanza) {
		this.dataOrdinanza = dataOrdinanza;
	}

	public Date getDataScadenzaOrdinanza() {
		return this.dataScadenzaOrdinanza;
	}

	public void setDataScadenzaOrdinanza(Date dataScadenzaOrdinanza) {
		this.dataScadenzaOrdinanza = dataScadenzaOrdinanza;
	}

	public BigDecimal getImportoOrdinanza() {
		return this.importoOrdinanza;
	}

	public void setImportoOrdinanza(BigDecimal importoOrdinanza) {
		this.importoOrdinanza = importoOrdinanza;
	}

	public String getNumDeterminazione() {
		return this.numDeterminazione;
	}

	public void setNumDeterminazione(String numDeterminazione) {
		this.numDeterminazione = numDeterminazione;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public List<CnmRAllegatoOrdinanza> getCnmRAllegatoOrdinanzas() {
		return this.cnmRAllegatoOrdinanzas;
	}

	public void setCnmRAllegatoOrdinanzas(List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas) {
		this.cnmRAllegatoOrdinanzas = cnmRAllegatoOrdinanzas;
	}

	public CnmRAllegatoOrdinanza addCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().add(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTOrdinanza(this);

		return cnmRAllegatoOrdinanza;
	}

	public CnmRAllegatoOrdinanza removeCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().remove(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTOrdinanza(null);

		return cnmRAllegatoOrdinanza;
	}

	public List<CnmROrdinanzaVerbSog> getCnmROrdinanzaVerbSogs() {
		return this.cnmROrdinanzaVerbSogs;
	}

	public void setCnmROrdinanzaVerbSogs(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs) {
		this.cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogs;
	}

	public CnmROrdinanzaVerbSog addCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().add(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmTOrdinanza(this);

		return cnmROrdinanzaVerbSog;
	}

	public CnmROrdinanzaVerbSog removeCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().remove(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmTOrdinanza(null);

		return cnmROrdinanzaVerbSog;
	}

	public List<CnmSStatoOrdinanza> getCnmSStatoOrdinanzas() {
		return this.cnmSStatoOrdinanzas;
	}

	public void setCnmSStatoOrdinanzas(List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas) {
		this.cnmSStatoOrdinanzas = cnmSStatoOrdinanzas;
	}

	public CnmSStatoOrdinanza addCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().add(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmTOrdinanza(this);

		return cnmSStatoOrdinanza;
	}

	public CnmSStatoOrdinanza removeCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().remove(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmTOrdinanza(null);

		return cnmSStatoOrdinanza;
	}

	public CnmDStatoOrdinanza getCnmDStatoOrdinanza() {
		return this.cnmDStatoOrdinanza;
	}

	public void setCnmDStatoOrdinanza(CnmDStatoOrdinanza cnmDStatoOrdinanza) {
		this.cnmDStatoOrdinanza = cnmDStatoOrdinanza;
	}

	public CnmDTipoOrdinanza getCnmDTipoOrdinanza() {
		return this.cnmDTipoOrdinanza;
	}

	public void setCnmDTipoOrdinanza(CnmDTipoOrdinanza cnmDTipoOrdinanza) {
		this.cnmDTipoOrdinanza = cnmDTipoOrdinanza;
	}

	public List<CnmTNotifica> getCnmTNotificas() {
		return this.cnmTNotificas;
	}

	public void setCnmTNotificas(List<CnmTNotifica> cnmTNotificas) {
		this.cnmTNotificas = cnmTNotificas;
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

	public boolean isFlagDocumentoPregresso() {
		return flagDocumentoPregresso;
	}

	public void setFlagDocumentoPregresso(boolean flagDocumentoPregresso) {
		this.flagDocumentoPregresso = flagDocumentoPregresso;
	}


	public List<CnmTAcconto> getCnmTAccontos() {
		return this.cnmTAccontos;
	}

	public void setCnmTAccontos(List<CnmTAcconto> cnmTAccontos) {
		this.cnmTAccontos = cnmTAccontos;
	}
	
	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	
}
