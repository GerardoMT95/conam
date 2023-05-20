/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.ordinanza.RicercaOrdinanzaService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(RestJunitClassRunner.class)
public class RicercaOrdinanzaServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(RicercaOrdinanzaServiceTest.class);

	@Autowired
	private RicercaOrdinanzaService ricercaOrdinanzaService;

	@Test
	public void testRicercaOrdinanza() {

		logger.info("START");

		RicercaOrdinanzaRequest request = new RicercaOrdinanzaRequest();

		request.setNumeroDeterminazione("12");

		List<MinOrdinanzaVO> response = ricercaOrdinanzaService.ricercaOrdinanza(request, mockProfileByToken(TokenMock.DEMO20));

		assertNotNull(response);

		logger.info("END");

	}

	@Test
	public void testRicercaSoggettiOrdinanza() {

		logger.info("START");

		RicercaSoggettiOrdinanzaRequest request = new RicercaSoggettiOrdinanzaRequest();

		request.setNumeroDeterminazione("12");
		request.setOrdinanzeSollecitate(true);

		List<SoggettoVO> response = ricercaOrdinanzaService.ricercaSoggetti(request, false);

		assertNotNull(response);

		logger.info("END");

	}

}
