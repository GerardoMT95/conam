/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;

public interface AzioneOrdinanzaPregressiService {

	AzioneOrdinanzaPregressiResponse azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

	AzioneOrdinanzaPregressiResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

}
