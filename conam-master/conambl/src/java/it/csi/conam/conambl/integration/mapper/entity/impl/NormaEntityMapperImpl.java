/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDNorma;
import it.csi.conam.conambl.integration.mapper.entity.NormaEntityMapper;
import it.csi.conam.conambl.vo.leggi.NormaVO;
import org.springframework.stereotype.Component;

@Component
public class NormaEntityMapperImpl implements NormaEntityMapper {

	@Override
	public NormaVO mapEntityToVO(CnmDNorma dto) {
		if (dto == null)
			return null;
		NormaVO normaVO = new NormaVO();
		normaVO.setDenominazione(dto.getRiferimentoNormativo());
		normaVO.setId(dto.getIdNorma().longValue());

		return normaVO;
	}

	@Override
	public CnmDNorma mapVOtoEntity(NormaVO norma) {
		if (norma == null)
			return null;
		CnmDNorma cnmDNorma = new CnmDNorma();
		cnmDNorma.setRiferimentoNormativo(norma.getDenominazione());
		return cnmDNorma;
	}

}
