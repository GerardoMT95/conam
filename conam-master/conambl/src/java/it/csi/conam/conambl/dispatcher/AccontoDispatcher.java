/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.ordinanza.SalvaAccontoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AccontoDispatcher {

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	AccontoVO salvaAcconto(SalvaAccontoRequest request,UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	AccontoVO salvaAcconto(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	
}	
