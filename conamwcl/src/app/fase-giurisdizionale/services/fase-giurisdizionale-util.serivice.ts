import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";

@Injectable()
export class FaseGiurisdizionaleUtilService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(FaseGiurisdizionaleUtilService.name);
    }

    private idOrdinanzaVerbaleSoggetto: Array<number>;
    private idOrdinanza: number;

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
        this.logger.destroyService(FaseGiurisdizionaleUtilService.name);
    }

}