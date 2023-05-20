import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { Config } from "protractor";
import { Constants } from "../../../commons/class/constants";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { Routing } from "../../../commons/routing";
import { TablePianoRateizzazione } from "../../../commons/table/table-piano-rateizzazione";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { PagamentiService } from "../../../pagamenti/services/pagamenti.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { RiscossioneService } from "../../services/riscossione.service";

@Component({
    selector: 'ricossione-sollecito-rate-ricerca',
    templateUrl: './ricossione-sollecito-rate-ricerca.component.html',
    styleUrls: ['./ricossione-sollecito-rate-ricerca.component.scss']
})
export class RiscossioneSollecitoRateRicercaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
  
    public config: Config;
    public loaded: boolean = true;
    public showTable: boolean = false;
    public pianiRateizzazione: Array<TablePianoRateizzazione> = new Array<TablePianoRateizzazione>();
    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();

    public max: boolean = false;
    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO &&
            el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_RICORSO_ACCOLTO
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE;
    }
    constructor(
        private logger: LoggerService,
        private router: Router,
        private pagamentiService: PagamentiService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private riscossioneService: RiscossioneService,
    ) { }

    ngOnInit(): void {
        this.logger.init(RiscossioneSollecitoRateRicercaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(true, "Dettaglio", 0, this.isSelectable, null,null);
    }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        this.request.ordinanzaProtocollata = true;
        this.request.statoManualeDiCompetenza = true;
        this.request.tipoRicerca = "RICERCA_SOLLECITO_RATE";
        this.pagamentiService.ricercaSoggettiPiano(request).subscribe(data => {
            this.resetMessageTop();
            this.request = request;
            this.loaded = true;
            this.showTable = true;
            if (data != null) {
                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo;
                    });
                }
                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                }else {
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    });
                }
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

    dettaglioPiano(event: TableSoggettiOrdinanza) {
        this.riscossioneService.soggettoSollecito = event;
        this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_DETTAGLIO_RATE);
    }

   

    ngOnDestroy(): void {
        this.logger.destroy(RiscossioneSollecitoRateRicercaComponent.name);
    }
}