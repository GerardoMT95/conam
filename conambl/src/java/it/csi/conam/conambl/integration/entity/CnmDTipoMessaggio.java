/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_tipo_messaggio database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_messaggio")
@NamedQuery(name="CnmDTipoMessaggio.findAll", query="SELECT c FROM CnmDTipoMessaggio c")
public class CnmDTipoMessaggio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_messaggio")
	private long idTipoMessaggio;

	@Column(name="desc_tipo_messaggio")
	private String descTipoMessaggio;

	//bi-directional many-to-one association to CnmDMessaggio
	@OneToMany(mappedBy="cnmDTipoMessaggio")
	private List<CnmDMessaggio> cnmDMessaggios;

	public CnmDTipoMessaggio() {
	}

	public long getIdTipoMessaggio() {
		return this.idTipoMessaggio;
	}

	public void setIdTipoMessaggio(long idTipoMessaggio) {
		this.idTipoMessaggio = idTipoMessaggio;
	}

	public String getDescTipoMessaggio() {
		return this.descTipoMessaggio;
	}

	public void setDescTipoMessaggio(String descTipoMessaggio) {
		this.descTipoMessaggio = descTipoMessaggio;
	}

	public List<CnmDMessaggio> getCnmDMessaggios() {
		return this.cnmDMessaggios;
	}

	public void setCnmDMessaggios(List<CnmDMessaggio> cnmDMessaggios) {
		this.cnmDMessaggios = cnmDMessaggios;
	}

	public CnmDMessaggio addCnmDMessaggio(CnmDMessaggio cnmDMessaggio) {
		getCnmDMessaggios().add(cnmDMessaggio);
		cnmDMessaggio.setCnmDTipoMessaggio(this);

		return cnmDMessaggio;
	}

	public CnmDMessaggio removeCnmDMessaggio(CnmDMessaggio cnmDMessaggio) {
		getCnmDMessaggios().remove(cnmDMessaggio);
		cnmDMessaggio.setCnmDTipoMessaggio(null);

		return cnmDMessaggio;
	}

}
