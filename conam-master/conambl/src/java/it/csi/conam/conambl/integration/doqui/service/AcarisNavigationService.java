/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;


public interface AcarisNavigationService 
{	
	
	public ObjectIdType recuperaFascicoloProtocollazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType idFascicolo) throws IntegrationException;
	public void init();
	public ObjectResponseType recuperaChildren(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, boolean isGruppoAllegati) throws IntegrationException;
	public PagingResponseType recuperaDescendants(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, Integer depth) throws IntegrationException;			// 20200707_LC
	public PagingResponseType recuperaAllChildrens(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId) throws IntegrationException;							// 20200708_LC
}
