import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

@Component({
    selector: 'shared-soggetto-ruolo',
    templateUrl: './shared-soggetto-ruolo.html'
})
export class SharedSoggettoRuolo implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    @Input("ruolo") ruolo: string;
    @Input("nota") nota: string;

    public isNota: boolean = false;

    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        if(this.nota!="null")
            this.isNota = true;
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoRuolo.name);
    }

}