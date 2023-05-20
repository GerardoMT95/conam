import { Component, OnInit, OnDestroy, ViewChild, Input, Output, EventEmitter } from "@angular/core";
import { runInThisContext } from "vm";
import { SalvaStatoVerbaleRequest } from "../../../commons/request/verbale/salva-stato-verbale.request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../commons/vo/messageVO";
import { IstruttoreVO, StatoVerbaleVO } from "../../../commons/vo/select-vo";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TemplateService } from "../../../template/services/template.service";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";



@Component({
    selector: 'pregresso-stato',
    templateUrl: './pregresso-stato.component.html'
})
export class PregressoStatoComponent implements OnInit, OnDestroy {
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;
    
    @Input()
      idVerbale: number;

    @Input()
      riepilogoVerbale: RiepilogoVerbaleVO; 
 
    @Output()
        onLoadChange: EventEmitter<boolean> = new EventEmitter<boolean>();
      
    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;
  

    public loadedistruttore:boolean;
    public loadedstativerbale: boolean;
    public showIstruttore: boolean = false;

    public funzionarioIstrModel: Array<IstruttoreVO> = new Array<IstruttoreVO>();
    public statiVerbaliModel: Array<StatoVerbaleVO> = new Array<StatoVerbaleVO>();
    public statiVerbaliMessage: string;
    salvaStatoVerbaleRequest: SalvaStatoVerbaleRequest = new SalvaStatoVerbaleRequest();



    public subscribers: any = {};
    public buttonAnnullaTexts: string;
    public buttonConfirmTexts: string;
    public subMessagess: Array<string>;


    public alertWarning: string;

    public statoCurrent: StatoVerbaleVO;
    

    constructor(
        private logger: LoggerService,
        private pregressoVerbaleService:PregressoVerbaleService,
        private verbaleService:VerbaleService,
        private utilSubscribersService: UtilSubscribersService,
        private templateService: TemplateService
        
    ) { }

    ngOnInit(): void {
        this.logger.init(PregressoStatoComponent.name);
        this.salvaStatoVerbaleRequest.id = this.idVerbale;
        this.showIstruttore = false;
        this.loadAlertWarning();
        this.loadStatiVerbale();
        this.loadIstruttoreByVerbale(this.idVerbale);
        
    }
    loadAlertWarning() {
        this.subscribers.alertWarning = this.templateService.getMessage('STVERPRWAR').subscribe(data => {
            this.alertWarning = data.message;
        }, err => {
            this.logger.error("Errore nel recupero del messaggio");
        });
    }

    loadIstruttoreByVerbale(idVerbale: number) {
        this.loadedistruttore = false;
        this.subscribers.istruttoreByIdRegione = this.verbaleService.getIstruttoreByIdVerbale(idVerbale).subscribe(data => {
            this.funzionarioIstrModel = data;
            this.loadedistruttore = true;
        }, err => {
            this.logger.error("Errore nel recupero degli istuttori");
        });
    }

    loadStatiVerbale() {
        this.statiVerbaliModel = [];
        this.statoCurrent = null; 
        this.subscribers.statiVerbale = this.pregressoVerbaleService.getStatiVerbaleById(this.idVerbale).subscribe(data => {
           if(data.messaggio && data.messaggio.message){
                this.statiVerbaliMessage = data.messaggio.message;
           }
           this.statiVerbaliModel = data.stati;
           this.loadedstativerbale=true;
        }, err => {
            this.loadedstativerbale = true;
            this.logger.error("Errore nel recupero degli stati");
        });
    }

    changeStato(){
        const idStato = this.salvaStatoVerbaleRequest.idAzione;
        const stato = this.statiVerbaliModel.find(i=>i.id==idStato);
        this.statoCurrent = stato;
        if(idStato==8) { // stato recupero pregresso terminato
            this.showIstruttore = true;
        }else{
            this.showIstruttore = false;
        }
    }

    
    salvaStatoVerbale() {
        let inserted = new Array<string>();
        
        /* genera messaggio */ 
        this.subMessagess = new Array<string>();
       
        let incipit:string = '';
        if(this.statoCurrent && this.statoCurrent.confirmMessage && this.statoCurrent.confirmMessage.message){
            incipit = this.statoCurrent.confirmMessage.message;
        }else{
            incipit = this.statiVerbaliMessage;
            const nuovoStato=this.statiVerbaliModel.find(i=>i.id==this.salvaStatoVerbaleRequest.idAzione).denominazione;
            incipit = incipit.replace('<VALORE_SELEZIONATO_DA_TENDINA>', nuovoStato);
        }
        
        
        
        this.subMessagess.push(incipit);
       
        this.buttonAnnullaTexts = "Annulla";
        this.buttonConfirmTexts = "Conferma";

        //mostro un messaggio
        this.sharedDialogs.open();

        //unsubscribe
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

        //premo "Conferma"
        this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(data => {
            this.subscribers.saveStatoVerbale = this._saveRequestStatoVerbale();
        }, err => {
            this.logger.error(err);
        });

        //premo "Annulla"
        this.subscribers.close = this.sharedDialogs.closeAction.subscribe(data => {
            this.subMessagess = new Array<string>();
        }, err => {
            this.logger.error(err);
        });
    }

    private _saveRequestStatoVerbale(){
        this.onLoadChange.emit(false);
        this.pregressoVerbaleService.salvaStatoVerbale(this.salvaStatoVerbaleRequest).subscribe(data => {
            this.onLoadChange.emit(true);
            if(data){
                this.manageMessage(data);
                // per efettuare il reset della parte che non serve a quel punto nel riepilogo
                this.loadStatiVerbale();
            }
        }, err => {
            this.onLoadChange.emit(true);
            this.logger.error("Errore nel salvataggio dello stato del fascicolo");
            
        });
    }

    manageMessage(mess: ExceptionVO | MessageVO) {
        this.typeMessageTop = mess.type;
        this.messageTop = mess.message;
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
        this.logger.destroy(PregressoStatoComponent.name);
    }

}
