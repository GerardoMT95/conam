/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoPianoRate;
import it.csi.conam.conambl.integration.mapper.entity.StatoPianoRateEntityMapper;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import org.springframework.stereotype.Component;

@Component
public class StatoPianoRateEntityMapperImpl implements StatoPianoRateEntityMapper {

	@Override
	public StatoPianoVO mapEntityToVO(CnmDStatoPianoRate dto) {
		if (dto == null)
			return null;
		StatoPianoVO statoPianoVO = new StatoPianoVO();
		statoPianoVO.setDenominazione(dto.getDescStatoPianoRate());
		statoPianoVO.setId(dto.getIdStatoPianoRate());
		return statoPianoVO;
	}

}
