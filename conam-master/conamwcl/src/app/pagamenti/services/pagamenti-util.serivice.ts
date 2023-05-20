import { Injectable } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { TableSoggettiOrdinanza } from "../../commons/table/table-soggetti-ordinanza";

@Injectable()
export class PagamentiUtilService {

    constructor(private logger: LoggerService) {
        this.logger.createService(PagamentiUtilService.name);
    }

    private idOrdinanzaVerbaleSoggetto: Array<number>;
    private idOrdinanza: number;

    public soggettoSollecito: TableSoggettiOrdinanza;

    public setId(idOrdinanzaVerbaleSoggetto: Array<number>, idOrdinanza: number) {
        this.idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
        this.idOrdinanza = idOrdinanza;
    }

    public getIdOrdinanzaVerbaleSoggetto(): Array<number> {
        return this.idOrdinanzaVerbaleSoggetto;
    }

    public getIdOrdinanza(): number {
        return this.idOrdinanza;
    }

    onDestroy() {
        this.logger.destroyService(PagamentiUtilService.name);
    }

}