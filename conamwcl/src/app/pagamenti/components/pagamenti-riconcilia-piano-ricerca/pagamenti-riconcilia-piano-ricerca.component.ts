import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { Config } from "protractor";
import { PagamentiService } from "../../services/pagamenti.service";
import { TablePianoRateizzazione } from "../../../commons/table/table-piano-rateizzazione";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'pagamenti-riconcilia-piano-ricerca',
    templateUrl: './pagamenti-riconcilia-piano-ricerca.component.html'
})
export class PagamentiRiconciliaPianoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean = true;
    public showTable: boolean = false;
    public pianiRateizzazione: Array<TablePianoRateizzazione> = new Array<TablePianoRateizzazione>();

    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();

    constructor(
        private logger: LoggerService,
        private router: Router,
        private pagamentiService: PagamentiService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaPianoComponent.name);
    }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        this.request.statoManualeDiCompetenza  = true;
        request.tipoRicerca = "RICONCILIA_PIANO";
        if(this.request.statoPiano==null){
            this.request.statoPiano = new Array(Constants.STATOPIANOVO_NON_NOTIFICATO, Constants.STATOPIANOVO_NOTIFICATO);
        }
        this.pagamentiService.ricercaPiani(request).subscribe(data => {
            this.resetMessageTop();
            this.loaded = true;
            this.showTable = true;
            this.request = request;
            if (data != null) {
                this.pianiRateizzazione = data.map(value => {
                    return TablePianoRateizzazione.map(value);
                });
            }
        });

    }

    dettaglioPiano(event: TablePianoRateizzazione) {
        if (event instanceof Array)
            throw new Error("Errore sono stati selezionati più elementi");
        else
            this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_PIANO_DETTAGLIO + event.id);
    }

    public config: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Aggiungi pagamento al piano",
            enable: true,
        },
        columns: [
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Codice Fiscale / Partita iva'
            },
            {
                columnName: 'stato.denominazione',
                displayName: 'Stato'
            },
            {
                columnName: 'saldo',
                displayName: 'Importo Totale'
            },
            {
                columnName: 'dataCreazione',
                displayName: 'Data Creazione'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo Piano'
            }
        ]
    };

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaPianoComponent.name);
    }

}