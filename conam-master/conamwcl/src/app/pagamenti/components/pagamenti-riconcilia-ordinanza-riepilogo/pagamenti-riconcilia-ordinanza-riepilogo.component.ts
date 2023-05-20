import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { Constants } from "../../../commons/class/constants";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";

@Component({
    selector: 'pagamenti-riconcilia-ordinanza-riepilogo',
    templateUrl: './pagamenti-riconcilia-ordinanza-riepilogo.component.html',
})
export class PagamentiRiconciliaOrdinanzaRiepilogoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;

    idOrdinanza: number;
    configSoggetti: Config;
    configAllegati: Config;

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        let stato: number = el.statoSoggettoOrdinanza.id;
        if (stato == Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE || stato == Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE || stato == Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO)
            return false;
        else
            return true;
    }

    showDetail: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        let stato: number = el.statoSoggettoOrdinanza.id;
        if (stato == Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE || stato == Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE || stato == Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO)
            return false;
        return true;
    }

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private pagamentiUtilService: PagamentiUtilService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaOrdinanzaRiepilogoComponent.name);
        this.loaded = false;
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['id'];
            if (!this.idOrdinanza)
                this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_ORDINANZA_RICERCA);

            if (this.activatedRoute.snapshot.paramMap.get('action') == 'allegato')
                this.manageMessage("Pagamento registrato con successo", 'SUCCESS');

            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "Aggiungi Allegato Pagamento", 2, this.isSelectable, this.showDetail,(el: any)=>true);
            this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
            this.callAzioneOrdinanzaSoggetto()
        });
    }

    manageMessage(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 20//this.configService.getTimeoutMessagge();
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    callAzioneOrdinanzaSoggetto() {
        let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
        request.id = this.idOrdinanza;
        request.tipoDocumento = Constants.RICEVUTA_PAGAMENTO_ORDINANZA;
        this.subscribers.callAzioneOrdinanza = this.sharedOrdinanzaService.azioneOrdinanzaSoggetto(request).subscribe(data => {
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(data.aggiungiAllegatoEnable, "Aggiungi Allegato Pagamento", 2, this.isSelectable, this.showDetail,(el: any)=>true);
            this.loaded = true;
        });
    }

    onDettaglio(event: TableSoggettiOrdinanza) {
        this.pagamentiUtilService.setId([event.idSoggettoOrdinanza], this.idOrdinanza);
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_ORDINANZA_ALLEGATO);
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaOrdinanzaRiepilogoComponent.name);
    }

}