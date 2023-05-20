/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTRata;
import it.csi.conam.conambl.integration.repositories.CnmRSoggRataRepository;
import it.csi.conam.conambl.integration.repositories.CnmTPianoRateRepository;
import it.csi.conam.conambl.integration.repositories.CnmTRataRepository;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class AllegatoPianoRateizzazioneServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(AllegatoPianoRateizzazioneServiceTest.class);

	@Autowired
	private AllegatoPianoRateizzazioneService documentoPianoRateizzazioneService;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;

	public static final String PATH_PRINT = "C:\\Users\\riccardo.bova\\Desktop\\bollettini.pdf";

	@Test
	public void creaBollettiniByIdPiano() {
		logger.info("START");
		byte[] response = null;
		Integer idPiano = 19;
		try {
			CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(idPiano);
			List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);
			List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
			documentoPianoRateizzazioneService.creaBollettiniByCnmRSoggRata(cnmRSoggRataList);
//			response = documentoPianoRateizzazioneService.downloadBollettiniByIdPiano(idPiano);

		} catch (Exception e) {
			logger.error(e);
		}

		try {
			FileUtils.writeByteArrayToFile(new File(PATH_PRINT), response);
		} catch (IOException e) {
			logger.error("ERRORE IN STAMPA FILE", e);
		}
	}

}
