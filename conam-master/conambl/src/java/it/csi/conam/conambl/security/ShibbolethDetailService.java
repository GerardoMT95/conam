/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ShibbolethDetailService {
	 UserDetails caricaUtenteDaIdentita(IdentityDetails identitaIride) throws UsernameNotFoundException;

}
