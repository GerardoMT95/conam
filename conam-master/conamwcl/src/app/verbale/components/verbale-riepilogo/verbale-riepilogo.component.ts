import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { UserService } from "../../../core/services/user.service";
import { SelectVO, IstruttoreVO, AzioneVO } from "../../../commons/vo/select-vo";
import { VerbaleService } from "../../services/verbale.service";
import { AzioneVerbaleResponse } from "../../../commons/response/verbale/azione-verbale-response";
import { SalvaAzioneRequest } from "../../../commons/request/verbale/salva-azione.request";
import { Constants } from "../../../commons/class/constants";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { UtenteVO } from "../../../commons/vo/utente-vo";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { SalvaStatoRequest } from "../../../commons/request/verbale/salva-stato-request";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";



@Component({
    selector: 'verbale-riepilogo',
    templateUrl: './verbale-riepilogo.component.html',
    styleUrls: ['./verbale-riepilogo.component.scss']
})
export class VerbaleRiepilogoComponent implements OnInit, OnDestroy {
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

    public subscribers: any = {};

    public loadedistruttore: boolean;
    public loaded: boolean;
    public messaggioIstruttore: boolean = false;

    private intervalIdS: number = 0;

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    public singoloEnte: boolean;
    public idVerbale: number;
    public funzionarioIstrModel: Array<IstruttoreVO> = new Array<IstruttoreVO>();
    public azioneVerbale: AzioneVerbaleResponse = new AzioneVerbaleResponse();
    salvaAzioneRequest: SalvaAzioneRequest[] = [];
    salavaStatoFascicolo: SalvaStatoRequest = new SalvaStatoRequest(); 
    public utente: UtenteVO;
    public visible: boolean = false;
    public riepilogoVerbale: RiepilogoVerbaleVO;
    public listaStati: any;
    public statoFascicoloSelezionato: any;
    public showStatoFascicoloSelect: boolean = false;
    public abilitaPulsanteConferma: boolean = false;
    public messageDialog: String = '';

    public idFunzionario: number ;
   
    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private userService: UserService,
        private verbaleService: VerbaleService,
        private sharedVerbaleService: SharedVerbaleService,
        private utilSubscribersService: UtilSubscribersService,

    ) { }

    ngOnInit(): void {
       
        this.logger.init(VerbaleRiepilogoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))
                this.router.navigateByUrl(Routing.VERBALE_DATI);
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessage("Documento caricato con successo","SUCCESS");
            

        });

       this.verbaleService.listaStati().subscribe(
            (data) => {
                this.listaStati = data;
            },
          );
          
        
        this.subscribers.riepilogo = this.sharedVerbaleService.riepilogoVerbale(this.idVerbale).subscribe(data => {
            this.riepilogoVerbale = data;
        });  
        this.loaded = false;
        this.verbaleService.getUtenteRuolo(this.idVerbale).subscribe(data => {
            this.utente = data;
           /*  if(this.utente.id == 1 || this.utente.id == 4){
                this.idFunzionario = this.utente.id;
            } */
            this.messaggioIstruttore = this.utente.isIstruttore == 1;
            this.loaded = true;
            this.getAzioneVerbale();
        }, (err) => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.message, err.type);
            }
            this.logger.info("Errore nel recupero utente");
            this.loaded = true;
        });
        
       
    }

    salvaAzioneVerbale(azione:AzioneVO , index: number) {
        if (azione && azione.confirmMessage){
            /* genera messaggio */
            this.messageDialog = azione.confirmMessage.message;

            //mostro un messaggio
            this.sharedDialogs.open();

            //unsubscribe
            this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
            this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

            //premo "Conferma"
            this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
                (data) => {
                    this._salvaAzioneVerbale(azione.id,index);
                },
                (err) => {
                    this.loaded = true;
                    this.logger.error(err);
                }
            );

            //premo "Annulla"
            this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
                (data) => {
                    this.messageDialog = '';
                },
                (err) => {
                    this.logger.error(err);
                }
            ); 
        }else{
            this._salvaAzioneVerbale(azione.id,index);
        }  
    }

    _salvaAzioneVerbale(idAzione: number, index: number ) {
        this.salvaAzioneRequest[index].id = this.idVerbale;
        this.salvaAzioneRequest[index].idAzione = idAzione;
        this.loaded = false;
        this.subscribers.salvaAzioneVerbale = this.verbaleService.salvaAzioneVerbale(this.salvaAzioneRequest[index]).subscribe(data => {
            this.messageDialog = '';
            this.azioneVerbale = new AzioneVerbaleResponse();
            this.getAzioneVerbale();
            if (idAzione == Constants.ID_AZIONE_ACQUISISCI || idAzione == Constants.ID_AZIONE_ACQUISISCI_CON_PAGAMENTO || idAzione == Constants.ID_AZIONE_ACQUISISCI_CON_SCRITTI || idAzione == Constants.ID_AZIONE_IN_ATTESA_VERIFICA_PAGAMENTO || idAzione == Constants.ID_AZIONE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO) {
              	if ( this.riepilogoVerbale.verbale.numeroProtocollo != null) {
            	 this.manageMessage("Operazione avvenuta con successo", 'SUCCESS');
            	} else {
                    this.manageMessage("La richiesta di acquisizione del fascicolo è stata presa in carico. Al termine del processo il fascicolo disporrà del numero di protocollo e sarà possibile eseguire altre attività utilizzando la funzionalità di ricerca", 'SUCCESS')
            	}          
            }       
            else if (idAzione == Constants.ID_AZIONE_ARCHIVIATO_IN_AUTOTUTELA) {
                this.manageMessage("Fascicolo archiviato in autotutela", 'SUCCESS');          
            } 
            this.loaded = true;
            this.scrollEnable = true;
        }, (err) => {
            if (err instanceof ExceptionVO) {
                this.manageMessage(err.message, err.type);
            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loaded = true;
        });
    }

    refreshService() {
        this.getAzioneVerbale();
    }

    getAzioneVerbale() {
        this.loaded = false;
        this.subscribers.statoVerbale = this.sharedVerbaleService.getAzioniVerbale(this.idVerbale).subscribe(data => {
            this.loaded = true;
            this.azioneVerbale = data;

            this.visible = true;
            
            if(this.azioneVerbale.azioneList != null 
                && this.azioneVerbale.azioneList.find(i=>i.listaIstruttoriEnable==true)){
                this.loadIstruttoreByVerbale(this.idVerbale); 
            }
            this.salvaAzioneRequest = [];
            if(this.azioneVerbale.azioneList && this.azioneVerbale.azioneList.length>0){
                this.azioneVerbale.azioneList.forEach((value, index) => {
                    this.salvaAzioneRequest[index]= new SalvaAzioneRequest();
                    this.salvaAzioneRequest[index].idFunzionario = this.idFunzionario;
                });
            }
            /*if (this.azioneVerbale.azione != null) {
                if (data.azione.listaIstruttoriEnable)
                    this.loadIstruttoreByVerbale(this.idVerbale);
            }*/
        })
    }

    loadIstruttoreByVerbale(idVerbale: number) {
        this.loadedistruttore = false;
        this.subscribers.istruttoreByIdRegione = this.verbaleService.getIstruttoreByIdVerbale(idVerbale).subscribe(data => {
            this.funzionarioIstrModel = data;
            this.loadedistruttore = true;
        }, err => {
            this.logger.error("Errore nel recupero delle province");
        });
    }

    manageMessage(message: string, type: String) {
        this.typeMessageTop = type;
        this.messageTop = message;
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

    byId(o1: SelectVO, o2: SelectVO) {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    goToVerbaleAllegato() {
        this.router.navigateByUrl(Routing.VERBALE_ALLEGATO + this.idVerbale);
    }

    scrollEnable: boolean;
    ngAfterViewChecked() {
        let out: HTMLElement = document.getElementById("scrollTop");
        if (this.loaded && this.scrollEnable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(VerbaleRiepilogoComponent.name);
    }

    editStatofascicolo(){
        this.abilitaPulsanteConferma =  false;
        this.showStatoFascicoloSelect = !this.showStatoFascicoloSelect
    
    }
    confermaStatofascicolo(){
        this.loaded = false;
        this.salavaStatoFascicolo.id = this.idVerbale;
        this.salavaStatoFascicolo.idStatoManuale = this.statoFascicoloSelezionato.id;
        this.verbaleService.salvaStatoVerbale(this.salavaStatoFascicolo).subscribe(data => {
            this.manageMessage("Stato fascicolo modifica con successo", 'SUCCESS');
            this.loaded = true;
        }, (err) => {
            if (err) {
                this.manageMessage("Non è e stato fascicolo modifica con successo", 'ERROR');
               
            }
            this.loaded = true;
            this.logger.info("Errore, fascicolo non modificato");  
        });
    }
    abilitaConferma(){
        this.abilitaPulsanteConferma =  true;
    }
}