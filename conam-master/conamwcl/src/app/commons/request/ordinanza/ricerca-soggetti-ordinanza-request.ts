import { StatoSentenzaVO } from "../../vo/select-vo";

export class RicercaSoggettiOrdinanzaRequest {

    public numeroDeterminazione: string;
    public numeroProtocolloSentenza: string;
    public ordinanzaProtocollata: boolean;
    public ordinanzeSollecitate: boolean;
    public statoSentenza: StatoSentenzaVO;
    public dataSentenzaDa: string;
    public dataSentenzaA: string;
    public dataNotificaDa: string;
    public dataNotificaA: string;
    public tipoRicerca: string;
    public statoManualeDiCompetenza : boolean;
}