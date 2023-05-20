/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface PianoRateizzazioneDispatcher {

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_PIANO_RATEIZZAZIONE)
	List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<StatoPianoVO> getStatiPiano(Boolean isRiconciliaPiano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	PianoRateizzazioneVO ricalcolaRate(PianoRateizzazioneVO piano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	PianoRateizzazioneVO calcolaRate(PianoRateizzazioneVO piano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	Integer salvaPiano(PianoRateizzazioneVO piano, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_PIANO_RATEIZZAZIONE)
	PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	Integer creaPiano(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	void deletePiano(Integer idPiano);

	// 20200825_LC nuovo type per doc multiplo
	@PreAuthorize(value = AuthorizationRoles.RICERCA_PIANO_RATEIZZAZIONE)
	List<DocumentoScaricatoVO> dowloadBollettiniPiano(Integer idPiano);

	@PreAuthorize(value = AuthorizationRoles.RICONCILIAZIONE_MANUALE_PIANO)
	RataVO riconcilaRata(RataVO rataVO, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	void inviaRichiestaBollettiniByIdPiano(Integer idPiano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PIANO_RATEIZZAZIONE)
	IsCreatedVO isLetteraPianoSaved(Integer idPiano);

}
