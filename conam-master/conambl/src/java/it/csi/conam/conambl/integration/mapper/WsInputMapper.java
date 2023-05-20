/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper;

import java.util.ArrayList;
import java.util.List;

public interface WsInputMapper<WsType, VOType> {

	WsType mapVOtoWsType(VOType vo);

	default List<WsType> mapListVOtoListEntity(List<VOType> vl) {
		if (null == vl)
			return null;
		List<WsType> t = new ArrayList<>();
		for (VOType v : vl) {
			t.add(mapVOtoWsType(v));
		}
		return t;
	}

}
