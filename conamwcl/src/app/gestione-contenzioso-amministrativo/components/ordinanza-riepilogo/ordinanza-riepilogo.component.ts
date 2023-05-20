import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { AzioneOrdinanzaResponse } from "../../../commons/response/ordinanza/azione-ordinanza-response";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { saveAs } from 'file-saver';
import { SharedOrdinanzaRiepilogoComponent } from "../../../shared-ordinanza/component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

@Component({
    selector: 'gestione-cont-amministrativo-ordinanza-riepilogo',
    templateUrl: './ordinanza-riepilogo.component.html',
})
export class OrdinanzaRiepilogoGestContAmministrativoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    idOrdinanza: number;
    idVerbale: number = 0;
    azione: AzioneOrdinanzaResponse;
    loadedAction: boolean;
    loaded: boolean;
    isRichiestaBollettiniSent: boolean;
    isFirstDownloadBollettini: boolean;

    @ViewChild(SharedOrdinanzaRiepilogoComponent)
    sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    public itsAnnullamento: string; 
    public itsArchiviazione: string; 
    public showAnnullamentoParts: boolean;
    public goToLettaraAnnullamento: boolean;
    public itsArchiviazioneType: boolean;
    
   
    constructor(
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private sharedVerbaleService: SharedVerbaleService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.showAnnullamentoParts = false;
        this.goToLettaraAnnullamento = false;
        this.itsArchiviazioneType = false;
        this.itsAnnullamento = '';
        this.itsArchiviazione = '';
        this.subscribers.route = this.activatedRoute.params.subscribe((params) => {  
             

            // verifico se arrivo da inserimento ordinanza di annullamento
           
            if (this.activatedRoute.snapshot.paramMap.get("azione")) {             
              this.itsAnnullamento  = this.activatedRoute.snapshot.paramMap.get(
                "azione"
              );
            
            } 
            if (this.itsAnnullamento === 'annullamento') {
                this.showAnnullamentoParts =  true;
            }            
           
        });

        this.logger.init(OrdinanzaRiepilogoGestContAmministrativoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['id'];
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'SUCCESS')
                this.manageMessageTop("Ordinanza creata con successo", 'SUCCESS');
        });
        this.callAzioneOrdinanza();
        this.getVerbaleByIdOrdinanza();
        this.sharedOrdinanzaService
        .getDettaglioOrdinanza(this.idOrdinanza)
        .subscribe((data) => {
           
          if (data != null) {
              if(data.tipo.denominazione === 'Archiviazione'){
                this.itsArchiviazioneType = true;
                this.itsArchiviazione = 'archiviazione'
            }
           
            if(data.tipo.id === 3 || data.tipo.id ===  4){
                this.goToLettaraAnnullamento = true;
                this.itsAnnullamento = 'annullamento';
               
            }
          }
        });
    }

    completaLetteraOrdinanza() {
        if(this.goToLettaraAnnullamento){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_ORDINANZA_TEMPLATE_LETTERA + this.idOrdinanza, {
                azione: this.itsAnnullamento
            }]);
        } 
        else if(!this.goToLettaraAnnullamento && this.itsArchiviazioneType){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_ORDINANZA_TEMPLATE_LETTERA + this.idOrdinanza, {
                azione: this.itsArchiviazione
            }]);
        }
        else{
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_ORDINANZA_TEMPLATE_LETTERA + this.idOrdinanza);
        }    
    }

    inviaRichiestaBollettini() {
        this.azione = null;
        this.loadedAction = false;
        this.subscribers.callAzioneOrdinanza = this.sharedOrdinanzaService
            .inviaRichiestaBollettiniOrdinanza(this.idOrdinanza).subscribe(data => {
                this.callAzioneOrdinanza()
                this.isRichiestaBollettiniSent = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.manageMessageTop(err.message, err.type);
                    this.callAzioneOrdinanza();
                }
                this.loadedAction = true;
                this.logger.error("Errore durante il download del PDF");

            });
    }

    callAzioneOrdinanza() {
        let request: AzioneOrdinanzaRequest = new AzioneOrdinanzaRequest();
        request.id = this.idOrdinanza;
        this.subscribers.callAzioneOrdinanza = this.sharedOrdinanzaService.azioneOrdinanza(request).subscribe(data => {
            this.azione = data;
            this.loaded = true;
            this.loadedAction = true;
        });
    }
    
    getVerbaleByIdOrdinanza() {
        this.subscribers.callAzioneVerbale = this.sharedVerbaleService.getVerbaleByIdOrdinanza(this.idOrdinanza).subscribe(data => {
            this.idVerbale = data.id;
        });
    }

    dowloadBollettini() {
        this.loadedAction = false;
        this.sharedOrdinanzaService.downloadBollettini(this.idOrdinanza).subscribe(data => {
            let name = this.sharedOrdinanzaRiepilogo.sharedOrdinanzaDettaglio.ordinanza.numDeterminazione;
            saveAs(data, "Bollettini_Ordinanza_" + name + ".pdf");
            this.loadedAction = true;
            if(this.isRichiestaBollettiniSent){
                this.isFirstDownloadBollettini = true;
                this.isRichiestaBollettiniSent = false;
            }
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.loadedAction = true;
            this.logger.error("Errore durante il download del PDF");
        });
    }


    dowloadLettera() {
        this.loadedAction = false;
        this.sharedOrdinanzaService.downloadLettera(this.idOrdinanza).subscribe(data => {
            let name = this.sharedOrdinanzaRiepilogo.sharedOrdinanzaDettaglio.ordinanza.numDeterminazione;
            saveAs(data, "Lettera_Ordinanza_" + name + ".pdf");
            this.loadedAction = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.loadedAction = true;
            this.logger.error("Errore durante il download del PDF");
        });
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
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
        clearInterval(this.intervalTop);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    goToAggiungiNotifica() {
     
        if(this.itsAnnullamento){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_AGGIUNGI_NOTIFICA + this.idOrdinanza,
                {
                azione: this.itsAnnullamento,
                }
            ]);
        }
        else{
            this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_AGGIUNGI_NOTIFICA + this.idOrdinanza);
        }
    }

    goToVisualizzaNotifica() {
        if(this.itsAnnullamento){
            this.router.navigate([Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA + this.idOrdinanza,
                {
                azione: this.itsAnnullamento,
                }
            ]);
        }
        else{
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_VISUALIZZA_NOTIFICA + this.idOrdinanza);
        }
    }


    goToInserisciAnnullamento(){
        this.router.navigate([Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_CREA_ORDINANZA + this.idVerbale,
            {
            azione: this.itsAnnullamento,
            idOrdinanza : this.idOrdinanza
            }
        ]);
    }

    ngOnDestroy(): void {
        this.logger.destroy(OrdinanzaRiepilogoGestContAmministrativoComponent.name);
    }

}