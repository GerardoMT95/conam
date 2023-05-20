/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.pianorateizzazione;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;

import java.util.List;

public interface PianoRateizzazioneService {

	Integer salvaPiano(PianoRateizzazioneVO piano, UserDetails userDetails);

	PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto, UserDetails userDetails, boolean pregresso);

	PianoRateizzazioneVO ricalcolaRate(PianoRateizzazioneVO piano);

	PianoRateizzazioneVO calcolaRate(PianoRateizzazioneVO piano);

	Integer creaPiano(Integer id, UserDetails userDetails);

	void deletePiano(Integer idPiano);

	PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails);

	RataVO riconcilaRata(RataVO rataVO, UserDetails userDetails);

	ImportoResponse getImportiPianoByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog dto);

}
