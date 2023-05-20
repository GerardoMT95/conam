import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { CalendarEventVO } from "../../module/calendar/component/commons/constants-calendar";
import { BehaviorSubject } from "rxjs/BehaviorSubject";
import { Observable } from "rxjs/Observable";
import { CalendarEventRequest } from "../../module/calendar/commons/calendar-request";
import { CalendarService } from "../../services/calendar.service";
import { CalendarUtils } from "../../module/calendar/component/commons/calendar-utils";

@Component({
    selector: 'fase-giurisdizionale-calendario-udienze',
    templateUrl: './fase-giurisdizionale-calendario-udienze.component.html'
})
export class FaseGiurisdizionaleCalendarioUdienzeComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    private _event = new BehaviorSubject<Array<CalendarEventVO>>(null);
    event: Observable<Array<CalendarEventVO>> = this._event.asObservable();

    private _saveError = new BehaviorSubject<String>(null);
    saveError: Observable<String> = this._saveError.asObservable();

     //Messaggio top
     private intervalTop: number = 0;
     public showMessageTop: boolean;
     public typeMessageTop: String;
     public messageTop: String;

    public eventArray: Array<CalendarEventVO> = new Array<CalendarEventVO>();
    constructor(
        private logger: LoggerService,
        private calendarService: CalendarService
    ) {
    }

    ngOnInit() {
        let req: CalendarEventRequest = new CalendarEventRequest();
        let actualDate = new Date();
        let getDaysInMonthIn = new Date(actualDate.getFullYear(), actualDate.getMonth(), 0).getDate();
        req.dataInizio = CalendarUtils.convertToString(new Date(actualDate.getFullYear(), actualDate.getMonth() - 1, getDaysInMonthIn - 7, 12, 0));
        req.dataFine = CalendarUtils.convertToString(new Date(actualDate.getFullYear(), (actualDate.getMonth() + 1), 7, 12, 0));
        this.getEvent(req);
    }


    getEvent(req: CalendarEventRequest) {
        this.calendarService.getEvents(req).subscribe(data => {
            this.eventArray = data;
            this._event.next(this.eventArray);
        });
    }

    saveEvent(event: CalendarEventVO) {
        var clone = JSON.parse(JSON.stringify(event));
        clone.dataInizio = CalendarUtils.convertToString(event.dataInizio);
        clone.dataFine = CalendarUtils.convertToString(event.dataFine);
        this.calendarService.saveEvent(clone).subscribe(data => {
            //Rimuovo il vecchio se presente e lo aggiorno
            let arr: Array<CalendarEventVO> = new Array<CalendarEventVO>();
            for (let x of this.eventArray)
                if (x.id != data.id) arr.push(x);
            this.eventArray = arr;
            this.eventArray.push(data);
            this._event.next(this.eventArray);
            this.manageMessageTop("Evento salvato correttamente nel calendario udienze",'SUCCESS');
        }, (err) => {
            this._saveError.next(err)
        });
    }


    deleteEvent(event: CalendarEventVO) {
        this.calendarService.deleteEvent(event.id).subscribe(data => {
            let arr: Array<CalendarEventVO> = new Array<CalendarEventVO>();
            for (let x of this.eventArray)
                if (x.id != event.id) arr.push(x);
            this.eventArray = arr;
            this._event.next(this.eventArray);
        });
    }


    mockEvent() {
        let event: Array<CalendarEventVO> = new Array<CalendarEventVO>();
        let ev1 = new CalendarEventVO();
        let ev2 = new CalendarEventVO();
        let ev3 = new CalendarEventVO();

        ev1.dataInizio = new Date(2018, 8, 25, 15, 30);
        ev1.dataFine = new Date(ev1.dataInizio.getFullYear(), ev1.dataInizio.getMonth(), ev1.dataInizio.getDate(), ev1.dataInizio.getHours() + 2, 0)

        ev1.color = "#d68080"
        event.push(ev1);

        ev3.dataInizio = new Date(2018, 8, 25, 17, 0);
        ev3.dataFine = new Date(ev3.dataInizio.getFullYear(), ev3.dataInizio.getMonth(), ev3.dataInizio.getDate(), ev3.dataInizio.getHours() + 2, 0)

        ev3.color = "	#95d680"
        event.push(ev3);


        ev2.dataInizio = new Date(ev1.dataInizio.getFullYear(), ev1.dataInizio.getMonth(), ev1.dataInizio.getDate() + 1, ev1.dataInizio.getHours() + 1, 0);
        ev2.dataFine = new Date(ev2.dataInizio.getFullYear(), ev2.dataInizio.getMonth(), ev2.dataInizio.getDate(), ev2.dataInizio.getHours() + 1, 0);

        ev2.color = "#ab80d6"
        event.push(ev2);

        this._event.next(event);
        this.logger.init(FaseGiurisdizionaleCalendarioUdienzeComponent.name);
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.scrollTopEnable = true;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalTop);
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleCalendarioUdienzeComponent.name);
    }


}