import {
  Component,
  OnInit,
  OnDestroy,
  HostListener,
  NgZone,
} from "@angular/core";
import { Routing } from "../../../commons/routing";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { RiscossioneService } from "../../services/riscossione.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedRiscossioneConfigService } from "../../../shared-riscossione/services/shared-riscossione-config.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TableSoggettiRiscossione } from "../../../commons/table/table-soggetti-riscossione";
import { map } from "rxjs/operators";
import { Constants } from "../../../commons/class/constants";
import { SalvaSoggettiRiscossioneCoattivaRequest } from "../../../commons/request/riscossione/salva-soggetti-riscossione-coattiva-request";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { RicercaStoricaSoggettiRiscossioneCoattivaRequest } from "../../../commons/request/riscossione/ricerca-storica-soggetti-riscossione-coattiva-request";
import { Router } from "@angular/router";
import { ValueTransformer } from "@angular/compiler/src/util";
import { SelectVO } from "../../../commons/vo/select-vo";

@Component({
  selector: "riscossione-riscossione-coattiva-elenco",
  templateUrl: "./riscossione-riscossione-coattiva-elenco.component.html",
})
export class RiscossioneRiscossioneCoattivaElencoComponent
  implements OnInit, OnDestroy {
  //general
  public loaded: boolean;
  public loadedAvviata: boolean;
  public enableInvioMassivoRiscossione: boolean = false;
  public subscribers: any = {};
  public configSoggettiModificabili: Config;
  public configSoggettiNonModificabili: Config;
  public soggettiRiscossioneModificabili: Array<TableSoggettiRiscossione> = new Array<TableSoggettiRiscossione>();
  public soggettiRiscossioneNonModificabili: Array<TableSoggettiRiscossione> = new Array<TableSoggettiRiscossione>();

  //edit
  public showEdit: boolean;
  public editModel: TableSoggettiRiscossione;

  //ricerca storica
  public ricerca: RicercaStoricaSoggettiRiscossioneCoattivaRequest = new RicercaStoricaSoggettiRiscossioneCoattivaRequest();
  public soggettiStoriciRiscossione: Array<TableSoggettiRiscossione> = new Array<TableSoggettiRiscossione>();
  public loadedStoriciSoggettiRisccossione: boolean = true;
  public showTableRiscossione: boolean;

  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  public scrollTopEnable: boolean;

  public importoOrdinanza: number;

  public scrollBar: boolean;
  public scrollBarAvviata: boolean;

  public loaderStato: boolean;
  public statoModel: Array<SelectVO> = new Array<SelectVO>();

  constructor(
    private logger: LoggerService,
    private riscossioneService: RiscossioneService,
    private sharedRiscossioneConfigService: SharedRiscossioneConfigService,
    private utilSubscribersService: UtilSubscribersService,
    private numberUtilsSharedService: NumberUtilsSharedService,
    private router: Router,
    private ngZone: NgZone
  ) {
    window.onresize = (e) => {
      //ngZone.run will help to run change detection
      this.ngZone.run(() => {
        this.hasScrollBar(window.innerWidth);
      });
    };
  }

  isEditable: (el: TableSoggettiRiscossione) => boolean = (
    el: TableSoggettiRiscossione
  ) => {
    return el.statoRiscossione.id == Constants.ID_STATO_RISCOSSIONE_BOZZA;
  };

  isDeletable: (el: TableSoggettiRiscossione) => boolean = (
    el: TableSoggettiRiscossione
  ) => {
    return el.statoRiscossione.id == Constants.ID_STATO_RISCOSSIONE_BOZZA;
  };

  ngOnInit(): void {
    this.loadStato();
    this.logger.init(RiscossioneRiscossioneCoattivaElencoComponent.name);
    this.configSoggettiModificabili = this.sharedRiscossioneConfigService.getConfigOrdinanzaRiscossioneElenco(
      this.isEditable,
      this.isDeletable
    );
    this.configSoggettiNonModificabili = this.sharedRiscossioneConfigService.getConfigOrdinanzaRiscossioneElencoStato(
      null,
      null
    );
    this.riscossioneService
      .getSoggettiRiscossioneBozza()
      .map((val) => val.map((value) => TableSoggettiRiscossione.map(value)))
      .subscribe(
        (val) => {
          this.soggettiRiscossioneModificabili = val;
          if (
            this.soggettiRiscossioneModificabili != null &&
            this.soggettiRiscossioneModificabili.length > 0
          )
            this.enableInvioMassivoRiscossione = this.soggettiRiscossioneModificabili[0].enableInvioMassivo;

          this.loaded = true;
        },
        (err) => {
          this.loaded = true;
          this.logger.error("Errore durante il recupero del soggetto");
        }
      );

    this.loadedAvviata = true;

    this.hasScrollBar(window.innerWidth);
    this.importoOrdinanza = 0;
  }

  hasScrollBar(width: number) {
    if (width < 1340) {
      this.scrollBar = true;
    } else {
      this.scrollBar = false;
    }
    if (width < 1300) {
      this.scrollBarAvviata = true;
    } else {
      this.scrollBarAvviata = false;
    }
  }

  //modifica
  onEdit(ev: TableSoggettiRiscossione) {
    this.showEdit = true;
    this.editModel = JSON.parse(JSON.stringify(ev));
  }

  daStringToNumeber(imp: string): number {
    let importo: number = 0;
    let i: number = 0;
    let tmp: string = "";
    while (i < imp.length) {
      if (
        imp.substring(i, i + 1) != "," &&
        imp.substring(i, i + 1) != "." &&
        imp.substring(i, i + 1) != " "
      ) {
        tmp = tmp + imp.substring(i, i + 1);
      }
      i++;
    }

    importo = parseInt(tmp.trim());
    return importo;
  }

  closeEdit() {
    this.showEdit = false;
    this.editModel = null;
  }

  saveEdit() {
    this.loaded = false;
    let s: SalvaSoggettiRiscossioneCoattivaRequest = new SalvaSoggettiRiscossioneCoattivaRequest();
    s.idRiscossione = this.editModel.idRiscossione;
    s.importoSanzione = +this.editModel.importoSanzione;
    s.importoSpeseNotifica = +this.editModel.importoSpeseNotifica;
    s.importoSpeseLegali = +this.editModel.importoSpeseLegali;
    //s.importoDaRiscuotere = s.importoSanzione + s.importoSpeseNotifica + s.importoSpeseLegali;
    s.dataDecorrenzaInteressi = this.editModel.dataDecorrenzaInteressi;
    this.riscossioneService
      .salvaSoggettoRiscossione(s)
      .map((val) => TableSoggettiRiscossione.map(val))
      .subscribe(
        (val) => {
          let index = this.soggettiRiscossioneModificabili.findIndex(
            (i) => i.idSoggettoOrdinanza == val.idSoggettoOrdinanza
          );
          this.soggettiRiscossioneModificabili[index] = val;
          this.loaded = true;
          this.manageMessageTop("Modifica effettuata con successo", "SUCCESS");
          this.closeEdit();
        },
        (err) => {
          this.loaded = true;
          this.manageMessageTop(
            "Errore durante il salvataggio del soggetto",
            "DANGER"
          );
          this.closeEdit();
        }
      );
  }

  onDelete(ev: TableSoggettiRiscossione) {
    this.loaded = false;
    this.riscossioneService
      .deleteSoggettoRiscossione(ev.idRiscossione)
      .subscribe(
        (val) => {
          let index = this.soggettiRiscossioneModificabili.findIndex(
            (i) => i.idSoggettoOrdinanza == ev.idSoggettoOrdinanza
          );
          this.soggettiRiscossioneModificabili.splice(index, 1);
          this.loaded = true;
          this.manageMessageTop(
            "Soggetto eliminato dalla lista di riscossione coattiva con successo",
            "SUCCESS"
          );
        },
        (err) => {
          this.loaded = true;
          this.logger.error("Errore durante il salvataggio del soggetto");
        }
      );
  }

  isRequired(field: string): boolean {
    switch (field) {
      case "codiceFiscaleFisico":
        if (
          !this.ricerca.codiceFiscaleGiuridico &&
          !this.ricerca.numeroDeterminazioneOrdinanza
        )
          return true;
        else return false;
      case "codiceFiscaleGiuridico":
        if (
          !this.ricerca.codiceFiscaleFisico &&
          !this.ricerca.numeroDeterminazioneOrdinanza
        )
          return true;
        else return false;
      case "numeroDeterminazioneOrdinanza":
        if (
          !this.ricerca.codiceFiscaleFisico &&
          !this.ricerca.codiceFiscaleGiuridico
        )
          return true;
        else return false;
    }
    return true;
  }

  ricercaStorica() {
    this.loadedStoriciSoggettiRisccossione = false;
    this.showTableRiscossione = false;
    this.riscossioneService
      .getSoggettiStoriciRiscossione(this.ricerca)
      .map((val) => val.map((value) => TableSoggettiRiscossione.map(value)))
      .subscribe(
        (val) => {
          this.soggettiStoriciRiscossione = val;
          this.showTableRiscossione = true;
          this.loadedStoriciSoggettiRisccossione = true;
        },
        (err) => {
          this.loadedStoriciSoggettiRisccossione = true;
          this.logger.error("Errore durante il recupero del soggetto");
        }
      );
  }

  inviaMassivoRiscossione() {
    this.scrollTopEnable = true;
    this.loaded = false;
    this.riscossioneService.invioMassivoRiscossione().subscribe(
      (val) => {
        if (val != null && val.esito != null)
          this.manageMessageTop(val.esito, "DANGER");
        else {
          this.manageMessageTop(
            "File creato con successo. Verrà inviato a SORIS per la riscossione degli importi dovuti",
            "SUCCESS"
          );

          this.riscossioneService
            .getSoggettiRiscossioneBozza()
            .map((val) =>
              val.map((value) => TableSoggettiRiscossione.map(value))
            )
            .subscribe(
              (val) => {
                this.soggettiRiscossioneModificabili = val;
              },
              (err) => {
                this.loaded = true;
                this.logger.error("Errore durante il recupero del soggetto");
              }
            );

          this.loadStato();
          this.soggettiRiscossioneNonModificabili = new Array<TableSoggettiRiscossione>();
        }

        this.loaded = true;
      },
      (err) => {
        this.loaded = true;
        this.logger.error("Errore durante il invio massivo");
      }
    );

    this.closeEdit();
  }

  pulisci() {
    this.ricerca.numeroDeterminazioneOrdinanza = null;
    this.ricerca.codiceFiscaleFisico = null;
    this.ricerca.codiceFiscaleGiuridico = null;
  }

  isKeyPressed(code: number): boolean {
    return this.numberUtilsSharedService.numberValid(code);
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

  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

  onInfo(el: TableSoggettiRiscossione | Array<TableSoggettiRiscossione>) {
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      let azione1: string = el.ruolo.denominazione;
      let azione2: string = el.noteSoggetto;
      this.router.navigate([
        Routing.SOGGETTO_RIEPILOGO + el.idSoggetto,
        { ruolo: azione1, nota: azione2 },
      ]);
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(RiscossioneRiscossioneCoattivaElencoComponent.name);
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  loadStato() {
    this.loaderStato = false;
    this.riscossioneService.getstatiRiscossioneCoattiva().subscribe(
      (data) => {
        this.statoModel = data;
        this.loaderStato = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  loadSoggettiNonBozza(id: number) {
    this.loadedAvviata = false;
    this.riscossioneService
      .getSoggettiRiscossioneNoBozza(id)
      .map((val) => val.map((value) => TableSoggettiRiscossione.map(value)))
      .subscribe(
        (val) => {
          this.soggettiRiscossioneNonModificabili = val;
          this.loadedAvviata = true;
        },
        (err) => {
          this.loadedAvviata = true;
          this.logger.error("Errore durante il recupero del soggetto");
        }
      );
  }
}
