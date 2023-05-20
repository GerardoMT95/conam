/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTResidenza;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface ResidenzaEntityMapper extends EntityMapper<CnmTResidenza, SoggettoVO> {

	CnmTResidenza mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTResidenza cnmTResidenza);

	SoggettoVO mapEntitytoVOUpdate(SoggettoVO soggetto, CnmTResidenza cnmTResidenza);
}
