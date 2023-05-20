export class Routing {

    //HOME
    public static HOME: string = "home";

    //VERBALE
    public static VERBALE_DATI: string = "verbale/dati/"; //+id
    public static VERBALE_SOGGETTO: string = "verbale/soggetto/"; //+id
    public static VERBALE_ALLEGATO: string = "verbale/allegato/"; //+id
    public static VERBALE_RIEPILOGO: string = "verbale/riepilogo/"; //+id
    public static VERBALE_RICERCA: string = "verbale/ricerca";
    public static VERBALE_ELIMINAZIONE: string = "verbale/eliminazione";
    public static VERBALE_RICERCA_SCRITTI_DIFENSIVI: string = "verbale/ricerca-scritti-difensivi";
    public static VERBALE_INSERIMENTO_SCRITTI_DIFENSIVI: string = "verbale/inserimento-scritti-difensivi";
    public static VERBALE_SCRITTO_DIFENSIVO: string = "verbale/scritto-difensivo/"; //+id
    //SOGGETTO
    public static SOGGETTO_RIEPILOGO: string = "soggetto/riepilogo/"; //+id
    //PRONTUARIO
    public static PRONTUARIO_UTILITY_NORME: string = "prontuario/utility";

    // PREGRESSO
    public static PREGRESSO_DATI: string = "pregresso/dati/"; //+id
    public static PREGRESSO_RICERCA: string = "pregresso/ricerca";
    public static PREGRESSO_SOGGETTO: string = "pregresso/soggetto/"; //+id
    public static PREGRESSO_ALLEGATO: string = "pregresso/allegato/"; //+id
    public static PREGRESSO_RIEPILOGO: string = "pregresso/riepilogo/"; //+id
    public static PREGRESSO_ELIMINAZIONE: string = "pregresso/eliminazione";
    public static PREGRESSO_INSERIMENTO: string = "pregresso/inserimento";
    public static PREGRESSO_INSERIMENTO_MANUALE: string = "pregresso/inserimento-manuale";
    public static PREGRESSO_INSERIMENTO_ACTA: string = "pregresso/inserimento-acta";
    public static PREGRESSO_RIEPILOGO_ORDINANZE: string = "pregresso/riepilogo-ordinanze/"; //+id
    public static PREGRESSO_RIEPILOGO_PIANI: string = "pregresso/riepilogo-piani/"; //+id
    public static PREGRESSO_RIEPILOGO_SOLLECITI: string = "pregresso/riepilogo-solleciti/"; //+id
    public static PREGRESSO_RIEPILOGO_DISPOSIZIONI: string = "pregresso/riepilogo-disposizioni/"; //+id
    public static PREGRESSO_RIEPILOGO_RICEVUTE: string = "pregresso/riepilogo-ricevute/"; //+id


    //GESTIONE CONTENZIOSO AMMINISTRATIVO
    public static GESTIONE_CONT_AMMI_INS_ORDINANZA_RICERCA_VERBALE: string = "gest-cont-amministrativo/ordinanza-ins-ricerca-verbale";
    public static GESTIONE_CONT_AMMI_INS_ORDINANZA_RIEPILOGO_VERBALE: string = "gest-cont-amministrativo/ordinanza-ins-riepilogo-verbale/";
    public static GESTIONE_CONT_AMMI_INS_ORDINANZA_CREA_ORDINANZA: string = "gest-cont-amministrativo/ordinanza-ins-crea-ordinanza/";
    public static GESTIONE_CONT_AMMI_INS_ORDINANZA_AGGIUNGI_NOTIFICA: string = "gest-cont-amministrativo/ordinanza-ins-aggiungi-notifica/";
    public static GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA: string = "gest-cont-amministrativo/ordinanza-view-notifica/";
    public static GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO: string = "gest-cont-amministrativo/ordinanza-riepilogo/";
    public static GESTIONE_CONT_AMMI_RICERCA_ORDINANZA: string = "gest-cont-amministrativo/ordinanza-ricerca";
    public static GESTIONE_CONT_AMMI_ORDINANZA_TEMPLATE_LETTERA: string = "gest-cont-amministrativo/ordinanza-template-lettera/"; //+id

    public static GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RICERCA: string = "gest-cont-amministrativo/emissione-verbale-audizione-ricerca";
    public static GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RIEPILOGO: string = "gest-cont-amministrativo/emissione-verbale-audizione-riepilogo/";
    public static GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_CREA: string = "gest-cont-amministrativo/emissione-verbale-audizione-crea/"; 
    public static GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_ALLEGA: string = "gest-cont-amministrativo/emissione-verbale-audizione-allega/"; 
    public static GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_TEMPLATE_LETTERA: string = "gest-cont-amministrativo/emissione-verbale-audizione-template-lettera/"; //+id

    public static GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_RICERCA: string = "gest-cont-amministrativo/convocazione-audizione-ricerca";
    public static GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_RIEPILOGO: string = "gest-cont-amministrativo/convocazione-audizione-riepilogo/";
    public static GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_CREA: string = "gest-cont-amministrativo/convocazione-audizione-crea/";
    public static GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_CREA_SOGG: string = "gest-cont-amministrativo/convocazione-audizione-crea-soggetto/";
    public static GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_TEMPLATE_LETTERA: string = "gest-cont-amministrativo/convocazione-audizione-template-lettera/"; //+id

    public static GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RICERCA: string = "gest-cont-amministrativo/controdeduzioni-verbale-ricerca";
    public static GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RIEPILOGO: string = "gest-cont-amministrativo/controdeduzioni-verbale-riepilogo/";
    public static GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_ALLEGATO: string = "gest-cont-amministrativo/controdeduzioni-verbale-allegato/";
    public static GESTIONE_CONT_AMMI_INSERIMENTO_RECIDIVITA_RICERCA: string = "gest-cont-amministrativo/recidivita-ricerca";
    public static GESTIONE_CONT_AMMI_INSERIMENTO_RECIDIVITA_DETTAGLIO: string = "gest-cont-amministrativo/recidivita-dettaglio/";//+id
    
    //FASE GIURISDIZIONALE
    public static FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RICERCA: string = "fase-giurisdizionale/comparsa-verbale-ricerca";
    public static FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RIEPILOGO: string = "fase-giurisdizionale/comparsa-verbale-riepilogo/";
    public static GESTIONE_CONT_AMMI_INSERIMENTO_COMPARSA_ALLEGATO: string = "fase-giurisdizionale/comparsa-verbale-allegato/";        
    public static FASE_GIURISDIZIONALE_CANCELLERIA_RICERCA_ORDINANZA: string = "fase-giurisdizionale/cancelleria-ricerca-ordinanza";
    public static FASE_GIURISDIZIONALE_CANCELLERIA_DETTAGLIO_ORDINANZA: string = "fase-giurisdizionale/cancelleria-dettaglio-ordinanza/";
    public static FASE_GIURISDIZIONALE_CANCELLERIA_ALLEGATO_ORDINANZA: string = "fase-giurisdizionale/cancelleria-allegato-ordinanza/";
    public static FASE_GIURISDIZIONALE_RATEIZZAZIONE_RICERCA_ORDINANZA: string = "fase-giurisdizionale/rateizzazione-ricerca-ordinanza";
    public static FASE_GIURISDIZIONALE_RATEIZZAZIONE_DETTAGLIO_ORDINANZA: string = "fase-giurisdizionale/rateizzazione-dettaglio-ordinanza/";
    public static FASE_GIURISDIZIONALE_RATEIZZAZIONE_ALLEGATO_ORDINANZA: string = "fase-giurisdizionale/rateizzazione-allegato-ordinanza/";
    public static FASE_GIURISDIZIONALE_RICORSO_RICERCA_ORDINANZA: string = "fase-giurisdizionale/ricorso-ricerca-ordinanza";
    public static FASE_GIURISDIZIONALE_RICORSO_DETTAGLIO_ORDINANZA: string = "fase-giurisdizionale/ricorso-dettaglio-ordinanza/"; //+id
    public static FASE_GIURISDIZIONALE_RICORSO_ALLEGATO_ORDINANZA: string = "fase-giurisdizionale/ricorso-allegato-ordinanza/"; //+id
    public static FASE_GIURISDIZIONALE_SENTENZA_DETTAGLIO_ORDINANZA: string = "fase-giurisdizionale/sentenza-dettaglio-ordinanza/";
    public static FASE_GIURISDIZIONALE_SENTENZA_RICERCA_ORDINANZA: string = "fase-giurisdizionale/sentenza-ricerca-ordinanza";
    public static FASE_GIURISDIZIONALE_SENTENZA_ALLEGATO_ORDINANZA: string = "fase-giurisdizionale/sentenza-allegato-ordinanza";
    public static FASE_GIURISDIZIONALE_CALENDARIO_UDIENZE: string = "fase-giurisdizionale/calendario-udienze";

    //RISCOSSIONE
    public static RISCOSSIONE_SOLLECITO_RICERCA: string = "riscossione/sollecito-pagamento-ricerca";
    public static RISCOSSIONE_SOLLECITO_DETTAGLIO: string = "riscossione/sollecito-pagamento-dettaglio";
    public static RISCOSSIONE_SOLLECITO_RICERCA_RATE: string = "riscossione/sollecito-pagamento-rate-ricerca";
    public static RISCOSSIONE_SOLLECITO_DETTAGLIO_RATE: string = "riscossione/sollecito-pagamento-rate-dettaglio";    
    public static RISCOSSIONE_SOLLECITO_TEMPLATE: string = "riscossione/sollecito-pagamento-template/"; //+id
    public static RISCOSSIONE_SOLLECITO_RATE_TEMPLATE: string = "riscossione/sollecito-pagamento-rate-template/"; //+id
    public static RISCOSSIONE_SOLLECITO_INS_NOTIFICA: string = "riscossione/sollecito-pagamento-ins-notifica/"; //+id
    public static RISCOSSIONE_SOLLECITO_VIEW_NOTIFICA: string = "riscossione/sollecito-pagamento-view-notifica/"; //+id

    public static RISCOSSIONE_COATTIVA: string = "riscossione/riscossione-coattiva-ricerca";
    public static RISCOSSIONE_COATTIVA_ELENCO: string = "riscossione/riscossione-coattiva-elenco";

    //PAGAMENTI
    public static PAGAMENTI_CREA_PIANO: string = "pagamenti/piano-crea";
    public static PAGAMENTI_RICERCA_PIANO: string = "pagamenti/piano-ricerca";
    public static PAGAMENTI_PIANO_INSERIMENTO: string = "pagamenti/piano-inserimento";
    public static PAGAMENTI_PIANO_MODIFICA: string = "pagamenti/piano-modifica/"; //+id
    public static PAGAMENTI_PIANO_VIEW: string = "pagamenti/piano-view/"; //+id
    public static PAGAMENTI_PIANO_TEMPLATE: string = "pagamenti/template/"; //+id
    public static PAGAMENTI_PIANO_INS_NOTIFICA: string = "pagamenti/piano-ins-notifica/"
    public static PAGAMENTI_PIANO_VIEW_NOTIFICA: string = "pagamenti/piano-view-notifica/"

    //RICONCILIA VERBALE
    public static PAGAMENTI_RICONCILIA_VERBALE_RICERCA: string = "pagamenti/riconcilia-verbale-ricerca";
    public static PAGAMENTI_RICONCILIA_VERBALE_RIEPILOGO: string = "pagamenti/riconcilia-verbale-riepilogo/"; //+id
    public static PAGAMENTI_RICONCILIA_VERBALE_ALLEGATO_PAGAMENTO: string = "pagamenti/riconcilia-verbale-allegato/"; //+id

    //RICONCILIA ORDINANZA
    public static PAGAMENTI_RICONCILIA_ORDINANZA_RICERCA: string = "pagamenti/riconcilia-ordinanza-ricerca";
    public static PAGAMENTI_RICONCILIA_ORDINANZA_RIEPILOGO: string = "pagamenti/riconcilia-ordinanza-riepilogo/"; //+id
    public static PAGAMENTI_RICONCILIA_ORDINANZA_ALLEGATO: string = "pagamenti/riconcilia-ordinanza-allegato/";

    //RICONCILIA PIANO
    public static PAGAMENTI_RICONCILIA_PIANO_RICERCA: string = "pagamenti/riconcilia-piano-ricerca";
    public static PAGAMENTI_RICONCILIA_PIANO_DETTAGLIO: string = "pagamenti/riconcilia-piano-dettaglio/";

    //RICONCILIA SOLLECITO
    public static PAGAMENTI_RICONCILIA_SOLLECITO_RICERCA: string = "pagamenti/riconcilia-sollecito-ricerca";
    public static PAGAMENTI_RICONCILIA_SOLLECITO_DETTAGLIO: string = "pagamenti/riconcilia-sollecito-dettaglio";
    public static PAGAMENTI_RICONCILIA_SOLLECITO_RATE_RICERCA: string = "pagamenti/riconcilia-sollecito-rate-ricerca";
    public static PAGAMENTI_RICONCILIA_SOLLECITO_RATE_DETTAGLIO: string = "pagamenti/riconcilia-sollecito-rate-dettaglio";
    public static PAGAMENTI_RICONCILIA_MANUALE_PAGAMENTI_ACCONTI_ORDINANZA: string = "pagamenti/pagamenti-riconcilia-manuale-pagamento-ordinanza";
    public static PAGAMENTI_RICONCILIA_MANUALE_PAGAMENTI_ACCONTI_ORDINANZA_DETTAGLIO: string = "pagamenti/pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio/";//+id

    // FASCICOLO
    public static FASCICOLO_ALLEGATO_DA_ACTA: string = "fascicolo/allegato-da-acta/"; //+id

}
