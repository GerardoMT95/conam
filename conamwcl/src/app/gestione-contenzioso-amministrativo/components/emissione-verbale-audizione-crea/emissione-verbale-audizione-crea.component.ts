import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { Routing } from "../../../commons/routing";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SelectVO } from "../../../commons/vo/select-vo";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "../../service/fase-giurisdizionale-verbale-audizione.service";
import { Constants } from "../../../commons/class/constants";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { saveAs } from "file-saver";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { RiepilogoAllegatoVO } from "../../../commons/vo/verbale/riepilogo-allegato-vo";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { NumeroRateDirective } from "../../../shared/directive/numero-rate.directive";
import { timer } from "rxjs/observable/timer";
import { THIS_EXPR } from "@angular/compiler/src/output/output_ast";

declare var $: any;

@Component({
  selector: "emissione-verbale-audizione-crea",
  templateUrl: "./emissione-verbale-audizione-crea.component.html",
})
export class EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};
  public tipoAllegatoModel: Array<TipoAllegatoVO> = new Array<TipoAllegatoVO>();

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;

  private intervalIdS: number = 0;
  public riepilogoVerbale: RiepilogoVerbaleVO;
  public riepilogoVerbaleAudizione: RiepilogoVerbaleVO;
  public configVerb: Config;
  public idAllegatoVerbaleAudizione: number;
  public verbale: Array<AllegatoVO>;

  config: Config;
  loaded: boolean;
  idVerbale: number;
  soggettiArray: Array<TableSoggettiVerbale>;
  idVerbaleSelect: number;
  allega: boolean;
  verbaleAudizioneTrovato: boolean = false;
  isVerbaleAudizione: boolean = true;
  viewTableAllegati: boolean;

  isSelectable: (el: TableSoggettiVerbale) => boolean = (
    el: TableSoggettiVerbale
  ) => {
    if (el.verbaleAudizioneCreato) return false;
    else return true;
  };

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedVerbaleConfigService: SharedVerbaleConfigService,
    private sharedVerbaleService: SharedVerbaleService,
    private faseGiurisdizionaleVerbaleAudizioneService: FaseGiurisdizionaleVerbaleAudizioneService,
    private allegatoSharedService: AllegatoSharedService,
    private verbaleService: SharedVerbaleService,
    private configSharedService: ConfigSharedService
  ) {}

  isSelectablePerAllegare: (el: TableSoggettiVerbale) => boolean = (
    el: TableSoggettiVerbale
  ) => {
    if (el.verbaleAudizioneCreato && el.idAllegatoVerbaleAudizione != null)
      return true;
    else return false;
  };

  ngOnInit(): void {
    this.logger.init(
      EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      let indietro = +params["flag"];
      if (indietro == 0) this.allega = true;
      else if (isNaN(this.idVerbale)) {
        this.allega = false;
        this.router.navigateByUrl(
          Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RICERCA
        );
      }
    });

    if (this.allega) {
      this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
        true,
        1,
        this.isSelectablePerAllegare,
        false
      );
      this.verbaleService
        .riepilogoVerbaleAudizione(this.idVerbale)
        .subscribe((data) => {
          this.riepilogoVerbale = data;
          this.verbale = this.riepilogoVerbale.allegati.verbale;
          this.riepilogoVerbale.allegati.verbale.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
          });

          this.riepilogoVerbaleAudizione = data;
          this.riepilogoVerbaleAudizione.allegati = this.riepilogoVerbale.allegati;
          this.riepilogoVerbaleAudizione.allegati.verbale = new Array<AllegatoVO>();
          this.loaded = true;
        });

      let tipoVerbale: TipoAllegatoVO = new TipoAllegatoVO();
      tipoVerbale.id = 10;
      tipoVerbale.denominazione = "Verbale di audizione";
      this.tipoAllegatoModel.push(tipoVerbale);

      this.configVerb = this.configSharedService.getConfigDocumentiVerbale(
        false
      );
    } else {
      this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
        true,
        1,
        this.isSelectable,
        false
      );
      this.loaded = true;
    }

    this.soggettiArray = new Array();
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    let request = new TipologiaAllegabiliRequest();
    request.id = this.idVerbale;
    request.tipoDocumento = Constants.VERBALE_AUDIZIONE;
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

  ngOnDestroy(): void {
    this.logger.destroy(
      EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent.name
    );
  }

  addToArraySoggettiSelezionati(e: Array<TableSoggettiVerbale>) {
    this.soggettiArray = e;

    if (this.allega) {
      this.cercaVerbale(e);
      this.riepilogoVerbaleAudizione = new RiepilogoVerbaleVO();
      this.riepilogoVerbaleAudizione.allegati = this.riepilogoVerbale.allegati;
      this.riepilogoVerbaleAudizione.allegati.verbale = new Array<AllegatoVO>();

      let j: number;
      this.viewTableAllegati = true;

      for (j = 0; j < this.verbale.length; j++) {
        if (this.verbale[j].id == this.idAllegatoVerbaleAudizione) {
          this.riepilogoVerbaleAudizione.allegati.verbale.push(this.verbale[j]);
          this.verbaleAudizioneTrovato = true;
        }
      }
    }
  }

  cercaVerbale(e: Array<TableSoggettiVerbale>): boolean {
    let i: number;
    let j: number;
    let flag: boolean = true;
    let soggetti: Array<SoggettoVO> = this.riepilogoVerbale.soggetto;
    for (j = 0; j < e.length && flag; j++) {
      for (i = 0; i < soggetti.length && flag; i++) {
        if (e[j].identificativoSoggetto == soggetti[i].codiceFiscale) {
          this.idAllegatoVerbaleAudizione =
            soggetti[i].idAllegatoVerbaleAudizione;
          flag = false;
        }
      }
    }

    return !flag;
  }

  notViewTableAllegati() {
    this.viewTableAllegati = false;
  }

  scrollEnable: boolean;
  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  goToEmittiVerbaleAudizione() {
    this.faseGiurisdizionaleVerbaleAudizioneService.idVerbaleSoggettoList = this.soggettiArray.map(
      (s) => s.idVerbaleSoggetto
    );
    this.router.navigateByUrl(
      Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_TEMPLATE_LETTERA +
        this.idVerbale
    );
  }

  getAllegato(el: AllegatoVO) {
    if (!el.id) return;
    this.logger.info("Richiesto download dell'allegato " + el.id);
    this.subscribers.allegatoDowload = this.allegatoSharedService
      .getAllegato(el.id)
      .subscribe((res) => saveAs(res, el.nome || "Unknown"));
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
    nuovoAllegato.idVerbale = this.idVerbale;
    nuovoAllegato.idVerbaleAudizione = this.idAllegatoVerbaleAudizione;
    //mando il file al Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.sharedVerbaleService
      .salvaAllegatoVerbale(nuovoAllegato)
      .subscribe(
        (data) => {
          this.loadTipoAllegato();
          this.router.navigate(
            [
              Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RIEPILOGO +
                this.idVerbale,
              { action: "caricato" },
            ],
            { replaceUrl: true }
          );
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.error("Errore nel salvataggio dell'allegato");
          this.loaded = true;
        }
      );
  }

  goBackToRiepilogo() {
    this.router.navigateByUrl(
      Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RIEPILOGO + this.idVerbale
    );
  }
}
