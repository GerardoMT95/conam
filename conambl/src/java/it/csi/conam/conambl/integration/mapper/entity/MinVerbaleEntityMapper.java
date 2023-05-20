/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface MinVerbaleEntityMapper {

	default List<MinVerbaleVO> mapListEntityToListVO(List<CnmTVerbale> tl, Long idUser) {
		if (null == tl)
			return null;
		List<MinVerbaleVO> v = new ArrayList<>();
		for (CnmTVerbale t : tl) {
			v.add(mapEntityToVO(t, idUser));
		}
		return v;
	}

	MinVerbaleVO mapEntityToVO(CnmTVerbale dto, Long idUser);
}
