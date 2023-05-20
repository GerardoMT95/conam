import { StatoSollecitoVO } from "../select-vo";

export class SollecitoVO {
    public idSollecito: number;
    public idSoggettoOrdinanza: number;
    public numeroProtocollo: string;
    public dataScadenza: string;
    public importoSollecitato: number;
    public maggiorazione: number;
    public statoSollecito: StatoSollecitoVO;
    public bollettinoDaCreare: boolean;
    public downloadBollettiniEnable: boolean;
    public isNotificaCreata: boolean;
    public importoPagato: number;
    public dataPagamento: string;
    public isRiconciliaEnable: boolean;
}