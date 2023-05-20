/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoOrdVerbSog;
import it.csi.conam.conambl.integration.mapper.entity.StatoSoggettoOrdinanzaMapper;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import org.springframework.stereotype.Component;

@Component
public class StatoSoggettoOrdinanzaMapperImpl implements StatoSoggettoOrdinanzaMapper {

	@Override
	public StatoSoggettoOrdinanzaVO mapEntityToVO(CnmDStatoOrdVerbSog dto) {
		if (dto == null)
			return null;
		StatoSoggettoOrdinanzaVO statoVerbaleVO = new StatoSoggettoOrdinanzaVO();
		statoVerbaleVO.setDenominazione(dto.getDescStatoOrdVerbSog());
		statoVerbaleVO.setId(dto.getIdStatoOrdVerbSog());
		return statoVerbaleVO;
	}

}
