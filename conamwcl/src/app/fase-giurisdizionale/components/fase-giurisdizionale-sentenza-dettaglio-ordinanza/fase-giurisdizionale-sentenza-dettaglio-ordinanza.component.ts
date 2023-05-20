import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { FaseGiurisdizionaleUtilService } from "../../services/fase-giurisdizionale-util.serivice";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";

@Component({
    selector: 'fase-giurisdizionale-sentenza-dettaglio-ordinanza',
    templateUrl: './fase-giurisdizionale-sentenza-dettaglio-ordinanza.component.html',
})
export class FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;

    idOrdinanza: number;
    configSoggetti: Config;
    configAllegati: Config;

    isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        if (el.statoSoggettoOrdinanza != null && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_INGIUNZIONE)
            return false;
        else
            return true;
    }

    showDetail: (arr: Array<TableSoggettiOrdinanza>) => boolean = (arr: Array<TableSoggettiOrdinanza>) => {
        let flag: boolean = true;
        arr.forEach(el => {
            if (flag && el.statoSoggettoOrdinanza != null && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_INGIUNZIONE)
                flag = false;
        });

        return flag;
    }

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private faseGiurisdizionaleUtilService: FaseGiurisdizionaleUtilService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private configSharedService: ConfigSharedService,
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent.name);
        this.loaded = false;

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "Aggiungi disposizione del giudice", 1, this.isSelectable, this.showDetail,(el: any)=>true);
            this.callAzioneOrdinanzaSoggetto();
            this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Disposizione del giudice allegata con successo", 'SUCCESS');
            this.loaded = true;
        });
    }


    callAzioneOrdinanzaSoggetto() {
        let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
        request.id = this.idOrdinanza;
        request.tipoDocumento = Constants.DISPOSIZIONE_DEL_GIUDICE;
        this.subscribers.callAzioneOrdinanza = this.sharedOrdinanzaService.azioneOrdinanzaSoggetto(request).subscribe(data => {
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(data.aggiungiAllegatoEnable, "Aggiungi disposizione del giudice", 1, this.isSelectable, this.showDetail,(el: any)=>true);
        });
    }

    onDettaglio(event: Array<TableSoggettiOrdinanza>) {
        let ids: Array<number> = [];
        let i: number;

        //idOrdinanzaVerbaleSoggetto
        for (i = 0; i < event.length; i++) {
            ids.push(event[i].idSoggettoOrdinanza);
        }
        this.faseGiurisdizionaleUtilService.setId(ids, this.idOrdinanza);
        this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_SENTENZA_ALLEGATO_ORDINANZA);
    }

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;


    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
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
        clearInterval(this.intervalTop);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent.name);
    }

}