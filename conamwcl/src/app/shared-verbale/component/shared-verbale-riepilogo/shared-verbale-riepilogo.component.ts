import { Component, OnInit, OnDestroy, ViewChild, Output, EventEmitter, Input } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { Routing } from "../../../commons/routing";
import { PregressoVerbaleService } from "../../../pregresso/services/pregresso-verbale.service";



@Component({
    selector: 'shared-verbale-riepilogo',
    templateUrl: './shared-verbale-riepilogo.component.html',
    styleUrls: ['./shared-verbale-riepilogo.component.scss']
})
export class SharedVerbaleRiepilogoComponent implements OnInit, OnDestroy {
    @Input()
      pregresso: boolean = false;
    @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

    public subscribers: any = {};

    public idVerbale: number;
    public riepilogoVerbale: RiepilogoVerbaleVO
    public loaded: boolean;
    public loadedAllegato : boolean = true;

    public dataRifNormativi: Array<any>;
    public dataSoggVerbale: Array<any>;

    public configVerb: Config;
    public configIstr: Config;
    public configGiurisd: Config;
    public configRateizzazione: Config;

    public eliminaAllegatoFlag: boolean;

    public buttonAnnullaText: string;
    public buttonConfirmText: string;
    public subMessages: Array<string>;

    @Output()
    public delete: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private sharedVerbaleService: SharedVerbaleService,
        private pregressoVerbaleService: PregressoVerbaleService,
        private configSharedService: ConfigSharedService,
        private utilSubscribersService: UtilSubscribersService,
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedVerbaleRiepilogoComponent.name); 
       
        this.load();
    }
    
    // TODO Controllare
    load(){
        this.loaded = false;
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            this.subscribers.riepilogo = this.sharedVerbaleService.riepilogoVerbale(this.idVerbale).subscribe(data => {
                this.riepilogoVerbale = data;
                this.riepilogoVerbale.allegati.verbale.forEach(all => {
                    all.theUrl = new MyUrl(all.nome, null);
                });
                this.riepilogoVerbale.allegati.istruttoria.forEach(all => {
                    all.theUrl = new MyUrl(all.nome, null);
                });
                this.riepilogoVerbale.allegati.giurisdizionale.forEach(all => {
                    all.theUrl = new MyUrl(all.nome, null);
                });
                this.riepilogoVerbale.allegati.rateizzazione.forEach(all => {
                    all.theUrl = new MyUrl(all.nome, null);
                });

                this.dataRifNormativi = data.verbale.riferimentiNormativi;

                let soggVerbale = data.soggetto.map(function (obj) {
                    let tipoSoggetto = obj.personaFisica ? "PERSONA FISICA" : "PERSONA GIURIDICA";
                    let identificativo = !obj.codiceFiscale ? obj.partitaIva : obj.codiceFiscale;
                    let nomeCognomeRagione = obj.personaFisica ? obj.cognome + " " + obj.nome : obj.ragioneSociale;
                    return {
                        "identificativoSoggetto": identificativo,
                        "nomeCognomeRagioneSociale": nomeCognomeRagione,
                        "tipoSoggetto": tipoSoggetto, 
                        "ruolo": obj.ruolo.denominazione,
                        "id": obj.id,
                        "noteSoggetto": obj.noteSoggetto
                    };
                });
                this.dataSoggVerbale = soggVerbale; //DA CONTROLLARE
                let call=(this.pregresso)?this.pregressoVerbaleService.getAzioniVerbale(this.idVerbale):this.sharedVerbaleService.getAzioniVerbale(this.idVerbale);

                this.subscribers.statoVerbale = call.subscribe(data => {
                    this.eliminaAllegatoFlag = data.eliminaAllegatoEnable;

                    //setto i config
                    this.configVerb = this.configSharedService.getConfigDocumentiVerbale(this.eliminaAllegatoFlag);
                    this.configIstr = this.configSharedService.configDocumentiIstruttoria;
                    this.configGiurisd = this.configSharedService.configDocumentiGiurisdizionale;
                    this.configRateizzazione = this.configSharedService.configDocumentiRateizzazione;

                    this.loaded = true;
                });

               
            });

        });
    }

    confermaEliminazione(el: AllegatoVO) {
        this.logger.info("Richiesta eliminazione dell'allegato " + el.id);
        this.generaMessaggio(el);
        this.buttonAnnullaText = "Annulla";
        this.buttonConfirmText = "Conferma";

        //mostro un messaggio
        this.sharedDialog.open();

        //unsubscribe
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

        //premo "Conferma"
        this.subscribers.save = this.sharedDialog.salvaAction.subscribe(data => {
            this.loaded = false;
            this.subscribers.eliminaAllegato = this.sharedVerbaleService.eliminaAllegato(el.id, this.idVerbale).subscribe(data => {
                //elimina l'allegato
                this.logger.info("Elimina elemento da tabella");
                this.riepilogoVerbale.allegati.verbale.splice(this.riepilogoVerbale.allegati.verbale.indexOf(el), 1);
                this.delete.next(true);
                this.loaded = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    //this.manageMessage(err.type, err.message);
                }
                this.logger.error("Errore nell'eliminazione dell'allegato " + el.id);
                this.loaded = true;
            });
        }, err => {
            this.logger.error(err);
        });

        //premo "Annulla"
        this.subscribers.close = this.sharedDialog.closeAction.subscribe(data => {
            this.subMessages = new Array<string>();
        }, err => {
            this.logger.error(err);
        });
    }

    generaMessaggio(el: AllegatoVO) {
        this.subMessages = new Array<string>();

        let incipit: string = "Si intende eliminare il seguente allegato?";

        this.subMessages.push(incipit);
        this.subMessages.push(el.theUrl.nomeFile);
    }

    configRifNorm: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: 'ambito.acronimo',
                displayName: 'Acronimo ambito'
            },
            {
                columnName: 'norma.denominazione',
                displayName: 'Normativa violata'
            },
            {
                columnName: 'articolo.denominazione',
                displayName: 'Articolo'
            },
            {
                columnName: 'comma.denominazione',
                displayName: 'Comma'
            },
            {
                columnName: 'lettera.denominazione', 
                displayName: 'Lettera'
            }]
    };

    onInfo(el: any | Array<any>){    
        if (el instanceof Array)
            throw Error("errore sono stati selezionati più elementi");
        else{
            let azione1: string = el.ruolo; 
            let azione2: string = el.noteSoggetto;
            this.router.navigate([Routing.SOGGETTO_RIEPILOGO + el.id, { ruolo: azione1, nota: azione2, idVerbale:this.idVerbale }]);    
        }
    }
    configSoggVerbale: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        buttonSelection: {
            label: "",
            enable: false,
        },
        info:{
            enable: true,
            hasInfo: (el: any) => {
                return true;
            }
        },
        columns: [
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo soggetto'
            },
            {
                columnName: 'nomeCognomeRagioneSociale',
                displayName: 'Cognome Nome/Ragione Sociale'
            },
            {
                columnName: 'tipoSoggetto',
                displayName: 'Tipo Soggetto'
            },
            {
                columnName: 'ruolo',
                displayName: 'Ruolo'
            }]
    };

    ngOnDestroy(): void {
        this.logger.destroy(SharedVerbaleRiepilogoComponent.name);
    }

    onLoadedAllegato(loaded:boolean){
        this.loadedAllegato = loaded;
    }

}