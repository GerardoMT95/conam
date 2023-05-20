/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDRegione;
import it.csi.conam.conambl.integration.mapper.entity.RegioneEntityMapper;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.springframework.stereotype.Component;

@Component
public class RegioniEntityMapperImpl implements RegioneEntityMapper {

	@Override
	public RegioneVO mapEntityToVO(CnmDRegione dto) {
		if (dto == null)
			return null;
		RegioneVO regioneVO = new RegioneVO();
		regioneVO.setDenominazione(dto.getDenomRegione());
		regioneVO.setId(dto.getIdRegione());
		return regioneVO;
	}

}
