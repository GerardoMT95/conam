/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTAcconto;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;

import java.util.List;

public interface AccontoEntityMapper extends EntityMapper<CnmTAcconto, AccontoVO> {

	List<AccontoVO> mapEntityListToVOList(List<CnmTAcconto> dtoList);
	
}
