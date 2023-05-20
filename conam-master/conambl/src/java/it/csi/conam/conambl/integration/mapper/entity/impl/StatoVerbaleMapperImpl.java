/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import org.springframework.stereotype.Component;

@Component
public class StatoVerbaleMapperImpl implements StatoVerbaleEntityMapper {

	@Override
	public StatoVerbaleVO mapEntityToVO(CnmDStatoVerbale dto) {
		if (dto == null)
			return null;
		StatoVerbaleVO statoVerbaleVO = new StatoVerbaleVO();
		statoVerbaleVO.setDenominazione(dto.getDescStatoVerbale());
		statoVerbaleVO.setId(dto.getIdStatoVerbale());
		return statoVerbaleVO;
	}

}
