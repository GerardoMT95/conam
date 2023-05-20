import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO, StatoSollecitoVO } from "../../../commons/vo/select-vo";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { SharedOrdinanzaRiepilogoComponent } from "../../../shared-ordinanza/component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TemplateService } from "../../../template/services/template.service";
import { SalvaStatoOrdinanzaPregressiRequest } from "../../../commons/request/pregresso/salva-stato-ordinanza-pregressi.request";
import { AzioneOrdinanzaPregressiResponse } from "../../../commons/response/pregresso/azione-ordinanza-pregressi-response";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SalvaSollecitoPregressiRequest } from "../../../commons/request/pregresso/salva-sollecito-pregressi-request";

@Component({
    selector: 'pregresso-solleciti-riepilogo',
    templateUrl: './pregresso-solleciti-riepilogo.component.html'
})
export class PregressoSollecitiRiepilogoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
    public idVerbale: number;

    public showTable: boolean;

    public config: Config;
    public ordinanze: Array<OrdinanzaVO>;
    public solleciti: Array<SollecitoVO>;
    public sollecitoSel: SollecitoVO;
    public sollecitoSaveStato: SollecitoVO;
    public loaded: boolean = true;
    
    public riepilogoVerbale: RiepilogoVerbaleVO;
    
    public loadedStatiSollecito  : boolean;
    public statiSollecitoModel: Array<StatoSollecitoVO>;
    public nuovoStatoSollecito: StatoSollecitoVO;

    public request: RicercaOrdinanzaRequest;

    public max: boolean = false;

    idOrdinanza: number;
    idSollecito: number;
    azione: AzioneOrdinanzaPregressiResponse;
    loadedAction: boolean;
    loadedSollecito: boolean = false;
    isRichiestaBollettiniSent: boolean;
    isFirstDownloadBollettini: boolean;

    @ViewChild(SharedOrdinanzaRiepilogoComponent)
    sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

    salvaStatoOrdinanzaPregressiRequest: SalvaStatoOrdinanzaPregressiRequest = new SalvaStatoOrdinanzaPregressiRequest();
    statiOrdinanzaMessage: string;

    public buttonAnnullaTexts: string;
    public buttonConfirmTexts: string;
    public subMessagess: Array<string>;
    public alertWarning: string;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private activatedRoute: ActivatedRoute,
        private pregressoVerbaleService: PregressoVerbaleService,
        private templateService: TemplateService
    ) { }

    ngOnInit(): void {
        this.logger.init(PregressoSollecitiRiepilogoComponent.name);
        this.config = this.configSharedService.configSolleciti;    
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
           
            if(this.activatedRoute.snapshot.paramMap.get('idOrdinanza')){
                // setto il riferimento per la ricerca documento protocollato    
                this.idOrdinanza = parseInt(this.activatedRoute.snapshot.paramMap.get('idOrdinanza'));
            }

            if (isNaN(this.idOrdinanza))
                this.router.navigateByUrl(Routing.PREGRESSO_DATI);

            this.load();

        });
    }

    load():void{
        this.loadedSollecito = false;
        this.loaded = false;
        this.pregressoVerbaleService.getSollecitiByOrdinanza(this.idOrdinanza).subscribe( 
            data => {
                this.resetMessageTop();
                
                this.solleciti = data;
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessageTop(err.message,err.type);
                }
                this.logger.error("Errore nella ricerca dell'ordinanza");
            }
        );   
    }
   
    onChangeData(event:any){
        if(event.reload){
            this.load();
        }
    }

    scrollEnable: boolean;
    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest) {
        this.request = ricercaOrdinanzaRequest;
        this.showTable = false;
        this.loaded = false;
        ricercaOrdinanzaRequest.ordinanzaProtocollata = false;
        this.request.tipoRicerca = "RICERCA_ORDINANZA";
        this.request.soggettoVerbale = null;
        this.request.statoOrdinanza = null;
        this.pregressoVerbaleService.ricercaOrdinanza(ricercaOrdinanzaRequest).subscribe( 
            data => {
                this.resetMessageTop();
                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo; 
                    });
                }                

                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                    this.ordinanze = new Array<OrdinanzaVO>();
                } else{
                    this.ordinanze = data;
                }  
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessageTop(err.message,err.type);
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
    
    callAzioneOrdinanza() {
        let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
        request.id = this.idOrdinanza;
        this.subscribers.callAzioneOrdinanza = this.pregressoVerbaleService.azioneOrdinanza(request).subscribe(data => {
            this.azione = data;
        });
    }

    onDettaglio(el: any | Array<any>) {
        this.loadedSollecito = false;
        this.sollecitoSaveStato = el;
        this.sollecitoSel = {...el};
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");
        else{
            this.idSollecito = this.sollecitoSel.idSollecito;
            this.loadedSollecito = true;
            this.loadStatiSollecito();
        }

    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
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

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoSollecitiRiepilogoComponent.name);
    }

    loadAlertWarning() {
        this.subscribers.alertWarning = this.templateService.getMessage('STORDPRWAR').subscribe(data => {
            this.alertWarning = data.message;
        }, err => {
            this.logger.error("Errore nel recupero del messaggio");
        });
    }

    loadStatiSollecito() {
        this.nuovoStatoSollecito = new StatoSollecitoVO();
        let id:number = this.idSollecito;
        this.subscribers.statiSollecito = this.pregressoVerbaleService.getStatiSollecito(id).subscribe(data => {
           this.statiSollecitoModel = data;
           this.loadedStatiSollecito=true;
        }, err => {
            this.logger.error("Errore nel recupero degli stati");
        });
    }

    salvaStatoSollecito() {
        this.loaded = false;
        let saveRequest = new SalvaSollecitoPregressiRequest(this.idVerbale,this.idOrdinanza);
        this.sollecitoSaveStato.statoSollecito = this.nuovoStatoSollecito;
        saveRequest.sollecito = this.sollecitoSaveStato;  
        this.subscribers.salva = this.pregressoVerbaleService.salvaSollecito(saveRequest).subscribe(data => {
            this.loaded = true;
            this.load();
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il salvataggio del piano");
            this.loaded = true;
        });
    }

    goBack():void{
        this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO_ORDINANZE + this.idVerbale);
    }
}