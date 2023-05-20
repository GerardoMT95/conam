import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "../../shared/module/datatable/classes/config";


@Injectable()
export class SharedRateConfigService implements OnDestroy {

    public getConfigRateView() {
        let config: Config = JSON.parse(JSON.stringify(this.configViewRate));
        return config;
    }
    public configViewRate: Config = {
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
                columnName: 'dataPagamento',
                displayName: 'Data Pagamento'
            },
            {
                columnName: 'importoPagato',
                displayName: 'Importo Pagato'
            },
            {
                columnName: 'dataScadenza',
                displayName: 'Data Scadenza'
            },
            {
                columnName: 'dataFineValidita',
                displayName: 'Data fine validità'
            },
            {
                columnName: 'codiceAvviso',
                displayName: 'Codice Avviso'
            },
            {
                columnName: 'identificativoSoggetto',
                displayName: 'Identificativo Soggetto'
            },
        ]
    };

    constructor(private logger: LoggerService) {
        this.logger.createService(SharedRateConfigService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(SharedRateConfigService.name);
    }
}

