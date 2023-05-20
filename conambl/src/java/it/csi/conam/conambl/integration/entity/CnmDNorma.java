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
 * The persistent class for the cnm_d_norma database table.
 * 
 */
@Entity
@Table(name="cnm_d_norma")
@NamedQuery(name="CnmDNorma.findAll", query="SELECT c FROM CnmDNorma c")
public class CnmDNorma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_norma")
	private Integer idNorma;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	private Boolean eliminato;

	@Column(name="riferimento_normativo")
	private String riferimentoNormativo;

	//bi-directional many-to-one association to CnmDAmbito
	@ManyToOne
	@JoinColumn(name="id_ambito")
	private CnmDAmbito cnmDAmbito;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmREnteNorma
	@OneToMany(mappedBy="cnmDNorma")
	private List<CnmREnteNorma> cnmREnteNormas;
	
	
	// bi-directional many-to-one association to cnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmDNorma")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;

	
	
	public CnmDNorma() {
	}

	public Integer getIdNorma() {
		return this.idNorma;
	}

	public void setIdNorma(Integer idNorma) {
		this.idNorma = idNorma;
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

	public Boolean getEliminato() {
		return this.eliminato;
	}

	public void setEliminato(Boolean eliminato) {
		this.eliminato = eliminato;
	}

	public String getRiferimentoNormativo() {
		return this.riferimentoNormativo;
	}

	public void setRiferimentoNormativo(String riferimentoNormativo) {
		this.riferimentoNormativo = riferimentoNormativo;
	}

	public CnmDAmbito getCnmDAmbito() {
		return this.cnmDAmbito;
	}

	public void setCnmDAmbito(CnmDAmbito cnmDAmbito) {
		this.cnmDAmbito = cnmDAmbito;
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

	public List<CnmREnteNorma> getCnmREnteNormas() {
		return this.cnmREnteNormas;
	}

	public void setCnmREnteNormas(List<CnmREnteNorma> cnmREnteNormas) {
		this.cnmREnteNormas = cnmREnteNormas;
	}

	public CnmREnteNorma addCnmREnteNorma(CnmREnteNorma cnmREnteNorma) {
		getCnmREnteNormas().add(cnmREnteNorma);
		cnmREnteNorma.setCnmDNorma(this);

		return cnmREnteNorma;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}

	public CnmREnteNorma removeCnmREnteNorma(CnmREnteNorma cnmREnteNorma) {
		getCnmREnteNormas().remove(cnmREnteNorma);
		cnmREnteNorma.setCnmDNorma(null);

		return cnmREnteNorma;
	}

}
