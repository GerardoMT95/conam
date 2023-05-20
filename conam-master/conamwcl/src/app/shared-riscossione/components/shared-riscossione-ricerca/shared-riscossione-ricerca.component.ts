import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { RicercaSoggettiRiscossioneCoattivaRequest } from "../../../commons/request/riscossione/ricerca-soggetti-riscossione-coattiva-request";
import {
  SelectVO,
  StatoOrdinanzaVO,
  StatoSentenzaVO,
} from "../../../commons/vo/select-vo";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { CalendarUtils } from "../../../fase-giurisdizionale/module/calendar/component/commons/calendar-utils";

declare var $: any;

@Component({
  selector: "shared-riscossione-ricerca",
  templateUrl: "./shared-riscossione-ricerca.component.html",
  styleUrls: ["./shared-riscossione-ricerca.component.scss"],
})
export class SharedRiscossioneRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};
  public tabOrdinanza: boolean = false;
  public tabSentenza: boolean = false;
  public tabRateizzazione: boolean = false;

  public ordinanzaModel: Array<StatoOrdinanzaVO>;
  public sentenzaModel: Array<StatoSentenzaVO>;

  public loadedOrdinanza: boolean;
  public loadedSentenza: boolean;

  public numeroProtocolloSentenza: string;
  public statoSentenza: StatoSentenzaVO;
  public dataSentenzaDa: string;
  public dataSentenzaA: string;

  public numDetOrdinanza: string;
  public dataNotificaDa: string;
  public dataNotificaA: string;

  @Input()
  enableRicercaSollecito: boolean; //true per Riscossione/Sollecito e per Gestione Pagamenti/Riconcilia sollecito
  @Input()
  enableCampiRiscossione: boolean; //true per Riscossione/Sollecito e per Riscossione/Riscossione coattiva
  @Input()
  public ricercaSollecitoRequest: RicercaSoggettiOrdinanzaRequest;
  @Input()
  public ricercaSoggettiRiscossioneCoattivaRequest: RicercaSoggettiRiscossioneCoattivaRequest;
  @Output()
  ricerca: EventEmitter<RicercaSoggettiOrdinanzaRequest> = new EventEmitter();
  @Output()
  ricercaRiscossione: EventEmitter<RicercaSoggettiRiscossioneCoattivaRequest> = new EventEmitter();
  @Output()
  messaggio: EventEmitter<any> = new EventEmitter();

  constructor(
    private logger: LoggerService,
    private allegatoSharedService: AllegatoSharedService,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedRiscossioneRicercaComponent.name);
    //if (this.enableRicercaPiano) this.loadStatiRateizzazione();
    this.setRequest();
    this.loadModels();
  }

  loadModels() {
    /*if(this.enableCampiRiscossione){
            this.loadedOrdinanza = false;
            this.sharedOrdinanzaService.getStatiOrdinanza().subscribe(data => {
                this.ordinanzaModel = data;
                this.loadedOrdinanza = true;
            });
        } */
    if (this.enableCampiRiscossione) {
      this.loadedSentenza = false;
      this.allegatoSharedService
        .getDecodificaSelectAllegato(Constants.ID_ELENCO_ESITO_SENTENZA)
        .subscribe((data) => {
          this.sentenzaModel = data.filter(
            (a) => a.id != Constants.ID_ELEMENTO_ELENCO_RICORSO_ACCOLTO
          );
          this.loadedSentenza = true;
        });
    }
  }

  tabActions: any = {
    changeOrdinanza: (event: Event) => {
      this.tabOrdinanza = !this.tabOrdinanza;
      if (!this.tabOrdinanza) {
        this.tabSentenza = true;
        if (!this.enableCampiRiscossione) {
          this.ricercaSollecitoRequest.numeroDeterminazione = null;
        } else {
          this.numDetOrdinanza = null;
          this.dataNotificaDa = null;
          this.dataNotificaA = null;
          this.numeroProtocolloSentenza = null;
          this.dataSentenzaA = null;
          this.dataSentenzaDa = null;
          this.statoSentenza = null;
        }
      } else {
        this.tabSentenza = false;
        this.tabRateizzazione = false;
        this.numeroProtocolloSentenza = null;
        this.dataSentenzaA = null;
        this.dataSentenzaDa = null;
        this.statoSentenza = null;
        this.numDetOrdinanza = null;
        this.dataNotificaDa = null;
        this.dataNotificaA = null;
        /*this.ricercaSollecitoRequest.statoPiano = null;
                this.ricercaSollecitoRequest.numeroProtocolloPiano = null;*/
      }
    },
    changeSentenza: () => {
      this.tabSentenza = !this.tabSentenza;
      if (!this.tabSentenza) {
        this.tabOrdinanza = true;
        this.numeroProtocolloSentenza = null;
        this.dataSentenzaA = null;
        this.dataSentenzaDa = null;
        this.statoSentenza = null;
        this.numDetOrdinanza = null;
        this.dataNotificaDa = null;
        this.dataNotificaA = null;
      } else {
        this.tabOrdinanza = false;
        this.tabRateizzazione = false;
        if (!this.enableCampiRiscossione) {
          this.ricercaSollecitoRequest.numeroDeterminazione = null;
        } else {
          this.numeroProtocolloSentenza = null;
          this.dataSentenzaA = null;
          this.dataSentenzaDa = null;
          this.statoSentenza = null;
          this.numDetOrdinanza = null;
          //this.ricercaSoggettiRiscossioneCoattivaRequest.statoOrdinanza = new StatoOrdinanzaVO();
          this.dataNotificaDa = null;
          this.dataNotificaA = null;
        }
        /*this.ricercaSollecitoRequest.statoPiano = null;
                this.ricercaSollecitoRequest.numeroProtocolloPiano = null;*/
      }
    },
    changeRateizzazione: () => {
      this.tabRateizzazione = !this.tabRateizzazione;
      if (!this.tabRateizzazione) {
        this.tabOrdinanza = true;
        /*this.ricercaSollecitoRequest.statoPiano = null;
                this.ricercaSollecitoRequest.numeroProtocolloPiano = null;*/
      } else {
        this.tabOrdinanza = false;
        this.tabSentenza = false;
        this.numeroProtocolloSentenza = null;
        this.ricercaSollecitoRequest.numeroDeterminazione = null;
      }
    },
  };

  setRequest() {
    if (this.enableCampiRiscossione && !this.enableRicercaSollecito) {
      this.numeroProtocolloSentenza = this.ricercaSoggettiRiscossioneCoattivaRequest.numeroProtocolloSentenza;
      this.dataSentenzaA = this.ricercaSoggettiRiscossioneCoattivaRequest.dataSentenzaA;
      this.dataSentenzaDa = this.ricercaSoggettiRiscossioneCoattivaRequest.dataSentenzaDa;
      this.statoSentenza = this.ricercaSoggettiRiscossioneCoattivaRequest.statoSentenza;
      this.numDetOrdinanza = this.ricercaSoggettiRiscossioneCoattivaRequest.numeroDeterminazione;
      this.dataNotificaDa = this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaDa;
      this.dataNotificaA = this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaA;
    } else if (this.enableCampiRiscossione && this.enableRicercaSollecito) {
      this.numeroProtocolloSentenza = this.ricercaSollecitoRequest.numeroProtocolloSentenza;
      this.dataSentenzaA = this.ricercaSollecitoRequest.dataSentenzaA;
      this.dataSentenzaDa = this.ricercaSollecitoRequest.dataSentenzaDa;
      this.statoSentenza = this.ricercaSollecitoRequest.statoSentenza;
      this.numDetOrdinanza = this.ricercaSollecitoRequest.numeroDeterminazione;
      this.dataNotificaDa = this.ricercaSollecitoRequest.dataNotificaDa;
      this.dataNotificaA = this.ricercaSollecitoRequest.dataNotificaA;
    }
    if (!this.enableCampiRiscossione) {
      if (this.ricercaSollecitoRequest == null) this.pulisciFiltri();
      if (this.ricercaSollecitoRequest.numeroDeterminazione != null) {
        this.tabOrdinanza = true;
      }
      /*else if (this.ricercaSollecitoRequest.statoPiano != null || this.ricercaSollecitoRequest.numeroProtocolloPiano != null) {
                this.tabRateizzazione = true;
            }*/
    } else if (this.enableCampiRiscossione && !this.enableRicercaSollecito) {
      if (this.ricercaSoggettiRiscossioneCoattivaRequest == null)
        this.pulisciFiltri();
      if (
        this.ricercaSoggettiRiscossioneCoattivaRequest.numeroDeterminazione !=
          null ||
        this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaDa != null ||
        this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaA != null
      ) {
        this.tabOrdinanza = true;
      } else if (
        this.ricercaSoggettiRiscossioneCoattivaRequest
          .numeroProtocolloSentenza != null ||
        this.ricercaSoggettiRiscossioneCoattivaRequest.statoSentenza != null ||
        this.ricercaSoggettiRiscossioneCoattivaRequest.dataSentenzaA != null
      ) {
        this.tabSentenza = true;
      }
    } else if (this.enableCampiRiscossione && this.enableRicercaSollecito) {
      if (this.ricercaSollecitoRequest == null) this.pulisciFiltri();
      if (
        this.ricercaSollecitoRequest.numeroDeterminazione != null ||
        this.ricercaSollecitoRequest.dataNotificaA != null
      ) {
        this.tabOrdinanza = true;
      }
      if (
        this.ricercaSollecitoRequest.numeroProtocolloSentenza != null ||
        this.ricercaSollecitoRequest.statoSentenza != null ||
        this.ricercaSollecitoRequest.dataSentenzaA != null
      ) {
        this.tabSentenza = true;
      }
    }
  }

  isDisable(s: string) {
    if (s == "PD") {
      if (this.dataSentenzaA != null && this.dataSentenzaA.length != 0)
        return true;
      else if (this.dataSentenzaDa != null && this.dataSentenzaDa.length != 0)
        return true;
      else if (this.statoSentenza != null) {
        return true;
      }
    } else if (s == "ED") {
      return (
        this.numeroProtocolloSentenza != null &&
        this.numeroProtocolloSentenza.length != 0
      );
    } else if (s == "DAS") {
      return (
        this.numeroProtocolloSentenza != null &&
        this.numeroProtocolloSentenza.length != 0
      );
    }
    return false;
  }

  /*private loadStatiRateizzazione() {
        this.loadedStatoRateizzazione = false;
        this.sharedPagamentiService.getStatiPiano().subscribe(data => {
            this.statoRateizzazioneModel = data;
            this.loadedStatoRateizzazione = true;
        });
    }*/

  emitRicerca() {
    let today = new Date();
    let date = CalendarUtils.convertToString(today).substring(0, 10);
    if (
      this.dataNotificaDa != null &&
      (!CalendarUtils.before(this.dataNotificaDa, date) ||
        !CalendarUtils.before(this.dataNotificaA, date))
    ) {
      this.messaggio.emit("Le date inserite non possono essere date future");
    } else if (
      this.dataSentenzaDa != null &&
      (!CalendarUtils.before(this.dataSentenzaDa, date) ||
        !CalendarUtils.before(this.dataSentenzaA, date))
    ) {
      this.messaggio.emit("Le date inserite non possono essere date future");
    } else {
      if (this.enableRicercaSollecito) {
        this.ricerca.emit(this.ricercaSollecitoRequest);
      } else {
        this.ricercaRiscossione.emit(
          this.ricercaSoggettiRiscossioneCoattivaRequest
        );
      }
    }
  }

  pulisciFiltri() {
    if (this.enableRicercaSollecito) {
      this.ricercaSollecitoRequest = new RicercaSoggettiOrdinanzaRequest();
    } else {
      this.ricercaSoggettiRiscossioneCoattivaRequest = new RicercaSoggettiRiscossioneCoattivaRequest();
    }
    this.numeroProtocolloSentenza = null;
    this.dataSentenzaA = null;
    this.dataSentenzaDa = null;
    this.statoSentenza = null;
  }

  //restituisce false se non ho popolato nessun campo (e sono nella pagina di riscossione coattiva)
  condizionePerRiscossione(): boolean {
    if (this.enableCampiRiscossione && !this.enableRicercaSollecito) {
      this.ricercaSoggettiRiscossioneCoattivaRequest.numeroProtocolloSentenza = this.numeroProtocolloSentenza;
      this.ricercaSoggettiRiscossioneCoattivaRequest.dataSentenzaA = this.dataSentenzaA;
      this.ricercaSoggettiRiscossioneCoattivaRequest.dataSentenzaDa = this.dataSentenzaDa;
      this.ricercaSoggettiRiscossioneCoattivaRequest.statoSentenza = this.statoSentenza;
      this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaDa = this.dataNotificaDa;
      this.ricercaSoggettiRiscossioneCoattivaRequest.dataNotificaA = this.dataNotificaA;
      this.ricercaSoggettiRiscossioneCoattivaRequest.numeroDeterminazione = this.numDetOrdinanza;
    } else if (this.enableCampiRiscossione && this.enableRicercaSollecito) {
      this.ricercaSollecitoRequest.numeroProtocolloSentenza = this.numeroProtocolloSentenza;
      this.ricercaSollecitoRequest.dataSentenzaA = this.dataSentenzaA;
      this.ricercaSollecitoRequest.dataSentenzaDa = this.dataSentenzaDa;
      this.ricercaSollecitoRequest.statoSentenza = this.statoSentenza;
      this.ricercaSollecitoRequest.dataNotificaDa = this.dataNotificaDa;
      this.ricercaSollecitoRequest.dataNotificaA = this.dataNotificaA;
      this.ricercaSollecitoRequest.numeroDeterminazione = this.numDetOrdinanza;
    }
    if (this.enableCampiRiscossione && !this.enableRicercaSollecito) {
      let flag: boolean = false;
      for (var field in this.ricercaSoggettiRiscossioneCoattivaRequest) {
        if (
          this.ricercaSoggettiRiscossioneCoattivaRequest.hasOwnProperty(field)
        ) {
          if (
            this.ricercaSoggettiRiscossioneCoattivaRequest[field] instanceof
            Object
          ) {
            for (var field_2 in this.ricercaSoggettiRiscossioneCoattivaRequest[
              field
            ]) {
              if (
                this.ricercaSoggettiRiscossioneCoattivaRequest[
                  field
                ].hasOwnProperty(field_2)
              ) {
                //strato 3 non considerato
                if (
                  this.ricercaSoggettiRiscossioneCoattivaRequest[field][field_2]
                )
                  flag = true;
              }
            }
          } else if (this.ricercaSoggettiRiscossioneCoattivaRequest[field])
            flag = true;
        }
      }
      return flag;
    } else return true;
  }

  manageDatePicker(event: any, i: number) {
    if ($("#datetimepicker").length > 0) {
      $("#datetimepicker").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker1").length > 0) {
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker3").length > 0) {
      $("#datetimepicker3").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker4").length > 0) {
      $("#datetimepicker4").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if (event != null) {
      switch (i) {
        case 1: {
          this.dataNotificaDa = event.srcElement.value;
          this.dataNotificaA = event.srcElement.value;
          break;
        }
        case 2: {
          this.dataNotificaA = event.srcElement.value;
          let flag: boolean = CalendarUtils.before(
            this.dataNotificaDa,
            this.dataNotificaA
          );
          if (!flag) {
            this.dataNotificaDa = event.srcElement.value;
          }
          break;
        }
        case 3: {
          this.dataSentenzaDa = event.srcElement.value;
          this.dataSentenzaA = event.srcElement.value;
          break;
        }
        case 4: {
          this.dataSentenzaA = event.srcElement.value;
          let flag: boolean = CalendarUtils.before(
            this.dataSentenzaDa,
            this.dataSentenzaA
          );
          if (!flag) {
            this.dataSentenzaDa = event.srcElement.value;
          }
          break;
        }
      }
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    this.manageDatePicker(null, 2);
    this.manageDatePicker(null, 3);
    this.manageDatePicker(null, 4);
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedRiscossioneRicercaComponent.name);
  }

  interceptor(event: Event) {
    event.preventDefault();
  }
}
