/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.CalendarService;
import it.csi.conam.conambl.dispatcher.CalendarDispatcher;
import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import it.csi.conam.conambl.vo.calendario.CalendarReminderDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */

@Component
public class CalendarDispatcherImpl implements CalendarDispatcher {

	@Autowired
	private CalendarService calendarService;

	@Override
	public List<CalendarEventVO> getEvents(CalendarEventRequest request, UserDetails userDetails) {
		return calendarService.getEvents(request, userDetails);
	}

	@Override
	public CalendarEventVO saveEvent(CalendarEventVO calendarEventVO, UserDetails userDetails) {
		return calendarService.saveEvent(calendarEventVO, userDetails);
	}

	@Override
	public void deleteEvent(Integer id, UserDetails userDetails) {
		calendarService.deleteEvent(id, userDetails);
	}

	@Override
	public CalendarReminderDataVO getReminderData() {
		return calendarService.getReminderData();
	}

}
