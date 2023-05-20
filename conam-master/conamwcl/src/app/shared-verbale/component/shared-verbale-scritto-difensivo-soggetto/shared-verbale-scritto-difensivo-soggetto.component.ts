import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  SimpleChanges,
  OnChanges,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SharedVerbaleConfigService } from "../../service/shared-verbale-config.service";
import { ScrittoDifensivoVO } from "../../../commons/vo/verbale/scritto-difensivo-vo";
import { NgForm } from "@angular/forms";

@Component({
  selector: "shared-verbale-scritto-difensivo-soggetto",
  templateUrl: "./shared-verbale-scritto-difensivo-soggetto.component.html",
})
export class SharedVerbaleScrittoDifensivoSoggettoComponent
  implements OnInit, OnDestroy, OnChanges  {
  public subscribers: any = {};

  @Input()
  scrittoDifensivo: ScrittoDifensivoVO;

  @Input()
  isEdit: boolean = true;

  @Output()
  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  @ViewChild('scrittoDifensivoSoggetto')
  private scrittoDifensivoSoggetto: NgForm;

  personaFisica: boolean = true;

  constructor(
    private logger: LoggerService
  ) { }
 
  ngOnChanges(changes: SimpleChanges) {
    if(changes['scrittoDifensivo']){
      this.personaFisica = this.scrittoDifensivo.ragioneSociale ? false : true;
    }
  }

  //CHANGE PERSONA
  cambiaPersona(type: string) {
    this.personaFisica = type == "G" ? false : true;
    if(this.personaFisica){
      this.scrittoDifensivo.ragioneSociale = null;
    }else{
      this.scrittoDifensivo.nome = null;
      this.scrittoDifensivo.cognome = null;
    }
  }

  ngOnInit(): void {
    this.logger.init(SharedVerbaleScrittoDifensivoSoggettoComponent.name);
    //VALIDAZIONE FORM
    if (this.isEdit) {
      this.subscribers.sog = this.scrittoDifensivoSoggetto.valueChanges.subscribe(data => {
        if (this.scrittoDifensivoSoggetto.status == "DISABLED") this.formValid.next(true);
        else
          this.formValid.next(this.scrittoDifensivoSoggetto.valid);
      });
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleScrittoDifensivoSoggettoComponent.name);
  }
}
