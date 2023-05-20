/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDStatoOrdinanza;
import it.csi.conam.conambl.integration.mapper.entity.StatoOrdinanzaMapper;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import org.springframework.stereotype.Component;

@Component
public class StatoOrdinanzaMapperImpl implements StatoOrdinanzaMapper {

	@Override
	public StatoOrdinanzaVO mapEntityToVO(CnmDStatoOrdinanza dto) {
		StatoOrdinanzaVO statoOrdinanzaVO = new StatoOrdinanzaVO();
		statoOrdinanzaVO.setDenominazione(dto.getDescStatoOrdinanza());
		statoOrdinanzaVO.setId(dto.getIdStatoOrdinanza());
		return statoOrdinanzaVO;
	}

}
