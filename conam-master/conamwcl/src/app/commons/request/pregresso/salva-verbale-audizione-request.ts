import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaVerbaleAudizioneRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idVerbale: number;
    public idVerbaleSoggettoList: Array<number>;
    constructor(idVerbale:number){
        super();
        this.idVerbale = idVerbale;
    }
}