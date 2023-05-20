import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SharedVerbaleConfigService } from "../../service/shared-verbale-config.service";
import { DataTableComponent } from "../../../shared/module/datatable/components/datatable/datatable.component";
import { t } from "@angular/core/src/render3";
import { PregressoVerbaleService } from "../../../pregresso/services/pregresso-verbale.service";

@Component({
  selector: "shared-verbale-dettaglio-soggetti",
  templateUrl: "./shared-verbale-dettaglio-soggetti.component.html",
})
export class SharedVerbaleDettaglioSoggettiComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  @Input()
  idVerbale: number;
  @Input()
  config: Config;
  @Input()
  isVerbaleAudizione: boolean = false;
  @Input()
  isVerbaleConvocazione: boolean = false;
  @Input()
  pregresso: boolean = false;
  @Output()
  selected: EventEmitter<Array<TableSoggettiVerbale>> = new EventEmitter<
    Array<TableSoggettiVerbale>
  >();
  @Output()
  notSelected: EventEmitter<any> = new EventEmitter<any>();

  //pagina
  loaded: boolean;
  soggetti: Array<TableSoggettiVerbale> = new Array<TableSoggettiVerbale>();
  soggettoSelected: boolean;

  constructor(
    private logger: LoggerService,
    private sharedVerbaleService: SharedVerbaleService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private sharedVerbaleConfigService: SharedVerbaleConfigService
  ) {}

  isSelectablePerAllegare: (el: TableSoggettiVerbale) => boolean = (
    el: TableSoggettiVerbale
  ) => {
    if (el.verbaleAudizioneCreato && el.idAllegatoVerbaleAudizione != null)
      return true;
    else return false;
  };

  ngOnInit(): void {
    this.logger.init(SharedVerbaleDettaglioSoggettiComponent.name);
    if (this.isVerbaleConvocazione) {
      this.sharedVerbaleService
        .getSoggettiByIdVerbaleConvocazione(this.idVerbale, false)
        .subscribe((data) => {
          if (data != null) {
            this.soggetti = data.map((value) => {
              return TableSoggettiVerbale.map(value);
            });
            this.loaded = true;
          }
        });
    } else {
      let call = this.pregresso
        ? this.pregressoVerbaleService.getSoggettiByIdVerbale(
            this.idVerbale,
            false
          )
        : this.sharedVerbaleService.getSoggettiByIdVerbale(
            this.idVerbale,
            false
          );
      call.subscribe((data) => {
        if (data != null) {
          this.soggetti = data.map((value) => {
            return TableSoggettiVerbale.map(value);
          });
          this.loaded = true;
        }
      });
    }
    this.soggettoSelected = false;
  }

  onSelected(el: Array<TableSoggettiVerbale>) {
    if (this.isVerbaleAudizione) {
      if (
        el != null &&
        el.length > 0 &&
        el[0].idAllegatoVerbaleAudizione != null
      ) {
        this.loaded = false;
        this.sharedVerbaleService
          .getSoggettiByIdVerbaleAudizione(el[0].idAllegatoVerbaleAudizione)
          .subscribe((data) => {
            if (data != null) {
              this.soggetti = data.map((value) => {
                return TableSoggettiVerbale.map(value);
              });
              this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
                true,
                1,
                (el: TableSoggettiVerbale) => false,
                false
              );
              this.loaded = true;
              this.soggettoSelected = true;
            }
          });
      }
    }
    this.selected.emit(el);
  }
  hasOneSog() {
    if (this.soggetti.length == 1) return true;
    else return false;
  }

  vediTuttiISoggetti() {
    this.loaded = false;
    this.soggettoSelected = false;
    this.sharedVerbaleService
      .getSoggettiByIdVerbale(this.idVerbale, false)
      .subscribe((data) => {
        if (data != null) {
          this.soggetti = data.map((value) => {
            return TableSoggettiVerbale.map(value);
          });
          this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
            true,
            1,
            this.isSelectablePerAllegare,
            false
          );
          this.loaded = true;
        }
      });
    this.notSelected.emit();
  }
 

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleDettaglioSoggettiComponent.name);
  }
}
