/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class CommonAllegatoServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(CommonAllegatoServiceTest.class);

	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
//	private UtilsStadoc utilsStadoc;

	public static final String PATH_PRINT = "C:\\Users\\riccardo.bova\\Desktop\\protocollo_test.pdf";

	@Test
	public void downloadAllegatoById() {
		logger.info("START");
		byte[] response = null;
		try {
//			response = commonAllegatoService.downloadAllegatoById(371);
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			FileUtils.writeByteArrayToFile(new File(PATH_PRINT), response);
		} catch (IOException e) {
			logger.error("ERRORE IN STAMPA FILE", e);
		}
		logger.info("END");
	}

//	@Test
//	public void getMymeType() {
//		logger.info("START");
//		try {
//			String mimeType = utilsStadoc.getMimeType(PATH_PRINT);
//
//			assertEquals(mimeType, StadocMimeType.APPLICATION_PDF.value());
//
//		} catch (Exception e) {
//			logger.error(e);
//		}
//	}

}
