/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.LuoghiService;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
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
public class LuoghiServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(LuoghiServiceTest.class);

	@Autowired
	private LuoghiService luoghiService;

	@Test
	public void getRegioni() {
		logger.info("START");
		List<RegioneVO> response = null;
		try {
			response = luoghiService.getRegioni();
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void getProvinceByIdRegione() {
		logger.info("START");
		List<ProvinciaVO> response = null;
		try {
			response = luoghiService.getProviceByIdRegione(1L);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void getComuniByIdProvincia() {
		logger.info("START");
		List<ComuneVO> response = null;
		try {
			response = luoghiService.getComuniByIdProvincia(1L);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void getNazioni() {
		logger.info("START");
		List<NazioneVO> response = null;
		try {
			response = luoghiService.getNazioni();
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}
}
