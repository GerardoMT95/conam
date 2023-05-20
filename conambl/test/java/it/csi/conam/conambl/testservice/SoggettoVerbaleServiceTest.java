/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.integration.mapper.entity.RuoloSoggettoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDRuoloSoggettoRepository;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
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
public class SoggettoVerbaleServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(SoggettoVerbaleServiceTest.class);

	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;

	
	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;
	@Autowired
	private CnmDRuoloSoggettoRepository cnmDRuoloSoggettoRepository;

//	@Test
	public void getSoggettiByIdVerbale() {
		logger.info("START");
		List<SoggettoVO> response = null;
		try {
			response = soggettoVerbaleService.getSoggettiByIdVerbale(9, mockProfileByToken(TokenMock.DEMO20), true);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

//	@Test
	public void salvaSoggetto() {
		logger.info("START");
		SoggettoVO response = null;
		try {
			SoggettoVO soggetto = new SoggettoVO();
			soggetto.setCodiceFiscale("GLOPLA75E21L219X");
			// soggetto.setCodiceFiscale("BVORCR93E08A182G");
			soggetto.setPersonaFisica(true);
			soggetto = soggettoVerbaleService.ricercaSoggetto(soggetto, mockProfileByToken(TokenMock.DEMO20));
			soggetto.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(cnmDRuoloSoggettoRepository.findOne(1L)));
			ComuneVO comuneNascita = new ComuneVO();
			comuneNascita.setId(1L);
			soggetto.setComuneNascita(comuneNascita);
			soggetto.setNazioneNascitaEstera(false);
			response = soggettoVerbaleService.salvaSoggetto(9, soggetto, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void printSoggettoPregressoVO() {
		logger.info("START");
		SoggettoVO response = null;
		try {
			SoggettoVO soggetto = new SoggettoVO();
			soggetto.setCodiceFiscale("GLOPLA75E21L219X");
			// soggetto.setCodiceFiscale("BVORCR93E08A182G");
			soggetto.setPersonaFisica(true);
			SoggettoPregressiVO soggettoPregressi = soggettoVerbaleService.ricercaSoggettoPregressi(soggetto, mockProfileByToken(TokenMock.DEMO20));
			soggettoPregressi.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(cnmDRuoloSoggettoRepository.findOne(1L)));
			ComuneVO comuneNascita = new ComuneVO();
			comuneNascita.setId(1L);
			soggettoPregressi.setComuneNascita(comuneNascita);
			soggettoPregressi.setNazioneNascitaEstera(false);

			org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
			mapper.writeValueAsString(soggettoPregressi);
			
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

//	@Test
	public void eliminaSoggettoByIdVerbaleSoggetto() {
		logger.info("START");
		try {
			soggettoVerbaleService.eliminaSoggettoByIdVerbaleSoggetto(18, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}

	}

//	@Test
	public void ricercaSoggetto() {
		logger.info("START");
		SoggettoVO response = null;
		try {
			MinSoggettoVO soggetto = new SoggettoVO();
			// RICERCA COD FISCALE FISICA
			soggetto.setCodiceFiscale("GLOPLA75E21L219X");
			soggetto.setPersonaFisica(true);

			// RICERCA AZIENDA
			soggetto.setCodiceFiscale("09220780010");
			soggetto.setPersonaFisica(false);

			// RICERCA CON MODULO
			soggetto.setCodiceFiscale("BVORCR93E08A182G");
			soggetto.setPersonaFisica(true);

			response = soggettoVerbaleService.ricercaSoggetto(soggetto, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

}
