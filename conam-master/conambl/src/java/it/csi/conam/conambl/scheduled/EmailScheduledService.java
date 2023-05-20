/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled;

/**
 * @author Lucio Rosadini
 */

public interface EmailScheduledService {

	public void sendAllPromemoriaDocumentazione() throws Throwable;
	
	public void sendAllPromemoriaUdienze() throws Throwable;
}
