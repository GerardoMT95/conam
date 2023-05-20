import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { Constants } from "../../../commons/class/constants";
import { Config } from "../../../shared/module/datatable/classes/config";
import { AzioneOrdinanzaResponse } from "../../../commons/response/ordinanza/azione-ordinanza-response";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";

@Component({
    selector: 'fase-giurisdizionale-ricorso-dettaglio-ordinanza',
    templateUrl: './fase-giurisdizionale-ricorso-dettaglio-ordinanza.component.html',
})
export class FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    idOrdinanza: number;
    configSoggetti: Config;
    configAllegati: Config;
    azione: AzioneOrdinanzaResponse;
    loader: boolean;

    //message
    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'allegato')
                this.manageMessage('SUCCESS',"Allegato opposizione giurisdizionale caricato con successo");
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "Aggiungi Ricorso", 0, null, null,(el: any)=>true);
            this.callAzioneOrdinanza();
            this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
        });
    }

    callAzioneOrdinanza() {
        let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
        request.id = this.idOrdinanza;
        request.tipoDocumento = Constants.OPPOSIZIONE_GIURISDIZIONALE;
        this.subscribers.callAzioneOrdinanza = this.sharedOrdinanzaService.azioneOrdinanza(request).subscribe(data => {
            this.azione = data;
            this.loader = true;
        });
    }

    manageMessage(type: string, message: string) {
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

    goToFaseGiurisdizionaleAllegatoRicorso() {
        this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_RICORSO_ALLEGATO_ORDINANZA + this.idOrdinanza.toString());
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent.name);
    }

}