/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.RicercaPianoRateizzazioneService;
import it.csi.conam.conambl.dispatcher.PianoRateizzazioneDispatcher;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
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
public class PianoRateizzazioneDispatcherImpl implements PianoRateizzazioneDispatcher {

	@Autowired
	private RicercaPianoRateizzazioneService ricercaPianoRateizzazioneService;

	@Autowired
	private PianoRateizzazioneService pianoRateizzazioneService;

	@Autowired
	private AllegatoPianoRateizzazioneService allegatoPianoRateizzazioneService;

	@Override
	public List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails) {
		return ricercaPianoRateizzazioneService.ricercaSoggetti(request, userDetails, false);
	}

	@Override
	public List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request, UserDetails userDetails) {
		return ricercaPianoRateizzazioneService.ricercaPiani(request, userDetails);
	}

	@Override
	public List<StatoPianoVO> getStatiPiano(Boolean isRiconciliaPiano) {
		return ricercaPianoRateizzazioneService.getStatiPiano(isRiconciliaPiano);
	}

	@Override
	public PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto, UserDetails userDetails) {
		return pianoRateizzazioneService.precompilaPiano(ordinanzaVerbaleSoggetto, userDetails, false);
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
	public Integer salvaPiano(PianoRateizzazioneVO piano, UserDetails userDetails) {
		return pianoRateizzazioneService.salvaPiano(piano, userDetails);
	}

	@Override
	public PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails) {
		return pianoRateizzazioneService.dettaglioPianoById(idPiano, flagModifica, userDetails);
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

}
