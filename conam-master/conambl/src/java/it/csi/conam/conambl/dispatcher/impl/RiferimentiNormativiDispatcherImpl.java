/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.dispatcher.RiferimentiNormativiDispatcher;
import it.csi.conam.conambl.vo.leggi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
@Component
public class RiferimentiNormativiDispatcherImpl implements RiferimentiNormativiDispatcher {

	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;

	@Override
	public List<AmbitoVO> getAmbitiByIdEnte(Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		return riferimentiNormativiService.getAmbitiByIdEnte(idEnte, filterDataValidita, filterPregresso, dataAccertamento);
	}

	@Override
	public List<NormaVO> getNormeByIdAmbitoAndIdEnte(Integer idAmbito, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		return riferimentiNormativiService.getNormeByIdAmbitoAndIdEnte(idAmbito, idEnte, filterDataValidita, filterPregresso, dataAccertamento);
	}

	@Override
	public List<ArticoloVO> getArticoliByIdNormaAndIdEnte(Long idNorma, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		return riferimentiNormativiService.getArticoliByIdNormaAndEnte(idNorma, idEnte, filterDataValidita, filterPregresso, dataAccertamento);
	}

	@Override
	public List<LetteraVO> letteraByIdComma(Long idComma, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		return riferimentiNormativiService.getLetteraByIdComma(idComma, filterDataValidita, filterPregresso, dataAccertamento);
	}

	@Override
	public List<CommaVO> commaByIdArticolo(Long idArticolo, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		return riferimentiNormativiService.getCommaByIdArticolo(idArticolo, filterDataValidita, filterPregresso, dataAccertamento);
	}

}
