import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { Routing } from "../../../commons/routing";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SelectVO, } from "../../../commons/vo/select-vo";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "../../service/fase-giurisdizionale-verbale-audizione.service";

declare var $: any;


@Component({
    selector: 'convocazione-audizione-crea',
    templateUrl: './convocazione-audizione-crea.component.html'
})
export class ConvocazioneAudizioneCreaGestContAmministrativoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    config: Config;
    loaded: boolean;
    isVerbaleConvocazione : boolean = true;
    idVerbale: number;
    soggettiArray: Array<TableSoggettiVerbale>;


    isSelectable: (el: TableSoggettiVerbale) => boolean = (el: TableSoggettiVerbale) => {
        if (el.verbaleAudizioneCreato)
            return false;
        else
            return true;
    }

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleConfigService: SharedVerbaleConfigService,
        private faseGiurisdizionaleVerbaleAudizioneService: FaseGiurisdizionaleVerbaleAudizioneService,
    ) { }

    ngOnInit(): void {
        this.logger.init(ConvocazioneAudizioneCreaGestContAmministrativoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))
                this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RICERCA);
        });
        this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(true, 1, this.isSelectable, false);
        this.soggettiArray = new Array();
        this.loaded = true;
        this.isVerbaleConvocazione = true;
    }

    ngOnDestroy(): void {
        this.logger.destroy(ConvocazioneAudizioneCreaGestContAmministrativoComponent.name);
    }

    addToArraySoggettiSelezionati(e: Array<TableSoggettiVerbale>) {
        this.soggettiArray = e;
    }

    scrollEnable: boolean;
    ngAfterViewChecked() {
        let out: HTMLElement = document.getElementById("scrollTop");
        if (this.loaded && this.scrollEnable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    byId(o1: SelectVO, o2: SelectVO) {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    goToEmittiConvocazioneAudizione() {
        this.faseGiurisdizionaleVerbaleAudizioneService.idVerbaleSoggettoList = this.soggettiArray.map(s => s.idVerbaleSoggetto);
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_TEMPLATE_LETTERA + this.idVerbale);
    }




}