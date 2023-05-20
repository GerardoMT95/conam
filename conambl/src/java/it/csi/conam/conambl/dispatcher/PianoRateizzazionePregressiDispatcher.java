/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
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
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface PianoRateizzazionePregressiDispatcher {

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<StatoPianoVO> getStatiPiano(Integer id);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	PianoRateizzazioneVO ricalcolaRate(PianoRateizzazioneVO piano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	PianoRateizzazioneVO calcolaRate(PianoRateizzazioneVO piano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SalvaPianoPregressiResponse salvaPiano(SalvaPianoPregressiRequest pianoRequest, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	Integer creaPiano(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void deletePiano(Integer idPiano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<DocumentoScaricatoVO> dowloadBollettiniPiano(Integer idPiano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	RataVO riconcilaRata(RataVO rataVO, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void inviaRichiestaBollettiniByIdPiano(Integer idPiano);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	IsCreatedVO isLetteraPianoSaved(Integer idPiano);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<PianoRateizzazioneVO> getPianiByOrdinanza(Integer id);

}
