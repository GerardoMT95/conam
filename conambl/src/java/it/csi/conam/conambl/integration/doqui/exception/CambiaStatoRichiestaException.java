/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.exception;

import it.csi.csi.wrapper.UserException;

////{PROTECTED REGION ID(R1845154095) ENABLED START////}
/**
 * Inserire qui la documentazione dell'eccezione CambiaStatoRichiestaException.
 * Consigli:
 * <ul>
 * <li> Dire se l'eccezione rappresenta una condizione di errore oppure 
 *      una casistica eccezionale applicativa
 * <li> Potrebbe essere meglio non dettagliare tanto la documentazione dell'
 *      eccezione quanto la documentazione delle clausole "throws" nei metodi
 *      che rilanciano effettivamente quest'eccezione
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public class CambiaStatoRichiestaException extends UserException {

	/**
	 * il serial version UID di una eccezione csi &egrave; sempre "1" perch&egrave; le
	 * eccezioni CSI non possono contenere features aggiuntive
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	public CambiaStatoRichiestaException(String msg, String nestedExcClassName, String nestedExcMessage,
			String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	/**
	 * @generated
	 */
	public CambiaStatoRichiestaException(String msg, String nestedExcClassName, String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	/**
	 * @generated
	 */
	public CambiaStatoRichiestaException(String msg, Throwable nested) {
		super(msg, nested);
	}

	/**
	 * @generated
	 */
	public CambiaStatoRichiestaException(String msg) {
		super(msg);
	}
}
