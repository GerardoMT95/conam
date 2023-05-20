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
 * The persistent class for the cnm_t_rata database table.
 * 
 */
@Entity
@Table(name="cnm_t_rata")
@NamedQuery(name="CnmTRata.findAll", query="SELECT c FROM CnmTRata c")
public class CnmTRata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rata")
	private Integer idRata;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name="data_scadenza")
	private Date dataScadenza;

	@Column(name="importo_rata")
	private BigDecimal importoRata;

	@Column(name="numero_rata")
	private BigDecimal numeroRata;

	//bi-directional many-to-one association to CnmRSoggRata
	@OneToMany(mappedBy="cnmTRata")
	private List<CnmRSoggRata> cnmRSoggRatas;

	//bi-directional many-to-one association to CnmTPianoRate
	@ManyToOne
	@JoinColumn(name="id_piano_rate")
	private CnmTPianoRate cnmTPianoRate;

	//bi-directional many-to-one association to CnmRSollecitoSoggRata
	@OneToMany(mappedBy="cnmTRata")
	private List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRatas;
	
	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	@Temporal(TemporalType.DATE)
	@Column(name="data_fine_validita")
	private Date dataFineValidita;
	
	
	public CnmTRata() {
	}

	public Integer getIdRata() {
		return this.idRata;
	}

	public void setIdRata(Integer idRata) {
		this.idRata = idRata;
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

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public BigDecimal getImportoRata() {
		return this.importoRata;
	}

	public void setImportoRata(BigDecimal importoRata) {
		this.importoRata = importoRata;
	}

	public BigDecimal getNumeroRata() {
		return this.numeroRata;
	}

	public void setNumeroRata(BigDecimal numeroRata) {
		this.numeroRata = numeroRata;
	}

	public List<CnmRSoggRata> getCnmRSoggRatas() {
		return this.cnmRSoggRatas;
	}

	public void setCnmRSoggRatas(List<CnmRSoggRata> cnmRSoggRatas) {
		this.cnmRSoggRatas = cnmRSoggRatas;
	}

	public CnmRSoggRata addCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().add(cnmRSoggRata);
		cnmRSoggRata.setCnmTRata(this);

		return cnmRSoggRata;
	}

	public CnmRSoggRata removeCnmRSoggRata(CnmRSoggRata cnmRSoggRata) {
		getCnmRSoggRatas().remove(cnmRSoggRata);
		cnmRSoggRata.setCnmTRata(null);

		return cnmRSoggRata;
	}

	public CnmTPianoRate getCnmTPianoRate() {
		return this.cnmTPianoRate;
	}

	public void setCnmTPianoRate(CnmTPianoRate cnmTPianoRate) {
		this.cnmTPianoRate = cnmTPianoRate;
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

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
