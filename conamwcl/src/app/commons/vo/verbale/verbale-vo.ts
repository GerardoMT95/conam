import { RiferimentiNormativiVO } from "./riferimenti-normativi-vo";
import { EnteVO, StatoVerbaleVO, ComuneVO, ProvinciaVO, RegioneVO, IstruttoreVO } from "../../../commons/vo/select-vo";

export class VerbaleVO {

    constructor() {
        if (!this.stato) this.stato = new StatoVerbaleVO();
        //if (!this.regione) this.regione = new RegioneVO();
        //if (!this.provincia) this.provincia = new ProvinciaVO();
        //if (!this.comune) this.comune = new ComuneVO();
        if (!this.riferimentiNormativi) this.riferimentiNormativi = new Array<RiferimentiNormativiVO>();
    }

    public numeroProtocollo: string;
    public numero: string;
    public dataOraViolazione: string;
    public enteAccertatore: EnteVO;
    public dataOraAccertamento: string;
    public comuneEnte: number;
    public importo: number;
    public importoResiduo: number;
    public stato: StatoVerbaleVO;
    public regione: RegioneVO;
    public provincia: ProvinciaVO;
    public comune: ComuneVO;
    public indirizzo: string;
    public contestato: boolean;
    public riferimentiNormativi: Array<RiferimentiNormativiVO>;
    public enteRiferimentiNormativi: EnteVO;
    public idVerbale: number;
    public id: number;
    public istruttoreAssegnato: IstruttoreVO;
    public istruttoreCreatore: IstruttoreVO;
}