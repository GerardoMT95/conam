/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.RiscossioneDispatcher;
import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.RicercaStoricaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.SalvaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("riscossione")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class RiscossioneResource extends SpringSupportedResource {

	@Autowired
	private RiscossioneDispatcher riscossioneDispatcher;

	@POST
	@Path("/ricercaOrdinanzaSoggetti")
	public Response ricercaOrdinanzaSoggetti(RicercaSoggettiRiscossioneCoattivaRequest request) {
		return Response.ok().entity(riscossioneDispatcher.ricercaOrdinanzaSoggetti(request)).build();
	}

	@POST
	@Path("/aggiungiInListaRiscossione")
	public Response aggiungiInListaRiscossione(List<Integer> request) {
		UserDetails userDetails = SecurityUtils.getUser();
		return Response.ok().entity(riscossioneDispatcher.aggiungiInListaRiscossione(request, userDetails)).build();
	}

	@GET
	@Path("/getSoggettiRiscossioneBozza")
	public Response getSoggettiRiscossioneBozza() {
		return Response.ok().entity(riscossioneDispatcher.getSoggettiRiscossione(true, null)).build();
	}

	@POST
	@Path("/getSoggettiRiscossioneNoBozza")
	public Response getSoggettiRiscossione(Long idStato) {
		return Response.ok().entity(riscossioneDispatcher.getSoggettiRiscossione(false, idStato)).build();
	}

	@POST
	@Path("/salvaSoggettoRiscossione")
	public Response salvaSoggettoRiscossione(SalvaSoggettiRiscossioneCoattivaRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		return Response.ok().entity(riscossioneDispatcher.salvaSoggettoRiscossione(request, userDetails)).build();
	}

	@DELETE
	@Path("/deleteSoggettoRiscossione")
	public Response deleteSoggettoRiscossione(@QueryParam("idRiscossione") Integer idRiscossione) {
		riscossioneDispatcher.deleteSoggettoRiscossione(idRiscossione);
		return Response.ok().build();
	}

	@POST
	@Path("/getSoggettiStoriciRiscossione")
	public Response getSoggettiStoriciRiscossione(RicercaStoricaSoggettiRiscossioneCoattivaRequest request) {
		return Response.ok().entity(riscossioneDispatcher.getSoggettiStoriciRiscossione(request)).build();
	}

	@POST
	@Path("/invioSoggettiInRiscossione")
	public Response invioSoggettiInRiscossione() {
		UserDetails userDetails = SecurityUtils.getUser();
		return Response.ok().entity(riscossioneDispatcher.invioSoggettiInRiscossione(userDetails)).build();
	}

	@GET
	@Path("/getstatiRiscossioneCoattiva")
	public Response getstatiRiscossioneCoattiva() {
		return Response.ok(riscossioneDispatcher.getstatiRiscossioneCoattiva()).build();
	}
}
