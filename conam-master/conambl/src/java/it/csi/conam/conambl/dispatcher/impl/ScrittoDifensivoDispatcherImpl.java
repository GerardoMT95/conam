/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.scrittodifensivo.RicercaScrittoDifensivoService;
import it.csi.conam.conambl.business.service.scrittodifensivo.ScrittoDifensivoService;
import it.csi.conam.conambl.dispatcher.ScrittoDifensivoDispatcher;
import it.csi.conam.conambl.request.scrittodifensivo.RicercaScrittoDifensivoRequest;
import it.csi.conam.conambl.response.SalvaScrittoDifensivoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScrittoDifensivoDispatcherImpl implements ScrittoDifensivoDispatcher {

	@Autowired
	private ScrittoDifensivoService scrittoDifensivoService;
	@Autowired
	private RicercaScrittoDifensivoService ricercaScrittoDifensivoService;


	@Override
	public SalvaScrittoDifensivoResponse salvaScrittoDifensivo(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return scrittoDifensivoService.salvaScrittoDifensivo(data, file, userDetails);
	}
	
	
	@Override
	public ScrittoDifensivoVO dettaglioScrittoDifensivo(Integer idScrittoDifensivo, UserDetails userDetails) {
		return scrittoDifensivoService.dettaglioScrittoDifensivo(idScrittoDifensivo, userDetails);
	}
	

	@Override
	public List<ScrittoDifensivoVO> ricercaScrittoDifensivo(RicercaScrittoDifensivoRequest request) {
		return ricercaScrittoDifensivoService.ricercaScrittoDifensivo(request);
	}

	@Override
	public void associaScrittoDifensivo(Integer idScrittoDifensivo, Integer idVerbale, UserDetails userDetails) {
		scrittoDifensivoService.associaScrittoDifensivoById(idScrittoDifensivo, idVerbale, userDetails);
	}


	
}
