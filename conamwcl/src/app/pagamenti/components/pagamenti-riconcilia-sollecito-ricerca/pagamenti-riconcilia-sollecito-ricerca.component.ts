import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";

@Component({
    selector: 'pagamenti-riconcilia-sollecito-ricerca',
    templateUrl: './pagamenti-riconcilia-sollecito-ricerca.component.html'
})
export class PagamentiRiconciliaSollecitoRicercaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
    public loaded: boolean = true;
    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public config: Config;
    public showTable: boolean;

    public request: RicercaSoggettiOrdinanzaRequest = new RicercaSoggettiOrdinanzaRequest();

    /*isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO &&
            el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_RICORSO_ACCOLTO
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE;
    }*/

    constructor(
        private logger: LoggerService,
        private router: Router,
        private pagamentiUtilService: PagamentiUtilService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaSollecitoRicercaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(true, "Dettaglio Sollecito", 0,(el:any)=>true, null,null);
    }

    ricerca(request: RicercaSoggettiOrdinanzaRequest) {
        this.loaded = false;
        this.showTable = false;
        request.ordinanzeSollecitate = true;
        request.statoManualeDiCompetenza  = true;
        request.tipoRicerca = "RICERCA_SOLLECITO";
        this.subscribers.ricerca = this.sharedOrdinanzaService.ricercaSoggettiOrdinanza(request).subscribe(data => {
            this.resetMessageTop();
            this.request = request;
            this.loaded = true;
            this.showTable = true;
            if (data != null) {
                this.soggetti = data.map(value => {
                    return TableSoggettiOrdinanza.map(value);
                });
            }
        });
    }

    onDettaglio(event: TableSoggettiOrdinanza) {
        this.pagamentiUtilService.soggettoSollecito = event;
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_DETTAGLIO);
    }

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
        this.logger.destroy(PagamentiRiconciliaSollecitoRicercaComponent.name);
    }
}