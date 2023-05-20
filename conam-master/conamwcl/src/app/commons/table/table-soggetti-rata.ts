import { CurrencyPipe } from "@angular/common";
import { RataVO } from "../vo/piano-rateizzazione/rata-vo";
import { StatoRataVO } from "../vo/select-vo";

export class TableSoggettiRata {

    public dataPagamento: string;
    public dataScadenza: string;
    public importoRata: string;
    public numeroRata: number;
    public stato: StatoRataVO;
    public identificativoSoggetto: string;
    public importoPagato: string;
    public iuv: string;
    public dataFineValidita: string;
    public codiceAvviso: string;
    public isRiconciliaEnable: boolean;
    public idOrdinanzaVerbaleSoggetto: number;
    public idRata: number;

    public static map(value: RataVO): TableSoggettiRata {
        let tableSoggetti = new TableSoggettiRata();

        tableSoggetti.numeroRata = value.numeroRata;
        tableSoggetti.stato = value.stato;
        let currencyPipe = new CurrencyPipe('it-IT');
        tableSoggetti.importoRata = currencyPipe.transform(value.importoRata, 'EUR', 'symbol');
        tableSoggetti.importoPagato = currencyPipe.transform(value.importoPagato, 'EUR', 'symbol');
        tableSoggetti.dataPagamento = value.dataPagamento;
        tableSoggetti.dataScadenza = value.dataScadenza;
        tableSoggetti.iuv = value.iuv;
        tableSoggetti.dataFineValidita = value.dataFineValidita;
        tableSoggetti.codiceAvviso = value.codiceAvviso;
        tableSoggetti.identificativoSoggetto = value.identificativoSoggetto;
        tableSoggetti.isRiconciliaEnable = value.isRiconciliaEnable;
        tableSoggetti.idOrdinanzaVerbaleSoggetto = value.idOrdinanzaVerbaleSoggetto;
        tableSoggetti.idRata = value.idRata;
        return tableSoggetti;
    }
}