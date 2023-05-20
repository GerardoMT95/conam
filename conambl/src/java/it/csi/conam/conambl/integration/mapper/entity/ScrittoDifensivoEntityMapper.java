/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;

public interface ScrittoDifensivoEntityMapper extends EntityMapper<CnmTScrittoDifensivo, ScrittoDifensivoVO> {

	CnmTScrittoDifensivo mapVOtoEntityUpdate(ScrittoDifensivoVO vo, CnmTScrittoDifensivo cnmTScrittoDifensivo);

	ScrittoDifensivoVO mapEntityToVO(CnmTScrittoDifensivo cnmTVerbale, boolean filtraNormeScadute);
	
}
