/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the cnm_c_schedulatore database table.
 * 
 */
@Entity
@Table(name="cnm_c_schedulatore")
@NamedQuery(name="CnmCSchedulatore.findAll", query="SELECT c FROM CnmCSchedulatore c")
public class CnmCSchedulatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_schedulatore")
	private Integer idSchedulatore;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	private String ip;

	private Boolean leader;

	@Column(name="ultimo_ping")
	private Timestamp ultimoPing;

	public CnmCSchedulatore() {
	}

	public Integer getIdSchedulatore() {
		return this.idSchedulatore;
	}

	public void setIdSchedulatore(Integer idSchedulatore) {
		this.idSchedulatore = idSchedulatore;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getLeader() {
		return this.leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	public Timestamp getUltimoPing() {
		return this.ultimoPing;
	}

	public void setUltimoPing(Timestamp ultimoPing) {
		this.ultimoPing = ultimoPing;
	}

}
