import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { StatoPianoVO } from "../../commons/vo/select-vo";
import { Observable } from "rxjs";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";

@Injectable()
export class SharedPagamentiService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(SharedPagamentiService.name);
    }

    getStatiPiano(isRiconciliaPiano: boolean): Observable<Array<StatoPianoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/getStatiPiano';
        let params: HttpParams = new HttpParams().set('isRiconciliaPiano', isRiconciliaPiano.toString());
        return this.http.get<Array<StatoPianoVO>>(url,{ params: params });
    }

    isLetteraPianoSaved(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/isLetteraPianoSaved';
        return this.http.post<IsCreatedVO>(url, request); 

    }

}