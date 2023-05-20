import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { DatiTemplateVO } from "../../commons/vo/template/dati-template-vo";
import { DatiTemplateRequest } from "../../commons/request/template/dati-template-request";
import { MessageVO } from "../../commons/vo/messageVO";

@Injectable()
export class TemplateService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(TemplateService.name);
    }

    getDatiTemplate(body: DatiTemplateRequest): Observable<DatiTemplateVO> {
        var url: string = this.config.getBEServer() + '/restfacade/template/getDatiTemplate';
        return this.http.post<DatiTemplateVO>(url, body);
    }

    stampaTemplate(body: DatiTemplateRequest): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/template/stampaTemplate';
        return this.http.post(url, body, { responseType: 'blob' }).map(res => new Blob([res], { type: 'application/pdf' }));
    }

    downloadTemplate(body: DatiTemplateRequest): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/template/downloadTemplate';
        return this.http.post(url, body, { responseType: 'blob' }).map(res => new Blob([res], { type: 'application/pdf' }));
    }

    nomiTemplate(body: DatiTemplateRequest): Observable<DatiTemplateVO> {
        var url: string = this.config.getBEServer() + '/restfacade/template/nomiTemplate';
        return  this.http.post<DatiTemplateVO>(url, body);
    }

    getMessage(codice:string): Observable<MessageVO> {
        var url: string = this.config.getBEServer() + '/restfacade/template/getMessaggio';
        let params = new HttpParams().set('codice', codice);
        return this.http.get<MessageVO>(url,  { params: params });
    }
}