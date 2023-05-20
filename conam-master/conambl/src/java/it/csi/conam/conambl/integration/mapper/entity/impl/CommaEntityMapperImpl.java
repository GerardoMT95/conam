/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.mapper.entity.CommaEntityMapper;
import it.csi.conam.conambl.vo.leggi.CommaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommaEntityMapperImpl implements CommaEntityMapper {

	@Autowired
	private UtilsDate utilsDate;

	@Override
	public CommaVO mapEntityToVO(CnmDComma dto) {
		if (dto == null)
			return null;

		CommaVO commaVO = new CommaVO();
		commaVO.setDenominazione(dto.getDescComma());
		commaVO.setId(dto.getIdComma().longValue());
		commaVO.setDataFineValidita(utilsDate.asLocalDate(dto.getFineValidita()));
		// 20201110 PP - Jira CONAM-102 - consente di restituire in output anche la data di inizio validita' norma
		commaVO.setDataInizioValidita(utilsDate.asLocalDate(dto.getInizioValidita()));
		return commaVO;
	}

	@Override
	public CnmDComma mapVOtoEntity(CommaVO vo) {
		if (vo == null)
			return null;
		CnmDComma dto = new CnmDComma();
		dto.setDescComma(vo.getDenominazione());
		dto.setInizioValidita(new Date());
		dto.setFineValidita(utilsDate.asDate(vo.getDataFineValidita()));
		return dto;
	}

}
