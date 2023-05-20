/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDTipoOrdinanza;
import it.csi.conam.conambl.integration.mapper.entity.TipoOrdinanzaMapper;
import it.csi.conam.conambl.vo.ordinanza.TipoOrdinanzaVO;
import org.springframework.stereotype.Component;

@Component
public class TipoOrdinanzaMapperImpl implements TipoOrdinanzaMapper {

	@Override
	public TipoOrdinanzaVO mapEntityToVO(CnmDTipoOrdinanza dto) {
		TipoOrdinanzaVO tipoOrdinanzaVO = new TipoOrdinanzaVO();
		tipoOrdinanzaVO.setDenominazione(dto.getDescTipoOrdinanza());
		tipoOrdinanzaVO.setId(dto.getIdTipoOrdinanza());
		return tipoOrdinanzaVO;
	}

}
