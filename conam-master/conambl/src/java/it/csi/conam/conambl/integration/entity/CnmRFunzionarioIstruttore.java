/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_r_funzionario_istruttore database table.
 * 
 */
@Entity
@Table(name="cnm_r_funzionario_istruttore")
@NamedQuery(name="CnmRFunzionarioIstruttore.findAll", query="SELECT c FROM CnmRFunzionarioIstruttore c")
public class CnmRFunzionarioIstruttore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_funzionario_istruttore")
	private Integer idFunzionarioIstruttore;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_assegnazione")
	private Date fineAssegnazione;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_assegnazione")
	private Date inizioAssegnazione;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user")
	private CnmTUser cnmTUser;

	//bi-directional many-to-one association to CnmTVerbale
	@ManyToOne
	@JoinColumn(name="id_verbale")
	private CnmTVerbale cnmTVerbale;

	public CnmRFunzionarioIstruttore() {
	}

	public Integer getIdFunzionarioIstruttore() {
		return this.idFunzionarioIstruttore;
	}

	public void setIdFunzionarioIstruttore(Integer idFunzionarioIstruttore) {
		this.idFunzionarioIstruttore = idFunzionarioIstruttore;
	}

	public Date getFineAssegnazione() {
		return this.fineAssegnazione;
	}

	public void setFineAssegnazione(Date fineAssegnazione) {
		this.fineAssegnazione = fineAssegnazione;
	}

	public Date getInizioAssegnazione() {
		return this.inizioAssegnazione;
	}

	public void setInizioAssegnazione(Date inizioAssegnazione) {
		this.inizioAssegnazione = inizioAssegnazione;
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

}
