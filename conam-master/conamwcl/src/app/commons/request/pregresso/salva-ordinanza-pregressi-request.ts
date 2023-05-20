import { NotificaVO } from "../../vo/notifica/notifica-vo";
import { OrdinanzaVO } from "../../vo/ordinanza/ordinanza-vo";
import { SoggettoOrdinanzaRequest } from "../ordinanza/salva-ordinanza-request";
import { SalvaAllegatoProtocollatoRequestCommon } from "../salva-allegato-protocollato-request-common";

export class SalvaOrdinanzaPregressiRequest extends SalvaAllegatoProtocollatoRequestCommon {
    public idOrdinanza: number;
    public ordinanza: OrdinanzaVO;
    public soggetti: Array<SoggettoOrdinanzaRequest>;
    public notifica: NotificaVO;
    constructor(idOrdinanza: number){
        super();
        this.idOrdinanza = idOrdinanza;
    }
}

