import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "../../service/fase-giurisdizionale-verbale-audizione.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'convocazione-audizione-riepilogo',
    templateUrl: './convocazione-audizione-riepilogo.component.html'
})
export class ConvocazioneAudizioneRiepilogoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idVerbale: number;
    public allegatoEnable: boolean;
    loadedAction: boolean = true;
    
    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private faseGiurisdizionaleVerbaleAudizioneService: FaseGiurisdizionaleVerbaleAudizioneService,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
      
        this.logger.init(ConvocazioneAudizioneRiepilogoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            this.getAggiungiVerbaleDiAudizione();
        });
        this.sharedVerbaleService.getMessaggioManualeByIdVerbale(this.idVerbale).subscribe(data => {
           if(data){
             this.manageMessage(data.message, data.type)
           }
        });
        
    }
    getAggiungiVerbaleDiAudizione() {
        this.subscribers.getAggiungiVerbaleDiAudizione = this.sharedVerbaleService.isTipoAllegatoAllegabile(this.idVerbale, Constants.CONVOCAZIONE_AUDIZIONE).subscribe(data => {
            this.allegatoEnable = data;
        });
    }

    goToCreaVerbaleAudizione() {
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_CREA + this.idVerbale);
    }
    goToCreaSoggettoAudizione() {
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_CREA_SOGG + this.idVerbale);
    }


    ngOnDestroy(): void {
        this.logger.destroy(ConvocazioneAudizioneRiepilogoComponent.name);
    }

    manageMessage(message: string, type: String) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 30//this.configService.getTimeoutMessagge();
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

}