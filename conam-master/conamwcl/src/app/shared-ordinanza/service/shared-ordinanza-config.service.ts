import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "../../shared/module/datatable/classes/config";
import { TableSoggettiRiscossione } from "../../commons/table/table-soggetti-riscossione";


@Injectable()
export class SharedOrdinanzaConfigService implements OnDestroy {
    public getConfigOrdinanzaSoggetti(selection: boolean,
        labelButton: string,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        hasInfo: (el: any) => boolean,
        buttonSelectionEnabled: boolean = selection
        ) {
        let config: Config = JSON.parse(JSON.stringify(this.configSoggettiOrdinanza));
        config.buttonSelection.label = labelButton;
        config.selection.enable = selection;
        config.buttonSelection.enable = buttonSelectionEnabled;
        config.selection.selectionType = selectionType;
        config.info.hasInfo = hasInfo;
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.buttonSelection.showDetail = showDetail;
        }
        return config;
    }

    private configSoggettiOrdinanza: Config = {
        pagination: {
            enable: true,
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "",
            enable: true,
        },
        info: {
            enable: true,
            hasInfo: (el: any) => {
                return true;
            }
        },
        columns: [
            {
                columnName: 'numeroDeterminazione',
                displayName: 'Numero determinazione'
            },
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo Soggetto'
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
            },
            {
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
        ]
    };


    public getConfigOrdinanzaSoggettiSentenza(selection: boolean,
        labelButton: string,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        hasInfo: (el: any) => boolean) {
        let config: Config = JSON.parse(JSON.stringify(this.configSoggettiOrdinanzaSentenza));
        config.buttonSelection.label = labelButton;
        config.selection.enable = selection;
        config.buttonSelection.enable = selection;
        config.selection.selectionType = selectionType;
        config.info.hasInfo = hasInfo;
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.buttonSelection.showDetail = showDetail;
        }
        return config;
    }


    private configSoggettiOrdinanzaSentenza: Config = {
        pagination: {
            enable: false
        },
        sort: {
            enable: false,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "",
            enable: true,
        },
        info: {
            enable: true,
            hasInfo: (el: any) => {
                return true;
            }
        },
        columns: [
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo Soggetto'
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
            },
            {
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
            {
                columnName: 'importoTitoloSanzione',
                displayName: 'Importo titolo di sanzione'
            },
            {
                columnName: 'importoSpeseProcessuali',
                displayName: 'Importo spese processuali'
            },
        ]
    };


    public getConfigOrdinanzaSoggettiCreaPiano(selection: boolean,
        labelButton: string,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        hasInfo: (el: any) => boolean) {
        let config: Config = JSON.parse(JSON.stringify(this.configSoggettiOrdinanzaCreaPiano));
        config.buttonSelection.label = labelButton;
        config.selection.enable = selection;
        config.buttonSelection.enable = selection;
        config.selection.selectionType = selectionType;
        config.info.hasInfo = hasInfo;
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.buttonSelection.showDetail = showDetail;
        }
        return config;
    }


    private configSoggettiOrdinanzaCreaPiano: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "",
            enable: true,
        },
        info: {
            enable: true,
            hasInfo: (el: any) => {
                return true;
            }
        },
        columns: [
            {
                columnName: 'numeroDeterminazione',
                displayName: 'Numero Determinazione'
            },
            {
                columnName: 'statoOrdinanza.denominazione',
                displayName: 'Stato Ordinanza'
            },
            {
                columnName: 'verbale.statoManuale.denominazione',
                displayName: 'Etichetta stato'
            },
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo Soggetto'
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
            },
            {
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
        ]
    };
    public getConfigOrdinanzaSoggettiIstanza(selection: boolean,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        hasInfo: (el: any) => boolean,
        buttonSelectionEnabled: boolean = selection
        ) {
        let config: Config = JSON.parse(JSON.stringify(this.configSoggettiOrdinanzaIstanza));
        config.selection.enable = selection;
        config.selection.selectionType = selectionType;
        config.info.hasInfo = hasInfo;
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.buttonSelection.showDetail = showDetail;
        }
        return config;
    }

    private configSoggettiOrdinanzaIstanza: Config = {
        pagination: {
            enable: true,
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "",
            enable: false,
        },
        info: {
            enable: true,
            hasInfo: (el: any) => {
                return true;
            }
        },
        columns: [
            {
                columnName: 'hasMasterIstanzaStr',
                displayName: 'Istanza Rateizzazione'
            },
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo Soggetto'
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
            },
            {
                columnName: 'numeroDeterminazione',
                displayName: 'Numero determinazione'
            },
            {
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
        ]
    };


    constructor(private logger: LoggerService) {
        this.logger.createService(SharedOrdinanzaConfigService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(SharedOrdinanzaConfigService.name);
    }
}
