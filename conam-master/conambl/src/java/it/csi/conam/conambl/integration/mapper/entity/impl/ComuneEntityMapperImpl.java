/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.mapper.entity.ComuneEntityMapper;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import org.springframework.stereotype.Component;

@Component
public class ComuneEntityMapperImpl implements ComuneEntityMapper {

	@Override
	public ComuneVO mapEntityToVO(CnmDComune dto) {
		if (dto == null)
			return null;
		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setDenominazione(dto.getDenomComune());
		comuneVO.setId(dto.getIdComune());
		comuneVO.setCodBelfiore(dto.getCodBelfioreComune());
		return comuneVO;
	}

}
