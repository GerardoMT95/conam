/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the cnm_t_mail_inviata database table.
 * 
 */
@Entity
@Table(name="cnm_t_mail_inviata")
@NamedQuery(name="CnmTMailInviata.findAll", query="SELECT c FROM CnmTMailInviata c")
public class CnmTMailInviata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_mail")
	private Integer idMail;

	@Temporal(TemporalType.DATE)
	@Column(name="data_invio")
	private Date dataInvio;

	@Column(name="destinatario")
	private String destinatario;

	@Column(name="soggetto")
	private String soggetto;
	
	@Column(name="testo")
	private String testo;

	@Column(name="esito")
	private String esito;
	
	@Column(name="info")
	private String info;

	//bi-directional many-to-one association to CnmDModalitaNotifica
	@ManyToOne
	@JoinColumn(name="id_calendario")
	private CnmTCalendario cnmTCalendario;
	
	public CnmTMailInviata(String destinatario, String soggetto, String testo, CnmTCalendario cnmTCalendario) {
		super();
		this.destinatario = destinatario;
		this.soggetto = soggetto;
		this.testo = testo;
		this.cnmTCalendario = cnmTCalendario;
	}

	public CnmTMailInviata() {
	}

	public Integer getIdMail() {
		return idMail;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public CnmTCalendario getCnmTCalendario() {
		return cnmTCalendario;
	}

	public void setCnmTCalendario(CnmTCalendario cnmTCalendario) {
		this.cnmTCalendario = cnmTCalendario;
	}


}
