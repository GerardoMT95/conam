import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute } from "@angular/router";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { Config } from "protractor";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { VerbaleVO } from "../../../commons/vo/verbale/verbale-vo";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../commons/vo/messageVO";
import { TypeAlert } from "../../../shared/component/shared-alert/shared-alert.component";
import { verbaliRecidivoVO } from "../../../commons/vo/verbale/verbali-recidivo-vo";

@Component({
  selector: "recitivita-dettaglio",
  templateUrl: "./recitivita-dettaglio.component.html",
  styleUrls: ["./recitivita-dettaglio.component.scss"],
})
export class RecidivitaDettaglioComponent implements OnInit, OnDestroy {
  public idSoggetto: number;
  public subscribers: any = {};
  public loaded: boolean = true;
  public soggetto: SoggettoVO;
  public soggettiVerbale: any;
  public config: Config;
  public verbaliSelected: verbaliRecidivoVO[];

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;
  constructor(
    private logger: LoggerService,
    private activatedRoute: ActivatedRoute,
    private verbaleService: VerbaleService,
    private sharedVerbaleConfigService: SharedVerbaleConfigService
  ) {}

  ngOnInit(): void {
    this.logger.init(RecidivitaDettaglioComponent.name);
    this.loaded = false;
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idSoggetto = +params["id"];
      this.getVerbaliSoggetto(this.idSoggetto);
    });

    this.config = this.sharedVerbaleConfigService.getConfigRecidivitaVerbali(
      true,
      1,
      (el: any) => true,
      null,
      null
    );
  }

  getVerbaliSoggetto(idSoggetto: number) {
    
    this.verbaleService
      .getVerbaleSoggettoByIdSoggetto(idSoggetto)
      .subscribe((data) => {
        if (data) {
          this.soggetto = data.soggetto;
          for (let i in data.soggettiVerbale) {
            data.soggettiVerbale[i].ruolo = data.ruoloSoggetto.denominazione;
            if (data.soggettiVerbale[i].recidivo) {
              data.soggettiVerbale[i].residivoString = "SI";
            } else {
              data.soggettiVerbale[i].residivoString = "NO";
            }
          }

          this.soggettiVerbale = data.soggettiVerbale;
          this.loaded = true;
        } else {
          this.soggettiVerbale;
          this.loaded = true;
        }
      });
  }

  save() {
    this.loaded = false;
    let lista = this.verbaliSelected.map(function (num) {
      return  {
        id: num.id,
        recidivo: !num.recidivo,
      };
    });
    let request = {lista}
    this.verbaleService
      .setRecidivoVerbaleSoggetto(request)
      .subscribe((data) => {
        this.manageMessage({
          type: TypeAlert.SUCCESS,
          message: "Stati di recidività aggiornati con successo",
        });
        this.getVerbaliSoggetto(this.idSoggetto);   
      },
      (err) => {
        this.manageMessage({
          type: TypeAlert.DANGER,
          message: "Errore nell'aggiornamento degli stati di recidività",
        });
      });
  }

  onSelected(el: Array<verbaliRecidivoVO>) {
    this.verbaliSelected = el;
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
    this.logger.destroy(RecidivitaDettaglioComponent.name);
  }
}
