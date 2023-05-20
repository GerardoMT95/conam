/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author riccardo.bova
 * @date 24 ott 2018
 */
@RunWith(RestJunitClassRunner.class)
public class StadocServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(StadocServiceTest.class);

	@Autowired
	private StadocServiceFacade stadocServiceFacade;

	@Test
	public void protocollaDocumento() {
		logger.info("START");
		ResponseProtocollaDocumento doc = null;
		try {
			String nomeFile = "allegato.pdf";

			byte[] document = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(nomeFile));
			String folder = "REN/2019 - JUNIT-10 - ";
			boolean isMaster = true;
			String idEntitaFruitore = "TEST DOC";
			boolean isProtocollazioneInUscita = true;

			String soggettoActa = "CICILLO CACACE";

			String rootActa = "REN-2019";

			doc = stadocServiceFacade.protocollaDocumentoFisico(folder, document, nomeFile, idEntitaFruitore, isMaster, isProtocollazioneInUscita, soggettoActa, rootActa,
					TipoAllegato.VERBALE_AUDIZIONE.getId(), null, null, null);

			assertNotNull(doc);

			if (doc != null) {
				logger.info("PROTOCOLLAZIONE documento " + doc.getProtocollo());
			}

		} catch (Exception e) {
			logger.info("Eccezione gestita", e);
			fail();
		} finally {
			logger.info("END");
		}

	}

}
