import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { RiscossioneService } from "../../services/riscossione.service";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { Routing } from "../../../commons/routing";



@Component({
    selector: 'riscossione-sollecito-ins-notifica',
    templateUrl: './riscossione-sollecito-ins-notifica.component.html',
})
export class RiscossioneSollecitoInsNotificaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;
    public idSollecito: number;
    public sollecito: SollecitoVO;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private riscossioneService: RiscossioneService,
    ) { }

    ngOnInit(): void {
        this.logger.destroy(RiscossioneSollecitoInsNotificaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idSollecito = +params['idSollecito'];
            this.loaded = true;
            this.loadSollecito();
        });

    }


    loadSollecito() {
        this.loaded = false;
        this.subscribers.getDettaglio = this.riscossioneService.getSollecitoById(this.idSollecito).subscribe(data => {
            this.sollecito = data;
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il caricamento del piano");
            this.loaded = true;
        });
    }

    goBack() {
        this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_DETTAGLIO);
    }

    save(event: number) {
        this.router.navigate([Routing.RISCOSSIONE_SOLLECITO_VIEW_NOTIFICA + this.idSollecito, { action: 'creazione' }], { replaceUrl: true });
    }






    ngOnDestroy(): void {
        this.logger.destroy(RiscossioneSollecitoInsNotificaComponent.name);
    }
}