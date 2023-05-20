/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui;

public class DoquiConstants {

	public static final String LOGGER_PREFIX = "doqui";
	public static final String APPLICATION_CODE = "conambl";

	public final static String NULL_VALUE = "NULL_VALUE";
	/*PROTECTED REGION ID(R-492306315) ENABLED START*/
	
	//JIRA - Gestione Notifica
	//public static final String COLLOCAZIONE_CARTACEA = "Archivio settore competente";
	public static final String COLLOCAZIONE_CARTACEA = "Ufficio Contenzioso Amministrativo";

	public static final String DIRIGENTE = "FABRIZIO ZANELLA"; //provvisorio, recuperarlo da tabella su TAU
	public static final String ENTE = "REGIONE PIEMONTE"; //provvisorio, recuperarlo da tabella su TAU	
	public static final String SI = "Si";
	public static final String NO = "No";
	public static final String LUOGO = "TORINO";
	public static final String FRUITORE_PEC = "PEC";
	public static final String TASSA_AUTO = "TASSA AUTO";

	/*PROTECTED REGION END*/
	
	
	// 20200612_LC
    // INDEX
	
//      //public static final String ROOT                        = "/app:company_home/cm:stadoc";
//    public static final String ROOT                         	= "/app:company_home";
//    public static final String DEFAULT_PREFIX               	= "cm:";
//    public static final String SUFFIX							= "/cm:";
//	  public static final String CONTENT_PREFIX_NAME_SHORT    	= "cm:name";
//    public static final String CONTENT_PREFIX_NAME          	= "cm:content";
//    public static final String CONTENT_PREFIX_MODEL       	= "cm:contentmodel";
//    //public static final String CONTENT_PREFIX_MODEL         = Constants.PREFIX_NAME  +"stadocCustomModel";
//    public static final String CONTENT_PREFIX_FOLDER        	= "cm:folder";
//    public static final String TYPE_TEXT                    	= "d:text";
//    public static final String CONTENT_PREFIX_CONTAINS      	="cm:contains";
//    //public static final String CONTENT_PREFIX_MODEL 		= "stadoc:fpdocCustomModel";
//    public static final String PREFIX_NAME                  	= "stadoc:";
//    public static final String ENCODING                     	= "UTF-8";   

    public static final String MIMETYPE_DEFAULT             = "application/octet-stream";
    
    
    // da IndexConstants
    
    //public static final String INDEX_PREFIX = "sivia:";
	//public static final String INDEX_METADATO_SUFFIX = "@cm\\:";
	//public static final long   INDEX_MAX_SIZE = 1073741824;

	// Gestione FILE
	//public static final String INDEX_CONTENT_TYPE_DOWNLOAD = "application/download";
	//public static final String INDEX_CONTENT_DISPOSITION_ATTACHMENT = "attachment; filename=";			
	//public static final String INDEX_CONTENT_ALLEGATO_NAME = "stadoc:allegato";		
	//public static final int    INDEX_MAX_SIZE_ALLEGATO = Integer.MAX_VALUE;
	//public static final long   INDEX_MAX_SIZE_ALLEGATO_FIRMA = 2097152;
	//public static final String INDEX_VISUALIZZA_FILE_ISTANZA = "IST";
	//public static final String INDEX_VISUALIZZA_FILE_DETTAGLIO = "DETT";
	//public static final String MIMETYPE_DEFAULT = "application/octet-stream";
	
	//from refactoring
	//public static final String INDEX_FRUITORE = "stadoc";											// andrà presa da DB
	public static final String INDEX_ROOT = "/app:company_home";									// 20200616_LC							
	public static final String INDEX_DEFAULT_PREFIX = "cm:";
	public static final String INDEX_ENCODING = "UTF-8";
	public static final String INDEX_TYPE_TEXT = "d:text";
	
	public static final String INDEX_SUFFIX= "/cm:";
	public static final String INDEX_CONTENT_PREFIX_NAME = "cm:content";
	public static final String INDEX_CONTENT_PREFIX_CONTAINS="cm:contains";
	public static final String INDEX_CONTENT_PREFIX_MODEL = "cm:contentmodel";
	public static final String INDEX_CONTENT_PREFIX_FOLDER = "cm:folder";
	public static final String INDEX_CONTENT_PREFIX_NAME_SHORT = "cm:name";
	//public static final String INDEX_CONTENT_STADOC_PREFIX_MODEL = "stadoc:stadocCustomModel";		// andrà presa da DB
	
	// fnz(fruitore)
	//public static final String INDEX_ROOT = "/app:company_home/cm:stadoc";						
	//public static final String INDEX_CONTENT_PREFIX = "stadoc:";										
    
	

	// 20200629_LC_CONFCONAM nella nuova configurazione Conam queste due costanti andranno nel DB (già aggiunte alla Enum)
//	public static final String INDEX_FRUITORE = "conam";	
//	public static final String INDEX_CONTENT_STADOC_PREFIX_MODEL = "conam:model";	// stadoc:stadocCustomModel 
	
	
	
	
	// 20200630_LC - spostate dalla interface CommonManageDocumentoHelper
	
	// StatoRichiesta
	public static final long STATO_RICHIESTA_INVIATA  = 1;
	public static final long STATO_RICHIESTA_EVASA    = 2;
	public static final long STATO_RICHIESTA_ERRATA   = 3;
	public static final long STATO_RICHIESTA_IN_ELAB  = 4;
	// Servizio
	public static final long SERVIZIO_INSERIMENTO_ARCHIVIAZIONE       = 10;
	public static final long SERVIZIO_CONSULTAZIONE_ARCHIVIAZIONE     = 11;	
	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE     = 20;
	public static final long SERVIZIO_CONSULTAZIONE_PROTOCOLLAZIONE   = 21;
	public static final long SERVIZIO_ASSOCIA_DOCUMENTO_PROTOCOLLO    = 22;
	public static final long SERVIZIO_INSERIMENTO_PROTOCOLLAZIONE_FISICA = 23;	
	public static final long SERVIZIO_SPOSTAMENTO_PROTOCOLLAZIONE     = 24;			// 20200706_LC	
	public static final long SERVIZIO_COPIA_PROTOCOLLAZIONE     	  = 25;			// 20200728_LC	
	public static final long SERVIZIO_INSERIMENTO_GENERICO            = 30;
	public static final long SERVIZIO_CONSULTAZIONE_GENERICO          = 31;
	public static final long SERVIZIO_CANCELLAZIONE_GENERICO          = 32;
	public static final long SERVIZIO_MODIFICA_STATO_RICHIESTA        = 33;	
	public static final long SERVIZIO_RICERCA_ALLEGATO_INDEX     = 34;
	public static final long SERVIZIO_AGGIUNGI_ALLEGATO_ACTA    = 35;	
	// TipoFornitore
	public static final long FORNITORE_ACTA	= 1;
	public static final long FORNITORE_INDEX	= 2;
	
	// --
	
	// Codice tipo documento
	public static final String TIPO_DOCUMENTO_INDEX = "CONAM_DOC";
	public static final String TIPO_DOCUMENTO_INDEX_SIGNED = "CONAM_DOC_SIGNED";
	public static final String TIPO_DOCUMENTO_ACTA_FIGLIO = "CONAM_DOC";                   // gi&agrave; esistente
	public static final String TIPO_DOCUMENTO_ACTA_FIGLIO_SIGNED = "CONAM_DOC_SIGNED";     // nuovo
	public static final String TIPO_DOCUMENTO_ACTA = "CONAM_ACTA";                         // gi&agrave; esistente
	public static final String TIPO_DOCUMENTO_ACTA_SIGNED = "CONAM_ACTA_SIGNED";           // nuovo
	public static final String TIPO_DOCUMENTO_CONAM_1 = "CONAM_A1";                        // gi&agrave; esistente
	public static final String TIPO_DOCUMENTO_CONAM_1_SIGNED = "CONAM_A1_SIGNED";          // nuovo
	public static final String TIPO_DOCUMENTO_CONAM_2 = "CONAM_A2";                        // gi&agrave; esistente
	public static final String TIPO_DOCUMENTO_CONAM_2_SIGNED = "CONAM_A2_SIGNED";          // nuovo
	
	// codice fruitore
	public static final String CODICE_FRUITORE = "CONAM";
	
	// --
	
	// 20200711_LC utenete SYSTEM (scheduled tasks)
	public static final String USER_SCHEDULED_TASK  = "SYSTEM";
	
	
	// 20200713_LC prefisso paroal chiave ACTA
	public static final String PREFIX_PAROLA_CHIAVE = "CONAM_";
	
	// 20200716_LC prefisso index per custom model conam
	public static final String PREFIX_CONAM_INDEX_MODEL = "conam:";
}
