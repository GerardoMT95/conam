/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper;

import java.util.ArrayList;
import java.util.List;

public interface EntityMapper<EntityType, VOType> {

	VOType mapEntityToVO(EntityType dto);

	EntityType mapVOtoEntity(VOType vo);

	default List<EntityType> mapListVOtoListEntity(List<VOType> vl) {
		if (null == vl)
			return null;
		List<EntityType> t = new ArrayList<>();
		for (VOType v : vl) {
			t.add(mapVOtoEntity(v));
		}
		return t;
	}

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
