/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.CalendarService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class CalendarServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(CalendarServiceTest.class);

	@Autowired
	private CalendarService calendarService;

	@Test
	public void getEvents() {
		logger.info("START");
		List<CalendarEventVO> response = null;
		try {
			CalendarEventRequest request = new CalendarEventRequest();
			response = calendarService.getEvents(request, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void saveEvent() {
		logger.info("START");
		try {
			CalendarEventVO request = new CalendarEventVO();
			request.setDataInizio(LocalDateTime.of(2018, 9, 27, 15, 30));
			request.setTribunale("ALESSANDRIA");
			request.setDataInizio(LocalDateTime.of(2018, 9, 27, 14, 30));
			request.setNomeGiudice("Pippo");
			calendarService.saveEvent(request, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
	}

	@Test
	public void deleteEvent() {
		logger.info("START");
		try {
			Integer id = 0;
			calendarService.deleteEvent(id, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
	}

}
