/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.dispatcher.AllegatoDispatcher;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Component
public class AllegatoDispatcherImpl implements AllegatoDispatcher {

	@Autowired
	private CommonAllegatoService commonAllegatoService;

	@Override
	public List<ConfigAllegatoVO> getConfigAllegati() {
		return commonAllegatoService.getConfigAllegati();
	}

	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoById(Integer id) {
		return commonAllegatoService.downloadAllegatoById(id);
	}

	@Override
	public List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect) {
		return commonAllegatoService.getDecodificaSelectAllegato(decodificaSelect);
	}
	
	@Override
	public List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idverbale) {
		return commonAllegatoService.getDecodificaSelectSoggettiAllegato(idverbale);
	}

	// 20200903_LC gestione pregresso
	@Override
	public RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(String numProtocollo, Integer idVerbale, Boolean flagPregresso) {
		return commonAllegatoService.ricercaProtocolloSuACTA(numProtocollo, idVerbale, flagPregresso);
	}

	// 20200711_LC
	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento) {
		return commonAllegatoService.downloadAllegatoByObjectIdDocumento(objectIdDocumento);
	}

	// 20200711_LC
	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico) {
		return commonAllegatoService.downloadAllegatoByObjectIdDocumentoFisico(objectIdDocumentoFisico);
	}
	
	
	// 20200827_LC
	@Override
	public List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id) {
		return commonAllegatoService.getDocFisiciByIdAllegato(id);
	}
	
	// 20200827_LC	
	@Override
	public List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento) {
		return commonAllegatoService.getDocFisiciByObjectIdDocumento(objectIdDocumento);
	}
	
	
	
}

