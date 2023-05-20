import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { HttpParams, HttpClient } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";


@Injectable()
export class FaseGiurisdizionaleVerbaleAudizioneService implements OnDestroy {

    constructor(private logger: LoggerService, private http: HttpClient, private config: ConfigService) {
        this.logger.createService(FaseGiurisdizionaleVerbaleAudizioneService.name);
    }

    idVerbaleSoggettoList: Array<number>;

    isConvocazioneCreated(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/isConvocazioneCreated';
        return this.http.post<IsCreatedVO>(url, request); 
    }

    isVerbaleAudizioneCreated(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/isVerbaleAudizioneCreated';
        return this.http.post<IsCreatedVO>(url, request); 
    }

    ngOnDestroy(): void {
        this.logger.destroyService(FaseGiurisdizionaleVerbaleAudizioneService.name);
    }
}

