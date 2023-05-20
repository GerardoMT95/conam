import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SalvaAllegatoOrdinanzaVerbaleSoggettoRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-verbale-soggetto-request";
import { FaseGiurisdizionaleUtilService } from "../../services/fase-giurisdizionale-util.serivice";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { TemplateService } from "../../../template/services/template.service";


declare var $: any;

@Component({
    selector: 'fase-giurisdizionale-sentenza-allegato-ordinanza',
    templateUrl: './fase-giurisdizionale-sentenza-allegato-ordinanza.component.html'
})
export class FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;
    public loadedConfig: boolean;
    public loadedCategoriaAllegato: boolean;

    public tipoAllegatoModel: Array<TipoAllegatoVO>;

    public idOrdinanzaVerbaleSoggetto: Array<number> = [];
    public idOrdinanza: number;


    //message
    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;
    private alertWarning: string;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private faseGiurisdizionaleUtilService: FaseGiurisdizionaleUtilService,
        private sharedVerbaleService: SharedVerbaleService,
        private fascicoloService: FascicoloService,
        private templateService: TemplateService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent.name);
        this.getIdOrdinanzaVerbaleSoggetto();
        if (this.idOrdinanzaVerbaleSoggetto) {
            this.loadTipoAllegato();
            // setto il riferimento per la ricerca documento protocollato    
            this.fascicoloService.ref = this.router.url;
        }
        this.loadAlertWarning();
    }
    
    loadAlertWarning() {
        this.subscribers.alertWarning = this.templateService.getMessage('PRODISPGIU').subscribe(data => {
            this.alertWarning = data.message;
        }, err => {
            this.logger.error("Errore nel recupero del messaggio");
        });
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegato = false;
        let request = new TipologiaAllegabiliOrdinanzaSoggettoRequest();
        request.id = this.idOrdinanzaVerbaleSoggetto;
        request.tipoDocumento = Constants.DISPOSIZIONE_DEL_GIUDICE;
        this.subscribers.tipoAllegato = this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(request).subscribe(data => {
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
        });
    }

    ricercaProtocollo (ricerca: RicercaProtocolloRequest) {
        ricerca.idVerbale = this.idOrdinanza;
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
            this.fascicoloService.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggetto;

            this.fascicoloService.successPage = Routing.FASE_GIURISDIZIONALE_SENTENZA_DETTAGLIO_ORDINANZA + this.idOrdinanza;
            if (data.messaggio) {
                this.fascicoloService.message = data.messaggio;
            }else{
                this.fascicoloService.message = null;
            }
            this.router.navigateByUrl(Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idOrdinanza);   
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.loaded = true;
            this.logger.error("Errore nel recupero dei documenti protocollati");
        });
    }

    salvaAllegato(nuovoAllegato: SalvaAllegatoOrdinanzaVerbaleSoggettoRequest) {
        nuovoAllegato.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggetto;
        //mando il file al Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedOrdinanzaService.salvaAllegatoOrdinanzaVerbaleSoggetto(nuovoAllegato).subscribe(data => {
            this.loadTipoAllegato();
            this.faseGiurisdizionaleUtilService.setId(null, null);
            this.router.navigate([Routing.FASE_GIURISDIZIONALE_SENTENZA_DETTAGLIO_ORDINANZA + this.idOrdinanza, { action: 'caricato' }], { replaceUrl: true });
            this.loaded = true;
            this.manageMessage("SUCCESS","Disposizione del giudice caricata con successo");
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }

    getIdOrdinanzaVerbaleSoggetto() {
        this.idOrdinanzaVerbaleSoggetto = this.faseGiurisdizionaleUtilService.getIdOrdinanzaVerbaleSoggetto();
        this.idOrdinanza = this.faseGiurisdizionaleUtilService.getIdOrdinanza();
        if (!this.idOrdinanzaVerbaleSoggetto)
            this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_SENTENZA_RICERCA_ORDINANZA);
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
        this.logger.destroy(FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent.name);
    }

}