import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { RataVO } from "../../../commons/vo/piano-rateizzazione/rata-vo";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { PagamentiService } from "../../services/pagamenti.service";
import { Constants } from "../../../commons/class/constants";
import { StatoRataVO } from "../../../commons/vo/select-vo";
import { TableSoggettiRata } from "../../../commons/table/table-soggetti-rata";

declare var $: any;

@Component({
  selector: "pagamenti-riconcilia-piano-dettaglio",
  templateUrl: "./pagamenti-riconcilia-piano-dettaglio.component.html",
  styleUrls: ["./pagamenti-riconcilia-piano-dettaglio.component.scss"],
})
export class PagamentiRiconciliaPianoDettaglioComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public loaded: boolean;
  public loadedRiconcilia: boolean = true;
  public loadedIdSoggettiOrdinanza: boolean;

  idPiano: number;
  idSoggettiOrdinanza: Array<number>;
  listTableSoggettiOrdinanza: Array<TableSoggettiOrdinanza>;

  public piano: PianoRateizzazioneVO;
  public listaSoggetti: Array<TableSoggettiOrdinanza>;

  configSoggetti: Config;

  isRataDaModificare: boolean;
  rataSelezionata: RataVO;

  public scrollRataEnable: boolean;
  public rate: Array<TableSoggettiRata>;
  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  public scrollRiconciliaEnable: boolean;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private pagamentiService: PagamentiService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      false,
      "",
      null,
      null,
      null,
      (el: any) => true
    );
    this.logger.init(PagamentiRiconciliaPianoDettaglioComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idPiano = +params["idPiano"];
      if (this.idPiano) this.loadPiano();
      else this.router.navigateByUrl(Routing.PAGAMENTI_RICERCA_PIANO);
    });
  }

  loadPiano() {
    this.loaded = false;
    this.loadedIdSoggettiOrdinanza = false;
    this.subscribers.getDettaglio = this.pagamentiService
      .getDettaglioPianoById(this.idPiano, false)
      .subscribe(
        (data) => {
          this.piano = data;
          this.rate = data.rate.map((value) => {
            return TableSoggettiRata.map(value);
          });
          this.idSoggettiOrdinanza = new Array<number>();
          this.idSoggettiOrdinanza = this.piano.soggetti.map(
            (sogg) => sogg.idSoggettoOrdinanza
          );
          this.listaSoggetti = this.piano.soggetti.map((sogg) =>
            TableSoggettiOrdinanza.map(sogg)
          );
          this.loadedIdSoggettiOrdinanza = true;
          this.loaded = true;
          this.scrollRataEnable = true;
        },
        (err) => {
          this.logger.error("Errore durante il caricamento del piano");
          this.loaded = true;
        }
      );
  }

  modificaRata(rataSelezionata: RataVO) {
    this.isRataDaModificare = true;
    this.rataSelezionata = JSON.parse(JSON.stringify(rataSelezionata));
    let out: HTMLElement = document.getElementById("scrollRate");
    if (
      this.loaded &&
      this.scrollRataEnable &&
      this.loadedRiconcilia &&
      out != null
    ) {
      out.scrollIntoView();
    }
  }

  annullaModificaRata() {
    this.isRataDaModificare = false;
    this.rataSelezionata = null;
  }

  isUltimaRata(): boolean {
    for (let i = 0; i < this.piano.rate.length; i++) {
      let stato: StatoRataVO = this.piano.rate[i].stato;
      if (
        stato.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE &&
        stato.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE
      ) {
        return false;
      }
    }
    return true;
  }

  riconciliaRata() {
    this.loadedRiconcilia = false;

    let s: Array<string> = this.rataSelezionata.importoRata
      .toString()
      .split("€");
    if (s != null && s.length > 0) {
      s[0] = s[0].trim().replace(",", ".");
      this.rataSelezionata.importoRata = +s[0];
    }

    this.pagamentiService.riconciliaRata(this.rataSelezionata).subscribe(
      (elsalvato) => {
        let index = this.piano.rate.findIndex(
          (el) =>
            el.idRata == elsalvato.idRata &&
            el.idOrdinanzaVerbaleSoggetto ==
              elsalvato.idOrdinanzaVerbaleSoggetto
        );
        this.piano.rate[index] = elsalvato;
        this.loadedRiconcilia = true;
        this.rataSelezionata = null;
        this.isRataDaModificare = false;
        if (this.isUltimaRata) {
          this.loadPiano();
        }
        this.manageMessageTop("Rata riconciliata con successo", "SUCCESS");
      },
      (err) => {
        this.manageMessageTop(
          "Errore durante la Riconciliazione della rata",
          "DANGER"
        );
      }
    );
  }

  isKeyPressed(code: number): boolean {
    return this.numberUtilsSharedService.numberValid(code);
  }

  public configRate: Config = {
    edit: {
      enable: true,
      isEditable: (rata: RataVO) => {
        return rata.isRiconciliaEnable;
      },
    },
    columns: [
      {
        columnName: "numeroRata",
        displayName: "Numero Rata",
      },
      {
        columnName: "identificativoSoggetto",
        displayName: "Identificativo Soggetto",
      },
      {
        columnName: "stato.denominazione",
        displayName: "Stato",
      },
      {
        columnName: "importoRata",
        displayName: "Importo Rata",
      },
      {
        columnName: "dataPagamento",
        displayName: "Data Pagamento",
      },
      {
        columnName: "importoPagato",
        displayName: "Importo Pagato",
      },
      {
        columnName: "dataScadenza",
        displayName: "Data Scadenza",
      },
      {
        columnName: "codiceAvviso",
        displayName: "Codice Avviso",
      },
    ],
  };

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

  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    let scrollRiconcilia: HTMLElement = document.getElementById(
      "scrollRiconcilia"
    );
    if (
      this.loaded &&
      this.scrollRiconciliaEnable &&
      scrollRiconcilia != null
    ) {
      scrollRiconcilia.scrollIntoView();
      this.scrollRiconciliaEnable = false;
    }
  }

  manageDatePicker(event: any, i: number) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length) {
      $(str).datetimepicker({
        format: "DD/MM/YYYY",
        minDate: new Date(),
      });
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.rataSelezionata.dataPagamento = event.srcElement.value;
          break;
      }
    }
  }

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
    this.scrollRiconciliaEnable = true;
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

  ngOnDestroy(): void {
    this.logger.destroy(PagamentiRiconciliaPianoDettaglioComponent.name);
  }
}
