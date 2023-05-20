import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { UserService } from "../../../core/services/user.service";
import { EnteVO, SelectVO, AmbitoVO } from "../../../commons/vo/select-vo";
import { ProntuarioVO } from "../../../commons/vo/prontuario/prontuario-vo";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { Config } from "protractor";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { ProntuarioService } from "../../services/prontuario.service";
import { RicercaLeggeProntuarioRequest } from "../../../commons/request/prontuario/ricerca-legge-prontuario-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";

declare var $: any;

@Component({
    selector: 'prontuario-utility-norme',
    templateUrl: './prontuario-utility-norme.component.html'
})
export class ProntuarioUtilityNormeComponent implements OnInit, OnDestroy {

    @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

    public subscribers: any = {};

    public loaded: boolean;
    public loadedEnte: boolean;
    public loadedAmbito: boolean;
    public loadedAmbitoEliminabile: boolean;
    public loadedRiferimento: boolean;
    public loadedTabella: boolean;
    public isSearching: boolean;
    public isEdit: boolean;
    public hasChanged: boolean;

    public riferimentoNormativo: ProntuarioVO;
    public nuovoAmbito: AmbitoVO = new AmbitoVO();
    public ambitoDaEliminare: AmbitoVO ;

    public enteModel: Array<EnteVO>;
    public ambitoModel: Array<AmbitoVO> = new Array<AmbitoVO>();
    public ambitoEliminabileModel: Array<AmbitoVO> = new Array<AmbitoVO>();
    public riferimentoNormativoModel: Array<ProntuarioVO> = new Array<ProntuarioVO>();

    public configProntuario: Config;

    public enableTable: boolean = false;

    //Pop-up
    public buttonAnnullaText: string;
    public buttonConfirmText: string;
    public subMessages: Array<string>;

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    //Messaggio mid
    public typeMessageMid: String;
    public messageMid: String;

    constructor(
        private logger: LoggerService,
        //private router: Router,
        //private activatedRoute: ActivatedRoute,
        private userService: UserService,
        private configSharedService: ConfigSharedService,
        private utilSubscribersService: UtilSubscribersService,
        private prontuarioService: ProntuarioService,
        private numberUtilsSharedService: NumberUtilsSharedService
    ) { }

    ngOnInit(): void {
        this.logger.init(ProntuarioUtilityNormeComponent.name);
        this.loaded = false;

        this.subscribers.userProfile = this.userService.profilo$.subscribe(data => {
            if (data != null) {
                this.riferimentoNormativo = new ProntuarioVO();
                this.enteModel = data.entiLegge;
                this.loadedEnte = true;
                this.loadAmbiti();
                this.loadAmbitiEliminabili();
                this.configProntuario = this.configSharedService.configProntuario;

                this.typeMessageMid = "INFO";
                this.messageMid = "In caso di Norma, Articolo o Comma già presente, la data di fine validità non verrà modificata. Per modificare tali date, utilizzare la funzione di modifica dei Riferimenti Normativi esistenti"

                this.loaded = true;
            }
        });
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
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

    loadAmbiti() {
        this.loadedAmbito = false;
        this.prontuarioService.getAmbiti().subscribe(ambiti => {
            this.ambitoModel = ambiti;
            this.loadedAmbito = true;
        });
    }

    loadAmbitiEliminabili() {
        this.loadedAmbitoEliminabile = false;
        this.prontuarioService.getAmbitiEliminabili().subscribe(ambitiEl => {
            this.ambitoEliminabileModel = ambitiEl;
            this.loadedAmbitoEliminabile = true;
        });
    }

    salvaAmbito() {
        this.loaded = false;

        //chiamo il Back End per salvare il nuovo ambito
        this.prontuarioService.salvaAmbito(this.nuovoAmbito).subscribe(data => {          
            
            //aggiorno l'elenco di ambiti
            this.loadAmbiti();
            this.loadAmbitiEliminabili();
            this.nuovoAmbito = new AmbitoVO();
            this.riferimentoNormativo.ambito = null;
            this.manageMessageTop("Ambito creato con successo", "SUCCESS");
            this.loaded = true;
            this.scrollEnable = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, "DANGER");
            }
            this.logger.error("Errore durante il salvataggio dell'ambito");
            this.loaded = true;
            this.scrollEnable = true;
        });
    }    

    eliminaAmbito() {
        this.loaded = false;
        //chiamo il Back End per eliminare l'ambito
        this.prontuarioService.eliminaAmbito(this.ambitoDaEliminare.id).subscribe(data => {
            //aggiorno l'elenco di ambiti eliminabili     
            this.loadAmbitiEliminabili();
            this.loadAmbiti();
            this.annullaRiferimentoNormativo();
            this.ambitoDaEliminare = null;
            this.riferimentoNormativo.ambito = null;
            this.manageMessageTop("Ambito eliminato con successo", "SUCCESS");
            this.loaded = true;
            this.scrollEnable = true;
        }, err => {
            this.logger.error("Errore durante l'eliminazione dell'ambito");
            this.loaded = true;
            this.manageMessageTop("Ambito non eliminato", "DANGER");
            this.scrollEnable = true;
        });
    }

    changeCriterioDiRicerca() {
        this.hasChanged = true;        
    }

    disable(action:String){
        if(action == "salva"){
            if((this.ambitoDaEliminare != null) && (this.ambitoDaEliminare.denominazione != null ||
                this.ambitoDaEliminare.acronimo != null) ||
                (this.riferimentoNormativo != null && 
                    (this.riferimentoNormativo.enteLegge != null || (this.riferimentoNormativo.ambito != null && 
                        this.riferimentoNormativo.ambito.acronimo != null)))){

                return true;
            }

        }

        if(action == "elimina"){
            if( this.nuovoAmbito != null && (this.nuovoAmbito.denominazione != null && this.nuovoAmbito.denominazione.length != 0) ||
                (this.nuovoAmbito.acronimo != null && this.nuovoAmbito.acronimo.length != 0)||
                (this.riferimentoNormativo != null && (this.riferimentoNormativo.enteLegge != null || (this.riferimentoNormativo.ambito != null && 
                    this.riferimentoNormativo.ambito.acronimo != null)))){
                return true;
            }
        }

        if(action == "modifica"){
            if(this.nuovoAmbito != null && (this.nuovoAmbito.denominazione != null && this.nuovoAmbito.denominazione.length != 0) ||
                 (this.nuovoAmbito.acronimo != null && this.nuovoAmbito.acronimo.length != 0)||
                (this.ambitoDaEliminare != null && (this.ambitoDaEliminare.denominazione != null || this.ambitoDaEliminare.acronimo != null))){

                return true;
            }
 
        }

       return false;
  
    }

    clearModifica(){   
       
        this.subscribers.userProfile = this.userService.profilo$.subscribe(data => {
            if (data != null) {
                this.riferimentoNormativo = new ProntuarioVO();
                this.enteModel = data.entiLegge;
                this.loadedEnte = true;
                this.loadAmbiti();
                this.configProntuario = this.configSharedService.configProntuario;

                this.typeMessageMid = "INFO";
                this.messageMid = "In caso di Norma, Articolo o Comma già presente, la data di fine validità non verrà modificata. Per modificare tali date, utilizzare la funzione di modifica dei Riferimenti Normativi esistenti"

                this.loaded = true;
            }
        });

        this.ambitoModel = new Array<AmbitoVO>();

        this.riferimentoNormativo = new ProntuarioVO();

        this.enableTable = false;   
      
    }

    clearElimina(){
        this.ambitoEliminabileModel = new Array<AmbitoVO>() 
        this.ambitoDaEliminare = null;
        this.loadAmbitiEliminabili();
    }


    nuovoRiferimentoNormativo() {
        let ente: EnteVO = this.riferimentoNormativo.enteLegge
        let ambito: AmbitoVO = this.riferimentoNormativo.ambito;
        this.riferimentoNormativo = new ProntuarioVO();
        this.riferimentoNormativo.enteLegge = ente;
        this.riferimentoNormativo.ambito = ambito;

        this.isEdit = false;
        this.loadedRiferimento = true;
        //se la tabella non è stata caricata o se è relativa ad un'altra ricerca diversa, effettuo anche la ricerca
        //se si modifica questa funzionalità, modificare anche l'aggiunta in tabella al salvataggio
        if (!this.loadedTabella || this.hasChanged) {
            this.cercaRiferimentoNormativo();
            this.hasChanged = false;
        }
    }

    cercaRiferimentoNormativo() {
        this.isSearching = true;
        this.loadedTabella = false;
        let ricercaLeggeProntuarioRequest = new RicercaLeggeProntuarioRequest();
        ricercaLeggeProntuarioRequest.ambito = this.riferimentoNormativo.ambito
        ricercaLeggeProntuarioRequest.enteLegge = this.riferimentoNormativo.enteLegge;
        this.prontuarioService.ricercaLeggeProntuario(ricercaLeggeProntuarioRequest).subscribe(data => {
            this.riferimentoNormativoModel = data;
            this.isSearching = false;
            this.loadedTabella = true;
            this.enableTable = true;
        });
    }

    annullaRiferimentoNormativo() {
        let tmpEnte = this.riferimentoNormativo.enteLegge;
        let tmpAmbito = this.riferimentoNormativo.ambito;
        this.riferimentoNormativo = new ProntuarioVO();
        this.riferimentoNormativo.enteLegge = tmpEnte;
        this.riferimentoNormativo.ambito = tmpAmbito;
        this.loadedRiferimento = false;
    }

    controllaSalvaRiferimentoNormativo() {
        if(this.riferimentoNormativo.articolo.dataInizioValidita){
            if(!this.riferimentoNormativo.comma.dataInizioValidita){
                this.riferimentoNormativo.comma.dataInizioValidita = this.riferimentoNormativo.articolo.dataInizioValidita;
            }
            if(!this.riferimentoNormativo.lettera.dataInizioValidita){
                this.riferimentoNormativo.lettera.dataInizioValidita = this.riferimentoNormativo.articolo.dataInizioValidita;
            }
        }
    }

    salvaRiferimentoNormativo() {
        this.loaded = false;
        this.loadedTabella = false;
        this.controllaSalvaRiferimentoNormativo();
        if (!this.riferimentoNormativo.lettera.id) {
            //chiamo il Back End per salvare il nuovo Riferimento Normativo
            this.prontuarioService.salvaLeggeProntuario(this.riferimentoNormativo).subscribe(data => {
                this.riferimentoNormativo = data;
                //aggiungo in tabella il riferimento che mi restituisce il BE (assumo che la tabella mostrata sotto sia coerente al riferimento che sto inserendo)
                this.riferimentoNormativoModel.push(this.riferimentoNormativo);
                this.annullaRiferimentoNormativo();
                this.loadAmbitiEliminabili();
                this.cercaRiferimentoNormativo();
                this.loadedTabella = true;
                this.loaded = true;
                this.manageMessageTop("Riferimento inserito con successo", "SUCCESS");
                this.scrollEnable = true;
            }, err => {
                //if (err instanceof ExceptionVO) {
                    this.manageMessageTop(err.message, err.type);
                //}
                this.logger.error("Errore durante il salvataggio di un nuovo Riferimento Normativo");
                this.loaded = true;
                this.scrollEnable = true;
            });
        } else {
            //chiamo il Back End per modificare il Riferimento Normativo caricato
            this.prontuarioService.salvaLeggeProntuario(this.riferimentoNormativo).subscribe(data => {
                this.riferimentoNormativo = data;
                //aggiungo in tabella il riferimento che mi restituisce il BE (assumo che id lettera non sia cambiato)
                let i: number = this.riferimentoNormativoModel.findIndex(rif => rif.lettera.id === this.riferimentoNormativo.lettera.id);
                this.riferimentoNormativoModel[i] = this.riferimentoNormativo;
                this.annullaRiferimentoNormativo();
                this.loadAmbitiEliminabili();
                this.cercaRiferimentoNormativo();
                this.loadedTabella = true;
                this.loaded = true;
                this.manageMessageTop("Riferimento modificato con successo", "SUCCESS");
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.manageMessageTop(err.message, err.type);
                }
                this.logger.error("Errore durante la modifica di un Riferimento Normativo");
                this.loaded = true;
                this.scrollEnable = true;
            });
        }
    }

    editRiferimentoNormativo(el: ProntuarioVO) {
        this.isEdit = true;
        this.annullaRiferimentoNormativo();
        this.riferimentoNormativo = JSON.parse(JSON.stringify(el));
        this.loadedRiferimento = true;
    }

    deleteFromTable(el: ProntuarioVO) {
        let tmp: ProntuarioVO = JSON.parse(JSON.stringify(this.riferimentoNormativo));
        this.annullaRiferimentoNormativo();
        this.riferimentoNormativo = JSON.parse(JSON.stringify(el));
        this.richiediEliminazioneRiferimentoNormativo('M');
    }

    richiediEliminazioneRiferimentoNormativo(action:string) {
        this.generaMessaggio(action);
        this.buttonAnnullaText = "Annulla";
        this.buttonConfirmText = "Conferma";

        //mostro un messaggio
        this.sharedDialog.open();

        //unsubscribe
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

        
        //premo "Conferma"
        this.subscribers.save = this.sharedDialog.salvaAction.subscribe(data => {
            if(action == 'M')
                this.eliminaRiferimentoNormativo();
            else if(action == 'E')
                this.eliminaAmbito();
        }, err => {
            this.logger.error(err);
        });
       
        //premo "Annulla"
        this.subscribers.close = this.sharedDialog.closeAction.subscribe(data => {
            this.subMessages = new Array<string>();
        }, err => {
            this.logger.error(err);
        });
        
    }

    generaMessaggio(action:string) {
        this.subMessages = new Array<string>();
        if(action == 'M'){
            let incipit: string = "Si intende eliminare il seguente Riferimento Normativo?";

            this.subMessages.push(incipit);
            this.subMessages.push("Norma: " + this.riferimentoNormativo.norma.denominazione);
            this.subMessages.push("Articolo: " + this.riferimentoNormativo.articolo.denominazione);
            this.subMessages.push("Comma: " + this.riferimentoNormativo.comma.denominazione);
            this.subMessages.push("Lettera: " + this.riferimentoNormativo.lettera.denominazione);
        } else  if(action == 'E'){
            let incipit: string = "Si intende eliminare il seguente Ambito?";

            this.subMessages.push(incipit);
            this.subMessages.push("Ambito: " + this.ambitoDaEliminare.denominazione);
            this.subMessages.push("Acronimo: " + this.ambitoDaEliminare.acronimo);
        }
    }

    eliminaRiferimentoNormativo() {
        this.loadedTabella = false;
        this.loadedRiferimento = false;
        this.loaded = false;

        //chiamo il Back End per eliminare il Riferimento Normativo
        this.prontuarioService.eliminaLeggeProntuario(this.riferimentoNormativo.lettera.id).subscribe(data => {
            this.riferimentoNormativoModel.splice(this.riferimentoNormativoModel.indexOf(this.riferimentoNormativo), 1);
            this.loadAmbitiEliminabili();
            this.loadedTabella = true;
            this.loaded = true;
            this.manageMessageTop("Riferimento eliminato con successo", "SUCCESS");
            this.scrollEnable = true;
        }, err => {
            this.logger.error("Errore durante l'eliminazione del Riferimento Normativo");
            this.scrollEnable = true;
        });
    }

    manageDatePicker(event: any, i: number) {
        var str: string = '#datetimepicker' + i.toString();
        if ($(str).length) {
            $(str).datetimepicker({
                format: 'DD/MM/YYYY'
            });
        }
        if (event != null) {
            switch (i) {
                case (1):
                    this.riferimentoNormativo.lettera.scadenzaImporti = event.srcElement.value;
                    break;
                case (2):
                    this.riferimentoNormativo.norma.dataFineValidita = event.srcElement.value;
                    break;
                case (3):
                    this.riferimentoNormativo.articolo.dataFineValidita = event.srcElement.value;
                    break;
                case (4):
                    this.riferimentoNormativo.comma.dataFineValidita = event.srcElement.value;
                    break;
                case (5):
                    this.riferimentoNormativo.lettera.dataFineValidita = event.srcElement.value;
                    break;
                case (6):
                    this.riferimentoNormativo.norma.dataInizioValidita = event.srcElement.value;
                    break;
                case (7):
                    this.riferimentoNormativo.articolo.dataInizioValidita = event.srcElement.value;
                    break;
                case (8):
                    this.riferimentoNormativo.comma.dataInizioValidita = event.srcElement.value;
                    break;
                case (9):
                    this.riferimentoNormativo.lettera.dataInizioValidita = event.srcElement.value;
                    break;
            }
        }
    }

    isKeyPressed(code: number): boolean {
        return this.numberUtilsSharedService.numberValid(code);
    }

    byId(o1: SelectVO, o2: SelectVO) {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    scrollEnable: boolean;
    ngAfterViewChecked() {
        let i: number;
        for (i = 1; i < 6; i++) this.manageDatePicker(null, i);

        let out: HTMLElement = document.getElementById("scrollTop");
        if (this.loaded && this.scrollEnable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }

    }

    isDisabled(value: String) {
        if (value == "DATALETTERA")
            return this.riferimentoNormativo.lettera.denominazione == null || this.riferimentoNormativo.lettera.denominazione == "" || this.riferimentoNormativo.lettera.denominazione == "NO LETTERA";
        if (value == "DATACOMMA")
            return this.riferimentoNormativo.comma.denominazione == null || this.riferimentoNormativo.comma.denominazione == "" || this.riferimentoNormativo.comma.denominazione == "NO COMMA";
        return false;
    }

    clearAmbito() {
        this.nuovoAmbito = new AmbitoVO();
    }


    ngOnDestroy(): void {
        this.logger.destroy(ProntuarioUtilityNormeComponent.name);
    }

}