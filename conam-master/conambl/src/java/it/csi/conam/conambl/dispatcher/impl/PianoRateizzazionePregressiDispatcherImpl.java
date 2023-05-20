/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazionePregressiService;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.RicercaPianoRateizzazioneService;
import it.csi.conam.conambl.dispatcher.PianoRateizzazionePregressiDispatcher;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.request.pianorateizzazione.SalvaPianoPregressiRequest;
import it.csi.conam.conambl.response.SalvaPianoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */

@Component
public class PianoRateizzazionePregressiDispatcherImpl implements PianoRateizzazionePregressiDispatcher {

	@Autowired
	private RicercaPianoRateizzazioneService ricercaPianoRateizzazioneService;

	@Autowired
	private PianoRateizzazioneService pianoRateizzazioneService;

	@Autowired
	private PianoRateizzazionePregressiService pianoRateizzazionePregressiService;
	
	@Autowired
	private AllegatoPianoRateizzazioneService allegatoPianoRateizzazioneService;

	@Override
	public List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails) {
		return ricercaPianoRateizzazioneService.ricercaSoggetti(request, userDetails, true);
	}

	@Override
	public List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request, UserDetails userDetails) {
		return ricercaPianoRateizzazioneService.ricercaPiani(request, userDetails);
	}

	@Override
	public List<StatoPianoVO> getStatiPiano(Integer id) {
		return pianoRateizzazionePregressiService.getStatiPianoRateizzazionePregressi(id);
	}

	@Override
	public PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto, UserDetails userDetails) {
		return pianoRateizzazioneService.precompilaPiano(ordinanzaVerbaleSoggetto, userDetails, true);
	}

	@Override
	public PianoRateizzazioneVO ricalcolaRate(PianoRateizzazioneVO piano) {
		return pianoRateizzazioneService.ricalcolaRate(piano);
	}

	@Override
	public PianoRateizzazioneVO calcolaRate(PianoRateizzazioneVO piano) {
		return pianoRateizzazioneService.calcolaRate(piano);
	}

	@Override
	public SalvaPianoPregressiResponse salvaPiano(SalvaPianoPregressiRequest pianoRequest, UserDetails userDetails) {
		return pianoRateizzazionePregressiService.salvaPiano(pianoRequest, userDetails);
	}

	@Override
	public PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails) {
		return pianoRateizzazionePregressiService.dettaglioPianoById(idPiano, flagModifica, userDetails);
	}

	@Override
	public Integer creaPiano(Integer id, UserDetails userDetails) {
		return pianoRateizzazioneService.creaPiano(id, userDetails);
	}

	@Override
	public void deletePiano(Integer idPiano) {
		pianoRateizzazioneService.deletePiano(idPiano);
	}

	// 20200825_LC nuovo type per doc multiplo
	@Override
	public List<DocumentoScaricatoVO> dowloadBollettiniPiano(Integer idPiano) {
		return allegatoPianoRateizzazioneService.downloadBollettiniByIdPiano(idPiano);
	}

	@Override
	public RataVO riconcilaRata(RataVO rataVO, UserDetails userDetails) {
		return pianoRateizzazioneService.riconcilaRata(rataVO, userDetails);
	}

	@Override
	public void inviaRichiestaBollettiniByIdPiano(Integer idPiano) {
		allegatoPianoRateizzazioneService.inviaRichiestaBollettiniByIdPiano(idPiano);
	}

	@Override
	public IsCreatedVO isLetteraPianoSaved(Integer idPiano) {
		return allegatoPianoRateizzazioneService.isLetteraPianoSaved(idPiano);
	}

	@Override
	public List<PianoRateizzazioneVO> getPianiByOrdinanza(Integer id) {
		return pianoRateizzazionePregressiService.getPianiByOrdinanza(id);
	}

}
