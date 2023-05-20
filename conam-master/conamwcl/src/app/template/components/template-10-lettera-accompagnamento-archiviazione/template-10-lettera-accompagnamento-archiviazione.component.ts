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
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";

@Component({
  selector: "template-10-lettera-accompagnamento-archiviazione",
  templateUrl: "./template-10-lettera-accompagnamento-archiviazione.component.html",
})
export class Template10LetteraAccompagnamentoArchiviazioneComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public funzionario: string;

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

  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

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
    this.logger.init(Template10LetteraAccompagnamentoArchiviazioneComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {
          this.funzionario = user.nome + " " + user.cognome;
        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(
        this.formTemplate.valid && this.formIntestazioneValid
      );
    });
  }

  setAnteprima(flag: boolean) {
  
    this.isAnteprima = flag;
    this.intestazione.setAnteprima(flag);
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }

  getDatiCompilati(): DatiTemplateCompilatiVO {
    return this.datiCompilati;
  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {
    this.datiCompilati = dati;
  }

  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }

  ngOnDestroy(): void {
    this.logger.destroy(Template10LetteraAccompagnamentoArchiviazioneComponent.name)
  }
}
