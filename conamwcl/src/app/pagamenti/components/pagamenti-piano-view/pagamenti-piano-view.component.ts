import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { SelectVO } from "../../../commons/vo/select-vo";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { saveAs } from 'file-saver';
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedRateConfigService } from "../../../shared-pagamenti/services/shared-pagamenti-config.service";
import { PagamentiService } from "../../services/pagamenti.service";
import { TableSoggettiRata } from "../../../commons/table/table-soggetti-rata";

declare var $: any;

@Component({
    selector: 'pagamenti-piano-view',
    templateUrl: './pagamenti-piano-view.component.html'
})
export class PagamentiPianoViewComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;
    public loadedIdSoggettiOrdinanza: boolean;
    public loadedAction: boolean = true;

    public isFirstDownloadBollettini: boolean;

    idPiano: number;
    idSoggettiOrdinanza: Array<number>;
    listTableSoggettiOrdinanza: Array<TableSoggettiOrdinanza>

    public piano: PianoRateizzazioneVO;
    public listaSoggetti: Array<TableSoggettiOrdinanza>;

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    configSoggetti: Config;
    configRate: Config;
    rate: Array<TableSoggettiRata>;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private pagamentiService: PagamentiService,
        private templateService: TemplateService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private sharedRateConfigService: SharedRateConfigService
    ) { }

    ngOnInit(): void {
        this.configRate = this.sharedRateConfigService.getConfigRateView();
        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "", null, null, null,(el: any)=>true);
        this.logger.init(PagamentiPianoViewComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idPiano = +params['idPiano'];

            if (this.activatedRoute.snapshot.paramMap.get('action') == 'creazione')
                this.manageMessageTop("Piano creato con successo", 'SUCCESS');

            if (this.idPiano)
                this.loadPiano();
            else
                this.router.navigateByUrl(Routing.PAGAMENTI_RICERCA_PIANO);
        });
    }

    loadPiano() {
        this.loaded = false;
        this.loadedIdSoggettiOrdinanza = false;
        this.subscribers.getDettaglio = this.pagamentiService.getDettaglioPianoById(this.idPiano, false).subscribe(data => {
            this.piano = data;
            this.rate = data.rate.map( value => {
                return TableSoggettiRata.map(value);
            });
            this.idSoggettiOrdinanza = new Array<number>();
            this.idSoggettiOrdinanza = this.piano.soggetti.map(sogg => sogg.idSoggettoOrdinanza);
            this.listaSoggetti = this.piano.soggetti.map(sogg => TableSoggettiOrdinanza.map(sogg));
            this.loadedIdSoggettiOrdinanza = true;
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il caricamento del piano");
            this.loaded = true;
        });
    }

    inviaRichiestaBollettini() {
        this.loaded = false;
        this.pagamentiService.inviaRichiestaBollettini(this.idPiano).subscribe(data => {
            this.loadPiano();
            this.loaded = true;
            this.isFirstDownloadBollettini = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante la creazione dei bollettini");
            this.loaded = true;
        });
    }

    scaricaBollettini() {
        this.loadedAction = false;
        this.pagamentiService.downloadBollettini(this.idPiano).subscribe(data => {
            let num: string = this.piano.numProtocollo != null ? this.piano.numProtocollo : "";
            saveAs(data, "Bollettini_Rateizzazione_" + num + ".pdf");
            if(this.isFirstDownloadBollettini){
                this.isFirstDownloadBollettini = false;
                this.subscribers.getDettaglio = this.pagamentiService.getDettaglioPianoById(this.idPiano, false).subscribe(data => {
                    this.rate = data.rate.map( value => {
                        return TableSoggettiRata.map(value);
                    });
                }, err => {
                    this.logger.error("Errore durante il caricamento delle rate");
                });
            }
            this.loadedAction = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il download del PDF");
            this.loadedAction = true;
        });
    }

    completaLetteraPiano() {
        this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_TEMPLATE + this.idPiano);
    }

    stampaPDF() {
        this.loaded = false;
        let request: DatiTemplateRequest = new DatiTemplateRequest();
        request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
        request.idPiano = this.idPiano;
        this.templateService.downloadTemplate(request).subscribe(data => {
            saveAs(data, 'Lettera Piano Rateizzazione.pdf');
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il download del PDF");
            this.loaded = true;
        });
    }

    byId(o1: SelectVO, o2: SelectVO) {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
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
        if (this.loaded && this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    goToInserisciNotifica() {
        this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_INS_NOTIFICA + this.idPiano);
    }

    goToVisualizzaNotifica() {
        this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_VIEW_NOTIFICA + this.idPiano);
    }

    onInfo(el: any | Array<any>){    
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");
        else{
            let azione1: string = el.ruolo; 
            let azione2: string = el.noteSoggetto;
            this.router.navigate([Routing.SOGGETTO_RIEPILOGO + el.idSoggetto, { ruolo: azione1, nota: azione2 }]);;    
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiPianoViewComponent.name);
    }

}