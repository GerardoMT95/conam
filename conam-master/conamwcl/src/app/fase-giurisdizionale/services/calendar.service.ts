import { Injectable, OnDestroy } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { LoggerService } from "../../core/services/logger/logger.service";
import { HttpClient, } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { CalendarEventVO } from "../module/calendar/component/commons/constants-calendar";
import { CalendarEventRequest } from "../module/calendar/commons/calendar-request";
import { CalendarUtils } from "../module/calendar/component/commons/calendar-utils";


@Injectable()
export class CalendarService implements OnDestroy {

    public baseUrl: string = this.config.getBEServer() + '/restfacade/calendar/';

    //PUT
    public saveEvent(calendarEventVO: CalendarEventVO): Observable<CalendarEventVO> {
        let url: string = this.baseUrl + 'saveEvent';
        return this.http.put<CalendarEventVO>(url, calendarEventVO).map(this.extractData);
    }

    //DELETE
    public deleteEvent(id: number): Observable<void> {
        let url: string = this.baseUrl + 'deleteEvent/{id}'.replace('{id}', id.toString());
        return this.http.delete<void>(url);
    }

    //POST
    public getEvents(request: CalendarEventRequest): Observable<Array<CalendarEventVO>> {
        let url: string = this.baseUrl + 'getEvents';
        return this.http.post<Array<CalendarEventVO>>(url, request).map(this.extractDataArr);
    }

    private extractDataArr(array: Array<CalendarEventVO>) {
        for (let data of array) {
            if (data.dataInizio != null) {
                data.dataInizio = CalendarUtils.convertToDate(data.dataInizio);
            }
            if (data.dataFine != null) {
                data.dataFine = CalendarUtils.convertToDate(data.dataFine);
            }
            
        }
        return array;
    }

    private extractData(data: CalendarEventVO) {
        if (data.dataInizio != null) {
            data.dataInizio = CalendarUtils.convertToDate(data.dataInizio);
        }
        if (data.dataFine != null) {
            data.dataFine = CalendarUtils.convertToDate(data.dataFine);
        }

        return data;
    }




    constructor(
        private http: HttpClient,
        private config: ConfigService,
        private logger: LoggerService) {
        this.logger.createService(CalendarService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(CalendarService.name);
    }


}