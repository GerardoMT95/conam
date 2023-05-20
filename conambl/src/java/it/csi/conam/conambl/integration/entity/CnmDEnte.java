/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_ente database table.
 * 
 */
@Entity
@Table(name="cnm_d_ente")
@NamedQuery(name="CnmDEnte.findAll", query="SELECT c FROM CnmDEnte c")
public class CnmDEnte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ente")
	private long idEnte;

	@Column(name="desc_ente")
	private String descEnte;

	//bi-directional many-to-one association to CnmREnteNorma
	@OneToMany(mappedBy="cnmDEnte")
	private List<CnmREnteNorma> cnmREnteNormas;

	//bi-directional many-to-one association to CnmRUserEnte
	@OneToMany(mappedBy="cnmDEnte")
	private List<CnmRUserEnte> cnmRUserEntes;

	//bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy="cnmDEnte")
	private List<CnmTVerbale> cnmTVerbales;

	//bi-directional many-to-one association to CnmTScrittoDifensivo
	@OneToMany(mappedBy="cnmDEnte")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;

	public CnmDEnte() {
	}

	public long getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(long idEnte) {
		this.idEnte = idEnte;
	}

	public String getDescEnte() {
		return this.descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public List<CnmREnteNorma> getCnmREnteNormas() {
		return this.cnmREnteNormas;
	}

	public void setCnmREnteNormas(List<CnmREnteNorma> cnmREnteNormas) {
		this.cnmREnteNormas = cnmREnteNormas;
	}

	public CnmREnteNorma addCnmREnteNorma(CnmREnteNorma cnmREnteNorma) {
		getCnmREnteNormas().add(cnmREnteNorma);
		cnmREnteNorma.setCnmDEnte(this);

		return cnmREnteNorma;
	}

	public CnmREnteNorma removeCnmREnteNorma(CnmREnteNorma cnmREnteNorma) {
		getCnmREnteNormas().remove(cnmREnteNorma);
		cnmREnteNorma.setCnmDEnte(null);

		return cnmREnteNorma;
	}

	public List<CnmRUserEnte> getCnmRUserEntes() {
		return this.cnmRUserEntes;
	}

	public void setCnmRUserEntes(List<CnmRUserEnte> cnmRUserEntes) {
		this.cnmRUserEntes = cnmRUserEntes;
	}

	public CnmRUserEnte addCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().add(cnmRUserEnte);
		cnmRUserEnte.setCnmDEnte(this);

		return cnmRUserEnte;
	}

	public CnmRUserEnte removeCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().remove(cnmRUserEnte);
		cnmRUserEnte.setCnmDEnte(null);

		return cnmRUserEnte;
	}

	public List<CnmTVerbale> getCnmTVerbales() {
		return this.cnmTVerbales;
	}

	public void setCnmTVerbales(List<CnmTVerbale> cnmTVerbales) {
		this.cnmTVerbales = cnmTVerbales;
	}

	public CnmTVerbale addCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().add(cnmTVerbale);
		cnmTVerbale.setCnmDEnte(this);

		return cnmTVerbale;
	}

	public CnmTVerbale removeCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().remove(cnmTVerbale);
		cnmTVerbale.setCnmDEnte(null);

		return cnmTVerbale;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}

}
