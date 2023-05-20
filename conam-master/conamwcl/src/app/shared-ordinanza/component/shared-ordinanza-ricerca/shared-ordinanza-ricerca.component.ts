import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import {
  StatoVerbaleVO,
  StatoOrdinanzaVO,
  SelectVO,
} from "../../../commons/vo/select-vo";
import { SoggettoVerbaleRequest } from "../../../commons/request/verbale/soggetto-verbale-request";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { SharedOrdinanzaService } from "../../service/shared-ordinanza.service";
import { CalendarUtils } from "../../../fase-giurisdizionale/module/calendar/component/commons/calendar-utils";

declare var $: any;

@Component({
  selector: "shared-ordinanza-ricerca",
  templateUrl: "./shared-ordinanza-ricerca.component.html",
  styleUrls: ["./shared-ordinanza-ricerca.component.scss"],
})
export class SharedOrdinanzaRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  //tab1
  public tabOrdinanza: boolean;
  public loadedStatoVerbale: boolean;
  //public isNumVerbObbligatorio: boolean = true;
  //public isNumDetObbligatorio: boolean = true;

  @Input()
  loadedStatiOrdinanza: boolean;
  @Input()
  statiOrdinanzaModel: Array<StatoOrdinanzaVO>;

  //tab2
  public tabTrasgressore: boolean = false;
  public isTrasgressorePersonaFisica: boolean = false;
  public isTrasgressorePersonaGiuridica: boolean = false;
  public soggettoVerbaleTrasgressore: SoggettoVerbaleRequest;

  //tab3
  public tabObbligatoInSolido: boolean = false;
  public isObbligatoInSolidoPersonaFisica: boolean = false;
  public soggettoVerbaleObbligato: SoggettoVerbaleRequest;
  public isObbligatoInSolidoPersonaGiuridica: boolean = false;

  //ricerca
  public ricercaOrdinanzaModel: RicercaOrdinanzaRequest;

  @Output() onSearch = new EventEmitter<RicercaOrdinanzaRequest>();
  @Output() messaggio = new EventEmitter<any>();
  @Input() request: RicercaOrdinanzaRequest;

  constructor(
    private logger: LoggerService,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedOrdinanzaRicercaComponent.name);
    this.setRequest();
  }

  setRequest() {
    if (this.request == null) this.pulisciFiltri();
    else {
      this.ricercaOrdinanzaModel = this.request;
      this.tabOrdinanza = true;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      if (
        this.request.soggettoVerbale != null &&
        this.request.soggettoVerbale.length > 0
      ) {
        for (let x of this.request.soggettoVerbale) {
          if (x.tipoSoggetto == "T") {
            this.soggettoVerbaleTrasgressore = x;
            this.tabTrasgressore = true;
            if (x.personaFisica) this.isTrasgressorePersonaFisica = true;
            else this.isTrasgressorePersonaGiuridica = true;
          } else if (x.tipoSoggetto == "O") {
            this.soggettoVerbaleObbligato = x;
            this.tabObbligatoInSolido = true;
            if (x.personaFisica) this.isObbligatoInSolidoPersonaFisica = true;
            else this.isObbligatoInSolidoPersonaGiuridica = true;
          }
        }
      }
      if (this.ricercaOrdinanzaModel.statoOrdinanza.length > 1) {
        this.ricercaOrdinanzaModel.statoOrdinanza = new Array<StatoOrdinanzaVO>();
      }
    }
  }

  pulisciFiltri() {
    this.ricercaOrdinanzaModel = new RicercaOrdinanzaRequest();
    this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
    this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
    this.soggettoVerbaleObbligato.tipoSoggetto = "O";
    this.tabOrdinanza = false;
    this.tabObbligatoInSolido = false;
    this.isObbligatoInSolidoPersonaFisica = false;
    this.isObbligatoInSolidoPersonaGiuridica = false;
    this.tabTrasgressore = false;
    this.isTrasgressorePersonaFisica = false;
    this.isTrasgressorePersonaGiuridica = false;
    //this.isNumDetObbligatorio = true;
    //this.isNumVerbObbligatorio = true;
  }

  tabActions: any = {
    //Ricordarsi di svuotare i campi quando ci saranno gli oggetti
    changeOrdinanza: () => {
      this.tabOrdinanza = !this.tabOrdinanza;
      if (!this.tabOrdinanza) {
        this.ricercaOrdinanzaModel.numeroDeterminazione = null;
        this.ricercaOrdinanzaModel.numeroVerbale = null;
        this.ricercaOrdinanzaModel.statoOrdinanza = new Array<StatoOrdinanzaVO>();
        this.ricercaOrdinanzaModel.dataOrdinanzaDa = null;
        this.ricercaOrdinanzaModel.dataOrdinanzaA = null;
      }
    },
    changeTrasgressore: () => {
      this.tabTrasgressore = !this.tabTrasgressore;
      if (!this.tabTrasgressore) {
        this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
        this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
        this.isTrasgressorePersonaFisica = false;
        this.isTrasgressorePersonaGiuridica = false;
      } else {
        this.isTrasgressorePersonaFisica = true;
        this.isTrasgressorePersonaGiuridica = false;
      }
    },
    changeObbligatoInSolido: () => {
      this.tabObbligatoInSolido = !this.tabObbligatoInSolido;
      if (!this.tabObbligatoInSolido) {
        this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
        this.soggettoVerbaleObbligato.tipoSoggetto = "O";
        this.isObbligatoInSolidoPersonaFisica = false;
        this.isObbligatoInSolidoPersonaGiuridica = false;
      } else {
        this.isObbligatoInSolidoPersonaFisica = true;
        this.isObbligatoInSolidoPersonaGiuridica = false;
      }
    },
    checkTrasgressorePersonaFisica: () => {
      this.isTrasgressorePersonaFisica = !this.isTrasgressorePersonaFisica;
      this.isTrasgressorePersonaGiuridica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleTrasgressore.personaFisica = true;
    },
    checkTrasgressorePersonaGiuridica: () => {
      this.isTrasgressorePersonaGiuridica = !this
        .isTrasgressorePersonaGiuridica;
      this.isTrasgressorePersonaFisica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleTrasgressore.personaFisica = false;
    },
    checkObbligatoInSolidoPersonaFisica: () => {
      this.isObbligatoInSolidoPersonaFisica = !this
        .isObbligatoInSolidoPersonaFisica;
      this.isObbligatoInSolidoPersonaGiuridica = false;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.soggettoVerbaleObbligato.personaFisica = true;
    },
    checkObbligatoInsolidoPersonaGiuridica: () => {
      this.isObbligatoInSolidoPersonaGiuridica = !this
        .isObbligatoInSolidoPersonaGiuridica;
      this.isObbligatoInSolidoPersonaFisica = false;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.soggettoVerbaleObbligato.personaFisica = false;
    },
    /*checkNumDetObbligatorio: () => {
            this.isNumDetObbligatorio = true;
            this.isNumVerbObbligatorio = false;
        },
        checkNumVerbObbligatorio: () => {
            this.isNumVerbObbligatorio = true;
            this.isNumDetObbligatorio = false;
        }*/
  };

  isDisable(field: string, type: string) {
    if (type == "T") {
      let condFisica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.cognome) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.nome)
        );
      if (field == "CG" || field == "NM")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.codiceFiscale)
        );
      let condGiuridica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale)
        );
    }
    if (type == "O") {
      let condFisica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.cognome) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.nome)
        );
      if (field == "CG" || field == "NM")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.codiceFiscale)
        );
      let condGiuridica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale)
        );
    }
    if (type == "ORD") {
      if (field == "ND") {
        if (
          this.ricercaOrdinanzaModel.dataOrdinanzaA != null &&
          this.ricercaOrdinanzaModel.dataOrdinanzaA.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.dataOrdinanzaDa != null &&
          this.ricercaOrdinanzaModel.dataOrdinanzaDa.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.statoOrdinanza[0] != null &&
          this.ricercaOrdinanzaModel.statoOrdinanza[0].denominazione != null
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.numeroVerbale != null &&
          this.ricercaOrdinanzaModel.numeroVerbale.length != 0
        )
          return true;
      } else if (field == "NV") {
        if (
          this.ricercaOrdinanzaModel.dataOrdinanzaA != null &&
          this.ricercaOrdinanzaModel.dataOrdinanzaA.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.dataOrdinanzaDa != null &&
          this.ricercaOrdinanzaModel.dataOrdinanzaDa.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.statoOrdinanza[0] != null &&
          this.ricercaOrdinanzaModel.statoOrdinanza[0].denominazione != null
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.numeroDeterminazione != null &&
          this.ricercaOrdinanzaModel.numeroDeterminazione.length != 0
        )
          return true;
      } else if (field == "SO") {
        if (
          this.ricercaOrdinanzaModel.numeroDeterminazione != null &&
          this.ricercaOrdinanzaModel.numeroDeterminazione.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.numeroVerbale != null &&
          this.ricercaOrdinanzaModel.numeroVerbale.length != 0
        )
          return true;
      } else if (field == "DAO") {
        if (
          this.ricercaOrdinanzaModel.numeroDeterminazione != null &&
          this.ricercaOrdinanzaModel.numeroDeterminazione.length != 0
        )
          return true;
        if (
          this.ricercaOrdinanzaModel.numeroVerbale != null &&
          this.ricercaOrdinanzaModel.numeroVerbale.length != 0
        )
          return true;
      }
    }
  }

  isEmpty(str) {
    return !str || 0 === str.length;
  }

  manageDatePicker(event: any, i: number) {
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
    if ($("#datetimepicker2").length > 0) {
      $("#datetimepicker2").datetimepicker({
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
          this.ricercaOrdinanzaModel.dataOrdinanzaDa = event.srcElement.value;
          this.ricercaOrdinanzaModel.dataOrdinanzaA = event.srcElement.value;
          break;
        }
        case 2: {
          this.ricercaOrdinanzaModel.dataOrdinanzaA = event.srcElement.value;
          let flag: boolean = CalendarUtils.before(
            this.ricercaOrdinanzaModel.dataOrdinanzaDa,
            this.ricercaOrdinanzaModel.dataOrdinanzaA
          );
          if (!flag) {
            this.ricercaOrdinanzaModel.dataOrdinanzaDa = event.srcElement.value;
          }
          break;
        }
      }
    }
  }

  disableForm(validForm: boolean) {
    let condition: boolean =
      !this.tabOrdinanza &&
      (!this.tabTrasgressore ||
        (!this.isTrasgressorePersonaFisica &&
          !this.isTrasgressorePersonaGiuridica)) &&
      (!this.tabObbligatoInSolido ||
        (!this.isObbligatoInSolidoPersonaFisica &&
          !this.isObbligatoInSolidoPersonaGiuridica));
    if (!validForm || condition) return true;
    return false;
  }

  ricercaOrdinanza() {
    let today = new Date();
    let date = CalendarUtils.convertToString(today).substring(0, 10);
    if (
      this.ricercaOrdinanzaModel.dataOrdinanzaDa != null &&
      (!CalendarUtils.before(
        this.ricercaOrdinanzaModel.dataOrdinanzaDa,
        date
      ) ||
        !CalendarUtils.before(this.ricercaOrdinanzaModel.dataOrdinanzaA, date))
    ) {
      this.messaggio.emit("Le date inserite non possono essere date future");
    } else {
      this.ricercaOrdinanzaModel.soggettoVerbale = new Array<SoggettoVerbaleRequest>();
      if (this.ricercaOrdinanzaModel.statoOrdinanza.length != 1) {
        this.ricercaOrdinanzaModel.statoOrdinanza = this.statiOrdinanzaModel;
      }
      if (this.tabTrasgressore) {
        this.soggettoVerbaleTrasgressore.personaFisica = this.isTrasgressorePersonaFisica;
        this.ricercaOrdinanzaModel.soggettoVerbale.push(
          this.soggettoVerbaleTrasgressore
        );
      }
      if (this.tabObbligatoInSolido) {
        this.soggettoVerbaleObbligato.personaFisica = this.isObbligatoInSolidoPersonaFisica;
        this.ricercaOrdinanzaModel.soggettoVerbale.push(
          this.soggettoVerbaleObbligato
        );
      }

      this.onSearch.emit(this.ricercaOrdinanzaModel);
    }
  }

  interceptor(event: Event) {
    event.preventDefault();
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    this.manageDatePicker(null, 2);
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedOrdinanzaRicercaComponent.name);
  }
}
