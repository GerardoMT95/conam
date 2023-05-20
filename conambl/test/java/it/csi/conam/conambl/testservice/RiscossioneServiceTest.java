/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.riscossione.RiscossioneService;
import it.csi.conam.conambl.business.service.riscossione.SorisService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.integration.entity.CnmTFile;
import it.csi.conam.conambl.integration.repositories.CnmDStatoFileRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoRiscossioneRepository;
import it.csi.conam.conambl.integration.repositories.CnmTFileRepository;
import it.csi.conam.conambl.integration.repositories.CnmTRiscossioneRepository;
import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(RestJunitClassRunner.class)
public class RiscossioneServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(RiscossioneServiceTest.class);

	@Autowired
	private RiscossioneService riscossioneService;
	@Autowired
	private SorisService sorisService;

	@Autowired
	private CnmTRiscossioneRepository cnmTRiscossioneRepository;
	@Autowired
	private CnmTFileRepository cnmTFileRepository;
	@Autowired
	private CnmDStatoRiscossioneRepository cnmDStatoRiscossioneRepository;
	@Autowired
	private CnmDStatoFileRepository cnmDStatoFileRepository;

	@Test
	public void testRiscossioneService() {

		logger.info("START");

		RicercaSoggettiRiscossioneCoattivaRequest request = new RicercaSoggettiRiscossioneCoattivaRequest();
		StatoOrdinanzaVO so = new StatoOrdinanzaVO();
		so.setDenominazione("Notificata");
		so.setId(2l);
		request.setStatoOrdinanza(so);

		List<SoggettiRiscossioneCoattivaVO> response = riscossioneService.ricercaOrdinanzaSoggetti(request);
		logger.info(response);

		logger.info("END");

	}

	@Test
	public void testCreaTracciato() {

		logger.info("START");

		List<CnmTFile> cnmTFileList = cnmTFileRepository.findByCnmDStatoFile(cnmDStatoFileRepository.findOne(Constants.ID_STATO_FILE_INIZIALIZZATO));
		CnmTFile cnmTFile = null;

		if (cnmTFileList != null && cnmTFileList.size() > 0) {
			cnmTFile = cnmTFileList.get(0);
		}
		try {
			InputStream is = sorisService.creaTracciato(cnmTFile);
			File f = new File("C:\\temp\\tracciato.txt");
			FileUtils.copyInputStreamToFile(is, f);
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			logger.info("END");
		}

	}

	@Test
	public void testGetSoggettiRiscossione() {
		logger.info("START");
		try {
			List<SoggettiRiscossioneCoattivaVO> response = riscossioneService.getSoggettiRiscossione(false, null);
			logger.info(response);
		} catch (Exception e) {
			logger.error("error", e);
		} finally {
			logger.info("END");
		}
	}

	@Test
	public void testAggiungiInListaRiscossione() {

		logger.info("START");

		List<Integer> request = new ArrayList<>();
		request.add(102);

		UserDetails userDetails = mockProfileByToken(TokenMock.DEMO20);
		try {
			List<SoggettiRiscossioneCoattivaVO> response = riscossioneService.aggiungiInListaRiscossione(request, userDetails);
			logger.info(response);
		} catch (Exception e) {
			logger.error("error", e);
		} finally {
			logger.info("END");
		}
	}

}
