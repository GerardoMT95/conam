import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { ComuneVO, ProvinciaVO } from "../../../commons/vo/select-vo";
import { UserService } from "../../../core/services/user.service";

@Component({
    selector: 'template-01-convocazione-audizione',
    templateUrl: './template-01-convocazione-audizione.component.html'
})
export class Template01ConvocazioneAudizioneComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;

    //Variabili per il singolare/plurale
    public signor: string;
    public signoriaVostra: string;
    public sentire: string;
    public convocare: string;
    public comparire: string;
    public sue: string;

    //Campi da compilare
    public oggetto: string;
    public dataOra: string;
    public luogo: string;

    //Parametri ricevuti dal BE
    public listaSoggetti: Array<SoggettoVO>;
    public avvocato: string; //user

    constructor(
        private logger: LoggerService,
        //private router: Router,
        //private activatedRoute: ActivatedRoute,
        private userService: UserService,
    ) { }

    ngOnInit(): void {
        this.logger.init(Template01ConvocazioneAudizioneComponent.name);

        this.subscribers.userProfile = this.userService.profilo$.subscribe(data => {
            if (data != null) {
                this.avvocato = data.nome + " " + data.cognome;

                this.getListaSoggetti();

                this.gestisciSingolariPlurali();

                this.loaded = true;
            }
        });
    }

    getListaSoggetti() {
        //MOCK
        this.listaSoggetti = new Array<SoggettoVO>();

        let mock1: SoggettoVO = new SoggettoVO();
        mock1.nome = "Tizio";
        mock1.cognome = "Sempronio";
        mock1.sesso = "M";
        mock1.indirizzoResidenza = "Via da lì";
        mock1.civicoResidenza = "1";
        mock1.comuneResidenza = new ComuneVO();
        mock1.comuneResidenza.denominazione = "Collegno";
        mock1.provinciaResidenza = new ProvinciaVO();
        mock1.provinciaResidenza.denominazione = "Torino";
        this.listaSoggetti.push(mock1);

        let mock2: SoggettoVO = new SoggettoVO();
        mock2.nome = "Caio";
        mock2.cognome = "Mevio";
        mock2.sesso = "M";
        mock2.indirizzoResidenza = "Via da qui";
        mock2.civicoResidenza = "2";
        mock2.comuneResidenza = new ComuneVO();
        mock2.comuneResidenza.denominazione = "Rivoli";
        mock2.provinciaResidenza = new ProvinciaVO();
        mock2.provinciaResidenza.denominazione = "Torino";
        this.listaSoggetti.push(mock2);
    }

    gestisciSingolariPlurali() {
        if (this.listaSoggetti.length <= 1) {
            this.signor = this.listaSoggetti[0].sesso == "M" ? "Al Signor" : "Alla Sig.ra";
            this.signoriaVostra = "la S.V.";
            this.sentire = "sentita";
            this.convocare = "è convocata";
            this.comparire = "comparisse";
            this.sue = "Sue";
        } else {
            this.signor = "Ai Signori";
            this.signoriaVostra = "le SS.VV.";
            this.sentire = "sentite";
            this.convocare = "sono convocate";
            this.comparire = "comparissero";
            this.sue = "Loro";
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(Template01ConvocazioneAudizioneComponent.name);
    }

}