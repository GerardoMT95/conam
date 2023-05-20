/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoRata;
import it.csi.conam.conambl.integration.mapper.entity.StatoRataEntityMapper;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoRataVO;
import org.springframework.stereotype.Component;

@Component
public class StatoRataEntityMapperImpl implements StatoRataEntityMapper {

	@Override
	public StatoRataVO mapEntityToVO(CnmDStatoRata dto) {
		if (dto == null)
			return null;
		StatoRataVO statoPianoVO = new StatoRataVO();
		statoPianoVO.setDenominazione(dto.getDescStatoRata());
		statoPianoVO.setId(dto.getIdStatoRata());
		return statoPianoVO;
	}

}
