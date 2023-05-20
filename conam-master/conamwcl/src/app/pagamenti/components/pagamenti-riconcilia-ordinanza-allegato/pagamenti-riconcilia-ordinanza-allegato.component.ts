import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SalvaAllegatoOrdinanzaVerbaleSoggettoRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-verbale-soggetto-request";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";

declare var $: any;

@Component({
    selector: 'pagamenti-riconcilia-ordinanza-allegato',
    templateUrl: './pagamenti-riconcilia-ordinanza-allegato.component.html'
})
export class PagamentiRiconciliaOrdinanzaAllegatoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean; 
    public loadedConfig: boolean;
    public loadedCategoriaAllegato: boolean;
    public senzaAllegati: boolean = true;

    public tipoAllegatoModel: Array<TipoAllegatoVO>;

    public idOrdinanzaVerbaleSoggettoArray: Array<number>;
    public idOrdinanza: number;

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private pagamentiUtilService: PagamentiUtilService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaOrdinanzaAllegatoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanzaVerbaleSoggettoArray = this.pagamentiUtilService.getIdOrdinanzaVerbaleSoggetto();
            this.idOrdinanza = this.pagamentiUtilService.getIdOrdinanza();
            if (this.idOrdinanzaVerbaleSoggettoArray == null || this.idOrdinanzaVerbaleSoggettoArray.length == 0 || this.idOrdinanza == null)
                this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_ORDINANZA_RICERCA);
            this.loadTipoAllegato();
        });
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegato = false;
        let request = new TipologiaAllegabiliOrdinanzaSoggettoRequest();
        request.id = this.idOrdinanzaVerbaleSoggettoArray;
        request.tipoDocumento = Constants.RICEVUTA_PAGAMENTO_ORDINANZA;
        this.subscribers.tipoAllegato = this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(request).subscribe(data => {
            this.tipoAllegatoModel = data;
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loadedCategoriaAllegato = true;
        });
    }

    salvaAllegato(nuovoAllegato: SalvaAllegatoOrdinanzaVerbaleSoggettoRequest) {
        nuovoAllegato.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggettoArray;
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedOrdinanzaService.creallegatoOrdinanzaVerbaleSoggetto(nuovoAllegato).subscribe(data => {
            this.pagamentiUtilService.setId(null, null);
            //reindirizzo alla pagina di successo
            let azione: string = "allegato";
            this.router.navigate([Routing.PAGAMENTI_RICONCILIA_ORDINANZA_RIEPILOGO + this.idOrdinanza, { action: azione }]);
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }

    manageMessage(type: string, message: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 20//this.configService.getTimeoutMessagge();
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
        this.logger.destroy(PagamentiRiconciliaOrdinanzaAllegatoComponent.name);
    }

}