/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.utils;

import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import org.apache.commons.lang3.StringUtils;


public class CleanUtil {

	
	public static String cleanNullValue(String value){
		return (StringUtils.equalsIgnoreCase(value, DoquiConstants.NULL_VALUE) ?  null : value);
	}
	

}
