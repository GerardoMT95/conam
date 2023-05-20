import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { saveAs } from "file-saver";
import { LoggerService } from "../../../core/services/logger/logger.service";
import {
  SelectVO,
} from "../../../commons/vo/select-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { ScrittoDifensivoVO } from "../../../commons/vo/verbale/scritto-difensivo-vo";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { SalvaScrittoDifensivoRequest } from "../../../commons/request/verbale/salva-scritto-difensivo-request";
import { VerbaleService } from "../../services/verbale.service";
import { DocumentoProtocollatoVO } from "../../../commons/vo/verbale/documento-protocollato-vo";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { RiferimentiNormativiVO } from "../../../commons/vo/verbale/riferimenti-normativi-vo";
import { MessageVO } from "../../../commons/vo/messageVO";


@Component({
  selector: "verbale-scritto-difensivo",
  templateUrl: "./verbale-scritto-difensivo.component.html",
})
export class VerbaleScrittoDifensivoComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;
  //Messaggio conferma eliminazione
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  //Messaggio allegati
  public subLinks: Array<any>;


  public subscribers: any = {};

  scrollEnable: boolean;

  public loaded: boolean;
  public modeInsert: boolean = true;
  public modeEdit: boolean = false;


  public idScrittoDifensivo: number;
  public scrittoDifensivoCurrent: ScrittoDifensivoVO;
  public scrittoDifensivoSaved: ScrittoDifensivoVO;
  private intervalIdS: number = 0;
  private intervalIdW: number = 0;

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  //warning meta pagina
  public typeWarning: string;
  public showWarning: boolean;
  public messageWarning: string;

  public validFormValidSoggetto: boolean = false;
  public validFormValidDatiVerbale: boolean = false;

  public typeUpload: SelectVO = { id: 1, denominazione: 'Da dispositivo' };
  public typeUploads: SelectVO[] = [
    { id: 1, denominazione: 'Da dispositivo' },
    { id: 2, denominazione: 'Recupera documento protocollato da DoQui ACTA' }
  ];

  public file: File;
  public documentoProtocollato: DocumentoProtocollatoVO;

  constructor(
    private logger: LoggerService,
    public verbaleService: VerbaleService,
    private allegatoSharedService: AllegatoSharedService,
    private utilSubscribersService: UtilSubscribersService,
    private router: Router,
    private activatedRoute: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.logger.init(VerbaleScrittoDifensivoComponent.name);

    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      let verbString = params["id"];
      if (!verbString || isNaN(+verbString)) this.idScrittoDifensivo = null;
      else this.idScrittoDifensivo = +verbString;
      if (!this.idScrittoDifensivo) {
        this.scrittoDifensivoCurrent = new ScrittoDifensivoVO();
        this.loaded = true;
      } else {
        this.loadScrittoDifensivo();
      }

      if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
        this.manageMessageSuccess("Scritto difensivo aggiornato con successo", 'SUCCESS');

    });

  }

  loadScrittoDifensivo() {
    this.subscribers.salvaScrittoDifensivo = this.verbaleService
      .getScrittoDifensivoById(this.idScrittoDifensivo)
      .subscribe(
        (data) => {
          this.scrittoDifensivoCurrent = <ScrittoDifensivoVO>data;
          // il BE potrebbe averlo vuoto
          if (!this.scrittoDifensivoCurrent.riferimentoNormativo){
            this.scrittoDifensivoCurrent.riferimentoNormativo = new RiferimentiNormativiVO();
          }
          this.scrittoDifensivoSaved = {...this.scrittoDifensivoCurrent};
          this.modeInsert = false;
          this.loaded = true;



          this.validFormValidSoggetto = true;

          this.validFormValidDatiVerbale = true;



        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.logger.error("Errore nel salvataggio dello scritto difensivo");
          this.scrollEnable = true;
        }
      );
  }

  viewMode() {
    this.scrittoDifensivoCurrent = {...this.scrittoDifensivoSaved};
    this.modeEdit = false;
  }

  updateMode(){
    this.modeEdit = true;
  }

  /* parte per caricare i file  */
  typeUploadChange() {
    this.file = null;
    this.documentoProtocollato = null;
  }

  onNewFileDevice(event: SalvaAllegatoRequest) {
    this.scrittoDifensivoCurrent.nomeFile = event.filename;
    this.file = event.file;
  }

  /* sul seleziona della tabella passo alla componente padre i documenti selezionati */
  onSelectedDocumentoProtocollato(doc: DocumentoProtocollatoVO) {
    if (doc){
      if (typeof doc.filename != "string") {
        doc.filename = doc.filename.nomeFile;
      }
      this.documentoProtocollato = doc;
      this.scrittoDifensivoCurrent.nomeFile =  doc.filename;
    }else{
      this.documentoProtocollato = null;
      this.scrittoDifensivoCurrent.nomeFile = null;
    }
  }

  manageMessageSuccess(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  manageMessageWarning(message: string) {
    this.typeWarning = "WARNING";
    this.messageWarning = message;
    this.timerShowMessageWarning();
  }
  
  manageMessage(err: ExceptionVO|MessageVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  timerShowMessageWarning() {
    this.showWarning = true;
    let seconds: number = 50; //this.configService.getTimeoutMessagge();
    this.intervalIdW = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageWarning();
      }
    }, 1000);
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  resetMessageWarning() {
    this.showWarning = false;
    this.typeWarning = null;
    this.messageWarning = null;
    clearInterval(this.intervalIdW);
  }

  formValidSoggetto(valid: boolean) {
    this.validFormValidSoggetto = valid;
  }

  formValidDatiVerbale(valid: boolean) {
    this.validFormValidDatiVerbale = valid;
  }

  salvaScrittoDifensivo() {
    let salvaScrittoDifensivoRequest = new SalvaScrittoDifensivoRequest();
    if (this.file) {
      salvaScrittoDifensivoRequest.file = this.file;
    }
    if (this.documentoProtocollato) {
      salvaScrittoDifensivoRequest.documentoProtocollato = this.documentoProtocollato;
    }
    this.scrittoDifensivoCurrent.ambito = this.scrittoDifensivoCurrent.riferimentoNormativo.ambito;
    salvaScrittoDifensivoRequest.scrittoDifensivo = this.scrittoDifensivoCurrent;
    this._confermaSalvaScrittoDifensivo(salvaScrittoDifensivoRequest);
  }

  aggiornaScrittoDifensivo() {
    let salvaScrittoDifensivoRequest = new SalvaScrittoDifensivoRequest();
    this.scrittoDifensivoCurrent.ambito = this.scrittoDifensivoCurrent.riferimentoNormativo.ambito;
    salvaScrittoDifensivoRequest.scrittoDifensivo = this.scrittoDifensivoCurrent;
    this.loaded = false;
    this.subscribers.salvaScrittoDifensivo = this.verbaleService
      .salvaScrittoDifensivo(salvaScrittoDifensivoRequest)
      .subscribe(
        (data) => {
          this.loaded = true;
          if (data.errorMsg) {
            this.manageMessage(data.errorMsg);
          }else{
            // se ci sono problemi di consistenza dei dati richiamiamo la loadScrittoDifensivo
            this.scrittoDifensivoSaved = {...this.scrittoDifensivoCurrent};
            // TODO messaggio di successo
            this.manageMessageSuccess(
              "Scritto difensivo aggiornato con successo",
              "SUCCESS"
            );

            this.viewMode();
          }           
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.logger.error("Errore nel salvataggio dello scritto difensivo");
          this.scrollEnable = true;
        }
      );
  }

  _confermaSalvaScrittoDifensivo(
    salvaScrittoDifensivoRequest: SalvaScrittoDifensivoRequest
  ) {

    /* genera messaggio */
    this.subMessages = new Array<string>();

    let incipit = 'Sicuro di voler salvare il documento ' + this.scrittoDifensivoCurrent.nomeFile + ' come scritto difensivo privo di Fascicolo?';
    this.subMessages.push(incipit);


    this.buttonAnnullaText = "Indietro";
    this.buttonConfirmText = "Si";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.salvaScrittoDifensivo = this.verbaleService
          .salvaScrittoDifensivo(salvaScrittoDifensivoRequest)
          .subscribe(
            (data) => {
              this.loaded = true;
              if (data.errorMsg) {
                this.manageMessage(data.errorMsg);
              }
              // routing
              if (data.idScrittoDifensivo && this.modeInsert) {
                this.router.navigate(
                  [Routing.VERBALE_SCRITTO_DIFENSIVO + data.idScrittoDifensivo, { action: 'caricato' }],
                  { replaceUrl: true }
                );
              }
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.loaded = true;
                this.manageMessage(err);
              }
              this.logger.error("Errore nel salvataggio dello scritto difensivo");
              this.scrollEnable = true;
            }
          );
      },
      (err) => {
        this.loaded = true;
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subMessages = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  isDisabledScrittoDifensivoCreate(): boolean {
    return !this.validFormValidSoggetto || !this.validFormValidDatiVerbale || (this.file == null && this.documentoProtocollato == null);
  }

  isDisabledScrittoDifensivoUpdate(): boolean {
    return !this.validFormValidSoggetto || !this.validFormValidDatiVerbale;
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  /* parte per scaricare allegati */
  getAllegato(el: AllegatoVO) {
    if (!el.id) return;
    this.logger.info("Richiesto download dell'allegato " + el.id);
    if (el.nome.toLowerCase().startsWith("documento multiplo")) {
      this.allegatoSharedService
        .getDocFisiciByIdAllegato(el.id)
        .subscribe((data) => {
          this.openAllegatoMultiplo(data);
        });
    } else {
      this.subscribers.allegatoDowload = this.allegatoSharedService
        .getAllegato(el.id)
        .subscribe((res) => {
          saveAs(res, el.nome || "Unknown");
        });
    }
  }

  openAllegatoMultiplo(els: any[]) {
    this.subLinks = new Array<any>();
    this.subMessages = new Array<string>();

    els.forEach((item) => {
      this.subLinks.push({ ...item, label: item.nomeFile });
    });

  
    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  openAllegatoFisico(el: any) {
    const myFilename = el.nomeFile;
    this.allegatoSharedService
      .getAllegatoByObjectIdDocumentoFisico(el.objectIdDocumentoFisico)
      .subscribe((data) => {
        saveAs(data, myFilename || "Unknown");
      });
  }


  ngAfterViewChecked() {

    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(VerbaleScrittoDifensivoComponent.name);
  }
}
