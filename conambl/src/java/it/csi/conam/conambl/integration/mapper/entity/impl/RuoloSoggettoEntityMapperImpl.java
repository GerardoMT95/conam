/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDRuoloSoggetto;
import it.csi.conam.conambl.integration.mapper.entity.RuoloSoggettoEntityMapper;
import it.csi.conam.conambl.vo.verbale.RuoloSoggettoVO;
import org.springframework.stereotype.Component;

@Component
public class RuoloSoggettoEntityMapperImpl implements RuoloSoggettoEntityMapper {

	@Override
	public RuoloSoggettoVO mapEntityToVO(CnmDRuoloSoggetto dto) {
		if (dto == null)
			return null;
		RuoloSoggettoVO ruoloVO = new RuoloSoggettoVO();
		ruoloVO.setDenominazione(dto.getDescRuoloSoggetto());
		ruoloVO.setId(dto.getIdRuoloSoggetto());
		return ruoloVO;
	}
	@Override
	public CnmDRuoloSoggetto mapVOtoEntity(RuoloSoggettoVO vo) {

		CnmDRuoloSoggetto cnmDRuoloSoggetto = new CnmDRuoloSoggetto();
		cnmDRuoloSoggetto.setDescRuoloSoggetto(vo.getDenominazione());
		cnmDRuoloSoggetto.setIdRuoloSoggetto(vo.getId());
		return cnmDRuoloSoggetto;
	}
	
}
