/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTRata;
import it.csi.conam.conambl.integration.mapper.entity.RataEntityMapper;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class RataEntityMapperImpl implements RataEntityMapper {

	@Autowired
	private UtilsDate utilsDate;

	@Override
	public RataVO mapEntityToVO(CnmTRata dto) {
		if (dto == null)
			return null;
		RataVO rataVO = new RataVO();
		rataVO.setIdRata(dto.getIdRata());
		rataVO.setDataScadenza(utilsDate.asLocalDate(dto.getDataScadenza()));
		rataVO.setImportoRata(dto.getImportoRata());
		rataVO.setNumeroRata(dto.getNumeroRata());
		rataVO.setIsEditEnable(Boolean.FALSE);
		rataVO.setDataFineValidita(utilsDate.asLocalDate(dto.getDataFineValidita()));

		return rataVO;
	}

	@Override
	public CnmTRata mapVOtoEntity(RataVO vo) {
		CnmTRata cnmTRata = new CnmTRata();
		mapVOtoEntityUpdate(vo, cnmTRata);
		return cnmTRata;
	}

	@Override
	public CnmTRata mapVOtoEntityUpdate(RataVO vo, CnmTRata cnmTRata) {
		cnmTRata.setDataScadenza(utilsDate.asDate(vo.getDataScadenza()));
		cnmTRata.setImportoRata(vo.getImportoRata().setScale(2, RoundingMode.HALF_UP));
		cnmTRata.setNumeroRata(vo.getNumeroRata());
		cnmTRata.setDataFineValidita(utilsDate.asDate(vo.getDataFineValidita()));

		return cnmTRata;
	}

}
