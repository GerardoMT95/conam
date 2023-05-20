import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { VerbaleService } from "../../../pregresso/services/verbale.service";
import { MinSoggettoVO } from "../../../commons/vo/verbale/min-soggetto-vo";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";

@Component({
  selector: "recidivita-ricerca",
  templateUrl: "./recidivita-ricerca.component.html",
})
export class RecidivitaRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};
  public showTable: boolean;
  public soggetti: Array<TableSoggettiVerbale>;
  public ordinanzaSel: OrdinanzaVO;
  public loaded: boolean = true;
  public loadedStatiOrdinanza: boolean;
  public statiOrdinanzaModel: Array<StatoOrdinanzaVO>;
  public request: RicercaOrdinanzaRequest;
  public max: boolean = false;

  isSelectable: (el: TableSoggettiVerbale) => boolean = (
    el: TableSoggettiVerbale
  ) => {
    return true;
  };

  constructor(
    private logger: LoggerService,
    private router: Router,
    private verbaleService: VerbaleService
  ) {}

  ngOnInit(): void {
    this.logger.init(RecidivitaRicercaComponent.name);
   
  }

  scrollEnable: boolean;
  ricercaSoggetto(min: MinSoggettoVO) {
    this.loaded = false;
    this.subscribers.ricercaSoggetto = this.verbaleService
      .ricercaSoggettoRecidivita(min[0])
      .subscribe((data) => {
        if (data) {
          this.soggetti = data.map((value) => {
            return TableSoggettiVerbale.map(value);
          });
          this.showTable = true;
          this.loaded = true;
        }
      });
  }

  ngAfterContentChecked() {
    let out: HTMLElement = document.getElementById("scrollBottom");
    if (this.loaded && this.scrollEnable && this.showTable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  onDettaglio(el: any | Array<any>) {
  
   if (el instanceof Array) {
      throw Error("errore sono stati selezionati più elementi");
    } else
      this.router.navigateByUrl(
        Routing.GESTIONE_CONT_AMMI_INSERIMENTO_RECIDIVITA_DETTAGLIO + el.id
      ); 
  }

  messaggio(message: string) {
    this.manageMessageTop(message, "DANGER");
  }

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
    this.scrollTopEnable = true;
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

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }
  public config: Config = {
    pagination: {
        enable: true
    },
    sort: {
        enable: true,
    },
    selection: {
        enable: true,
    },
    buttonSelection: {
        label: "Dettaglio Soggetto",
        enable: true,
    },
    columns: [
        {
            columnName: 'name',
            displayName: 'Nome'
        },
        {
            columnName: 'surname',
            displayName: 'Cognome '
        },
        {
            columnName: 'birthdayDate',
            displayName: 'Data nascita'
        },
        {
            columnName: 'identificativoSoggetto',
            displayName: 'Codice fiscale'
        },
        {
            columnName: 'ragioneSociale',
            displayName: 'Ragione sociale'
        },
        {
            columnName: 'partitaIva',
            displayName: 'Patrtita IVA'
        },
    ]
};
  ngOnDestroy(): void {
    this.logger.destroy(RecidivitaRicercaComponent.name);
  }
}
