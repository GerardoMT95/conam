/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<T, V> {

	T mapFrom(V from);

	V mapTo(T to);

	default List<T> mapListFrom(List<V> vl) {
		if (null == vl)
			return null;
		List<T> t = new ArrayList<>();
		for (V v : vl) {
			t.add(mapFrom(v));
		}
		return t;
	}

	default List<V> mapListTo(List<T> tl) {
		if (null == tl)
			return null;
		List<V> v = new ArrayList<V>();
		for (T t : tl) {
			v.add(mapTo(t));
		}
		return v;
	}
}
