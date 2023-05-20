/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

/**
 * @author riccardo.bova
 * @date 23 nov 2017
 */
public class ErrorCode {

	// CONFIGURAZIONE
	public static final long ERROR_DANGER_ID = 1;
	public static final String ERROR_DANGER_DESC = "danger";
	public static final long ERROR_WARNING = 2;
	public static final String ERRORE_GENERICO = "GENERIC";
	public static final String SESSIONE_NON_VALIDA = "SESSIONINVALID";
	public static final String ACCESSO_NON_AUTORIZZATO = "ACCESSINVALID";

	// VERBALE
	public static final String INSERIMENTO_VERBALE_NUMERO_VERBALE_ESISTENTE = "INSVERB01";
	public static final String MODIFICA_VERBALE_NUMERO = "MODVERB01";
	public static final String INSERISCI_SOG_GIA_ASSOCIATO_ID = "INSSOG01";
	public static final String INSERISCI_SOG_GIA_ASSOCIATO_IDSTAS = "INSSOG02";

	public static final String INSERISCI_SOG_FIS_CF_GIA_CENSITO_SUL_DB = "INSSOG03";

	public static final String DATA_NOTIFICA_FIELD_DIVERSA = "SVALL01";
	public static final String STATO_VERBALE_INCOMPATIBILE ="STATOVERIN";
	public static final String DATA_ACCERTAMENTO_NON_PREGRESSA ="DATACCNPR";
	public static final String CONFIRM_SALVA_STATO_FASCICOLO_PREGRESSO = "SAVESTAPR";
	public static final String LAVORAZIONE_FASCICOLO_PREGRESSO_SCADUTA = "STOPLAVPR";
	public static final String SALVA_STATO_FASCICOLO_PREGRESSO_OK_SPOSTA_ACTA_OK = "SAVSTPOK1";
	public static final String SALVA_STATO_FASCICOLO_PREGRESSO_OK = "SAVSTPOK2";
	public static final String SALVA_STATO_FASCICOLO_PREGRESSO_OK_TERMINA_PREG = "SAVSTPOK3";
	public static final String VERIFICA_DATE_FASCICOLO_PREGRESSO = "VERDATEPRE";
	public static final String CONFIRM_RECUPERO_PROTOCOLLO ="CONFRECPRO";
	
	public static final String WARNING_VERBALE_ACQUISISCI ="ACQUIWARN";
	
	public static final String WARNING_VERBALE_SOGGETTO_SENZA_CF ="NOCFWARN";
	public static final String CONFIRM_VERBALE_SOGGETTO_SENZA_CF ="NOCFCONF";

	public static final String WARNING_VERBALE_VERIFICA_PAGAMENTO ="VERPWARN";
	public static final String CONFIRM_VERBALE_VERIFICA_PAGAMENTO ="VERPCONF";
	
	// prontuario
	public static final String INSERIMENTO_AMBITO = "INSAMB01";
	public static final String INSERIMENTO_LEGGE = "INSLEG01";

	// ALLEGATI
	public static final String FILE_SIZE_ERR = "FILE_SIZE_ERR";
	public static final String ALLEGATO_SALVATO_MASTER_DA_SALVARE = "SAVEDOC01";
	public static final String PROTOCOLLO_NON_RECUPERATO_DA_PEC = "PROTNONPEC";
	public static final String PROTOCOLLO_RECUPERATO_NON_PREGRESSO = "SRCHPROT01";
	public static final String PROTOCOLLO_GIA_PRESENTE_IN_CONAM = "SRCHPROT02";
	public static final String PROTOCOLLO_NON_PRESENTE_IN_ACTA = "SRCHPROT03";
	public static final String SCRITTO_DIFENSIVO_DA_ASSOCIARE = "SRCHPROT04";
	public static final String PROTOCOLLO_GIA_SCRITTO_DIFENSIVO = "PROTGIASCR";
	public static final String STESSO_SOGGETTO_PIU_RELATE = "ERRSOGGREL";

	// rateizzazione
	public static final String CALCOLA_RATE_MINORE_DI_IMPORTO_MINIMO = "CALRATE01";
	public static final String PRIMA_RATA_UGUALE_A_SALDO = "CALRATE02";
	public static final String ULTIMA_RATA_UGUALE_A_SALDO = "CALRATE03";
	public static final String PRIMA_RATA_MINORE_DI_IMPORTO_MINIMO = "CALRATE04";
	public static final String ULTIMA_RATA_MINORE_DI_IMPORTO_MINIMO = "CALRATE05";
	public static final String BOLLETTINI_NON_ANCORA_GENERATI_RATEIZZAZIONE = "DOCRATE01";

	// WEB REMOTE (20200923 STADOC to DOQUI)
	public static final String PIEMONTE_PAY_LISTA_CARICO_NON_DISPONIBILE = "EPAYND01";
	public static final String PIEMONTE_PAY_LISTA_CARICO_CON_ERRORI = "EPAYND02";
	public static final String DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND01";
	public static final String DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND02";
	public static final String DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND03";
	public static final String DOQUI_PROTOCOLLA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND04";
	public static final String DOQUI_SPOSTA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND05";	// 2020706_LC
	public static final String DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE = "DOQUIND06";	// 2020728_LC
	public static final String DOQUI_RECUPERA_DOCUMENTO_NO_DOC_ELETTRONICO = "DOQUIND07";

	// ORDINANZA
	public static final String INSERIMENTO_ORDINANZA_NUMERO_DETERMINAZIONE_ESISTENTE = "INSORD01";
	public static final String BOLLETTINI_NON_ANCORA_GENERATI_ORDINANZA = "DOCORD01";
	public static final String CONFIRM_SALVA_STATO_ORDINANZA_PREGRESSA = "SAVESTORPR";
	public static final String DATE_ORDINANZA_NON_PREGRESSE ="DATORDNPR";
	public static final String UPDATE_STATO_OK = "UPDSTATOOK";
	public static final String NO_COD_FIS = "NOCODFIS";
	
	// SOLLECITO
	public static final String BOLLETTINI_NON_ANCORA_GENERATI_SOLLECITO = "DOCSOL01";
	public static final String SOLLECITO_PAGATO_SENZA_IMPORTO_DATA ="SPAGIMPDAT";

	// NOTIFICA
	public static final String NOTIFICA_NUMERO_RACCOMANDATA_GIA_PRESENTE = "NOTNUMR01";
	public static final String NOTIFICA_DATA_SPEDIZIONE_MAGGIORE_DATA_NOTIFICA = "NOTDATA01";
	public static final String DOQUI_MIMETYPE_NON_CORRETTO = "DOQUIMT01";
	public static final String DOQUI_MIMETYPE_NON_ESTRATTO = "DOQUIMT02";

	// SORIS
	public static final String FILE_NON_CARICATO_SU_SERVER_FTP_SORIS = "SORISFTP01";
	
	//GESTIONE BUSINESS EXCEPTION
	public static final String ERRORE_GENERICO_SISTEMA = "ERRGENSIS";
	public static final String UTENTE_NOACCESS_VERBALE = "VERBUTNAB";
	
	//SALVA VERBALE
	public static final String ERRORE_COMUNE_ENTE = "ERRCOMENT";


}
