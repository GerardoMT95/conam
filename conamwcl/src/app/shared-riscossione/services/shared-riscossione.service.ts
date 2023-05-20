import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { SollecitoVO } from "../../commons/vo/riscossione/sollecito-vo";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";

@Injectable()
export class SharedRiscossioneService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(SharedRiscossioneService.name);
    }

    getListaSolleciti(id: number): Observable<Array<SollecitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/getSollecitiByIdOrdinanzaSoggetto';
        let params = new HttpParams().set('idOrdinanzaVerbaleSoggetto', id.toString());
        return this.http.get<Array<SollecitoVO>>(url, { params: params });
    }

    getSollecitiByIdPianoRateizzazione(id: number): Observable<Array<SollecitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/getSollecitiByIdPianoRateizzazione';
        let params = new HttpParams().set('idPianoRateizzazione', id.toString());
        return this.http.get<Array<SollecitoVO>>(url, { params: params });
    }

    isLetteraSollecitoSaved(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/isLetteraSollecitoSaved';
        return this.http.post<IsCreatedVO>(url, request); 
    }
}