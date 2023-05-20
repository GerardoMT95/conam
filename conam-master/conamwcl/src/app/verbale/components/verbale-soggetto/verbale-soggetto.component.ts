import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {
  ProvinciaVO,
  ComuneVO,
  RegioneVO,
  RuoloVO,
  NazioneVO,
} from "../../../commons/vo/select-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { VerbaleService } from "../../services/verbale.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { MinSoggettoVO } from "../../../commons/vo/verbale/min-soggetto-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { LuoghiService } from "../../../core/services/luoghi.service";
import { NgForm } from "@angular/forms";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";

declare var $: any;

@Component({
  selector: "verbale-soggetto",
  templateUrl: "./verbale-soggetto.component.html",
})
export class VerbaleSoggettoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  //datatable
  public soggetti: Array<TableSoggettiVerbale> = new Array<TableSoggettiVerbale>();
  public config: Config;

  //global
  public loadedSoggetti: boolean;
  public idVerbale: number;
  public subscribers: any = {};

  public loaderRegioni: boolean = false;
  public regioneModel: Array<RegioneVO> = new Array<RegioneVO>();
  public provinciaModel: Array<ProvinciaVO> = new Array<ProvinciaVO>();
  public loaderProvince: boolean = true;
  public comuneModel: Array<ComuneVO> = new Array<ComuneVO>();
  public loaderComuni: boolean = true;
  public loaderNazioni: boolean;
  public nazioneResidenzaModel: Array<NazioneVO> = new Array<NazioneVO>();

  private indirizzo: string;
  private civico: string;
  private cap: string;
  private indirizzoEstero: string;
  private civicoEstero: string;
  private capEstero: string;

  //ruolo
  public ruoloModel = new Array<RuoloVO>();

  //insert soggetto
  public isAggiungiSoggetto: boolean;
  public soggetto: SoggettoVO;
  public modalita: string;
  public showResidenza: boolean = false;
  public loadedSalvaRicerca: boolean;
  public comuneEstero: boolean = false;
  public comuneEsteroDisabled: boolean = false;

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  private intervalIdS: number = 0;
  private intervalIdW: number = 0;

  //Messaggio conferma eliminazione
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  //warning meta pagina
  public showMessageBottom: boolean;
  public typeMessageBottom: String;
  public messageBottom: String;

  private intervalIdSBottom: number = 0;

  //RUOLO
  public loaderRuolo: boolean;

  //VALIDAZIONE
  public formGiuridicoValid: boolean;
  public formFisicoValid: boolean;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private luoghiService: LuoghiService,
    private verbaleService: VerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private sharedVerbaleService: SharedVerbaleService,
    private sharedVerbaleConfigService: SharedVerbaleConfigService
  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleSoggettoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      //condizione di uscita
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.VERBALE_RICERCA);

      this.loadSoggettiAssociatiAVerbale();
      this.loadNazioni();
      this.loadRegioni();
      this.loadRuoli();
      this.manageDatePicker();
      this.pulisciFiltri();
      this.loadedSalvaRicerca = true;
    });
  }

  //PROVINCIA RESIDENZA
  public isDisabledComuneProvincia(type: string) {
    if (type == "P" && this.soggetto.regioneResidenza.id == null) return true;
    if (type == "C" && this.soggetto.provinciaResidenza.id == null) return true;
    return false;
  }

  loadNazioni() {
    this.loaderNazioni = false;
    this.subscribers.nazioni = this.luoghiService.getNazioni().subscribe(
      (data) => {
        this.nazioneResidenzaModel = data;
        this.loaderNazioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle nazioni");
      }
    );
  }

  loadRegioni() {
    this.loaderRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.regioneModel = data;
        this.loaderRegioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  loadProvince(id: number) {
    this.loaderProvince = false;
    this.subscribers.provinceByRegione = this.luoghiService
      .getProvinciaByIdRegione(id)
      .subscribe(
        (data) => {
          this.comuneModel = null;
          this.provinciaModel = data;
          this.loaderProvince = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle province");
        }
      );
  }

  loadComuni(id: number) {
    this.loaderComuni = false;
    this.subscribers.comuniByProvince = this.luoghiService
      .getComuneByIdProvincia(id)
      .subscribe(
        (data) => {
          this.comuneModel = data;
          this.loaderComuni = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero dei comuni");
        }
      );
  }

  manageLuoghiBySoggetto() {
    if (
      this.soggetto.regioneResidenza != null &&
      this.soggetto.regioneResidenza.id != null
    ) {
      this.loadProvince(this.soggetto.regioneResidenza.id);
    }
    if (
      this.soggetto.provinciaResidenza != null &&
      this.soggetto.provinciaResidenza.id != null
    ) {
      this.loadComuni(this.soggetto.provinciaResidenza.id);
    }
  }

  //RUOLI
  loadRuoli() {
    this.loaderRuolo = false;
    this.subscribers.ruolo = this.verbaleService.ruoliSoggetto().subscribe(
      (data) => {
        this.ruoloModel = data;
        this.loaderRuolo = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero dei ruoli");
      }
    );
  }

  loadSoggettiAssociatiAVerbale() {
    this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
      false,
      null,
      null,
      true
    );
    this.subscribers.soggetto = this.sharedVerbaleService
      .getSoggettiByIdVerbale(this.idVerbale, true)
      .subscribe((data) => {
        if (data != null) {
          this.soggetti = data.map((value) => {
            return TableSoggettiVerbale.map(value);
          });
        }
        this.loadedSoggetti = true;
      });
  }

  //CHANGE PERSONA
  cambiaPersona(type: string) {
    this.soggetto = new SoggettoVO();
    this.soggetto.personaFisica = type == "G" ? false : true;
    this.modalita = "R";
    this.showResidenza = false;
  }

  //CHANGE RESIDENZA
  cambiaResidenza(type: string) {
    this.soggetto.residenzaEstera = type == "I" ? false : true;
  }

  ricerca(event: any, tipoPersona: string) {
    let min: MinSoggettoVO = MinSoggettoVO.constructrFromSoggetto(
      this.soggetto
    );
    this.loadedSalvaRicerca = false;
    if (
      tipoPersona == "G" &&
      min.partitaIva != null &&
      min.partitaIva.length > 0
    ) {
      this.subscribers.ricercaSoggetto = this.verbaleService
        .ricercaSoggettoPerPIva(min)
        .subscribe((data) => {
          if (data == null) {
            this.manageMessageBottom(
              "Soggetto non trovato, provare con codice fiscale ",
              "WARNING"
            );

            this.loadedSalvaRicerca = true;
          } else {
            this.ricercaSoggetto(min);
          }
        });
    } else {
      this.ricercaSoggetto(min);
    }
  }

  ricercaSoggetto(min: MinSoggettoVO) {
    this.subscribers.ricercaSoggetto = this.verbaleService
      .ricercaSoggetto(min)
      .subscribe(
        (data) => {
          this.comuneEstero = true;
          if (data.nazioneNascitaEstera && !data.denomComuneNascitaEstero)
            this.comuneEsteroDisabled = false;
          else this.comuneEsteroDisabled = true;
          this.loadedSalvaRicerca = true;
          this.soggetto = SoggettoVO.editSoggettoFromSoggetto(
            this.soggetto,
            data
          );
          if (this.soggetto.residenzaEstera) {
            this.indirizzoEstero = this.soggetto.indirizzoResidenza;
            this.civicoEstero = this.soggetto.civicoResidenza;
            this.capEstero = this.soggetto.cap;
            if (this.soggetto.idStas != null) {
              this.indirizzo = this.soggetto.indirizzoResidenzaStas;
              this.civico = this.soggetto.civicoResidenzaStas;
              this.cap = this.soggetto.capStas;
            }
          } else {
            this.indirizzo = this.soggetto.indirizzoResidenza;
            this.civico = this.soggetto.civicoResidenza;
            this.cap = this.soggetto.cap;
          }
          this.manageLuoghiBySoggetto();
          this.modalita = "E";
          this.showResidenza = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.showResidenza = true;
            this.loadedSalvaRicerca = true;
            this.modalita = "E";
            this.manageMessage(err);
            this.logger.error("Errore nell'eliminazione del verbale");
          }
        }
      );
  }

  manageMessage(err: ExceptionVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  manageMessageTop(message: string, type: string) {
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

  manageMessageBottom(message: string, type: string) {
    this.typeMessageBottom = type;
    this.messageBottom = message;
    this.timerShowMessageBottom();
  }

  timerShowMessageBottom() {
    this.showMessageBottom = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
    this.intervalIdSBottom = window.setInterval(() => {
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
    clearInterval(this.intervalIdSBottom);
  }

  pulisciFiltri() {
    this.comuneEstero = false;
    this.comuneEsteroDisabled = false;
    this.soggetto = new SoggettoVO();
    this.modalita = "R";
    this.showResidenza = false;
    this.soggetto.personaFisica = true;
    this.indirizzo = null;
    this.indirizzoEstero = null;
    this.civico = null;
    this.civicoEstero = null;
    this.cap = null;
    this.capEstero = null;
  }

  salvaSoggetto() {
    this.loadedSalvaRicerca = false;
    if (this.soggetto.residenzaEstera) {
      this.soggetto.indirizzoResidenza = this.indirizzoEstero;
      this.soggetto.civicoResidenza = this.civicoEstero;
      this.soggetto.cap = this.capEstero;
      this.soggetto.regioneResidenza = null;
      this.soggetto.provinciaResidenza = null;
      this.soggetto.comuneResidenza = null;
    } else {
      this.soggetto.indirizzoResidenza = this.indirizzo;
      this.soggetto.civicoResidenza = this.civico;
      this.soggetto.cap = this.cap;
      this.soggetto.nazioneResidenza = null;
      this.soggetto.denomComuneResidenzaEstero = null;
    }
    this.subscribers.salvaSoggettoVerbale = this.verbaleService
      .salvaSoggetto(this.soggetto, this.idVerbale)
      .subscribe(
        (data) => {
          if (data.comuneNascitaValido) {
            this.soggetti.push(TableSoggettiVerbale.map(data));
            this.isAggiungiSoggetto = false;
            this.pulisciFiltri();
            this.manageMessageTop("Soggetto aggiunto con successo", "SUCCESS");
          } else {
            this.manageMessageTop(
              "Il luogo di nascita selezionato non è compatibile con la data di nascita del soggetto. Inserire una regione-provincia-comune validi alla data di nascita.",
              "WARNING"
            );
          }

          this.loadedSalvaRicerca = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.showResidenza = true;
            this.loadedSalvaRicerca = true;
            this.manageMessage(err);
            this.logger.error("Errore nell'eliminazione del verbale");
          }
        }
      );
  }

  //elimina soggetto
  confermaEliminazione(el: TableSoggettiVerbale) {
    this.logger.info(
      "Richiesta eliminazione del soggetto " + el.idVerbaleSoggetto
    );
    this.generaMessaggio(el);
    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    //mostro un messaggio
    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.loadedSoggetti = false;
        this.subscribers.eliminaSoggetto = this.verbaleService
          .eliminaSoggettoByIdVerbaleSoggetto(el.idVerbaleSoggetto)
          .subscribe(
            (data) => {
              //elimina il soggetto
              this.logger.info("Elimina elemento da tabella");
              this.soggetti.splice(this.soggetti.indexOf(el), 1);
              this.loadSoggettiAssociatiAVerbale();
              this.loadedSoggetti = true;
              this.manageMessageTop(
                "Soggetto eliminato con successo",
                "SUCCESS"
              );
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.manageMessage(err);
              }
              this.logger.error(
                "Errore nell'eliminazione del soggetto " +
                  el.identificativoSoggetto
              );
              this.loadedSoggetti = true;
            }
          );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
        this.subMessages = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  generaMessaggio(el: TableSoggettiVerbale) {
    this.subMessages = new Array<string>();

    let incipit: string = "Si intende eliminare il seguente soggetto?";

    this.subMessages.push(incipit);
    this.subMessages.push(el.nomeCognomeRagioneSociale);
  }

  //ROUTING
  goToVerbaleRiepilogo() {
    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);
  }

  goToVerbaleDati() {
    this.router.navigateByUrl(Routing.VERBALE_DATI + this.idVerbale);
  }

  goToVerbaleAllegato() {
    this.router.navigateByUrl(Routing.VERBALE_ALLEGATO + this.idVerbale);
  }

  //DATEPICKER
  manageDatePicker() {
    if ($("#datepicker").length) {
      $("#datepicker").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
      });
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(VerbaleSoggettoComponent.name);
  }

  isDisable(field: string) {
    let s = this.soggetto;
    if (s.idStas != null) return true;
    else {
      if (field == "PR") {
        return this.isDisabledComuneProvincia("P");
      } else if (["CR", "IR", "CI", "CAP"].indexOf(field) >= 0) {
        return this.isDisabledComuneProvincia("C");
      }
    }
    return false;
  }

  formDisabled(form: NgForm) {
    if (this.soggetto.personaFisica)
      return !(this.formFisicoValid && form.valid);
    else return !(this.formGiuridicoValid && form.valid);
  }

  public setFormValid(event: boolean, type: string) {
    if (type == "F") this.formFisicoValid = event;
    if (type == "G") this.formGiuridicoValid = event;
  }
}
