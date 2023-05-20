import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { MinVerbaleVO } from "../../../commons/vo/verbale/min-verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

@Component({
    selector: 'pagamenti-riconcilia-verbale-ricerca',
    templateUrl: './pagamenti-riconcilia-verbale-ricerca.component.html',
    styleUrls: ['./pagamenti-riconcilia-verbale-ricerca.component.scss']
})
export class PagamentiRiconciliaVerbaleRicercaComponent implements OnInit, OnDestroy {

    public showTable: boolean;

    public config: Config;
    public verbali: Array<MinVerbaleVO>;
    public verbaleSel: MinVerbaleVO;
    public loaded: boolean = true;

    request: RicercaVerbaleRequest;


    constructor(
        private logger: LoggerService,
        private configSharedService: ConfigSharedService,
        private sharedVerbaleService: SharedVerbaleService,
        private router: Router,
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaVerbaleRicercaComponent.name);

        this.config = this.configSharedService.configRicercaVerbale;
        this.verbali = new Array();
    }

    scrollEnable: boolean;
    ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
        this.request = ricercaVerbaleRequest;
        this.request.statoManualeDiCompetenza  = true;
        this.showTable = false;
        this.loaded = false;
        this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
            data => {
                if (data != null)
                    this.verbali = data;
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessage(err);
                }
                this.logger.error("Errore nel salvataggio del verbale");
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

    onDettaglio(el: any | Array<any>) {
        this.verbaleSel = el;
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");

        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_VERBALE_RIEPILOGO + el.id)
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
        let seconds: number = 30//this.configService.getTimeoutMessagge();
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


    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaVerbaleRicercaComponent.name);
    }
}