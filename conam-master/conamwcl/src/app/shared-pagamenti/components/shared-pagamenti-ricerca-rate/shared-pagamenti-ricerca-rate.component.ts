import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import {
  StatoPianoVO,
  StatoSentenzaVO,
  SelectVO,
  StatoOrdinanzaVO,
} from "../../../commons/vo/select-vo";

import { IfObservable } from "rxjs/observable/IfObservable";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { Constants } from "../../../commons/class/constants";
import { CalendarUtils } from "../../../fase-giurisdizionale/module/calendar/component/commons/calendar-utils";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { SharedPagamentiService } from "../../services/shared-pagamenti.service";

declare var $: any;

@Component({
  selector: "shared-pagamenti-ricerca-rate",
  templateUrl: "./shared-pagamenti-ricerca-rate.component.html",
  styleUrls: ["./shared-pagamenti-ricerca-rate.component.scss"],
})
export class SharedPagamentiRicercaRateComponent implements OnInit, OnDestroy {
  public subscribers: any = {};
  public tabOrdinanza: boolean = false;
  public tabSentenza: boolean = false;
  public tabRateizzazione: boolean = false;

  public loadedStatoRateizzazione: boolean = true;
  public statoRateizzazioneModel: Array<StatoPianoVO>;
  public statoPianoSelezionato: StatoPianoVO;
  public sentenzaModel: Array<StatoSentenzaVO>;
  public loadedSentenza: boolean;

  public statoOrdinanzaModel: Array<StatoOrdinanzaVO>;
  public loadedStatoOrdinanza: boolean;

  public dateStart: string;
  public dateEnd: string;
  public dateStartS: string;
  public dateEndS: string;
  public dateStartSNP: string;
  public dateEndSNP: string;

  @Input()
  enableRicercaPiano: boolean;
  @Input()
  isRiconciliaPiano: boolean;
  @Output()
  ricerca: EventEmitter<RicercaPianoRateizzazioneRequest> = new EventEmitter();
  @Input()
  enableCampiCreaPiano: boolean;
  @Input()
  public ricercaPianoRequest: RicercaPianoRateizzazioneRequest;
  @Output()
  messaggio: EventEmitter<any> = new EventEmitter();

  constructor(
    private logger: LoggerService,
    private allegatoSharedService: AllegatoSharedService,
    private sharedPagamentiService: SharedPagamentiService,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedPagamentiRicercaRateComponent.name);
    this.loadStatiRateizzazione();
    this.setRequest();
    this.loadModels();
  }

  loadModels() {
    this.loadedSentenza = false;

    this.allegatoSharedService
      .getDecodificaSelectAllegato(Constants.ID_ELENCO_ESITO_SENTENZA)
      .subscribe((data) => {
        this.sentenzaModel = data.filter(
          (a) => a.id != Constants.ID_ELEMENTO_ELENCO_RICORSO_ACCOLTO
        );
        this.loadedSentenza = true;
      });
    if (this.enableCampiCreaPiano) {
      this.loadedStatoOrdinanza = false;
      this.sharedOrdinanzaService.getStatiOrdinanza().subscribe((data) => {
        this.statoOrdinanzaModel = data;
        this.loadedStatoOrdinanza = true;
      });
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  tabActions: any = {
   
    changeRateizzazione: () => {
      this.tabRateizzazione = !this.tabRateizzazione;
      if (!this.tabRateizzazione) {
        this.tabOrdinanza = true;
        this.statoPianoSelezionato = null;
        this.ricercaPianoRequest.statoPiano = null;
        this.ricercaPianoRequest.numeroProtocolloPiano = null;
        this.ricercaPianoRequest.dataCreazioneA = null;
        this.ricercaPianoRequest.dataCreazioneDa = null;
        this.dateStartSNP = null;
        this.dateEndSNP = null;
      } else {
        this.tabOrdinanza = false;
        this.tabSentenza = false;
        this.ricercaPianoRequest.numeroProtocolloSentenza = null;
        this.ricercaPianoRequest.statoSentenza = null;
        this.ricercaPianoRequest.dataSentenzaA = null;
        this.ricercaPianoRequest.dataSentenzaDa = null;
        this.dateStartS = null;
        this.dateEndS = null;
        this.ricercaPianoRequest.numeroDeterminazione = null;
      }
    },
  };

  setRequest() {
    if (this.ricercaPianoRequest == null) this.pulisciFiltri();
    if (
      this.ricercaPianoRequest.numeroDeterminazione != null ||
      this.ricercaPianoRequest.dataNotificaDa != null
    ) {
      this.tabOrdinanza = true;
      this.dateStart = this.ricercaPianoRequest.dataNotificaDa;
      this.dateEnd = this.ricercaPianoRequest.dataNotificaA;
    } else if (
      this.ricercaPianoRequest.numeroProtocolloSentenza != null ||
      this.ricercaPianoRequest.statoSentenza != null ||
      this.ricercaPianoRequest.dataSentenzaA != null
    ) {
      this.tabSentenza = true;
      this.dateStartS = this.ricercaPianoRequest.dataSentenzaDa;
      this.dateEndS = this.ricercaPianoRequest.dataSentenzaA;
    } else if (
      this.ricercaPianoRequest.statoPiano != null ||
      this.ricercaPianoRequest.numeroProtocolloPiano != null ||
      this.ricercaPianoRequest.dataCreazioneA != null
    ) {
      if (this.ricercaPianoRequest.statoPiano.length == 1)
        this.statoPianoSelezionato = this.ricercaPianoRequest.statoPiano[0];
      this.tabRateizzazione = true;
      this.dateStartSNP = this.ricercaPianoRequest.dataCreazioneDa;
      this.dateEndSNP = this.ricercaPianoRequest.dataCreazioneA;
    }
  }

  private loadStatiRateizzazione() {
    this.loadedStatoRateizzazione = false;
    this.sharedPagamentiService
      .getStatiPiano(this.isRiconciliaPiano)
      .subscribe((data) => {
        this.statoRateizzazioneModel = data;
        this.loadedStatoRateizzazione = true;
      });
  }

  manageDatePicker() {
    if ($("#datetimepicker1").length > 0) {
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker2").length > 0) {
      $("#datetimepicker2").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
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
        locale: "it",
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
        locale: "it",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker5").length > 0) {
      $("#datetimepicker5").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker6").length > 0) {
      $("#datetimepicker6").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
  }

  setDate(date: string, param) {
    if (param == "start") {
      this.dateStart = date;
      this.dateEnd = date;
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY",
      });

      this.ricercaPianoRequest.dataNotificaDa = date;
      this.ricercaPianoRequest.dataNotificaA = date;
    } else if (param == "end") {
      this.dateEnd = date;
      $("#datetimepicker2").datetimepicker({
        format: "DD/MM/YYYY",
      });

      this.ricercaPianoRequest.dataNotificaA = date;
      let flag: boolean = CalendarUtils.before(
        this.ricercaPianoRequest.dataNotificaDa,
        this.ricercaPianoRequest.dataNotificaA
      );
      if (!flag) {
        this.ricercaPianoRequest.dataNotificaDa = date;
        this.dateStart = date;
      }
    }
  }

  setDateS(date: string, param) {
    if (param == "start") {
      this.dateStartS = date;
      this.dateEndS = date;
      $("#datetimepicker3").datetimepicker({
        format: "DD/MM/YYYY",
      });
      this.ricercaPianoRequest.dataSentenzaDa = date;
      this.ricercaPianoRequest.dataSentenzaA = date;
    } else if (param == "end") {
      this.dateEndS = date;
      $("#datetimepicker4").datetimepicker({
        format: "DD/MM/YYYY",
      });
      this.ricercaPianoRequest.dataSentenzaA = date;
      let flag: boolean = CalendarUtils.before(
        this.ricercaPianoRequest.dataSentenzaDa,
        this.ricercaPianoRequest.dataSentenzaA
      );
      if (!flag) {
        this.ricercaPianoRequest.dataSentenzaDa = date;
        this.dateStartS = date;
      }
    }
  }

  setDateSNP(date: string, param) {
    if (param == "start") {
      this.dateStartSNP = date;
      this.dateEndSNP = date;
      $("#datetimepicker5").datetimepicker({
        format: "DD/MM/YYYY",
      });
      this.ricercaPianoRequest.dataCreazioneA = date;
      this.ricercaPianoRequest.dataCreazioneDa = date;
    } else if (param == "end") {
      this.dateEndSNP = date;
      $("#datetimepicker6").datetimepicker({
        format: "DD/MM/YYYY",
      });
      this.ricercaPianoRequest.dataCreazioneA = date;
      let flag: boolean = CalendarUtils.before(
        this.ricercaPianoRequest.dataCreazioneDa,
        this.ricercaPianoRequest.dataCreazioneA
      );
      if (!flag) {
        this.ricercaPianoRequest.dataCreazioneDa = date;
        this.dateStartSNP = date;
      }
    }
  }

  isDisable(s: string) {
    if (s == "ND") {
      if (
        this.ricercaPianoRequest.dataNotificaA != null &&
        this.ricercaPianoRequest.dataNotificaA.length != 0
      ) {
        return true;
      } else if (
        this.ricercaPianoRequest.dataNotificaDa != null &&
        this.ricercaPianoRequest.dataNotificaDa.length != 0
      ) {
        return true;
      } else if (this.ricercaPianoRequest.statoOrdinanza != null) {
        return true;
      }
    } else if (s == "DA") {
      return (
        this.ricercaPianoRequest.numeroDeterminazione != null &&
        this.ricercaPianoRequest.numeroDeterminazione.length != 0
      );
    } else if (s == "SO") {
      return (
        this.ricercaPianoRequest.numeroDeterminazione != null &&
        this.ricercaPianoRequest.numeroDeterminazione.length != 0
      );
    } else if (s == "PD") {
      if (
        this.ricercaPianoRequest.dataSentenzaA != null &&
        this.ricercaPianoRequest.dataSentenzaA.length != 0
      )
        return true;
      else if (
        this.ricercaPianoRequest.dataSentenzaDa != null &&
        this.ricercaPianoRequest.dataSentenzaDa.length != 0
      )
        return true;
      else if (this.ricercaPianoRequest.statoSentenza != null) {
        return true;
      }
    } else if (s == "ED") {
      return (
        this.ricercaPianoRequest.numeroProtocolloSentenza != null &&
        this.ricercaPianoRequest.numeroProtocolloSentenza.length != 0
      );
    } else if (s == "DAS") {
      return (
        this.ricercaPianoRequest.numeroProtocolloSentenza != null &&
        this.ricercaPianoRequest.numeroProtocolloSentenza.length != 0
      );
    } else if (s == "SPD") {
      if (
        this.ricercaPianoRequest.dataCreazioneA != null &&
        this.ricercaPianoRequest.dataCreazioneA.length != 0
      )
        return true;
      else if (
        this.ricercaPianoRequest.dataCreazioneDa != null &&
        this.ricercaPianoRequest.dataCreazioneDa.length != 0
      )
        return true;
      else if (this.statoPianoSelezionato != null) {
        return true;
      }
    } else if (s == "SNP") {
      return (
        this.ricercaPianoRequest.numeroProtocolloPiano != null &&
        this.ricercaPianoRequest.numeroProtocolloPiano.length != 0
      );
    } else if (s == "DASNP") {
      return (
        this.ricercaPianoRequest.numeroProtocolloPiano != null &&
        this.ricercaPianoRequest.numeroProtocolloPiano.length != 0
      );
    }
    return false;
  }

  emitRicerca() {
    let today = new Date();
    let date = CalendarUtils.convertToString(today).substring(0, 10);
    if (
      (this.ricercaPianoRequest.dataNotificaDa != null &&
        (!CalendarUtils.before(this.ricercaPianoRequest.dataNotificaDa, date) ||
          !CalendarUtils.before(
            this.ricercaPianoRequest.dataNotificaA,
            date
          ))) ||
      (this.ricercaPianoRequest.dataSentenzaDa != null &&
        (!CalendarUtils.before(this.ricercaPianoRequest.dataSentenzaDa, date) ||
          !CalendarUtils.before(
            this.ricercaPianoRequest.dataSentenzaA,
            date
          ))) ||
      (this.ricercaPianoRequest.dataCreazioneDa != null &&
        (!CalendarUtils.before(
          this.ricercaPianoRequest.dataCreazioneDa,
          date
        ) ||
          !CalendarUtils.before(this.ricercaPianoRequest.dataCreazioneA, date)))
    ) {
      this.messaggio.emit("Le date inserite non possono essere date future");
    } else {
      if (this.statoPianoSelezionato) {
        let statoPianoArray = new Array<StatoPianoVO>();
        statoPianoArray.push(this.statoPianoSelezionato);
        this.ricercaPianoRequest.statoPiano = statoPianoArray;
      }
      this.ricerca.emit(this.ricercaPianoRequest);
      this.loadModels();
    }
  }

  pulisciFiltri() {
    this.ricercaPianoRequest = new RicercaPianoRateizzazioneRequest();
    this.statoPianoSelezionato = null;
    this.dateStart = null;
    this.dateStartS = null;
    this.dateStartSNP = null;
    this.dateEnd = null;
    this.dateEndS = null;
    this.dateEndSNP = null;
  }

  compareFn(c1: StatoPianoVO, c2: StatoPianoVO): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  ngAfterViewChecked() {
    //this.manageDatePicker(null, 1);
    //this.manageDatePicker(null, 2);
    this.manageDatePicker();
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedPagamentiRicercaRateComponent.name);
  }

  interceptor(event: Event) {
    event.preventDefault();
  }
}
