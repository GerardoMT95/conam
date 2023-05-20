import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { Constants } from "../../../commons/class/constants";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";

@Component({
  selector: "pagamenti-riconcilia-verbale-allegato",
  templateUrl: "./pagamenti-riconcilia-verbale-allegato.component.html",
})
export class PagamentiRiconciliaVerbaleAllegatoComponent
  implements OnInit, OnDestroy
{
  public subscribers: any = {};

  public idVerbale: number;
  public loaded: boolean;
  public loadedConfig: boolean;
  public loadedCategoriaAllegato: boolean;

  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public senzaAllegati: boolean = true;

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;
  public riepilogoVerbale: RiepilogoVerbaleVO;
  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedVerbaleService: SharedVerbaleService
  ) {}

  ngOnInit(): void {
    this.logger.init(PagamentiRiconciliaVerbaleAllegatoComponent.name);

    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["idVerbale"];

      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_VERBALE_RICERCA);
      this.subscribers.riepilogo = this.sharedVerbaleService
        .riepilogoVerbale(this.idVerbale)
        .subscribe((data) => {
          this.riepilogoVerbale = data;
          this.loadTipoAllegato();
        });
    });
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliRequest();
    request.id = this.idVerbale;
    request.tipoDocumento = Constants.RICEVUTA_PAGAMENTO_VERBALE;
    this.subscribers.tipoAllegato = this.sharedVerbaleService
      .getTipologiaAllegatiAllegabiliVerbale(request)
      .subscribe(
        (data) => {
          this.tipoAllegatoModel = data;
          this.loadedCategoriaAllegato = true;
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
          this.loadedCategoriaAllegato = true;
          this.loaded = true;
        }
      );
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
    nuovoAllegato.idVerbale = this.idVerbale;
    //mando il file al Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.sharedVerbaleService
      .creaAllegatoVerbale(nuovoAllegato)
      .subscribe(
        (data) => {
          let azione: string = "";
          if (nuovoAllegato.allegatoField[3].booleanValue == "true") {
            azione = "parziale";
          } else {
            azione = "allegato";
          }
          this.router.navigate([
            Routing.PAGAMENTI_RICONCILIA_VERBALE_RIEPILOGO + this.idVerbale,
            { action: azione },
          ]);
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.error("Errore nel salvataggio dell'allegato");
          this.loaded = true;
        }
      );
  }

  manageMessage(type: string, message: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
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

  ngOnDestroy(): void {
    this.logger.destroy(PagamentiRiconciliaVerbaleAllegatoComponent.name);
  }
}
