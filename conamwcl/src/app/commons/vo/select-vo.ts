import { MessageVO } from "./messageVO";

export class SelectVO {

    public id: number;
    public denominazione: string;
   
}

export class modalitaCaricamentoVO extends SelectVO { }
export class EnteVO extends SelectVO { }
export class RegioneVO extends SelectVO { }
export class ProvinciaVO extends SelectVO { 
    public sigla: string;
}
export class ComuneVO extends SelectVO { }
export class NazioneVO extends SelectVO { }

export class StatoVerbaleVO extends SelectVO { 
    public confirmMessage: MessageVO;
    public warningMessage: MessageVO;
}
export class StatoOrdinanzaVO extends SelectVO { }
export class StatoSentenzaVO extends SelectVO { }
export class StatoSoggettoOrdinanzaVO extends SelectVO { }
export class StatoPianoVO extends SelectVO { }
export class StatoRataVO extends SelectVO { }
export class StatoSollecitoVO extends SelectVO { }

export class RuoloVO extends SelectVO { }
export class TipoAllegatoVO extends SelectVO { }
export class FieldTypeVO extends SelectVO { }
export class TipoOrdinanzaVO extends SelectVO { }
export class ModalitaNotificaVO extends SelectVO { }
export class StatoRiscossioneVO extends SelectVO { }

export class IstruttoreVO extends SelectVO {
    public ente: EnteVO;
}

export class AzioneVO extends SelectVO {
    public listaIstruttoriEnable: boolean;
    public confirmMessage: MessageVO;
    public warningMessage: MessageVO;
}

export class AmbitoVO extends SelectVO {
    public acronimo: string;
}

export class NormaVO extends SelectVO {
    public dataInizioValidita: string; 
    public dataFineValidita: string;
}

export class ArticoloVO extends SelectVO {
    public dataInizioValidita: string;
    public dataFineValidita: string;
}

export class CommaVO extends SelectVO {
    public dataInizioValidita: string;
    public dataFineValidita: string;
}

export class LetteraVO extends SelectVO {
    public sanzioneMinima: number;
    public sanzioneMassima: number;
    public importoMisuraRidotta: number;
    public scadenzaImporti: string;
    public descrizioneIllecito: string;
    public dataInizioValidita: string;
    public dataFineValidita: string;
}