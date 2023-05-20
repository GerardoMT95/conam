/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDNazione;
import it.csi.conam.conambl.integration.mapper.entity.NazioneEntityMapper;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import org.springframework.stereotype.Component;

@Component
public class NazioneEntityMapperImpl implements NazioneEntityMapper {

	@Override
	public NazioneVO mapEntityToVO(CnmDNazione dto) {
		if (dto == null)
			return null;
		NazioneVO nazioneVO = new NazioneVO();
		nazioneVO.setDenominazione(dto.getDenomNazione());
		nazioneVO.setId(dto.getIdNazione());
		return nazioneVO;
	}

}
