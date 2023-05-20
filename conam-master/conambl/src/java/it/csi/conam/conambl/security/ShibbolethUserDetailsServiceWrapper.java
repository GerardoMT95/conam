/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * 
 * @author lorenzo.fantini Classe che effettua fisicamente la pre autenticazione
 *         su Shibboleth e passa il controllo a Spring MVC Da loadUserDetails,
 *         nel caso in cui l'utente venga riconosciuto, uscira un utente
 *         valorizzato con nome, cognome, cf e ruoli
 */
public class ShibbolethUserDetailsServiceWrapper implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private ShibbolethDetailService userDetailsService = null;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authentication) throws UsernameNotFoundException {

		IdentityDetails identityDetails = (IdentityDetails) authentication.getPrincipal();

		return this.userDetailsService.caricaUtenteDaIdentita(identityDetails);
	}

	public void setUserDetailsService(ShibbolethDetailService aUserDetailsService) {
		this.userDetailsService = aUserDetailsService;
	}

	public ShibbolethDetailService getUserDetailsService() {
		return userDetailsService;
	}

}
