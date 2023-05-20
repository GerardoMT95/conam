import { Component, OnInit, OnDestroy, Input, OnChanges, ElementRef, ViewChild, Output, EventEmitter, SimpleChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { SharedDialogComponent } from "../shared-dialog/shared-dialog.component";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../commons/vo/messageVO";
import { DocumentoProtocollatoVO } from "../../../commons/vo/verbale/documento-protocollato-vo";
import { Config } from "../../module/datatable/classes/config";
import { ConfigSharedService } from "../../service/config-shared.service";
import { AllegatoSharedService } from "../../service/allegato-shared.service";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { winLoadingHtml } from "../../../fascicolo/components/fascicolo-allegato-da-acta/win-loading-html";
import { saveAs } from "file-saver";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { MyUrl } from "../../module/datatable/classes/url";



@Component({
    selector: 'shared-allegato-documento-protocollato',
    templateUrl: './shared-allegato-documento-protocollato.component.html',
    styleUrls: ['./shared-allegato-documento-protocollato.component.scss']
})
export class SharedAllegatoDocumentoProtocollatoComponent implements OnInit, OnDestroy, OnChanges {
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

    @Input()
    printNameFiles: boolean = true;

    @Input()
    multipleSelection: boolean = false;
    @Input()
    isPregresso: boolean = false;
    @Input()
    idVerbale: number;
    @Input()
    resetSelection: boolean = false;
    
    @Input()
    allegatiProtocollati: DocumentoProtocollatoVO[] = [];



    @Output()
    onSelected = new EventEmitter<DocumentoProtocollatoVO | DocumentoProtocollatoVO[]>();

    @Output()
    onSearch = new EventEmitter<string>();

    public subscribers: any = {};
    public loaded: boolean;

    public showMessageTop: boolean;
    public typeMessageTop: string;
    public messageTop: string;
    private intervalIdS: number = 0;

    public buttonAnnullaTexts: string;
    public buttonConfirmTexts: string;
    public subMessagess: Array<string>;
    public subLinks: Array<any>;

    searchFormRicercaProtocol: string;
    public configRicercaProtocollo: Config;

    dataRicercaProtocollo: Array<DocumentoProtocollatoVO>;
    dataRicercaProtocolloSelected: DocumentoProtocollatoVO;
    nameFiles: string;

    constructor(
        private logger: LoggerService,
        public configSharedService: ConfigSharedService,
        private allegatoSharedService: AllegatoSharedService,
        private utilSubscribersService: UtilSubscribersService,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedAllegatoDocumentoProtocollatoComponent.name);
        this.loaded = true;


        this.configRicercaProtocollo = this.configSharedService.getConfigRicercaProtocollo(
            true,
            this.multipleSelection
        );
    }

    ngOnChanges(changes: SimpleChanges) {
        this.logger.change(SharedAllegatoDocumentoProtocollatoComponent.name);
        if(changes['resetSelection'] && 
        !changes['resetSelection']['firstChange'] 
        ){
            if(this.dataRicercaProtocollo){
                this.onSelected.emit(null);
                this.nameFiles = "";
                this.configRicercaProtocollo = null;
                setTimeout(()=>{
                    this.configRicercaProtocollo = this.configSharedService.getConfigRicercaProtocollo(
                    true,
                    this.multipleSelection
                );
                });
            }
        }
      }

    ngOnDestroy(): void {
        this.logger.destroy(SharedAllegatoDocumentoProtocollatoComponent.name);
    }

    /* messaggi alert */
    manageMessage(mess: ExceptionVO | MessageVO) {
        this.typeMessageTop = mess.type;
        this.messageTop = mess.message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 20; //this.configService.getTimeoutMessagge();
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
    /* allegati */
    getAllegato(el: DocumentoProtocollatoVO) {
        const myFilename =
            typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
        if (myFilename.toLowerCase().startsWith("documento multiplo")) {
            this.loaded = false;
            this.allegatoSharedService
                .getDocFisiciByObjectIdDocumento(el.objectIdDocumento)
                .subscribe((data) => {
                    this.loaded = true;
                    this.openAllegatoMultiplo(data);
                });
        } else {
            this.openAllegato(el);
        }
    }

    openAllegatoMultiplo(els: any[]) {
        this.subMessagess = new Array<string>();

        this.subLinks = new Array<any>();

        els.forEach((item) => {
            this.subLinks.push({ ...item, label: item.nomeFile });
        });

        this.buttonAnnullaTexts = "";
        this.buttonConfirmTexts = "Ok";

        //mostro un messaggio
        this.sharedDialogs.open();

        //unsubscribe
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
        this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");

        //premo "Conferma"
        this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
            (data) => {
                this.subLinks = new Array<any>();
                this.subMessagess = new Array<string>();
            },
            (err) => {
                this.logger.error(err);
            }
        );

        //premo "Annulla"
        this.subscribers.xclose = this.sharedDialogs.XAction.subscribe(
            (data) => {
                this.subLinks = new Array<any>();
                this.subMessagess = new Array<string>();
            },
            (err) => {
                this.logger.error(err);
            }
        );
    }

    openAllegatoFisico(el: any) {
        const myFilename = el.nomeFile;
        const donwloadTypes = ["tiff", "p7m"];
        const ext = myFilename.split(".").pop();

        const winUrl = URL.createObjectURL(
            new Blob([winLoadingHtml], { type: "text/html" })
        );

        var win;
        if (donwloadTypes.indexOf(ext) == -1) {
            win = window.open(winUrl);
        } else {
            this.loaded = false;
        }

        this.allegatoSharedService
            .getAllegatoByObjectIdDocumentoFisico(el.objectIdDocumentoFisico)
            .subscribe((data) => {
                if (donwloadTypes.indexOf(ext) > -1) {
                    this.loaded = true;
                    saveAs(data, myFilename || "Unknown");
                } else {
                    let myBlob: any = data;
                    if(myBlob.type=='application/json'){
                      win.close();
                      myBlob.text().then(text => {
                        this.manageMessage(JSON.parse(text));
                      });
                    }else{
                      myBlob.name = myFilename;
                      var fileURL = URL.createObjectURL(myBlob);
                      win.location.href = fileURL;
                    }
                }
            });
    }

    openAllegato(el: DocumentoProtocollatoVO) {
        const myFilename =
            typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
        const donwloadTypes = ["tiff", "p7m"];
        const ext = myFilename.split(".").pop();

        const winUrl = URL.createObjectURL(
            new Blob([winLoadingHtml], { type: "text/html" })
        );

        var win;
        if (donwloadTypes.indexOf(ext) == -1) {
            win = window.open(winUrl);
        } else {
            this.loaded = false;
        }

        this.allegatoSharedService
            .getAllegatoByParolaChiave(el.objectIdDocumento)
            .subscribe((data) => {
                if (donwloadTypes.indexOf(ext) > -1) {
                    this.loaded = true;
                    saveAs(data, myFilename || "Unknown");
                } else {
                    let myBlob: any = data;
                    if(myBlob.type=='application/json'){
                      win.close();
                      myBlob.text().then(text => {
                        this.manageMessage(JSON.parse(text));
                      });
                    }else{
                      myBlob.name = myFilename;
                      var fileURL = URL.createObjectURL(myBlob);
                      win.location.href = fileURL;
                    }
                }
            });
    }

    /* Ricerca */

    ricercaProtocollo(ricerca: RicercaProtocolloRequest) {
        //ricerca.idVerbale = this.idVerbale;
        if (this.idVerbale) {
            ricerca.idVerbale = this.idVerbale;
        }

        if (this.isPregresso) {
            ricerca.flagPregresso = true;
        }
        this.nameFiles = "";

        this.loaded = false;
        this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
            (data) => {
                let tipiAllegatoDuplicabili = [];

                if (
                    data.documentoProtocollatoVOList &&
                    data.documentoProtocollatoVOList.length > 0
                ) {
                    if (data.documentoProtocollatoVOList[0]) {
                        tipiAllegatoDuplicabili =
                            data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
                    }
                    data.documentoProtocollatoVOList.forEach((all) => {
                        all.filename = new MyUrl(<string>all.filename, null);
                    });
                } else {
                    data.documentoProtocollatoVOList = [];
                }

                if (data.messaggio) {
                    this.manageMessage(data.messaggio);
                }

                this.loaded = true;
                this.searchFormRicercaProtocol = ricerca.numeroProtocollo;
                this.dataRicercaProtocollo = data.documentoProtocollatoVOList;

                let numProtocollo='';
                if(this.dataRicercaProtocollo[0]){
                    if(this.dataRicercaProtocollo[0].numProtocollo){
                        numProtocollo = this.dataRicercaProtocollo[0].numProtocollo;
                    }
                    if(this.dataRicercaProtocollo[0].numProtocolloMaster){
                        numProtocollo = this.dataRicercaProtocollo[0].numProtocolloMaster;
                    }
                }
                this.onSearch.emit(numProtocollo);
            },
            (err) => {
                if (err instanceof ExceptionVO) {
                    this.manageMessage(err);
                }
                this.loaded = true;
                this.logger.error("Errore nel recupero degli allegati");
            }
        );
    }

    /* sul seleziona della tabella passo alla componente padre i documenti selezionati */
    private _onSelected(el: DocumentoProtocollatoVO | DocumentoProtocollatoVO[]) {
        if (Array.isArray(el)) {
            let elsTmp =[];
            for (var item of el) {
                let newItem={...item};
                if (typeof newItem.filename != "string") {
                    newItem.filename = newItem.filename.nomeFile;
                }
                elsTmp.push(newItem);
            }
            this.onSelected.emit(elsTmp);
            this.nameFiles = el.map(e => (typeof e.filename == "string" ? e.filename : e.filename.nomeFile)).join(",");
        } else {
            const myFilename = typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
            this.nameFiles = myFilename;
            let elTmp ={...el};
            if (typeof elTmp.filename != "string") {
                elTmp.filename = elTmp.filename.nomeFile;
            }
            this.onSelected.emit(elTmp);
        }

        
    }
}