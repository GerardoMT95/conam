import { SoggettoVO } from "../verbale/soggetto-vo";
import { StatoOrdinanzaVO, StatoRiscossioneVO } from "../select-vo";

export class SoggettiRiscossioneCoattivaVO extends SoggettoVO {

    public numeroDeterminazione: string;
    public statoOrdinanza: StatoOrdinanzaVO;
    public saldo: number;
    public dataNotifica: string;
    public importoPianoResponse: ImportoResponse;
    public importoSollecitoResponse: ImportoResponse;
    public importoOrdinanzaResponse: ImportoResponse;

    public importoSanzione: number = 0;
    public importoSpeseNotifica: number = 0;
    public importoSpeseLegali: number = 0;
    public statoRiscossione: StatoRiscossioneVO;
    public idRiscossione: number;
    public dataDecorrenzaInteressi: string;
}

export class ImportoResponse {
    public importoDaPagare: number;
    public importoPagato: number;
}