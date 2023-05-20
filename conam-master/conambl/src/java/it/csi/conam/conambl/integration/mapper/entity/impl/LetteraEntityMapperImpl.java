/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.mapper.entity.LetteraEntityMapper;
import it.csi.conam.conambl.vo.leggi.LetteraVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Date;

@Component
public class LetteraEntityMapperImpl implements LetteraEntityMapper {

	@Autowired
	private UtilsDate utilsDate;

	@Override
	public LetteraVO mapEntityToVO(CnmDLettera dto) {
		if (dto == null)
			return null;
		LetteraVO letteraVO = new LetteraVO();
		letteraVO.setDenominazione(dto.getLettera());
		letteraVO.setId(dto.getIdLettera().longValue());
		letteraVO.setDescrizioneIllecito(dto.getDescIllecito());
		letteraVO.setImportoMisuraRidotta(dto.getImportoRidotto().setScale(2, RoundingMode.HALF_UP));
		letteraVO.setSanzioneMassima(dto.getSanzioneMassima());
		letteraVO.setSanzioneMinima(dto.getSanzioneMinima());
		letteraVO.setScadenzaImporti(utilsDate.asLocalDate(dto.getScadenzaImporti()));
		letteraVO.setDataFineValidita(utilsDate.asLocalDate(dto.getFineValidita()));
		// 20201110 PP - Jira CONAM-102 - consente di restituire in output anche la data di inizio validita' norma
		letteraVO.setDataInizioValidita(utilsDate.asLocalDate(dto.getInizioValidita()));
		return letteraVO;
	}

	@Override
	public CnmDLettera mapVOtoEntity(LetteraVO vo) {
		if (vo == null)
			return null;

		CnmDLettera dto = new CnmDLettera();
		dto.setInizioValidita(new Date());
		dto.setLettera(vo.getDenominazione());
		dto.setDescIllecito(vo.getDescrizioneIllecito());
		dto.setImportoRidotto(vo.getImportoMisuraRidotta().setScale(2, RoundingMode.HALF_UP));
		dto.setSanzioneMassima(vo.getSanzioneMassima());
		dto.setSanzioneMinima(vo.getSanzioneMinima());
		dto.setScadenzaImporti(utilsDate.asDate(vo.getScadenzaImporti()));
		dto.setFineValidita(utilsDate.asDate(vo.getDataFineValidita()));

		return dto;
	}

}
