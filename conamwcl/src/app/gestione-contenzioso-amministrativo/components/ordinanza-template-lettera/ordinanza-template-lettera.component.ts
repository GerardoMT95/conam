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
import { DomSanitizer } from "@angular/platform-browser";
import { saveAs } from "file-saver";
import { Template03LetteraAccompagnamentoComponent } from "../../../template/components/template-03-lettera-accompagnamento/template-03-lettera-accompagnamento.component";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";
import { Routing } from "../../../commons/routing";
import { Template09LetteraAccompagnamentoAnnullamentoComponent } from "../../../template/components/template-09-lettera-accompagnamento-annullamento/template-09-lettera-accompagnamento-annullamento.component";
import { Template10LetteraAccompagnamentoArchiviazioneComponent } from "../../../template/components/template-10-lettera-accompagnamento-archiviazione/template-10-lettera-accompagnamento-archiviazione.component";

@Component({
  selector: "ordinanza-template-lettera",
  templateUrl: "./ordinanza-template-lettera.component.html",
})
export class OrdinanzaTemplateLetteraGestContAmministrativoComponent
  implements OnInit, OnDestroy {
  @ViewChild(Template03LetteraAccompagnamentoComponent)
  template: Template03LetteraAccompagnamentoComponent;
  @ViewChild(Template09LetteraAccompagnamentoAnnullamentoComponent)
  templateAnnullamento: Template09LetteraAccompagnamentoAnnullamentoComponent;
  @ViewChild(Template10LetteraAccompagnamentoArchiviazioneComponent)
  templateArchiviazione: Template10LetteraAccompagnamentoArchiviazioneComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public isAnteprima: boolean = false;
  public isPrinted: boolean = false;
  public scrollEnable: boolean;

  public idOrdinanza: number;

  public datiTemplateModel: DatiTemplateVO;
  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  public datiCompilati: DatiTemplateCompilatiVO;

  public url: string;

  //Messaggio Top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;
  private intervalIdS: number = 0;
  public itsLetteraAnnullamento:  boolean;
  public itsLetteraArchiviazione: boolean;
  public visualizzaAnteprimaValidTemplate: boolean = false;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private templateService: TemplateService,
    private location: Location,
    private sanitizer: DomSanitizer,
    private sharedOrdinanzaService: SharedOrdinanzaService
  ) //private userService: UserService,
  {}

  ngOnInit(): void {
    this.itsLetteraArchiviazione = false;
    this.logger.init(
      OrdinanzaTemplateLetteraGestContAmministrativoComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
  
      this.idOrdinanza = +params["id"];
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      if(params.azione === 'annullamento'){
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO;
        this.itsLetteraAnnullamento = true
      }
      else if(params.azione === 'archiviazione'){
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE;
        this.itsLetteraArchiviazione = true
      }
      else{
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO;  
        this.itsLetteraAnnullamento = false
      }
      
      request.idOrdinanza = this.idOrdinanza;
     
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
    if(this.itsLetteraAnnullamento){
      this.datiTemplateModelStampa = this.templateAnnullamento.getDatiCompilati();
      this.isAnteprima = true;
      this.templateAnnullamento.setAnteprima(true);
      this.scrollEnable = true;
    }
    else if (this.itsLetteraArchiviazione && !this.itsLetteraAnnullamento){
      this.datiTemplateModelStampa = this.templateArchiviazione.getDatiCompilati();
      this.isAnteprima = true;
      this.templateArchiviazione.setAnteprima(true);
      this.scrollEnable = true;
    }
    else{
      this.datiTemplateModelStampa = this.template.getDatiCompilati();
      this.isAnteprima = true;
      this.template.setAnteprima(true);
      this.scrollEnable = true;
    }
  }

  proseguiModifica() {
    if(this.itsLetteraAnnullamento){
      this.isAnteprima = false;
      this.isStampa = false;
      this.templateAnnullamento.setAnteprima(false);
      this.templateAnnullamento.setStampa(false);
      this.scrollEnable = true;
    }
    else if (this.itsLetteraArchiviazione && !this.itsLetteraAnnullamento){
      this.isAnteprima = false;
      this.isStampa = false;
      this.templateArchiviazione.setAnteprima(false);
      this.templateArchiviazione.setStampa(false);
      this.scrollEnable = true;
    }
    else{
      this.isAnteprima = false;
      this.isStampa = false;
      this.template.setAnteprima(false);
      this.template.setStampa(false);
      this.scrollEnable = true;
    }
 
  }

  public isStampa: boolean = false;
  public isStampaProtocollata: boolean = false;
  public loadedScarica: boolean = false;

  stampaPDF() {
    let tmp: DatiTemplateCompilatiVO
    this.isPrinted = true;
    this.isStampa = true;
    
    if(this.itsLetteraAnnullamento){
      this.templateAnnullamento.setStampa(true);
      this.templateAnnullamento.setDatiCompilati(this.datiTemplateModelStampa); 
       tmp = this.templateAnnullamento.getDatiCompilati()
    }
    else if (this.itsLetteraArchiviazione && !this.itsLetteraAnnullamento){
      this.templateArchiviazione.setStampa(true);
      this.templateArchiviazione.setDatiCompilati(this.datiTemplateModelStampa); 
       tmp = this.templateArchiviazione.getDatiCompilati()
    }
    else{
      this.template.setStampa(true);
      this.template.setDatiCompilati(this.datiTemplateModelStampa);
      tmp = this.template.getDatiCompilati()
    }
   

    
    this.resetMessageTop();
    if (this.checkDatiTemplate(tmp)) {
      //this.loaded = false;
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      if(this.itsLetteraAnnullamento){
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO;
      }
      else if(this.itsLetteraArchiviazione && !this.itsLetteraAnnullamento){
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE;
        this.itsLetteraArchiviazione = true
      }
      else{
        request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO;
      }     
      request.idOrdinanza = this.idOrdinanza;
      request.datiTemplateCompilatiVO = tmp;
      let isCreatedVO: IsCreatedVO = new IsCreatedVO();
      isCreatedVO.id = this.idOrdinanza;
      this.subscribers.isLetteraSaved = this.sharedOrdinanzaService
        .isLetteraSaved(isCreatedVO)
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
                    "Lettera accompagnatoria creata con successo.",
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
              "La lettera accompagnatoria dell'ordinanza è già stata creata.",
              "DANGER"
            );
            this.isAnteprima = false;
            //this.loaded = true;
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
    if(this.itsLetteraAnnullamento){
      request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO;
    }
    else if(this.itsLetteraArchiviazione && !this.itsLetteraAnnullamento){
      request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE;
      this.itsLetteraArchiviazione = true
    }
    else{
      request.codiceTemplate = Constants.TEMPLATE_LETTERA_ACCOMPAGNAMENTO;
    }
    request.idOrdinanza = this.idOrdinanza;
    let nome: string;
    this.templateService.nomiTemplate(request).subscribe((data) => {
      nome = data.nome;
      this.templateService.downloadTemplate(request).subscribe(
        (data) => {
          saveAs(data, nome);
          this.loaded = true;
          this.router.navigateByUrl(
            Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + this.idOrdinanza
          );
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
    this.logger.destroy(
      OrdinanzaTemplateLetteraGestContAmministrativoComponent.name
    );
  }
}
