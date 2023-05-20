/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.doqui.entity.CnmTFruitore;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;


public interface CommonManageDocumentoHelper {
	
	// 20200630_LC FROM int TO long - spostate in DOquiConstants
//	public static final long STATO_RICHIESTA_INVIATA  = 1;
//	public static final long STATO_RICHIESTA_EVASA    = 2;
//	public static final long STATO_RICHIESTA_ERRATA   = 3;
//	public static final long STATO_RICHIESTA_IN_ELAB  = 4;
//	
//	public static final long SERVIZIO_INSERIMENTO_ARCHIVIAZIONE       = 10;
//	public static final long SERVIZIO_CONSULTAZIONE_ARCHIVIAZIONE     = 11;
//	
//	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE     = 20;
//	public static final long SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE   = 21;
//	public static final long SERVIZIO_ASSOCIA_DOCUMENTO_PROTOCOLLO    = 22;
//	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE_FISICA = 23;
//	
//	
//	public static final long SERVIZIO_INSERIMENTO_GENERICO            = 30;
//	public static final long SERVIZIO_CONSULTAZIONE_GENERICO          = 31;
//	public static final long SERVIZIO_CANCELLAZIONE_GENERICO          = 32;
//	public static final long SERVIZIO_MODIFICA_STATO_RICHIESTA        = 33;
//	
//	public static final long SERVIZIO_RICERCA_ALLEGATO_INDEX     = 34;
//	public static final long SERVIZIO_AGGIUNGI_ALLEGATO_ACTA    = 35;
//	
//	
//	public static final long FORNITORE_ACTA	= 1;
//	public static final long FORNITORE_INDEX	= 2;
	
	/**
	 * 
	 * @return
	 * @throws FruitoreException
	 */
	public CnmTFruitore getFruitore() throws FruitoreException;
	
	

	
	
	
	
	
	
}
