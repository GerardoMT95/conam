import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  EventEmitter,
  Output,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { RiepilogoAllegatoVO } from "../../../commons/vo/verbale/riepilogo-allegato-vo";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { saveAs } from "file-saver";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";

@Component({
  selector: "shared-verbale-allegato-tabella",
  templateUrl: "./shared-verbale-allegato-tabella.component.html",
})
export class SharedVerbaleAllegatoTabellaComponent
  implements OnInit, OnDestroy, OnChanges {
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

  @Input()
  riepilogoAllegato: RiepilogoAllegatoVO;
  @Input()
  configDocumentiVerbale: Config;
  @Input()
  configDocumentiIstruttoria: Config;
  @Input()
  configDocumentiFaseGiurisdizionale: Config;
  @Input()
  configDocumentiRateizzazione: Config;
  @Input()
  verbaleAudizione: boolean;
  @Output()
  onDelete = new EventEmitter<AllegatoVO>();

  @Output()
  onLoaded: EventEmitter<boolean> = new EventEmitter<boolean>();

  public buttonAnnullaTexts: string;
  public buttonConfirmTexts: string;
  public subLinks: Array<any>;

  public subscribers: any = {};

  public dataDocumentiVerbale: Array<any>;
  public dataDocumentiIstruttoria: Array<any>;
  public dataDocumentiFaseGiurisdizionale: Array<any>;
  public dataDocumentiRateizzazione: Array<any>;
  public mostraTabellaVerbaleAudizione: boolean;

  constructor(
    private logger: LoggerService,
    private allegatoSharedService: AllegatoSharedService,
    private utilSubscribersService: UtilSubscribersService
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedVerbaleAllegatoTabellaComponent.name);
    this.initModel();
  }

  //serve? da controllare
  initModel() {
    this.dataDocumentiVerbale = this.riepilogoAllegato.verbale;
    this.dataDocumentiIstruttoria = this.riepilogoAllegato.istruttoria;
    this.dataDocumentiFaseGiurisdizionale = this.riepilogoAllegato.giurisdizionale;
    this.dataDocumentiRateizzazione = this.riepilogoAllegato.rateizzazione;
    this.mostraTabellaVerbaleAudizione = this.verbaleAudizione;
  }

  getAllegato(el: AllegatoVO) {
    if (!el.id) return;
    this.logger.info("Richiesto download dell'allegato " + el.id);
    if (el.nome.toLowerCase().startsWith("documento multiplo")) {
      this.onLoaded.emit(false);
      this.allegatoSharedService
        .getDocFisiciByIdAllegato(el.id)
        .subscribe((data) => {
          this.onLoaded.emit(true);
          this.openAllegatoMultiplo(data);
        });
    } else {
      this.onLoaded.emit(false);
      this.subscribers.allegatoDowload = this.allegatoSharedService
        .getAllegato(el.id)
        .subscribe((res) => {
          this.onLoaded.emit(true);
          saveAs(res, el.nome || "Unknown");
        });
    }
  }

  openAllegatoMultiplo(els: any[]) {
    this.subLinks = new Array<any>();

    els.forEach((item) => {
      this.subLinks.push({ ...item, label: item.nomeFile });
    });

    this.buttonAnnullaTexts = "";
    this.buttonConfirmTexts = "Ok";

    //mostro un messaggio
    this.sharedDialogs.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  openAllegatoFisico(el: any) {
    const myFilename = el.nomeFile;
    this.onLoaded.emit(false);
    this.allegatoSharedService
      .getAllegatoByObjectIdDocumentoFisico(el.objectIdDocumentoFisico)
      .subscribe((data) => {
        this.onLoaded.emit(true);
        saveAs(data, myFilename || "Unknown");
      });
  }

  emitEliminazione(el: AllegatoVO) {
    this.onDelete.emit(el);
  }

  ngOnChanges(): void {
    this.logger.change(SharedVerbaleAllegatoTabellaComponent.name);
    this.initModel();
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleAllegatoTabellaComponent.name);
  }
}
