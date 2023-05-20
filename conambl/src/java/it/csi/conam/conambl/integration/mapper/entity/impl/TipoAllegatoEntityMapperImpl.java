/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.springframework.stereotype.Component;

@Component
public class TipoAllegatoEntityMapperImpl implements TipoAllegatoEntityMapper {

	@Override
	public TipoAllegatoVO mapEntityToVO(CnmDTipoAllegato dto) {
		if (dto == null)
			return null;
		TipoAllegatoVO tipoAllegatoVO = new TipoAllegatoVO();
		tipoAllegatoVO.setDenominazione(dto.getDescTipoAllegato());
		tipoAllegatoVO.setId(dto.getIdTipoAllegato());
		return tipoAllegatoVO;
	}

}
