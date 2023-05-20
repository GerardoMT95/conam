import { Component, OnInit, OnDestroy, Input, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaDettaglio } from "../shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";

@Component({
  selector: "shared-ordinanza-riepilogo",
  templateUrl: "./shared-ordinanza-riepilogo.component.html",
})
export class SharedOrdinanzaRiepilogoComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  @Input()
  idOrdinanza: number;
  @Input()
  isFirstDownloadBollettini: boolean;
  @Input()
  idVerbale: number = 0;
  @Input()
  showAnnullamentoParts: boolean;
  configSoggetti: Config;
  configAllegati: Config;

  @ViewChild(SharedOrdinanzaDettaglio)
  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;

  //JIRA - Gestione Notifica
  isImportoNotificaInserito: boolean = true;

  constructor(
    private logger: LoggerService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private configSharedService: ConfigSharedService
  ) {}

  ngOnInit(): void {
    if (!this.idOrdinanza) throw new Error("id ordinanza non valorizzato");
    this.logger.init(SharedOrdinanzaRiepilogoComponent.name);
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      false,
      null,
      0,
      null,
      null,
      (el: any) => true
    );
    this.configAllegati = this.configSharedService.configDocumentiOrdinanza;
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedOrdinanzaRiepilogoComponent.name);
  }

  //JIRA - Gestione Notifica
  importNotificaInseritoHandler(valueEmitted) {
    this.isImportoNotificaInserito = valueEmitted;
  }
  //--------------------------
}
