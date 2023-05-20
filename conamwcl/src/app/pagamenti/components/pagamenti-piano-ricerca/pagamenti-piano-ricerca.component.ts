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
    selector: 'pagamenti-piano-ricerca',
    templateUrl: './pagamenti-piano-ricerca.component.html',
    styleUrls: ['./pagamenti-piano-ricerca.component.scss']
})
export class PagamentiPianoRicercaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean = true;
    public showTable: boolean = false;
    public pianiRateizzazione: Array<TablePianoRateizzazione> = new Array<TablePianoRateizzazione>();

    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();

    public max: boolean = false;
    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;


    constructor(
        private logger: LoggerService,
        private router: Router,
        private pagamentiService: PagamentiService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiPianoRicercaComponent.name);
    }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        if(this.request.statoPiano==null){
            this.request.statoPiano = new Array(Constants.STATOPIANOVO_NON_NOTIFICATO, Constants.STATOPIANOVO_NOTIFICATO, Constants.STATOPIANOVO_PROTOCOLLATO, Constants.STATOPIANOVO_CONSOLIDATO,Constants.STATOPIANOVO_IN_DEFINIZIONE,Constants.STATOPIANOVO_ESTINTO);
        }
        request.tipoRicerca = "RICERCA_PIANO";
        request.statoManualeDiCompetenza  = true;
        this.pagamentiService.ricercaPiani(request).subscribe(data => {
            this.resetMessageTop();
            this.loaded = true;
            this.showTable = true;
            this.request = request;
            if (data != null) {

                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo;  
                    });
                }   

                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                    this.pianiRateizzazione = new Array<TablePianoRateizzazione>();
                } else{
                    this.pianiRateizzazione = data.map(value => {
                        return TablePianoRateizzazione.map(value);
                    });
                }
            } else {
                this.pianiRateizzazione = new Array<TablePianoRateizzazione>();
            }
        });

    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.scrollTopEnable = true;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
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
        clearInterval(this.intervalTop);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    dettaglioPiano(event: TablePianoRateizzazione) {
        //GESTIRE STATO PER ANDARE A MODIFICA O VIEW
        if (event.stato.id == Constants.PIANO_IN_DEFINIZIONE)
            this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_MODIFICA + event.id);
        else
            this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_VIEW + event.id);
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
            label: "Dettaglio Piano",
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

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiPianoRicercaComponent.name);
    }

}