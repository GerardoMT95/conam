/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;

import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.utils.DateFormat;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ActaManagementServiceMockImpl extends ActaManagementServiceImpl {

	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException {
		final String method = "archiviaDocumentoFisico";
		log.debug(method + ". BEGIN");

		try {

			log.info(method + ". MOCK CLASS");


			KeyDocumentoActa keyDocumentoActa = new KeyDocumentoActa(documentoActa.getIdDocumento());

			keyDocumentoActa.setNumeroProtocollo("mock-" + RandomStringUtils.randomNumeric(7)+"/"  + DateFormat.getCurrentYear());

			keyDocumentoActa.setUUIDDocumento("mock-"+ RandomStringUtils.randomAlphabetic(31));





			return keyDocumentoActa; 
		}
		finally{
			log.debug(method + ". END");
		}
	}





}
