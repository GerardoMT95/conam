import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";


@Injectable()
export class SoggettoService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(SoggettoService.name);
    }

    getSoggettoById(idSoggetto: number, idVerbale:number): Observable<SoggettoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/soggetto/dettaglioSoggetto';
        let params = new HttpParams().set('idSoggetto', idSoggetto.toString()).set('idVerbale', idVerbale.toString());
        return this.http.get<SoggettoVO>(url, { params: params });
    }
}