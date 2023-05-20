/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.UserService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.ProfiloVO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class UserServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService;

	@Test
	public void getProfilo() {
		logger.info("START");
		ProfiloVO response = null;
		try {
			response = userService.getProfilo(mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

}
