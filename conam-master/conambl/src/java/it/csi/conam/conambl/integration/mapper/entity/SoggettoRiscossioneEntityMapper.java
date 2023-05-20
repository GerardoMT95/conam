/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTRiscossione;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface SoggettoRiscossioneEntityMapper {

	SoggettiRiscossioneCoattivaVO mapCnmROrdinanzaVerbSogToSoggettiRiscossioneCoattivaVO(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<SoggettiRiscossioneCoattivaVO> mapListCnmROrdinanzaVerbSogToListSoggettiRiscossioneCoattivaVO(List<CnmROrdinanzaVerbSog> list);

	SoggettiRiscossioneCoattivaVO mapCnmTRiscossioneToSoggettiRiscossioneCoattivaVO(CnmTRiscossione cnmTRiscossione);

	List<SoggettiRiscossioneCoattivaVO> mapListCnmTRiscossioneToListSoggettiRiscossioneCoattivaVO(List<CnmTRiscossione> list);
}
