/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import org.springframework.security.core.GrantedAuthority;

public class AppGrantedAuthority implements GrantedAuthority {

	public static final int TIPO_AUTHORITY_USE_CASE_DB = 1;
	public static final int TIPO_AUTHORITY_RUOLO_DB = 2;
	public static final int TIPO_AUTHORITY_ALTRO = 3;

	private String codice;
	private String descrizione;
	private int tipoAuthority;

	private static final long serialVersionUID = 7104984587412214819L;

	public AppGrantedAuthority(String codice, String descrizione, int tipoAuthority) {
		super();
		this.codice = codice;
		this.tipoAuthority = tipoAuthority;
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public int getTipoAuthority() {
		return tipoAuthority;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public void setTipoAuthority(int tipoAuthority) {
		this.tipoAuthority = tipoAuthority;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + tipoAuthority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppGrantedAuthority other = (AppGrantedAuthority) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (tipoAuthority != other.tipoAuthority)
			return false;
		return true;
	}

	@Override
	public String getAuthority() {
		return this.getCodice();
	}

}
