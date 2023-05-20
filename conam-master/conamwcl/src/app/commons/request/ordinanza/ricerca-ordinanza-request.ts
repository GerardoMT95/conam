import { SoggettoVerbaleRequest } from "../verbale/soggetto-verbale-request";
import { StatoOrdinanzaVO } from "../../vo/select-vo";

export class RicercaOrdinanzaRequest {

    public numeroDeterminazione: string;
    public numeroVerbale: string;
    public ordinanzaProtocollata: boolean;
    public soggettoVerbale: Array<SoggettoVerbaleRequest>;
    public statoOrdinanza: Array<StatoOrdinanzaVO>;
    public dataOrdinanzaDa: string;
    public dataOrdinanzaA: string;
    public tipoRicerca: string;
    public statoManualeDiCompetenza : boolean;

    constructor() {
        this.soggettoVerbale = new Array<SoggettoVerbaleRequest>();
        this.statoOrdinanza = new Array<StatoOrdinanzaVO>();
    }
}