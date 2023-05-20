/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

public interface SoggettoEntityMapper extends EntityMapper<CnmTSoggetto, SoggettoVO> {

	MinSoggettoVO mapEntityToMinVO(CnmTSoggetto dto);
	
	void mapVOtoUpdateEntity(SoggettoVO soggetto, CnmTSoggetto cnmTSoggetto);

}
