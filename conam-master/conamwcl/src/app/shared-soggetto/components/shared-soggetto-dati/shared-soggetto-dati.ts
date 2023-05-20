import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

@Component({
    selector: 'shared-soggetto-dati',
    templateUrl: './shared-soggetto-dati.html'
})
export class SharedSoggettoDati implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    
    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
       
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoDati.name);
    }

}