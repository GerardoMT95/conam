/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.pianorateizzazione;

import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

import java.util.List;

public interface RicercaPianoRateizzazioneService {

	List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails, boolean pregressi);

	List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request, UserDetails userDetails);

	List<StatoPianoVO> getStatiPiano(Boolean isRiconciliaPiano);

}
