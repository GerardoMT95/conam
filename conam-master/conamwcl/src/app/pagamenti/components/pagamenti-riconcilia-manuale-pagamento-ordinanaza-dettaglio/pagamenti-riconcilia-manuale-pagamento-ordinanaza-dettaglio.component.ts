import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { AzioneOrdinanzaResponse } from "../../../commons/response/ordinanza/azione-ordinanza-response";
import { SharedOrdinanzaRiepilogoComponent } from "../../../shared-ordinanza/component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Config } from "protractor";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { AccontoVO } from "../../../commons/vo/ordinanza/acconto-vo";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { AccontoOrdinanzaRequest } from "../../../commons/request/ordinanza/acconto-ordinanza-request";
import { TypeAlert } from "../../../shared/component/shared-alert/shared-alert.component";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../commons/vo/messageVO";
declare var $: any;
@Component({
  selector: "pagamenti-riconcilia-manuale-pagamento-ordinanaza-dettaglio",
  templateUrl:
    "./pagamenti-riconcilia-manuale-pagamento-ordinanaza-dettaglio.component.html",
})
export class pagamentiRiconciliaManualePagamentoOrdinanazaDettaglioComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};
  acconto: AccontoVO;
  request: AccontoOrdinanzaRequest;
  idOrdinanza: number;
  idVerbale: number = 0;
  azione: AzioneOrdinanzaResponse;
  loadedAction: boolean;
  loaded: boolean;
  isRichiestaBollettiniSent: boolean;
  isFirstDownloadBollettini: boolean;
  selected: TableSoggettiOrdinanza[];
  @ViewChild(SharedOrdinanzaRiepilogoComponent)
  sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;

  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  public configSoggetti: Config;
  public uploader: ElementRef;
  public senzaAllegati: boolean = true;
  public listaAcconti: any;
  public nuovoAllegato: SalvaAllegatoRequest;
  private intervalIdS: number = 0;
  constructor(
    private logger: LoggerService,
    private activatedRoute: ActivatedRoute,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private sharedVerbaleService: SharedVerbaleService,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) {}

  ngOnInit(): void {
    this.logger.init(
      pagamentiRiconciliaManualePagamentoOrdinanazaDettaglioComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idOrdinanza = +params["id"];
    });
    this.getVerbaleByIdOrdinanza();
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      true,
      null,
      0,
      null,
      null,
      (el: any) => false,
      false
    );
    this.acconto = new AccontoVO();
  }

  getVerbaleByIdOrdinanza() {
    this.subscribers.callAzioneVerbale = this.sharedVerbaleService
      .getVerbaleByIdOrdinanza(this.idOrdinanza)
      .subscribe((data) => {
        this.idVerbale = data.id;
        this.loaded = true;
        this.listaAcconti = data;
      });
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }
  onSelected(event) {
    this.selected = event;
    this.acconto.idSoggetto = event.idSoggetto;
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
          this.acconto.dataPagamento = event.srcElement.value;
          break;
      }
    }
  }

  salvaAcconto() {
    this.loaded =  false;
    this.acconto.idOrdinanza = this.idOrdinanza;
    let request = new AccontoOrdinanzaRequest();
    request.acconto = this.acconto;
 
    if (this.nuovoAllegato) {
      request.allegatoField = this.nuovoAllegato.allegatoField;
      request.file = this.nuovoAllegato.file;
      request.filename = this.nuovoAllegato.filename;
    }
    this.sharedOrdinanzaService.salvaAcconto(request).subscribe(
      (data) => {
        this.loaded =  true;
        this.selected = null;
        this.acconto = new AccontoVO();
        this.manageMessage({
          type: TypeAlert.SUCCESS,
          message: "Acconto salveto con successo",
        });
      },
      (err) => {
        this.manageMessage({
          type: TypeAlert.DANGER,
          message: "Errore durante il salvataggio",
        });
      }
    );
  }

  addFile() {
    this.logger.info("=> Add file");
    this.uploader.nativeElement.click();
    this.senzaAllegati = false;
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoRequest) {
    this.nuovoAllegato = nuovoAllegato;
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

  ngOnDestroy(): void {
    this.logger.destroy(
      pagamentiRiconciliaManualePagamentoOrdinanazaDettaglioComponent.name
    );
  }
}
