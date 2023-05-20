import { NotificaVO } from "../../vo/notifica/notifica-vo";
import { PianoRateizzazioneVO } from "../../vo/piano-rateizzazione/piano-rateizzazione-vo";
import { SollecitoVO } from "../../vo/riscossione/sollecito-vo";
import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaSollecitoPregressiRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idVerbale: number;
    public idOrdinanza: number;
    public idSollecito:number;
    public sollecito: SollecitoVO;
    public notifica: NotificaVO;
    constructor(id:number,idOrdinanza:number){
        super();
        this.idVerbale = id;
        this.idOrdinanza = idOrdinanza;
    }
}

