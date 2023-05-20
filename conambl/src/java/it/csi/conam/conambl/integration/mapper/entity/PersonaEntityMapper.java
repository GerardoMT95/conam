/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTPersona;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface PersonaEntityMapper extends EntityMapper<CnmTPersona, SoggettoVO> {

	CnmTPersona mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTPersona cnmTPersona);

}
