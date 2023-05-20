import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO } from "../../../commons/vo/select-vo";

@Component({
    selector: 'ordinanza-ricerca',
    templateUrl: './ordinanza-ricerca.component.html'
})
export class OrdinanzaRicercaGestContAmministrativoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public showTable: boolean;

    public config: Config;
    public ordinanze: Array<OrdinanzaVO>;
    public ordinanzaSel: OrdinanzaVO;
    public loaded: boolean = true;
    
    public loadedStatiOrdinanza: boolean;
    public statiOrdinanzaModel: Array<StatoOrdinanzaVO>;

    public request: RicercaOrdinanzaRequest;

    public max: boolean = false;
    public itsAnnullamento: string; 
    
    constructor(
        private logger: LoggerService,
        private router: Router,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private activatedRoute: ActivatedRoute,
    ) { }

    ngOnInit(): void {
        this.itsAnnullamento = ''
        this.subscribers.route = this.activatedRoute.params.subscribe((params) => {    

            // verifico se arrivo da inserimento ordinanza di annullamento
           
            if (this.activatedRoute.snapshot.paramMap.get("azione")) {             
              this.itsAnnullamento  = this.activatedRoute.snapshot.paramMap.get(
                "azione"
              );
            } 
                          
          });
         

        this.logger.init(OrdinanzaRicercaGestContAmministrativoComponent.name);
        this.config = this.configSharedService.configRicercaOrdinanza;    
        this.loadStatiOrdinanza();
    }

    loadStatiOrdinanza(){
        this.loadedStatiOrdinanza = false;
        this.sharedOrdinanzaService.getStatiOrdinanza().subscribe( data => {
            if(data!=null)
                this.statiOrdinanzaModel = data;
                this.loadedStatiOrdinanza = true;
        })
    }

    scrollEnable: boolean;
    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest) {
        this.request = ricercaOrdinanzaRequest;
        this.showTable = false;
        this.loaded = false;
        ricercaOrdinanzaRequest.ordinanzaProtocollata = false;
        this.request.statoManualeDiCompetenza = true;
        this.request.tipoRicerca = "RICERCA_ORDINANZA";
        if(this.itsAnnullamento === 'annullamento'){
            this.sharedOrdinanzaService.ricercaOrdinanzaAnnullamento(ricercaOrdinanzaRequest).subscribe( 
                data => {
                    this.resetMessageTop();
                    if(data.length == 1){
                        data.map(value => {
                            this.max = value.superatoIlMassimo; 
                        });
                    }                
    
                    if(this.max){
                        this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                        this.ordinanze = new Array<OrdinanzaVO>();
                    } else{
                        this.ordinanze = data;
                    }  
                    this.showTable = true;
                    this.loaded = true;
                    this.scrollEnable = true;
                }, err => {
                    if (err instanceof ExceptionVO) {
                        this.loaded = true;
                        this.manageMessageTop(err.message,err.type);
                    }
                    this.logger.error("Errore nella ricerca dell'ordinanza");
                }
            );
        }else{
        this.sharedOrdinanzaService.ricercaOrdinanza(ricercaOrdinanzaRequest).subscribe( 
            data => {
                this.resetMessageTop();
                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo; 
                    });
                }                

                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                    this.ordinanze = new Array<OrdinanzaVO>();
                } else{
                    this.ordinanze = data;
                }  
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessageTop(err.message,err.type);
                }
                this.logger.error("Errore nella ricerca dell'ordinanza");
            }
        );
    }
    }

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    onDettaglio(el: any | Array<any>) {
        this.ordinanzaSel = el;
        if (el instanceof Array){
            throw Error("errore sono stati selezionati più elementi");
        }else if(this.itsAnnullamento === 'annullamento'){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + el.id, {azione: this.itsAnnullamento}]);
        } 
        else
            this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + el.id);
    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(OrdinanzaRicercaGestContAmministrativoComponent.name);
    }

}