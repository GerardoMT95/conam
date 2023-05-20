/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.exception;

public class FileP7MNotSignedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileP7MNotSignedException() {
		super();
	}

	public FileP7MNotSignedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileP7MNotSignedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileP7MNotSignedException(String message) {
		super(message);
	}

	public FileP7MNotSignedException(Throwable cause) {
		super(cause);
	}

}
