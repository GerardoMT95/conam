/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_modalita_caricamento database table.
 * 
 */
@Entity
@Table(name="cnm_d_modalita_caricamento")
@NamedQuery(name="CnmDModalitaCaricamento.findAll", query="SELECT c FROM CnmDModalitaCaricamento c")
public class CnmDModalitaCaricamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_modalita_caricamento")
	private long idModalitaCaricamento;

	@Column(name="desc_modalita_caricamento")
	private String descModalitaCaricamento;

	//bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy="cnmDModalitaCaricamento")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos;



	
	
	

	public long getIdModalitaCaricamento() {
		return idModalitaCaricamento;
	}

	public void setIdModalitaCaricamento(long idModalitaCaricamento) {
		this.idModalitaCaricamento = idModalitaCaricamento;
	}

	public String getDescModalitaCaricamento() {
		return descModalitaCaricamento;
	}

	public void setDescModalitaCaricamento(String descModalitaCaricamento) {
		this.descModalitaCaricamento = descModalitaCaricamento;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos() {
		return cnmTScrittoDifensivos;
	}

	public void setCnmTScrittoDifensivos(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos) {
		this.cnmTScrittoDifensivos = cnmTScrittoDifensivos;
	}
	
	

}
