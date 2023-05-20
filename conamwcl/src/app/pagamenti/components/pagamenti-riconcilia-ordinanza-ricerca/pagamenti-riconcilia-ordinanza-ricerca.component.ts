import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'pagamenti-riconcilia-ordinanza-ricerca',
    templateUrl: './pagamenti-riconcilia-ordinanza-ricerca.component.html'
})
export class PagamentiRiconciliaRicercaOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public showTable: boolean;

    public config: Config;
    public ordinanze: Array<OrdinanzaVO>;
    public ordinanzaSel: OrdinanzaVO;
    public loaded: boolean = true;

    public loadedStatiOrdinanza: boolean;
    public statiOrdinanzaModel: Array<StatoOrdinanzaVO>;

    public request: RicercaOrdinanzaRequest

    constructor(
        private logger: LoggerService,
        private router: Router,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaRicercaOrdinanzaComponent.name);
        this.config = this.configSharedService.configRicercaOrdinanza;
        this.loadStatiOrdinanza();
    }

    loadStatiOrdinanza(){
        this.loadedStatiOrdinanza = false;
        this.sharedOrdinanzaService.getStatiOrdinanza().subscribe( data => {
            if(data!=null)
                this.statiOrdinanzaModel = data.filter(a => (a.id != Constants.STATO_ORDINANZA_RISCOSSA_CON_SORIS && a.id != Constants.STATO_ORDINANZA_ATTESA_NOTIFICA));
            this.loadedStatiOrdinanza = true;
        })
    }

    scrollEnable: boolean;
    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest) {
        this.request = ricercaOrdinanzaRequest;
        this.showTable = false;
        this.loaded = false;
        ricercaOrdinanzaRequest.ordinanzaProtocollata = true;
        this.request.statoManualeDiCompetenza  = true;
        this.sharedOrdinanzaService.ricercaOrdinanza(ricercaOrdinanzaRequest).subscribe(
            data => {
                this.resetMessageTop2();
                if (data != null)
                    this.ordinanze = data;
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessage(err);
                }
                this.logger.error("Errore nella ricerca dell'ordinanza");
            }
        );
    }

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    manageMessage(err: ExceptionVO) {
        this.typeMessageTop = err.type;
        this.messageTop = err.message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 30;
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

    onDettaglio(el: any | Array<any>) {
        this.ordinanzaSel = el;
        if (el instanceof Array)
            throw new Error("errore sono stati selezionati più elementi");
        else
            this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_ORDINANZA_RIEPILOGO + el.id);
    }

    messaggio(message: string){
        this.manageMessageTop2(message,"DANGER");
    }

    //Messaggio top
    public showMessageTop2: boolean;
    public typeMessageTop2: String;
    public messageTop2: String;
    private intervalIdS2: number = 0;

    manageMessageTop2(message: string, type: string) {
        this.typeMessageTop2 = type;
        this.messageTop2 = message;
        this.timerShowMessageTop2();
        this.scrollTopEnable2 = true;
    }

    timerShowMessageTop2() {
        this.showMessageTop2 = true;
        let seconds: number = 10;
        this.intervalIdS2 = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop2();
            }
        }, 1000);
    }

    resetMessageTop2() {
        this.showMessageTop2 = false;
        this.typeMessageTop2 = null;
        this.messageTop2 = null;
        clearInterval(this.intervalIdS2);
    }

    scrollTopEnable2: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable2 && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable2 = false;
        }
    }


    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaRicercaOrdinanzaComponent.name);
    }

}