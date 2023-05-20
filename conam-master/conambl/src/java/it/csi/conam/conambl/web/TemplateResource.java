/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.TemplateDispatcher;
import it.csi.conam.conambl.request.template.DatiTemplateRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
@Path("template")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class TemplateResource extends SpringSupportedResource {

	@Autowired
	private TemplateDispatcher templateDispatcher;

	@POST
	@Path("/getDatiTemplate")
	public Response getDatiTemplate(DatiTemplateRequest request) {//(@Valid @NotNull(message = "RESCON17") DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		DatiTemplateVO response = templateDispatcher.getDatiTemplate(request, userDetails);
		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/stampaTemplate")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response stampaTemplate(@Valid @NotNull(message = "RESCON18") DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		byte[] response = templateDispatcher.stampaTemplate(request, userDetails);
		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/downloadTemplate")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadTemplate(@Valid @NotNull(message = "RESCON19") DatiTemplateRequest request) {
		// 20200825_LC gestione doc multiplo
		UserDetails userDetails = SecurityUtils.getUser();
		List<DocumentoScaricatoVO> docList = templateDispatcher.downloadTemplate(request, userDetails);
		
		// 20200827_LC
		byte[] file = null;
		
		if (docList.size()==1)
			file=docList.get(0).getFile();
		
		return Response.ok().entity(file).build();
		
//		byte[] response = templateDispatcher.downloadTemplate(request, userDetails);
//		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/nomiTemplate")
	public Response nomiTemplate(@Valid @NotNull(message = "RESCON19") DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		DatiTemplateVO vo = templateDispatcher.nomiTemplate(request, userDetails);
		return Response.ok(vo).build();
	}

	@GET
	@Path("/getMessaggio")
	public Response getMessaggio(@QueryParam("codice") String codice) {
		MessageVO messaggioVO = templateDispatcher.getMessaggioByCodice(codice);
		return Response.ok().entity(messaggioVO).build();

	}

}
