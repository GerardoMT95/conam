/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.vo.verbale.StatoManualeVO;
import org.springframework.stereotype.Component;

@Component
public class StatoManualeMapperImpl implements StatoManualeEntityMapper {

	@Override
	public StatoManualeVO mapEntityToVO(CnmDStatoManuale dto) {
		if (dto == null)
			return null;
		StatoManualeVO statoManualeVO = new StatoManualeVO();
		statoManualeVO.setDenominazione(dto.getDescription());
		statoManualeVO.setId(dto.getId());
		return statoManualeVO;
	}

}
