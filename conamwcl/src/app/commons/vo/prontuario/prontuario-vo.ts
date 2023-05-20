import { EnteVO, AmbitoVO, LetteraVO, CommaVO, ArticoloVO, NormaVO } from "../select-vo";

export class ProntuarioVO {

    public enteLegge: EnteVO;
    public ambito: AmbitoVO;
    public norma: NormaVO;
    public articolo: ArticoloVO;
    public comma: CommaVO;
    public lettera: LetteraVO;
    public eliminaEnable: boolean;

    constructor() {
        //if (!this.enteLegge) this.enteLegge = new EnteVO();
        //if (!this.ambito) this.ambito = new AmbitoVO();
        if (!this.norma) this.norma = new NormaVO();
        if (!this.articolo) this.articolo = new ArticoloVO();
        if (!this.comma) this.comma = new CommaVO();
        if (!this.lettera) this.lettera = new LetteraVO();
    }
}