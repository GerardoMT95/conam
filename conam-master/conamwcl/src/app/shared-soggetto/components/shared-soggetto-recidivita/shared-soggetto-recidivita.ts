import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";

@Component({
    selector: 'shared-soggetto-recidivita',
    templateUrl: './shared-soggetto-recidivita.html'
})
export class SharedSoggettoRecidivita implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
     
    public isNota: boolean = false;
    public config: Config;
    public soggettiVerbale: any;
    public loaded: boolean = true;
    
    constructor(
        private logger: LoggerService,
        private verbaleService: VerbaleService,
        private sharedVerbaleConfigService: SharedVerbaleConfigService
    ) { }

    ngOnInit(): void {
        this.loaded = false; 
        this.verbaleService
        .getVerbaleSoggettoByIdSoggetto(this.soggetto.id, true)
        .subscribe((data) => {
          if (data) {
            this.soggetto = data.soggetto;
            for(let i in data.soggettiVerbale){
              data.soggettiVerbale[i].ruolo = data.ruoloSoggetto.denominazione;
              if(data.soggettiVerbale[i].recidivo){
                data.soggettiVerbale[i].residivoString = "SI"
              }
              else{
                data.soggettiVerbale[i].residivoString = "NO"
              }
            }
            this.soggettiVerbale = data.soggettiVerbale;
            this.loaded = true;
          } else {
            this.loaded = true;
          }
        }); 
        this.config = this.sharedVerbaleConfigService.getConfigRecidivitaVerbali(
            false,
            1,
            (el: any) => true,
            null,
            null
          );
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoRecidivita.name);
    }

}