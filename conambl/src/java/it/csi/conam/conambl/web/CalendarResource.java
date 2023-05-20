/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.CalendarDispatcher;
import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import it.csi.conam.conambl.vo.calendario.CalendarReminderDataVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("calendar")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class CalendarResource extends SpringSupportedResource {

	@Autowired
	private CalendarDispatcher eventoDispatcher;

	@PUT
	@Path("/saveEvent")
	public Response saveEvent(CalendarEventVO calendarEventVO) {
		UserDetails utente = SecurityUtils.getUser();
		CalendarEventVO event = eventoDispatcher.saveEvent(calendarEventVO, utente);
		return Response.ok(event).build();
	}

	@DELETE
	@Path("/deleteEvent/{id}")
	public Response deleteEvent(@Valid @PathParam("id") @NotNull(message = "RESCON03") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		eventoDispatcher.deleteEvent(id, utente);
		return Response.ok().build();
	}

	@POST
	@Path("/getEvents")
	public Response getEvents(CalendarEventRequest request) {
		UserDetails utente = SecurityUtils.getUser();
		List<CalendarEventVO> event = eventoDispatcher.getEvents(request, utente);
		return Response.ok(event).build();
	}
	
	@GET
	@Path("/getReminderData")
	public Response getReminderData() {
		CalendarReminderDataVO reminder = eventoDispatcher.getReminderData();
		return Response.ok(reminder).build();
	}
	

}
