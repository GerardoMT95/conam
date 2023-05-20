/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_ruolo_soggetto database table.
 * 
 */
@Entity
@Table(name="cnm_d_ruolo_soggetto")
@NamedQuery(name="CnmDRuoloSoggetto.findAll", query="SELECT c FROM CnmDRuoloSoggetto c")
public class CnmDRuoloSoggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ruolo_soggetto")
	private long idRuoloSoggetto;

	@Column(name="desc_ruolo_soggetto")
	private String descRuoloSoggetto;

	//bi-directional many-to-one association to CnmRVerbaleSoggetto
	@OneToMany(mappedBy="cnmDRuoloSoggetto")
	private List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos;

	public CnmDRuoloSoggetto() {
	}

	public long getIdRuoloSoggetto() {
		return this.idRuoloSoggetto;
	}

	public void setIdRuoloSoggetto(long idRuoloSoggetto) {
		this.idRuoloSoggetto = idRuoloSoggetto;
	}

	public String getDescRuoloSoggetto() {
		return this.descRuoloSoggetto;
	}

	public void setDescRuoloSoggetto(String descRuoloSoggetto) {
		this.descRuoloSoggetto = descRuoloSoggetto;
	}

	public List<CnmRVerbaleSoggetto> getCnmRVerbaleSoggettos() {
		return this.cnmRVerbaleSoggettos;
	}

	public void setCnmRVerbaleSoggettos(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos) {
		this.cnmRVerbaleSoggettos = cnmRVerbaleSoggettos;
	}

	public CnmRVerbaleSoggetto addCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().add(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmDRuoloSoggetto(this);

		return cnmRVerbaleSoggetto;
	}

	public CnmRVerbaleSoggetto removeCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().remove(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmDRuoloSoggetto(null);

		return cnmRVerbaleSoggetto;
	}

}
