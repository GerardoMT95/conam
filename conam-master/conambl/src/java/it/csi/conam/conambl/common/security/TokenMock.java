/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.security;

/**
 * @author riccardo.bova
 * @date 12 giu 2018
 */
public class TokenMock {

	public static final String DEMO20 = "AAAAAA00B77B000F/CSI PIEMONTE/DEMO 20/ACTALIS_EU/20210507105354/16/XBgBD/BJQi8WYHePVowlHw==";
	public static final String DEMO21 = "AAAAAA00A11B000J/CSI PIEMONTE/DEMO 21/ACTALIS_EU/20210505164007/16/w1QEpBD7Z9IplEaAr/FgMQ==";
	
	public static final String DEMO22 = "AAAAAA00A11C000K/DEMO 22/CSI PIEMONTE/INFOCERT_3/20181217115634/16/NtX84zYAiOx0AKyGK3qq0A==";// accertatore
	public static final String DEMO24 = "AAAAAA00A11E000M/DEMO 24/CSI PIEMONTE/INFOCERT_3/20190618165626/16/+zRPstvCOw6GUYjZTO6DEg==";// ISTRUTTORE
	public static final String UTENTE = "WEWEWE87E78W787W/PIPPO/PAPERINO/IPA/20180625192126/2/CI/7FiPNWqm9tdzOAwJpZw==";// UTENTE

	/*
	 * SELECT u.CODICE_FISCALE, p.DESC_PROFILO FROM GRM_R_PROFILO_USER pu INNER
	 * JOIN GRM_T_USER u ON pu.ID_USER=u.ID_USER INNER JOIN GRM_D_PROFILO p ON
	 * P.ID_PROFILO=pu.ID_PROFILO;
	 */
}
