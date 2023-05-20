/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testservice;

import it.csi.conam.conambl.business.service.TemplateService;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.common.security.TokenMock;
import it.csi.conam.conambl.request.template.DatiTemplateRequest;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import it.csi.conam.conambl.vo.template.DatiTemplateCompilatiVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * @author riccardo.bova
 * @date 27 ott 2017
 */

@RunWith(RestJunitClassRunner.class)
public class TemplateServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(TemplateServiceTest.class);

	@Autowired
	private TemplateService templateService;

	public static final String PATH_PRINT = "C:\\Users\\matteo.coscia\\Desktop\\TEST.pdf";

	@Test
	public void getDatiTemplate() {
		
		logger.info("START");
		DatiTemplateVO response = null;
		try {
			mockProfileByToken(TokenMock.DEMO20);
			DatiTemplateRequest request = new DatiTemplateRequest();
			request.setIdPiano(16);
			request.setCodiceTemplate(Report.REPORT_LETTERA_RATEIZZAZIONE.getCodiceFrontend());
			response = templateService.getDatiTemplate(request, null);
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

//	@Test
	public void stampaTemplate() {
		logger.info("START");
		byte[] response = null;
		try {
			DatiTemplateRequest request = initRequestAccompagnamento(12);
			// DatiTemplateRequest request = initRequestSollecito(4);
			// DatiTemplateRequest request = initRequestPiano(5);
			response = templateService.stampaTemplate(request, mockProfileByToken(TokenMock.DEMO20));
		} catch (Exception e) {
			logger.error("Eccezione", e);
		}

		try {
			FileUtils.writeByteArrayToFile(new File(PATH_PRINT), response);
		} catch (IOException e) {
			logger.error("ERRORE IN STAMPA FILE", e);
		}
		assertNotNull(response);
		logger.info(response);
	}

	private DatiTemplateRequest initRequestAccompagnamento(Integer idOrdinanza) {
		DatiTemplateRequest request = new DatiTemplateRequest();
		request.setIdOrdinanza(idOrdinanza);
		//request.setCodiceTemplate(Report.REPORT_LETTERA_ACCOMPAGNAMENTO.getCodiceFrontend());
		DatiTemplateCompilatiVO datiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
		datiTemplateCompilatiVO.setOggetto("adsadsadsadsa");
		datiTemplateCompilatiVO.setDescrizione("descrizione....");
		request.setDatiTemplateCompilatiVO(datiTemplateCompilatiVO);
		return request;
	}

	private DatiTemplateRequest initRequestPiano(Integer idPiano) {
		DatiTemplateRequest request = new DatiTemplateRequest();
		request.setIdPiano(idPiano);
		request.setCodiceTemplate(Report.REPORT_LETTERA_RATEIZZAZIONE.getCodiceFrontend());
		DatiTemplateCompilatiVO datiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
		datiTemplateCompilatiVO.setOggetto("adsadsadsadsa");
		datiTemplateCompilatiVO.setDescrizione("descrizione....");
		request.setDatiTemplateCompilatiVO(datiTemplateCompilatiVO);
		return request;
	}

	private DatiTemplateRequest initRequestSollecito(Integer idSollecito) {
		DatiTemplateRequest request = new DatiTemplateRequest();
		request.setIdSollecito(idSollecito);
		request.setCodiceTemplate(Report.REPORT_LETTERA_SOLLECITO.getCodiceFrontend());
		DatiTemplateCompilatiVO datiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
		datiTemplateCompilatiVO.setOggetto("adsadsadsadsa");
		datiTemplateCompilatiVO.setDescrizione("descrizione....");
		request.setDatiTemplateCompilatiVO(datiTemplateCompilatiVO);
		return request;
	}

}
