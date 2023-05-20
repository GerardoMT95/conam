/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmCFieldType;
import it.csi.conam.conambl.integration.mapper.entity.CfieldTypeEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.FieldTypeVO;
import org.springframework.stereotype.Component;

@Component
public class CfieldTypeEntityMapperImpl implements CfieldTypeEntityMapper {

	@Override
	public FieldTypeVO mapEntityToVO(CnmCFieldType dto) {
		if (dto == null)
			return null;
		FieldTypeVO fieldTypeVO = new FieldTypeVO();
		fieldTypeVO.setDenominazione(dto.getDescFieldType());
		fieldTypeVO.setId(dto.getIdFieldType());
		return fieldTypeVO;

	}

}
