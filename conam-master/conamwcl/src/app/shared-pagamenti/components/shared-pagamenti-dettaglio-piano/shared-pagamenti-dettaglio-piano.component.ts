import { Component, OnInit, OnDestroy, Input } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";


@Component({
    selector: 'shared-pagamenti-dettaglio-piano',
    templateUrl: './shared-pagamenti-dettaglio-piano.component.html'
})
export class SharedPagamentiDettaglioPianoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input()
    piano: PianoRateizzazioneVO;

    public importoTotale: number;
    public dataScadenzaPrimaRata: string;

    constructor(
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedPagamentiDettaglioPianoComponent.name);

        this.importoTotale = this.piano.importoSanzione + 
                             this.piano.importoSpeseNotifica + 
                             this.piano.importoSpeseProcessuali +
                             ((this.piano.importoMaggiorazione===undefined||this.piano.importoMaggiorazione===null) ? 0 : this.piano.importoMaggiorazione);
        this.dataScadenzaPrimaRata = this.piano.rate.find(rata => rata.numeroRata == 1).dataScadenza;
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedPagamentiDettaglioPianoComponent.name);
    }

}