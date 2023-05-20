/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import it.csi.conam.conambl.integration.entity.CnmTUser;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name="cnm_t_richiesta")
@NamedQuery(name="CnmTRichiesta.findAll", query="SELECT c FROM CnmTRichiesta c")
public class CnmTRichiesta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_richiesta")
	private Integer idRichiesta;
	
	@Column(name="id_fruitore")
	private long idFruitore;
	
	
	
	// 20200630_LC
//	@Column(name="id_stato_richiesta")
//	private long idStatoRichiesta;
	
//	@Column(name="id_servizio")
//	private long idServizio;
	
	// bi-directional many-to-one association to CnmDStatoRichiesta
	@ManyToOne
	@JoinColumn(name = "id_stato_richiesta")
	private CnmDStatoRichiesta cnmDStatoRichiesta;
	
	// bi-directional many-to-one association to CnmDServizio
	@ManyToOne
	@JoinColumn(name = "id_servizio")
	private CnmDServizio cnmDServizio;	
	
	
	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;
	
	// 20200702_LC

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;
	
	
	
	
	//	---
	
	
//	
//	@Column(name="data_ins_richiesta")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dataInsRichiesta;
//	
//	
//	@Column(name="data_upd_richiesta")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dataUpdRichiesta;

	public Integer getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Integer idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(long idFruitore) {
		this.idFruitore = idFruitore;
	}

	
	
	// 20200630_LC
//	public long getIdStatoRichiesta() {
//		return idStatoRichiesta;
//	}
//
//	public void setIdStatoRichiesta(long idStatoRichiesta) {
//		this.idStatoRichiesta = idStatoRichiesta;
//	}
	
//	public long getIdServizio() {
//		return idServizio;
//	}
//
//	public void setIdServizio(long idServizio) {
//		this.idServizio = idServizio;
//	}
	
	public CnmDStatoRichiesta getCnmDStatoRichiesta() {
		return this.cnmDStatoRichiesta;
	}

	public void setCnmDStatoRichiesta(CnmDStatoRichiesta cnmDStatoRichiesta) {
		this.cnmDStatoRichiesta = cnmDStatoRichiesta;
	}

	public CnmDServizio getCnmDServizio() {
		return this.cnmDServizio;
	}

	public void setCnmDServizio(CnmDServizio cnmDServizio) {
		this.cnmDServizio = cnmDServizio;
	}	
	//	---
	
	

//	public Date getDataInsRichiesta() {
//		return dataInsRichiesta;
//	}
//
//	public void setDataInsRichiesta(Date dataInsRichiesta) {
//		this.dataInsRichiesta = dataInsRichiesta;
//	}
//
//
//	public Date getDataUpdRichiesta() {
//		return dataUpdRichiesta;
//	}
//
//	public void setDataUpdRichiesta(Date dataUpdRichiesta) {
//		this.dataUpdRichiesta = dataUpdRichiesta;
//	}

	public Timestamp getDataOraInsert() {
		return dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
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
	
	
	public Timestamp getDataOraUpdate() {
		return dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CnmTRichiesta [idRichiesta=");
		builder.append(idRichiesta);
		builder.append(", idFruitore=");
		builder.append(idFruitore);
		builder.append(", cnmDStatoRichiesta=");
		builder.append(cnmDStatoRichiesta);
		builder.append(", cnmDServizio=");
		builder.append(cnmDServizio);
		builder.append(", dataOraInsert=");
		builder.append(dataOraInsert);
		builder.append(", dataOraUpdate=");
		builder.append(dataOraUpdate);
		builder.append(", cnmTUser1=");
		builder.append(cnmTUser1);
		builder.append(", cnmTUser2=");
		builder.append(cnmTUser2);
		builder.append("]");
		return builder.toString();
	}



	
	

	


	

	
	
	
}
