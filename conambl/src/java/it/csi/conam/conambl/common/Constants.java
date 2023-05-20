/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public abstract class Constants {
	public final static String PRODUCT_NAME = "CONAM";
	public final static String COMPONENT_NAME = "conambl";

	public static final String HANDLER_IRIDE_SRV = COMPONENT_NAME + ".integration.iride";
	public static final String HANDLER_SECURITY = COMPONENT_NAME + ".security";

	public static final String HANDLER_EXCEPTION = COMPONENT_NAME + ".ExceptionHandler";

	// id utilizzo ente
	public static final long ID_UTILIZZO_ENTE_ACCERTATORE = 2;
	public static final long ID_UTILIZZO_ENTE_LEGGE = 1;

	// ricerca verbale selezione ricerca
	public static final String TYPE_SOGGETTO_RICERCA_TRASGRESSORE = "T";
	public static final String TYPE_SOGGETTO_RICERCA_OBBLIGATO_IN_SOLIDO = "O";

	// ID RUOLI SOGGETTO DB
	public static final Long VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID = 1L;
	public static final Long VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID = 2L;

	// ID RUOLI SOGGETTO DB
	public static final String RUOLO_UTENTE_AMMINISTRATIVO = "2";
	public static final String RUOLO_UTENTE_ISTRUTTORE = "1";
	public static final String RUOLO_UTENTE_ACCERTATORE = "3";

	// UTILIZZO ALLEGATO
	public static final Long ID_UTILIZZO_ALLEGATO_VERBALE = 1L;
	public static final Long ID_UTILIZZO_ALLEGATO_VERBALE_SOGGETTO = 3L;
	public static final Long ID_UTILIZZO_ALLEGATO_ORDINANZA = 2L;
	public static final Long ID_UTILIZZO_ALLEGATO_ORDINANZA_SOGGETTO = 4L;
	public static final Long ID_UTILIZZO_ALLEGATO_RATEIZZAZIONE = 5L;

	// CATEGORIA ALLEGATO
	public static final long ID_CATEGORIA_ALLEGATO_VERBALE = 1;
	public static final long ID_CATEGORIA_ALLEGATO_ISTRUTTORIA = 2;
	public static final long ID_CATEGORIA_ALLEGATO_GIURISDIZIONALE = 3;
	public static final long ID_CATEGORIA_ALLEGATO_RATEIZZAZIONE = 4;

	// STATO VERBALE
	public static final long STATO_VERBALE_INCOMPLETO = 1;
	
	public static final long STATO_VERBALE_IN_ACQUISIZIONE = 12;
	public static final long STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO = 13;
	public static final long STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI = 14;
	public static final long STATO_VERBALE_IN_ARCHIVIATO_IN_AUTOTUTELA = 15;
	public static final long STATO_VERBALE_PROTOCOLLAZIONE_IN_ATTESA_VERIFICA_PAGAMENTO = 18;
	public static final long STATO_VERBALE_PROTOCOLLAZIONE_PER_MANCANZA_CF = 19;
	
	public static final long STATO_VERBALE_ACQUISITO = 2;
	public static final long STATO_VERBALE_ACQUISITO_CON_PAGAMENTO = 3;
	public static final long STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI = 4;
	public static final long STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA = 5;
	public static final long STATO_VERBALE_ELIMINATO = 6;
	public static final long STATO_VERBALE_ORDINANZA = 7;
	public static final long STATO_VERBALE_FINE_PREGRESSO = 8;
	
	public static final long STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO = 16;
	public static final long STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO = 17;
	
//	public static final String STATO_VERBALE_FINE_PREGRESSO_DESC = "Recupero pregresso terminato";

	// TIPO FIELD TYPE
	public static final long FIELD_TYPE_NUMERIC = 1;
	public static final long FIELD_TYPE_STRING = 2;
	public static final long FIELD_TYPE_DATA = 3;
	public static final long FIELD_TYPE_BOOLEAN = 4;
	public static final long FIELD_TYPE_ELENCO = 5;
	public static final long FIELD_TYPE_DATA_ORA = 6;
	public static final long FIELD_TYPE_ELENCO_SOGGETTI = 7;

	// ID FIELD CNM_C_FIELD
	public static final long ID_FIELD_NUMERO_RACC_NOTIFICA = 1;
	public static final long ID_FIELD_DATA_SPEDIZIONE_NOTIFICA = 2;
	public static final long ID_FIELD_DATA_NOTIFICA = 3;
	public static final long ID_FIELD_SPESE_NOTIFICA = 4;
	public static final long ID_FIELD_MODALITA_NOTIFICA = 5;
	public static final long ID_FIELD_NOTIFICATA_NOTIFICA = 6;
	public static final long ID_FIELD_ESITO_SENTENZA = 12;
	public static final long ID_FIELD_DATA_SENTENZA = 13;
	public static final long ID_FIELD_DATA_PAGAMENTO_RICEVUTA_ORDINANZA = 16;
	public static final long ID_FIELD_IMPORTO_PAGATO_RICEVUTA_ORDINANZA = 17;
	public static final long ID_FIELD_CONTO_CORRENTE_VERSAMENTO = 18;

	public static final long ID_FIELD_IMPORTO_SANZIONE_SENTENZA = 14;
	public static final long ID_FIELD_IMPORTO_SPESE_PROCESSUALI_SENTENZA = 15;

	public static final long ID_FIELD_DATA_PAGAMENTO = 7;
	public static final long ID_FIELD_IMPORTO_PAGATO = 8;
	public static final long ID_FIELD_CONTO_CORRENTE_PAGAMENTO = 9;

	// DIMENSIONE ALLEGATI (10 mb)
	public static final int ALLEGATO_MAX_SIZE = 10240000;

	// PRONTUARIO
	public static final String NO_LETTERA = "NO LETTERA";
	public static final String NO_COMMA = "NO COMMA";

	// CPARAMETRO
	public static final long ID_NUMERO_CONTO_POSTALE = 1L;
	public static final long ID_DIRIGENTE = 2L;
	public static final long ID_CODICE_FISCALE_ENTE_CREDITORE = 4L;
	public static final long ID_ENTE_CREDITORE = 5L;
	public static final long ID_CBILL = 6L;
	public static final long ID_AUTORIZZAZIONE = 7L;
	public static final long ID_CODICE_VERSAMENTO_PIEMONTE_PAY = 8L;
	public static final long ID_INTESTATARIO_VERSAMENTO_POSTALE = 10L;
	public static final long ID_SETTORE_ENTE = 11L;
	public static final long ID_INFO_ENTE = 12L;
	public static final long ID_MAIL_SETTORE_TRIBUTI = 16L;
	
	
	// 20200622_LC
	public static final long ID_CODICE_PRODOTTO = 17L;
	public static final long ID_CODICE_LINEA_CLIENTE = 18L;
	public static final long ID_CODICE_AMBIENTE = 19L;
	public static final long ID_CODICE_UNITA_INSTALLAZIONE = 20L;
	
	// 20200903_LC
	public static final long ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO = 21L;
	public static final long ID_DATA_TERMINE_GESTIONE_PREGRESSO = 22L;
	
	// 20210312_LC
	public static final long ID_DIREZIONE = 23L;
	public static final long ID_SETTORE = 24L;
	public static final long ID_DIRIGENTE_SETTORE= 25L;
	public static final long ID_SEDE_ENTE= 26L;
	public static final long ID_OGGETTO_PRECOMIPLATO= 27L;
	public static final long ID_ORGANO_ACCERTATORE= 28L;
	public static final long ID_CORPO_LETTERA_ANN= 29L;
	public static final long ID_SALUTI_LETTERA_ANN= 30L;
	
	// 20210401_LC
	public static final long ID_OGGETTO_LETSOLRATE_PARZIALE = 31L;
	public static final long ID_CORPO1_LETSOLRATE_PARZIALE = 32L;
	public static final long ID_CORPO2_LETSOLRATE_PARZIALE = 33L;
	public static final long ID_OGGETTO_LETSOLRATE_NP = 34L;
	public static final long ID_CORPO1_LETSOLRATE_NP = 35L;
	public static final long ID_CORPO2_LETSOLRATE_NP = 36L;
	
	

	public static final long ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE = 3L;
	public static final long ID_OGGETTO_PAGAMENTO_SOLLECITO = 13L;
	public static final long ID_OGGETTO_PAGAMENTO_ORDINANZA = 14L;
	public static final long ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE = 37L;
	
	

	public static final List<Long> PARAMETRI_BOLLETTINI = Arrays.asList(ID_NUMERO_CONTO_POSTALE, //
			ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE, //
			ID_OGGETTO_PAGAMENTO_SOLLECITO, //
			ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE, //
			ID_OGGETTO_PAGAMENTO_ORDINANZA, //
			ID_CODICE_FISCALE_ENTE_CREDITORE, //
			ID_ENTE_CREDITORE, //
			ID_CBILL, //
			ID_INTESTATARIO_VERSAMENTO_POSTALE, //
			ID_AUTORIZZAZIONE, //
			ID_SETTORE_ENTE, //
			ID_INFO_ENTE);

	// rateizzazione
	public static final long ID_STATO_PIANO_IN_DEFINIZIONE = 1L;
	public static final long ID_STATO_PIANO_CONSOLIDATO = 2L;
	public static final long ID_STATO_PIANO_PROTOCOLLATO = 3L;
	public static final long ID_STATO_PIANO_NON_NOTIFICATO = 4L;
	public static final long ID_STATO_PIANO_NOTIFICATO = 5L;
	public static final long ID_STATO_PIANO_ESTINTO = 6L;

	// rata
	public static final long ID_STATO_RATA_NON_PAGATO = 1L;
	public static final long ID_STATO_RATA_PAGATO_ONLINE = 2L;
	public static final long ID_STATO_RATA_PAGATO_OFFLINE = 3L;
	public static final long ID_STATO_RATA_ESTINTO = 4L;

	public static final BigDecimal NUM_RATE_MINIMO = new BigDecimal("3");
	public static final BigDecimal NUM_RATE_MASSIMO = new BigDecimal("30");
	public static final BigDecimal IMPORTO_MINIMO_RATA = new BigDecimal("15.49");

	// costanti qrcode e datamatrix
	public static final String PAGO_PA = "PAGOPA";
	public static final String SEPARATORE_QRCODE = "|";
	public static final String VERSIONE = "002";

	public static final String CODFASE = "codfase=";
	public static final String NBPA = "NBPA";
	public static final String SEPARATORE_DATAMATRIX = ";";
	public static final String FASE_PAGAMENTO = "P1";
	public static final String LUNGHEZZA_IUV = "18";
	public static final String LUNGHEZZA_CODAVVISO = "35";
	public static final String LUNGHEZZA_CONTO = "12";
	public static final String LUNGHEZZA_IMPORTO = "10";
	public static final String LUNGHEZZA_TIPO_DOCUMENTO = "3";
	public static final String TIPO_DOCUMENTO = "896";
	public static final String ID_DATAMATRIX = "1";
	public static final String VALORE_FINALE_DATAMATRIX = "A";

	// EPAY
	public static final String CFEPAY = "EPAY";

	// ALLEGATI CREA ORDINANZA
	public static final List<Long> ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA = Arrays.asList(TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId(), TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId());
	public static final List<Long> ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO = Arrays.asList(TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId(), TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId());
	public static final List<Long> ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO_INGIUNZIONE= Arrays.asList(TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId());
	public static final List<Long> ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE= Arrays.asList(TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId());
	public static final Long ALLEGATO_ACCONTO_ORDINANZA = TipoAllegato.ORDINANZA_ACCONTO.getId();

	// costanti per gli elenchi di tipologie di allegato
	public static final long ID_ELENCO_ESITO_SENTENZA = 2;

	// STATI ORDINANZA SOGGETTO
	public static final long ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE = 1L;
	public static final long ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO = 2L;
	public static final List<Long> STATI_ORDINANZA_SOGGETTO = Arrays.asList(ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO, ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE);

	// stati ordinanza verbale soggetto
	public static final long ID_STATO_ORDINANZA_VERB_SOGG_INGIUNZIONE = 1;
	public static final long ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO = 2;
	public static final long ID_STATO_ORDINANZA_VERB_SOGG_RICORSO_ACCOLTO = 3;
	public static final long ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE = 7;
	public static final long ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_ONLINE = 8;

	// TIPO ORDINANZA
	public static final long ID_TIPO_ORDINANZA_ARCHIVIATO = 1L;
	public static final long ID_TIPO_ORDINANZA_INGIUNZIONE = 2L;
	public static final long ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE = 3L;
	public static final long ID_TIPO_ORDINANZA_ANNULLAMENTO_INGIUNZIONE = 4L;

	// STATO ORDINANZA
	public static final Long ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA = 1L;
	public static final Long ID_STATO_ORDINANZA_NOTIFICATA = 2L;
	public static final Long ID_STATO_ORDINANZA_NON_NOTIFICATA = 3L;
	public static final Long ID_STATO_ORDINANZA_RICORSO_IN_ATTO = 4L;
	public static final Long ID_STATO_ORDINANZA_PAGATA = 5L;
	public static final List<Long> LISTA_STATI_ORDINANZA_NON_PAGATI = Arrays.asList(
		ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA,
		ID_STATO_ORDINANZA_NOTIFICATA,
		ID_STATO_ORDINANZA_NON_NOTIFICATA,
		ID_STATO_ORDINANZA_RICORSO_IN_ATTO
	);
	

	// STATO SOLLECITO
	public static final Long ID_STATO_SOLLECITO_BOZZA = 1L;
	public static final Long ID_STATO_SOLLECITO_PROTOCOLLATO = 2L;
	public static final Long ID_STATO_SOLLECITO_NOTIFICATO = 4L;
	public static final Long ID_STATO_SOLLECITO_NON_NOTIFICATO = 3L;
	public static final Long ID_STATO_SOLLECITO_PAGATO_OFFLINE = 5L;
	public static final Long ID_STATO_SOLLECITO_PAGATO_ONLINE = 6L;
	public static final Long ID_STATO_SOLLECITO_ESTINTO = 7L;

	// TIPO SOLLECITO
	public static final Long ID_TIPO_SOLLECITO_ORDINANZA = 1L;
	public static final Long ID_TIPO_SOLLECITO_RATE = 2L;


	// MODALITA CARICAMENTO
	public static final Long ID_MODALITA_CARICAMENTO_DA_ACTA = 1L;
	public static final Long ID_MODALITA_CARICAMENTO_DA_DISPOSITVO = 2L;
	
	// codice gen bollettini
	public static final String CODICE_PIANO_RATEIZZAZIONE = "PIANORAT";
	public static final String CODICE_ORDINANZA = "ORDRAT";
	public static final String CODICE_SOLLECITO = "SOLRAT";

	// STATO ALLEGATO
	public static final Long STATO_ALLEGATO_NON_PROTOCOLLARE = 1L;
	public static final Long STATO_ALLEGATO_PROTOCOLLATO = 2L;
	public static final Long STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO = 3L;
	public static final Long STATO_ALLEGATO_IN_CODA = 4L;
	public static final Long STATO_AVVIA_SPOSTAMENTO_ACTA = 5L;
	public static final Long STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE = 6L;
	public static final Long STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA = 7L;		// 20200706_LC
	public static final Long STATO_ALLEGATO_PROTOCOLLATO_CARTACEO = 8L;

	public static final String ACRONIMO_AMBITO_MULTI = "MULTI";

	public static final List<Long> DOCUMENTI_CREATI_DA_CONAM = Arrays.asList(//
			TipoAllegato.VERBALE_AUDIZIONE.getId(), //
			TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId(), //
			TipoAllegato.LETTERA_ORDINANZA.getId(), //
			TipoAllegato.LETTERA_RATEIZZAZIONE.getId(), //
			TipoAllegato.LETTERA_SOLLECITO.getId(), //
			TipoAllegato.LETTERA_SOLLECITO_RATE.getId());//

	// STATO_RISCOSSIONE
	public static final long ID_STATO_RISCOSSIONE_BOZZA = 1L;
	public static final long ID_STATO_RISCOSSIONE_AVVIATA = 2L;
	public static final long ID_STATO_RISCOSSO = 4L;

	// STATO FILE
	public static final Long ID_STATO_FILE_INIZIALIZZATO = 1L;
	public static final Long ID_STATO_FILE_INVIATO = 2L;
	public static final Long ID_STATO_FILE_IN_ATTESA_DI_INVIO = 3L;
	public static final Long ID_STATO_FILE_RISCOSSO = 4L;

	// DESCR STATO FILE
	public static final String DESCR_STATO_FILE_INIZIALIZZATO = "Bozza";
	public static final String DESCR_STATO_FILE_INVIATO = "Inviato";
	public static final String DESCR_STATO_FILE_IN_ATTESA_DI_INVIO = "In attesa di invio";
	public static final String DESCR_STATO_FILE_RISCOSSO = "Riscosso";

	// STATO_ORDINANZA
	public static final long ID_STATO_RISCOSSO_SORIS = 6L;

	// STATO_SOLLECITO
	public static final long ID_STATO_ESTINTO_SOLLECITO = 7L;

	// STATO_RATA
	public static final long ID_STATO_ESTINTO_RATA = 4L;

	// STATO_RATA_PIANO
	public static final long ID_STATO_ESTINTO_PIANO_RATA = 6L;

	// STATO_ORDINANZA_VERB_SOG
	public static final long ID_STATO_ORDINANZA_VERB_SOG_RISCOSSO_SORIS = 9L;

	// STATO_RECORD
	public static final long ID_STATO_RECORD_ELAB = 2L;

	// TIPO FILE
	public static final Long ID_TIPO_FILE_TRACCIATO_290 = 1L;

	// TIPO RECORD
	public static final Long ID_TIPO_RECORD_N2 = 1L;
	public static final Long ID_TIPO_RECORD_N3 = 2L;
	public static final Long ID_TIPO_RECORD_N4 = 3L;

	// PRESENZA OBBLIGATO (N3)
	public static final String OBBLIGATO_PRESENTE = "C";
	public static final String OBBLIGATO_NON_PRESENTE = "N";

	// ID TIPO TRIBUTO (N4)
	public static final Long ID_TIPO_TRIBUTO_IMPORTO_SANZIONE = 1L;
	public static final Long ID_TIPO_TRIBUTO_IMPORTO_SPESE_NOTIFICA = 2L;
	public static final Long ID_TIPO_TRIBUTO_IMPORTO_SPESE_LEGALI = 3L;
	

	// STATO PREGRESSO
	public static final Long STATO_PREGRESSO_NON_PRESENTE = 1L;
	public static final Long STATO_PREGRESSO_IN_LAVORAZIONE = 2L;
	public static final Long STATO_PREGRESSO_LAVORATO = 3L;
	

	public static final List<Long> LISTA_STATI_IN_LAVORAZIONE = Arrays.asList(STATO_PREGRESSO_IN_LAVORAZIONE);
	public static final List<Long> LISTA_STATI_LAVORATO = Arrays.asList(Constants.STATO_PREGRESSO_NON_PRESENTE, Constants.STATO_PREGRESSO_LAVORATO);

	//MESSAGGIO PER CAMBIO STATO PREGRESSO
	public static final String PLACEHOLDER_MSG_NUOVO_STATO = "<VALORE_SELEZIONATO_DA_TENDINA>";
	
	//JIRA - Gestione Notifica
	//MODALITA NOTIFICA
	public static final Long ID_COMPIUTA_GIACENZA = 2L;
	public static final Long ID_IRREPERIBILE = 3L;
	public static final Long ID_RIFIUTATA = 4L;
	public static final Long ID_CONSEGNA_A_MANI_PROPRIE = 1L;
	
	// costanti per logAudit
	public static final String OGGETTO_ACTA = "ACTA";
	public static final String OPERAZIONE_TAGLIA_INCOLLA = "TI";
	public static final String OPERAZIONE_COPIA_INCOLLA = "CI";
	
	//PARAMETRI CONFIGURAZIONE EMAIL
	public static final Long ID_MAIL_FROM = 1L;

	//PARAMETRI UDIENZA
	public static final Long ID_MAIL_UDIENZA_ADVANCE_DAY                 = 10L;
	public static final Long ID_MAIL_UDIENZA_OBJECT_AUTORITA_GIUDIZIARIA = 11L;
	public static final Long ID_MAIL_UDIENZA_OBJECT_GIORNO               = 12L;
	public static final Long ID_MAIL_UDIENZA_BODY_AUTORITA_GIUDIZIARIA   = 13L;
	public static final Long ID_MAIL_UDIENZA_BODY_GIORNO                 = 14L;
	public static final Long ID_MAIL_UDIENZA_BODY_GIUDICE                = 15L;
	public static final Long ID_MAIL_UDIENZA_BODY_LUOGO                  = 16L;
	public static final Long ID_MAIL_UDIENZA_BODY_NOTE                   = 17L;
	public static final Long ID_MAIL_UDIENZA_CRON                        = 18L;
	public static final Long ID_MAIL_UDIENZA_ENABLE                      = 19L;
	
	//PARAMETRI DOCUMENTAZIONE
	public static final Long ID_MAIL_DOCUMENTAZIONE_ADVANCE_DAY               = 20L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_OBJECT_GIORNO             = 21L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_BODY_GIORNO               = 22L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_BODY_AUTORITA_GIUDIZIARIA = 23L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_BODY_GIUDICE              = 24L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_BODY_LUOGO                = 25L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_BODY_NOTE                 = 26L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_CRON                      = 27L;
	public static final Long ID_MAIL_DOCUMENTAZIONE_ENABLE                    = 28L;
	
	//PARAMETRI STATO MANUALE
	public static final Long ID_STATO_MANUALE_DEFAULT = 0L;
	public static final Long ID_STATO_MANUALE_NON_DI_COMPETENZA = 1L;
	public static final Long ID_STATO_MANUALE_DEVOLUTO_PER_COMPETENZA = 2L;
	public static final String ID_MESSAGGIO_STATO_MANUALE_NON_DI_COMPETENZA = "STMANINFO";
	
	
	public static final List<Long> LISTA_STATI_MANUALI_NON_DI_COMPETENZA = Arrays.asList(ID_STATO_MANUALE_DEFAULT,ID_STATO_MANUALE_NON_DI_COMPETENZA);
	public static final List<Long> LISTA_STATI_MANUALI_DEVOLUTI_PER_COMPETENZA = Arrays.asList(ID_STATO_MANUALE_DEVOLUTO_PER_COMPETENZA);
	
	
	
}
