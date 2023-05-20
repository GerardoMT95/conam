import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { Routing } from "../../../commons/routing";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { SalvaAllegatoOrdinanzaRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";

@Component({
    selector: 'fase-giurisdizionale-cancelleria-allegato-ordinanza',
    templateUrl: './fase-giurisdizionale-cancelleria-allegato-ordinanza.component.html'
})
export class FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idOrdinanza: number;
    public loaded: boolean;
    public loadedConfig: boolean;
    public loadedCategoriaAllegatoDallaCancelleria: boolean;
    public loadedCategoriaAllegatoAllaCancelleria: boolean;

    public tipoAllegatoModel: Array<TipoAllegatoVO> = [];

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent.name);

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            if (isNaN(this.idOrdinanza))
                this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_CANCELLERIA_RICERCA_ORDINANZA);
            this.loadTipoAllegato();
        });
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegatoDallaCancelleria = false;
        let request = new TipologiaAllegabiliRequest();
        request.id = this.idOrdinanza;
        request.tipoDocumento = Constants.COMUNICAZIONI_DALLA_CANCELLERIA; 
        //il backend mi torna anche COMUNICAZIONI_ALLA_CANCELLERIA
        this.subscribers.tipoAllegato = this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(request).subscribe(data => {
            this.tipoAllegatoModel = data;
            this.loadedCategoriaAllegatoDallaCancelleria = true;
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loadedCategoriaAllegatoDallaCancelleria = true;
        });
    }

    salvaAllegato(nuovoAllegato: SalvaAllegatoOrdinanzaRequest) {
        nuovoAllegato.idOrdinanza = this.idOrdinanza;
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedOrdinanzaService.salvaAllegatoOrdinanza(nuovoAllegato).subscribe(data => {
            this.loadTipoAllegato();
            //reindirizzo alla pagina di successo
            this.router.navigate([Routing.FASE_GIURISDIZIONALE_CANCELLERIA_DETTAGLIO_ORDINANZA + this.idOrdinanza, { action: 'caricato' }], { replaceUrl: true });
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
        this.logger.destroy(FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent.name);
    }

}