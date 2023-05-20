import { StatoPianoVO } from "../vo/select-vo";

export class Constants {
  public static COD_INCOMPLETO: number = 1;

  //costanti per la decodifica dei valori dei metadati degli allegati
  public static FT_NUMERIC: number = 1;
  public static FT_STRING: number = 2;
  public static FT_DATE: number = 3;
  public static FT_BOOLEAN: number = 4;
  public static FT_ELENCO: number = 5;
  public static FT_DATA_ORA: number = 6;
  public static FT_ELENCO_SOGG: number = 7;

  //costanti per lo stato del soggetto legato ad un'ordinanza
  public static STATO_ORDINANZA_SOGGETTO_INGIUNZIONE: number = 1;
  public static STATO_ORDINANZA_SOGGETTO_ARCHIVIATO: number = 2;
  public static STATO_ORDINANZA_SOGGETTO_RICORSO_ACCOLTO: number = 3;
  public static STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE: number = 7;
  public static STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE: number = 8;

  //costanti per lo stato dell'ordinanza
  public static STATO_ORDINANZA_ATTESA_NOTIFICA: number = 1;
  public static STATO_ORDINANZA_NOTIFICATA: number = 2;
  public static STATO_ORDINANZA_NON_NOTIFICA: number = 3;
  public static STATO_ORDINANZA_RICORSO_IN_ATTO: number = 4;
  public static STATO_ORDINANZA_PAGATA: number = 5;
  public static STATO_ORDINANZA_RISCOSSA_CON_SORIS: number = 6;

  //costanti per gli elenchi di tipologie di allegato
  public static ID_ELENCO_ESITO_SENTENZA: number = 2;

  //costanti per i parametri di input delle varie getTipologiaAllegato
  public static RAPPORTO_TRASMISSIONE: string = "RAPTRASM";
  public static VERBALE_ACCERTAMENTO: string = "VERBACC";
  public static VERBALE_SEQUESTRO: string = "VERBSEQ";
  public static RILIEVI_SEGNALETICI: string = "RILSEGN";
  public static RELATA_NOTIFICA: string = "RELNOT";
  public static SCRITTI_DIFENSIVI: string = "SCRITDIF";
  public static RICEVUTA_PAGAMENTO_VERBALE: string = "RICPAGVERB";
  public static ISTANZA_AUTOTUTELA: string = "ISTAUTO";
  public static CONTRODEDUZIONE: string = "CONTRDED";
  public static COMPARSA: string = "COMPRS";
  public static VERBALE_AUDIZIONE: string = "VERBAUDI";
  public static CONVOCAZIONE_AUDIZIONE: string = "CONVAU";
  public static ORDINANZA_ARCHIVIAZIONE: string = "ORDARCH";
  public static ORDINANZA_INGIUNZIONE_PAGAMENTO: string = "ORDING";
  public static OPPOSIZIONE_GIURISDIZIONALE: string = "OPPGIUR";
  public static DISPOSIZIONE_DEL_GIUDICE: string = "DISPGIUD";
  public static VERBALE_DI_CONTESTAZIONE: string = "VERBCOST";
  public static VERBALE_DI_CONFISCA: string = "VERBCONF";
  public static LETTERA_RATEIZZAZIONE: string = "LETRAT";
  public static BOLLETTINI_RATEIZZAZIONE: string = "BOLRAT";
  public static LETTERA_ORDINANZA: string = "LETORD";
  public static RICEVUTA_PAGAMENTO_ORDINANZA: string = "RICPAGORD";
  public static COMUNICAZIONI_DALLA_CANCELLERIA: string = "COMDACANC";
  public static COMUNICAZIONI_ALLA_CANCELLERIA: string = "COMACANC";
  public static ISTANZA_RATEIZZAZIONE: string = "ISTRAT";

  //costanti per l'acquisione del fascicolo
  public static ID_AZIONE_ACQUISISCI: number = 1; //
  public static ID_AZIONE_ACQUISISCI_CON_PAGAMENTO: number = 2;
  public static ID_AZIONE_ACQUISISCI_CON_SCRITTI: number = 3;
  public static ID_AZIONE_ARCHIVIATO_IN_AUTOTUTELA: number = 4;
    public static ID_AZIONE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO: number = 5;
    public static ID_AZIONE_IN_ATTESA_VERIFICA_PAGAMENTO: number = 6;
  

  //costanti per lo stato del sollecito
  public static SOLLECITO_BOZZA: number = 1;
  public static SOLLECITO_LETTERA_COMPLETATA: number = 2;

  //costanti per lo stato del piano
  public static PIANO_IN_DEFINIZIONE: number = 1;
  public static PIANO_CONSOLIDATO: number = 2;
  public static PIANO_PROTOCOLLATO: number = 3;
  public static PIANO_NON_NOTIFICATO: number = 4;
  public static PIANO_NOTIFICATO: number = 5;
  public static PIANO_ESTINTO: number = 6;

  //stato piano VO
  public static STATOPIANOVO_PROTOCOLLATO: StatoPianoVO = {
    id: Constants.PIANO_PROTOCOLLATO,
    denominazione: "Protocollato",
  };
  public static STATOPIANOVO_NON_NOTIFICATO: StatoPianoVO = {
    id: Constants.PIANO_NON_NOTIFICATO,
    denominazione: "Non notificato",
  };
  public static STATOPIANOVO_NOTIFICATO: StatoPianoVO = {
    id: Constants.PIANO_NOTIFICATO,
    denominazione: "Notificato",
  };
  public static STATOPIANOVO_IN_DEFINIZIONE: StatoPianoVO = {
    id: Constants.PIANO_IN_DEFINIZIONE,
    denominazione: "In definizione",
  };
  public static STATOPIANOVO_CONSOLIDATO: StatoPianoVO = {
    id: Constants.PIANO_CONSOLIDATO,
    denominazione: "Consolidato",
  };
  public static STATOPIANOVO_ESTINTO: StatoPianoVO = {
    id: Constants.PIANO_ESTINTO,
    denominazione: "Estinto",
  };

  //costanti per i template
  //public static TEMPLATE_: number = 1;
  public static TEMPLATE_VERBALE_AUDIZIONE: number = 2;
  public static TEMPLATE_LETTERA_ACCOMPAGNAMENTO: number = 3;
  public static TEMPLATE_RATEIZZAZIONI: number = 4;
  public static TEMPLATE_SOLLECITO_PAGAMENTO: number = 5;
  public static TEMPLATE_CONVOCAZIONE_AUDIZIONE: number = 6;
  public static TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO: number = 9;
  public static TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE: number = 10;
  public static TEMPLATE_SOLLECITO_PAGAMENTO_RATE: number = 11;

  //costanti stato del verbale
  public static ID_STATO_VERBALE_ACQUISITO: number = 2;
  public static ID_STATO_VERBALE_IN_SCRITTI_DIFENSIVI: number = 4;
  public static ID_STATO_VERBALE_ORDINANZA: number = 7;

  //tipo allegato
  public static ID_TIPO_ALLEGATO_ARCHIVIAZIONE: number = 11;
  public static ID_TIPO_ALLEGATO_INGIUNZIONE: number = 12;
  public static ID_TIPO_ALLEGATO_ARCHIVIAZIONE_ANNULLAMENTO: number = 34;
  public static ID_TIPO_ALLEGATO_INGIUNZIONE_ANNULLAMENTO: number = 35;

  //id stato riscossione
  public static ID_STATO_RISCOSSIONE_BOZZA: number = 1;

  public static ID_ELEMENTO_ELENCO_RICORSO_ACCOLTO: number = 5;
}

export class ComboVO {
  constructor(public codice: string, public denominazione: string) {}
}

export class ComboForm {
  public static SESSO: Array<ComboVO> = [
    new ComboVO("M", "M"),
    new ComboVO("F", "F"),
  ];
}
