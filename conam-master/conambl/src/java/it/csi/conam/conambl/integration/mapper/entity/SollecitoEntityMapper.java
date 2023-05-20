/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface SollecitoEntityMapper extends EntityMapper<CnmTSollecito, SollecitoVO> {

	CnmTSollecito mapVOtoEntityUpdate(SollecitoVO sollecitoVO, CnmTSollecito cnmTSollecito);

}
