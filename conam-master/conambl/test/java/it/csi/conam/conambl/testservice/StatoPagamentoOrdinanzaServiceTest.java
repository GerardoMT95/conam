/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
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
public class StatoPagamentoOrdinanzaServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(StatoPagamentoOrdinanzaServiceTest.class);

	@Autowired
	private StatoPagamentoOrdinanzaService statoPagamentoOrdinanzaService;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Test
	public void statoPagamentoOrdinanzaService() {
		logger.info("START");
		Integer idOrdinanzaVerbSog = 100;
		try {
			CnmTUser cnmTUser = cnmTUserRepository.findOne(1L);
			CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbSog);
			statoPagamentoOrdinanzaService.verificaTerminePagamentoOrdinanza(cnmROrdinanzaVerbSog, cnmTUser);
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
