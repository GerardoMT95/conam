/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.response.StatiOrdinanzaResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

import java.time.LocalDate;
import java.util.List;

public interface RicercaOrdinanzaService {

	List<MinOrdinanzaVO> ricercaOrdinanza(RicercaOrdinanzaRequest request, UserDetails userDetails);

	List<SoggettoVO> ricercaSoggetti(RicercaSoggettiOrdinanzaRequest ricercaSoggettiOrdinanzaRequest, boolean pregressi);

	List<StatoOrdinanzaVO> getStatiOrdinanza();

	List<SoggettoVO> ricercaSoggetti(RicercaSoggettiOrdinanzaRequest request, StatoOrdinanzaVO statoOrdinanza, LocalDate dataNotificaDa, LocalDate dataNotificaA, String tipoRicerca, boolean pregresso, String numProtocolloPiano, List<StatoPianoVO> statoPiano);

	StatiOrdinanzaResponse getStatiOrdinanzaPregressi(Integer idOrdinanza);
}
