/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper;

import java.util.ArrayList;
import java.util.List;

public interface WsOutputMapper<WsType, VOType> {

	VOType mapWsTypeToVO(WsType wsType);

	default List<VOType> mapArrayWsTypeToListVO(WsType[] tl) {
		if (null == tl)
			return null;
		List<VOType> v = new ArrayList<>();
		if (tl != null) {
			for (WsType t : tl) {
				v.add(mapWsTypeToVO(t));
			}
		}
		return v;
	}
}
