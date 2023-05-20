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
 * The persistent class for the cnm_t_notifica database table.
 * 
 */
@Entity
@Table(name="cnm_t_notifica")
@NamedQuery(name="CnmTNotifica.findAll", query="SELECT c FROM CnmTNotifica c")
public class CnmTNotifica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notifica")
	private Integer idNotifica;

	@Temporal(TemporalType.DATE)
	@Column(name="data_notifica")
	private Date dataNotifica;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name="data_spedizione")
	private Date dataSpedizione;

	@Column(name="importo_spese_notifica")
	private BigDecimal importoSpeseNotifica;

	@Column(name="numero_raccomandata")
	private BigDecimal numeroRaccomandata;

	//bi-directional many-to-one association to CnmDModalitaNotifica
	@ManyToOne
	@JoinColumn(name="id_modalita_notifica")
	private CnmDModalitaNotifica cnmDModalitaNotifica;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-many association to CnmTOrdinanza
	@ManyToMany(mappedBy="cnmTNotificas")
	private List<CnmTOrdinanza> cnmTOrdinanzas;

	//bi-directional many-to-many association to CnmTPianoRate
	@ManyToMany(mappedBy="cnmTNotificas")
	private List<CnmTPianoRate> cnmTPianoRates;

	//bi-directional many-to-many association to CnmTSollecito
	@ManyToMany(mappedBy="cnmTNotificas")
	private List<CnmTSollecito> cnmTSollecitos;

	public CnmTNotifica() {
	}

	public Integer getIdNotifica() {
		return this.idNotifica;
	}

	public void setIdNotifica(Integer idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Date getDataNotifica() {
		return this.dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public Date getDataSpedizione() {
		return this.dataSpedizione;
	}

	public void setDataSpedizione(Date dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return this.importoSpeseNotifica;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public BigDecimal getNumeroRaccomandata() {
		return this.numeroRaccomandata;
	}

	public void setNumeroRaccomandata(BigDecimal numeroRaccomandata) {
		this.numeroRaccomandata = numeroRaccomandata;
	}

	public CnmDModalitaNotifica getCnmDModalitaNotifica() {
		return this.cnmDModalitaNotifica;
	}

	public void setCnmDModalitaNotifica(CnmDModalitaNotifica cnmDModalitaNotifica) {
		this.cnmDModalitaNotifica = cnmDModalitaNotifica;
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

	public List<CnmTOrdinanza> getCnmTOrdinanzas() {
		return this.cnmTOrdinanzas;
	}

	public void setCnmTOrdinanzas(List<CnmTOrdinanza> cnmTOrdinanzas) {
		this.cnmTOrdinanzas = cnmTOrdinanzas;
	}

	public List<CnmTPianoRate> getCnmTPianoRates() {
		return this.cnmTPianoRates;
	}

	public void setCnmTPianoRates(List<CnmTPianoRate> cnmTPianoRates) {
		this.cnmTPianoRates = cnmTPianoRates;
	}

	public List<CnmTSollecito> getCnmTSollecitos() {
		return this.cnmTSollecitos;
	}

	public void setCnmTSollecitos(List<CnmTSollecito> cnmTSollecitos) {
		this.cnmTSollecitos = cnmTSollecitos;
	}

}
