/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.security;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

public class SecurityUtils {
	public static boolean hasRoles(String[] roles) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}

		org.springframework.security.core.Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return false;
		}

		boolean hasRole;
		for (String role : roles) {
			hasRole = false;
			for (GrantedAuthority auth : authentication.getAuthorities()) {
				if (role.equals(auth.getAuthority())) {
					hasRole = true;
				}
			}
			if (!hasRole) {
				return false;
			}
		}

		return true;
	}

	public static UserDetails getUser() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static org.springframework.security.core.Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static boolean isEnteValido(Long idEnte) {
		return isValido(getUser().getEntiAccertatore(),idEnte);
	}

	public static boolean isEnteNormaValido(Long idEnte) {
		return isValido(getUser().getEntiLegge(),idEnte);
	}

	private static boolean isValido(List<EnteVO> enti,Long idEnte){
		if (enti != null && !enti.isEmpty()) {
			for (EnteVO e : enti)
				if (e.getId().longValue() == idEnte.longValue())
					return true;
		}
		return false;
	}
	public static AppGrantedAuthority getRuolo(Collection<GrantedAuthority> granted) {
		if (granted != null && !granted.isEmpty()) {
			for (GrantedAuthority g : granted) {
				AppGrantedAuthority app = (AppGrantedAuthority) g;
				if (app.getTipoAuthority() == AppGrantedAuthority.TIPO_AUTHORITY_RUOLO_DB)
					return app;
			}
		}
		throw new RuntimeException("Ruolo non trovato in sessione");
	}

	public static void verbaleSecurityView(CnmTVerbale cnmTVerbale, EnteVO enteLegge) {
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(getUser().getAuthorities());
		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			if (!SecurityUtils.isEnteNormaValido(enteLegge.getId()))
				throw new SecurityException("non si ha i permessi per vedere il verbale");
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE)) {
			if (!SecurityUtils.isEnteValido(cnmTVerbale.getCnmDEnte().getIdEnte()))
				throw new SecurityException("non si ha i permessi per vedere il verbale");
		} else
			throw new SecurityException("Ruolo non riconosciuto dal sistema");
	}

}
