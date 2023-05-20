import { PianoRateizzazioneVO } from "../../vo/piano-rateizzazione/piano-rateizzazione-vo";
import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaPianoPregressiRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idVerbale: number;
    public idOrdinanza: number;
    public piano: PianoRateizzazioneVO;
    constructor(id:number,idOrdinanza:number){
        super();
        this.idVerbale = id;
        this.idOrdinanza = idOrdinanza;
    }
}

