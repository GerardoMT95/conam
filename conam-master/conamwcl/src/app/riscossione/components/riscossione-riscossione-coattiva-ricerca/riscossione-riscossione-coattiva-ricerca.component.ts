import { Component, OnInit, OnDestroy, ViewChild, NgZone } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { RiscossioneService } from "../../services/riscossione.service";
import { Constants } from "../../../commons/class/constants";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { TableSoggettiRiscossione } from "../../../commons/table/table-soggetti-riscossione";
import { RicercaSoggettiRiscossioneCoattivaRequest } from "../../../commons/request/riscossione/ricerca-soggetti-riscossione-coattiva-request";
import { zip } from "rxjs/operators";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SharedRiscossioneConfigService } from "../../../shared-riscossione/services/shared-riscossione-config.service";

@Component({
  selector: "riscossione-riscossione-coattiva-ricerca",
  templateUrl: "./riscossione-riscossione-coattiva-ricerca.component.html",
})
export class RiscossioneRiscossioneCoattivaRicercaComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public subscribers: any = {};
  public loaded: boolean = true;
  public ordinanze: Array<TableSoggettiRiscossione> = new Array<TableSoggettiRiscossione>();
  public config: Config;
  public showTable: boolean;
  public max: boolean = false;

  //Pop-up
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public scrollBar: boolean;
  public scrollBarAvviata: boolean;

  public request: RicercaSoggettiRiscossioneCoattivaRequest = new RicercaSoggettiRiscossioneCoattivaRequest();

  isSelectable: (el: TableSoggettiRiscossione) => boolean = (
    el: TableSoggettiRiscossione
  ) => {
    let isStatoCorretto: boolean =
      el.statoSoggettoOrdinanza.id !=
        Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO &&
      el.statoSoggettoOrdinanza.id !=
        Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE &&
      el.statoSoggettoOrdinanza.id !=
        Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE &&
      el.statoOrdinanza.id != Constants.STATO_ORDINANZA_ATTESA_NOTIFICA;
    let statoRiscossioneCorreto: boolean = el.statoRiscossione.id == null;
    return isStatoCorretto && statoRiscossioneCorreto;
  };

  /* isEditable: (el: TableSoggettiRiscossione) => boolean = (el: TableSoggettiRiscossione) => {
         return el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO
             && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE
             && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE
             && el.statoOrdinanza.id != Constants.STATO_ORDINANZA_ATTESA_NOTIFICA;
     }*/

  constructor(
    private logger: LoggerService,
    private router: Router,
    private riscossioneService: RiscossioneService,
    private sharedRiscossioneConfigService: SharedRiscossioneConfigService,
    private utilSubscribersService: UtilSubscribersService,
    private ngZone: NgZone
  ) {
    window.onresize = (e) => {
      //ngZone.run will help to run change detection
      this.ngZone.run(() => {
        this.hasScrollBar(window.innerWidth);
      });
    };
  }

  ngOnInit(): void {
    this.logger.init(RiscossioneRiscossioneCoattivaRicercaComponent.name);
    this.config = this.sharedRiscossioneConfigService.getConfigOrdinanzaRiscossione(
      true,
      "Aggiungi in Lista Riscossione",
      1,
      (el: any) => true,
      null,
      null
    );
    this.hasScrollBar(window.innerWidth);
 
  }

  private requestRicerca: RicercaSoggettiRiscossioneCoattivaRequest;
  ricerca(request: RicercaSoggettiRiscossioneCoattivaRequest) {
    this.loaded = false;
    this.showTable = false;
    this.request.statoManualeDiCompetenza = true;
    this.subscribers.ricerca = this.riscossioneService
      .ricercaSoggettiRiscossioneCoattiva(request)
      .subscribe((data) => {
        this.resetMessageTop();
        this.request = request;
        this.loaded = true;
        this.showTable = true;
        if (data != null) {
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
            this.ordinanze = new Array<TableSoggettiRiscossione>();
          } else {
            this.ordinanze = data.map((value) => {
              return TableSoggettiRiscossione.map(value);
            });

            this.requestRicerca = request;
          }
        }
        if (
          request.dataNotificaA == null &&
          !this.max &&
          this.ordinanze.length == 0 &&
          request.numeroDeterminazione != null
        ) {
          this.manageMessageTop(
            " Non ci sono ordinanze per le quali procedere con la riscossione coattiva. Consultare l'ordinanza ricercata tramite voce 'Gestione contenzioso amministrativo/Ricerca ordinanza'",
            "WARNING"
          );
        }
        if (
          request.dataNotificaA != null &&
          !this.max &&
          this.ordinanze.length == 0
        ) {
          this.manageMessageTop(
            "nessuna ordinanza Notificata nell'intervallo data di notifica ricercato",
            "WARNING"
          );
        }
      });
  }

  hasScrollBar(width: number) {
    if (width < 1300) {
      this.scrollBarAvviata = true;
    } else {
      this.scrollBarAvviata = false;
    }
  }

  /* onDettaglio(arr: Array<TableSoggettiRiscossione>) {
         this.generaMessaggio(arr);
         this.buttonAnnullaText = "Annulla";
         this.buttonConfirmText = "Conferma";
 
         //mostro un messaggio
         this.sharedDialog.open();
 
         //unsubscribe
         this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
         this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");
 
         //premo "Conferma"
         this.subscribers.save = this.sharedDialog.salvaAction.subscribe(data => {
             this.aggiungiInListaRiscossione(arr);
         }, err => {
             this.logger.error(err);
         });
 
         //premo "Annulla"
         this.subscribers.close = this.sharedDialog.closeAction.subscribe(data => {
             this.subMessages = new Array<string>();
         }, err => {
             this.logger.error(err);
         });
     }*/

  /* generaMessaggio(arr: Array<TableSoggettiRiscossione>) {
         this.subMessages = new Array<string>();
 
         this.subMessages.push("Si intendono inviare le informazioni selezionate?");
         arr.forEach(el => {
             this.subMessages.push("");
             this.subMessages.push("Numero determinazione: " + el.numeroDeterminazione);
             this.subMessages.push("Denominazione: " + el.nomeCognomeRagioneSociale);
             this.subMessages.push("Ruolo: " + el.ruolo.denominazione);
             this.subMessages.push("Data notifica: " + el.dataNotifica);
         });
     }*/

  aggiungiInListaRiscossione(arr: Array<TableSoggettiRiscossione>) {
    this.loaded = false;
    this.showTable = false;
    this.riscossioneService
      .aggiungiInListaRiscossione(arr.map((e) => e.idSoggettoOrdinanza))
      .subscribe(
        (data) => {
          let newSoggetti = data.map((value) => {
            return TableSoggettiRiscossione.map(value);
          });
          for (let i = 0; i < this.ordinanze.length; i++) {
            let soggetto = newSoggetti.find(
              (f) =>
                f.idSoggettoOrdinanza == this.ordinanze[i].idSoggettoOrdinanza
            );
            if (soggetto) {
              this.ordinanze[i] = soggetto;
            }
          }

          this.ricerca(this.requestRicerca);
          this.loaded = true;
          this.showTable = true;
          this.manageMessageTop(
            "Soggetto aggiunto alla lista di riscossione coattiva con successo",
            "SUCCESS"
          );
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

  ngOnDestroy(): void {
    this.logger.destroy(RiscossioneRiscossioneCoattivaRicercaComponent.name);
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
    }, 1000);
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

  messaggio(message: string) {
    this.manageMessageTop(message, "DANGER");
  }
}
