/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.LetteraVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.verbale.RiepilogoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class VerbaleServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(VerbaleServiceTest.class);

	@Autowired
	private VerbaleService verbaleService;

	@Test
	public void saveVerbale() {
		logger.info("START");
		Integer response = null;
		try {
			VerbaleVO verbale = new VerbaleVO();
			verbale.setId(3L);
			ComuneVO comune = new ComuneVO();
			comune.setDenominazione("Alessandria");
			comune.setId(2L);
			verbale.setComune(comune);
			verbale.setDataOraAccertamento(LocalDateTime.now());
			verbale.setDataOraViolazione(LocalDateTime.now());
			EnteVO ente = new EnteVO();
			ente.setId(1L);
			verbale.setEnteAccertatore(ente);
			verbale.setImporto(new Double(2));
			verbale.setIndirizzo("alessandria");
			verbale.setNumero("1213");
			List<RiferimentiNormativiVO> riferimentiNormativiList = new ArrayList<>();
			RiferimentiNormativiVO riferimentiNormativiVO = new RiferimentiNormativiVO();
			LetteraVO lettera = new LetteraVO();
			lettera.setId(1L);
			riferimentiNormativiVO.setLettera(lettera);
			riferimentiNormativiVO.setId(1);
			riferimentiNormativiList.add(riferimentiNormativiVO);
			verbale.setRiferimentiNormativi(riferimentiNormativiList);
			response = verbaleService.salvaVerbale(verbale, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void getVerbaleById() {
		logger.info("START");
		VerbaleVO response = null;
		try {
			response = verbaleService.getVerbaleById(3, mockProfileByToken(TokenMock.DEMO20), true, true, true);
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}
		assertNotNull(response);
	}

	@Test
	public void eliminaVerbale() {
		logger.info("START");
		try {
			verbaleService.eliminaVerbale(3, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}

	}

	@Test
	public void riepilogo() {
		logger.info("START");
		RiepilogoVerbaleVO response = null;
		try {
			response = verbaleService.riepilogo(3, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
		}

		logger.info(response);
		logger.info("END");
	}

	@Autowired
	CnmTVerbaleRepository cnmTVerbaleRepository;

	@Test
	public void query() {
		//List<CnmTVerbale> cnmTVerbale = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO, new PageRequest(0, 1));
		//logger.info(cnmTVerbale);
		logger.info("END");
	}
}
