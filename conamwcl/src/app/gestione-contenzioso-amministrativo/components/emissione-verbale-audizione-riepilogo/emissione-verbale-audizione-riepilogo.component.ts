import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "../../service/fase-giurisdizionale-verbale-audizione.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";
import { timer } from "rxjs/observable/timer";

@Component({
    selector: 'emissione-verbale-audizione-riepilogo',
    templateUrl: './emissione-verbale-audizione-riepilogo.component.html'
})
export class EmissioneVerbaleAudizioneRiepilogoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idVerbale: number;
    public allegatoEnable: boolean;
    public creaAllegatoEnable: boolean;
    loadedAction: boolean = true;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private faseGiurisdizionaleVerbaleAudizioneService: FaseGiurisdizionaleVerbaleAudizioneService,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
        this.logger.init(EmissioneVerbaleAudizioneRiepilogoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            this.getAggiungiVerbaleDiAudizione();  
            this. getCreaVerbaleDiAudizione();   
        });
        if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Verbale di audizione firmato caricato con successo", 'SUCCESS');
                this.sharedVerbaleService.getMessaggioManualeByIdVerbale(this.idVerbale).subscribe(data => {
                    if(data){
                      this.manageMessageTop(data.message, data.type)
                    }
                  
                 });
    }


    getAggiungiVerbaleDiAudizione() {
        this.subscribers.getAggiungiVerbaleDiAudizione = this.sharedVerbaleService.isTipoAllegatoAllegabile(this.idVerbale, Constants.VERBALE_AUDIZIONE).subscribe(data => {
            this.allegatoEnable = data;
        });
    }

    getCreaVerbaleDiAudizione() {
        this.subscribers.getCreaVerbaleDiAudizione = this.sharedVerbaleService.isEnableCaricaVerbaleAudizione(this.idVerbale).subscribe(data => {
            this.creaAllegatoEnable = data;
        });
    }
    
    

    goToCreaVerbaleAudizione() {
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_CREA + this.idVerbale);
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
        this.logger.destroy(EmissioneVerbaleAudizioneRiepilogoComponent.name);
    }

    CaricaVerbaleAudizione(){
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_ALLEGA + "0/" + this.idVerbale);
    }

}