import { Component, OnInit, OnDestroy, ViewChild, Input, Output, EventEmitter } from "@angular/core";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { SalvaStatoVerbaleRequest } from "../../../commons/request/verbale/salva-stato-verbale.request";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../commons/vo/messageVO";
import { IstruttoreVO, StatoVerbaleVO } from "../../../commons/vo/select-vo";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TemplateService } from "../../../template/services/template.service";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";



@Component({
    selector: 'pregresso-seleziona-soggetti',
    templateUrl: './pregresso-seleziona-soggetti.component.html'
})
export class PregressoSelezionaSoggettiComponent implements OnInit, OnDestroy {
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;
    
    @Input()
    numDeterminazione: string;

    @Input()
      idVerbale: number;

    @Input()
      riepilogoVerbale: RiepilogoVerbaleVO;
    
    @Input()
        isPiano:boolean = false;
    
    @Input()    
        isSollecito:boolean = false;
    
    @Input() 
        isDisposizione:boolean = false;
    
    @Output()
        onSelect: EventEmitter<TableSoggettiOrdinanza[]> = new EventEmitter<TableSoggettiOrdinanza[]>();
      
    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;
  

    public loadedistruttore:boolean;
    public loadedstativerbale: boolean;
    public showIstruttore: boolean = false;

    public funzionarioIstrModel: Array<IstruttoreVO> = new Array<IstruttoreVO>();
    public statiVerbaliModel: Array<StatoVerbaleVO> = new Array<StatoVerbaleVO>();
    public statiVerbaliMessage: string;
    salvaStatoVerbaleRequest: SalvaStatoVerbaleRequest = new SalvaStatoVerbaleRequest();



    public subscribers: any = {};
    public buttonAnnullaTexts: string;
    public buttonConfirmTexts: string;
    public subMessagess: Array<string>;


    public alertWarning: string;

    public loaded:boolean = false;
    public soggetti: TableSoggettiOrdinanza[];
    public configSoggetti: Config;

    

    constructor(
        private logger: LoggerService,
        private pregressoVerbaleService:PregressoVerbaleService,
        private verbaleService:VerbaleService,
        private utilSubscribersService: UtilSubscribersService,
        private templateService: TemplateService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        
    ) { }

    ngOnInit(): void {
        this.logger.init(PregressoSelezionaSoggettiComponent.name);
        this.loaded = false;
        this.loadSoggetti();
        this.setConfig();
    }

    onSelected(els:TableSoggettiOrdinanza[]){
        this.onSelect.emit(els);
    }
    
    setConfig() {
        if(this.isPiano){
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
                true, 
                null,
                1, 
                null,
                (el: any)=>false,
                (el: any)=>false);
        }else if(this.isSollecito){
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
                true, 
                null,
                0, 
                null,
                (el: any)=>false,
                (el: any)=>false);
        }else if(this.isDisposizione){
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
            true, 
            null,
            1, 
            null,
            (el: any)=>false,
            (el: any)=>false);
        }else{
            this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
            true, 
            null,
            0, 
            null,
            (el: any)=>false,
            (el: any)=>false);
        }        
        this.configSoggetti.buttonSelection.enable = false;
        
    }

    loadSoggetti(): void{
        if(this.idVerbale){

        }else if(this.isPiano){
            let request:RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();
            request.numeroDeterminazione = this.numDeterminazione;
            request.ordinanzaProtocollata = true;
            request.tipoRicerca = "CREA_PIANO";
            this.pregressoVerbaleService.ricercaSoggettiPiano(request).subscribe(data => {
                if (data != null) {
                    this.loaded = true;
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    }); 
                }
            });
        }else if(this.isSollecito) {
            let request:RicercaSoggettiOrdinanzaRequest = new RicercaSoggettiOrdinanzaRequest();
            request.numeroDeterminazione = this.numDeterminazione;
            request.ordinanzaProtocollata = true;
            request.tipoRicerca = "RICERCA_SOLLECITO";
            this.subscribers.ricerca = this.pregressoVerbaleService.ricercaSoggettiOrdinanza(request).subscribe(data => {
                if (data != null) {
                    this.loaded = true;
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    });
                }
            });

        }else{
            let request:RicercaSoggettiOrdinanzaRequest = new RicercaSoggettiOrdinanzaRequest();
            request.numeroDeterminazione = this.numDeterminazione;
            request.ordinanzaProtocollata = true;
            request.tipoRicerca = "RICERCA_SOLLECITO";
            this.subscribers.ricerca = this.pregressoVerbaleService.ricercaSoggettiOrdinanza(request).subscribe(data => {
                if (data != null) {
                    this.loaded = true;
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    });
                }
            });
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoSelezionaSoggettiComponent.name);
    }

}
