import { NormaVO, ArticoloVO, CommaVO, LetteraVO, AmbitoVO } from "../../../commons/vo/select-vo";

export class RiferimentiNormativiVO {

    public id: number;
    public ambito: AmbitoVO;
    public norma: NormaVO;
    public articolo: ArticoloVO;
    public comma: CommaVO;
    public lettera: LetteraVO;
}