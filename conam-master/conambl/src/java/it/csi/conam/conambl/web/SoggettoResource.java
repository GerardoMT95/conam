/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.SoggettoDispatcher;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("soggetto")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class SoggettoResource extends SpringSupportedResource {

	@Autowired
	private SoggettoDispatcher soggettoDispatcher;
	

	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	/*@POST
	@Path("/ricercaSoggetti")
	public Response ricercaSoggetti(MinSoggettoVO soggetto) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(soggettoDispatcher.ricercaSoggetti(soggetto, utente)).build();
	}*/
	@POST
	@Path("/ricercaSoggetti")
	public Response ricercaSoggetti(@Valid SoggettoRequest soggetto) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(soggettoDispatcher.ricercaSoggetti(soggetto, utente)).build();
	}

	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/

	@GET
	@Path("/dettaglioSoggetto")
	public Response dettaglioSoggettoVerbale(@QueryParam("idSoggetto") Integer id, @QueryParam("idVerbale") Integer idVerbale) {
		UserDetails utente = SecurityUtils.getUser();
		if(idVerbale == null) {
			return Response.ok(soggettoDispatcher.getSoggettoById(id, utente)).build();
		}
		return Response.ok(soggettoDispatcher.getSoggettoByIdAndIdVerbale(id, idVerbale, utente)).build();
	}
	
	@GET
	@Path("/dettaglioSoggettoPregressi")
	public Response dettaglioSoggettoPregressi(@QueryParam("idSoggetto") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(soggettoDispatcher.getSoggettoPregressiById(id, utente)).build();
	}
	
}
