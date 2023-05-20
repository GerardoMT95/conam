/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.mapper.entity.AmbitoEntityMapper;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import org.springframework.stereotype.Component;

@Component
public class AmbitoEntityMapperImpl implements AmbitoEntityMapper {

	@Override
	public AmbitoVO mapEntityToVO(CnmDAmbito dto) {
		if (dto == null)
			return null;
		AmbitoVO ambito = new AmbitoVO();
		ambito.setDenominazione(dto.getDescAmbito());
		ambito.setId(dto.getIdAmbito().longValue());
		ambito.setAcronimo(dto.getAcronimo());
		return ambito;
	}

	@Override
	public CnmDAmbito mapVOtoEntity(AmbitoVO vo) {
		if (vo == null)
			return null;
		CnmDAmbito dto = new CnmDAmbito();
		dto.setDescAmbito(vo.getDenominazione());
		dto.setAcronimo(vo.getAcronimo().toUpperCase());
		return dto;
	}

}
