import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { Routing } from "../../../commons/routing";
import { FaseGiurisdizionaleOrdinanzaService } from "../../service/fase-giurisdizionale-ordinanza.service";
import { NotificaVO } from "../../../commons/vo/notifica/notifica-vo";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component"

declare var $: any;

@Component({
    selector: 'ordinanza-view-notifica',
    templateUrl: './ordinanza-view-notifica.component.html',
})
export class OrdinanzaViewNotificaGestContAmministrativoComponent implements OnInit, OnDestroy {

    public idOrdinanza: number;
    public subscribers: any = {};
    public configSoggetti: Config;
    public showAnnullamentoParts: boolean = false;
    public itsAnnullamento: string; 
    @ViewChild(SharedOrdinanzaDettaglio)
    sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    ) { }

    ngOnInit(): void {
        this.itsAnnullamento = ''
        this.logger.init(OrdinanzaViewNotificaGestContAmministrativoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['id'];
            if (this.activatedRoute.snapshot.paramMap.get("azione")) {             
                this.itsAnnullamento  = this.activatedRoute.snapshot.paramMap.get("azione");
                
              } 
              if (this.itsAnnullamento === 'annullamento') {
                  this.showAnnullamentoParts =  true;
              }     

        });

        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, null, 0, null, null,(el: any)=>true);

        this.sharedOrdinanzaDettaglio.visualizzaImportoNotifica=false;
    }

    goToRiepilogoOrdinanza() {
        if(this.showAnnullamentoParts){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + this.idOrdinanza, {azione: this.itsAnnullamento}]);
        }
        else{
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + this.idOrdinanza)
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(OrdinanzaViewNotificaGestContAmministrativoComponent.name);
    }

    //JIRA - Gestione Notifica
    //------------------------------------
    goToVisualizzaNotifica() {
        if(this.showAnnullamentoParts){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA + this.idOrdinanza, {azione: this.itsAnnullamento}]);
        }
        else{
            this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA + this.idOrdinanza);        

        }
    }

    save(event: number) {        
        window.location.reload();
    }
    //------------------------------------
}