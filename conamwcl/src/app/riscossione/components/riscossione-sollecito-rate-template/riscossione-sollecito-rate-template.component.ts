import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Router } from "@angular/router";
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { Location } from "@angular/common";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";
import { saveAs } from "file-saver";

import { Template11SollecitoPagamentoRateComponent } from "../../../template/components/template-11-lettera-sollecito-pagamento-rate/template-11-lettera-sollecito-pagamento-rate.component";

@Component({
  selector: "riscossione-sollecito-rate-template",
  templateUrl: "./riscossione-sollecito-rate-template.component.html",
})
export class RiscossioneSollecitoRateTemplateComponent
  implements OnInit, OnDestroy {
  @ViewChild(Template11SollecitoPagamentoRateComponent)
  template: Template11SollecitoPagamentoRateComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public isAnteprima: boolean = false;
  public isPrinted: boolean = false;
  public scrollEnable: boolean;

  public idSollecito: number;

  public datiTemplateModel: DatiTemplateVO;
  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  public datiCompilati: DatiTemplateCompilatiVO;

  public url: string;

  //Messaggio Top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  visualizzaAnteprimaValidIntestazione: boolean;
  visualizzaAnteprimaValidTemplate: boolean;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private templateService: TemplateService,
    private location: Location,
    private sanitizer: DomSanitizer,
    private sharedRiscossioneService: SharedRiscossioneService
  ) //private userService: UserService,
  {}

  ngOnInit(): void {
    this.logger.init(RiscossioneSollecitoRateTemplateComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idSollecito = +params["id"];

      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
      request.idSollecito = this.idSollecito;
      this.subscribers.getTemplate = this.templateService
        .getDatiTemplate(request)
        .subscribe(
          (data) => {
            this.datiTemplateModel = data;
            this.loaded = true;
          },
          (err) => {
            this.logger.error("Errore durante il recupero dei dati");
            this.loaded = true;
          }
        );
    });
  }

  indietro() {
    this.location.back();
  }

  visualizzaAnteprima() {
    this.datiTemplateModelStampa = this.template.getDatiCompilati();
    this.isAnteprima = true;
    this.template.setAnteprima(true);
    this.scrollEnable = true;
  }

  proseguiModifica() {
    this.isAnteprima = false;
    this.isStampa = false;
    this.template.setAnteprima(false);
    this.template.setStampa(false);
    this.scrollEnable = true;
  }

  public isStampa: boolean = false;
  public isStampaProtocollata: boolean = false;
  public loadedScarica: boolean = false;
  stampaPDF() {
    this.isPrinted = true;
    this.isStampa = true;

    this.template.setStampa(true);
    this.template.setDatiCompilati(this.datiTemplateModelStampa);

    let tmp: DatiTemplateCompilatiVO = this.template.getDatiCompilati();
    this.resetMessageTop();
    if (this.checkDatiTemplate(tmp)) {
      //this.loaded = false;
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
      request.idSollecito = this.idSollecito;
      request.datiTemplateCompilatiVO = tmp;
      let isCreatedVO: IsCreatedVO = new IsCreatedVO();
      isCreatedVO.id = this.idSollecito;
      this.subscribers.isLetteraSaved = this.sharedRiscossioneService
        .isLetteraSollecitoSaved(isCreatedVO)
        .subscribe((data) => {
          if (!data.isCreated) {
            this.subscribers.stampa = this.templateService
              .stampaTemplate(request)
              .subscribe(
                (data) => {
                  //this.url = URL.createObjectURL(data);
                  this.scrollEnable = true;
                  this.loadedScarica = true;
                  this.isStampaProtocollata = true;
                  this.manageMessageTop(
                    "Lettera del sollecito creata con successo",
                    "SUCCESS"
                  );
                },
                (err) => {
                  this.logger.error("Errore durante il download del PDF");
                  //this.loaded = true;
                  this.scrollEnable = true;
                  this.isStampa = true;
                  this.loadedScarica = false;
                  this.isPrinted = false;
                }
              );
          } else {
            this.manageMessageTop(
              "La lettera del sollecito è già stata creata.",
              "DANGER"
            );
            this.isAnteprima = false;
            //this.loaded = true;
            this.scrollEnable = true;
            this.isStampa = true;
            this.loadedScarica = false;
            this.isPrinted = false;
          }
        });
    } else {
      this.manageMessageTop("Compilare tutti i campi obbligatori", "WARNING");
      this.scrollEnable = true;
    }
  }

  scarica() {
    this.loaded = false;
    let request: DatiTemplateRequest = new DatiTemplateRequest();
    request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
    request.idSollecito = this.idSollecito;
    let nome: string;
    this.templateService.nomiTemplate(request).subscribe((data) => {
      nome = data.nome;
      this.templateService.downloadTemplate(request).subscribe(
        (data) => {
          saveAs(data, nome);
          this.loaded = true;
          this.location.back();
        },
        (err) => {
          this.logger.error("Errore durante il download del PDF");
          this.loaded = true;
        }
      );
    });
  }

  checkDatiTemplate(dati: DatiTemplateCompilatiVO): boolean {
    let flag: boolean = true;
    for (let field in dati) {
      if (!dati[field]) flag = false;
    }
    return flag;
  }

  manageMessageTop(message: string, type: string) {
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

  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  setFormValidTemplate(event: boolean) {
    this.visualizzaAnteprimaValidTemplate = event;
  }

  ngOnDestroy(): void {
    this.logger.init(RiscossioneSollecitoRateTemplateComponent.name);
  }
}
