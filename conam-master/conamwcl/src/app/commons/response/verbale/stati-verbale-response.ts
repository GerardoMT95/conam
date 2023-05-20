import { MessageVO } from "../../vo/messageVO";
import { StatoVerbaleVO } from "../../vo/select-vo";

export class StatiVerbaleResponse{

    public messaggio: MessageVO;
    public stati: Array<StatoVerbaleVO>;

    constructor(){
    }
}