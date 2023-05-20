/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoRiscossione;
import it.csi.conam.conambl.integration.mapper.entity.StatoRiscossioneMapper;
import it.csi.conam.conambl.vo.riscossione.StatoRiscossioneVO;
import org.springframework.stereotype.Component;

@Component
public class StatoRiscossioneMapperImpl implements StatoRiscossioneMapper {

	@Override
	public StatoRiscossioneVO mapEntityToVO(CnmDStatoRiscossione dto) {
		StatoRiscossioneVO statoRiscossioneVO = new StatoRiscossioneVO();
		statoRiscossioneVO.setDenominazione(dto.getDescStatoRiscossione());
		statoRiscossioneVO.setId(dto.getIdStatoRiscossione());
		return statoRiscossioneVO;
	}

}
