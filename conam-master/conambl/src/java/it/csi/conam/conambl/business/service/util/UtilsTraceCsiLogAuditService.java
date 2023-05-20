/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util;

public interface UtilsTraceCsiLogAuditService {
	
	// 20200622_LC
	
	//public String  getIdAppToTrace();
	//public String  getIpAddressToTrace();
	//public String  getUtenteToTrace();
	void traceCsiLogAudit(String operazione, String tabella, String key, String metodo, String dettaglioOperazione);
}
