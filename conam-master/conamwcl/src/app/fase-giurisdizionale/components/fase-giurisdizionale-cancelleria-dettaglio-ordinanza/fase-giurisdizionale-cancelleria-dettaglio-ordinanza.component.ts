import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { timer } from "rxjs/observable/timer";

@Component({
    selector: 'fase-giurisdizionale-cancelleria-dettaglio-ordinanza',
    templateUrl: './fase-giurisdizionale-cancelleria-dettaglio-ordinanza.component.html',
})
export class FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    idOrdinanza: number;
    configSoggetti: Config;
    configAllegati: Config;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private configSharedService: ConfigSharedService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "Aggiungi Comunicazioni Cancelleria", 0, null, null,(el: any)=>true);
            this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Comunicazione allegata con successo", 'SUCCESS');
        });
    }

    goToFaseGiurisdizionaleAllegatoCancelleria() {
        this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_CANCELLERIA_ALLEGATO_ORDINANZA + this.idOrdinanza.toString());
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        const source = timer(1000, 1000).take(31);
        this.subscribers.timer = source.subscribe(val => {
            if (val == 30)
                this.resetMessageTop();
        });
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent.name);
    }

}