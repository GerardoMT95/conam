import { RicercaSoggettiOrdinanzaRequest } from "../ordinanza/ricerca-soggetti-ordinanza-request";
import { StatoOrdinanzaVO, StatoSentenzaVO } from "../../vo/select-vo";

export class RicercaSoggettiRiscossioneCoattivaRequest extends RicercaSoggettiOrdinanzaRequest {

    public statoOrdinanza: StatoOrdinanzaVO;
    public statoSentenza: StatoSentenzaVO;
    public dataSentenzaDa: string;
    public dataSentenzaA: string;

    constructor() {
        super();
        this.statoOrdinanza = new StatoOrdinanzaVO();
    }
}