import { StatoOrdinanzaVO, TipoOrdinanzaVO } from "../../../commons/vo/select-vo";

export class OrdinanzaVO {

    constructor() {
        if (!this.stato) this.stato = new StatoOrdinanzaVO();
        if (!this.tipo) this.tipo = new TipoOrdinanzaVO();
    }

    public id: number;
    public tipo: TipoOrdinanzaVO;
    public dataOrdinanza: string;
    public stato: StatoOrdinanzaVO;
    public numeroProtocollo: string;
    public dataProtocollo: string;
    public numDeterminazione: string;
    public dataDeterminazione: string;
    public dataFineValidita: string;
    public importoOrdinanza: string;
    public dataScadenza: string;
    public superatoIlMassimo: boolean;
    public dettaglioOrdinanzaAnnullata: string;
    public listaAcconti: any;

}