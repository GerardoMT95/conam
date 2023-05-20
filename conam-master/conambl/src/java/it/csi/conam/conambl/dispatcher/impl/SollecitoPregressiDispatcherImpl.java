/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoPregressiService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoService;
import it.csi.conam.conambl.dispatcher.SollecitoPregressiDispatcher;
import it.csi.conam.conambl.request.riscossione.SalvaSollecitoPregressiRequest;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.response.SalvaSollecitoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SollecitoPregressiDispatcherImpl implements SollecitoPregressiDispatcher {
	
	@Autowired
	private SollecitoService sollecitoService;
	
	@Autowired
	private AllegatoSollecitoService allegatoSollecitoService;
	
	@Autowired
	private SollecitoPregressiService sollecitoPregressiService;
	
	// modificata
	@Override
	public SollecitoVO getSollecitoById(Integer id) {
		return sollecitoPregressiService.getSollecitoById(id);
	}
	
	// modificata
	@Override
	public SalvaSollecitoPregressiResponse salvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails user) {
		return sollecitoPregressiService.salvaSollecito(salvaSollecitoPregressiRequest, user);
	}
	
	// modificata
	@Override
	public List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		return sollecitoPregressiService.getSollecitiByIdOrdinanzaSoggetto(idOrdinanzaVerbaleSoggetto);
	}
	
	
	// nuova
	@Override
	public List<StatoSollecitoVO> getStatiSollecito(Integer id) {
		return sollecitoPregressiService.getStatiSollecitoPregressi(id);
	}

	// nuova
	@Override
	public List<SollecitoVO> getSollecitiByOrdinanza(Integer id) {
		return sollecitoPregressiService.getSollecitiByOrdinanza(id);
	}

	
	// nuova
	@Override
	public SalvaSollecitoPregressiResponse checkSalvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails user) {
		return sollecitoPregressiService.checkSalvaSollecito(salvaSollecitoPregressiRequest, user);
	}
	
	
	
	
	// as standard
	@Override
	public void eliminaSollecito(Integer idSollecito) {
		sollecitoService.eliminaSollecito(idSollecito);
	}

	@Override
	public void inviaRichiestaBollettiniByIdSollecito(Integer idSollecito) {
		allegatoSollecitoService.inviaRichiestaBollettiniByIdSollecito(idSollecito);

	}

	// 20200825_LC nuovo type per doc multiplo
	@Override
	public List<DocumentoScaricatoVO> dowloadBollettiniSollecito(Integer idSollecito) {
		return allegatoSollecitoService.dowloadBollettiniSollecito(idSollecito);
	}

	// 20200825_LC nuovo type per doc multiplo
	@Override
	public List<DocumentoScaricatoVO> dowloadLettera(Integer idSollecito) {
		return allegatoSollecitoService.downloadLetteraSollecito(idSollecito);
	}


	@Override
	public RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails user) {
		return sollecitoService.riconcilaSollecito(sollecito, user);
	}

	@Override
	public IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito) {
		return allegatoSollecitoService.isLetteraSollecitoSaved(idSollecito);
	}




}
