/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDElementoElenco;
import it.csi.conam.conambl.integration.mapper.entity.ElementoElencoEntityMapper;
import it.csi.conam.conambl.vo.common.SelectVO;
import org.springframework.stereotype.Component;

@Component
public class ElementoElencoEntityMapperImpl implements ElementoElencoEntityMapper {

	@Override
	public SelectVO mapEntityToVO(CnmDElementoElenco dto) {
		if (dto == null)
			return null;
		SelectVO selectVO = new SelectVO();
		selectVO.setDenominazione(dto.getDescElementoElenco());
		selectVO.setId(dto.getIdElementoElenco());
		return selectVO;
	}

}
