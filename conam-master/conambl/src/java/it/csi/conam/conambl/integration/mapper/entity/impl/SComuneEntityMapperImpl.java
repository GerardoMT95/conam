/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmSComune;
import it.csi.conam.conambl.integration.mapper.entity.SComuneEntityMapper;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import org.springframework.stereotype.Component;

@Component
public class SComuneEntityMapperImpl implements SComuneEntityMapper {

	@Override
	public ComuneVO mapEntityToVO(CnmSComune dto) {
		if (dto == null)
			return null;
		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setDenominazione(dto.getDenomComune());
		comuneVO.setId(dto.getCnmDComune().getIdComune());
		comuneVO.setCodBelfiore(dto.getCodBelfioreComune());
		return comuneVO;
	}

}
