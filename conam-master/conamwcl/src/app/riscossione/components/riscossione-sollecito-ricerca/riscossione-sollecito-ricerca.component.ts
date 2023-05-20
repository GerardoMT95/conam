import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { RiscossioneService } from "../../services/riscossione.service";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";

@Component({
    selector: 'riscossione-sollecito-ricerca',
    templateUrl: './riscossione-sollecito-ricerca.component.html'
})
export class RiscossioneSollecitoRicercaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
    public loaded: boolean = true;
    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public config: Config;
    public showTable: boolean;

    public request: RicercaSoggettiOrdinanzaRequest = new RicercaSoggettiOrdinanzaRequest();
    public max: boolean;

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

    /*showDetail: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return !el.pianoRateizzazioneCreato;
    }*/

    constructor(
        private logger: LoggerService,
        private router: Router,
        private riscossioneService: RiscossioneService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(RiscossioneSollecitoRicercaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(true, "Dettaglio", 0, this.isSelectable, null,null);
    }

    ricerca(request: RicercaSoggettiOrdinanzaRequest) {
        this.loaded = false;
        this.showTable = false;
        request.ordinanzaProtocollata = true;
        this.request.statoManualeDiCompetenza = true;
        request.tipoRicerca = "RICERCA_SOLLECITO";
        this.subscribers.ricerca = this.sharedOrdinanzaService.ricercaSoggettiOrdinanza(request).subscribe(data => {
            this.resetMessageTop();
            if (data != null) {

                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo; 
                    });
                }                

                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                    this.soggetti = new Array<TableSoggettiOrdinanza>();
                } else{
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    });
                }          
            }
            if(request.dataNotificaA == null && !this.max && this.soggetti.length==0 && request.numeroDeterminazione!=null){
                this.manageMessageTop(" Non ci sono ordinanze per le quali creare un sollecito di pagamento. Consultare l'ordinanza ricercata tramite voce 'Gestione contenzioso amministrativo/Ricerca ordinanza'","WARNING");
            }  
            if(request.dataNotificaA != null && !this.max && this.soggetti.length==0){
                this.manageMessageTop("nessuna ordinanza Notificata nell'intervallo data di notifica ricercato","WARNING");
            } 
            this.request = request;
            this.loaded = true;
            this.showTable = true; 
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

    onDettaglio(event: TableSoggettiOrdinanza) {
        this.riscossioneService.soggettoSollecito = event;
        this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_DETTAGLIO);
    }

    ngOnDestroy(): void {
        this.logger.destroy(RiscossioneSollecitoRicercaComponent.name);
    }
}