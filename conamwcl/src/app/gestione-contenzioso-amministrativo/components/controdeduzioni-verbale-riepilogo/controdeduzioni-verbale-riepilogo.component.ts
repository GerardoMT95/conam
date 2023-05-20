import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";
import { timer } from "rxjs/observable/timer";

@Component({
    selector: 'controdeduzioni-verbale-riepilogo',
    templateUrl: './controdeduzioni-verbale-riepilogo.component.html',
})
export class ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idVerbale: number;
    public allegatoEnable: boolean;


    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleService: SharedVerbaleService,

    ) { }

    ngOnInit(): void {
        this.logger.init(ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))
                this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RICERCA);
            this.isTipoAllegatoAllegabile();

            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Controdeduzione allegata con successo", 'SUCCESS');

        });
    }


    isTipoAllegatoAllegabile() {
        this.subscribers.riconcilia = this.sharedVerbaleService.isTipoAllegatoAllegabile(this.idVerbale, Constants.CONTRODEDUZIONE).subscribe(data => {
            this.allegatoEnable = data;
        })
    }


    goToAggiugiControdeduzioni() {
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_ALLEGATO + this.idVerbale);
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
        this.logger.destroy(ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent.name);
    }

}