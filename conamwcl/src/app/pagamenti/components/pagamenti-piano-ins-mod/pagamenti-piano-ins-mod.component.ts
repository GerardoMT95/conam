import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { StatoPianoVO, SelectVO } from "../../../commons/vo/select-vo";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { RataVO } from "../../../commons/vo/piano-rateizzazione/rata-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { PagamentiService } from "../../services/pagamenti.service";
import { SelectionType } from "../../../shared/module/datatable/datatable.module";

declare var $: any;

@Component({
    selector: 'pagamenti-piano-ins-mod',
    templateUrl: './pagamenti-piano-ins-mod.component.html',
    styleUrls: ['./pagamenti-piano-ins-mod.component.scss']
})
export class PagamentiPianoInsModComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public configSoggetti: Config

    public loaded: boolean;
    public loadedStatiPiano: boolean;
    public loadedIdSoggettiOrdinanza: boolean;

    //sono in modifica
    public isModifica: boolean;
    //campi abilitati in modifica
    public abilitaModifica: boolean;

    public isRataCalcolata: boolean;
    public isRataRicalcolata: boolean;
    public isRataDaModificare: boolean;
    public isRataDaModificareData: boolean;
    public rataDaModificareDataInput: string[]=[];
    public rataDaModificareDataCurrentRata: RataVO[];

    public numeroRataSelezionata: string;

    //Routing
    idPiano: number;
    idSoggettiOrdinanza: Array<number>;
    listTableSoggettiOrdinanza: Array<TableSoggettiOrdinanza>

    public statoPianoModel: Array<StatoPianoVO>;
    public importoTotale: number;
    public nuovoImportoRata: number;
    public idRataSelezionata: number;

    public piano: PianoRateizzazioneVO;
    public listaSoggetti: Array<TableSoggettiOrdinanza>;

    //Messaggio top
    private intervalTop: number = 0;
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private pagamentiService: PagamentiService,
        private numberUtilsSharedService: NumberUtilsSharedService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiPianoInsModComponent.name);
        this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(false, "", null, null, null,(el: any)=>true);
        
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idPiano = +params['idPiano'];
            this.listTableSoggettiOrdinanza = this.pagamentiService.soggettiCreaPiano;

            if (this.idPiano) {
                if (this.activatedRoute.snapshot.paramMap.get('action') == 'primoSalvataggio')
                    this.manageMessageTop("Piano salvato con successo", "SUCCESS");
                this.modifica();
            }
            else if (this.listTableSoggettiOrdinanza != null && this.listTableSoggettiOrdinanza.length > 0) {
                this.idSoggettiOrdinanza = this.listTableSoggettiOrdinanza.map(data => data.idSoggettoOrdinanza);
                this.listaSoggetti = this.listTableSoggettiOrdinanza;
                this.loadedIdSoggettiOrdinanza = true;
                this.nuovo();
                this.getMessaggioManualeByidOrdinanzaVerbaleSoggetto();
            }
            else this.router.navigateByUrl(Routing.PAGAMENTI_CREA_PIANO);
        });       
    }
    getMessaggioManualeByidOrdinanzaVerbaleSoggetto(){
        this.pagamentiService.getMessaggioManualeByidOrdinanzaVerbaleSoggetto(this.idSoggettiOrdinanza[0]).subscribe(data => {
            if(data){
              this.manageMessageTop(data.message, data.type)
            }  
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

    modifica() {
        this.isModifica = true;
        this.loadPiano();
    }

    nuovo() {
        this.isModifica = false;
        this.loadPiano();
    }

    loadPiano() {
        this.loaded = false;        
        if (!this.isModifica) {
            this.subscribers.precompilaPiano = this.pagamentiService.precompilaPiano(this.idSoggettiOrdinanza).subscribe(data => {
                this.piano = data;
                this.importoTotale = this.piano.importoSanzione + 
                                     this.piano.importoSpeseNotifica + 
                                     this.piano.importoSpeseProcessuali + 
                                     ((this.piano.importoMaggiorazione===undefined||this.piano.importoMaggiorazione===null) ? 0 : this.piano.importoMaggiorazione);
                this.abilitaModifica = true;
                this.loaded = true;
            }, err => {
                this.logger.error("Errore durante il caricamento del piano");
                this.loaded = true;
            });
        } else {
            this.loadedIdSoggettiOrdinanza = false;
            this.subscribers.getDettaglio = this.pagamentiService.getDettaglioPianoById(this.idPiano, true).subscribe(data => {
                this.piano = data;
                this.idSoggettiOrdinanza = new Array<number>();
                this.idSoggettiOrdinanza = this.piano.soggetti.map(sogg => sogg.idSoggettoOrdinanza);
                this.getMessaggioManualeByidOrdinanzaVerbaleSoggetto();
                this.listaSoggetti = this.piano.soggetti.map(sogg => TableSoggettiOrdinanza.map(sogg));
                this.loadedIdSoggettiOrdinanza = true;
                this.importoTotale = this.piano.importoSanzione + 
                                     this.piano.importoSpeseNotifica + 
                                     this.piano.importoSpeseProcessuali + 
                                     ((this.piano.importoMaggiorazione===undefined||this.piano.importoMaggiorazione===null) ? 0 : this.piano.importoMaggiorazione);
                this.abilitaModifica = true;
                this.isRataCalcolata = true;
                this.loaded = true;
            }, err => {
                this.logger.error("Errore durante il caricamento del piano");
                this.loaded = true;
            });
        }
    }

    loadStatiPiano() {
        this.loadedStatiPiano = false;
        this.subscribers.statiPiano = this.pagamentiService.getStatiPiano().subscribe(data => {
            this.statoPianoModel = data;
            this.loadedStatiPiano = true;
        });
    }
    setDefaultRateDataFineValidita(){    
        const maxDataScadenza = this.piano.rate[this.piano.rate.length-1].dataScadenza;
        this.piano.rate.map(
            item=>{
                if(!item.dataFineValidita){
                    item.dataFineValidita = maxDataScadenza;
                }
                return item;
            }
        );
    }

    calcolaRata() {
        this.loaded = false;
        this.isRataCalcolata = false;        
        this.subscribers.calcolRata = this.pagamentiService.calcolaRata(this.piano).subscribe(data => {
            this.piano = data;
            this.setDefaultRateDataFineValidita();            
            this.importoTotale = this.piano.importoSanzione + 
                                 this.piano.importoSpeseNotifica + 
                                 this.piano.importoSpeseProcessuali + 
                                 ((this.piano.importoMaggiorazione===undefined||this.piano.importoMaggiorazione===null) ? 0 : this.piano.importoMaggiorazione);
            this.isRataCalcolata = true;
            this.abilitaModifica = false;
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il calcolo delle rate");
            this.loaded = true;
        });
    }

    ricalcolaRata() {
        this.loaded = false;
        this.isRataCalcolata = false;
        this.isRataDaModificare = false;
        this.isRataDaModificareData = false;

        let tmp = JSON.parse(JSON.stringify(this.piano));
        tmp.rate.forEach(rata => {
            if (rata.numeroRata == this.idRataSelezionata) rata.importoRata = this.nuovoImportoRata;
        });

        this.subscribers.calcolRata = this.pagamentiService.ricalcolaRata(tmp).subscribe(data => {
            this.piano = data;
            this.setDefaultRateDataFineValidita();
            this.importoTotale = this.piano.importoSanzione + 
                                 this.piano.importoSpeseNotifica + 
                                 this.piano.importoSpeseProcessuali + 
                                 ((this.piano.importoMaggiorazione===undefined||this.piano.importoMaggiorazione===null) ? 0 : this.piano.importoMaggiorazione);
            this.isRataCalcolata = true;
            this.isRataRicalcolata = true;
            this.abilitaModifica = false;
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il ricalcolo delle rate");
            this.loaded = true;
            this.isRataCalcolata = true;
        });
    }

    modificaRata(rataSelezionata: RataVO) {
        this.nuovoImportoRata = null;
        this.idRataSelezionata = null;
        this.isRataDaModificare = true;
        this.scrollImportoEnable = true;
        this.idRataSelezionata = this.piano.rate.find(rata => {
            return rata.numeroRata == rataSelezionata.numeroRata;
        }).numeroRata;
        this.numeroRataSelezionata = (this.idRataSelezionata == 1 ? "prima" : "ultima");
    }

    isDisabledRicalcola() {
        return (!this.nuovoImportoRata || this.nuovoImportoRata <= 0) ? true : false;
    }

    annulla() {
        this.isRataCalcolata = false;
        this.abilitaModifica = true;
        this.piano.rate = null;
    }

    salva() {
        this.loaded = false;
        this.subscribers.salva = this.pagamentiService.salvaPiano(this.piano).subscribe(data => {
            this.isRataRicalcolata = false;
            if (!this.isModifica)
                this.router.navigate([Routing.PAGAMENTI_PIANO_MODIFICA + data, { action: 'primoSalvataggio' }], { replaceUrl: true });
            else {
                this.modifica();
                this.manageMessageTop("Piano salvato con successo", "SUCCESS");
            };
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante il salvataggio del piano");
            this.loaded = true;
        });
    }

    creaPiano() {
        this.loaded = false;
        this.subscribers.crea = this.pagamentiService.creaPiano(this.idPiano).subscribe(data => {
            let azione: string = "creazione";
            this.router.navigate([Routing.PAGAMENTI_PIANO_VIEW + data, { action: azione }], { replaceUrl: true });
        }, err => {
            if (err instanceof ExceptionVO) {
                this.manageMessageTop(err.message, err.type);
            }
            this.logger.error("Errore durante la creazione del piano");
            this.loaded = true;

        });
    }

    onDettaglioCambiaDataFineValidita(event:RataVO[]) {
        this.rataDaModificareDataCurrentRata = event;
        let y=0;
        this.rataDaModificareDataInput=[];
        event.forEach(r=>{
            this.rataDaModificareDataInput[y]=r.dataFineValidita;
            this.manageDatePicker(null,2,y);
            y++;
        });
        this.isRataDaModificareData = true;
    }

    cambiaDataFineValidita() {
        let y=0;
        this.rataDaModificareDataCurrentRata.forEach(r=>{
            r.dataFineValidita = this.rataDaModificareDataInput[y];   
            this.rataDaModificareDataInput[y]='';
            y++;
        });
        this.isRataDaModificareData = false;
    }

    public configRate: Config = {
        edit: {
            enable: true,
            isEditable: (rata: RataVO) => {
                return rata.isEditEnable;
            }
        },
        selection:{
            enable: true,
            selectionType: SelectionType.MULTIPLE
        },
        buttonSelection: {
            label: "Aggiorna data fine validità",
            enable: true
        },
        columns: [
            {
                columnName: 'numeroRata',
                displayName: 'Numero Rata'
            },
            {
                columnName: 'stato.denominazione',
                displayName: 'Stato'
            },
            {
                columnName: 'importoRata',
                displayName: 'Importo Rata'
            },
            {
                columnName: 'dataScadenza',
                displayName: 'Data Scadenza'
            },
            {
                columnName: 'dataFineValidita',
                displayName: 'Data Fine validità'
            },
            {
                columnName: 'codiceAvviso',
                displayName: 'Codice Avviso'
            },
        ]
    };



    //DATEPICKER
    scrollImportoEnable: boolean;
    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        this.manageDatePicker(null, 1);
        let scrollImporto: HTMLElement = document.getElementById("scrollImporto");
        if (this.loaded && this.scrollImportoEnable && scrollImporto != null) {
            scrollImporto.scrollIntoView();
            this.scrollImportoEnable = false;
        }
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.loaded && this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    manageDatePicker(event: any, i: number, y:number=null) {
        var str: string = '#datetimepicker' + i.toString();
        if(y!=null){
            str = str+ '_'+y.toString();
        }
        if ($(str).length) {
            $(str).datetimepicker({
                format: 'DD/MM/YYYY',
                minDate: new Date(),
            });
        }
        if (event != null) {
            switch (i) {
                case (1):
                    this.piano.dataScadenzaPrimaRata = event.srcElement.value;
                    break;
                case (2):
                    if(y!=null){
                        this.rataDaModificareDataInput[y] = event.srcElement.value;
                    }
                    break;
            }
        }
    }

    byId(o1: SelectVO, o2: SelectVO) {
        return o1 && o2 ? o1.id === o2.id : o1 === o2;
    }

    isKeyPressed(code: number): boolean {
        return this.numberUtilsSharedService.numberValid(code);
    }

    onInfo(el: any | Array<any>){    
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");
        else{
            let azione: string = el.ruolo; 
            this.router.navigate([Routing.SOGGETTO_RIEPILOGO + el.idSoggetto, { action: azione }]);    
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiPianoInsModComponent.name);
    }

}