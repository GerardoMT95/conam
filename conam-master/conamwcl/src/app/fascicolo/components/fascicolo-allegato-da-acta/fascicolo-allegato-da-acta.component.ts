import { Component, OnInit, OnDestroy, ViewChild, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO, SelectVO } from "../../../commons/vo/select-vo";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { RiepilogoAllegatoVO } from "../../../commons/vo/verbale/riepilogo-allegato-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { DatatableService } from "../../../shared/module/datatable/services/datatable-service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { DOCUMENT } from "@angular/platform-browser";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { DocumentoProtocollatoVO } from "../../../commons/vo/verbale/documento-protocollato-vo";
import {
  FascicoloService,
  NumberDocument,
} from "../../services/fascicolo.service";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { SalvaAllegatoProtocollatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-protocollato-verbale-request";
import { SalvaAllegatoProtocollatoVerbaleRequestAllegato } from "../../../commons/request/salva-allegato-protocollato-request-common";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-soggetto-request";
import { SalvaAllegatoProtocollatoOrdinanzaRequest } from "../../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-request";
import { saveAs } from "file-saver";
import { winLoadingHtml } from "./win-loading-html";
import { Constants } from "../../../commons/class/constants";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { PregressoVerbaleService } from "../../../pregresso/services/pregresso-verbale.service";
import { MessageVO } from "../../../commons/vo/messageVO";
import { TypeAlert } from "../../../shared/component/shared-alert/shared-alert.component";
import { SalvaOrdinanzaPregressiRequest } from "../../../commons/request/pregresso/salva-ordinanza-pregressi-request";
import { SalvaOrdinanzaPregressiResponse } from "../../../commons/response/pregresso/salva-ordinanza-pregressi-response";
import { SalvaPianoPregressiRequest } from "../../../commons/request/pregresso/salva-piano-pregressi-request";
import { PregressoRiscossioneSollecitoDettaglioInsComponent } from "../../../pregresso/components/pregresso-riscossione-sollecito-dettaglio-ins/pregresso-riscossione-sollecito-dettaglio-ins.component";
import { SalvaSollecitoPregressiRequest } from "../../../commons/request/pregresso/salva-sollecito-pregressi-request";
import { SalvaAllegatoOrdinanzaRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-request";
import { SalvaAllegatoOrdinanzaVerbaleSoggettoRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-verbale-soggetto-request";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SalvaConvocazioneAudizioneRequest } from "../../../commons/request/pregresso/salva-convocazione-audizione-request";
import { SalvaVerbaleAudizioneRequest } from "../../../commons/request/pregresso/salva-verbale-audizione-request";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";

type saveRequestFascicoloAllegatoDaActaComponent =
  | SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest
  | SalvaAllegatoProtocollatoVerbaleRequest
  | SalvaAllegatoProtocollatoOrdinanzaRequest
  | SalvaPianoPregressiRequest
  | SalvaSollecitoPregressiRequest
  | SalvaConvocazioneAudizioneRequest
  | SalvaVerbaleAudizioneRequest;

const idAllegatiSuper: number[] = [
  12, //Ordinanza id 12
  27, //Convocazione audizione id 27
  11, //Ordinanza archiviazione id 11
  10, //Verbale Audizione id 10
  17, // Lettera di rateizzazione/Piano  id 17
  20, //Sollecito id 20
  14, //Disposizione del giudice id 14
  22, //Ricevuta-pagamento-ordinanza id 22
  19, //Lettera accompagnatoria ordinanza 19
  7, //Ricevuta Pagamento 7
];

@Component({
  selector: "fascicolo-allegato-da-acta",
  templateUrl: "./fascicolo-allegato-da-acta.component.html",
})
export class FascicoloAllegatoDaActaComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;
  @ViewChild(PregressoRiscossioneSollecitoDettaglioInsComponent)
  pregressoRiscossioneSollecitoDettaglioInsComponent: PregressoRiscossioneSollecitoDettaglioInsComponent;

  public subscribers: any = {};

  public idVerbale: number;
  public idOrdinanza: number;
  public numDeterminazione: string;
  public loaded: boolean;
  public loadedConfig: boolean;
  public loadedCategoriaAllegato: boolean;

  public allegatoModel: RiepilogoAllegatoVO = new RiepilogoAllegatoVO();
  public tipoAllegatoModel: Array<TipoAllegatoVO>;

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;

  public showMessageBottom: boolean;
  public typeMessageBottom: string;
  public messageBottom: string;
  public mess: ExceptionVO | MessageVO;

  public buttonModificaFlag: boolean;
  public allegaDocumentoFlag: boolean;
  public eliminaAllegatoFlag: boolean;

  public buttonAnnullaTexts: string;
  public buttonConfirmTexts: string;
  public subMessagess: Array<string>;
  public subLinks: Array<any>;

  public configVerb: Config;
  public configIstr: Config;
  public configGiurisd: Config;
  public configRateizzazione: Config;
  public configRicercaProtocollo: Config;

  configSoggetti: Config;

  scrollEnable: boolean;

  searchFormRicercaProtocol: string;
  dataRicercaProtocollo: Array<DocumentoProtocollatoVO>;
  dataRicercaProtocolloSelected: DocumentoProtocollatoVO;
  numberDocument: NumberDocument;
  numberDocumentSuper: NumberDocument;
  validsAllegatoMetadata: any[] = [];
  saveDisabled: boolean = true;
  incompleted: boolean = false;
  typeSaveRequestCategory: number = -1;

  showCompMeta: number = -1;

  isPregresso: boolean = false;
  compMetaDataDisabled: boolean = false;
  compMetaData: any;

  modeSuper: boolean = false;
  currentSuper: number = -1;

  riepilogoVerbale: RiepilogoVerbaleVO;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedVerbaleService: SharedVerbaleService,
    private verbaleService: VerbaleService,
    private sharedVerbaleConfigService: SharedVerbaleConfigService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private datatableService: DatatableService,
    private configSharedService: ConfigSharedService,
    private allegatoSharedService: AllegatoSharedService,
    private sharedOrdinanzaService: SharedOrdinanzaService,
    private fascicoloService: FascicoloService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(FascicoloAllegatoDaActaComponent.name);

    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.VERBALE_DATI);

      this.configRicercaProtocollo =
        this.configSharedService.getConfigRicercaProtocollo(true);
      // carico la stringa di ricerca da cui arrivo
      this.searchFormRicercaProtocol =
        this.fascicoloService.searchFormRicercaProtocol;

      // carico i dati della ricerca da cui arrivo e utilizzo il tipo url per lo scaricamento dei file

      this.dataRicercaProtocollo = this.fascicoloService.dataRicercaProtocollo;
      if (this.dataRicercaProtocollo && this.dataRicercaProtocollo.length > 0) {
        this.dataRicercaProtocollo.forEach((all) => {
          if (!(all.filename instanceof MyUrl)) {
            all.filename = new MyUrl(<string>all.filename, null);
          }
        });
      }
      // setto e recupero le info del numberdocument
      this.numberDocument = this.fascicoloService.setNumberDocument();
      this.loadedConfig = true;
      this.loaded = true;
      this.typeSaveRequestCategory = -1;
      if (this.activatedRoute.snapshot.paramMap.get("idOrdinanza")) {
        // setto il riferimento per la ricerca documento protocollato
        this.idOrdinanza = parseInt(
          this.activatedRoute.snapshot.paramMap.get("idOrdinanza")
        );
      }

      if (this.activatedRoute.snapshot.paramMap.get("numDeterminazione")) {
        // setto il riferimento per la ricerca documento protocollato
        this.numDeterminazione =
          this.activatedRoute.snapshot.paramMap.get("numDeterminazione");
      }
      this.modeSuper = false;
      this.currentSuper = -1;

      this.loadTipoAllegato();
      const typeLoadTipoAllegato =
        this.fascicoloService.getTypeLoadTipoAllegato();
      this.isPregresso =
        typeLoadTipoAllegato == 6 || typeLoadTipoAllegato == 7 ? true : false;

      if (typeLoadTipoAllegato === 1 || this.isPregresso) this.loadRiepilogo();

      if (this.fascicoloService.message) {
        this.mess = this.fascicoloService.message;
        this.manageMessageBottom();
      }
    });
  }

  manageMessage(mess: ExceptionVO | MessageVO) {
    this.typeMessageTop = mess.type;
    this.messageTop = mess.message;
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

  manageMessageBottom() {
    this.showMessageBottom = true;
    this.typeMessageBottom = this.mess.type;
    this.messageBottom = this.mess.message;
  }

  resetMessageBottom() {
    this.showMessageBottom = false;
    this.typeMessageBottom = null;
    this.messageBottom = null;
  }

  loadRiepilogo() {
    this.subscribers.riepilogo = this.sharedVerbaleService
      .riepilogoVerbale(this.idVerbale)
      .subscribe((data) => {
        if (data.verbale) {
          if (data.verbale.stato && data.verbale.stato.id === 1) {
            this.incompleted = true;
          }
          this.riepilogoVerbale = data;
        }
      });
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    const typeLoadTipoAllegato =
      this.fascicoloService.getTypeLoadTipoAllegato();
    this.isPregresso =
      typeLoadTipoAllegato == 6 || typeLoadTipoAllegato == 7 ? true : false;
    this.loadedCategoriaAllegato = false;
    let request:
      | TipologiaAllegabiliRequest
      | TipologiaAllegabiliOrdinanzaSoggettoRequest;
    let call;
    if (typeLoadTipoAllegato === 3 || typeLoadTipoAllegato === 5) {
      request = new TipologiaAllegabiliOrdinanzaSoggettoRequest();
      request.id = this.fascicoloService.idOrdinanzaVerbaleSoggetto;
    } else if (typeLoadTipoAllegato === 7) {
      request = new TipologiaAllegabiliRequest();
      request.id = this.idOrdinanza;
    } else {
      request = new TipologiaAllegabiliRequest();
      request.id = this.idVerbale;
    }

    if (typeLoadTipoAllegato === 2) {
      request.tipoDocumento = Constants.CONTRODEDUZIONE;
    } else if (typeLoadTipoAllegato === 3) {
      request.tipoDocumento = Constants.DISPOSIZIONE_DEL_GIUDICE;
    } else if (typeLoadTipoAllegato === 4) {
      request.tipoDocumento = Constants.OPPOSIZIONE_GIURISDIZIONALE;
    } else if (typeLoadTipoAllegato === 5) {
      request.tipoDocumento = Constants.ISTANZA_RATEIZZAZIONE;
    }
    request.aggiungiCategoriaEmail = true;

    if (request instanceof TipologiaAllegabiliOrdinanzaSoggettoRequest) {
      call =
        this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(
          request
        );
    } else if (typeLoadTipoAllegato === 4) {
      call =
        this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(
          request
        );
    } else if (typeLoadTipoAllegato === 6) {
      call =
        this.pregressoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(
          request
        );
    } else if (typeLoadTipoAllegato === 7) {
      call =
        this.pregressoVerbaleService.getTipologiaAllegatiAllegabiliByOrdinanza(
          request
        );
    } else {
      call = this.verbaleService.getTipologiaAllegatiAllegabiliVerbale(request);
    }

    this.subscribers.tipoAllegato = call.subscribe(
      (data) => {
        this.tipoAllegatoModel = data.sort((a, b) => a.id - b.id);
        this.loadedCategoriaAllegato = true;
        // setto il riferimento per la ricerca documento protocollato
        this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.info("Errore nel recupero dei tipi di allegato");
        this.loadedCategoriaAllegato = true;
      }
    );
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
    nuovoAllegato.idVerbale = this.idVerbale;
    //mando il file al Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.sharedVerbaleService
      .salvaAllegatoVerbale(nuovoAllegato)
      .subscribe(
        (data) => {
          if (nuovoAllegato.file != null) {
            data.theUrl = this.datatableService.creaUrl(nuovoAllegato.file);
            //mostro il nuovo allegato in tabella
            if (data.idCategoria == 1) this.allegatoModel.verbale.push(data);
            if (data.idCategoria == 2)
              this.allegatoModel.istruttoria.push(data);
            if (data.idCategoria == 3)
              this.allegatoModel.giurisdizionale.push(data);
            if (data.idCategoria == 4)
              this.allegatoModel.rateizzazione.push(data);
          }

          //recupero gli allegati da mettere in tabella
          this.subscribers.allegati = this.verbaleService
            .getAllegatiByIdVerbale(this.idVerbale)
            .subscribe(
              (data) => {
                this.allegatoModel = data;
                this.allegatoModel.verbale.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.istruttoria.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.giurisdizionale.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.rateizzazione.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
              },
              (err) => {
                this.logger.error("Errore nel recupero degli allegati");
              }
            );

          this.loadTipoAllegato();
          //mostro un messaggio
          this.manageMessage({
            type: TypeAlert.SUCCESS,
            message: "Documento caricato con successo",
          });
          this.loaded = true;
          this.scrollEnable = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err);
          }
          this.logger.error("Errore nel salvataggio dell'allegato");
          this.loaded = true;
          this.scrollEnable = true;
        }
      );
  }

  ricercaProtocollo(ricerca: RicercaProtocolloRequest) {
    ricerca.idVerbale = this.idVerbale;
    if (this.fascicoloService.getTypeRicerca() === 2) {
      ricerca.flagPregresso = true;
    }

    this.loaded = false;
    this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
      (data) => {
        let tipiAllegatoDuplicabili = [];

        if (
          data.documentoProtocollatoVOList &&
          data.documentoProtocollatoVOList.length > 0
        ) {
          if (data.documentoProtocollatoVOList[0]) {
            tipiAllegatoDuplicabili =
              data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
          }
          data.documentoProtocollatoVOList.forEach((all) => {
            all.filename = new MyUrl(<string>all.filename, null);
          });
        } else {
          data.documentoProtocollatoVOList = [];
        }

        if (data.messaggio) {
          this.mess = data.messaggio;
          this.manageMessageBottom();
        } else {
          this.mess = null;
          this.resetMessageBottom();
        }

        this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;
        this.loaded = true;
        this.fascicoloService.searchFormRicercaProtocol =
          ricerca.numeroProtocollo;
        this.searchFormRicercaProtocol =
          this.fascicoloService.searchFormRicercaProtocol;
        this.dataRicercaProtocollo = data.documentoProtocollatoVOList;

        // reset sezione assegna documenti
        this._resetCategories();
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.loaded = true;
        this.logger.error("Errore nel recupero degli allegati");
      }
    );
  }

  onValidAllegatoMetadati(event: any) {
    if (
      this.validsAllegatoMetadata.find(
        (item: any) => item.current === event.current
      )
    ) {
      this.validsAllegatoMetadata = this.validsAllegatoMetadata.map((item) => {
        return item.current === event.current ? event : item;
      });
    } else {
      this.validsAllegatoMetadata.push(event);
    }
    this._checkSave();
  }

  _allegatiSuperInit(event: any) {
    this.modeSuper = true;
    this.currentSuper = event.current;
    this.numberDocumentSuper = {
      value: 1,
      readonly: true,
    };
  }

  onChangeCategoriaDocumento(event: any) {
    let $idTipo: number = event.tipoAllegatoSelezionato.id;

    if (this.isPregresso && idAllegatiSuper.indexOf($idTipo) > -1) {
      this._allegatiSuperInit(event);
    } else {
      this.modeSuper = false;
      this.currentSuper = -1;
    }

    if ($idTipo == 12) {
      // ordinanza
      this.typeSaveRequestCategory = 1;
      this.showCompMeta = 12;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 11) {
      // ordinanza archviazione
      this.typeSaveRequestCategory = 1;
      this.showCompMeta = 11;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 17) {
      // piano
      this.typeSaveRequestCategory = 2;
      this.showCompMeta = 17;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 20) {
      // sollecito
      this.typeSaveRequestCategory = 3;
      this.showCompMeta = 20;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 14) {
      // disposizione del giudice
      this.typeSaveRequestCategory = 4;
      this.showCompMeta = 14;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 22) {
      // ricevuta-pagamento-ordinanza
      this.typeSaveRequestCategory = 5;
      this.showCompMeta = 22;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 27) {
      // convocazione audizione
      this.typeSaveRequestCategory = 6;
      this.configSoggetti =
        this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
          true,
          1,
          (el: TableSoggettiVerbale) => {
            if (el.verbaleAudizioneCreato) {
              return false;
            } else {
              return true;
            }
          },
          false
        );
      this.showCompMeta = 27;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 10) {
      // verbale audizione
      this.typeSaveRequestCategory = 7;
      this.configSoggetti =
        this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
          true,
          1,
          (el: TableSoggettiVerbale) => {
            if (el.verbaleAudizioneCreato) {
              return false;
            } else {
              return true;
            }
          },
          false
        );
      this.showCompMeta = 10;
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else if ($idTipo == 19) {
      // letter accompagnatoria ordinanza
      this.typeSaveRequestCategory = 4;
      this.showCompMeta = 19;
      this.compMetaDataDisabled = true;
      this._checkSave();
    }
    if ($idTipo == 5) {
      // ordinanza
      this.compMetaDataDisabled = true;
      this._checkSave();
    } else {
      this.typeSaveRequestCategory = -1;
      this.showCompMeta = -1;
      this.compMetaDataDisabled = false;
      this._checkSave();
    }
  }

  onNumberChange(v: number): void {
    let validsAllegatoMetadataTmp = [];
    this.validsAllegatoMetadata.forEach((value, index) => {
      if (value.current < v) validsAllegatoMetadataTmp.push(value);
    });
    this.validsAllegatoMetadata = validsAllegatoMetadataTmp;
    this._checkSave();
  }

  _checkSave() {
    if (
      !this.modeSuper &&
      (this.validsAllegatoMetadata.length !== this.numberDocument.value ||
        this.validsAllegatoMetadata.find((item: any) => item.valid === false))
    ) {
      this.saveDisabled = true;
    } else {
      if (this.modeSuper && this.compMetaDataDisabled) {
        this.saveDisabled = true;
      } else {
        this.saveDisabled = false;
      }
    }
  }

  _isCategoriesAllowed(): boolean {
    // this.fascicoloService.categoriesNotDuplicated;
    let categories: Array<number> = [];
    let allowed: boolean = true;
    this.validsAllegatoMetadata.forEach((value, index) => {
      if (
        this.fascicoloService.categoriesDuplicated.indexOf(value.idTipo) === -1
      ) {
        if (categories.indexOf(value.idTipo) > -1) {
          allowed = false;
        } else {
          categories.push(value.idTipo);
        }
      }
    });
    return allowed;
  }

  _messageCategoriesNotAllowed() {
    /* genera messaggio */
    this.subLinks = new Array<any>();
    this.subMessagess = new Array<string>();
    let message: string =
      "Salvataggio non consentito. Verifica la coerenza delle categorie presenti per il documento selezionato.";
    this.subMessagess.push(message);

    this.buttonAnnullaTexts = "";
    this.buttonConfirmTexts = "Ok";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  isRicevutaPagamentoPregressa(): boolean {
    if (
      this.isPregresso &&
      this.modeSuper &&
      this.validsAllegatoMetadata[this.currentSuper].idTipo == 7
    ) {
      return true;
    }
    return false;
  }

  save() {
    if (this._isCategoriesAllowed()) {
      let typeSaveRequest = this.fascicoloService.getTypeSaveRequest();
      let saveRequest: saveRequestFascicoloAllegatoDaActaComponent;
      if (this.typeSaveRequestCategory == 1) {
        saveRequest = new SalvaOrdinanzaPregressiRequest(this.idVerbale);
        (<SalvaOrdinanzaPregressiRequest>saveRequest).soggetti =
          this.compMetaData.soggetti;
        (<SalvaOrdinanzaPregressiRequest>saveRequest).notifica =
          this.compMetaData.notifica;
        (<SalvaOrdinanzaPregressiRequest>saveRequest).ordinanza =
          this.compMetaData.ordinanza;
      } else if (this.typeSaveRequestCategory == 2) {
        saveRequest = new SalvaPianoPregressiRequest(
          this.idVerbale,
          this.idOrdinanza
        );
        (<SalvaPianoPregressiRequest>saveRequest).piano =
          this.compMetaData.piano;
      } else if (this.typeSaveRequestCategory == 3) {
        if (
          (this.compMetaData.getData &&
            this.pregressoRiscossioneSollecitoDettaglioInsComponent != null) ||
          this.pregressoRiscossioneSollecitoDettaglioInsComponent != undefined
        ) {
          saveRequest = new SalvaSollecitoPregressiRequest(
            this.idVerbale,
            this.idOrdinanza
          );
          const myData =
            this.pregressoRiscossioneSollecitoDettaglioInsComponent.getData();
          (<SalvaSollecitoPregressiRequest>saveRequest).notifica =
            myData.notifica;
          (<SalvaSollecitoPregressiRequest>saveRequest).sollecito =
            myData.sollecito;
        } else {
          // errore non previsto
          return;
        }
      } else if (this.typeSaveRequestCategory == 4) {
        saveRequest = new SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest(
          this.compMetaData.idOrdinanzaVerbaleSoggetto
        );
      } else if (this.typeSaveRequestCategory == 5) {
        let request = new SalvaAllegatoOrdinanzaVerbaleSoggettoRequest();
        request.idOrdinanzaVerbaleSoggetto =
          this.compMetaData.idOrdinanzaVerbaleSoggetto;
        request.idTipoAllegato = this.compMetaData.tipoAllegatoSelezionatoId;
        request.allegatoField = this.compMetaData.metaDataModel;
        this._saveRequestAsis(typeSaveRequest, request);
        return;
      } else if (this.typeSaveRequestCategory == 6) {
        // convocazione audizione
        saveRequest = new SalvaConvocazioneAudizioneRequest(this.idVerbale);
        (<SalvaConvocazioneAudizioneRequest>saveRequest).idVerbaleSoggettoList =
          this.compMetaData.idVerbaleSoggettoList;
      } else if (this.typeSaveRequestCategory == 7) {
        // verbale audizione
        saveRequest = new SalvaVerbaleAudizioneRequest(this.idVerbale);
        (<SalvaVerbaleAudizioneRequest>saveRequest).idVerbaleSoggettoList =
          this.compMetaData.idVerbaleSoggettoList;
      } else if (this.isRicevutaPagamentoPregressa()) {
        let request = new SalvaAllegatoVerbaleRequest();
        request.idVerbale = this.idVerbale;
        request.idTipoAllegato =
          this.validsAllegatoMetadata[this.currentSuper].idTipo;
        request.allegatoField =
          this.validsAllegatoMetadata[this.currentSuper].metaDataModel;
        this._saveRequestAsis(typeSaveRequest, request);
        return;
      } else if (typeSaveRequest === 2) {
        saveRequest = new SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest(
          this.fascicoloService.idOrdinanzaVerbaleSoggetto
        );
      } else if (typeSaveRequest === 3) {
        // in realta' è id ordinanza
        saveRequest = new SalvaAllegatoProtocollatoOrdinanzaRequest(
          this.idVerbale
        );
      } else if (typeSaveRequest === 5) {
        // in realta' è id ordinanza
        saveRequest = new SalvaAllegatoProtocollatoOrdinanzaRequest(
          this.idOrdinanza
        );
      } else {
        saveRequest = new SalvaAllegatoProtocollatoVerbaleRequest(
          this.idVerbale
        );
      }

      let documentoProtocollato: DocumentoProtocollatoVO = {
        ...this.dataRicercaProtocolloSelected,
      };

      const filenameTmp: any = documentoProtocollato.filename;
      if (filenameTmp.nomeFile) {
        documentoProtocollato.filename = filenameTmp.nomeFile;
      }

      saveRequest.allegati = [];
      this.validsAllegatoMetadata.forEach((value, index) => {
        let allegato: SalvaAllegatoProtocollatoVerbaleRequestAllegato = {
          idTipoAllegato: <number>value.idTipo,
        };
        if (value.metaDataModel) {
          allegato.allegatoField = value.metaDataModel;
        }
        if (
          this.compMetaData &&
          this.compMetaData.metaDataModel &&
          this.typeSaveRequestCategory == 4
        ) {
          allegato.allegatoField = this.compMetaData.metaDataModel;
        }
        if (!this.modeSuper) {
          saveRequest.allegati[value.current] = allegato;
        } else if (this.currentSuper == value.current) {
          saveRequest.allegati[0] = allegato;
        }
      });

      saveRequest.documentoProtocollato = documentoProtocollato;

      if (
        !this.incompleted &&
        saveRequest.documentoProtocollato.registrazioneId !== null
      ) {
        this._confermaSave(typeSaveRequest, saveRequest);
      } else {
        this._saveRequest(typeSaveRequest, saveRequest);
      }
    } else {
      this._messageCategoriesNotAllowed();
    }
  }

  onChangeData(event: any) {
    this.compMetaDataDisabled = event.disabled;
    this.compMetaData = event;
    this._checkSave();
  }

  _saveRequestAsis(
    typeSaveRequest: number,
    saveRequest:
      | SalvaAllegatoVerbaleRequest
      | SalvaAllegatoOrdinanzaRequest
      | SalvaAllegatoOrdinanzaVerbaleSoggettoRequest
  ) {
    let call;
    if (saveRequest instanceof SalvaAllegatoVerbaleRequest) {
      call = this.pregressoVerbaleService.salvaAllegatoVerbale(saveRequest);
    } else if (saveRequest instanceof SalvaAllegatoOrdinanzaRequest) {
      call = this.pregressoVerbaleService.salvaAllegatoOrdinanza(saveRequest);
    } else if (
      saveRequest instanceof SalvaAllegatoOrdinanzaVerbaleSoggettoRequest
    ) {
      call =
        this.pregressoVerbaleService.salvaAllegatoOrdinanzaVerbaleSoggetto(
          saveRequest
        );
    }

    if (call) {
      this.loaded = false;
      return call.subscribe(
        (data) => {
          this.resetMessageBottom();
          this.loaded = true;
          this.loadTipoAllegato();
          // resetto per il salvataggio successivo
          this._resetCategories();
          let msgOk = "Documento salvato correttamente";
          this._messageSave(msgOk);
        },
        (err) => {
          this.loaded = true;
          this.logger.error("Errore nel recupero degli allegati");
          if (err instanceof ExceptionVO) {
            this._messageSave(err.message);
          } else {
            this._messageSave("Errore nel salvataggio");
          }
          //this.router.navigateByUrl(this.fascicoloService.ref);
        }
      );
    }
  }

  _saveRequest(
    typeSaveRequest: number,
    saveRequest: saveRequestFascicoloAllegatoDaActaComponent
  ) {
    let call;

    if (this.typeSaveRequestCategory == 1) {
      call = this.pregressoVerbaleService.salvaOrdinanza(
        <SalvaOrdinanzaPregressiRequest>saveRequest
      );
    } else if (this.typeSaveRequestCategory == 2) {
      //
      call = this.pregressoVerbaleService.salvaPiano(
        <SalvaPianoPregressiRequest>saveRequest
      );
    } else if (this.typeSaveRequestCategory == 3) {
      call = this.pregressoVerbaleService.checkSalvaSollecito(
        <SalvaSollecitoPregressiRequest>saveRequest
      );
    } else if (this.typeSaveRequestCategory == 1003) {
      call = this.pregressoVerbaleService.salvaSollecito(
        <SalvaSollecitoPregressiRequest>saveRequest
      );
    } else if (this.typeSaveRequestCategory == 4) {
      call =
        this.pregressoVerbaleService.salvaAllegatoProtocollatoOrdinanzaSoggetto(
          <SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest>saveRequest
        );
    } else if (this.typeSaveRequestCategory == 6) {
      call = this.pregressoVerbaleService.salvaConvocazioneAudizione(
        <SalvaConvocazioneAudizioneRequest>saveRequest
      );
    } else if (this.typeSaveRequestCategory == 7) {
      call = this.pregressoVerbaleService.salvaVerbaleAudizione(
        <SalvaVerbaleAudizioneRequest>saveRequest
      );
    } else if (typeSaveRequest === 2) {
      call =
        this.sharedOrdinanzaService.salvaAllegatoProtocollatoOrdinanzaSoggetto(
          <SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest>saveRequest
        );
    } else if (typeSaveRequest === 3) {
      call = this.sharedOrdinanzaService.salvaAllegatoProtocollatoOrdinanza(
        <SalvaAllegatoProtocollatoOrdinanzaRequest>saveRequest
      );
    } else if (typeSaveRequest === 4) {
      call = this.pregressoVerbaleService.salvaAllegatoProtocollato(
        <SalvaAllegatoProtocollatoVerbaleRequest>saveRequest
      );
    } else if (typeSaveRequest === 5) {
      call = this.pregressoVerbaleService.salvaAllegatoProtocollatoOrdinanza(
        <SalvaAllegatoProtocollatoOrdinanzaRequest>saveRequest
      );
    } else {
      call = this.sharedVerbaleService.salvaAllegatoProtocollato(
        <SalvaAllegatoProtocollatoVerbaleRequest>saveRequest
      );
    }
    this.loaded = false;
    return call.subscribe(
      (data) => {
        this.resetMessageBottom();
        if (this.typeSaveRequestCategory == 3) {
          if (data.result == "OK") {
            this.typeSaveRequestCategory = 1003;
            this._saveRequest(typeSaveRequest, saveRequest);
          } else {
            this._confirmSaveSollecito(data.confirmMsg, () => {
              this.typeSaveRequestCategory = 1003;
              this._saveRequest(typeSaveRequest, saveRequest);
            });
          }
        } else if (
          this.isPregresso ||
          this.incompleted ||
          saveRequest.documentoProtocollato.registrazioneId === null
        ) {
          this.loaded = true;
          this.loadTipoAllegato();
          // aggiungo questo documento tra quelli che verranno salvati al fascicolo
          this.dataRicercaProtocollo.forEach((item) => {
            if (
              saveRequest.documentoProtocollato.objectIdDocumento ===
              item.objectIdDocumento
            ) {
              item.giaSalvato = true;
            }
          });
          // resetto per il salvataggio successivo
          this._resetCategories();
          if (data && data.confirmMsg) {
            let callbackSave = () => {};
            if (data.idOrdinanza && data.numDeterminazione) {
              callbackSave = () => {
                this.fascicoloService.ref =
                  "pregresso/riepilogo-ordinanze/" + this.idVerbale;
                this.fascicoloService.backPage =
                  "pregresso/riepilogo-ordinanze/" + this.idVerbale;
                this.router.navigate([
                  Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idVerbale,
                  {
                    idOrdinanza: data.idOrdinanza,
                    numDeterminazione: data.numDeterminazione,
                  },
                ]);
              };
            }
            this._confirmAggiungiAllegati(data.confirmMsg, callbackSave);
          } else {
            let msgOk =
              data && data.msg ? data.msg : "Documento salvato correttamente";
            if (data && data.message) {
              msgOk = data.message;
            }
            this._messageSave(msgOk);
          }
        } else {
          this.loaded = true;
          // azione per allegato caricato
          this.router.navigate([
            this.fascicoloService.successPage,
            { action: this.fascicoloService.getSaveActionSuccessMessage() },
          ]);
        }
      },
      (err) => {
        this.loaded = true;
        this.logger.error("Errore nel recupero degli allegati");
        if (err instanceof ExceptionVO) {
          this._messageSave(err.message);
        } else {
          this._messageSave("Errore nel salvataggio");
        }
        //this.router.navigateByUrl(this.fascicoloService.ref);
      }
    );
  }

  _confirmSaveSollecito(messageVo: MessageVO, saveClick: any) {
    /* genera messaggio */
    this.subMessagess = new Array<string>();
    this.subLinks = new Array<any>();

    //let incipit: string = 'Si vuole continuare con il recupero della documentazione individuata tramite il protocollo '+this.fascicoloService.searchFormRicercaProtocol+'?';
    let incipit: string = messageVo.message;
    this.subMessagess.push(incipit);

    this.buttonAnnullaTexts = "No";
    this.buttonConfirmTexts = "Si";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        // salva
        saveClick();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.loaded = true;
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  _confirmAggiungiAllegati(messageVo: MessageVO, callbackSave: any) {
    /* genera messaggio */
    this.subMessagess = new Array<string>();
    this.subLinks = new Array<any>();

    //let incipit: string = 'Si vuole continuare con il recupero della documentazione individuata tramite il protocollo '+this.fascicoloService.searchFormRicercaProtocol+'?';
    let incipit: string = messageVo.message;
    this.subMessagess.push(incipit);

    this.buttonAnnullaTexts = "No";
    this.buttonConfirmTexts = "Si";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        // il reset è stato gia' fatto
        if (callbackSave) {
          callbackSave();
        }
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
        this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO + this.idVerbale);
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  onSelectSoggetti(e: any) {
    if (this.showCompMeta == 19 && e) {
      this.compMetaDataDisabled = false;
      this.compMetaData = {
        idOrdinanzaVerbaleSoggetto: [e.idSoggettoOrdinanza],
      };
    } else if (e.length) {
      this.compMetaDataDisabled = e.length == 0 ? true : false;
      this.compMetaData = {
        idVerbaleSoggettoList: e.map((s) => s.idVerbaleSoggetto),
      };
    } else {
      this.compMetaDataDisabled = true;
    }
    this._checkSave();
  }

  _resetCategories() {
    this.modeSuper = false;
    this.currentSuper = -1;
    this.numberDocument = this.fascicoloService.setNumberDocument();
    this.dataRicercaProtocolloSelected = null;
    this.typeSaveRequestCategory = -1;
    this.typeSaveRequestCategory = -1;
    this.showCompMeta = -1;
    this.saveDisabled = true;
    this.validsAllegatoMetadata = [];
  }

  _messageSave(message: string) {
    /* genera messaggio */
    this.subLinks = new Array<any>();
    this.subMessagess = new Array<string>();

    this.subMessagess.push(message);

    this.buttonAnnullaTexts = "";
    this.buttonConfirmTexts = "Ok";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  _confermaSave(
    typeSaveRequest: number,
    saveRequest: saveRequestFascicoloAllegatoDaActaComponent
  ) {
    let inserted = new Array<string>();
    let losed = new Array<string>();
    saveRequest.documentoProtocollato.objectIdDocumento;

    this.dataRicercaProtocollo.forEach((item) => {
      let nome =
        typeof item.filename == "string"
          ? item.filename
          : item.filename.nomeFile;
      if (
        saveRequest.documentoProtocollato.objectIdDocumento ===
          item.objectIdDocumento ||
        item.giaSalvato
      ) {
        inserted.push(nome);
      } else {
        losed.push(nome);
      }
    });

    /* genera messaggio */
    this.subMessagess = new Array<string>();
    this.subLinks = new Array<any>();

    let incipit: string =
      "Verrano inseriti nel fascicolo Conam i seguenti documenti: " +
      inserted.join(", ") +
      ".";
    this.subMessagess.push(incipit);
    let incipit3: string = "Procedere?";

    this.subMessagess.push(incipit3);

    this.buttonAnnullaTexts = "No";
    this.buttonConfirmTexts = "Si";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subscribers.saveAllegato = this._saveRequest(
          typeSaveRequest,
          saveRequest
        );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  onSelected(el: DocumentoProtocollatoVO) {
    this.dataRicercaProtocolloSelected = el;
    // this.selected.emit(el);
  }

  getAllegato(el: DocumentoProtocollatoVO) {
    const myFilename =
      typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
    if (myFilename.toLowerCase().startsWith("documento multiplo")) {
      this.loaded = false;
      this.allegatoSharedService
        .getDocFisiciByObjectIdDocumento(el.objectIdDocumento)
        .subscribe((data) => {
          this.loaded = true;
          this.openAllegatoMultiplo(data);
        });
    } else {
      this.openAllegato(el);
    }
  }

  openAllegatoMultiplo(els: any[]) {
    this.subMessagess = new Array<string>();

    this.subLinks = new Array<any>();

    els.forEach((item) => {
      this.subLinks.push({ ...item, label: item.nomeFile });
    });

    this.buttonAnnullaTexts = "";
    this.buttonConfirmTexts = "Ok";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  openAllegatoFisico(el: any) {
    const myFilename = el.nomeFile;
    const donwloadTypes = ["tiff", "p7m"];
    const ext = myFilename.split(".").pop();

    const winUrl = URL.createObjectURL(
      new Blob([winLoadingHtml], { type: "text/html" })
    );

    var win;
    if (donwloadTypes.indexOf(ext) == -1) {
      win = window.open(winUrl);
    } else {
      this.loaded = false;
    }

    this.allegatoSharedService
      .getAllegatoByObjectIdDocumentoFisico(el.objectIdDocumentoFisico)
      .subscribe((data) => {
        if (donwloadTypes.indexOf(ext) > -1) {
          this.loaded = true;
          saveAs(data, myFilename || "Unknown");
        } else {
          let myBlob: any = data;
          if (myBlob.type == "application/json") {
            win.close();
            myBlob.text().then((text) => {
              this.manageMessage(JSON.parse(text));
            });
          } else {
            myBlob.name = myFilename;
            var fileURL = URL.createObjectURL(myBlob);
            win.location.href = fileURL;
          }
        }
      });
  }

  openAllegato(el: DocumentoProtocollatoVO) {
    const myFilename =
      typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
    const donwloadTypes = ["tiff", "p7m"];
    const ext = myFilename.split(".").pop();

    const winUrl = URL.createObjectURL(
      new Blob([winLoadingHtml], { type: "text/html" })
    );

    var win;
    if (donwloadTypes.indexOf(ext) == -1) {
      win = window.open(winUrl);
    } else {
      this.loaded = false;
    }

    this.allegatoSharedService
      .getAllegatoByParolaChiave(el.objectIdDocumento)
      .subscribe((data) => {
        if (donwloadTypes.indexOf(ext) > -1) {
          this.loaded = true;
          saveAs(data, myFilename || "Unknown");
        } else {
          let myBlob: any = data;
          if (myBlob.type == "application/json") {
            win.close();
            myBlob.text().then((text) => {
              this.manageMessage(JSON.parse(text));
            });
          } else {
            myBlob.name = myFilename;
            var fileURL = URL.createObjectURL(myBlob);
            win.location.href = fileURL;
          }
        }
      });
  }

  confermaEliminazione(el: AllegatoVO) {
    this.logger.info("Richiesta eliminazione dell'allegato " + el.id);
    this.generaMessaggio(el);
    this.buttonAnnullaTexts = "Annulla";
    this.buttonConfirmTexts = "Conferma";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.eliminaAllegato = this.sharedVerbaleService
          .eliminaAllegato(el.id, this.idVerbale)
          .subscribe(
            (data) => {
              //elimina l'allegato
              this.logger.info("Elimina elemento da tabella");
              this.allegatoModel.verbale.splice(
                this.allegatoModel.verbale.indexOf(el),
                1
              );
              this.loadTipoAllegato();
              this.loaded = true;
              this.scrollEnable = true;
              this.manageMessage({
                type: TypeAlert.SUCCESS,
                message: "Documento eliminato con successo",
              });
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.manageMessage(err);
              }
              this.logger.error(
                "Errore nell'eliminazione dell'allegato " + el.id
              );
              this.loaded = true;
              this.scrollEnable = true;
            }
          );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (
      this.loaded &&
      this.scrollEnable &&
      this.loadedConfig &&
      this.loadedCategoriaAllegato &&
      out != null
    ) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  generaMessaggio(el: AllegatoVO) {
    this.subLinks = new Array<any>();
    this.subMessagess = new Array<string>();

    let incipit: string = "Si intende eliminare il seguente allegato?";

    this.subMessagess.push(incipit);
    this.subMessagess.push(el.theUrl.nomeFile);
  }

  goToVerbaleRiepilogo() {
    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);
  }

  goToVerbaleDati() {
    this.router.navigateByUrl(Routing.VERBALE_DATI + this.idVerbale);
  }

  goToVerbaleSoggetto() {
    this.router.navigateByUrl(Routing.VERBALE_SOGGETTO + this.idVerbale);
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngOnDestroy(): void {
    this.logger.destroy(FascicoloAllegatoDaActaComponent.name);
  }

  back(): void {
    if (this.incompleted) {
      this.router.navigateByUrl(this.fascicoloService.backPage);
    } else {
      this.goToVerbaleRiepilogo();
    }
  }
}
