/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.mapper.entity.ArticoloEntityMapper;
import it.csi.conam.conambl.vo.leggi.ArticoloVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ArticoloEntityMapperImpl implements ArticoloEntityMapper {

	@Autowired
	private UtilsDate utilsDate;

	@Override
	public ArticoloVO mapEntityToVO(CnmDArticolo dto) {
		if (dto == null)
			return null;
		ArticoloVO articoloVO = new ArticoloVO();
		articoloVO.setDenominazione(dto.getDescArticolo());
		articoloVO.setId(dto.getIdArticolo().longValue());
		articoloVO.setDataFineValidita(utilsDate.asLocalDate(dto.getFineValidita()));
		// 20201110 PP - Jira CONAM-102 - consente di restituire in output anche la data di inizio validita' norma
		articoloVO.setDataInizioValidita(utilsDate.asLocalDate(dto.getInizioValidita()));
		return articoloVO;
	}

	@Override
	public CnmDArticolo mapVOtoEntity(ArticoloVO vo) {
		if (vo == null)
			return null;
		CnmDArticolo dto = new CnmDArticolo();
		dto.setDescArticolo(vo.getDenominazione());
		dto.setInizioValidita(new Date());
		dto.setFineValidita(utilsDate.asDate(vo.getDataFineValidita()));
		return dto;
	}

}
