import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaAllegatoProtocollatoVerbaleRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idVerbale: number;
    constructor(idVerbale:number){
        super();
        this.idVerbale = idVerbale;
    }
}