import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  Sanitizer,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute } from "@angular/router";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { Template04RateizzazioneComponent } from "../../../template/components/template-04-rateizzazione/template-04-rateizzazione.component";
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { Location } from "@angular/common";
import { DomSanitizer } from "@angular/platform-browser";
import { SharedPagamentiService } from "../../../shared-pagamenti/services/shared-pagamenti.service";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";
import { saveAs } from "file-saver";

@Component({
  selector: "pagamenti-template",
  templateUrl: "./pagamenti-template.component.html",
  styleUrls: ["./pagamenti-template.component.scss"],
})
export class PagamentiTemplateComponent implements OnInit, OnDestroy {
  @ViewChild(Template04RateizzazioneComponent)
  template: Template04RateizzazioneComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public isAnteprima: boolean = false;
  public isPrinted: boolean = false;
  public scrollEnable: boolean;

  public idPiano: number;

  public datiTemplateModel: DatiTemplateVO;
  public datiCompilati: DatiTemplateCompilatiVO;

  public url: string;

  //Messaggio Top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

  visualizzaAnteprimaValidIntestazione: boolean;
  visualizzaAnteprimaValidTemplate: boolean = false;

  constructor(
    private logger: LoggerService,
    //private router: Router,
    private activatedRoute: ActivatedRoute,
    private templateService: TemplateService,
    private location: Location,
    private sanitizer: DomSanitizer,
    private sharedPagamentiService: SharedPagamentiService
  ) //private userService: UserService,
  {}

  ngOnInit(): void {
    this.logger.init(PagamentiTemplateComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idPiano = +params["id"];

      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
      request.idPiano = this.idPiano;
      this.subscribers.getTemplate = this.templateService
        .getDatiTemplate(request)
        .subscribe(
          (data) => {
            this.datiTemplateModel = data;
            this.scrollEnable = true;
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
    this.isAnteprima = true;
    this.datiTemplateModelStampa = this.template.getDatiCompilati();
    this.template.setAnteprima(true);
    this.scrollEnable = true;
  }

  proseguiModifica() {
    this.isAnteprima = false;
    this.template.setAnteprima(false);
    this.scrollEnable = true;
    this.isStampa = false;
    this.template.setStampa(false);
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
      request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
      request.idPiano = this.idPiano;
      request.datiTemplateCompilatiVO = tmp;
      let isCreatedVO: IsCreatedVO = new IsCreatedVO();
      isCreatedVO.id = this.idPiano;
      this.subscribers.isLetteraSaved = this.sharedPagamentiService
        .isLetteraPianoSaved(isCreatedVO)
        .subscribe((data) => {
          if (!data.isCreated) {
            this.subscribers.stampa = this.templateService
              .stampaTemplate(request)
              .subscribe(
                (data) => {
                  //saveAs(data, 'Lettera Piano Rateizzazione.pdf');
                  //this.url = URL.createObjectURL(data);
                  // this.isPrinted = true;
                  this.scrollEnable = true;
                  //this.loaded = true;
                  this.isStampaProtocollata = true;
                  this.manageMessageTop(
                    "Lettera di rateizzazione creata con successo.",
                    "SUCCESS"
                  );
                },
                (err) => {
                  this.logger.error("Errore durante il download del PDF");
                  // this.loaded = true;
                  this.scrollEnable = true;
                }
              );
          } else {
            this.manageMessageTop(
              "La lettera di rateizzazione del piano a è già stata creata.",
              "DANGER"
            );
            this.isAnteprima = false;
            // this.loaded = true;
            this.scrollEnable = true;
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
    request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
    request.idPiano = this.idPiano;

    let nome: string;
    this.templateService.nomiTemplate(request).subscribe(
      (data) => {
        nome = data.nome;

        this.templateService.downloadTemplate(request).subscribe(
          (data1) => {
            saveAs(data1, nome);
            this.loaded = true;
            this.location.back();
          },
          (err) => {
            this.logger.error("Errore durante il download del PDF");
            this.loaded = true;
          }
        );
      },
      (err) => {
        this.logger.error("Errore durante il download del PDF");
        this.loaded = true;
      }
    );
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
    this.logger.destroy(PagamentiTemplateComponent.name);
  }
}
