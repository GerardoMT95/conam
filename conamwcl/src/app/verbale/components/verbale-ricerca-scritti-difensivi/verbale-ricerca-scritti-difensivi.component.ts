import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {
  RegioneVO,
  ProvinciaVO,
  ComuneVO,
  NormaVO,
  ArticoloVO,
  CommaVO,
  LetteraVO,
  EnteVO,
  SelectVO,
  AmbitoVO,
} from "../../../commons/vo/select-vo";
import { RifNormativiService } from "../../services/rif-normativi.service";
import { UserService } from "../../../core/services/user.service";
import { VerbaleService } from "../../services/verbale.service";
import { VerbaleVO } from "../../../commons/vo/verbale/verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RiferimentiNormativiVO } from "../../../commons/vo/verbale/riferimenti-normativi-vo";
import { LuoghiService } from "../../../core/services/luoghi.service";
import { DOCUMENT } from "@angular/platform-browser";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { Config } from "protractor";
import { MinVerbaleVO } from "../../../commons/vo/verbale/min-verbale-vo";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { RicercaScrittoDifensivoRequest } from "../../../commons/request/verbale/ricerca-scritto-difensivo-request";
import { ScrittoDifensivoVO } from "../../../commons/vo/verbale/scritto-difensivo-vo";
import { TableScrittiDifensiviVerbale } from "../../../commons/table/table-scritti-difensivi-verbale";

declare var $: any;

@Component({
  selector: "verbale-ricerca-scritti-difensivi",
  templateUrl: "./verbale-ricerca-scritti-difensivi.component.html",
})
export class VerbaleRicercaScrittiDifensiviComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public showTable: boolean;

  public config: Config;
  public scrittiDifensivi: Array<TableScrittiDifensiviVerbale> = new Array<TableScrittiDifensiviVerbale>();

  public scrittiDifensivoSel: TableScrittiDifensiviVerbale;
  public loaded: boolean = true;

  public request: RicercaScrittoDifensivoRequest;

  constructor(
    private logger: LoggerService,
    private configSharedService: ConfigSharedService,
    private verbaleService: VerbaleService,
    private router: Router,
    @Inject(DOCUMENT) private document: Document
 
  ) {}

  ngOnInit(): void {
    
    this.logger.init(VerbaleRicercaScrittiDifensiviComponent.name);
    this.config = this.configSharedService.configRicercaScrittoDifensivo;
    this.scrittiDifensivi = new Array();
  }

  scrollEnable: boolean;
  ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest: RicercaScrittoDifensivoRequest) {
    this.request = ricercaScrittoDifensivoRequest;
    this.request.tipoRicerca = 'RICERCA_SCRITTO';
    this.showTable = false;
    this.loaded = false;
    this.verbaleService.ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest).subscribe(
      (data) => {
        if (data != null){
          this.scrittiDifensivi = data.map((value) => {
            return TableScrittiDifensiviVerbale.map(value);
          });
          this.showTable = true;
        }
        this.loaded = true;
        this.scrollEnable = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.loaded = true;
          this.manageMessage(err);
        }
        this.scrollEnable = true;
        this.logger.error("Errore nella ricerca di scritti difensivi");
      }
    );
  }

  onDettaglio(el: any | Array<any>) {
    this.scrittiDifensivoSel = el;
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else this.router.navigateByUrl(Routing.VERBALE_SCRITTO_DIFENSIVO + el.id);
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

  ngAfterContentChecked() {
    let out: HTMLElement = document.getElementById("scrollBottom");
    if (this.loaded && this.scrollEnable && out != null && this.showTable) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }
  ngOnDestroy(): void {
    this.logger.destroy(VerbaleRicercaScrittiDifensiviComponent.name);
  }
}
