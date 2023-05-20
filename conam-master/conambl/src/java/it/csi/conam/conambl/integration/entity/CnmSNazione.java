/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the cnm_s_nazione database table.
 * 
 */
@Entity
@Table(name="cnm_s_nazione")
@NamedQuery(name="CnmSNazione.findAll", query="SELECT c FROM CnmSNazione c")
public class CnmSNazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_nazione")
	private long idSNazione;

	@Column(name="cod_belfiore_nazione")
	private String codBelfioreNazione;

	@Column(name="cod_istat_nazione")
	private String codIstatNazione;

	@Column(name="denom_nazione")
	private String denomNazione;

	@Column(name="dt_id_stato")
	private BigDecimal dtIdStato;

	@Column(name="dt_id_stato_next")
	private BigDecimal dtIdStatoNext;

	@Column(name="dt_id_stato_prev")
	private BigDecimal dtIdStatoPrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione")
	private CnmDNazione cnmDNazione;

	public CnmSNazione() {
	}

	public long getIdSNazione() {
		return this.idSNazione;
	}

	public void setIdSNazione(long idSNazione) {
		this.idSNazione = idSNazione;
	}

	public String getCodBelfioreNazione() {
		return this.codBelfioreNazione;
	}

	public void setCodBelfioreNazione(String codBelfioreNazione) {
		this.codBelfioreNazione = codBelfioreNazione;
	}

	public String getCodIstatNazione() {
		return this.codIstatNazione;
	}

	public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}

	public String getDenomNazione() {
		return this.denomNazione;
	}

	public void setDenomNazione(String denomNazione) {
		this.denomNazione = denomNazione;
	}

	public BigDecimal getDtIdStato() {
		return this.dtIdStato;
	}

	public void setDtIdStato(BigDecimal dtIdStato) {
		this.dtIdStato = dtIdStato;
	}

	public BigDecimal getDtIdStatoNext() {
		return this.dtIdStatoNext;
	}

	public void setDtIdStatoNext(BigDecimal dtIdStatoNext) {
		this.dtIdStatoNext = dtIdStatoNext;
	}

	public BigDecimal getDtIdStatoPrev() {
		return this.dtIdStatoPrev;
	}

	public void setDtIdStatoPrev(BigDecimal dtIdStatoPrev) {
		this.dtIdStatoPrev = dtIdStatoPrev;
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

	public CnmDNazione getCnmDNazione() {
		return this.cnmDNazione;
	}

	public void setCnmDNazione(CnmDNazione cnmDNazione) {
		this.cnmDNazione = cnmDNazione;
	}

}
