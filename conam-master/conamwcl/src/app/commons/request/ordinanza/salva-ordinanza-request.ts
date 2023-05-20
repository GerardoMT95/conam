import { SalvaAllegatoRequest } from "../salva-allegato-request";
import { OrdinanzaVO } from "../../vo/ordinanza/ordinanza-vo";
import { NotificaVO } from "../../vo/notifica/notifica-vo";

export class SalvaOrdinanzaRequest extends SalvaAllegatoRequest {

    public ordinanza: OrdinanzaVO;
    public soggetti: Array<SoggettoOrdinanzaRequest>;
    public notifica: NotificaVO;
}

export class SoggettoOrdinanzaRequest {
    public idVerbaleSoggetto: number;
    public idTipoOrdinanza: number;
}