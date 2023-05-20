/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoService;
import it.csi.conam.conambl.dispatcher.SollecitoDispatcher;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SollecitoDispatcherImpl implements SollecitoDispatcher {

	@Autowired
	private SollecitoService sollecitoService;

	@Autowired
	private AllegatoSollecitoService allegatoSollecitoService;

	@Override
	public List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		return sollecitoService.getSollecitiByIdOrdinanzaSoggetto(idOrdinanzaVerbaleSoggetto);
	}

	@Override
	public Integer salvaSollecito(SollecitoVO sollecitoVO, NotificaVO notificaVO, UserDetails user) {
		return sollecitoService.salvaSollecito(sollecitoVO, notificaVO, user);
	}

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
	public SollecitoVO getSollecitoById(Integer id) {
		return sollecitoService.getSollecitoById(id);
	}

	@Override
	public RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails user) {
		return sollecitoService.riconcilaSollecito(sollecito, user);
	}

	@Override
	public IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito) {
		return allegatoSollecitoService.isLetteraSollecitoSaved(idSollecito);
	}

	@Override
	public Integer salvaSollecitoRate(SollecitoVO sollecitoVO, NotificaVO notificaVO, Integer idPianoRateizzazione, UserDetails user) {
		return sollecitoService.salvaSollecitoRate(sollecitoVO, notificaVO, idPianoRateizzazione, user);
	}
	
	@Override
	public List<SollecitoVO> getSollecitiByIdPianoRateizzazione(Integer idPianoRateizzazione) {
		return sollecitoService.getSollecitiByIdPianoRateizzazione(idPianoRateizzazione);
	}
	
}
