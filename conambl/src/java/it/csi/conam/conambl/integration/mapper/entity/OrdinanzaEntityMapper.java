/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;

public interface OrdinanzaEntityMapper extends EntityMapper<CnmTOrdinanza, MinOrdinanzaVO> {

	CnmTOrdinanza mapVOtoEntityUpdate(MinOrdinanzaVO ordinanza, CnmTOrdinanza cnmTOrdinanza);
	
	OrdinanzaVO mapEntityToFullVO(CnmTOrdinanza dto);
	
}
