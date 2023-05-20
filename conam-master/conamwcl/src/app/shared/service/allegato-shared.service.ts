import { Injectable, OnDestroy } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs/Observable";
import { ConfigAllegatoVO } from "../../commons/vo/verbale/config-allegato-vo";
import { SelectVO } from "../../commons/vo/select-vo";

@Injectable()
export class AllegatoSharedService implements OnDestroy {

    constructor(
        private http: HttpClient,
        private config: ConfigService,
        private logger: LoggerService) {
        this.logger.createService(AllegatoSharedService.name);
    }

    getConfigAllegato(): Observable<Array<ConfigAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/getConfigAllegatiByIdVerbale';
        return this.http.get<Array<ConfigAllegatoVO>>(url);
    }

    getAllegato(idAllegato: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/downloadAllegato';
        let params = new HttpParams().set('idAllegato', idAllegato.toString());
        return this.http.get(url, { params: params, responseType: 'blob' }).map(res => new Blob([res]));
    }

    getAllegatoByParolaChiave(objectIdDocumento: string):  Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/downloadAllegatoByObjectIdDocumento';
        let params = new HttpParams().set('objectIdDocumento', objectIdDocumento);
        return this.http.get(url, { params: params, responseType: 'blob' as 'json' }).map(res => <Blob>res);
    }
    
    getAllegatoByObjectIdDocumentoFisico(objectIdDocumentoFisico : string):  Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/downloadAllegatoByObjectIdDocumentoFisico';
        let params = new HttpParams().set('objectIdDocumentoFisico', objectIdDocumentoFisico);
        return this.http.get(url, { params: params, responseType: 'blob' as 'json' }).map(res => <Blob>res);
    }

    getDocFisiciByObjectIdDocumento(objectIdDocumento: string):  Observable<any> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/getDocFisiciByObjectIdDocumento';
        let params = new HttpParams().set('objectIdDocumento', objectIdDocumento);
        return this.http.get(url, { params: params }).map(res => res);
    }

    getDocFisiciByIdAllegato(idAllegato: number):  Observable<any> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/getDocFisiciByIdAllegato';
        let params = new HttpParams().set('idAllegato', idAllegato.toString());
        return this.http.get(url, { params: params }).map(res => res);
    }

    getDecodificaSelectAllegato(idDecodifica: number): Observable<Array<SelectVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/getDecodificaSelectAllegato/' + idDecodifica.toString();
        return this.http.get<Array<SelectVO>>(url);
    }
    getDecodificaSelectSoggettiAllegato(idVerbale: number): Observable<Array<SelectVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/allegato/getDecodificaSelectSoggettiAllegato/' + idVerbale.toString();
        return this.http.get<Array<SelectVO>>(url);
    }
    
    ngOnDestroy(): void {
        this.logger.destroyService(AllegatoSharedService.name);
    }
}