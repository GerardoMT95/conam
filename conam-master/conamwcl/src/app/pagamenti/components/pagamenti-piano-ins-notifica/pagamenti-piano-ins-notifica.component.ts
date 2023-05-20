import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedRateConfigService } from "../../../shared-pagamenti/services/shared-pagamenti-config.service";
import { Routing } from "../../../commons/routing";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { PagamentiService } from "../../services/pagamenti.service";

@Component({
    selector: 'pagamenti-piano-ins-notifica',
    templateUrl: './pagamenti-piano-ins-notifica.component.html',
})
export class PagamentiPianoInsNotificaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public configSoggetti: Config;
    public configRate: Config;

    public loaded: boolean;

    idPiano: number;
    idSoggettiOrdinanza: Array<number>;

    public piano: PianoRateizzazioneVO;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private sharedRateConfigService: SharedRateConfigService,
        private pagamentiService: PagamentiService
    ) { }

    ngOnInit(): void {
        this.configRate = this.sharedRateConfigService.getConfigRateView();
        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, null, 0, null, null,(el: any)=>true);

        this.logger.init(PagamentiPianoInsNotificaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idPiano = +params['idPiano'];
            if (this.idPiano)
                this.loadPiano();
            else
                this.router.navigateByUrl(Routing.PAGAMENTI_RICERCA_PIANO);
        });
    }

    loadPiano() {
        this.loaded = false;
        this.subscribers.getDettaglio = this.pagamentiService.getDettaglioPianoById(this.idPiano, false).subscribe(data => {
            this.piano = data;
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il caricamento del piano");
            this.loaded = true;
        });
    }

    save(event: number) {
        this.router.navigate([Routing.PAGAMENTI_PIANO_VIEW_NOTIFICA + this.idPiano, { action: 'creazione' }], { replaceUrl: true });
    }


    ngOnDestroy(): void {
        this.logger.destroy(PagamentiPianoInsNotificaComponent.name);
    }
}