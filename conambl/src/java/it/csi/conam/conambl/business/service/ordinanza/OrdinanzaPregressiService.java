/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaPregressiRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaStatoOrdinanzaRequest;
import it.csi.conam.conambl.response.DatiSentenzaPregressiResponse;
import it.csi.conam.conambl.response.RicevutaPagamentoResponse;
import it.csi.conam.conambl.response.SalvaOrdinanzaPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;

import java.util.List;

public interface OrdinanzaPregressiService {

	SalvaOrdinanzaPregressiResponse salvaOrdinanza(SalvaOrdinanzaPregressiRequest request, UserDetails userDetails);
	
	MessageVO salvaStatoOrdinanza(SalvaStatoOrdinanzaRequest salvaStatoOrdinanza, UserDetails userDetails);

	List<DatiSentenzaPregressiResponse> getDatiSentenzaByIdOrdinanza(Integer idOrdinanza);

	List<RicevutaPagamentoResponse> getRicevutePagamentoByIdOrdinanza(Integer idOrdinanza);
}
