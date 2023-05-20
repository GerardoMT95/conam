/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDProvincia;
import it.csi.conam.conambl.integration.mapper.entity.ProvinciaEntityMapper;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import org.springframework.stereotype.Component;

@Component
public class ProvinciaEntityMapperImpl implements ProvinciaEntityMapper {

	@Override
	public ProvinciaVO mapEntityToVO(CnmDProvincia dto) {
		if (dto == null)
			return null;
		ProvinciaVO provinciaVO = new ProvinciaVO();
		provinciaVO.setDenominazione(dto.getDenomProvincia());
		provinciaVO.setId(dto.getIdProvincia());
		provinciaVO.setSigla(dto.getSiglaProvincia());	// // 20210315_LC Jira124
		return provinciaVO;
	}

}
