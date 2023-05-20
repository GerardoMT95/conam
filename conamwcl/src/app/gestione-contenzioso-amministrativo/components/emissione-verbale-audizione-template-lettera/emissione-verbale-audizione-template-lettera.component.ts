import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  Sanitizer,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { Location } from "@angular/common";
import { saveAs } from "file-saver";
import { DomSanitizer } from "@angular/platform-browser";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "../../service/fase-giurisdizionale-verbale-audizione.service";
import { Template02VerbaleAudizioneComponent } from "../../../template/components/template-02-verbale-audizione/template-02-verbale-audizione.component";
import { Routing } from "../../../commons/routing";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";

@Component({
  selector: "emissione-verbale-audizione-template-lettera",
  templateUrl: "./emissione-verbale-audizione-template-lettera.component.html",
})
export class EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent
  implements OnInit, OnDestroy {
  @ViewChild(Template02VerbaleAudizioneComponent)
  template: Template02VerbaleAudizioneComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public isAnteprima: boolean = false;
  public isPrinted: boolean = false;
  public scrollEnable: boolean;

  public idVerbale: number;

  public datiTemplateModel: DatiTemplateVO;
  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  public datiCompilati: DatiTemplateCompilatiVO;

  public url: string;

  //Messaggio Top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;

  visualizzaAnteprimaValidTemplate: boolean = false;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private templateService: TemplateService,
    private location: Location,
    private sanitizer: DomSanitizer,
    private faseGiurisdizionaleVerbaleAudizioneService: FaseGiurisdizionaleVerbaleAudizioneService
  ) //private userService: UserService,
  {}

  ngOnInit(): void {
    this.logger.init(
      EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_VERBALE_AUDIZIONE;
      let soggetti = this.faseGiurisdizionaleVerbaleAudizioneService
        .idVerbaleSoggettoList;
      if (soggetti == null || soggetti.length == 0)
        this.router.navigateByUrl(
          Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_CREA + this.idVerbale
        );
      else {
        request.idVerbaleSoggettoList = this.faseGiurisdizionaleVerbaleAudizioneService.idVerbaleSoggettoList;
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
      }
    });
  }

  indietro() {
    this.router.navigateByUrl(
      Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_ALLEGA +
        "0/" +
        this.idVerbale
    );
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
    //this.loaded = false;
    let request: DatiTemplateRequest = new DatiTemplateRequest();
    request.codiceTemplate = Constants.TEMPLATE_VERBALE_AUDIZIONE;
    request.idVerbaleSoggettoList = this.faseGiurisdizionaleVerbaleAudizioneService.idVerbaleSoggettoList;
    request.datiTemplateCompilatiVO = tmp;
    let isCreatedVO: IsCreatedVO = new IsCreatedVO();
    isCreatedVO.ids = request.idVerbaleSoggettoList;

    this.subscribers.stampa = this.templateService
      .stampaTemplate(request)
      .subscribe(
        (data) => {
          //this.url = URL.createObjectURL(data);

          this.scrollEnable = true;
          this.loadedScarica = true;
          this.isStampaProtocollata = true;
          this.manageMessageTop(
            "Verbale di audizione creato con successo.",
            "SUCCESS"
          );
        },
        (err) => {
          this.logger.error("Errore durante il download del PDF");
          this.scrollEnable = true;
          this.isStampa = true;
          this.loadedScarica = false;
          this.isPrinted = false;
        }
      );
  }

  scarica() {
    this.loaded = false;
    let request: DatiTemplateRequest = new DatiTemplateRequest();
    request.codiceTemplate = Constants.TEMPLATE_VERBALE_AUDIZIONE;
    request.idVerbaleSoggettoList = this.faseGiurisdizionaleVerbaleAudizioneService.idVerbaleSoggettoList;

    let nome: string;
    this.templateService.nomiTemplate(request).subscribe(
      (data) => {
        nome = data.nome;

        this.templateService.downloadTemplate(request).subscribe(
          (data1) => {
            saveAs(data1, nome);
            this.loaded = true;
            this.router.navigateByUrl(
              Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RIEPILOGO +
                this.idVerbale
            );
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

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
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
    this.logger.destroy(
      EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent.name
    );
  }
}
