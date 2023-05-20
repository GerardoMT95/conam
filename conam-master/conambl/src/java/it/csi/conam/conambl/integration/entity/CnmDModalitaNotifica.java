/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_modalita_notifica database table.
 * 
 */
@Entity
@Table(name="cnm_d_modalita_notifica")
@NamedQuery(name="CnmDModalitaNotifica.findAll", query="SELECT c FROM CnmDModalitaNotifica c")
public class CnmDModalitaNotifica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_modalita_notifica")
	private long idModalitaNotifica;

	@Column(name="desc_modalita_notifica")
	private String descModalitaNotifica;

	//bi-directional many-to-one association to CnmTNotifica
	@OneToMany(mappedBy="cnmDModalitaNotifica")
	private List<CnmTNotifica> cnmTNotificas;

	public CnmDModalitaNotifica() {
	}

	public long getIdModalitaNotifica() {
		return this.idModalitaNotifica;
	}

	public void setIdModalitaNotifica(long idModalitaNotifica) {
		this.idModalitaNotifica = idModalitaNotifica;
	}

	public String getDescModalitaNotifica() {
		return this.descModalitaNotifica;
	}

	public void setDescModalitaNotifica(String descModalitaNotifica) {
		this.descModalitaNotifica = descModalitaNotifica;
	}

	public List<CnmTNotifica> getCnmTNotificas() {
		return this.cnmTNotificas;
	}

	public void setCnmTNotificas(List<CnmTNotifica> cnmTNotificas) {
		this.cnmTNotificas = cnmTNotificas;
	}

	public CnmTNotifica addCnmTNotifica(CnmTNotifica cnmTNotifica) {
		getCnmTNotificas().add(cnmTNotifica);
		cnmTNotifica.setCnmDModalitaNotifica(this);

		return cnmTNotifica;
	}

	public CnmTNotifica removeCnmTNotifica(CnmTNotifica cnmTNotifica) {
		getCnmTNotificas().remove(cnmTNotifica);
		cnmTNotifica.setCnmDModalitaNotifica(null);

		return cnmTNotifica;
	}

}
