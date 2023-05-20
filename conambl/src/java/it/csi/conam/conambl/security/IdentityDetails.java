/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import java.io.Serializable;

/**
 * 
 * @author lorenzo.fantini
 * Usata da Iride
 */
public class IdentityDetails implements Serializable {

	private static final long serialVersionUID = -1938371395836781704L;
	private String identity;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
