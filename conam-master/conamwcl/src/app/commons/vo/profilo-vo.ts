import { MessageVO } from "./messageVO";
import { EnteVO } from "./select-vo";

export class ProfiloVO {
    public useCase: Array<String>;
    public codiceFiscale: string;
    public nome: string;
    public cognome: string;
    public idGruppo: Number;
    public entiAccertatore: Array<EnteVO>;
    public entiLegge: Array<EnteVO>;
    public rai: string;
    // TODO Controllare
    public homeMessage: MessageVO;
}