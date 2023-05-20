/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
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
public class AllegatoVerbaleServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(AllegatoVerbaleServiceTest.class);

	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;
	@Autowired
	private CommonAllegatoService allegatoService;

	@Test
	public void getTipologiaAllegatiByIdVerbale() {
		logger.info("START");
		List<TipoAllegatoVO> response = null;
		try {
			response = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(new Integer(19), null, false);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void configAllegati() {
		logger.info("START");
		List<ConfigAllegatoVO> response = null;
		try {
			response = allegatoService.getConfigAllegati();
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

}
