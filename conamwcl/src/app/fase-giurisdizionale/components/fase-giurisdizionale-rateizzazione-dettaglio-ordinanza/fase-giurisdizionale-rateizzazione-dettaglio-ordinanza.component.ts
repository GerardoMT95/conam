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
import { timer } from "rxjs/observable/timer";
import { TemplateService } from "../../../template/services/template.service";

@Component({
    selector: 'fase-giurisdizionale-rateizzazione-dettaglio-ordinanza',
    templateUrl: './fase-giurisdizionale-rateizzazione-dettaglio-ordinanza.component.html',
})
export class FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;

    idOrdinanza: number;
    configSoggetti: Config;
    configAllegati: Config;
    aggiungiIstanzaDisabled: boolean = true;
    aggiungiIstanza: boolean = true;
    aggiungiIstanzaAllegati: boolean = true;
    aggiungiIstanzaMessageView: boolean = true;
    aggiungiIstanzaMessage: string = '';
    aggiungiIstanzaSelected:Array<TableSoggettiOrdinanza>;

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
        private templateService: TemplateService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent.name);
        this.loaded = false;

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggettiIstanza(true, 1, this.isSelectable, this.showDetail,(el: any)=>true);
            this.loadAggiungiIstanzaMessage();
            this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
            this.loaded = true;
            // Logica pulsante messaggio per selezionare i soggetti
            this.aggiungiIstanzaDisabled = true;
            this.aggiungiIstanza = true;
            this.aggiungiIstanzaAllegati = false;
            this.aggiungiIstanzaMessageView = false;
            this.aggiungiIstanzaSelected = [];
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Salvataggio riuscito con successo", 'SUCCESS');
        });
    }

    loadAggiungiIstanzaMessage() {
        this.subscribers.alertWarning = this.templateService.getMessage('SOGGISTNOK').subscribe(data => {
            this.aggiungiIstanzaMessage = data.message;
        }, err => {
            this.logger.error("Errore nel recupero del messaggio");
        });
    }

    goDettaglio() {
        let ids: Array<number> = [];
        let i: number;
        for (i = 0; i < this.aggiungiIstanzaSelected.length; i++) {
            ids.push(this.aggiungiIstanzaSelected[i].idSoggettoOrdinanza);
        }
        this.faseGiurisdizionaleUtilService.setId(ids, this.idOrdinanza);
        this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_ALLEGATO_ORDINANZA);
    }
    
    onSelected(event: Array<TableSoggettiOrdinanza>) {
        this.aggiungiIstanzaSelected = event;
        let disabled:boolean = false;
        const selHasMaster:boolean=event.filter(item=>item.hasMasterIstanza).length>0;
        const selNotHasMaster:boolean=event.filter(item=>!item.hasMasterIstanza).length>0;
        if(selHasMaster){
            disabled = false;
            this.aggiungiIstanza = false;
            this.aggiungiIstanzaAllegati = true;
        }else if(selNotHasMaster){
            disabled = false;
            this.aggiungiIstanza = true;
            this.aggiungiIstanzaAllegati = false;
        }else{
            disabled = true;
            this.aggiungiIstanza = true;
            this.aggiungiIstanzaAllegati = false;
        }
        if(selHasMaster && selNotHasMaster){
            disabled = true;
            this.aggiungiIstanza = true;
            this.aggiungiIstanzaAllegati = false;
            this.aggiungiIstanzaMessageView = true;
        }else{
            this.aggiungiIstanzaMessageView = false;
        }
        this.aggiungiIstanzaDisabled = disabled;
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
        this.logger.destroy(FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent.name);
    }

}