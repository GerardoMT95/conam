/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.UserDispatcher;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.ProfiloVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Path("user")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UserResource extends SpringSupportedResource {

	@Autowired
	private UserDispatcher userDispatcher;

	@GET
	@Path("/getProfilo")
	public Response getProfilo() {
		UserDetails utente = SecurityUtils.getUser();
		ProfiloVO profiloVO = userDispatcher.getProfilo(utente);
		return Response.ok().entity(profiloVO).build();

	}

	@GET
	@Path("/localLogout")
	public Response localLogout(@Context HttpServletRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(req, null, null);
		}
		return Response.ok().build();
	}
	
}
