/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.pianorateizzazione;

import it.csi.conam.conambl.request.pianorateizzazione.SalvaPianoPregressiRequest;
import it.csi.conam.conambl.response.SalvaPianoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;

import java.util.List;

public interface PianoRateizzazionePregressiService {

	SalvaPianoPregressiResponse salvaPiano(SalvaPianoPregressiRequest pianoRequest, UserDetails userDetails);

	List<StatoPianoVO> getStatiPianoRateizzazionePregressi(Integer id);

	List<PianoRateizzazioneVO> getPianiByOrdinanza(Integer id);

	PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails);

}
