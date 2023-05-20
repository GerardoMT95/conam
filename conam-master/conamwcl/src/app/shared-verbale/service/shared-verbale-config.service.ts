import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "../../shared/module/datatable/classes/config";


@Injectable()
export class SharedVerbaleConfigService implements OnDestroy {

    public getConfigVerbaleSoggetti(
        selection: boolean,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        deleteEnable: boolean
    ) {
        let config: Config = JSON.parse(JSON.stringify(this.configSoggettiVerbale));
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.selection.enable = selection;
            config.selection.selectionType = selectionType;
        }
        config.delete.enable = deleteEnable;
        return config;
    }
   
    public getConfigResidenzaList(
        selection: boolean,
        selectionType: number,
        isSelectable: (el: any) => boolean,
        deleteEnable: boolean
    ) {
        let config: Config = JSON.parse(JSON.stringify(this.configResidenzaList));
        if (selection) {
            config.selection.isSelectable = isSelectable;
            config.selection.enable = selection;
            config.selection.selectionType = selectionType;
        }
        config.delete.enable = deleteEnable;
        return config;
    }

    private configSoggettiVerbale: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false,
        },
        sort: {
            enable: false
        },
        delete: {
            enable: true
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
        ]
    };
  
    private configResidenzaList: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: true,
        },
        sort: {
            enable: false
        },
        delete: {
            enable: false
        },
        columns: [
            {
                columnName: 'regioneResidenza.denominazione',
                displayName: 'Regione Residenza'
            },
            {
                columnName: 'comuneResidenza.denominazione',
                displayName: 'Comune Residenza'
            },
            {
                columnName: 'provinciaResidenza.denominazione',
                displayName: 'Provincia Residenza'
            },
            {
                columnName: 'indirizzoResidenza',
                displayName: 'Indirizzo residenza'
            },
            {
                columnName: 'civicoResidenza',
                displayName: 'Civico Residenza'
            },
            {
                columnName: 'cap',
                displayName: 'Cap'
            }
        ]
    };

    public getConfigRecidivitaVerbali(selection: boolean,
       
        selectionType: number,
        isSelectable: (el: any) => boolean,
        showDetail: (el: any) => boolean,
        isEditable: (el: any) => boolean,
    ) {
        
        let config: Config = JSON.parse(JSON.stringify(this.configVerbaleSoggetto));
        config.selection.enable = selection;
        config.selection.selectionType = selectionType;
        //config.selection.
        if (isEditable) {
            config.edit.isEditable = isEditable;
            config.edit.enable = true;
        }
        if (selection) {
            config.selection.isSelectable = isSelectable; 
        }
        return config;
    }
    public configVerbaleSoggetto: Config = {
      pagination: {
        enable: true,
      },
      selection: {
        enable: false,
      },
      sort: {
        enable: false,
      },
      columns: [
        {
          columnName: "residivoString",
          displayName: "Recidivo",
        },
        {
          columnName: "numero",
          displayName: "Numero verbale",
        },
        {
          columnName: "descNormaViolata",
          displayName: "Norma violata",
        },
    
        {
          columnName: "numeroProtocollo",
          displayName: "Numero protocollo",
        },
        {
          columnName: "dataCaricamento",
          displayName: "Data verbale",
        },
        {
          columnName: "stato.denominazione",
          displayName: "Stato fascicolo",
        },
        {
          columnName: "ruolo",
          displayName: "Ruolo",
        },
      ],
    };

    constructor(private logger: LoggerService) {
        this.logger.createService(SharedVerbaleConfigService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(SharedVerbaleConfigService.name);
    }
}

