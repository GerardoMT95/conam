/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper;

import java.util.ArrayList;
import java.util.List;

public interface EntityDMapper<EntityType, VOType> {

	VOType mapEntityToVO(EntityType dto);

	default List<VOType> mapListEntityToListVO(List<EntityType> tl) {
		if (null == tl)
			return null;
		List<VOType> v = new ArrayList<>();
		for (EntityType t : tl) {
			v.add(mapEntityToVO(t));
		}
		return v;
	}
}
