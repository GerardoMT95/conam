/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import it.csi.conam.conambl.vo.calendario.CalendarReminderDataVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2017
 */
public interface CalendarService {

	CalendarEventVO saveEvent(CalendarEventVO request, UserDetails userDetails);

	List<CalendarEventVO> getEvents(CalendarEventRequest request, UserDetails userDetails);

	void deleteEvent(Integer id, UserDetails userDetails);

	CalendarReminderDataVO getReminderData();
}
