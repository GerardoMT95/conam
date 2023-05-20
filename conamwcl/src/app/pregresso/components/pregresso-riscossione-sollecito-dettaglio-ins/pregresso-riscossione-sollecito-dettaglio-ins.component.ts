import { Component, OnInit, OnDestroy, ViewChild, Input, Output, EventEmitter, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Routing } from "../../../commons/routing";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";

//JIRA - Gestione Notifica
import { SalvaSollecitoRequest } from "../../../commons/request/riscossione/salva-sollecito-request";
import { SharedRiscossioneSollecitoDettaglioComponent } from "../../../shared-riscossione/components/shared-riscossione-sollecito-dettaglio/shared-riscossione-sollecito-dettaglio.component"
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { NgForm } from "@angular/forms";
import { SalvaSollecitoPregressiRequest } from "../../../commons/request/pregresso/salva-sollecito-pregressi-request";

declare var $: any;

@Component({
    selector: 'pregresso-riscossione-sollecito-dettaglio-ins',
    templateUrl: './pregresso-riscossione-sollecito-dettaglio-ins.component.html',
})
export class PregressoRiscossioneSollecitoDettaglioInsComponent implements OnInit, OnDestroy, OnChanges {
    @Input()
    idOrdinanza: number;

    @Input()
    idVerbale: number;

    @Input()
    idSollecito: number;

    @Input()
    sollecito: SollecitoVO;

    @Input()
    numDeterminazione: string;
    
    @Output()
    onChangeData: EventEmitter<any> = new EventEmitter<any>();
    
    @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
    @ViewChild(SharedOrdinanzaDettaglio) sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
    @ViewChild(SharedRiscossioneSollecitoDettaglioComponent) sharedRiscossioneSollecitoDettaglioComponent: SharedRiscossioneSollecitoDettaglioComponent;
    @ViewChild('creaSollecitoForm')
    private creaSollecitoForm: NgForm;

    public subscribers: any = {};

    public loaded: boolean;
    public loadedAction: boolean = true;
    isCreaSollecito:boolean = true;

    public idOrdinanzaVerbaleSoggetto: number[];

    public soggettoSollecito: TableSoggettiOrdinanza[];
    public listaSolleciti: Array<SollecitoVO>;

    public configSoggetti: Config;
    public configSolleciti: Config;

    //Pop-up
    public buttonAnnullaText: string;
    public buttonConfirmText: string;
    public subMessages: Array<string>;

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    //FLAG MODIFICA
    public isModifica: boolean = true;
    public modificabile: boolean;

    //flag nuovo
    public isNuovo: boolean = true;

    public formValid: boolean = false;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private sharedRiscossioneService: SharedRiscossioneService,
        private pregressoVerbaleService:PregressoVerbaleService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,

    ) { }

    ngOnInit(): void {
        this.logger.init(PregressoRiscossioneSollecitoDettaglioInsComponent.name);
        
        this.setConfig();
        if(this.idSollecito){
            this._initSollecito();
            


        }else{
            this.loaded = false;
            this.isCreaSollecito=true;
            this.isModifica = true;
            this.sollecito = new SollecitoVO();
            this.formValid = false;
            this.loaded = true;
            this.idOrdinanzaVerbaleSoggetto=[];
        }
       
    }
    
    onSelect(els:TableSoggettiOrdinanza){
        if(els){
            this.idOrdinanzaVerbaleSoggetto = [];
            this.idOrdinanzaVerbaleSoggetto[0] = els.idSoggettoOrdinanza;
            this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0]; 
        }else{
            this.idOrdinanzaVerbaleSoggetto = [];
            this.sollecito.idSoggettoOrdinanza = null; 
        }
        this.onChangeDataEmitter();
    }

    _initSollecito(){
        if(this.idSollecito){
            let soggetti;
            soggetti = [this.sollecito.soggetto].map(value => {
                return TableSoggettiOrdinanza.map(value);
            });
        
            this.idOrdinanzaVerbaleSoggetto = [];
            this.idOrdinanzaVerbaleSoggetto[0] = soggetti[0].idSoggettoOrdinanza;
            this.soggettoSollecito = soggetti;
            
            this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];   

            this.isModifica=false;
            if(this.sollecito.statoSollecito.id==1)
                this.isModifica=true;
            
            this.isCreaSollecito=false;
            this.loaded = true;
        }
    }

    ngOnChanges() {
        this._initSollecito();
    }

    loadSollecitiEsistentiCopiaPerRichiestaBollettini() {
        this.loaded = false;
        this.listaSolleciti = new Array<SollecitoVO>();
        this.subscribers.solleciti = this.sharedRiscossioneService.getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0]).subscribe(data => {
            this.listaSolleciti = data;
            this.modificaVediSollecito(this.listaSolleciti.find(soll => soll.idSollecito == this.sollecito.idSollecito));
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il caricamento dei solleciti");
            this.loaded = true;
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

    generaMessaggio() {
        this.subMessages = new Array<string>();

        this.subMessages.push("Si intende eliminare il sollecito selezionato?");
        this.subMessages.push("Data Creazione: " + this.sollecito.dataScadenza);
        this.subMessages.push("Importo da sollecitare: " + this.sollecito.importoSollecitato);
    }

    salvaSollecito() {
        this.loaded = false;
        let saveRequest = new SalvaSollecitoPregressiRequest(this.idVerbale,this.idOrdinanza);
        saveRequest.notifica = this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject();
        saveRequest.sollecito = this.sollecito;  
        this.subscribers.salva = this.pregressoVerbaleService.salvaSollecito(saveRequest).subscribe(data => {
            this.loaded = true;
            // ricaricare stati
            this.onChangeDataEmitterSave(true);
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il salvataggio del piano");
            this.loaded = true;
        });

    }

    modificaVediSollecito(el: SollecitoVO) {
        this.sollecito = JSON.parse(JSON.stringify(el));
        if (el.statoSollecito.id == Constants.SOLLECITO_BOZZA) {
            this.modificabile = true;
        }
        else {
            this.modificabile = false;
        }
        this.isModifica = true;
        this.isNuovo = false;

        if(this.sharedRiscossioneSollecitoDettaglioComponent){
            this.sharedRiscossioneSollecitoDettaglioComponent.aggiornaNotifica();
        }
    }

    annullaModifica() {
        this.sollecito = new SollecitoVO();
        this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
        this.isModifica = false;
        this.isNuovo = true;

    }

    sharedRiscossioneSollecitoDettaglioEmitter(e:any){
        this.formValid = e.valid;

        this.onChangeDataEmitter();
    }

    onChangeDataEmitter(){
        let obj:any={};
        obj.disabled=!this.formValid  || this.idOrdinanzaVerbaleSoggetto.length==0;
        // isDisabledCreaSollecito da verificare?
        obj.getData = true;
        obj.sollecito = this.sollecito;
        if(this.sharedRiscossioneSollecitoDettaglioComponent != null || this.sharedRiscossioneSollecitoDettaglioComponent != undefined){
            if(this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()!=null||this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()!=undefined){
                obj.notifica = this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject();
            }
        }
        this.onChangeData.emit(obj);
    }
    
    onChangeDataEmitterSave(reload:boolean){
        let obj:any={};
        obj.reload = reload;
        this.onChangeData.emit(obj);
    }

    getData(){
        return {
            sollecito:this.sollecito,
            notifica:this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
        }
    }

    isDisabledCreaSollecito(): boolean {
        
        //JIRA - Gestione Notifica: non necessaria in questa sezione 
        let checkNotifica: boolean = true;
        if(this.sharedRiscossioneSollecitoDettaglioComponent != null || this.sharedRiscossioneSollecitoDettaglioComponent != undefined){
            if(this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()!=null||this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()!=undefined)
            {
                if(this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject().importoSpeseNotifica==undefined ||
                   this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject().importoSpeseNotifica==null)
                {
                    checkNotifica =true;    
                }else{
                    checkNotifica = isNaN(Number(this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject().importoSpeseNotifica));
                } 
            }
        }
        if(checkNotifica) return true;
                
        return !this.sollecito.importoSollecitato;
    }

    

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    onInfo(el: any | Array<any>){    
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");
        else{
            let azione1: string = el.ruolo; 
            let azione2: string = el.noteSoggetto;
            this.router.navigate([Routing.SOGGETTO_RIEPILOGO + el.idSoggetto, { ruolo: azione1, nota: azione2 }]);    
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoRiscossioneSollecitoDettaglioInsComponent.name);
    }
    setConfig() {
        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, null, 0, null, null,(el: any)=>true);
        this.configSolleciti = {
            selection: {
                enable: true,
                isSelectable: (el: SollecitoVO) => { return el.isCreatoDalloUserCorrente }
            },
            columns: [
                {
                    columnName: 'numeroProtocollo',
                    displayName: 'Numero Protocollo'
                },
                {
                    columnName: 'dataScadenza',
                    displayName: 'Data Scadenza'
                },
                {
                    columnName: 'importoSollecitatoString',
                    displayName: 'Importo da sollecitare' 
                },
                {
                    columnName: 'maggiorazioneString',
                    displayName: 'Maggiorazione'
                },
                {
                    columnName: 'statoSollecito.denominazione',
                    displayName: 'Stato'
                },
            ]
        };
    }

}