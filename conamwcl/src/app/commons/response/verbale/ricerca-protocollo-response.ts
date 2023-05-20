import { MessageVO } from "../../vo/messageVO";
import { DocumentoProtocollatoVO } from "../../vo/verbale/documento-protocollato-vo";

export class RicercaProtocolloResponse{

    public messaggio: MessageVO;
    public documentoProtocollatoVOList: Array<DocumentoProtocollatoVO>;

    constructor(){
    }
}