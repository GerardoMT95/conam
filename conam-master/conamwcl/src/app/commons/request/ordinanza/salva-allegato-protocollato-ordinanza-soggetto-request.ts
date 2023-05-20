import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idOrdinanzaVerbaleSoggetto: Array<number>;
    constructor(idOrdinanzaVerbaleSoggetto:Array<number>){
        super();
        this.idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
    }
}