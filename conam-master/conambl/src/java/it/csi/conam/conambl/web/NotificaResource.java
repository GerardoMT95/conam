/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.NotificaDispatcher;
import it.csi.conam.conambl.request.notifica.NotificaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("notifica")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class NotificaResource extends SpringSupportedResource {

	@Autowired
	private NotificaDispatcher notificaDispatcher;

	@GET
	@Path("/getModalitaNotifica")
	public Response getModalitaNotificaOrdinanza() {
		return Response.ok(notificaDispatcher.getModalitaNotifica()).build();
	}

	@POST
	@Path("/salvaNotifica")
	public Response salvaNotificaOrdinanza(NotificaVO notifica) {
		UserDetails userDetails = SecurityUtils.getUser();
		return Response.ok(notificaDispatcher.salvaNotifica(notifica, userDetails)).build();
	}

	@POST
	@Path("/getNotificaBy")
	public Response getModalitaNotificaOrdinanza(NotificaRequest notificaRequest) {
		return Response.ok(notificaDispatcher.getNotificaBy(notificaRequest)).build();
	}

}
