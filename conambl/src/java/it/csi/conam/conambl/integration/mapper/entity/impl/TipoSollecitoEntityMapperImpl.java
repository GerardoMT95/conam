/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDTipoSollecito;
import it.csi.conam.conambl.integration.mapper.entity.TipoSollecitoEntityMapper;
import it.csi.conam.conambl.vo.sollecito.TipoSollecitoVO;
import org.springframework.stereotype.Component;

@Component
public class TipoSollecitoEntityMapperImpl implements TipoSollecitoEntityMapper {

	@Override
	public TipoSollecitoVO mapEntityToVO(CnmDTipoSollecito dto) {
		if (dto == null)
			return null;
		TipoSollecitoVO tipoSollecitoVO = new TipoSollecitoVO();
		tipoSollecitoVO.setDenominazione(dto.getDescTipoSollecito());
		tipoSollecitoVO.setId(dto.getIdTipoSollecito());
		return tipoSollecitoVO;
	}

}
