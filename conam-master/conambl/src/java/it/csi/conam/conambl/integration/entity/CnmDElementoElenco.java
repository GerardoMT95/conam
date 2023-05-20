/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the cnm_d_elemento_elenco database table.
 * 
 */
@Entity
@Table(name="cnm_d_elemento_elenco")
@NamedQuery(name="CnmDElementoElenco.findAll", query="SELECT c FROM CnmDElementoElenco c")
public class CnmDElementoElenco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_elemento_elenco")
	private long idElementoElenco;

	@Column(name="desc_elemento_elenco")
	private String descElementoElenco;

	@Column(name="id_elenco")
	private BigDecimal idElenco;

	//bi-directional many-to-one association to CnmDStatoOrdVerbSog
	@OneToMany(mappedBy="cnmDElementoElenco")
	private List<CnmDStatoOrdVerbSog> cnmDStatoOrdVerbSogs;

	public CnmDElementoElenco() {
	}

	public long getIdElementoElenco() {
		return this.idElementoElenco;
	}

	public void setIdElementoElenco(long idElementoElenco) {
		this.idElementoElenco = idElementoElenco;
	}

	public String getDescElementoElenco() {
		return this.descElementoElenco;
	}

	public void setDescElementoElenco(String descElementoElenco) {
		this.descElementoElenco = descElementoElenco;
	}

	public BigDecimal getIdElenco() {
		return this.idElenco;
	}

	public void setIdElenco(BigDecimal idElenco) {
		this.idElenco = idElenco;
	}

	public List<CnmDStatoOrdVerbSog> getCnmDStatoOrdVerbSogs() {
		return this.cnmDStatoOrdVerbSogs;
	}

	public void setCnmDStatoOrdVerbSogs(List<CnmDStatoOrdVerbSog> cnmDStatoOrdVerbSogs) {
		this.cnmDStatoOrdVerbSogs = cnmDStatoOrdVerbSogs;
	}

	public CnmDStatoOrdVerbSog addCnmDStatoOrdVerbSog(CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog) {
		getCnmDStatoOrdVerbSogs().add(cnmDStatoOrdVerbSog);
		cnmDStatoOrdVerbSog.setCnmDElementoElenco(this);

		return cnmDStatoOrdVerbSog;
	}

	public CnmDStatoOrdVerbSog removeCnmDStatoOrdVerbSog(CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog) {
		getCnmDStatoOrdVerbSogs().remove(cnmDStatoOrdVerbSog);
		cnmDStatoOrdVerbSog.setCnmDElementoElenco(null);

		return cnmDStatoOrdVerbSog;
	}

}
