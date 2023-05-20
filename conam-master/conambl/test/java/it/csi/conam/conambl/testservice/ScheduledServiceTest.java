/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.scheduled.OrdinanzaScheduledService;
import it.csi.conam.conambl.scheduled.SorisScheduledService;
import it.csi.conam.conambl.scheduled.VerbaleScheduledService;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class ScheduledServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(ScheduledServiceTest.class);

	@Autowired
	private OrdinanzaScheduledService ordinanzaScheduledService;
	@Autowired
	private VerbaleScheduledService verbaleScheduledService;
	@Autowired
	private SorisScheduledService sorisScheduledService;

	@Test
	public void ordinanzaScheduledServiceSendAllegatiToActa() {
		logger.info("START");
		try {
			ordinanzaScheduledService.sendAllegatiInCodaToActa();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		logger.info("END");
	}

	@Test
	public void verbaleScheduledServiceSendAllegatiToActa() {
		logger.info("START");
		try {
			verbaleScheduledService.sendAllegatiInCodaToActa();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		logger.info("END");
	}

	@Test
	public void sorisScheduledServiceSpostaDocumentoDaConam() {
		logger.info("START");
		try {
			sorisScheduledService.elaboraStatoDellaRiscossione();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		logger.info("END");
	}

}
