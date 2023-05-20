/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.gmscore.dto.Anagrafica;
import it.csi.gmscore.dto.ModuloRicercaPF;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 24 ott 2018
 */
@RunWith(RestJunitClassRunner.class)
public class StasServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(StasServiceTest.class);

	@Autowired
	private StasServFacade stasServFacade;

	@Test
	public void testCf() {
		logger.info("START");
		Anagrafica[] response = null;
		try {
			response = stasServFacade.ricercaSoggettoCF("GLOPLA75E21L219X", TokenMock.DEMO20);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void testRicercaPersonaFisicaCompleta() {
		logger.info("START");
		Anagrafica[] response = null;
		try {
			String token = TokenMock.DEMO20;
			ModuloRicercaPF moduloRicerca = new ModuloRicercaPF();
			// moduloRicerca.setDataNascita("20/05/1975");
			moduloRicerca.setCognome("GOLA");
			moduloRicerca.setNome("PAOLO");
			// moduloRicerca.setSesso("M");
			// LuogoNascitaFiltroRicerca luogoNascita =
			// LuogoNascitaFiltroRicerca.creaLuogoNascitaNazionale("TORINO",
			// "TORINO");
			// moduloRicerca.setLuogoNascita(luogoNascita);
			response = stasServFacade.ricercaPersonaFisicaCompleta(moduloRicerca, token);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}
}
