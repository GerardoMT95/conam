import { Component, OnInit, OnDestroy, Input, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedSoggettoDati } from "../shared-soggetto-dati/shared-soggetto-dati";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { initDomAdapter } from "@angular/platform-browser/src/browser";
import { SharedSoggettoDatiResidenza } from "../shared-soggetto-dati-residenza/shared-soggetto-dati-residenza";
import { SharedSoggettoRuolo } from "../shared-soggetto-ruolo/shared-soggetto-ruolo";




@Component({
    selector: 'shared-soggetto-riepilogo',
    templateUrl: './shared-soggetto-riepilogo.component.html',
})
export class SharedSoggettoRiepilogoComponent implements OnInit, OnDestroy {

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    @Input("ruolo") ruolo: string;
    @Input("nota") nota: string;

    public subscribers: any = {};

    @ViewChild(SharedSoggettoDati)
    sharedSoggettoDati: SharedSoggettoDati;
    @ViewChild(SharedSoggettoDatiResidenza)
    sharedSoggettoDatiResidenza: SharedSoggettoDatiResidenza;
    @ViewChild(SharedSoggettoRuolo)
    sharedSoggettoRuolo: SharedSoggettoRuolo;


    constructor(
        private logger: LoggerService,

    ) { }

    ngOnInit(): void {
    }



    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoRiepilogoComponent.name);
    }

}