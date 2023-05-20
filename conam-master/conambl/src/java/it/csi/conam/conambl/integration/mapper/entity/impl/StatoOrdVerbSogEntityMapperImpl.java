/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoOrdVerbSog;
import it.csi.conam.conambl.integration.mapper.entity.StatoOrdVerbSogEntityMapper;
import it.csi.conam.conambl.vo.common.SelectVO;
import org.springframework.stereotype.Component;

@Component
public class StatoOrdVerbSogEntityMapperImpl implements StatoOrdVerbSogEntityMapper {

	@Override
	public SelectVO mapEntityToVO(CnmDStatoOrdVerbSog dto) {
		if (dto == null)
			return null;
		SelectVO selectVO = new SelectVO();
		selectVO.setDenominazione(dto.getDescStatoOrdVerbSog());
		selectVO.setId(dto.getIdStatoOrdVerbSog());
		return selectVO;
	}

}
