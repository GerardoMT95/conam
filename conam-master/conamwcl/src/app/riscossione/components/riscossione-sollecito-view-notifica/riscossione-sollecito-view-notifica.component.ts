import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { RiscossioneService } from "../../services/riscossione.service";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { Routing } from "../../../commons/routing";



@Component({
    selector: 'riscossione-sollecito-view-notifica',
    templateUrl: './riscossione-sollecito-view-notifica.component.html',
})
export class RiscossioneSollecitoViewNotificaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;
    public idSollecito: number;
    public sollecito: SollecitoVO;
    public sollecitoType: number = 0;

      //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private riscossioneService: RiscossioneService,
    ) { }

    ngOnInit(): void {
        this.logger.destroy(RiscossioneSollecitoViewNotificaComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idSollecito = +params['idSollecito'];
            this.loaded = true;
            this.loadSollecito();
        });

    }


    loadSollecito() {
        this.loaded = false;
        this.subscribers.getDettaglio = this.riscossioneService.getSollecitoById(this.idSollecito).subscribe(data => {
           this.sollecitoType = data.tipoSollecito.id
            this.sollecito = data;
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il caricamento del piano");
            this.loaded = true;
        });
    }

    goBack() {
        if(this.sollecitoType === 2){
            this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_DETTAGLIO_RATE);
        }
        else {
            this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_DETTAGLIO);
        }
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
      }
    
      timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
          seconds -= 1;
          if (seconds === 0) {
            this.resetMessageTop();
          }
        }, 2000);
      }
    
      resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalTop);
      }


      save(event: any){
        this.manageMessageTop("Dati notifica salvati con successo", "SUCCESS");
      }
      
        ngOnDestroy(): void {
            this.logger.destroy(RiscossioneSollecitoViewNotificaComponent.name);
        }
}