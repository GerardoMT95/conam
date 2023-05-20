import { NormaVO, StatoVerbaleVO, EnteVO, AmbitoVO } from "../../../commons/vo/select-vo";

export class DatiVerbaleRequest {

    public numeroProtocollo: string;
    public numeroVerbale: string;
    public ente: EnteVO;
    public ambito: AmbitoVO;
    public norma: NormaVO;
    public stato: Array<StatoVerbaleVO>;
    public pregresso?:boolean;

}