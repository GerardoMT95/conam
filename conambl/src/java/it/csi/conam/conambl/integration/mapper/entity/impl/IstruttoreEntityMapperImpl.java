/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.vo.IstruttoreVO;
import org.springframework.stereotype.Component;

@Component
public class IstruttoreEntityMapperImpl implements IstruttoreEntityMapper {

	@Override
	public IstruttoreVO mapEntityToVO(CnmTUser dto) {
		if (dto == null)
			return null;
		IstruttoreVO istruttore = new IstruttoreVO();
		istruttore.setDenominazione(dto.getNome().toUpperCase() + " " + dto.getCognome().toUpperCase());
		istruttore.setId(dto.getIdUser());
		return istruttore;
	}

}
