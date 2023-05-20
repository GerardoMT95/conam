import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { Routing } from "../../../commons/routing";

@Component({
  selector: "ordinanza-ins-aggiungi-notifica",
  templateUrl: "./ordinanza-ins-aggiungi-notifica.component.html",
})
export class OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public idOrdinanza: number;

  public configSoggetti: Config;
  public showAnnullamentoParts: boolean = false;
  public itsAnnullamento: string;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService
  ) {}

  ngOnInit(): void {
    this.logger.init(
      OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent.name
    );
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idOrdinanza = +params["id"];
      if (this.activatedRoute.snapshot.paramMap.get("azione")) {
        this.itsAnnullamento = this.activatedRoute.snapshot.paramMap.get(
          "azione"
        );
      }
      if (this.itsAnnullamento === "annullamento") {
        this.showAnnullamentoParts = true;
      }
    });
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      false,
      null,
      0,
      null,
      null,
      (el: any) => true
    );
  }

  save(event: number) {
    if (this.showAnnullamentoParts) {
      this.router.navigate(
        [
          Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA +
            this.idOrdinanza,
          { action: "creazione", azione: this.itsAnnullamento },
        ],
        { replaceUrl: true }
      );
    } else {
      this.router.navigate(
        [
          Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA +
            this.idOrdinanza,
          { action: "creazione" },
        ],
        { replaceUrl: true }
      );
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(
      OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent.name
    );
  }
}
