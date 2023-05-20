/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTSocieta;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

public interface SocietaEntityMapper extends EntityMapper<CnmTSocieta, SoggettoVO> {

	CnmTSocieta mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTSocieta cnmTSocieta);

}
