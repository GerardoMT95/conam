import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { SharedOrdinanzaRiepilogoComponent } from "../../../shared-ordinanza/component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TemplateService } from "../../../template/services/template.service";
import { SalvaStatoOrdinanzaPregressiRequest } from "../../../commons/request/pregresso/salva-stato-ordinanza-pregressi.request";
import { AzioneOrdinanzaPregressiResponse } from "../../../commons/response/pregresso/azione-ordinanza-pregressi-response";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { MessageVO } from "../../../commons/vo/messageVO";

@Component({
  selector: "pregresso-ordinanze-riepilogo",
  templateUrl: "./pregresso-ordinanze-riepilogo.component.html",
})
export class PregressoOrdinanzeRiepilogoComponent implements OnInit, OnDestroy {
  public subscribers: any = {};
  public idVerbale: number;

  public showTable: boolean;

  public config: Config;
  public ordinanze: Array<OrdinanzaVO>;
  public ordinanzaSel: OrdinanzaVO;
  public loaded: boolean = true;

  public riepilogoVerbale: RiepilogoVerbaleVO;

  public loadedStatiOrdinanza: boolean;
  public statiOrdinanzaModel: Array<StatoOrdinanzaVO>;

  public request: RicercaOrdinanzaRequest;

  public max: boolean = false;

  idOrdinanza: number;
  azione: AzioneOrdinanzaPregressiResponse;
  loadedAction: boolean;
  loadedOrdinanza: boolean = false;
  isRichiestaBollettiniSent: boolean;
  isFirstDownloadBollettini: boolean;

  @ViewChild(SharedOrdinanzaRiepilogoComponent)
  sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

  salvaStatoOrdinanzaPregressiRequest: SalvaStatoOrdinanzaPregressiRequest = new SalvaStatoOrdinanzaPregressiRequest();
  statiOrdinanzaMessage: string;

  public buttonAnnullaTexts: string;
  public buttonConfirmTexts: string;
  public subMessagess: Array<string>;
  public alertWarning: string;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private configSharedService: ConfigSharedService,
    private sharedOrdinanzaService: SharedOrdinanzaService,
    private activatedRoute: ActivatedRoute,
    private pregressoVerbaleService: PregressoVerbaleService,
    private templateService: TemplateService,
    private utilSubscribersService: UtilSubscribersService
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoOrdinanzeRiepilogoComponent.name);
    this.config = this.configSharedService.configRicercaOrdinanza;
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.PREGRESSO_DATI);
      this.load();
    });
  }

  load(): void {
    this.subscribers.riepilogo = this.pregressoVerbaleService
      .riepilogoVerbale(this.idVerbale)
      .subscribe((data) => {
        this.riepilogoVerbale = data;
        let ricercaOrdinanzaRequest: RicercaOrdinanzaRequest = new RicercaOrdinanzaRequest();
        ricercaOrdinanzaRequest.numeroVerbale = this.riepilogoVerbale.verbale.numero;
        this.ricercaOrdinanza(ricercaOrdinanzaRequest);
      });
  }

  scrollEnable: boolean;
  ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest) {
    this.request = ricercaOrdinanzaRequest;
    this.showTable = false;
    this.loaded = false;
    ricercaOrdinanzaRequest.ordinanzaProtocollata = false;
    this.request.tipoRicerca = "RICERCA_ORDINANZA";
    this.request.soggettoVerbale = null;
    this.request.statoOrdinanza = null;
    this.pregressoVerbaleService
      .ricercaOrdinanza(ricercaOrdinanzaRequest)
      .subscribe(
        (data) => {
          this.resetMessageTop();
          if (data.length == 1) {
            data.map((value) => {
              this.max = value.superatoIlMassimo;
            });
          }

          if (this.max) {
            this.manageMessageTop(
              "Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca",
              "WARNING"
            );
            this.ordinanze = new Array<OrdinanzaVO>();
          } else {
            this.ordinanze = data;
          }
          this.showTable = true;
          this.loaded = true;
          this.scrollEnable = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore nella ricerca dell'ordinanza");
        }
      );
  }

  ngAfterContentChecked() {
    let out: HTMLElement = document.getElementById("scrollBottom");
    if (this.loaded && this.scrollEnable && this.showTable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  callAzioneOrdinanza() {
    this.loadedAction = false;
    let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
    request.id = this.idOrdinanza;
    this.subscribers.callAzioneOrdinanza = this.pregressoVerbaleService
      .azioneOrdinanza(request)
      .subscribe((data) => {
        this.azione = data;
        this.loadedAction = true;
      });
  }

  onDettaglio(el: any | Array<any>) {
    this.ordinanzaSel = el;
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      this.idOrdinanza = el.id;
      this.loadedOrdinanza = true;
      this.loadAlertWarning();
      this.loadStatiOrdinanza();
      this.callAzioneOrdinanza();
    }
  }

  messaggio(message: string) {
    this.manageMessageTop(message, "DANGER");
  }

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  //Messaggio bottom
  public showMessageBottom: boolean;
  public typeMessageBottom: String;
  public messageBottom: String;
  private intervalIdSB: number = 0;

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
    this.scrollTopEnable = true;
  }

  manageMessageBottom(mess: ExceptionVO | MessageVO) {
    this.typeMessageBottom = mess.type;
    this.messageBottom = mess.message;
    this.timerShowMessageBottom();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 10;
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

  timerShowMessageBottom() {
    this.showMessageBottom = true;
    let seconds: number = 10;
    this.intervalIdSB = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageBottom();
      }
    }, 1000);
  }

  resetMessageBottom() {
    this.showMessageBottom = false;
    this.typeMessageBottom = null;
    this.messageBottom = null;
    clearInterval(this.intervalIdSB);
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(PregressoOrdinanzeRiepilogoComponent.name);
  }

  loadAlertWarning() {
    this.subscribers.alertWarning = this.templateService
      .getMessage("STORDPRWAR")
      .subscribe(
        (data) => {
          this.alertWarning = data.message;
        },
        (err) => {
          this.logger.error("Errore nel recupero del messaggio");
        }
      );
  }

  loadStatiOrdinanza() {
    this.subscribers.statiVerbale = this.pregressoVerbaleService
      .getStatiOrdinanzaById(this.idOrdinanza)
      .subscribe(
        (data) => {
          this.statiOrdinanzaMessage = data.messaggio.message;
          this.statiOrdinanzaModel = data.stati;
          this.loadedStatiOrdinanza = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero degli stati");
        }
      );
  }

  salvaStatoOrdinanza() {
    /* genera messaggio */
    this.subMessagess = new Array<string>();

    let incipit: string = this.statiOrdinanzaMessage;
    const nuovoStato = this.statiOrdinanzaModel.find(
      (i) => i.id == this.salvaStatoOrdinanzaPregressiRequest.idStato
    ).denominazione;
    incipit = incipit.replace("<VALORE_SELEZIONATO_DA_TENDINA>", nuovoStato);
    this.subMessagess.push(incipit);

    this.buttonAnnullaTexts = "Annulla";
    this.buttonConfirmTexts = "Conferma";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subscribers.saveStatoVerbale = this._saveRequestStatoOrdinanza();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  private _saveRequestStatoOrdinanza() {
    this.salvaStatoOrdinanzaPregressiRequest.id = this.idOrdinanza;
    this.pregressoVerbaleService
      .salvaStatoOrdinanza(this.salvaStatoOrdinanzaPregressiRequest)
      .subscribe(
        (data) => {
          if (data) {
            this.manageMessageBottom(data);
            // per efettuare il reset della parte che non serve a quel punto nel riepilogo
            this.loaded = false;
            this.loadedOrdinanza = false;
            this.load();
          }
        },
        (err) => {
          this.logger.error("Errore nel salvataggio dello stato del fascicolo");
        }
      );
  }

  goToPianiRateizzazione() {
    this.router.navigate([
      Routing.PREGRESSO_RIEPILOGO_PIANI + this.idVerbale,
      { idOrdinanza: this.idOrdinanza },
    ]);
  }

  goToSolleciti() {
    this.router.navigate([
      Routing.PREGRESSO_RIEPILOGO_SOLLECITI + this.idVerbale,
      { idOrdinanza: this.idOrdinanza },
    ]);
  }

  goToDisposizioni() {
    this.router.navigate([
      Routing.PREGRESSO_RIEPILOGO_DISPOSIZIONI + this.idVerbale,
      { idOrdinanza: this.idOrdinanza },
    ]);
  }

  goToRicevute() {
    this.router.navigate([
      Routing.PREGRESSO_RIEPILOGO_RICEVUTE + this.idVerbale,
      { idOrdinanza: this.idOrdinanza },
    ]);
  }

  goToVerbaleAllegato() {
    this.router.navigate([
      Routing.PREGRESSO_ALLEGATO + this.idVerbale,
      {
        ref: "pregresso/riepilogo-ordinanze/" + this.idVerbale,
        idOrdinanza: this.idOrdinanza,
        numDeterminazione: this.ordinanzaSel.numDeterminazione,
      },
    ]);
  }
}
