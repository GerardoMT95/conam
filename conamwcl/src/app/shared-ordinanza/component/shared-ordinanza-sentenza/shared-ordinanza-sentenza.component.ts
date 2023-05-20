import { Component, OnInit, OnDestroy, Input } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SharedOrdinanzaService } from "../../service/shared-ordinanza.service";
import { DatiSentenzaResponse } from "../../../commons/response/ordinanza/dati-sentenza-response";

@Component({
    selector: 'shared-ordinanza-sentenza',
    templateUrl: './shared-ordinanza-sentenza.component.html'
})
export class SharedOrdinanzaSentenza implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input()
    public idOrdinanzaVerbaleSoggetto: number;
    public sentenza: DatiSentenzaResponse;
    public loaded: boolean;



    constructor(
        private logger: LoggerService,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedOrdinanzaSentenza.name);
        this.loadSentenza();
    }


    loadSentenza() {
        this.loaded = false;
        this.subscribers.sentenza = this.sharedOrdinanzaService.getDatiSentenza(this.idOrdinanzaVerbaleSoggetto).subscribe(data => {
            this.sentenza = data;
            this.loaded = true;
        }, err => {
            this.loaded = true;
            this.logger.error("Errore durante il caricamento della sentenza");
        });
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedOrdinanzaSentenza.name);
    }

}