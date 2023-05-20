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
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";

@Component({
    selector: 'controdeduzioni-verbale-allegato',
    templateUrl: './controdeduzioni-verbale-allegato.component.html',
})
export class ControdeduzioniVerbaleAllegatoComponent implements OnInit, OnDestroy {

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

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleService: SharedVerbaleService,
        private fascicoloService: FascicoloService,
    ) { }

    ngOnInit(): void {
        this.logger.init(ControdeduzioniVerbaleAllegatoComponent.name);

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))
                this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RICERCA);
            
            // setto il riferimento per la ricerca documento protocollato    
            this.fascicoloService.ref = this.router.url;
            

            this.loadTipoAllegato();
        });
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegato = false;
        let request = new TipologiaAllegabiliRequest();
        request.id = this.idVerbale;
        request.tipoDocumento = Constants.CONTRODEDUZIONE;
        this.subscribers.tipoAllegato = this.sharedVerbaleService.getTipologiaAllegatiAllegabiliVerbale(request).subscribe(data => {
            this.tipoAllegatoModel = data;
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
            // setto il riferimento per la ricerca documento protocollato    
            this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel; 
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
        });
    }

    salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
        nuovoAllegato.idVerbale = this.idVerbale;
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedVerbaleService.salvaAllegatoVerbale(nuovoAllegato).subscribe(data => {
            this.loadTipoAllegato();
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RIEPILOGO + this.idVerbale, { action: 'caricato' }], { replaceUrl: true });
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }

    ricercaProtocollo (ricerca: RicercaProtocolloRequest) {
        ricerca.idVerbale = this.idVerbale;
        this.loaded = false;
        this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(data => {
            this.loaded = true;

            let tipiAllegatoDuplicabili = []; 
            if(!data.documentoProtocollatoVOList){
                data.documentoProtocollatoVOList = [];
            } else if(data.documentoProtocollatoVOList[0]){
                tipiAllegatoDuplicabili = data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
            }
            this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili; 
            
            // setto il numero di protocollo per la ricerca documento protocollato    
            this.fascicoloService.searchFormRicercaProtocol = ricerca.numeroProtocollo;
            // setto i dati per la ricerca documento protocollato    
            this.fascicoloService.dataRicercaProtocollo = data.documentoProtocollatoVOList;
            this.fascicoloService.successPage = Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RIEPILOGO + this.idVerbale;

            if (data.messaggio) {
                this.fascicoloService.message = data.messaggio;
            }else{
                this.fascicoloService.message = null;
            }
            this.router.navigateByUrl(Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idVerbale);   
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.loaded = true;
            this.logger.error("Errore nel recupero dei documenti protocollati");
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
        this.logger.destroy(ControdeduzioniVerbaleAllegatoComponent.name);
    }

}