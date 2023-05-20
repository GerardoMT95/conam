import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { NotificaVO } from "../../commons/vo/notifica/notifica-vo";
import { ModalitaNotificaVO } from "../../commons/vo/select-vo";
import { NotificaRequest } from "../../commons/request/notifica/notifica-request";

@Injectable()
export class SharedNotificaService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(SharedNotificaService.name);
    }


    getModalitaNotifica(): Observable<Array<ModalitaNotificaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/notifica/getModalitaNotifica';
        return this.http.get<Array<ModalitaNotificaVO>>(url);
    }

    salvaNotifica(notificaVO: NotificaVO): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/notifica/salvaNotifica';
        return this.http.post<number>(url, notificaVO);
    }

    getNotificaBy(idOrdinanza: number, idPiano: number, idSollecito: number): Observable<NotificaVO> {
        let notificaRequest: NotificaRequest = new NotificaRequest();
        notificaRequest.idPiano = idPiano;
        notificaRequest.idOrdinanza = idOrdinanza;
        notificaRequest.idSollecito = idSollecito;
        var url: string = this.config.getBEServer() + '/restfacade/notifica/getNotificaBy';
        return this.http.post<NotificaVO>(url, notificaRequest);
    }




}