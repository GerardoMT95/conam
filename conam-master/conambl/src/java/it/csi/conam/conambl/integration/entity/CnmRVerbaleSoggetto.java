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
 * The persistent class for the cnm_r_verbale_soggetto database table.
 * 
 */
@Entity
@Table(name="cnm_r_verbale_soggetto")
@NamedQuery(name="CnmRVerbaleSoggetto.findAll", query="SELECT c FROM CnmRVerbaleSoggetto c")
public class CnmRVerbaleSoggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_verbale_soggetto")
	private Integer idVerbaleSoggetto;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	private String note;

	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	@Column(name="recidivo")
	private Boolean recidivo;
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/
	
	
	//bi-directional many-to-one association to CnmRAllegatoVerbSog
	@OneToMany(mappedBy="cnmRVerbaleSoggetto")
	private List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs;

	//bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@OneToMany(mappedBy="cnmRVerbaleSoggetto")
	private List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs;

	//bi-directional many-to-one association to CnmDRuoloSoggetto
	@ManyToOne
	@JoinColumn(name="id_ruolo_soggetto")
	private CnmDRuoloSoggetto cnmDRuoloSoggetto;

	//bi-directional many-to-one association to CnmTSoggetto
	@ManyToOne
	@JoinColumn(name="id_soggetto")
	private CnmTSoggetto cnmTSoggetto;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser;

	//bi-directional many-to-one association to CnmTVerbale
	@ManyToOne
	@JoinColumn(name="id_verbale")
	private CnmTVerbale cnmTVerbale;

	public CnmRVerbaleSoggetto() {
	}

	public Integer getIdVerbaleSoggetto() {
		return this.idVerbaleSoggetto;
	}

	public void setIdVerbaleSoggetto(Integer idVerbaleSoggetto) {
		this.idVerbaleSoggetto = idVerbaleSoggetto;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<CnmRAllegatoVerbSog> getCnmRAllegatoVerbSogs() {
		return this.cnmRAllegatoVerbSogs;
	}

	public void setCnmRAllegatoVerbSogs(List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs) {
		this.cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogs;
	}

	public CnmRAllegatoVerbSog addCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().add(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmRVerbaleSoggetto(this);

		return cnmRAllegatoVerbSog;
	}

	public CnmRAllegatoVerbSog removeCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().remove(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmRVerbaleSoggetto(null);

		return cnmRAllegatoVerbSog;
	}

	public List<CnmROrdinanzaVerbSog> getCnmROrdinanzaVerbSogs() {
		return this.cnmROrdinanzaVerbSogs;
	}

	public void setCnmROrdinanzaVerbSogs(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs) {
		this.cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogs;
	}

	public CnmROrdinanzaVerbSog addCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().add(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmRVerbaleSoggetto(this);

		return cnmROrdinanzaVerbSog;
	}

	public CnmROrdinanzaVerbSog removeCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().remove(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmRVerbaleSoggetto(null);

		return cnmROrdinanzaVerbSog;
	}

	public CnmDRuoloSoggetto getCnmDRuoloSoggetto() {
		return this.cnmDRuoloSoggetto;
	}

	public void setCnmDRuoloSoggetto(CnmDRuoloSoggetto cnmDRuoloSoggetto) {
		this.cnmDRuoloSoggetto = cnmDRuoloSoggetto;
	}

	public CnmTSoggetto getCnmTSoggetto() {
		return this.cnmTSoggetto;
	}

	public void setCnmTSoggetto(CnmTSoggetto cnmTSoggetto) {
		this.cnmTSoggetto = cnmTSoggetto;
	}

	public CnmTUser getCnmTUser() {
		return this.cnmTUser;
	}

	public void setCnmTUser(CnmTUser cnmTUser) {
		this.cnmTUser = cnmTUser;
	}

	public CnmTVerbale getCnmTVerbale() {
		return this.cnmTVerbale;
	}

	public void setCnmTVerbale(CnmTVerbale cnmTVerbale) {
		this.cnmTVerbale = cnmTVerbale;
	}

	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	public Boolean getRecidivo() {
		return recidivo;
	}

	public void setRecidivo(Boolean recidivo) {
		this.recidivo = recidivo;
	}
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVerbaleSoggetto == null) ? 0 : idVerbaleSoggetto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CnmRVerbaleSoggetto other = (CnmRVerbaleSoggetto) obj;
		if (idVerbaleSoggetto == null) {
			if (other.idVerbaleSoggetto != null)
				return false;
		} else if (!idVerbaleSoggetto.equals(other.idVerbaleSoggetto))
			return false;
		return true;
	}

}
