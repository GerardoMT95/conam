import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { NgForm } from "@angular/forms";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";

@Component({
    selector: 'shared-template-intestazione',
    templateUrl: './shared-template-intestazione.component.html',
    styleUrls: ['./shared-template-intestazione.component.scss']
})
export class SharedTemplateIntestazioneComponent implements OnInit {

    public subscribers: any = {};


    //gestione anteprima
    public isAnteprima: boolean;

    //campi
    public dataOdierna: string;
    public viewMailTributiField: boolean;

    //input
    @Input()
    datiTemplate: DatiTemplateVO;
    @Input()
    datiCompilati: DatiTemplateCompilatiVO;

    //output
    @Output()
    formValid: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedTemplateIntestazioneComponent.name);
        this.dataOdierna = new Date().toLocaleDateString();
        this.formValid.next(true);
        if(this.datiTemplate.mailSettoreTributi!=null){
            this.viewMailTributiField=true;
        }else{
            this.viewMailTributiField=false;
        }
    }

    setAnteprima(flag: boolean) {
        this.isAnteprima = flag;
    }


 

}