/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.exception;

public class RemoteWebServiceException extends BusinessException {

	private static final long serialVersionUID = -2631969419398877860L;

	public RemoteWebServiceException(String codice) {
		super(codice);
	}

}
