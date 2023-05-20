import { SoggettoVO } from "../verbale/soggetto-vo";

export class DatiTemplateVO {

    //intestazione
    public numeroProtocollo: string;
    public classificazione: string;
    public fn: string;

    //body
    public listaSoggetti: Array<SoggettoVO>;
    public importoTotale: number;
    public numeroRate: number;
    public scadenzaDefinita: boolean;
    public importoPrimaRata: number;
    public scadenzaPrimaRata: string;
    public importoAltreRate: number;
    public importoUltimaRata: number;
    public dirigente: string;
    public funzionario: string;
    public mailSettoreTributi: string;

    public nome: string;

    // in caso di annullamento
    public infoOrganoAccertatore? : string;
    public direzione?: string;
    public settore?: string;
    public oggettoLettera?: string;
    public corpoLettera?: string;
    public salutiLettera?: string;
    public dirigenteLettera?: string;
    public inizialiLettera?: string;
    public sedeEnte?: string;

}