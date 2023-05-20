import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  Output,
  Input,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { NgForm } from "@angular/forms";
import { UserService } from "../../../core/services/user.service";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";

@Component({
  selector: "template-05-sollecito-pagamento",
  templateUrl: "./template-05-sollecito-pagamento.component.html",
})
export class Template05SollecitoPagamentoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  //gestione anteprima
  public isAnteprima: boolean;
  public isStampa: boolean;

  //dati precompilati
  @Input()
  data: DatiTemplateVO;
  funzionario: string;

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
    this.logger.init(Template05SollecitoPagamentoComponent.name);
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
    //La richiesta Bertinetti riguarda solo l'ordinanza
    this.data.mailSettoreTributi = null;
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }

  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
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
    this.logger.destroy(Template05SollecitoPagamentoComponent.name);
  }
}
