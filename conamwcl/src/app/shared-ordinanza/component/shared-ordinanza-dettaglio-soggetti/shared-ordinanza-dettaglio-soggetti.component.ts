import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { Observable } from 'rxjs/Observable';
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { SharedOrdinanzaService } from "../../service/shared-ordinanza.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { SharedOrdinanzaConfigService } from "../../service/shared-ordinanza-config.service";
import { Constants } from "../../../commons/class/constants";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";

@Component({
  selector: "shared-ordinanza-dettaglio-soggetti",
  templateUrl: "./shared-ordinanza-dettaglio-soggetti.component.html",
})
export class SharedOrdinanzaDettaglioSoggetti implements OnInit, OnDestroy {
  public subscribers: any = {};

  @Input()
  idOrdinanza: number;
  @Input()
  config: Config;
  @Output()
  dettaglio: EventEmitter<SoggettoVO> = new EventEmitter<SoggettoVO>();
  @Output()
  selected: EventEmitter<Array<TableSoggettiOrdinanza>> = new EventEmitter<
    Array<TableSoggettiOrdinanza>
  >();

  @Input()
  isIstanzaRateizzazione: boolean = false;

  private idMaster:number;
  private idAllegato:number;
  //pagina
  loaded: boolean;
  soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
  @Input()
  idVerbale: number = 0;

  constructor(
    private logger: LoggerService,
    private sharedOrdinanzaService: SharedOrdinanzaService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.logger.init(SharedOrdinanzaDettaglioSoggetti.name);
    // statici se serve li possiamo caricare dal BE
    this.idMaster = 26;
    this.idAllegato = 38;
    this.sharedOrdinanzaService
      .getSoggettiOrdinanza(this.idOrdinanza)
      .subscribe((data) => {
        if (data != null) {
          this.soggetti = data.map((value) => {
            return TableSoggettiOrdinanza.map(value);
          });
          for (let s of this.soggetti) {
            if (s.importoTitoloSanzione != null) {
              this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggettiSentenza(
                false,
                null,
                0,
                null,
                null,
                (el: any) => true
              );
              break;
            }
          }
          if(!this.isIstanzaRateizzazione){
            this.loaded = true;
          }else{
            if(this.soggetti.length>0){
              this._callHasMasterIstanza();
            }else{
              this.loaded = true;
            }
          }
        }
      });
  }
  onSelected(el: Array<TableSoggettiOrdinanza>) {
    this.selected.emit(el);
  }
  onDettaglio(el: SoggettoVO) {
    this.dettaglio.emit(el);
  }

  private _callHasMasterIstanza():void{
    const calls = this.soggetti.map((value) => {
      return this.loadTipoAllegato(value.idSoggettoOrdinanza);
    });
    Observable.forkJoin(calls).subscribe((data:Array<Array<TipoAllegatoVO>>)=>{
      let i=0;
      data.forEach(item=>{
        if(item.filter(item=>item.id==this.idMaster).length>0){
          this.soggetti[i] = TableSoggettiOrdinanza.setMasterIstanza(this.soggetti[i],false);  
        }else{
          this.soggetti[i] = TableSoggettiOrdinanza.setMasterIstanza(this.soggetti[i],true);  
        }
        i++;
      });     
      this.loaded = true;
    },
    (err) => {
      this.logger.info("Errore nel recupero dei tipi di allegato");
    });
  }
  
  //recupero le tipologie di allegato allegabili
  loadTipoAllegato(idOrdinanzaVerbaleSoggetto:number): Observable<Array<TipoAllegatoVO>> {
    let request = new TipologiaAllegabiliOrdinanzaSoggettoRequest();
    request.id = [idOrdinanzaVerbaleSoggetto];
    request.tipoDocumento = Constants.ISTANZA_RATEIZZAZIONE;
    return this.subscribers.tipoAllegato = this.sharedOrdinanzaService
      .getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(request);
  }

  onInfo(el: any | Array<any>) {
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      let azione1: string = el.ruolo;
      let azione2: string = el.noteSoggetto;
      this.router.navigate([
        Routing.SOGGETTO_RIEPILOGO + el.idSoggetto,
        { ruolo: azione1, nota: azione2, idVerbale: this.idVerbale },
      ]);
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedOrdinanzaDettaglioSoggetti.name);
  }
}
