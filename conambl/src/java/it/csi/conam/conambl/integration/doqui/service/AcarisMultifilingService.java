/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;

// 20200728_LC
public interface AcarisMultifilingService 
{	
	
	public void init();
	
	public ObjectIdType aggiungiClassificazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType sourceClassificazioneId, ObjectIdType destinationFolderId, boolean isOfflineRequest) throws IntegrationException;

}
