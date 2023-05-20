/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.ScrittoDifensivoDispatcher;
import it.csi.conam.conambl.request.scrittodifensivo.RicercaScrittoDifensivoRequest;
import it.csi.conam.conambl.response.SalvaScrittoDifensivoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;


@Path("scrittoDifensivo")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class ScrittoDifensivoResource extends SpringSupportedResource {

	@Autowired
	private ScrittoDifensivoDispatcher scrittoDifensivoDispatcher;

	
	
	@GET
	@Path("/dettaglioScrittoDifensivo")
	public Response dettaglioScrittoDifensivo(@QueryParam("idScrittoDifensivo") Integer idScrittoDifensivo) {
		UserDetails userDetails = SecurityUtils.getUser();
		ScrittoDifensivoVO scritto = scrittoDifensivoDispatcher.dettaglioScrittoDifensivo(idScrittoDifensivo, userDetails);
		return Response.ok().entity(scritto).build();
	}
	
	
	
	@POST
	@Path("/salvaScrittoDifensivo")
	@Consumes("multipart/form-data")
	public Response salvaScrittoDifensivo(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		SalvaScrittoDifensivoResponse response = scrittoDifensivoDispatcher.salvaScrittoDifensivo(map.get("data"), map.get("file"), userDetails);
		return Response.ok().entity(response).build();
	}
	
	

	@POST
	@Path("/ricercaScrittoDifensivo")
	public Response ricercaScrittoDifensivo(RicercaScrittoDifensivoRequest request) {
		return Response.ok(scrittoDifensivoDispatcher.ricercaScrittoDifensivo(request)).build();
	}

	
	
	@POST
	@Path("/associaScrittoDifensivo")
	public Response associaScrittoDifensivo(@QueryParam("idVerbale") Integer idVerbale, @QueryParam("idScrittoDifensivo") Integer idScrittoDifensivo) {
		UserDetails userDetails = SecurityUtils.getUser();
		scrittoDifensivoDispatcher.associaScrittoDifensivo(idVerbale, idScrittoDifensivo, userDetails);
		return Response.ok().build();
	}	

	
}
