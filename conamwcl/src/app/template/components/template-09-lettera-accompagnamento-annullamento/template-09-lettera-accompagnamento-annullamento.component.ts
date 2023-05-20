import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  ViewChild,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UserService } from "../../../core/services/user.service";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";

import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";
import { DatiTemplateAnnullamentoVO } from "../../../commons/vo/template/dati-template-annullamento-vo";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

@Component({
  selector: "template-09-lettera-accompagnamento-annullamento",
  templateUrl:
    "./template-09-lettera-accompagnamento-annullamento.component.html",
  styleUrls: [
    "./template-09-lettera-accompagnamento-annullamento.component.scss",
  ],
})
export class Template09LetteraAccompagnamentoAnnullamentoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public funzionario: string;
  public dataOdierna: string;
  //gestione anteprima
  public isAnteprima: boolean;
  public isStampa: boolean;
  //dati precompilati
  @Input()
  data: DatiTemplateVO;

  //output
  @Output()
  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;
  public viewMailTributiField: boolean;
  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  public nomeCognomeSogg: string;
  public indirizzoSogg: string;
  public oggettoLetteraRigaUno: string;
  public oggettoLetteraRigaDue: string;
  public oggettoLetteraRigaTre: string;
  public sessoSogg: string;

  //form view child
  @ViewChild("formTemplate")
  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)
  intestazione: SharedTemplateIntestazioneComponent;

  constructor(
    private logger: LoggerService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
        this.logger.init(
      Template09LetteraAccompagnamentoAnnullamentoComponent.name
    );
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {
          this.funzionario = user.nome + " " + user.cognome;
        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(this.formTemplate.valid);
    });
    if (this.data.mailSettoreTributi != null) {
      this.viewMailTributiField = true;
    } else {
      this.viewMailTributiField = false;
    }
    this.dataOdierna = new Date().toLocaleDateString();

    this.popolaCampi();
  }
  splitterMetod() {
    let sedeEnteSplited = this.data.sedeEnte.split(";");
    this.datiCompilati.datiLetteraAnnullamento.sedeEnteRiga1 =
      sedeEnteSplited[0];
    this.datiCompilati.datiLetteraAnnullamento.sedeEnteRiga2 =
      sedeEnteSplited[1];
    this.datiCompilati.datiLetteraAnnullamento.sedeEnteRiga3 =
      sedeEnteSplited[2];
    let infoOrganoAccertatoreSplited = this.data.infoOrganoAccertatore.split(
      ";"
    );
    this.datiCompilati.indirizzoOrganoAccertatoreRiga1 =
      infoOrganoAccertatoreSplited[0];
    this.datiCompilati.indirizzoOrganoAccertatoreRiga2 =
      infoOrganoAccertatoreSplited[1];
    this.datiCompilati.indirizzoOrganoAccertatoreRiga3 =
      infoOrganoAccertatoreSplited[2];
  }
  popolaCampi() {
    this.datiCompilati.datiLetteraAnnullamento.direzioneLettera = this.data.direzione;
    this.datiCompilati.datiLetteraAnnullamento.settoreLettera = this.data.settore;
    this.datiCompilati.datiLetteraAnnullamento.mailLettera = this.data.mailSettoreTributi;
    this.datiCompilati.datiLetteraAnnullamento.dataLettera = this.dataOdierna;

    this.datiCompilati.oggettoLettera = this.data.oggettoLettera;
    this.datiCompilati.corpoLettera =
      "Si trasmette in allegato copia conforme del provvedimento relativo all'oggetto.";
    this.datiCompilati.datiLetteraAnnullamento.salutiLettera =
      " Cordiali saluti.";
    this.datiCompilati.datiLetteraAnnullamento.dirigenteLettera = this.data.dirigenteLettera;
    this.datiCompilati.datiLetteraAnnullamento.inizialiLettera = this.data.funzionario;
    this.datiCompilati.datiLetteraAnnullamento.bloccoFirmaOmessa = this.data.dirigente;
  
    this.splitterMetod();
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
  }
  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
  }

  getDatiCompilati(): DatiTemplateCompilatiVO {
    return this.datiCompilati;
  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {
    this.datiCompilati = dati;
  }


  ngOnDestroy(): void {
    this.logger.destroy(
      Template09LetteraAccompagnamentoAnnullamentoComponent.name
    );
  }
}
