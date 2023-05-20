import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { runInThisContext } from "vm";

@Component({
    selector: 'shared-soggetto-dati-residenza',
    templateUrl: './shared-soggetto-dati-residenza.html'
})
export class SharedSoggettoDatiResidenza implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    
    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoDatiResidenza.name);
    }

}