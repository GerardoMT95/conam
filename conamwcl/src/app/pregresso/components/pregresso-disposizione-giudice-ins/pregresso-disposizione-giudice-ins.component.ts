import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Routing } from "../../../commons/routing";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";

//JIRA - Gestione Notifica
import { SalvaSollecitoRequest } from "../../../commons/request/riscossione/salva-sollecito-request";
import { SharedRiscossioneSollecitoDettaglioComponent } from "../../../shared-riscossione/components/shared-riscossione-sollecito-dettaglio/shared-riscossione-sollecito-dettaglio.component";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { NgForm } from "@angular/forms";
import { ConfigAllegatoVO } from "../../../commons/vo/verbale/config-allegato-vo";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import {
  FieldTypeVO,
  SelectVO,
  TipoAllegatoVO,
} from "../../../commons/vo/select-vo";
import { AllegatoFieldVO } from "../../../commons/vo/verbale/allegato-field-vo";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";

declare var $: any;
interface Elem {
  value: any;
  fieldType: FieldTypeVO;
  idField: number;
}

@Component({
  selector: "pregresso-disposizione-giudice-ins",
  templateUrl: "./pregresso-disposizione-giudice-ins.component.html",
})
export class PregressoDisposizioneGiudiceInsComponent
  implements OnInit, OnDestroy {
  @Input()
  idOrdinanza: number;

  @Input()
  numDeterminazione: string;

  @Input()
  tipoAllegatoInput: Array<TipoAllegatoVO>;
  @Input()
  allegati: boolean;

  @Output()
  onChangeData: EventEmitter<any> = new EventEmitter<any>();

  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  @ViewChild(SharedOrdinanzaDettaglio)
  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
  @ViewChild(SharedRiscossioneSollecitoDettaglioComponent)
  sharedRiscossioneSollecitoDettaglioComponent: SharedRiscossioneSollecitoDettaglioComponent;
  @ViewChild("creaDisposizioneForm")
  private creaDisposizioneForm: NgForm;

  public mapConfigAllegati: Map<number, Array<Array<ConfigAllegatoVO>>>;

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
  public isModifica: boolean = true;
  public modificabile: boolean;

  //flag nuovo
  public isNuovo: boolean = true;

  public loadedConfig: boolean;
  public comboLoaded: Array<boolean>;
  public validMetadata: boolean;
  public disableAll: boolean;

  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public tipoAllegatoSelezionato: TipoAllegatoVO;
  public configAllegatoSelezionato: Array<Array<ConfigAllegatoVO>>;
  public nomeAllegatoTmp: string;

  public comboModel: Array<Array<SelectVO>>;
  public metaDataModel: Array<AllegatoFieldVO>;
  public tmpModel: Array<Elem>; //un array di elementi senza tipo, da rimappare nell'array effettivo alla fine
  public maxSize: number = 5242880;
  public sizeAlert: boolean = false;
  public typeAlert: boolean = false;

  public eliminaM: boolean = true;
  public confermaM: boolean = true;
  public senzaAllegati: boolean = true;
  public allegatoM: boolean = false;
  public flagAllegatoMaster: boolean = false;
  typeAction: any = {
    isCombo: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_ELENCO : false;
    },
    isDate: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_DATE : false;
    },
    isText: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_STRING : false;
    },
    isNumeric: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_NUMERIC : false;
    },
    isCheckbox: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_BOOLEAN : false;
    },
    isDataOra: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_DATA_ORA : false;
    },
    getInputType: (ft: SelectVO): string => {
      if (
        this.typeAction.isText(ft) ||
        this.typeAction.isDate(ft) ||
        this.typeAction.isDataOra(ft)
      )
        return "text";
      if (this.typeAction.isNumeric(ft)) return "number";
      if (this.typeAction.isCheckbox(ft)) return "checkbox";
    },
  };

  constructor(
    private logger: LoggerService,
    private router: Router,
    private sharedRiscossioneService: SharedRiscossioneService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private allegatoSharedService: AllegatoSharedService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoDisposizioneGiudiceInsComponent.name);
    this.setConfig();

    this.sollecito = new SollecitoVO();

    this.mapConfigAllegati = new Map();
    this.callConfig();
    this.loaded = true;
    setTimeout(() => {
      this.subscribers.form = this.creaDisposizioneForm.valueChanges.subscribe(
        (data) => {
          this.onChangeDataEmitter();
        }
      );
    });
  }

  onSelect(els: TableSoggettiOrdinanza[]) {
    this.idOrdinanzaVerbaleSoggetto = [];
    this.idOrdinanzaVerbaleSoggetto[0] = els[0].idSoggettoOrdinanza;
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.onChangeDataEmitter();
  }

  onChangeDataEmitter() {
    let obj: any = {};
    if (
      !this.creaDisposizioneForm.valid ||
      !this.idOrdinanzaVerbaleSoggetto ||
      this.idOrdinanzaVerbaleSoggetto.length < 1
    ) {
      obj.disabled = true;
    } else {
      obj.disabled = false;
      obj.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggetto;
      obj.tipoAllegatoSelezionatoId = this.tipoAllegatoSelezionato.id;
      this.mapMetadati();
      obj.metaDataModel = this.metaDataModel;
    }
    this.onChangeData.emit(obj);
  }

  mapMetadati() {
    let i: number;
    for (i = 0; i < this.tmpModel.length; i++) {
      this.metaDataModel[i].idField = this.tmpModel[i].idField;
      this.metaDataModel[i].fieldType = this.tmpModel[i].fieldType;
      if (this.typeAction.isNumeric(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].numberValue = this.tmpModel[i].value;
      else if (this.typeAction.isText(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].stringValue = this.tmpModel[i].value;
      else if (this.typeAction.isDate(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].dateValue = this.tmpModel[i].value;
      else if (this.typeAction.isCheckbox(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].booleanValue = this.tmpModel[i].value;
      else if (
        this.typeAction.isCombo(this.metaDataModel[i].fieldType) &&
        this.tmpModel[i].value
      )
        this.metaDataModel[i].numberValue = this.tmpModel[i].value.id;
      //salvo l'ID
      else if (this.typeAction.isDataOra(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].dateTimeValue = this.tmpModel[i].value;
    }
  }

  callConfig() {
    //recupero la lista di config
    this.subscribers.configAllegato = this.allegatoSharedService
      .getConfigAllegato()
      .map((config) => {
        this.mapConfigAllegati = this.remapConfig(
          //È IMPORTANTE che il campo ordine e il campo riga siano coerenti come numerazione crescente sul DB!!
          config.sort((el, el2) => {
            if (el.idTipo > el2.idTipo) return 1;
            if (el.idTipo < el2.idTipo) return -1;
            return el.ordine > el2.ordine ? 1 : -1;
          })
        );
        this.mostraMetadati();
      })
      .subscribe(
        (data) => {
          //
        },
        (err) => {
          this.logger.error("Errore nel recupero dei config degli allegati");
        }
      );
  }
  /**
   * Trasforma i dati di configurazione da BE del form di allegati in una mappa {key=id tipo,value=array di config}
   * Copiato da GRMED :)
   * @param data dati da trasformare
   */
  remapConfig(
    data: Array<ConfigAllegatoVO>
  ): Map<number, Array<Array<ConfigAllegatoVO>>> {
    let res: Map<number, Array<Array<ConfigAllegatoVO>>> = new Map();
    data.forEach((e, i) => {
      let c;
      if (!res.has(e.idTipo)) res.set(e.idTipo, []);
      c = res.get(e.idTipo);
      // Dividere i config in più array per elemento.riga identici:
      // 1) se inizio del ciclo sul tipo
      // 2) nuovo valore prop "riga" rispetto a elemento precedente
      if (c.length === 0 || e.riga !== (i < 1 ? 0 : data[i - 1].riga))
        c.push([]);
      c[c.length - 1].push(e);
    });
    return res;
  }

  check(index: number, type: FieldTypeVO) {
    if (!this.typeAction.isCheckbox(type)) return;
    this.tmpModel[index].value = !this.tmpModel[index].value;
  }

  onBlur($event, index: number, type: FieldTypeVO) {
    if (!(this.typeAction.isDate(type) || this.typeAction.isDataOra(type)))
      return;
    this.tmpModel[index].value = $event.srcElement.value;
  }

  manageDatePicker(i: number) {
    var str: string = "#datetimepicker_" + i.toString();
    if (this.typeAction.isDataOra(this.tmpModel[i].fieldType)) {
      if ($(str).length) {
        $(str).datetimepicker({
          format: "DD/MM/YYYY, HH:mm",
        });
      }
    } else if (this.typeAction.isDate(this.tmpModel[i].fieldType)) {
      if ($(str).length) {
        $(str).datetimepicker({
          format: "DD/MM/YYYY",
        });
      }
    }
  }

  isKeyPressed(code: number, type: FieldTypeVO): boolean {
    if (!this.typeAction.isNumeric(type)) return true;
    return this.numberUtilsSharedService.numberValid(code);
  }

  initModel() {
    this.tmpModel = new Array<Elem>();
    this.metaDataModel = new Array<AllegatoFieldVO>();
    this.comboModel = new Array<Array<SelectVO>>();
    this.comboLoaded = new Array<boolean>();
    this.tipoAllegatoModel = this.tipoAllegatoInput;
    this.senzaAllegati = this.allegati;
  }

  mostraMetadati() {
    // disposizioni del giudice
    this.tipoAllegatoSelezionato = this.tipoAllegatoInput.find(
      (item) => item.id == 14
    );
    this.loadedConfig = false;
    this.validMetadata = false;
    this.disableAll = false;
    this.initModel();
    let $idTipo: number = this.tipoAllegatoSelezionato.id;

    this.senzaAllegati = false;

    // se trovo un id, cerco il config corrispondente
    if ($idTipo != 10 && this.mapConfigAllegati.has($idTipo)) {
      this.configAllegatoSelezionato = this.mapConfigAllegati.get($idTipo);

      //inizializzo i model
      let index = 0;
      this.configAllegatoSelezionato.forEach((arr, i) => {
        arr.forEach((el, j) => {
          let elem: Elem = {
            value: null,
            fieldType: el.fieldType,
            idField: el.idField,
          };
          el.idModel = index;
          index++;
          this.tmpModel.push(elem);
          this.metaDataModel.push(new AllegatoFieldVO());
          if (this.typeAction.isCombo(el.fieldType)) {
            this.subscribers.getSelect = this.allegatoSharedService
              .getDecodificaSelectAllegato(el.idFonteElenco)
              .subscribe(
                (data) => {
                  this.comboModel[el.idModel] = data;
                  this.comboLoaded[el.idModel] = true;
                  this.logger.info(
                    "Caricata lista per il campo numero " + el.idModel
                  );
                },
                (err) => {
                  this.logger.error(
                    "Errore nel recupero dell'elenco di " + el.idFonteElenco
                  );
                }
              );
          }
        });
      });

      this.loadedConfig = true;
    }
  }

  loadSollecitiEsistentiCopiaPerRichiestaBollettini() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0])
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
          this.modificaVediSollecito(
            this.listaSolleciti.find(
              (soll) => soll.idSollecito == this.sollecito.idSollecito
            )
          );
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

  generaMessaggio() {
    this.subMessages = new Array<string>();

    this.subMessages.push("Si intende eliminare il sollecito selezionato?");
    this.subMessages.push("Data Creazione: " + this.sollecito.dataScadenza);
    this.subMessages.push(
      "Importo da sollecitare: " + this.sollecito.importoSollecitato
    );
  }

  creaSollecito() {
    /*this.loaded = false;
        //JIRA - Gestione Notifica
        //----------------------
        let salvaSollecitoRequest: SalvaSollecitoRequest = new SalvaSollecitoRequest();
        salvaSollecitoRequest.sollecito = this.sollecito;
        salvaSollecitoRequest.notifica = this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject();
        //------------------------
        //this.subscribers.salvataggio = this.riscossioneService.salvaSollecito(this.sollecito).subscribe(data => {
        this.subscribers.salvataggio = this.riscossioneService.salvaSollecito(salvaSollecitoRequest).subscribe(data => {
            this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_TEMPLATE + data);
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il salvataggio del sollecito");
            this.loaded = true;
        });*/
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
  }

  getData() {
    return {
      sollecito: this.sollecito,
      notifica: this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject(),
    };
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

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
    let i: number;
    if (this.tmpModel) {
      for (i = 0; i < this.tmpModel.length; i++) this.manageDatePicker(i);
    }
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
    this.logger.destroy(PregressoDisposizioneGiudiceInsComponent.name);
  }
  setConfig() {
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

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }
}
