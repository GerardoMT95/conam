/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.ProntuarioService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.leggi.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class ProntuarioServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(ProntuarioServiceTest.class);

	@Autowired
	private ProntuarioService prontuarioService;

	@Test
	public void ricercaLeggeProntuario() {
		logger.info("START");
		List<ProntuarioVO> response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			RicercaLeggeProntuarioRequest request = new RicercaLeggeProntuarioRequest();
			AmbitoVO ambito = new AmbitoVO();
			ambito.setId(4L);
			request.setAmbito(ambito);
			EnteVO enteLegge = new EnteVO();
			enteLegge.setId(1L);
			request.setEnteLegge(enteLegge);
			response = prontuarioService.ricercaLeggeProntuario(request);
		} catch (Exception e) {
			logger.info("Eccezione", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	@Test
	public void salvaLeggeProntuario() {
		logger.info("START");
		ProntuarioVO prontuario = new ProntuarioVO();
		try {
			AmbitoVO ambito = new AmbitoVO();
			ambito.setId(1L);
			prontuario.setAmbito(ambito);

			EnteVO enteLegge = new EnteVO();
			enteLegge.setId(1L);
			prontuario.setEnteLegge(enteLegge);

			NormaVO norma = new NormaVO();
			norma.setDenominazione("L.R. 4/2020");
			norma.setDataFineValidita(LocalDate.of(2020, 1, 20));
			prontuario.setNorma(norma);

			ArticoloVO articolo = new ArticoloVO();
			articolo.setDenominazione("1");
			articolo.setDataFineValidita(LocalDate.of(2020, 1, 25));
			prontuario.setArticolo(articolo);

			CommaVO comma = new CommaVO();
			comma.setDenominazione("2");
			comma.setDataFineValidita(LocalDate.of(2020, 1, 1));
			prontuario.setComma(comma);

			LetteraVO lettera = new LetteraVO();

			lettera.setDenominazione("B");
			lettera.setImportoMisuraRidotta(new BigDecimal(1.20));
			lettera.setDescrizioneIllecito("PESCA SENZA PERMESSO SPORTIVO");
			lettera.setScadenzaImporti(LocalDate.now());
			lettera.setDataFineValidita(LocalDate.of(2020, 2, 1));

			prontuario.setLettera(lettera);

			UserDetails userDetails = mockProfileByToken(TokenMock.DEMO20);
			prontuarioService.salvaLeggeProntuario(prontuario, userDetails);
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}
		logger.info("END");
	}

	@Test
	public void eliminaLeggeProntuario() {
		logger.info("START");
		Long idLettera = 4L;
		try {
			UserDetails userDetails = mockProfileByToken(TokenMock.DEMO20);
			prontuarioService.eliminaLeggeProntuario(idLettera, userDetails);
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}
		logger.info("END");
	}

	@Test
	public void getAmbiti() {
		logger.info("START");
		List<AmbitoVO> response = null;
		try {
			response = prontuarioService.getAmbiti();
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}
		assertNotNull(response);
		logger.info(response);

	}

	@Test
	public void salvaAmbito() {
		logger.info("START");
		AmbitoVO ambito = new AmbitoVO();
		try {
			ambito.setDenominazione("RICCARDO TEST");
			UserDetails userDetails = mockProfileByToken(TokenMock.DEMO20);
			prontuarioService.salvaAmbito(ambito, userDetails);
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}

	}

	@Test
	public void getAmbitiEliminabili() {
		logger.info("START");
		List<AmbitoVO> response = null;
		try {
			response = prontuarioService.getAmbitiEliminabili();
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}
		assertNotNull(response);
		logger.info(response);
		logger.info("END");
	}

	@Test
	public void eliminaAmbito() {

	}

}
