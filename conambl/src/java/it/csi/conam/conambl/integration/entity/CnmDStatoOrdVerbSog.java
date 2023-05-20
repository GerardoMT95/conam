/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_ord_verb_sog database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_ord_verb_sog")
@NamedQuery(name="CnmDStatoOrdVerbSog.findAll", query="SELECT c FROM CnmDStatoOrdVerbSog c")
public class CnmDStatoOrdVerbSog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_ord_verb_sog")
	private long idStatoOrdVerbSog;

	@Column(name="desc_stato_ord_verb_sog")
	private String descStatoOrdVerbSog;

	//bi-directional many-to-one association to CnmDElementoElenco
	@ManyToOne
	@JoinColumn(name="id_elemento_elenco")
	private CnmDElementoElenco cnmDElementoElenco;

	//bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@OneToMany(mappedBy="cnmDStatoOrdVerbSog")
	private List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs;

	public CnmDStatoOrdVerbSog() {
	}

	public long getIdStatoOrdVerbSog() {
		return this.idStatoOrdVerbSog;
	}

	public void setIdStatoOrdVerbSog(long idStatoOrdVerbSog) {
		this.idStatoOrdVerbSog = idStatoOrdVerbSog;
	}

	public String getDescStatoOrdVerbSog() {
		return this.descStatoOrdVerbSog;
	}

	public void setDescStatoOrdVerbSog(String descStatoOrdVerbSog) {
		this.descStatoOrdVerbSog = descStatoOrdVerbSog;
	}

	public CnmDElementoElenco getCnmDElementoElenco() {
		return this.cnmDElementoElenco;
	}

	public void setCnmDElementoElenco(CnmDElementoElenco cnmDElementoElenco) {
		this.cnmDElementoElenco = cnmDElementoElenco;
	}

	public List<CnmROrdinanzaVerbSog> getCnmROrdinanzaVerbSogs() {
		return this.cnmROrdinanzaVerbSogs;
	}

	public void setCnmROrdinanzaVerbSogs(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs) {
		this.cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogs;
	}

	public CnmROrdinanzaVerbSog addCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().add(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(this);

		return cnmROrdinanzaVerbSog;
	}

	public CnmROrdinanzaVerbSog removeCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		getCnmROrdinanzaVerbSogs().remove(cnmROrdinanzaVerbSog);
		cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(null);

		return cnmROrdinanzaVerbSog;
	}

}
