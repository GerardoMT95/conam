import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "../../shared/module/datatable/classes/config";


@Injectable()
export class SharedRiscossioneConfigService implements OnDestroy {

    public getConfigOrdinanzaRiscossione(selection: boolean,
        labelButton: string,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        isEditable: (el: any) => boolean,
    ) {
        let config: Config = JSON.parse(JSON.stringify(this.configRiscossioneOrdinanza));
        config.buttonSelection.label = labelButton;
        config.selection.enable = selection;
        config.buttonSelection.enable = selection;
        config.selection.selectionType = selectionType;
        if (isEditable) {
            config.edit.isEditable = isEditable;
            config.edit.enable = true;
        }
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.buttonSelection.showDetail = showDetail;
        }
        return config;
    }

    public getConfigOrdinanzaRiscossioneElenco(
        isEditable: (el: any) => boolean,
        isDeletable: (el: any) => boolean,
    ) {
        let config: Config = JSON.parse(JSON.stringify(this.configRiscossioneOrdinanza));
        if (isEditable) {
            config.edit.isEditable = isEditable;
            config.edit.enable = true;
        }
        if (isDeletable) {
            config.delete.enable = true;
            config.delete.isDeletable = isDeletable;
            config.delete.internal = false;
        }
        config.buttonSelection.enable = false;
        config.selection.enable = false;
        config.info.enable=true;
        config.info.hasInfo=(el: any)=>true;
        return config;
    }

    public getConfigOrdinanzaRiscossioneElencoStato(
        isEditable: (el: any) => boolean,
        isDeletable: (el: any) => boolean,
    ) {
        let config: Config = JSON.parse(JSON.stringify(this.configRiscossioneOrdinanzaStato));
        if (isEditable) {
            config.edit.isEditable = isEditable;
            config.edit.enable = true;
        }
        if (isDeletable) {
            config.delete.enable = true;
            config.delete.isDeletable = isDeletable;
            config.delete.internal = false;
        }
        config.buttonSelection.enable = false;
        config.selection.enable = false;
        config.info.enable=true;
        config.info.hasInfo=(el: any)=>true;
        return config;
    }

    private configRiscossioneOrdinanza: Config = {
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
        edit: {
            enable: false,
            isEditable: (any: any) => false
        },
        delete: {
            enable: false,
            internal: false,
            isDeletable: (any: any) => false
        },
        info:{
            enable: false,
            hasInfo: (el: any) => {
                return false;
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
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
            {
                columnName: 'nomeCognomeRagioneSociale',
                displayName: 'Identificativo Soggetto' /*'Cognome Nome/RagioneSociale'*/
            },
            {
                columnName: 'ruolo.denominazione',
                displayName: 'Ruolo'
            },
            {
                columnName: 'importoPagatoDaPagarePiano',
                displayName: 'Saldo Piano'
            },
            {
                columnName: 'importoPagatoDaPagareOrdinanza',
                displayName: 'Saldo Ordinanza'
            },
            {
                columnName: 'importoPagatoDaPagareSollecito',
                displayName: 'Saldo Sollecito'
            },
            {
                columnName: 'importoDaRiscuotere',
                displayName: 'Importo da riscuotere'
            },
           
        ]
    };

    private configRiscossioneOrdinanzaStato: Config = {
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
        edit: {
            enable: false,
            isEditable: (any: any) => false
        },
        delete: {
            enable: false,
            internal: false,
            isDeletable: (any: any) => false
        },
        info:{
            enable: false,
            hasInfo: (el: any) => {
                return false;
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
                columnName: 'statoSoggettoOrdinanza.denominazione',
                displayName: 'Posizione'
            },
            {
                columnName: 'nomeCognomeRagioneSociale',
                displayName: 'Identificativo Soggetto' /*'Cognome Nome/RagioneSociale'*/
            },
            {
                columnName: 'ruolo.denominazione',
                displayName: 'Ruolo'
            },
            {
                columnName: 'importoPagatoDaPagarePiano',
                displayName: 'Saldo Piano'
            },
            {
                columnName: 'importoPagatoDaPagareOrdinanza',
                displayName: 'Saldo Ordinanza'
            },
            {
                columnName: 'importoPagatoDaPagareSollecito',
                displayName: 'Saldo Sollecito'
            },
            {
                columnName: 'importoDaRiscuotere',
                displayName: 'Importo da riscuotere'
            },
        ]
    };

    constructor(private logger: LoggerService) {
        this.logger.createService(SharedRiscossioneConfigService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(SharedRiscossioneConfigService.name);
    }
}

