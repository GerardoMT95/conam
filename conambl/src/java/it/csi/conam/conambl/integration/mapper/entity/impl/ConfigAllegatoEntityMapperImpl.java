/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmCField;
import it.csi.conam.conambl.integration.mapper.entity.CfieldTypeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ConfigAllegatoEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigAllegatoEntityMapperImpl implements ConfigAllegatoEntityMapper {

	@Autowired
	private CfieldTypeEntityMapper cfieldTypeEntityMapper;

	@Override
	public ConfigAllegatoVO mapEntityToVO(CnmCField dto) {
		if (dto == null)
			return null;
		ConfigAllegatoVO conf = new ConfigAllegatoVO();

		conf.setRiga(dto.getRiga());
		conf.setFieldType(cfieldTypeEntityMapper.mapEntityToVO(dto.getCnmCFieldType()));
		conf.setIdTipo(dto.getCnmDTipoAllegato().getIdTipoAllegato());
		conf.setMaxLength(dto.getMaxLength());
		conf.setLabel(dto.getLabel());
		conf.setRequired(dto.getRequired());
		conf.setScale(dto.getScale());
		conf.setIdFonteElenco(dto.getCnmDElenco() == null ? null : dto.getCnmDElenco().getIdElenco());
		conf.setOrdine(dto.getOrdine());
		conf.setIdField(dto.getIdField());
		return conf;

	}

}
