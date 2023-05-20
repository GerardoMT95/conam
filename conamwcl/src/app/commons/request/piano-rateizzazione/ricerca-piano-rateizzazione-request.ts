import { StatoPianoVO, StatoOrdinanzaVO, StatoSentenzaVO } from "../../vo/select-vo";


export class RicercaPianoRateizzazioneRequest {

    public numeroDeterminazione: string;
    public statoOrdinanza: StatoOrdinanzaVO;
    public numeroProtocolloSentenza: string;
    public statoSentenza: StatoSentenzaVO;
    public numeroProtocolloPiano: string;
    public statoPiano: Array<StatoPianoVO>;
    public ordinanzaProtocollata: Boolean;
    public dataNotificaDa: string;
    public dataNotificaA: string;
    public dataSentenzaDa: string;
    public dataSentenzaA: string;
    public dataCreazioneDa: string;
    public dataCreazioneA: string;
    public tipoRicerca: string;
    public statoManualeDiCompetenza : boolean;
}