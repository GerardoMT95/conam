import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { Routing } from "../../../commons/routing";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import {
  TipoAllegatoVO,
  SelectVO,
  StatoSoggettoOrdinanzaVO,
} from "../../../commons/vo/select-vo";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { Constants } from "../../../commons/class/constants";
import {
  SalvaOrdinanzaRequest,
  SoggettoOrdinanzaRequest,
} from "../../../commons/request/ordinanza/salva-ordinanza-request";
import { FaseGiurisdizionaleOrdinanzaService } from "../../service/fase-giurisdizionale-ordinanza.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SharedAllegatoMetadatiInserimentoComponent } from "../../../shared/component/shared-allegato-metadati-inserimento/shared-allegato-metadati-inserimento.component";

import { SharedInserimentoNotificaComponent } from "../../../shared-notifica/components/shared-inserimento-notifica/shared-inserimento-notifica.component";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";


declare var $: any;

@Component({
  selector: "ordinanza-ins-crea-ordinanza",
  templateUrl: "./ordinanza-ins-crea-ordinanza.component.html",
})
export class OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedAllegatoMetadatiInserimentoComponent)
  allegatiSubComponent: SharedAllegatoMetadatiInserimentoComponent;

  @ViewChild(SharedInserimentoNotificaComponent)
  insertNotifica: SharedInserimentoNotificaComponent;

  public subscribers: any = {};

  config: Config;
  configSoggettiOrdinanza : Config;
  loaded: boolean;
  idVerbale: number;
  ordinanza: OrdinanzaVO;
  soggettiArray: Array<TableSoggettiVerbale>;
  

  tipoOrdinanzaSoggettoModel: Array<StatoSoggettoOrdinanzaVO>;

  loadedCategoriaAllegato: boolean;
  tipoAllegatoModel: Array<TipoAllegatoVO>;
  allegatoCaricato: boolean;
  salvaAllegatoRequest: SalvaAllegatoRequest;

  checkboxOrdinanzaMista: boolean;

  isCreaOrdinanza: boolean = true;
  itsAnnullamento: string; 
  showAnnullamentoParts: boolean = false;
  idOrdinanza : number; 
  //JIRA - Gestione Notifica
  isImportoNotificaInserito: boolean = true;

  isSelectable: (el: TableSoggettiVerbale) => boolean = (
    el: TableSoggettiVerbale
  ) => {
    if (!this.showAnnullamentoParts && el.ordinanzaCreata) return false;
    else return true;
  };
  

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedVerbaleConfigService: SharedVerbaleConfigService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private faseGiurisdizionaleOrdinanzaService: FaseGiurisdizionaleOrdinanzaService
  ) {}

  ngOnInit(): void {

    this.itsAnnullamento = ''
        this.subscribers.route = this.activatedRoute.params.subscribe((params) => {    

            // verifico se arrivo da inserimento ordinanza di annullamento
            if (this.activatedRoute.snapshot.paramMap.get("azione")) {             
              this.itsAnnullamento  = this.activatedRoute.snapshot.paramMap.get("azione");
              this.idOrdinanza = +this.activatedRoute.snapshot.paramMap.get('idOrdinanza');
            } 
            if (this.itsAnnullamento === 'annullamento') {
                this.showAnnullamentoParts =  true;
            }          
        });


    this.logger.init(
      OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(
          Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_RICERCA_VERBALE
        );
    });
  
    this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
      true,
      1,
      this.isSelectable,
      false
    );

    this.configSoggettiOrdinanza = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      true,
      null,
      1,
      this.isSelectable,
      (el: any) => false,
      (el: any) => false,
      false
    );
    

    this.ordinanza = new OrdinanzaVO();
    this.soggettiArray = new Array();
    this.loadTipoAllegato();
    this.loadStatiOrdinanzaSoggettoInCreazioneOrdinanza();
    this.loaded = true;
    }

  addToArraySoggettiSelezionati(e: Array<TableSoggettiVerbale>) {
    this.soggettiArray = e;
  }
  
  addToArraySoggettiSelezionatiAnnullamento(arr: Array<any>){
    arr.forEach(el => {
      el.idVerbaleSoggetto = el.idSoggettoVerbale;
    });    
    this.soggettiArray = arr;
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliRequest();
    request.id = this.idVerbale;
    this.tipoAllegatoModel = new Array<TipoAllegatoVO>();
    this.loadedCategoriaAllegato = true;
    if(this.showAnnullamentoParts){
      this.subscribers.tipoAllegato = this.faseGiurisdizionaleOrdinanzaService
      . getTipologiaAllegatiCreaOrdinanzaAnnullamento(this.idOrdinanza)
      .subscribe((data) => {
        this.tipoAllegatoModel = data;
        this.loadedCategoriaAllegato = true;
      });
     
    }
    else{
      this.subscribers.tipoAllegato = this.faseGiurisdizionaleOrdinanzaService
      .getTipologiaAllegatiCreaOrdinanza(request)
      .subscribe((data) => {
        this.tipoAllegatoModel = data;
        this.loadedCategoriaAllegato = true;
      });
    }
   
  }

  loadStatiOrdinanzaSoggettoInCreazioneOrdinanza() {
    this.subscribers.TipoSoggettoOrdinanza = this.faseGiurisdizionaleOrdinanzaService
      .getStatiOrdinanzaSoggettoInCreazioneOrdinanza()
      .subscribe((data) => {
        this.tipoOrdinanzaSoggettoModel = data;
      });
  }

  scrollEnable: boolean;
  ngAfterViewChecked() {
    this.manageDatePicker(null, 1, null);
    this.manageDatePicker(null, 2, null);
    this.manageDatePicker(null, 3, new Date());
    this.manageDatePicker(null, 4, new Date());
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  manageDatePicker(event: any, i: number, minDate: Date) {
    var str: string = "#datepicker" + i.toString();
    if ($(str).length) {
      if (minDate != null) {
        $(str).datetimepicker({
          format: "DD/MM/YYYY",
          minDate: minDate,
        });
      } else {
        $(str).datetimepicker({
          format: "DD/MM/YYYY",
        });
      }
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.ordinanza.dataDeterminazione = event.srcElement.value;
          break;
        case 2:
          this.ordinanza.dataOrdinanza = event.srcElement.value;
          break;
        case 3:
          this.ordinanza.dataScadenza = event.srcElement.value;
          this.ordinanza.dataFineValidita = event.srcElement.value;
          break;
        case 4:
          this.ordinanza.dataFineValidita = event.srcElement.value;
          break;
      }
    }
  }

  addAllegato(event) {
    this.salvaAllegatoRequest = event;
  }

  isTipoAllegatoIngiunzione(): boolean {
  if(this.showAnnullamentoParts){
    return this.allegatiSubComponent
    ? this.allegatiSubComponent.getTipoAllegatoSelezionato()
      ? this.allegatiSubComponent.getTipoAllegatoSelezionato().id ==
        Constants.ID_TIPO_ALLEGATO_INGIUNZIONE_ANNULLAMENTO
      : false
    : false;
  }
  else{
    return this.allegatiSubComponent
      ? this.allegatiSubComponent.getTipoAllegatoSelezionato()
        ? this.allegatiSubComponent.getTipoAllegatoSelezionato().id ==
          Constants.ID_TIPO_ALLEGATO_INGIUNZIONE
        : false
      : false;
    }
  }
  isTipoAllegatoOrdinanza(): boolean {
    if(this.showAnnullamentoParts) {
      return this.allegatiSubComponent
      ? this.allegatiSubComponent.getTipoAllegatoSelezionato()
        ? this.allegatiSubComponent.getTipoAllegatoSelezionato().id ==
          Constants.ID_TIPO_ALLEGATO_ARCHIVIAZIONE_ANNULLAMENTO
        : false
      : false;
    }
    else {
      return this.allegatiSubComponent
      ? this.allegatiSubComponent.getTipoAllegatoSelezionato()
        ? this.allegatiSubComponent.getTipoAllegatoSelezionato().id ==
          Constants.ID_TIPO_ALLEGATO_ARCHIVIAZIONE
        : false
      : false;
    }
  }

  isAllegatoCaricato(): boolean {
    return (
      this.salvaAllegatoRequest != null &&
      this.salvaAllegatoRequest.idTipoAllegato != null
    );
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  showCheckboxWarning(): boolean {
    if (
      !(
        this.soggettiArray.length > 1 &&
        this.checkboxOrdinanzaMista &&
        this.isTipoAllegatoIngiunzione()
      )
    )
      return false;


    if(!this.showAnnullamentoParts){    
      if (
        this.soggettiArray.some(
          (e) => !(e.idTipoOrdinanza && e.idTipoOrdinanza.id)
        )
      )
        return false;
      return !this.soggettiArray.find((e, i, arr) => {
        if (i == 0) return false;
        return !this.byId(e.idTipoOrdinanza, arr[i - 1].idTipoOrdinanza);
      });
    }
    
  }

  //JIRA - Gestione Notifica
  importNotificaInseritoHandler(valueEmitted) {
    this.isImportoNotificaInserito = valueEmitted;
  }
  //--------------------------
  salvaOrdinanza() {
    this.loaded = false;
    let request: SalvaOrdinanzaRequest = new SalvaOrdinanzaRequest();

    if (this.isTipoAllegatoIngiunzione()) {
      request.idTipoAllegato = Constants.ID_TIPO_ALLEGATO_INGIUNZIONE;
    } else {
      request.idTipoAllegato = Constants.ID_TIPO_ALLEGATO_ARCHIVIAZIONE;
      this.ordinanza.importoOrdinanza = null;
      this.ordinanza.dataScadenza = null;
    }

    //request.idTipoAllegato = this.salvaAllegatoRequest.idTipoAllegato;
    request.filename = this.salvaAllegatoRequest.filename;
    request.file = this.salvaAllegatoRequest.file;
    request.allegatoField = this.salvaAllegatoRequest.allegatoField;
    request.ordinanza = this.ordinanza;
    request.soggetti = this.soggettiArray.map((x) => {
      let obj: SoggettoOrdinanzaRequest = new SoggettoOrdinanzaRequest();
      obj.idVerbaleSoggetto = x.idVerbaleSoggetto;
      obj.idTipoOrdinanza =
        x.idTipoOrdinanza != null ? x.idTipoOrdinanza.id : null;
      return obj;
    });
    request.notifica = this.insertNotifica.getNotificaObject();
    this.subscribers.salvaOrdinanza = this.faseGiurisdizionaleOrdinanzaService
      .salvaOrdinanza(request)
      .subscribe(
        (data) => {
          this.router.navigate(
            [
              Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + data,
              { action: "SUCCESS" },
            ],
            { replaceUrl: true }
          );
        },
        (err) => {
          this.loaded = true;
          if (err instanceof ExceptionVO) {
            this.manageMessage(err);
          }
          this.scrollEnable = true;
          this.logger.error("Errore nel salvataggio dell'ordinanza");
        }
      );
  }
  
  salvaOrdinanzaAnnullamento() {
    this.loaded = false;
    let request: SalvaOrdinanzaRequest = new SalvaOrdinanzaRequest();

    if (this.isTipoAllegatoIngiunzione()) {
      request.idTipoAllegato = this.showAnnullamentoParts ?  Constants.ID_TIPO_ALLEGATO_INGIUNZIONE_ANNULLAMENTO : Constants.ID_TIPO_ALLEGATO_INGIUNZIONE;
    } else {
      request.idTipoAllegato = this.showAnnullamentoParts ?  Constants.ID_TIPO_ALLEGATO_ARCHIVIAZIONE_ANNULLAMENTO : Constants.ID_TIPO_ALLEGATO_ARCHIVIAZIONE;
      this.ordinanza.importoOrdinanza = null;
      this.ordinanza.dataScadenza = null;
    }

    //request.idTipoAllegato = this.salvaAllegatoRequest.idTipoAllegato;
    request.filename = this.salvaAllegatoRequest.filename;
    request.file = this.salvaAllegatoRequest.file;
    request.allegatoField = this.salvaAllegatoRequest.allegatoField;
    request.ordinanza = this.ordinanza;
    
    request.idOrdinanzaDaAnnullare = +this.idOrdinanza;
    request.soggetti = this.soggettiArray.map((x) => {
      let obj: SoggettoOrdinanzaRequest = new SoggettoOrdinanzaRequest();
      obj.idVerbaleSoggetto = x.idVerbaleSoggetto;
      obj.idTipoOrdinanza =
        x.idTipoOrdinanza != null ? x.idTipoOrdinanza.id : null;
      return obj;
    });
    request.notifica = this.insertNotifica.getNotificaObject();
    
    this.subscribers.salvaOrdinanza = this.faseGiurisdizionaleOrdinanzaService
      .salvaOrdinanzaAnnullamento(request)
      .subscribe(
        (data) => {
          this.router.navigate(
            [
              Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + data,
              { action: "SUCCESS" },
            ],
            { replaceUrl: true }
          );
        },
        (err) => {
          this.loaded = true;
          if (err instanceof ExceptionVO) {
            this.manageMessage(err);
          }
          this.scrollEnable = true;
          this.logger.error("Errore nel salvataggio dell'ordinanza");
        }
      );
  }

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  manageMessage(err: ExceptionVO) {
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
  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  ngOnDestroy(): void {
    this.logger.destroy(
      OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent.name
    );
  }

  /*
    settaCampiNotifica(notificaVO: NotificaVO):void{
        var marts = notificaVO;
    }
    */
}
