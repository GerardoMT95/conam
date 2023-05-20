/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.verbale.RicercaVerbaleService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.leggi.NormaVO;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
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
public class RicercaVerbaleServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(RicercaVerbaleServiceTest.class);

	@Autowired
	private RicercaVerbaleService ricercaVerbaleService;

	@Test
	public void ricercaVerbale() {
		logger.info("START");
		List<MinVerbaleVO> response = null;
		try {
			RicercaVerbaleRequest request = new RicercaVerbaleRequest();
			DatiVerbaleRequest datiVerbale = new DatiVerbaleRequest();

			// SoggettoVerbaleRequest sog = new SoggettoVerbaleRequest();
			// sog.setCodiceFiscale("BVORCR93E08A182G");
			// sog.setTipoSoggetto("T");
			// sog.setPersonaFisica(true);
			// soggettoVerbale.add(sog);

			NormaVO norma = new NormaVO();
			norma.setId(1L);
			datiVerbale.setNorma(norma);
			request.setDatiVerbale(datiVerbale);
			// request.setSoggettoVerbale(soggettoVerbale);

			response = ricercaVerbaleService.ricercaVerbale(request, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

}
