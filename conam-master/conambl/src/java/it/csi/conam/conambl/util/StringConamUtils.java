/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 29 nov 2018
 */
public class StringConamUtils {

	public static String nullOrUppercase(String string) {
		if (StringUtils.isEmpty(string))
			return null;
		return string.toUpperCase();
	}
	
	public static String createStringFromList(List<String> stringList) {
		StringBuilder stringBuilder = new StringBuilder();
		for(String string: stringList) {
			stringBuilder.append(string);
			stringBuilder.append(" ");
		}
		return stringBuilder.toString();
	}
}
