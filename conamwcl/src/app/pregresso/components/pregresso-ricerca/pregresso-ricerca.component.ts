import { Component, OnInit, OnDestroy, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { MinVerbaleVO } from "../../../commons/vo/verbale/min-verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { DOCUMENT } from "@angular/platform-browser";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

@Component({
  selector: "pregresso-ricerca",
  templateUrl: "./pregresso-ricerca.component.html",
  styleUrls: ["./pregresso-ricerca.component.scss"],
})
export class PregressoRicercaComponent implements OnInit, OnDestroy {
  public showTable: boolean;

  public config: Config;
  public verbali: Array<MinVerbaleVO>;
  public verbaleSel: MinVerbaleVO;
  public loaded: boolean = true;

  public request: RicercaVerbaleRequest;

  constructor(
    private logger: LoggerService,
    private configSharedService: ConfigSharedService,
    private sharedVerbaleService: SharedVerbaleService,
    private router: Router,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoRicercaComponent.name);
    this.config = this.configSharedService.configRicercaVerbale;
    this.verbali = new Array();
  }

  scrollEnable: boolean;
  ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
    this.request = ricercaVerbaleRequest;
    this.showTable = false;
    this.loaded = false;
    ricercaVerbaleRequest.datiVerbale.pregresso = true;
    this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
      (data) => {
        if (data != null) this.verbali = data;
        this.showTable = true;
        this.loaded = true;
        this.scrollEnable = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.loaded = true;
          this.manageMessage(err);
        }
        this.scrollEnable = true;
        this.logger.error("Errore nel salvataggio del verbale");
      }
    );
  }

  onDettaglio(el: any | Array<any>) {
    this.verbaleSel = el;
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else if (this.verbaleSel.modificabile)
      this.router.navigateByUrl(Routing.PREGRESSO_DATI + el.id);
    else this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO + el.id);
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
    this.logger.destroy(PregressoRicercaComponent.name);
  }
}
