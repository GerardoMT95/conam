import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { UserService } from "../../../core/services/user.service";
import { SelectVO, IstruttoreVO } from "../../../commons/vo/select-vo";
import { VerbaleService } from "../../services/verbale.service";
import { SalvaAzioneRequest } from "../../../commons/request/verbale/salva-azione.request";
import { Constants } from "../../../commons/class/constants";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ProfiloVO } from "../../../commons/vo/profilo-vo";
import { UtenteVO } from "../../../commons/vo/utente-vo";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { AzioneVerbalePregressiResponse } from "../../../commons/response/pregresso/azione-verbale-pregressi-response";
import { SharedVerbaleRiepilogoComponent } from "../../../shared-verbale/component/shared-verbale-riepilogo/shared-verbale-riepilogo.component";

@Component({
  selector: "pregresso-riepilogo",
  templateUrl: "./pregresso-riepilogo.component.html",
  styleUrls: ["./pregresso-riepilogo.component.scss"],
})
export class PregressoRiepilogoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedVerbaleRiepilogoComponent)
  sharedVerbaleRiepilogoComponent: SharedVerbaleRiepilogoComponent;

  public subscribers: any = {};

  public loadedistruttore: boolean;
  public loaded: boolean;
  public messaggioIstruttore: boolean = false;

  private intervalIdS: number = 0;

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public singoloEnte: boolean;
  public idVerbale: number;
  public funzionarioIstrModel: Array<IstruttoreVO> = new Array<IstruttoreVO>();
  public azioneVerbale: AzioneVerbalePregressiResponse =
    new AzioneVerbalePregressiResponse();
  salvaAzioneRequest: SalvaAzioneRequest = new SalvaAzioneRequest();

  public utente: UtenteVO;
  public visible: boolean = false;
  public riepilogoVerbale: RiepilogoVerbaleVO;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private verbaleService: VerbaleService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private sharedVerbaleService: SharedVerbaleService
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoRiepilogoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.VERBALE_DATI);
      if (this.activatedRoute.snapshot.paramMap.get("action") == "caricato")
        this.manageMessage("Documento caricato con successo", "SUCCESS");
      this.getAzioneVerbale();
    });

    this.loaded = false;
    // TODO Controllare
    this.loadRiepilogo();
    this.verbaleService.getUtenteRuolo(this.idVerbale).subscribe(
      (data) => {
        this.utente = data;
        if (this.utente.id == 1 || this.utente.id == 4) {
          this.salvaAzioneRequest.idFunzionario = this.utente.id;
        }
        this.messaggioIstruttore = this.utente.isIstruttore == 1;
        this.loaded = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err.message, err.type);
        }
        this.logger.info("Errore nel recupero utente");
        this.loaded = true;
      }
    );
  }

  // TODO Controllare
  loadRiepilogo() {
    this.subscribers.riepilogo = this.sharedVerbaleService
      .riepilogoVerbale(this.idVerbale)
      .subscribe((data) => {
        this.riepilogoVerbale = data;
      });
  }

  salvaAzioneVerbale(idAzione: number) {
    this.salvaAzioneRequest.id = this.idVerbale;
    this.salvaAzioneRequest.idAzione = idAzione;
    this.loaded = false;
    this.subscribers.salvaAzioneVerbale = this.verbaleService
      .salvaAzioneVerbale(this.salvaAzioneRequest)
      .subscribe(
        (data) => {
          this.getAzioneVerbale();
          if (
            idAzione == Constants.ID_AZIONE_ACQUISISCI ||
            idAzione == Constants.ID_AZIONE_ACQUISISCI_CON_PAGAMENTO ||
            idAzione == Constants.ID_AZIONE_ACQUISISCI_CON_SCRITTI ||
            idAzione == Constants.ID_AZIONE_IN_ATTESA_VERIFICA_PAGAMENTO ||
            idAzione == Constants.ID_AZIONE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO
          ) {
            if (this.riepilogoVerbale.verbale.numeroProtocollo != null) {
              this.manageMessage("Operazione avvenuta con successo", "SUCCESS");
            } else {
              this.manageMessage(
                "La richiesta di acquisizione del fascicolo è stata presa in carico. Al termine del processo il fascicolo disporrà del numero di protocollo e sarà possibile eseguire altre attività utilizzando la funzionalità di ricerca",
                "SUCCESS"
              );
            }
          } else if (idAzione == Constants.ID_AZIONE_ARCHIVIATO_IN_AUTOTUTELA) {
            this.manageMessage("Fascicolo archiviato in autotutela", "SUCCESS");
          }
          this.loaded = true;
          this.scrollEnable = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.message, err.type);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
          this.loaded = true;
        }
      );
  }

  refreshService() {
    this.getAzioneVerbale();
  }

  getAzioneVerbale() {
    this.loaded = false;
    this.subscribers.statoVerbale = this.pregressoVerbaleService
      .getAzioniVerbale(this.idVerbale)
      .subscribe((data) => {
        this.loaded = true;
        this.azioneVerbale = data;
        this.visible = true;
      });
  }

  loadIstruttoreByVerbale(idVerbale: number) {
    this.loadedistruttore = false;
    this.subscribers.istruttoreByIdRegione = this.verbaleService
      .getIstruttoreByIdVerbale(idVerbale)
      .subscribe(
        (data) => {
          this.funzionarioIstrModel = data;
          this.loadedistruttore = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle province");
        }
      );
  }

  manageMessage(message: string, type: String) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
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

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  goToVerbaleAllegato() {
    this.router.navigateByUrl(Routing.PREGRESSO_ALLEGATO + this.idVerbale);
  }

  goToRiepilogoOrdinanze() {
    this.router.navigateByUrl(
      Routing.PREGRESSO_RIEPILOGO_ORDINANZE + this.idVerbale
    );
  }

  scrollEnable: boolean;
  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(PregressoRiepilogoComponent.name);
  }

  onLoadChange(loaded: boolean) {
    this.loaded = loaded;

    // TODO Controllare
    if (loaded) {
      this.getAzioneVerbale();
      this.loadRiepilogo();
      this.sharedVerbaleRiepilogoComponent.load();
    }
  }
}
