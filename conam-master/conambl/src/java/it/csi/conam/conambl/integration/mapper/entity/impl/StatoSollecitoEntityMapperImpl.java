/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoSollecito;
import it.csi.conam.conambl.integration.mapper.entity.StatoSollecitoEntityMapper;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;
import org.springframework.stereotype.Component;

@Component
public class StatoSollecitoEntityMapperImpl implements StatoSollecitoEntityMapper {

	@Override
	public StatoSollecitoVO mapEntityToVO(CnmDStatoSollecito dto) {
		if (dto == null)
			return null;
		StatoSollecitoVO statoSollecitoVO = new StatoSollecitoVO();
		statoSollecitoVO.setDenominazione(dto.getDescStatoSollecito());
		statoSollecitoVO.setId(dto.getIdStatoSollecito());
		return statoSollecitoVO;
	}

}
