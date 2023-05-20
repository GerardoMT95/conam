import { StatoSoggettoOrdinanzaVO, StatoOrdinanzaVO, RuoloVO, StatoRiscossioneVO } from "../vo/select-vo";
import { SoggettiRiscossioneCoattivaVO } from "../vo/riscossione/soggetti-riscossione-coattiva-vo";
import { CurrencyPipe } from "@angular/common";

export class TableSoggettiRiscossione {

    public idSoggettoOrdinanza: number;
    public numeroDeterminazione: string;
    public statoOrdinanza: StatoOrdinanzaVO;
    public statoSoggettoOrdinanza: StatoSoggettoOrdinanzaVO; //posizione
    public nomeCognomeRagioneSociale: string;
    public ruolo: RuoloVO;
    public dataNotifica: string;

    public idSoggetto: number;
    public noteSoggetto: string;

    public importoPagatoDaPagarePiano: string;
    public importoPagatoDaPagareOrdinanza: string;
    public importoPagatoDaPagareSollecito: string;

    public importoSanzione: number;
    public importoSpeseNotifica: number;
    public importoSpeseLegali: number;
    public importoDaRiscuotere: string;
    public statoRiscossione: StatoRiscossioneVO;
    public idRiscossione: number;
    public dataDecorrenzaInteressi: string;
    public superatoIlMassimo:boolean;
    public enableInvioMassivo:boolean;
    public verbale: any;

    public static map(value: SoggettiRiscossioneCoattivaVO): TableSoggettiRiscossione {
        let tableSoggetti = new TableSoggettiRiscossione();
        
        tableSoggetti.idSoggettoOrdinanza = value.idSoggettoOrdinanza; 
        tableSoggetti.numeroDeterminazione = value.numeroDeterminazione;
        tableSoggetti.statoOrdinanza = value.statoOrdinanza;
        tableSoggetti.statoSoggettoOrdinanza = value.statoSoggettoOrdinanza;
        tableSoggetti.nomeCognomeRagioneSociale = value.personaFisica ? value.codiceFiscale /*value.nome + " " + value.cognome*/ : value.partitaIva /*value.ragioneSociale*/;
        tableSoggetti.ruolo = value.ruolo;
        tableSoggetti.dataNotifica = value.dataNotifica;
        tableSoggetti.idSoggetto = value.id;
        tableSoggetti.noteSoggetto = value.noteSoggetto;
        let currencyPipe = new CurrencyPipe('it-IT');
        tableSoggetti.importoPagatoDaPagareOrdinanza = currencyPipe.transform(value.importoOrdinanzaResponse.importoPagato, 'EUR', 'symbol') + "/" + currencyPipe.transform(value.importoOrdinanzaResponse.importoDaPagare, 'EUR', 'symbol');
        tableSoggetti.importoPagatoDaPagarePiano = currencyPipe.transform(value.importoPianoResponse.importoPagato, 'EUR', 'symbol') + "/" + currencyPipe.transform(value.importoPianoResponse.importoDaPagare, 'EUR', 'symbol');
        tableSoggetti.importoPagatoDaPagareSollecito = currencyPipe.transform(value.importoSollecitoResponse.importoPagato, 'EUR', 'symbol') + "/" + currencyPipe.transform(value.importoSollecitoResponse.importoDaPagare, 'EUR', 'symbol');
        tableSoggetti.importoSpeseNotifica= value.importoSpeseNotifica;
        tableSoggetti.importoSpeseLegali = value.importoSpeseLegali;
        let importoSan : number = value.importoSanzione != null && value.importoSanzione > 0? value.importoSanzione : value.importoOrdinanzaResponse.importoDaPagare;
        tableSoggetti.importoDaRiscuotere = currencyPipe.transform(importoSan + value.importoSpeseNotifica + value.importoSpeseLegali, 'EUR', 'symbol');
        tableSoggetti.importoSanzione = importoSan;
        tableSoggetti.statoRiscossione = !value.statoRiscossione ? new StatoRiscossioneVO() : value.statoRiscossione;
        tableSoggetti.idRiscossione = value.idRiscossione;
        tableSoggetti.dataDecorrenzaInteressi = value.dataDecorrenzaInteressi;
        tableSoggetti.superatoIlMassimo = value.superatoIlMassimo;
        tableSoggetti.enableInvioMassivo = value.enableInvioMassivo;
        tableSoggetti.verbale = value.verbale;
        return tableSoggetti;
    }
}