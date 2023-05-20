/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import it.csi.conam.conambl.vo.calendario.CalendarReminderDataVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface CalendarDispatcher {

	@PreAuthorize(value = AuthorizationRoles.GESTIONE_CALENDARIO_UDIENZE)
	List<CalendarEventVO> getEvents(CalendarEventRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.GESTIONE_CALENDARIO_UDIENZE)
	CalendarEventVO saveEvent(CalendarEventVO calendarEventVO, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.GESTIONE_CALENDARIO_UDIENZE)
	void deleteEvent(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.GESTIONE_CALENDARIO_UDIENZE)
	CalendarReminderDataVO getReminderData();

}
