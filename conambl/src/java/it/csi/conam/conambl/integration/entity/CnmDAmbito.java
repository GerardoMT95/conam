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
 * The persistent class for the cnm_d_ambito database table.
 * 
 */
@Entity
@Table(name="cnm_d_ambito")
@NamedQuery(name="CnmDAmbito.findAll", query="SELECT c FROM CnmDAmbito c")
public class CnmDAmbito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ambito")
	private Integer idAmbito;

	private String acronimo;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Column(name="desc_ambito")
	private String descAmbito;

	private Boolean eliminato;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmDNorma
	@OneToMany(mappedBy="cnmDAmbito")
	private List<CnmDNorma> cnmDNormas;
	
	// bi-directional many-to-one association to CnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmDAmbito")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;
	

	public CnmDAmbito() {
	}

	public Integer getIdAmbito() {
		return this.idAmbito;
	}

	public void setIdAmbito(Integer idAmbito) {
		this.idAmbito = idAmbito;
	}

	public String getAcronimo() {
		return this.acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
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

	public String getDescAmbito() {
		return this.descAmbito;
	}

	public void setDescAmbito(String descAmbito) {
		this.descAmbito = descAmbito;
	}

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
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

	public List<CnmDNorma> getCnmDNormas() {
		return this.cnmDNormas;
	}

	public void setCnmDNormas(List<CnmDNorma> cnmDNormas) {
		this.cnmDNormas = cnmDNormas;
	}

	public CnmDNorma addCnmDNorma(CnmDNorma cnmDNorma) {
		getCnmDNormas().add(cnmDNorma);
		cnmDNorma.setCnmDAmbito(this);

		return cnmDNorma;
	}

	public CnmDNorma removeCnmDNorma(CnmDNorma cnmDNorma) {
		getCnmDNormas().remove(cnmDNorma);
		cnmDNorma.setCnmDAmbito(null);

		return cnmDNorma;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}



}
