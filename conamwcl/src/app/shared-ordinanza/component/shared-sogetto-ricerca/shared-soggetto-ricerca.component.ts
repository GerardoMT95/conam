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


declare var $: any;

@Component({
  selector: "shared-soggetto-ricerca",
  templateUrl: "./shared-soggetto-ricerca.component.html",
  styleUrls: ["./shared-soggetto-ricerca.component.scss"],
})
export class SharedSoggettoRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  


  //tab2
  public tabTrasgressore: boolean = false;
  public isTrasgressorePersonaFisica: boolean = false;
  public isTrasgressorePersonaGiuridica: boolean = false;
  public soggettoVerbaleTrasgressore: SoggettoVerbaleRequest;

  loaded: boolean;

  //ricerca
  public ricercaOrdinanzaModel: RicercaOrdinanzaRequest;

  @Output() onSearch = new EventEmitter<SoggettoVerbaleRequest[]>();
  @Output() messaggio = new EventEmitter<any>();
  @Input() request: RicercaOrdinanzaRequest;

  constructor(
    private logger: LoggerService,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedSoggettoRicercaComponent.name);
    this.setRequest();
  }

  setRequest() {
    if (this.request == null) this.pulisciFiltri();
    else {
      this.ricercaOrdinanzaModel = this.request;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    
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
    this.tabTrasgressore = false;
    this.isTrasgressorePersonaFisica = false;
    this.isTrasgressorePersonaGiuridica = false;
  }

  tabActions: any = {
   
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



  disableForm(validForm: boolean) {
    let condition: boolean =
       (!this.tabTrasgressore ||
        (!this.isTrasgressorePersonaFisica &&
          !this.isTrasgressorePersonaGiuridica)) 
             
    if (!validForm || condition) return true;
    return false;
  }

  ricercaSoggetto() {
   
      this.ricercaOrdinanzaModel.soggettoVerbale = new Array<SoggettoVerbaleRequest>();
      if (this.ricercaOrdinanzaModel.statoOrdinanza.length != 1) {
      }
      if (this.tabTrasgressore) {
        this.soggettoVerbaleTrasgressore.personaFisica = this.isTrasgressorePersonaFisica;
        this.ricercaOrdinanzaModel.soggettoVerbale.push(
          this.soggettoVerbaleTrasgressore
        );
      this.onSearch.emit(this.ricercaOrdinanzaModel.soggettoVerbale);
    }
  }

  interceptor(event: Event) {
    event.preventDefault();
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }
  scrollEnable: boolean;
 
  ngAfterViewChecked() {
    let i: number;
    for (i = 1; i < 3; i++) this.manageDatePicker(null, i);

    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }
  manageDatePicker(event: any, i: number) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length) {
      $(str).datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
      });
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.soggettoVerbaleTrasgressore.dataNascita = event.srcElement.value;
          break;
       
      }
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedSoggettoRicercaComponent.name);
  }


}
