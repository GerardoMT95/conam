import { Component, OnInit, OnDestroy, ViewChild, Input, Output, EventEmitter } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { NotificaVO } from "../../../commons/vo/notifica/notifica-vo";
//JIRA - Gestione Notifica
import { SharedInserimentoNotificaComponent } from "../../../shared-notifica/components/shared-inserimento-notifica/shared-inserimento-notifica.component";
import { SharedViewNotificaComponent } from "../../../shared-notifica/components/shared-view-notifica/shared-view-notifica.component";
import { StatoSollecitoVO } from "../../../commons/vo/select-vo";
import { NgForm } from "@angular/forms";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";

declare var $: any;

class SollecitoExtended extends SollecitoVO{
    numeroRateDaSollecitare?:number;
    importoTotalePagato?:number;
    numeroRatePagate?:number;

}

@Component({
    selector: 'shared-riscossione-sollecito-dettaglio-rate',
    templateUrl: './shared-riscossione-sollecito-dettaglio-rate.component.html',
})
export class SharedRiscossioneSollecitoDettaglioRateComponent implements OnInit, OnDestroy {
    @Output()
    onChangeData: EventEmitter<any> = new EventEmitter<any>();
    @ViewChild('creaSollecitoDettaglioForm')
    private creaSollecitoDettaglioForm: NgForm;

    @ViewChild(SharedInserimentoNotificaComponent) insertNotifica: SharedInserimentoNotificaComponent;
    @ViewChild(SharedViewNotificaComponent) viewtNotifica: SharedViewNotificaComponent;

    public subscribers: any = {};

    @Input()
    piano: PianoRateizzazioneVO;

    @Input()
    sollecito: SollecitoVO;
    @Input()
    flgModifica: boolean;
    @Input()
    pregresso: boolean = false; 
    @Input()
    isCreaSollecito: boolean = false; 
    
    sollecitoExtended: SollecitoExtended;
    errorDate: boolean = false;

    loadedstatisoll: boolean = false;
    public statiSollModel: Array<StatoSollecitoVO> = new Array<StatoSollecitoVO>();    
    
    //JIRA - Gestione Notifica
    isImportoNotificaInserito: boolean = true;   
    flgModificaNotifica: boolean; 

    constructor(
        private logger: LoggerService,
        private numberUtilsSharedService: NumberUtilsSharedService
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedRiscossioneSollecitoDettaglioRateComponent.name);
        this.createSollecitoExtended();
        this.errorDate = false;
    }

    createSollecitoExtended():void{
        const baseExended={
            numeroRateDaSollecitare:0,
            importoTotalePagato:0,
            numeroRatePagate:0
        }
        
        let importoSollecitato=0;
        this.sollecitoExtended={...this.sollecito,...baseExended};
        this.piano.rate.forEach(
            element =>{
                if(element.stato.id===2 || element.stato.id===3){
                    this.sollecitoExtended.importoTotalePagato+=element.importoRata;
                    this.sollecitoExtended.numeroRatePagate++;
                }else if(element.stato.id===1){
                    
                    importoSollecitato+=element.importoRata;
                    this.sollecitoExtended.numeroRateDaSollecitare++;
                }
            }    
        );
        this.sollecito.importoSollecitato = importoSollecitato + this.piano.importoSpeseNotifica;
    }


    onChangeDataEmitter(valid:string){
        let obj:any={};
        obj.valid=true;
        switch (valid) {
            case "VALID":
              obj.valid=true;
              break;
            case "INVALID":
              obj.valid=false;
              break;
        }
        this.onChangeData.emit(obj);
    }
    ngAfterViewChecked() {
        this.manageDatePicker(null, 1);
        this.manageDatePicker(null, 2);
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
                    this.sollecito.dataScadenza = event.srcElement.value;
                    this.sollecito.dataFineValidita = event.srcElement.value;
                    break;
                case (2):
                    this.sollecito.dataFineValidita = event.srcElement.value;
                    break;
            }
        }
        if(this.sollecito.dataScadenza && this.sollecito.dataFineValidita && this.isAfter(this.sollecito.dataScadenza,this.sollecito.dataFineValidita)){
            this.errorDate = true;
            this.onChangeDataEmitter('INVALID');
        }else{
            this.errorDate = false;
            this.onChangeDataEmitter('VALID');
        }

    }

    //restituisce true se la dataOra1 è successiva alla dataOra2
    isAfter(dataOra1: string, dataOra2: string): boolean {
        let DD1, DD2, MM1, MM2, YYYY1, YYYY2, HH1, HH2, mm1, mm2: string;

        YYYY1 = dataOra1.substring(6, 10);
        YYYY2 = dataOra2.substring(6, 10);

        if (YYYY1 > YYYY2) return true;
        else if (YYYY1 < YYYY2) return false;
        else {
        MM1 = dataOra1.substring(3, 5);
        MM2 = dataOra2.substring(3, 5);

        if (MM1 > MM2) return true;
        else if (MM1 < MM2) return false;
        else {
            DD1 = dataOra1.substring(0, 2);
            DD2 = dataOra2.substring(0, 2);

            if (DD1 > DD2) return true;
            else if (DD1 < DD2) return false;
            else {
            HH1 = dataOra1.substring(12, 14);
            HH2 = dataOra2.substring(12, 14);

            if (HH1 > HH2) return true;
            else if (HH1 < HH2) return false;
            else {
                mm1 = dataOra1.substring(15, 17);
                mm2 = dataOra2.substring(15, 17);

                if (mm1 > mm2) return true;
            }
            }
        }
        }

        return false;
    }

    isKeyPressed(code: number): boolean {
        return this.numberUtilsSharedService.numberValid(code);
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedRiscossioneSollecitoDettaglioRateComponent.name);
    }

    //JIRA - Gestione Notifica
    importNotificaInseritoHandler(valueEmitted){
        this.isImportoNotificaInserito = valueEmitted;
    }
    
    getNotificaObject(): NotificaVO{
        if(this.insertNotifica!=undefined||this.insertNotifica!=null)
            return this.insertNotifica.getNotificaObject();
        else
            return null;
    }

    aggiornaNotifica(){
        this.viewtNotifica.realoadNotifca();
    }
    
    

}