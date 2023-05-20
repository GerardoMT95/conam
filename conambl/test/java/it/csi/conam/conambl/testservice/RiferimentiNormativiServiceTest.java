/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.leggi.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class RiferimentiNormativiServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(RiferimentiNormativiServiceTest.class);

	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;

	@Test
	public void getAmbitiByIdEnte() {
		logger.info("START");
		List<AmbitoVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			response = riferimentiNormativiService.getAmbitiByIdEnte(1L, false, false, null);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void getNormeByIdAmbitoAndIdEnte() {
		logger.info("START");
		List<NormaVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			response = riferimentiNormativiService.getNormeByIdAmbitoAndIdEnte(48, 1L, false, false, null);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void getArticoliByIdNorma() {
		logger.info("START");
		List<ArticoloVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			response = riferimentiNormativiService.getArticoliByIdNormaAndEnte(1L, 1L, false, false, null);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void getCommaByIdArticolo() {
		logger.info("START");
		List<CommaVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			response = riferimentiNormativiService.getCommaByIdArticolo(1L, false, false, null);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void getLetteraByIdComma() {
		logger.info("START");
		List<LetteraVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			response = riferimentiNormativiService.getLetteraByIdComma(1L, false, false, null);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

}
