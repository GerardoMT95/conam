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
 * The persistent class for the cnm_d_comma database table.
 * 
 */
@Entity
@Table(name="cnm_d_comma")
@NamedQuery(name="CnmDComma.findAll", query="SELECT c FROM CnmDComma c")
public class CnmDComma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_comma")
	private Integer idComma;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="desc_comma")
	private String descComma;

	private Boolean eliminato;

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDArticolo
	@ManyToOne
	@JoinColumn(name="id_articolo")
	private CnmDArticolo cnmDArticolo;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmDLettera
	@OneToMany(mappedBy="cnmDComma")
	private List<CnmDLettera> cnmDLetteras;

	// bi-directional many-to-one association to cnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmDComma")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;
	
	
	public CnmDComma() {
	}

	public Integer getIdComma() {
		return this.idComma;
	}

	public void setIdComma(Integer idComma) {
		this.idComma = idComma;
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

	public String getDescComma() {
		return this.descComma;
	}

	public void setDescComma(String descComma) {
		this.descComma = descComma;
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

	public CnmDArticolo getCnmDArticolo() {
		return this.cnmDArticolo;
	}

	public void setCnmDArticolo(CnmDArticolo cnmDArticolo) {
		this.cnmDArticolo = cnmDArticolo;
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

	public List<CnmDLettera> getCnmDLetteras() {
		return this.cnmDLetteras;
	}

	public void setCnmDLetteras(List<CnmDLettera> cnmDLetteras) {
		this.cnmDLetteras = cnmDLetteras;
	}

	public CnmDLettera addCnmDLettera(CnmDLettera cnmDLettera) {
		getCnmDLetteras().add(cnmDLettera);
		cnmDLettera.setCnmDComma(this);

		return cnmDLettera;
	}

	public CnmDLettera removeCnmDLettera(CnmDLettera cnmDLettera) {
		getCnmDLetteras().remove(cnmDLettera);
		cnmDLettera.setCnmDComma(null);

		return cnmDLettera;
	}

}
