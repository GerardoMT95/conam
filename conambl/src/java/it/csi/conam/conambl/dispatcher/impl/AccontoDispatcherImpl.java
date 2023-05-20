/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.ordinanza.AccontoService;
import it.csi.conam.conambl.dispatcher.AccontoDispatcher;
import it.csi.conam.conambl.request.ordinanza.SalvaAccontoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccontoDispatcherImpl implements AccontoDispatcher {

	@Autowired
	private AccontoService accontoService;

	@Override
	public AccontoVO salvaAcconto(SalvaAccontoRequest request,UserDetails userDetails) {
		return accontoService.salvaAcconto(request, userDetails);
	}
	
	
	@Override
	public AccontoVO salvaAcconto(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return accontoService.salvaAcconto(data, file, userDetails);
	}
	
	
}
