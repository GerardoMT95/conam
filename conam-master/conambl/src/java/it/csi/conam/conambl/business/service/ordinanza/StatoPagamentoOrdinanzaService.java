/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.integration.entity.*;

/**
 * @author riccardo.bova
 * @date 25 mar 2019
 */
public interface StatoPagamentoOrdinanzaService {

	void verificaTerminePagamentoRate(CnmRSoggRata cnmRSoggRata, CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog, CnmTUser cnmTUser);

	void verificaTerminePagamentoOrdinanza(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmTUser cnmTUser);

	void verificaTerminePagamentoSollecito(
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog,
		CnmTUser cnmTUser,
		CnmTSollecito cnmTSollecito
	);

}
