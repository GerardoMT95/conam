/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.TemplateService;
import it.csi.conam.conambl.dispatcher.TemplateDispatcher;
import it.csi.conam.conambl.request.template.DatiTemplateRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
@Component
public class TemplateDispatcherImpl implements TemplateDispatcher {

	@Autowired
	private TemplateService templateService;

	@Override
	public DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails ) {
		return templateService.getDatiTemplate(request, userDetails);
	}

	@Override
	public byte[] stampaTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		return templateService.stampaTemplate(request, userDetails);
	}

	// 20200825_LC nuovo type per doc multiplo
	public List<DocumentoScaricatoVO> downloadTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		return templateService.downloadTemplate(request, userDetails);
	}

	public DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		return templateService.nomiTemplate(request, userDetails);
	}

	@Override
	public MessageVO getMessaggioByCodice(String codice) {
		return templateService.getMessaggioByCodice(codice);
	}
}
