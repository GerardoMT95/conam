import { StatoRataVO } from "../select-vo";

export class RataVO {

    public idRata: number;
    public iuv: string;
    public codiceAvviso: string;
    public dataPagamento: string;
    public dataScadenza: string;
    public importoRata: number;
    public numeroRata: number;
    public stato: StatoRataVO;
    public isEditEnable: boolean;
    public isRiconciliaEnable: boolean;
    public identificativoSoggetto: string;
    public importoPagato: number;
    public idOrdinanzaVerbaleSoggetto: number    
    public dataFineValidita: string;
}