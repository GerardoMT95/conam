/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.security;

/**
 * @author riccardo.bova
 * @date 14 mar 2018
 */
public class AuthorizationRoles {

	// TUTTI HANNO IL RUOLO UTENTE
	public static final String UTENTE = "hasRole('UTENTE')";
	public static final String CREAZIONE_VERBALE_SOGGETTI = "hasRole('CONAM01A')";
	public static final String INSERIMENTO_ALLEGATI_VERBALE = "hasRole('CONAM01B')";
	public static final String CONSULTAZIONE_VARIAZIONE_VERBALI = "hasRole('CONAM02')";
	public static final String INSERIMENTO_CONTRODEDUZIONI = "hasRole('CONAM03A')";
	public static final String EMISSIONE_VERBALE_AUDIZIONE = "hasRole('CONAM03B')";
	public static final String EMISSIONE_ORDINANZA_ARCHIVIAZIONE = "hasRole('CONAM04')";
	public static final String IRROGAZIONE_ORDINANZA_INGIUNZIONE = "hasRole('CONAM05')";
	public static final String INSERIMENTO_RICORSO = "hasRole('CONAM06A')";
	public static final String GESTIONE_CALENDARIO_UDIENZE = "hasRole('CONAM06B')";
	public static final String ACQUISIZIONE_SENTENZA = "hasRole('CONAM06C')";
	public static final String RICERCA_NOTIFICHE = "hasRole('CONAM06D')";
	public static final String INSERIMENTO_PIANO_RATEIZZAZIONE = "hasRole('CONAM07A')";
	public static final String RICERCA_PIANO_RATEIZZAZIONE = "hasRole('CONAM07B')";
	public static final String RICONCILIAZIONE_MANUALE_VERBALE = "hasRole('CONAM09A')";
	public static final String RICONCILIAZIONE_MANUALE_ORDINANZA = "hasRole('CONAM09B')";
	public static final String RICONCILIAZIONE_MANUALE_PIANO = "hasRole('CONAM09C')";
	public static final String SOLLECITO_PAGAMENTO = "hasRole('CONAM08')";
	public static final String RISCOSSIONE_COATTIVA = "hasRole('CONAM10')";
	public static final String INSERIMENTO_PRONTUARIO = "hasRole('CONAM11')";
	public static final String INSERIMENTO_PREGRESSI = "hasRole('CONAM20')";

	// INSERIRE ALTRE AUTORIZZAZIONI
	public static final String RICERCA_ORDINANZA_DETTAGLIO = INSERIMENTO_RICORSO + " OR " + RICERCA_PIANO_RATEIZZAZIONE + " OR " + ACQUISIZIONE_SENTENZA + " OR " + SOLLECITO_PAGAMENTO;
	public static final String SALVATAGGIO_ALLEGATI_ORDINANZA = INSERIMENTO_RICORSO;
	public static final String SALVATAGGIO_ALLEGATI_ORDINANZA_SOGGETTO = ACQUISIZIONE_SENTENZA + " or " + RICONCILIAZIONE_MANUALE_ORDINANZA;
}
