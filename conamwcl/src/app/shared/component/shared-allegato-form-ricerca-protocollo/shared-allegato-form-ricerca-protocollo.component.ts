import { Component, OnInit, OnDestroy, Input, OnChanges, ElementRef, ViewChild, Output, EventEmitter } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";


@Component({
    selector: 'shared-allegato-form-ricerca-protocollo',
    templateUrl: './shared-allegato-form-ricerca-protocollo.component.html',
    styleUrls: ['./shared-allegato-form-ricerca-protocollo.component.scss']
}) 
export class SharedAllegatoRicercaProtocolloComponent implements OnInit, OnDestroy, OnChanges {
    @Input()
    numeroProtocollo = '';

    @Input()
    disabled:boolean = false;

    @Input()
    alertWarning:string = '';

    @Output()
    onSearch = new EventEmitter<any>();
    
    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedAllegatoRicercaProtocolloComponent.name);

    
    }

    ngOnChanges() {
        this.logger.change(SharedAllegatoRicercaProtocolloComponent.name);
    }


    ngOnDestroy(): void {
        this.logger.destroy(SharedAllegatoRicercaProtocolloComponent.name);
    }

    ricercaProtocollo(){
        let s:RicercaProtocolloRequest = new RicercaProtocolloRequest();
        s.numeroProtocollo = this.numeroProtocollo;
        this.onSearch.emit(s);
    }
}