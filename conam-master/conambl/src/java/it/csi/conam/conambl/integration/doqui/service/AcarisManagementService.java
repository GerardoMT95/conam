/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;


public interface AcarisManagementService 
{	
	public  VitalRecordCodeType[] recuperaVitalRecordCode(ObjectIdType repositoryId) throws IntegrationException;
	
	public void init();
	
}
