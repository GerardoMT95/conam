import { MinPianoRateizzazioneVO } from "../vo/piano-rateizzazione/min-piano-rateizzazione-vo";
import { CurrencyPipe } from "@angular/common";

export class TablePianoRateizzazione extends MinPianoRateizzazioneVO {
    public identificativoSoggetto: string;

    public static map(value: MinPianoRateizzazioneVO): any {
        let tablePianoRat = new TablePianoRateizzazione();
        tablePianoRat.id = value.id;
        tablePianoRat.stato = value.stato;
        let identificativosoggetti: string = "";
        let delimeter = " - ";
        if (value.soggetti != null) {
            for (let i = 0; i < value.soggetti.length; i++) {
                if (identificativosoggetti != "") identificativosoggetti = identificativosoggetti + delimeter;
                if (value.soggetti[i].personaFisica) {
                    identificativosoggetti = identificativosoggetti + value.soggetti[i].codiceFiscale;
                }
                else {
                    identificativosoggetti = identificativosoggetti + value.soggetti[i].codiceFiscale;
                }
            }
        }
        tablePianoRat.identificativoSoggetto = identificativosoggetti;
        tablePianoRat.dataCreazione = value.dataCreazione;
        tablePianoRat.numProtocollo = value.numProtocollo;
        let currencyPipe = new CurrencyPipe('it-IT');
        tablePianoRat.saldo = currencyPipe.transform(value.saldo, 'EUR', 'symbol');

        return tablePianoRat;

    }
}