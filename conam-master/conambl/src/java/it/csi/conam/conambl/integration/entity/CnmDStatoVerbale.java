/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_verbale database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_verbale")
@NamedQuery(name="CnmDStatoVerbale.findAll", query="SELECT c FROM CnmDStatoVerbale c")
public class CnmDStatoVerbale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_verbale")
	private long idStatoVerbale;

	@Column(name="desc_stato_verbale")
	private String descStatoVerbale;

	//bi-directional many-to-many association to CnmDTipoAllegato
	@ManyToMany(mappedBy="cnmDStatoVerbales")
	private List<CnmDTipoAllegato> cnmDTipoAllegatos;

	//bi-directional many-to-one association to CnmSStatoVerbale
	@OneToMany(mappedBy="cnmDStatoVerbale")
	private List<CnmSStatoVerbale> cnmSStatoVerbales;

	//bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy="cnmDStatoVerbale")
	private List<CnmTVerbale> cnmTVerbales;

	public CnmDStatoVerbale() {
	}

	public long getIdStatoVerbale() {
		return this.idStatoVerbale;
	}

	public void setIdStatoVerbale(long idStatoVerbale) {
		this.idStatoVerbale = idStatoVerbale;
	}

	public String getDescStatoVerbale() {
		return this.descStatoVerbale;
	}

	public void setDescStatoVerbale(String descStatoVerbale) {
		this.descStatoVerbale = descStatoVerbale;
	}

	public List<CnmDTipoAllegato> getCnmDTipoAllegatos() {
		return this.cnmDTipoAllegatos;
	}

	public void setCnmDTipoAllegatos(List<CnmDTipoAllegato> cnmDTipoAllegatos) {
		this.cnmDTipoAllegatos = cnmDTipoAllegatos;
	}

	public List<CnmSStatoVerbale> getCnmSStatoVerbales() {
		return this.cnmSStatoVerbales;
	}

	public void setCnmSStatoVerbales(List<CnmSStatoVerbale> cnmSStatoVerbales) {
		this.cnmSStatoVerbales = cnmSStatoVerbales;
	}

	public CnmSStatoVerbale addCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().add(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmDStatoVerbale(this);

		return cnmSStatoVerbale;
	}

	public CnmSStatoVerbale removeCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().remove(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmDStatoVerbale(null);

		return cnmSStatoVerbale;
	}

	public List<CnmTVerbale> getCnmTVerbales() {
		return this.cnmTVerbales;
	}

	public void setCnmTVerbales(List<CnmTVerbale> cnmTVerbales) {
		this.cnmTVerbales = cnmTVerbales;
	}

	public CnmTVerbale addCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().add(cnmTVerbale);
		cnmTVerbale.setCnmDStatoVerbale(this);

		return cnmTVerbale;
	}

	public CnmTVerbale removeCnmTVerbale(CnmTVerbale cnmTVerbale) {
		getCnmTVerbales().remove(cnmTVerbale);
		cnmTVerbale.setCnmDStatoVerbale(null);

		return cnmTVerbale;
	}

}
