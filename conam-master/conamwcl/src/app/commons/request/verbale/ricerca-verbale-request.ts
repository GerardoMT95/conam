import { DatiVerbaleRequest } from "./dati-verbale-request";
import { SoggettoVerbaleRequest } from "./soggetto-verbale-request";

export class RicercaVerbaleRequest{
    
    public datiVerbale: DatiVerbaleRequest;
    public soggettoVerbale: Array<SoggettoVerbaleRequest>;
    public statoManualeDiCompetenza : boolean;
    
    constructor(){
        this.datiVerbale=new DatiVerbaleRequest();
        this.soggettoVerbale=new Array<SoggettoVerbaleRequest>();
    }
}