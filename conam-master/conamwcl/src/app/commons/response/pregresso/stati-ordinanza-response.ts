import { MessageVO } from "../../vo/messageVO";
import { StatoOrdinanzaVO } from "../../vo/select-vo";

export class StatiOrdinanzaPregressiResponse{

    public messaggio: MessageVO;
    public stati: Array<StatoOrdinanzaVO>;

    constructor(){
    }
}