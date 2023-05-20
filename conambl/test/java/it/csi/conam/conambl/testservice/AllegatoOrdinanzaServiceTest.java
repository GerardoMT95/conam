/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
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
public class AllegatoOrdinanzaServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(AllegatoOrdinanzaServiceTest.class);

	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;

	public static final String PATH_PRINT = "C:\\Users\\riccardo.bova\\Desktop\\bollettini.pdf";

	@Test
	public void creaBollettiniByIdOrdinanza() {
		logger.info("START");
		byte[] response = null;
		Integer idOrdinanza = 1;
		try {
			CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
			allegatoOrdinanzaService.creaBollettiniByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSogList);
//			response = allegatoOrdinanzaService.downloadBollettiniByIdOrdinanza(idOrdinanza);
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
