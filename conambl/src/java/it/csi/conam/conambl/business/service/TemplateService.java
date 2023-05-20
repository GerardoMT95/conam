/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.request.template.DatiTemplateRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
public interface TemplateService {

	DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails );

	byte[] stampaTemplate(DatiTemplateRequest request, UserDetails user);

	// 20200824_LC
	List<DocumentoScaricatoVO> downloadTemplate(DatiTemplateRequest request, UserDetails userDetails);

	DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails);

	MessageVO getMessaggioByCodice(String codice);

}
