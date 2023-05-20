/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;

public interface VerbaleEntityMapper extends EntityMapper<CnmTVerbale, VerbaleVO> {

	CnmTVerbale mapVOtoEntityUpdate(VerbaleVO vo, CnmTVerbale cnmTVerbale);

	VerbaleVO mapEntityToVO(CnmTVerbale cnmTVerbale, boolean filtraNormeScadute);
	
	MinVerbaleVO mapEntityToMinVO(CnmTVerbale dto);
	
	VerbaleSearchParam getVerbaleParamFromRequest(DatiVerbaleRequest dati);
	
}
