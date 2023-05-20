import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  ElementRef,
  ViewChild,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import {
  SelectVO,
  FieldTypeVO,
  TipoAllegatoVO,
} from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";
import { ConfigAllegatoVO } from "../../../commons/vo/verbale/config-allegato-vo";
import { AllegatoFieldVO } from "../../../commons/vo/verbale/allegato-field-vo";
import { AllegatoSharedService } from "../../service/allegato-shared.service";
import { Config } from "../../module/datatable/classes/config";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { NumberUtilsSharedService } from "../../service/number-utils-shared.service";
import { FileUploader, FileItem } from "ng2-file-upload";
import { SalvaAllegatoMultipleRequest } from "../../../commons/request/salva-allegato-multiple-request";
import { AllegatoMultipleFieldVO } from "../../../commons/vo/verbale/allegato-multiple-field-vo";
import { timer } from "rxjs/observable/timer";
import { SharedDialogComponent } from "../shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedAlertComponent } from "../shared-alert/shared-alert.component";

declare var $: any;
const URL = "https://evening-anchorage-3159.herokuapp.com/api/";

interface Elem {
  value: any;
  fieldType: FieldTypeVO;
  idField: number;
}

interface Upload {
  dtConfig: Config;
  uploader: ElementRef;
  actions: any;
}

@Component({
  selector: "shared-allegato",
  templateUrl: "./shared-allegato.component.html",
  styleUrls: ["./shared-allegato.component.scss"],
})
export class SharedAllegatoComponent implements OnInit, OnDestroy, OnChanges {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  @Output()
  onNewFile = new EventEmitter<any>();

  public subscribers: any = {};

  public mapConfigAllegati: Map<number, Array<Array<ConfigAllegatoVO>>>;

  public loadedConfig: boolean;
  public comboLoaded: Array<boolean>;
  public validMetadata: boolean;
  public disableAll: boolean;

  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public tipoAllegatoSelezionato: TipoAllegatoVO;
  public configAllegatoSelezionato: Array<Array<ConfigAllegatoVO>>;
  public nuovoAllegato: SalvaAllegatoRequest;
  public nomeAllegatoTmp: string;

  public comboModel: Array<Array<SelectVO>>;
  public metaDataModel: Array<AllegatoFieldVO>;
  public tmpModel: Array<Elem>; //un array di elementi senza tipo, da rimappare nell'array effettivo alla fine
  public maxSize: number = 5242880;
  public sizeAlert: boolean = false;
  public typeAlert: boolean = false;

  public eliminaM: boolean = true;
  public confermaM: boolean = true;
  public allegatoM: boolean = false;
  public flagAllegatoMaster: boolean = false;
  public nuovoAllegatoMultiple: SalvaAllegatoMultipleRequest;

  allowedFilesType = ["pdf", "tiff", "jpg", "jpeg", "p7m"];
  allowedFilesTypeMessage: string;

  constructor(
    private logger: LoggerService,
    private allegatoSharedService: AllegatoSharedService,
    private numberUtilsSharedService: NumberUtilsSharedService,
    private utilSubscribersService: UtilSubscribersService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedAllegatoComponent.name);

    this.initModel();
  }

  ngOnChanges() {
    this.logger.change(SharedAllegatoComponent.name);
    this.initModel();
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

  initModel() {
    this.tmpModel = new Array<Elem>();
    this.metaDataModel = new Array<AllegatoFieldVO>();
    this.comboModel = new Array<Array<SelectVO>>();
    this.comboLoaded = new Array<boolean>();
    this.nuovoAllegato = new SalvaAllegatoRequest();
    this.nuovoAllegatoMultiple = new SalvaAllegatoMultipleRequest();
  }

  // Gestione allegati
  public uploaderM: FileUploader = new FileUploader({ url: URL });
  public upload: Upload = {
    dtConfig: null,
    uploader: null,
    actions: {
      addFile: () => {
        this.logger.info("=> Add file");
        this.upload.uploader.nativeElement.click();
        this.flagAllegatoMaster = false;
      },
      addFileMaster: () => {
        this.logger.info("=> Add file Master");
        this.upload.uploader.nativeElement.click();
        this.flagAllegatoMaster = true;
      },
      addFileAllegati: () => {
        this.logger.info("=> Add file Allegati");
        this.uploaderM.autoUpload.click();
        this.flagAllegatoMaster = true;
      },
      onFilesAdded: () => {
        const files: { [key: string]: File } = this.upload.uploader
          .nativeElement.files;
        for (let key in files) {
          if (!isNaN(parseInt(key))) {
            if (files[key].size > this.maxSize) {
              this.sizeAlert = true;
              this.logger.info("File size: " + files[key].size);
            } else {
              this.sizeAlert = false;
              this.nuovoAllegato.file = files[key];
              this.nuovoAllegato.filename = this.nuovoAllegato.file.name;
              let type: string = "";
              let array: string[] = this.nuovoAllegato.filename.split(".");
              if (array.length > 1) {
                type = array[array.length - 1];
              }
              this.nuovoAllegato.filename = this.nuovoAllegato.filename.replace(
                type,
                type.toLowerCase()
              );

              //spezza il nome del file da visualizzare se è troppo lungo e senza spazi. Non copre tutti i casi, ma dovrebbe bastare
              if (
                this.nuovoAllegato.filename.length > 30 &&
                this.nuovoAllegato.filename.split(" ").length == 1
              ) {
                if (this.nuovoAllegato.filename.length <= 60) {
                  this.nomeAllegatoTmp =
                    this.nuovoAllegato.filename.slice(0, 30) +
                    " " +
                    this.nuovoAllegato.filename.slice(30);
                } else
                  this.nomeAllegatoTmp =
                    this.nuovoAllegato.filename.slice(0, 30) +
                    " " +
                    this.nuovoAllegato.filename.slice(30, 60) +
                    " " +
                    this.nuovoAllegato.filename.slice(60);
              }

              if (
                this.allowedFilesType.filter((x) => x == type.toLowerCase())
                  .length == 0
              ) {
                this.typeAlert = true;
                this.nuovoAllegato.filename = null;
                this.allowedFilesTypeMessage =
                  "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
                this.nuovoAllegato = new SalvaAllegatoRequest();
                this.nomeAllegatoTmp = null;
              } else {
                this.typeAlert = false;
                this.resetMessageTop();
                this.nomeAllegatoTmp = this.nuovoAllegato.filename;
                this.onNewFile.emit(this.nuovoAllegato);
              }
            }
           
          }
        }
       
        this.upload.uploader.nativeElement.value = "";
      },
    },
  };

  isConfermaDisabled() {
    return this.nomeAllegatoTmp == null;
  }

  
 
  
 

  confermaAllegato(files: FileItem[]) {
    for (let i = 0; i < files.length; i++) {
      let key: File = files[i]._file;
      if (key.size > this.maxSize) {
        this.sizeAlert = true;
        this.logger.info("File size: " + key.size);
        break;
      } else {
        let allegato: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
        this.sizeAlert = false;
        allegato.file = key;
        allegato.filename = allegato.file.name;
        //allegato.idTipoAllegato =  28;

        //spezza il nome del file da visualizzare se è troppo lungo e senza spazi. Non copre tutti i casi, ma dovrebbe bastare
        if (
          allegato.filename.length > 30 &&
          allegato.filename.split(" ").length == 1
        ) {
          if (this.nuovoAllegato.filename.length <= 60) {
            this.nomeAllegatoTmp =
              allegato.filename.slice(0, 30) +
              " " +
              allegato.filename.slice(30);
          } else
            this.nomeAllegatoTmp =
              allegato.filename.slice(0, 30) +
              " " +
              allegato.filename.slice(30, 60) +
              " " +
              allegato.filename.slice(60);
        }
        let type: string = "";
        let array: string[] = allegato.filename.split(".");
        if (array.length > 1) {
          type = array[array.length - 1];
        }
        if (
          this.allowedFilesType.filter((x) => x == type.toLowerCase()).length ==
          0
        ) {
          this.typeAlert = true;
          this.confermaM = true;
          this.allowedFilesTypeMessage =
            "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
          break;
        } else {
          this.typeAlert = false;
          this.confermaM = false;
          this.eliminaM = true;
          this.manageMessageTop(
            " I documenti caricati possono essere archiviati su Doqui ACTA. Per proseguire cliccare sul tasto Allega",
            "SUCCESS",
            true
          );
          this.nuovoAllegatoMultiple.allegati.push(allegato);
        }
      }
    }
  }

  aggiungiM() {
    this.confermaM = true;
    this.eliminaM = true;
  }

  elimina(file: FileItem) {
    file.remove();
    if (!this.confermaM) this.eliminaM = false;
  }

  eliminaTutti(files: FileUploader) {
    files.clearQueue();
    if (!this.confermaM) this.eliminaM = false;
  }

  //Messaggio conferma eliminazione
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  generaMessaggio(msn: string) {
    this.subMessages = new Array<string>();

    let incipit: string = msn;
    this.subMessages.push(incipit);
  }

  eliminaFile(file: FileItem, files: FileUploader) {
    if (file != null)
      this.generaMessaggio("Sicuro di voler eliminare il file?");
    else this.generaMessaggio("Sicuro di voler eliminare tutti i file?");

    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    //mostro un messaggio
    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe((data) => {
      if (file != null) this.elimina(file);
      else this.eliminaTutti(files);
    });

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe((data) => {
      this.subMessages = new Array<string>();
    });
  }

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public typeMessageTopM: String;
  public messageTopM: String;

  manageMessageTop(message: string, type: string, multipli: boolean) {
    if (multipli) {
      this.typeMessageTopM = type;
      this.messageTopM = message;
      this.timerShowMessageTop();
    } else {
      this.typeMessageTop = type;
      this.messageTop = message;
      this.timerShowMessageTop();
    }
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    const source = timer(160, 160).take(31);
    this.subscribers.timer = source.subscribe((val) => {
      if (val == 30) this.resetMessageTop();
    });
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
  }

  @ViewChild("file") set uploader(uploader: ElementRef) {
    this.upload.uploader = uploader;
  }

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

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
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

  ngAfterViewChecked() {
    let i: number;
    for (i = 0; i < this.tmpModel.length; i++) this.manageDatePicker(i);
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedAllegatoComponent.name);
  }
}
