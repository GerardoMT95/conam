import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaService } from "../../service/shared-ordinanza.service";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { saveAs } from 'file-saver';

@Component({
    selector: 'shared-ordinanza-dettaglio-documenti',
    templateUrl: './shared-ordinanza-dettaglio-documenti.component.html'
})
export class SharedOrdinanzaDettaglioDocumenti implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input()
    idOrdinanza: number;
    @Input()
    config: Config;
    @Input()
    isFirstDownloadBollettini: boolean;

    //pagina
    loaded: boolean;
    documenti: Array<AllegatoVO> = new Array<AllegatoVO>();

    constructor(
        private logger: LoggerService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private allegatoSharedService: AllegatoSharedService
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedOrdinanzaDettaglioDocumenti.name);
        this.sharedOrdinanzaService.getDocumentiOrdinanza(this.idOrdinanza).subscribe(data => {
            this.loaded = true;
            this.documenti = data;
            this.documenti.forEach(all => {
                all.theUrl = new MyUrl(all.nome, null);
            });
        });
    }

    getAllegato(el: AllegatoVO) {
        if (!el.id) return;
        this.logger.info("Richiesto download dell'allegato " + el.id);
        this.subscribers.allegatoDowload = this.allegatoSharedService.getAllegato(el.id).subscribe(
            res => saveAs(res, el.nome || 'Unknown')
        );
    }

    ngAfterViewChecked(){
        if(this.isFirstDownloadBollettini){
            this.ngOnInit();
            this.isFirstDownloadBollettini = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedOrdinanzaDettaglioDocumenti.name);
    }
}
