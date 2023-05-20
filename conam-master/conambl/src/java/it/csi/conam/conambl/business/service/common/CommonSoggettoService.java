/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.common;

import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

public interface CommonSoggettoService {

	SoggettoVO getSoggettoFromStas(MinSoggettoVO minSoggettoVO, String identita);

	CnmTSoggetto updateSoggettoDBWithIdStas(CnmTSoggetto cnmTSoggetto, CnmTUser cnmTUser, SoggettoVO sogDb, SoggettoVO sogStas, SoggettoVO soggetto);

	CnmTSoggetto getSoggettoFromDb(MinSoggettoVO minSoggettoVO, boolean isRicerca);

	// 20201217_LC - JIRA 118
	SoggettoVO attachResidenzaPregressi(SoggettoVO sog, CnmTSoggetto cnmTSoggetto, Integer id);

}
