import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { Constants } from "../../../commons/class/constants";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { SalvaAllegatoMultipleRequest } from "../../../commons/request/salva-allegato-multiple-request";

@Component({
    selector: 'fase-giurisdizionale-comparsa-verbale-allegato',
    templateUrl: './fase-giurisdizionale-comparsa-verbale-allegato.component.html',
})
export class FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idVerbale: number;
    public loaded: boolean;
    public loadedConfig: boolean;
    public loadedCategoriaAllegato: boolean;

    public tipoAllegatoModel: Array<TipoAllegatoVO>; 

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;
    public allegatiMultipli : boolean = true;
    public multipli: boolean;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent.name);

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))
                this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RICERCA);
            this.loadTipoAllegato();
        });
    }

    setMultipli(e: boolean){
        if(e){
            this.multipli = true;
        }
        else{
            this.multipli = false;
        }
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegato = false;
        let request = new TipologiaAllegabiliRequest();
        request.id = this.idVerbale;
        request.tipoDocumento = Constants.COMPARSA;
        this.subscribers.tipoAllegato = this.sharedVerbaleService.getTipologiaAllegatiAllegabiliVerbale(request).subscribe(data => {
            this.tipoAllegatoModel = data;
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
        }); 
    }

    salvaAllegato(allegati: SalvaAllegatoMultipleRequest) { 
        allegati.idVerbale = this.idVerbale;
        
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedVerbaleService.salvaAllegatoVerbaleMaster(allegati).subscribe(data => {
            this.loadTipoAllegato();
            let azione: string;
            if(!this.multipli)
                azione = "caricato";
            else
                azione = "caricati";
            this.router.navigate([Routing.FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RIEPILOGO + this.idVerbale, { action: azione }], { replaceUrl: true })
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }

    /*
    salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) { //TODO salvaAllegatoVerbaleMaster
        nuovoAllegato.idVerbale = this.idVerbale;
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedVerbaleService.salvaAllegatoVerbale(nuovoAllegato).subscribe(data => {
            this.loadTipoAllegato();
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_INSERIMENTO_COMPARSA_RIEPILOGO + this.idVerbale, { action: 'caricato' }], { replaceUrl: true })
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }*/

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
        this.logger.destroy(FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent.name);
    }

}