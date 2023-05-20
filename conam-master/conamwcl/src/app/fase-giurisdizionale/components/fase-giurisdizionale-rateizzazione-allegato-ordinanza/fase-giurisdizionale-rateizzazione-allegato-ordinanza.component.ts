import {
  Component,
  OnInit,
  OnDestroy,
  ElementRef,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { SelectVO, TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { FaseGiurisdizionaleUtilService } from "../../services/fase-giurisdizionale-util.serivice";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { FileUploader, FileItem } from "ng2-file-upload";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { ConfigAllegatoVO } from "../../../commons/vo/verbale/config-allegato-vo";
import { SalvaAllegatoMultipleRequest } from "../../../commons/request/salva-allegato-multiple-request";
import { AllegatoMultipleFieldVO } from "../../../commons/vo/verbale/allegato-multiple-field-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { DocumentoProtocollatoVO } from "../../../commons/vo/verbale/documento-protocollato-vo";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { ConditionalExpr } from "@angular/compiler";

declare var $: any;
const URL = "https://evening-anchorage-3159.herokuapp.com/api/";

interface Upload {
  dtConfig: Config;
  uploader: ElementRef;
  actions: any;
}
@Component({
  selector: "fase-giurisdizionale-rateizzazione-allegato-ordinanza",
  templateUrl:
    "./fase-giurisdizionale-rateizzazione-allegato-ordinanza.component.html",
})
export class FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  public subscribers: any = {};

  public config: Config;
  public loaded: boolean;
  public loadedConfig: boolean;
  public loadedCategoriaAllegato: boolean;

  public tipoAllegatoModel: Array<TipoAllegatoVO>;

  public idOrdinanzaVerbaleSoggetto: Array<number> = [];
  public idOrdinanza: number;
  public idVerbale : number;
  public allegatiMultipli: boolean = true;
  public tipoCaricamento: SelectVO[];
  public validMetadata: boolean;
  public typeUpload: SelectVO = { id: 1, denominazione: "Da dispositivo" };
  public typeUploads: SelectVO[] = [
    { id: 1, denominazione: "Da dispositivo" },
    { id: 2, denominazione: "Recupera documento protocollato da DoQui ACTA" },
  ];

  public typeUploadTwo: SelectVO = { id: 1, denominazione: "Da dispositivo" };
  public typeUploadsTwo: SelectVO[] = [
    { id: 1, denominazione: "Da dispositivo" },
    { id: 2, denominazione: "Recupera documento protocollato da DoQui ACTA" },
  ];
  public tipoRicercaView: boolean = true;
  public tipoRicercaViewAllegati: boolean = true;
  public file: File;
  public documentoProtocollato: DocumentoProtocollatoVO;
  public documenti: Array<AllegatoVO> = new Array<AllegatoVO>();
  public allegatoMasterPresente: boolean = false;
  public fileAllegatoMaster: SalvaAllegatoRequest;
  public fileMaster: boolean = false;
  public allegatiProtocollati: DocumentoProtocollatoVO[] = [];
  public numProtocolloDocumentoProtocollatoAllegato: string = '';
  //message
  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;

  public eliminaM: boolean = true;
  public confermaM: boolean = true;
  public resetSelection: boolean = false;

  //metadati
  public flagAllegatoMaster: boolean = false;
  public maxSize: number = 5242880;
  public configAllegatoSelezionato: Array<Array<ConfigAllegatoVO>>;
  public nuovoAllegato: SalvaAllegatoRequest;
  public nomeAllegatoTmp: string;
  public sizeAlert: boolean = false;
  public typeAlert: boolean = false;
  public tipoAllegatoSelezionato: TipoAllegatoVO;
  public nuovoAllegatoMultiple: SalvaAllegatoMultipleRequest;
  allowedFilesType = ["pdf", "tiff", "jpg", "jpeg", "p7m"];
  allowedFilesTypeMessage: string;

  //Messaggio conferma eliminazione
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  public idMaster: number;
  public idAllegato: number;


  constructor(
    private logger: LoggerService,
    private router: Router,
    private sharedOrdinanzaService: SharedOrdinanzaService,
    private faseGiurisdizionaleUtilService: FaseGiurisdizionaleUtilService,
    private sharedVerbaleService: SharedVerbaleService,
    private fascicoloService: FascicoloService,
    private utilSubscribersService: UtilSubscribersService,
    private configSharedService: ConfigSharedService,
  ) {}

  @ViewChild("fileInput") set uploader(uploader: ElementRef) {
    this.upload.uploader = uploader;
  }

  ngOnInit(): void {
    this.logger.init(
      FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent.name
    );
    this.loaded = false;
    this.idMaster = 26;
    this.idAllegato = 38;
    this.getIdOrdinanzaVerbaleSoggetto();

    if (this.idOrdinanzaVerbaleSoggetto) {
      // setto il riferimento per la ricerca documento protocollato
      this.fascicoloService.ref = this.router.url;
      this.loadTipoAllegato();
    }
    this.getVerbaleByIdOrdinanza();
    this.sharedOrdinanzaService
      .getAllegatiByIdOrdinanzaVerbaleSoggetto(this.idOrdinanzaVerbaleSoggetto)
      .subscribe((data) => {
        for (let i in data) {
          if (data[i].tipo.id === 26) {
            this.documenti.push(data[i]);
            this.fileMaster = true;
          }
        }
        this.documenti.forEach((all) => {
          all.theUrl = new MyUrl(all.nome, null);
        });
        this.loaded = true;
      });
    this.config = this.configSharedService.configDocumentiOrdinanza;
  }

  getVerbaleByIdOrdinanza() {
    this.subscribers.callAzioneVerbale = this.sharedVerbaleService.getVerbaleByIdOrdinanza(this.idOrdinanza).subscribe(data => {
        this.idVerbale = data.id;
    });
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliOrdinanzaSoggettoRequest();
    request.id = this.idOrdinanzaVerbaleSoggetto;
    request.tipoDocumento = Constants.ISTANZA_RATEIZZAZIONE;
    this.subscribers.tipoAllegato = this.sharedOrdinanzaService
      .getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(request)
      .subscribe(
        (data) => {
          this.tipoAllegatoModel = data;
          this.loadedCategoriaAllegato = true;

          // setto il riferimento per la ricerca documento protocollato
          this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
          this.loadedCategoriaAllegato = true;
        }
      );
  }

  ricercaProtocollo(ricerca: RicercaProtocolloRequest) {
    ricerca.idVerbale = this.idOrdinanza;
    this.loaded = false;
    this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
      (data) => {
        this.loaded = true;

        let tipiAllegatoDuplicabili = [];
        if (!data.documentoProtocollatoVOList) {
          data.documentoProtocollatoVOList = [];
        } else if (data.documentoProtocollatoVOList[0]) {
          tipiAllegatoDuplicabili =
            data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
        }
        this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;

        // setto il numero di protocollo per la ricerca documento protocollato
        this.fascicoloService.searchFormRicercaProtocol =
          ricerca.numeroProtocollo;
        // setto i dati per la ricerca documento protocollato
        this.fascicoloService.dataRicercaProtocollo =
          data.documentoProtocollatoVOList;
        this.fascicoloService.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggetto;

        this.fascicoloService.successPage =
          Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_DETTAGLIO_ORDINANZA +
          this.idOrdinanza;
        if (data.messaggio) {
          this.fascicoloService.message = data.messaggio;
        }else{
          this.fascicoloService.message = null;
        }
        this.router.navigateByUrl(
          Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idOrdinanza
        );
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err.type, err.message);
        }
        this.loaded = true;
        this.logger.error("Errore nel recupero dei documenti protocollati");
      }
    );
  }

  onNewFileDevice(event: SalvaAllegatoRequest) {
    this.file = event.file;
    this.fileAllegatoMaster = event;
  }

  getIdOrdinanzaVerbaleSoggetto() {
    this.idOrdinanzaVerbaleSoggetto = this.faseGiurisdizionaleUtilService.getIdOrdinanzaVerbaleSoggetto();
    this.idOrdinanza = this.faseGiurisdizionaleUtilService.getIdOrdinanza();
    if (!this.idOrdinanzaVerbaleSoggetto)
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_RICERCA_ORDINANZA
      );
  }

  manageMessage(type: string, message: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
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

  /* parte per caricare i file  */
  typeUploadChange() {
    this.file = null;
    this.documentoProtocollato = null;
  }
  aggiungiM() {
    this.confermaM = true;
    this.eliminaM = true;
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
              this.nuovoAllegato.idTipoAllegato = this.tipoAllegatoSelezionato.id;

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
                if (!this.allegatiMultipli) {
                  this.resetMessageTop();
                  this.nomeAllegatoTmp = this.nuovoAllegato.filename;
                } else {
                  let allegato: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
                  allegato.file = files[key];
                  allegato.filename = this.nuovoAllegato.filename;
                  allegato.idTipoAllegato = this.nuovoAllegato.idTipoAllegato;
                  allegato.master = true;

                  this.nuovoAllegatoMultiple.allegati = new Array<AllegatoMultipleFieldVO>();
                  this.nuovoAllegatoMultiple.allegati.push(allegato);
                  this.nomeAllegatoTmp = allegato.filename;

                  if (!this.flagAllegatoMaster) {
                    this.resetMessageTop();
                  }
                }
              }
            }
          }
        }
        this.upload.uploader.nativeElement.value = "";
      },
    },
  };
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
  elimina(file: FileItem) {
    file.remove();
    if (!this.confermaM) this.eliminaM = false;
  }

  eliminaTutti(files: FileUploader) {
    files.clearQueue();
    if (!this.confermaM) this.eliminaM = false;
  }
  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }
  /* sul seleziona della tabella passo alla componente padre i documenti selezionati */
  onSelectedDocumentoProtocollato(doc: DocumentoProtocollatoVO) {
    if (doc) {
      if (typeof doc.filename != "string") {
        doc.filename = doc.filename.nomeFile;
      }
      this.documentoProtocollato = doc;
    }
  }

  
  onSelectedDocumentoProtocollatoAllegato(docs: DocumentoProtocollatoVO[]) {
    let toRemove = []; 

    if(docs && docs.length>0){

      this.allegatiProtocollati.forEach(element => {
        const elementDocs = docs.find(x => x.documentoUUID === element.documentoUUID);
        const index = docs.indexOf(elementDocs);

        
        if(index>-1){
          docs.splice(index, 1);
        }

        if(
          (element.numProtocollo===this.numProtocolloDocumentoProtocollatoAllegato ||
          element.numProtocolloMaster===this.numProtocolloDocumentoProtocollatoAllegato) 
          && index==-1){
          toRemove.push(element);
        }
      });
    }else{
      this.allegatiProtocollati.forEach(element => {
        if(element.numProtocollo===this.numProtocolloDocumentoProtocollatoAllegato ||
          element.numProtocolloMaster===this.numProtocolloDocumentoProtocollatoAllegato) {
          toRemove.push(element);
        }
      });  
    }
      
    this.allegatiProtocollati = this.allegatiProtocollati.filter(item => !toRemove.includes(item));

    // aggiungo docs residui
    if(docs && docs.length>0){
      this.allegatiProtocollati = this.allegatiProtocollati.concat(docs);
    }  
  }
  
  onSearchDocumentoProtocollatoAllegato(numProtocollo:string){
    this.numProtocolloDocumentoProtocollatoAllegato = numProtocollo;
  }

  rimuoviAllegatoProtocollato(el:DocumentoProtocollatoVO){
    const index = this.allegatiProtocollati.indexOf(el);
    if(index>-1){
      const elementDocs = this.allegatiProtocollati.find(x => x.documentoUUID === el.documentoUUID);
      const toRemove = [elementDocs];
      this.allegatiProtocollati = this.allegatiProtocollati.filter(item => !toRemove.includes(item));
    }
  }

  eliminaFileProtoccolatiDialog(file: DocumentoProtocollatoVO) {
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
      if (file != null) this.rimuoviAllegatoProtocollato(file);
      else this.eliminaTuttiAllegatoproticollati();
    });

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe((data) => {
      this.subMessages = new Array<string>();
    });
  }

  eliminaTuttiAllegatoproticollati() {
    this.allegatiProtocollati = [];
  }

  isDisabledConfermaAllegato(): boolean {
    return (
      !this.tipoAllegatoSelezionato ||
      (this.loadedConfig && !this.validMetadata)
    );
  }
  clearAllegatiMultipli(event: boolean) {
    if (!event) {
      this.eliminaTuttiAllegatoproticollati();
      this.eliminaTutti(this.uploaderM);
    }
  }
  salvaAllegato() {
    let salvaAllegatoMultipleRequest: SalvaAllegatoMultipleRequest = new SalvaAllegatoMultipleRequest();

    salvaAllegatoMultipleRequest.idOrdinanzaVerbaleSoggettoList = this.idOrdinanzaVerbaleSoggetto;
    salvaAllegatoMultipleRequest.allegati = [];

    if (this.documentoProtocollato) {
      let master: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
      master.master = true;
      master.documentoProtocollato = this.documentoProtocollato;
      master.idTipoAllegato = this.idMaster;
      salvaAllegatoMultipleRequest.allegati.push(master);
    }

    if (this.fileAllegatoMaster) {
      let master: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
      master.master = true;
      master.file = this.fileAllegatoMaster.file;
      master.filename = this.fileAllegatoMaster.filename;
      master.idTipoAllegato = this.idMaster;
      salvaAllegatoMultipleRequest.allegati.push(master);
    }

    if (this.allegatiProtocollati) {
      for (let item of this.allegatiProtocollati) {
        let master: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
        master.master = false;
        master.documentoProtocollato = item;
        master.idTipoAllegato = this.idAllegato;
        salvaAllegatoMultipleRequest.allegati.push(master);
      }
    }
    if (this.uploaderM.queue) {
      for (let item of this.uploaderM.queue) {
        let master: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
        master.master = false;
        master.file = item._file;
        master.filename = item.file.name;
        master.idTipoAllegato = this.idAllegato;
        salvaAllegatoMultipleRequest.allegati.push(master);
      }
    }

    //mando il file al Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.sharedVerbaleService
      .salvaAllegatoOrdinanzaMaster(salvaAllegatoMultipleRequest)
      .subscribe(
        (data) => {
          this.loadTipoAllegato();
          let azione: string;
          if (!this.allegatiMultipli) azione = "caricato";
          else azione = "caricati";
          this.loaded = true;
          this.router.navigate([Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_DETTAGLIO_ORDINANZA , this.idOrdinanza, { action: 'caricato' }], { replaceUrl: true });
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.error("Errore nel salvataggio");
          this.loaded = true;
        }
      );
  }
  
  back(): void {
    this.router.navigate([Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_DETTAGLIO_ORDINANZA,this.idOrdinanza]);
  }

  ngOnDestroy(): void {
    this.logger.destroy(
      FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent.name
    );
  }
}
