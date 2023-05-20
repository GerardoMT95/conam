/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.pianorateizzazione;

import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;

public class SalvaPianoPregressiRequest extends SalvaAllegatiProtocollatiRequest{
	
	private static final long serialVersionUID = 1L;
	
	private PianoRateizzazioneVO piano;
	
	public PianoRateizzazioneVO getPiano() {
		return piano;
	}
	public void setPiano(PianoRateizzazioneVO piano) {
		this.piano = piano;
	}

}
