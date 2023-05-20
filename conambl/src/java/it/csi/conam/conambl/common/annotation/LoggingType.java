/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.annotation;

/**
 * @author riccardo.bova
 * @date 06 feb 2019
 */
public enum LoggingType {

	NONE(1), //
	REQUIRED(2);

	private final int value;

	LoggingType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
