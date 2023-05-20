/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import org.springframework.stereotype.Component;

@Component
public class EnteEntityMapperImpl implements EnteEntityMapper {

	@Override
	public EnteVO mapEntityToVO(CnmDEnte dto) {
		if (dto == null)
			return null;
		EnteVO ente = new EnteVO();
		ente.setDenominazione(dto.getDescEnte());
		ente.setId(dto.getIdEnte());
		return ente;
	}

}
