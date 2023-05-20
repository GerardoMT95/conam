import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaAllegatoProtocollatoOrdinanzaRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idOrdinanza: number;
    constructor(idOrdinanza: number){
        super();
        this.idOrdinanza = idOrdinanza;
    }
}