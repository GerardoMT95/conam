/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmTSocieta;
import it.csi.conam.conambl.integration.mapper.entity.SocietaEntityMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.stereotype.Component;

@Component
public class SocietaEntityMapperImpl implements SocietaEntityMapper {

	@Override
	public SoggettoVO mapEntityToVO(CnmTSocieta cnmTSocieta) {
		if (cnmTSocieta == null)
			return null;
		SoggettoVO soggettoVO = new SoggettoVO();
		soggettoVO.setPersonaFisica(false);

		return soggettoVO;

	}

	@Override
	public CnmTSocieta mapVOtoEntity(SoggettoVO soggetto) {
		if (soggetto == null)
			return null;
		CnmTSocieta cnmTSocieta = new CnmTSocieta();
		return cnmTSocieta;
	}

	@Override
	public CnmTSocieta mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTSocieta cnmTSocieta) {
		if (soggetto == null)
			return null;

		return cnmTSocieta;
	}

}
