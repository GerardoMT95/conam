/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UtenteVO extends ProfiloVO {

	private static final long serialVersionUID = -548472540241324347L;

	private long idRuolo;
	private String descRuolo;
	private Long id;
	private int isIstruttore;

	public long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescRuolo() {
		return descRuolo;
	}

	public void setDescRuolo(String descRuolo) {
		this.descRuolo = descRuolo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int getIsIstruttore() {
		return isIstruttore;
	}

	public void setIsIstruttore(int isIstruttore) {
		this.isIstruttore = isIstruttore;
	}

}
