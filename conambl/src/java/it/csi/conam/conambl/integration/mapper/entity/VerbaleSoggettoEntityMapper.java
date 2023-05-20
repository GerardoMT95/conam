/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVORaggruppatoPerSoggetto;

import java.util.List;

public interface VerbaleSoggettoEntityMapper extends EntityMapper<CnmRVerbaleSoggetto, VerbaleSoggettoVO> {
	
	VerbaleSoggettoVORaggruppatoPerSoggetto mapEntityToVORaggruppatoPerSoggetto(List<CnmRVerbaleSoggetto> dto);
}
