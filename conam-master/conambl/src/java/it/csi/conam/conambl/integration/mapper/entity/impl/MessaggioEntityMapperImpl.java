/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.mapper.entity.MessaggioEntityMapper;
import it.csi.conam.conambl.vo.ExceptionVO;
import org.springframework.stereotype.Component;

@Component
public class MessaggioEntityMapperImpl implements MessaggioEntityMapper {

	@Override
	public ExceptionVO mapEntityToVO(CnmDMessaggio dto) {
		if (dto == null)
			return null;
		return new ExceptionVO(dto.getCodMessaggio(), dto.getDescMessaggio(), dto.getCnmDTipoMessaggio().getDescTipoMessaggio());
	}

}
