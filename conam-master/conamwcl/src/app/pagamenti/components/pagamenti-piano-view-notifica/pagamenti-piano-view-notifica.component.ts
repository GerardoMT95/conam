import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { Routing } from "../../../commons/routing";
import { SharedRateConfigService } from "../../../shared-pagamenti/services/shared-pagamenti-config.service";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { PagamentiService } from "../../services/pagamenti.service";

declare var $: any;

@Component({
    selector: 'pagamenti-piano-view-notifica',
    templateUrl: './pagamenti-piano-view-notifica.component.html',
})
export class PagamentiPianoViewNotificaComponent implements OnInit, OnDestroy {

    public idPiano: number;
    public subscribers: any = {};
    public configSoggetti: Config;
    public configRate: Config;


    public loaded: boolean;

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
        this.logger.init(PagamentiPianoViewNotificaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idPiano = +params['idPiano'];
            if (this.idPiano)
                this.loadPiano();
            else
                this.router.navigateByUrl(Routing.PAGAMENTI_RICERCA_PIANO);
        });
        this.configRate = this.sharedRateConfigService.getConfigRateView();
        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, null, 0, null, null,(el: any)=>true);
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

    goToPianoView() {
        this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_VIEW + this.idPiano)
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiPianoViewNotificaComponent.name);
    }

    //JIRA - Gestione Notifica
    save(event: number) {
        //Non e' necessario ricaricare inquanto le info che devono essere aggiornate riguaradanmo solo l'oggetto notifica        
        window.location.reload();
    }
}