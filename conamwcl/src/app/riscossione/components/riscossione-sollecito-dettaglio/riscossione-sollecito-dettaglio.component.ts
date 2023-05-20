import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { RiscossioneService } from "../../services/riscossione.service";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Routing } from "../../../commons/routing";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { DatiSentenzaResponse } from "../../../commons/response/ordinanza/dati-sentenza-response";
import { Constants } from "../../../commons/class/constants";
import { saveAs } from "file-saver";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { CurrencyPipe } from "@angular/common";

//JIRA - Gestione Notifica
import { SalvaSollecitoRequest } from "../../../commons/request/riscossione/salva-sollecito-request";
import { SharedRiscossioneSollecitoDettaglioComponent } from "../../../shared-riscossione/components/shared-riscossione-sollecito-dettaglio/shared-riscossione-sollecito-dettaglio.component";

declare var $: any;

@Component({
  selector: "riscossione-sollecito-dettaglio",
  templateUrl: "./riscossione-sollecito-dettaglio.component.html",
})
export class RiscossioneSollecitoDettaglioComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  @ViewChild(SharedOrdinanzaDettaglio)
  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
  @ViewChild(SharedRiscossioneSollecitoDettaglioComponent)
  sharedRiscossioneSollecitoDettaglioComponent: SharedRiscossioneSollecitoDettaglioComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public loadedAction: boolean = true;

  public idOrdinanzaVerbaleSoggetto: number[];

  public sollecito: SollecitoVO;
  public soggettoSollecito: TableSoggettiOrdinanza[];
  public listaSolleciti: Array<SollecitoVO>;

  public configSoggetti: Config;
  public configSolleciti: Config;

  //Pop-up
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  //FLAG MODIFICA
  public isModifica: boolean;
  public modificabile: boolean;

  //flag nuovo
  public isNuovo: boolean = true;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private riscossioneService: RiscossioneService,
    private sharedRiscossioneService: SharedRiscossioneService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private utilSubscribersService: UtilSubscribersService
  ) {}

  ngOnInit(): void {
    this.logger.init(RiscossioneSollecitoDettaglioComponent.name);
    this.loaded = false;

    this.soggettoSollecito = [];
    this.soggettoSollecito[0] = this.riscossioneService.soggettoSollecito;
  
    //this.riscossioneService.soggettoSollecito = null;
    if (!this.soggettoSollecito[0]) {
      this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_RICERCA);
    } else {
      this.idOrdinanzaVerbaleSoggetto = [];
      this.idOrdinanzaVerbaleSoggetto[0] = this.soggettoSollecito[0].idSoggettoOrdinanza;

      this.loadSollecitiEsistenti();

      this.setConfig();

      this.sollecito = new SollecitoVO();
      this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    }
    this.riscossioneService.getMessaggioManualeByidOrdinanzaVerbaleSoggetto(this.idOrdinanzaVerbaleSoggetto[0]).subscribe(data => {
      if(data){
        this.manageMessageTop(data.message, data.type)
      }  
    });
  }
  

  loadSollecitiEsistenti() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0])
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
          if (this.listaSolleciti != null && this.listaSolleciti.length > 0) {
            let currencyPipe = new CurrencyPipe("it-IT");
            for (let i = 0; i < this.listaSolleciti.length; i++) {
              this.listaSolleciti[
                i
              ].importoSollecitatoString = currencyPipe.transform(
                this.listaSolleciti[i].importoSollecitato,
                "EUR",
                "symbol"
              );
              this.listaSolleciti[
                i
              ].maggiorazioneString = currencyPipe.transform(
                this.listaSolleciti[i].maggiorazione,
                "EUR",
                "symbol"
              );
            }
          }

          //
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il caricamento dei solleciti");
          this.loaded = true;
        }
      );
  }

  loadSollecitiEsistentiCopiaPerRichiestaBollettini() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0])
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
          this.loaded = true;
          this.modificaVediSollecito(
            this.listaSolleciti.find(
              (soll) => soll.idSollecito == this.sollecito.idSollecito
            )
          );
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il caricamento dei solleciti");
          this.loaded = true;
        }
      );
  }

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.scrollTopEnable = true;
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

  richiediEliminazioneSollecito() {
    this.buttonAnnullaText = "Annulla";
      //mostro un messaggio
    this.sharedDialog.open();
    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.eliminaSollecito(this.sollecito);
        this.sollecito = new SollecitoVO();
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
  
  generaMessaggio() {
    this.subMessages = new Array<string>();

    this.subMessages.push("Si intende eliminare il sollecito selezionato?");
    this.subMessages.push("Data Creazione: " + this.sollecito.dataScadenza);
    this.subMessages.push(
      "Importo da sollecitare: " + this.sollecito.importoSollecitato
    );
  }

  creaSollecito() {
    this.loaded = false;
    //JIRA - Gestione Notifica
    //----------------------
    let salvaSollecitoRequest: SalvaSollecitoRequest = new SalvaSollecitoRequest();
    salvaSollecitoRequest.sollecito = this.sollecito;
    salvaSollecitoRequest.notifica = this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject();
    //------------------------
    //this.subscribers.salvataggio = this.riscossioneService.salvaSollecito(this.sollecito).subscribe(data => {
    this.subscribers.salvataggio = this.riscossioneService
      .salvaSollecito(salvaSollecitoRequest)
      .subscribe(
        (data) => {
          this.router.navigateByUrl(
            Routing.RISCOSSIONE_SOLLECITO_TEMPLATE + data
          );
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il salvataggio del sollecito");
          this.loaded = true;
        }
      );
  }

  modificaVediSollecito(el: SollecitoVO) {
    this.sollecito = JSON.parse(JSON.stringify(el));

    if (el.statoSollecito.id == Constants.SOLLECITO_BOZZA) {
      this.modificabile = true;
    } else {
      this.modificabile = false;
    }
    this.isModifica = true;
    this.isNuovo = false;
    if(this.sharedRiscossioneSollecitoDettaglioComponent){
      this.sharedRiscossioneSollecitoDettaglioComponent.aggiornaNotifica();
    }
  }

  annullaModifica() {
    this.sollecito = new SollecitoVO();
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.isModifica = false;
    this.isNuovo = true;

    this.loadSollecitiEsistenti();
  }

  eliminaSollecito(el: SollecitoVO) {
    this.generaMessaggio();
    this.loaded = false;
    this.subscribers.elimina = this.riscossioneService
      .eliminaSollecito(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          this.loadSollecitiEsistenti();
          this.manageMessageTop("Sollecito eliminato con successo!", "SUCCESS");
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante l'eliminazione del sollecito");
          this.loaded = true;
        }
      );
  }

  isDisabledCreaSollecito(): boolean {
    //JIRA - Gestione Notifica: non necessaria in questa sezione
    let checkNotifica: boolean = true;
    if (
      this.sharedRiscossioneSollecitoDettaglioComponent != null ||
      this.sharedRiscossioneSollecitoDettaglioComponent != undefined
    ) {
      if (
        this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject() !=
          null ||
        this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject() !=
          undefined
      ) {
        if (
          this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
            .importoSpeseNotifica == undefined ||
          this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
            .importoSpeseNotifica == null
        ) {
          checkNotifica = true;
        } else {
          checkNotifica = isNaN(
            Number(
              this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
                .importoSpeseNotifica
            )
          );
        }
      }
    }
    if (checkNotifica) return true;

    return !this.sollecito.importoSollecitato;
  }

  setConfig() {
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      false,
      null,
      0,
      null,
      null,
      (el: any) => true
    );
    this.configSolleciti = {
      selection: {
        enable: true,
        isSelectable: (el: SollecitoVO) => {
          return el.isCreatoDalloUserCorrente;
        },
      },
      columns: [
        {
          columnName: "numeroProtocollo",
          displayName: "Numero Protocollo",
        },
        {
          columnName: "dataScadenza",
          displayName: "Data Scadenza",
        },
        {
          columnName: "importoSollecitatoString",
          displayName: "Importo da sollecitare",
        },
        {
          columnName: "maggiorazioneString",
          displayName: "Maggiorazione",
        },
        {
          columnName: "statoSollecito.denominazione",
          displayName: "Stato",
        },
      ],
    };
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

  inviaRichiestaBollettini() {
    this.loaded = false;
    this.subscribers.inviaRichiestaBollettini = this.riscossioneService
      .inviaRichiestaBollettiniSollecito(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          this.loadSollecitiEsistentiCopiaPerRichiestaBollettini();
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.loaded = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }

  downloadBollettini() {
    this.loadedAction = false;
    this.riscossioneService
      .downloadBollettini(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          saveAs(
            data,
            "Bollettini_Sollecito_" +
              this.sharedOrdinanzaDettaglio.ordinanza.numDeterminazione +
              ".pdf"
          );
          this.loadedAction = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.loadedAction = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }

  downloadLettera() {
    this.loaded = false;
    this.riscossioneService
      .downloadLettera(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          saveAs(
            data,
            "Lettera_Sollecito_" + this.sollecito.idSollecito + ".pdf"
          );
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.loaded = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }

  goToCreaNotifica() {
    this.router.navigateByUrl(
      Routing.RISCOSSIONE_SOLLECITO_INS_NOTIFICA + this.sollecito.idSollecito
    );
  }

  goToVisualizzaNotifica() {
    this.router.navigateByUrl(
      Routing.RISCOSSIONE_SOLLECITO_VIEW_NOTIFICA + this.sollecito.idSollecito
    );
  }

  onInfo(el: any | Array<any>) {
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      let azione1: string = el.ruolo;
      let azione2: string = el.noteSoggetto;
      this.router.navigate([
        Routing.SOGGETTO_RIEPILOGO + el.idSoggetto,
        { ruolo: azione1, nota: azione2 },
      ]);
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(RiscossioneSollecitoDettaglioComponent.name);
  }
}
