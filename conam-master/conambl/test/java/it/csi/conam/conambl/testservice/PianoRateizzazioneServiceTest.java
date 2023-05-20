/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.RicercaPianoRateizzazioneService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class PianoRateizzazioneServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(PianoRateizzazioneServiceTest.class);

	@Autowired
	private PianoRateizzazioneService pianoRateizzazioneService;
	@Autowired
	private RicercaPianoRateizzazioneService ricercaPianoRateizzazioneService;

	@Test
	public void calcolaRata() {
		logger.info("START");
		PianoRateizzazioneVO response = null;
		try {
			PianoRateizzazioneVO piano = new PianoRateizzazioneVO();
			piano.setNumeroRate(new BigDecimal(3));
			piano.setImportoSanzione(new BigDecimal(100.50));
			piano.setImportoSpeseNotifica(new BigDecimal(3));
			piano.setImportoSpeseProcessuali(new BigDecimal(2));
			response = pianoRateizzazioneService.calcolaRate(piano);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BigDecimal totaleRate = BigDecimal.ZERO;
		for (RataVO rata : response.getRate()) {
			totaleRate = totaleRate.add(rata.getImportoRata());
		}
		assertTrue(totaleRate.compareTo(response.getSaldo()) == 0);
	}

	public static Boolean MODIFICA_PRIMA_RATA = Boolean.TRUE;
	public static Boolean MODIFICA_ULTIMA_RATA = Boolean.FALSE;
	public static Boolean MODIFICA_DATA_PRIMA_RATA = Boolean.FALSE;

	@Test
	public void ricalcolaRata() {
		logger.info("START");
		PianoRateizzazioneVO response = null;
		try {
			// response = pianoRateizzazioneService.dettaglioPianoById(9, us);
			response = new PianoRateizzazioneVO();
			response.setNumeroRate(new BigDecimal(12));
			response.setImportoSanzione(new BigDecimal(234));
			response.setImportoSpeseNotifica(new BigDecimal(11));
			response.setImportoSpeseProcessuali(new BigDecimal(123));
			response = pianoRateizzazioneService.calcolaRate(response);
			for (RataVO rata : response.getRate()) {
				if (rata.getNumeroRata().intValue() == 1 && MODIFICA_PRIMA_RATA.booleanValue())
					rata.setImportoRata(new BigDecimal(20));
				if (rata.getNumeroRata().intValue() == response.getNumeroRate().intValue() && MODIFICA_ULTIMA_RATA.booleanValue())
					rata.setImportoRata(new BigDecimal(20));
				if (rata.getNumeroRata().intValue() == 1 && MODIFICA_DATA_PRIMA_RATA.booleanValue())
					rata.setDataScadenza(LocalDate.now());

			}
			response = pianoRateizzazioneService.ricalcolaRate(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BigDecimal totaleRate = BigDecimal.ZERO;
		for (RataVO rata : response.getRate()) {
			totaleRate = totaleRate.add(rata.getImportoRata());
		}

		assertTrue(totaleRate.compareTo(response.getSaldo()) == 0);
	}

	@Test
	public void salvaPiano() {
		logger.info("START");
		Integer id = null;
		try {
			List<Integer> ordinanzaVerbaleSoggetto = new ArrayList<>();
			ordinanzaVerbaleSoggetto.add(new Integer(6));
			UserDetails us = mockProfileByToken(TokenMock.DEMO20);
			/*PianoRateizzazioneVO piano = pianoRateizzazioneService.precompilaPiano(ordinanzaVerbaleSoggetto, us);
			piano.setNumeroRate(new BigDecimal(3));
			piano.setImportoSanzione(new BigDecimal(100.50));
			piano.setImportoSpeseNotifica(new BigDecimal(3));
			piano.setImportoSpeseProcessuali(new BigDecimal(2));
			piano = pianoRateizzazioneService.calcolaRate(piano);
			for (RataVO rata : piano.getRate()) {
				if (rata.getNumeroRata().intValue() == 1 && MODIFICA_PRIMA_RATA.booleanValue())
					rata.setImportoRata(new BigDecimal(20));
				if (rata.getNumeroRata().intValue() == piano.getNumeroRate().intValue() && MODIFICA_ULTIMA_RATA.booleanValue())
					rata.setImportoRata(new BigDecimal(20));
				if (rata.getNumeroRata().intValue() == 1 && MODIFICA_DATA_PRIMA_RATA.booleanValue())
					rata.setDataScadenza(LocalDate.now());

			}
			piano = pianoRateizzazioneService.ricalcolaRate(piano);
			id = pianoRateizzazioneService.salvaPiano(piano, us);*/
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		assertNotNull(id);
	}

	@Test
	public void dettaglioPianoId() {
		logger.info("START");
		PianoRateizzazioneVO piano = null;
		try {
			piano = pianoRateizzazioneService.dettaglioPianoById(19, true, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		assertNotNull(piano);
	}

	@Test
	public void ricercaSoggetti() {
		logger.info("START");
		List<SoggettoVO> response = null;
		RicercaPianoRequest request = new RicercaPianoRequest();
		try {
			request.setNumeroDeterminazione("ORD9385");
			response = ricercaPianoRateizzazioneService.ricercaSoggetti(request, mockProfileByToken(TokenMock.DEMO20), false);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		assertNotNull(response);
	}

	@Test
	public void ricercaPiani() {
		logger.info("START");
		List<MinPianoRateizzazioneVO> response = null;
		RicercaPianoRequest request = new RicercaPianoRequest();
		try {
			StatoPianoVO stato1 = new StatoPianoVO();
			stato1.setId(Constants.ID_STATO_PIANO_IN_DEFINIZIONE);
			StatoPianoVO stato2 = new StatoPianoVO();
			stato2.setId(Constants.ID_STATO_PIANO_NOTIFICATO);
			request.setStatoPiano(Arrays.asList(stato1, stato2));
			response = ricercaPianoRateizzazioneService.ricercaPiani(request, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		assertNotNull(response);
	}

	@Test
	public void deletePiano() {
		logger.info("START");
		try {
			pianoRateizzazioneService.deletePiano(new Integer(6));
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

}
