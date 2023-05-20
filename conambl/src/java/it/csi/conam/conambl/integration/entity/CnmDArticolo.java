/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_d_articolo database table.
 * 
 */
@Entity
@Table(name="cnm_d_articolo")
@NamedQuery(name="CnmDArticolo.findAll", query="SELECT c FROM CnmDArticolo c")
public class CnmDArticolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_articolo")
	private Integer idArticolo;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="desc_articolo")
	private String descArticolo;

	private Boolean eliminato;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmREnteNorma
	@ManyToOne
	@JoinColumn(name="id_ente_norma")
	private CnmREnteNorma cnmREnteNorma;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmDComma
	@OneToMany(mappedBy="cnmDArticolo")
	private List<CnmDComma> cnmDCommas;
	
	
	// bi-directional many-to-one association to cnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmDArticolo")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;

	public CnmDArticolo() {
	}

	public Integer getIdArticolo() {
		return this.idArticolo;
	}

	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
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

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getDescArticolo() {
		return this.descArticolo;
	}

	public void setDescArticolo(String descArticolo) {
		this.descArticolo = descArticolo;
	}

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public CnmREnteNorma getCnmREnteNorma() {
		return this.cnmREnteNorma;
	}

	public void setCnmREnteNorma(CnmREnteNorma cnmREnteNorma) {
		this.cnmREnteNorma = cnmREnteNorma;
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

	public List<CnmDComma> getCnmDCommas() {
		return this.cnmDCommas;
	}

	public void setCnmDCommas(List<CnmDComma> cnmDCommas) {
		this.cnmDCommas = cnmDCommas;
	}

	public CnmDComma addCnmDComma(CnmDComma cnmDComma) {
		getCnmDCommas().add(cnmDComma);
		cnmDComma.setCnmDArticolo(this);

		return cnmDComma;
	}

	public CnmDComma removeCnmDComma(CnmDComma cnmDComma) {
		getCnmDCommas().remove(cnmDComma);
		cnmDComma.setCnmDArticolo(null);

		return cnmDComma;
	}

}
