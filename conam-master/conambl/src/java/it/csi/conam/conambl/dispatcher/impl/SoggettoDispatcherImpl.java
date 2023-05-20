/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.verbale.SoggettoService;
import it.csi.conam.conambl.dispatcher.SoggettoDispatcher;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SoggettoDispatcherImpl implements SoggettoDispatcher {

	@Autowired
	private SoggettoService soggettoService;

	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	public List<SoggettoVO> ricercaSoggetti(MinSoggettoVO soggetto, UserDetails userDetails){
		return soggettoService.ricercaSoggetti(soggetto, userDetails);
	}
	
	public List<SoggettoVO> ricercaSoggetti(SoggettoRequest soggetto, UserDetails userDetails){
		return ricercaSoggetti(new MinSoggettoVO(soggetto), userDetails);
	}
	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/
	
	@Override
	public SoggettoVO getSoggettoById(Integer id, UserDetails userDetails) {
		return soggettoService.getSoggettoById(id, userDetails, true);
	}
	
	@Override
	public SoggettoPregressiVO getSoggettoPregressiById(Integer id, UserDetails userDetails) {
		return soggettoService.getSoggettoPregressiById(id, userDetails, true);
	}

	@Override
	public SoggettoVO getSoggettoByIdAndIdVerbale(Integer id, Integer idVerbale, UserDetails userDetails) {
		return soggettoService.getSoggettoByIdAndIdVerbale(id, idVerbale, userDetails, true);
	}

}
