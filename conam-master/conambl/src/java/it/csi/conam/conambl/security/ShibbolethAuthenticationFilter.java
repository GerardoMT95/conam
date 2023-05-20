/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.TokenMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lorenzo.fantini Filtro di pre autenticazione usato da spring mvc
 *         (deriva da shibboleth)
 */
public class ShibbolethAuthenticationFilter extends RequestHeaderAuthenticationFilter {
	// RequestHeader
	private String shibbIdentity; // Shib-Iride-IdentitaDigitale

	private String shibbTestMode;

	private Logger logger = LoggerFactory.getLogger(Constants.HANDLER_SECURITY);

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		IdentityDetails principal = new IdentityDetails();
		String token = request.getHeader(this.shibbIdentity);
		if (token == null && "enabled".equalsIgnoreCase(shibbTestMode)) {
			token = TokenMock.DEMO20;
//			token = "AAAAAA00B77B000F/CSI PIEMONTE/DEMO 20/ACTALIS_EU/20210910101800/16/MWClpvfpR2ccfJpkqqGQlg==";
		}
		logger.info("[ShibbolethAuthenticationFilter::getPreAuthenticatedPrincipal] <" + request.getRequestURI() + "> (token::" + token + ")");
		principal.setIdentity(token);
		return principal;
	}

	public String getShibbTestMode() {
		return shibbTestMode;
	}

	public void setShibbTestMode(String shibbTestMode) {
		this.shibbTestMode = shibbTestMode;
	}

	public String getShibbIdentity() {
		return shibbIdentity;
	}

	public void setShibbIdentity(String shibbIdentity) {
		this.shibbIdentity = shibbIdentity;
	}

}
