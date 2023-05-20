import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'pagamenti-riconcilia-verbale-riepilogo',
    templateUrl: './pagamenti-riconcilia-verbale-riepilogo.component.html',
    styleUrls: ['./pagamenti-riconcilia-verbale-riepilogo.component.scss']
})
export class PagamentiRiconciliaVerbaleRiepilogoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public idVerbale: number;
    public allegatoEnable: boolean;

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaVerbaleRiepilogoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'allegato')
                this.manageMessage("Pagamento inserito con successo. Per riconciliare il fascicolo, ricercarlo dalla funzionalità 'Riepilogo Fascicolo'.", 'SUCCESS');
                if (this.activatedRoute.snapshot.paramMap.get('action') == 'parziale')
                this.manageMessage("Pagamento parziale inserito con successo.", 'SUCCESS');
           
            this.getAggiugiAllegatoRiconciliazioneVerbale();
        });
    }

    getAggiugiAllegatoRiconciliazioneVerbale() {
        this.subscribers.riconcilia = this.sharedVerbaleService.isTipoAllegatoAllegabile(this.idVerbale, Constants.RICEVUTA_PAGAMENTO_VERBALE).subscribe(data => {
            this.allegatoEnable = data;
        })
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

    goToAllegatoPagamento() {
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_VERBALE_ALLEGATO_PAGAMENTO + this.idVerbale);
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaVerbaleRiepilogoComponent.name);
    }

}