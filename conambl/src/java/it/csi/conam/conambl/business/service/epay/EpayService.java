/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.epay;

import it.csi.conam.conambl.integration.epay.from.EsitoAggiornaPosizioniDebitorieRequest;
import it.csi.conam.conambl.integration.epay.from.EsitoInserimentoListaDiCaricoRequest;
import it.csi.conam.conambl.integration.epay.from.ResponseType;
import it.csi.conam.conambl.integration.epay.from.TrasmettiNotifichePagamentoRequest;

/**
 * @author riccardo.bova
 * @date 01 feb 2019
 */
public interface EpayService {

	ResponseType esitoInserimentoListaDiCarico(EsitoInserimentoListaDiCaricoRequest parameters);

	ResponseType trasmettiNotifichePagamento(TrasmettiNotifichePagamentoRequest parameters);

	ResponseType esitoAggiornaPosizioniDebitorie(EsitoAggiornaPosizioniDebitorieRequest parameters);

}
