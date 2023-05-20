/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cnm_d_messaggio database table.
 * 
 */
@Entity
@Table(name="cnm_d_messaggio")
@NamedQuery(name="CnmDMessaggio.findAll", query="SELECT c FROM CnmDMessaggio c")
public class CnmDMessaggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_messaggio")
	private Integer idMessaggio;

	@Column(name="cod_messaggio")
	private String codMessaggio;

	@Column(name="desc_messaggio")
	private String descMessaggio;

	//bi-directional many-to-one association to CnmDTipoMessaggio
	@ManyToOne
	@JoinColumn(name="id_tipo_messaggio")
	private CnmDTipoMessaggio cnmDTipoMessaggio;

	public CnmDMessaggio() {
	}

	public Integer getIdMessaggio() {
		return this.idMessaggio;
	}

	public void setIdMessaggio(Integer idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getCodMessaggio() {
		return this.codMessaggio;
	}

	public void setCodMessaggio(String codMessaggio) {
		this.codMessaggio = codMessaggio;
	}

	public String getDescMessaggio() {
		return this.descMessaggio;
	}

	public void setDescMessaggio(String descMessaggio) {
		this.descMessaggio = descMessaggio;
	}

	public CnmDTipoMessaggio getCnmDTipoMessaggio() {
		return this.cnmDTipoMessaggio;
	}

	public void setCnmDTipoMessaggio(CnmDTipoMessaggio cnmDTipoMessaggio) {
		this.cnmDTipoMessaggio = cnmDTipoMessaggio;
	}

}
