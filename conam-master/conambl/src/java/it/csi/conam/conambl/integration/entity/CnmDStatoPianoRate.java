/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_stato_piano_rate database table.
 * 
 */
@Entity
@Table(name="cnm_d_stato_piano_rate")
@NamedQuery(name="CnmDStatoPianoRate.findAll", query="SELECT c FROM CnmDStatoPianoRate c")
public class CnmDStatoPianoRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stato_piano_rate")
	private long idStatoPianoRate;

	@Column(name="desc_stato_piano_rate")
	private String descStatoPianoRate;

	//bi-directional many-to-one association to CnmTPianoRate
	@OneToMany(mappedBy="cnmDStatoPianoRate")
	private List<CnmTPianoRate> cnmTPianoRates;

	public CnmDStatoPianoRate() {
	}

	public long getIdStatoPianoRate() {
		return this.idStatoPianoRate;
	}

	public void setIdStatoPianoRate(long idStatoPianoRate) {
		this.idStatoPianoRate = idStatoPianoRate;
	}

	public String getDescStatoPianoRate() {
		return this.descStatoPianoRate;
	}

	public void setDescStatoPianoRate(String descStatoPianoRate) {
		this.descStatoPianoRate = descStatoPianoRate;
	}

	public List<CnmTPianoRate> getCnmTPianoRates() {
		return this.cnmTPianoRates;
	}

	public void setCnmTPianoRates(List<CnmTPianoRate> cnmTPianoRates) {
		this.cnmTPianoRates = cnmTPianoRates;
	}

	public CnmTPianoRate addCnmTPianoRate(CnmTPianoRate cnmTPianoRate) {
		getCnmTPianoRates().add(cnmTPianoRate);
		cnmTPianoRate.setCnmDStatoPianoRate(this);

		return cnmTPianoRate;
	}

	public CnmTPianoRate removeCnmTPianoRate(CnmTPianoRate cnmTPianoRate) {
		getCnmTPianoRates().remove(cnmTPianoRate);
		cnmTPianoRate.setCnmDStatoPianoRate(null);

		return cnmTPianoRate;
	}

}
