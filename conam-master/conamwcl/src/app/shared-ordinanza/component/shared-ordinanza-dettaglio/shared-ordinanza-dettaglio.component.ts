import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { SharedOrdinanzaService } from "../../service/shared-ordinanza.service";
import { NotificaVO } from "../../../commons/vo/notifica/notifica-vo";
import { SharedNotificaService } from "../../../shared-notifica/services/shared-notifica.service";

@Component({
  selector: "shared-ordinanza-dettaglio",
  templateUrl: "./shared-ordinanza-dettaglio.component.html",
})
export class SharedOrdinanzaDettaglio implements OnInit, OnDestroy {
  public subscribers: any = {};

  @Input()
  idOrdinanza: number;
  @Input()
  idSoggettiOrdinanza: Array<number>;
  @Input()
  showDatiPagamento: boolean;

  //pagina
  loaded: boolean;
  ordinanza: OrdinanzaVO;
  notifica: NotificaVO;
  visualizzaImportoNotifica: boolean = true;
  
  constructor(
    private logger: LoggerService,
    private sharedOrdinanzaService: SharedOrdinanzaService,
    private sharedNotificaService: SharedNotificaService
  ) {}

  ngOnInit(): void {
    if (this.showDatiPagamento == undefined) {
      this.showDatiPagamento = false;
    }
    this.logger.init(SharedOrdinanzaDettaglio.name);
    if (!this.idOrdinanza && !this.idSoggettiOrdinanza)
      throw new Error("idOrdinanza e idSoggettoOrdinanza non valorizzati");
    if (this.idOrdinanza) {
      this.sharedOrdinanzaService
        .getDettaglioOrdinanza(this.idOrdinanza)
        .subscribe((data) => {
          if (data != null) {
            this.ordinanza = data;            
            this.loaded = true;
            
          }
        });
      this.sharedNotificaService
        .getNotificaBy(this.idOrdinanza, null, null)
        .subscribe((data) => {
          if (data != null) {
            this.notifica = data;
          }
        });
    } else if (this.idSoggettiOrdinanza) {
      this.sharedOrdinanzaService
        .getDettaglioOrdinanzaByIdSoggettoOrdinanza(this.idSoggettiOrdinanza)
        .subscribe(
          (data) => {
            if (data != null) {
              this.ordinanza = data;

              this.loaded = true;

              this.sharedNotificaService
                .getNotificaBy(this.ordinanza.id, null, null)
                .subscribe((data) => {
                  if (data != null) {
                    this.notifica = data;
                  }
                });
            }
          },
          (err) => {
            this.logger.error("Errore durante il recupero del dettaglio");
          }
        );
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedOrdinanzaDettaglio.name);
  }
}
