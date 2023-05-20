/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.mapper.entity.AllegatoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllegatoEntityMapperImpl implements AllegatoEntityMapper {

	@Autowired
	private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;
	@Autowired
	private UtilsDate utilsDate;

	@Override
	public AllegatoVO mapEntityToVO(CnmTAllegato dto) {
		if (dto == null)
			return null;
		AllegatoVO allegatoVO = new AllegatoVO();
		allegatoVO.setDataOraCaricamento(dto.getDataOraInsert() != null ? utilsDate.asLocalDateTime(dto.getDataOraInsert()) : null);
		allegatoVO.setId(dto.getIdAllegato());
		allegatoVO.setNome(dto.getNomeFile());
		allegatoVO.setTipo(tipoAllegatoEntityMapper.mapEntityToVO(dto.getCnmDTipoAllegato()));
		if (dto.getCnmTUser2().getNome() != null)
			allegatoVO.setUtente(dto.getCnmTUser2().getNome() + " " + dto.getCnmTUser2().getCognome());
		allegatoVO.setNumProtocollo(dto.getNumeroProtocollo());
		allegatoVO.setIdCategoria(dto.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato());
		if (StringUtils.isNotBlank(dto.getIdActaMaster()))
			allegatoVO.setIdActa(dto.getIdActaMaster());
		else
			allegatoVO.setIdActa(dto.getIdActa());
		return allegatoVO;
	}

}
