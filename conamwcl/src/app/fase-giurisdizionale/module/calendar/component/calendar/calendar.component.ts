import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  ViewChild,
  Output,
  EventEmitter,
} from "@angular/core";
import {
  CalendarConstants,
  CalendarElement,
  ViewOption,
  CalendarEventVO,
} from "../commons/constants-calendar";
import { Observable } from "rxjs/Observable";
import { CalendarDialogComponent } from "../calendar-dialog/calendar-dialog.component";
import { CalendarShowDialogComponent } from "../calendar-show-dialog/calendar-show-dialog.component";
import { TypeAlert } from "../../../../../shared/component/shared-alert/shared-alert.component";
import { UtilSubscribersService } from "../../../../../core/services/util-subscribers-service";
import { CalendarUtils } from "../commons/calendar-utils";
import { CalendarEventRequest } from "../../commons/calendar-request";
import { UserService } from "../../../../../core/services/user.service";
import { ExceptionVO } from "../../../../../commons/vo/exceptionVO";

@Component({
  selector: "calendar",
  templateUrl: "./calendar.component.html",
  styleUrls: ["./calendar.component.css"],
})
export class CalendarComponent implements OnInit, OnDestroy {
  //paramatri di config
  actualMonth: CalendarElement;
  actualYear: number;
  allDaysOfMonth: Array<Number>;
  allDaysOfWeek: Array<Date>;
  view: ViewOption;
  viewOption: Array<ViewOption> = [
    CalendarConstants.DAY_VIEW,
    CalendarConstants.MONTH_VIEW,
    CalendarConstants.WEEK_VIEW,
  ];
  hours: Array<Number> = CalendarConstants.hour;
  partOfHour: Array<Number> = [0, 15, 30, 45];

  //DAY SETTED
  dayTobeInsertEvent: number;

  //dialog
  @ViewChild(CalendarDialogComponent) dialog: CalendarDialogComponent;
  @ViewChild(CalendarShowDialogComponent)
  dialogShowEvent: CalendarShowDialogComponent;

  titolo: string;
  event: CalendarEventVO;
  buttonAnnullaText: string;
  buttonConfirmText: string;

  nomeFunzionario: string;
  cognomeFunzionario: string;
  cfProprietario: string;

  //carimento
  loaded: boolean;

  //eventi da mostrare nel dialog
  eventToShow: Array<CalendarEventVO>;

  //output
  @Output() saveEvent = new EventEmitter<CalendarEventVO>();
  @Output() deleteEvent = new EventEmitter<CalendarEventVO>();
  @Output() changeMonth = new EventEmitter<CalendarEventRequest>();

  @Input()
  public eventInCalendar: Observable<Array<CalendarEventVO>>;
  @Input() saveError: Observable<ExceptionVO>;

  private events: Array<CalendarEventVO>;

  ngOnInit(): void {
    let d = new Date();
    this.view = CalendarConstants.MONTH_VIEW;
    this.setCalendar(new Date(d.getFullYear(), d.getMonth(), 1), new Date());
    this.setEvent();
    this.setNomeCognomeFunzionario();
  }

  setCalendar(dStart: Date, dActual: Date) {
    this.actualMonth = CalendarConstants.months[dStart.getMonth()];
    this.actualYear = dStart.getFullYear();

    this.allDaysOfMonth = CalendarUtils.calcolaGiorniMese(dStart);

    let date: Date = dActual == null ? dStart : dActual;
    this.dayTobeInsertEvent = date.getDate();
    this.allDaysOfWeek = CalendarUtils.calcolaGiorniSettimana(date);
  }

  setEvent() {
    this.eventInCalendar.subscribe((e) => {
      if (e == null) this.events = new Array<CalendarEventVO>();
      else {
        this.events = e;
       
      }
      this.loaded = true;
    });
  }

  setNomeCognomeFunzionario() {
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (data) => {
        if (data != null) {
          this.nomeFunzionario = data.nome;
          this.cognomeFunzionario = data.cognome;
          this.cfProprietario = data.codiceFiscale;
          this.loaded = false;
        }
      }
    );
  }

  //view 1
  getDayByDay(day: number): string {
    return CalendarConstants.weekday[
      new Date(this.actualYear, this.actualMonth.id, day).getDay()
    ].descrizione.substring(0, 1);
  }

  //view 1
  getEventFromDayOfMonth(day: number): Array<CalendarEventVO> {
    let date = new Date(this.actualYear, this.actualMonth.id, day);
    let tmpEvent: Array<CalendarEventVO> = new Array<CalendarEventVO>();
    for (let m of this.events) {
      if (m.dataInizio.getDate() == date.getDate())
        if (m.dataInizio.getFullYear() == date.getFullYear())
          if (m.dataInizio.getMonth() == date.getMonth()) tmpEvent.push(m);
    }
    return tmpEvent;
  }

  getEventFromDayOfMonthEx(day: number, exclude: number) {
    return this.getEventFromDayOfMonth(day).slice(exclude);
  }

  //view 2
  getEventFromHours(
    hours: number,
    minutesS: number,
    minutesE: number
  ): Array<CalendarEventVO> {
    let dateS = new Date(
      this.actualYear,
      this.actualMonth.id,
      this.dayTobeInsertEvent,
      hours,
      minutesS
    );
    if (minutesE == 60) {
      hours++;
      minutesE = 0;
    }
    let dateE = new Date(
      this.actualYear,
      this.actualMonth.id,
      this.dayTobeInsertEvent,
      hours,
      minutesE
    );
    return this.getEventIncludedList(dateS, dateE);
  }

  getEventFromDate(
    d: Date,
    hours: number,
    minutesS: number,
    minutesE: number
  ): Array<CalendarEventVO> {
    let dateS = new Date(
      this.actualYear,
      d.getMonth(),
      d.getDate(),
      hours,
      minutesS
    );
    if (minutesE == 60) {
      hours++;
      minutesE = 0;
    }
    let dateE = new Date(
      this.actualYear,
      d.getMonth(),
      d.getDate(),
      hours,
      minutesE
    );
    return this.getEventIncludedList(dateS, dateE);
  }

  private getEventIncludedList(dateS: Date, dateE: Date) {
    let eventsIN: Array<CalendarEventVO> = new Array<CalendarEventVO>();
    for (let m of this.events) {
      if (
        dateS.getTime() >= m.dataInizio.getTime() &&
        dateE.getTime() <= m.dataFine.getTime()
      )
        eventsIN.push(m);
    }
    return eventsIN;
  }

  /*
   * @deprecated Use getEventIncludedList
   */
  private getEventIncluded(dateS: Date, dateE: Date) {
    for (let m of this.events) {
      if (
        dateS.getTime() >= m.dataInizio.getTime() &&
        dateE.getTime() <= m.dataFine.getTime()
      )
        return m;
    }
  }

  /* solo x view settimanale*/
  getDayByDate(date: Date): string {
    return CalendarConstants.weekday[date.getDay()].descrizione.substring(0, 1);
  }

  action: any = {
    next: () => {
      let month: number = this.actualMonth.id;
      let year: number = this.actualYear;

      if (this.view == CalendarConstants.MONTH_VIEW) {
        this.setCalendar(
          new Date(this.actualYear, this.actualMonth.id + 1, 1),
          null
        );
      }
      if (this.view == CalendarConstants.DAY_VIEW) {
        let date: Date = new Date(
          this.actualYear,
          this.actualMonth.id,
          this.dayTobeInsertEvent
        );
        date.setDate(date.getDate() + 1);
        this.dayTobeInsertEvent = date.getDate();
        this.actualMonth = CalendarConstants.months[date.getMonth()];
        this.actualYear = date.getFullYear();
        if (month != this.actualMonth.id || this.actualYear != year) {
          this.allDaysOfMonth = CalendarUtils.calcolaGiorniMese(date);
        }
        if (CalendarConstants.weekday[date.getDay()].id == 1) {
          this.allDaysOfWeek = CalendarUtils.calcolaGiorniSettimana(date);
        }
      }
      if (this.view == CalendarConstants.WEEK_VIEW) {
        let date = new Date(this.allDaysOfWeek[6]);
        date.setDate(date.getDate() + 1);
        this.actualMonth = CalendarConstants.months[date.getMonth()];
        this.actualYear = date.getFullYear();
        this.dayTobeInsertEvent = date.getDate();
        this.allDaysOfWeek = CalendarUtils.calcolaGiorniSettimana(date);
        if (month != this.actualMonth.id || this.actualYear != year) {
          this.allDaysOfMonth = CalendarUtils.calcolaGiorniMese(date);
        }
      }

      if (month != this.actualMonth.id || this.actualYear != year) {
        let req: CalendarEventRequest = new CalendarEventRequest();
        let getDaysInMonthIn = new Date(this.actualYear, month, 0).getDate();
        req.dataInizio = CalendarUtils.convertToString(
          new Date(year, month, getDaysInMonthIn - 7, 12, 0)
        );
        req.dataFine = CalendarUtils.convertToString(
          new Date(this.actualYear, this.actualMonth.id + 1, 7, 12, 0)
        );
        this.loaded = false;
        this.changeMonth.emit(req);
      }
    },
    prev: () => {
      let month: number = this.actualMonth.id;
      let year: number = this.actualYear;
      if (this.view == CalendarConstants.MONTH_VIEW) {
        this.setCalendar(
          new Date(this.actualYear, this.actualMonth.id - 1, 1),
          null
        );
      }
      if (this.view == CalendarConstants.DAY_VIEW) {
        let date: Date = new Date(
          this.actualYear,
          this.actualMonth.id,
          this.dayTobeInsertEvent
        );
        date.setDate(date.getDate() - 1);
        this.dayTobeInsertEvent = date.getDate();
        this.actualMonth = CalendarConstants.months[date.getMonth()];
        this.actualYear = date.getFullYear();
        if (month != this.actualMonth.id || this.actualYear != year) {
          this.allDaysOfMonth = CalendarUtils.calcolaGiorniMese(date);
        }
        if (CalendarConstants.weekday[date.getDay()].id == 0) {
          this.allDaysOfWeek = CalendarUtils.calcolaGiorniSettimana(date);
        }
      }
      if (this.view == CalendarConstants.WEEK_VIEW) {
        let date = new Date(this.allDaysOfWeek[0]);
        date.setDate(date.getDate() - 1);
        this.actualMonth = CalendarConstants.months[date.getMonth()];
        this.actualYear = date.getFullYear();
        this.dayTobeInsertEvent = date.getDate();
        this.allDaysOfWeek = CalendarUtils.calcolaGiorniSettimana(date);
        if (month != this.actualMonth.id || this.actualYear != year) {
          this.allDaysOfMonth = CalendarUtils.calcolaGiorniMese(date);
        }
      }

      if (month != this.actualMonth.id || this.actualYear != year) {
        let req: CalendarEventRequest = new CalendarEventRequest();
        req.dataInizio = CalendarUtils.convertToString(
          new Date(year, month - 1, 1, 12, 0)
        );
        let getDaysInMonth = new Date(
          this.actualYear,
          this.actualMonth.id + 1,
          0
        ).getDate();
        req.dataFine = CalendarUtils.convertToString(
          new Date(this.actualYear, this.actualMonth.id, getDaysInMonth, 12, 0)
        );

        this.loaded = false;
        this.changeMonth.emit(req);
      }
    },
    openDay: (day: number) => {
      this.dayTobeInsertEvent = day;
      this.action.changeView(CalendarConstants.DAY_VIEW.id);
    },
    changeView: (idView: number) => {
      for (let i of this.viewOption)
        if (i.id == idView) {
          this.view = i;
          break;
        }
    },
    createEvent: (day: number) => {
      this.dayTobeInsertEvent = day;
      this.titolo = "Inserimento evento in calendario";
      this.buttonAnnullaText = "Annulla";
      this.buttonConfirmText = "Salva";
      this.event = new CalendarEventVO();
      this.event.dataInizio = new Date(
        this.actualYear,
        this.actualMonth.id,
        day,
        9,
        0
      );
      this.event.dataFine = new Date(
        this.actualYear,
        this.actualMonth.id,
        day,
        10,
        0
      );
      this.event.color = CalendarConstants.colorList[0].valore;
      this.event.nomeFunzionarioSanzionatore = this.nomeFunzionario;
      this.event.cognomeFunzionarioSanzionatore = this.cognomeFunzionario;
      this.event.codiceFiscaleProprietario = this.cfProprietario;
      this.event.regione = null;
      this.event.provincia = null;
      this.event.comune = null;
      this.subscribeResultSaveDialog();
      this.dialog.open();
    },
    createEventFromHour: (hour: number) => {
      this.titolo = "Inserimento evento in calendario";
      this.buttonAnnullaText = "Annulla";
      this.buttonConfirmText = "Salva";
      this.event = new CalendarEventVO();
      this.event.dataInizio = new Date(
        this.actualYear,
        this.actualMonth.id,
        this.dayTobeInsertEvent,
        hour,
        0
      );
      this.event.dataFine = new Date(
        this.actualYear,
        this.actualMonth.id,
        this.dayTobeInsertEvent,
        hour + 1,
        0
      );
      this.event.color = CalendarConstants.colorList[0].valore;
      this.event.nomeFunzionarioSanzionatore = this.nomeFunzionario;
      this.event.cognomeFunzionarioSanzionatore = this.cognomeFunzionario;
      this.event.codiceFiscaleProprietario = this.cfProprietario;
      this.event.regione = null;
      this.event.provincia = null;
      this.event.comune = null;
      this.subscribeResultSaveDialog();
      this.dialog.open();
    },
    createEventHourAndDate: (date: Date, hour: number) => {
      this.dayTobeInsertEvent = date.getDay();
      this.titolo = "Inserimento evento in calendario";
      this.buttonAnnullaText = "Annulla";
      this.buttonConfirmText = "Salva";
      this.event = new CalendarEventVO();
      this.event.dataInizio = new Date(
        date.getFullYear(),
        date.getMonth(),
        date.getDate(),
        hour,
        0
      );
      this.event.dataFine = new Date(
        date.getFullYear(),
        date.getMonth(),
        date.getDate(),
        hour + 1,
        0
      );
      this.event.color = CalendarConstants.colorList[0].valore;
      this.event.nomeFunzionarioSanzionatore = this.nomeFunzionario;
      this.event.cognomeFunzionarioSanzionatore = this.cognomeFunzionario;
      this.event.codiceFiscaleProprietario = this.cfProprietario;
      this.event.regione = null;
      this.event.provincia = null;
      this.event.comune = null;
      this.subscribeResultSaveDialog();
      this.dialog.open();
    },
    openDetailEvent: (e: CalendarEventVO, event: any) => {
      this.event = this.clone(e);
      this.titolo = "Modifica evento in calendario";
      this.buttonAnnullaText = "Elimina Evento";
      this.buttonConfirmText = "Salva Modifica";
      this.dialog.manageLuoghi(this.event);
      this.subscribeResultEditDialog();
      this.dialog.open();
      if (event != null) event.stopPropagation();
    },
    openDialogMultipleEvent: (exclude: number, day: number, event$: any) => {
      this.eventToShow = this.getEventFromDayOfMonthEx(day, exclude);
      this.dialogShowEvent.open();
      event$.stopPropagation();
    },
    oggi: () => {
      let d = new Date();
      this.setCalendar(new Date(d.getFullYear(), d.getMonth(), 1), new Date());
      let req: CalendarEventRequest = new CalendarEventRequest();
      req.dataInizio = CalendarUtils.convertToString(
        new Date(d.getFullYear(), d.getMonth() - 1, 1, 12, 0)
      );
      let getDaysInMonth = new Date(
        this.actualYear,
        this.actualMonth.id + 1,
        0
      ).getDate();
      req.dataFine = CalendarUtils.convertToString(
        new Date(this.actualYear, this.actualMonth.id, getDaysInMonth, 12, 0)
      );

      this.loaded = false;
      this.changeMonth.emit(req);
    },
  };

  public subscribers: any = {};

  clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
  }

  subscribeResultEditDialog() {
    this.unsbscribeDialogEvent();
    this.subscribers.closeE = this.dialog.closeAction.subscribe((data) => {
      this.loaded = false;
      this.deleteEvent.emit(this.event);
    });

    this.subscribers.XE = this.dialog.XAction.subscribe((data) => {
      this.event = new CalendarEventVO();
    });

    this.subscribers.salvaE = this.dialog.salvaAction.subscribe((data) => {
      this.subscribeSaveError();
      this.loaded = false;
      this.saveEvent.emit(this.event);
    });
  }

  subscribeResultSaveDialog() {
    this.unsbscribeDialogEvent();
    this.subscribers.close = this.dialog.closeAction.subscribe((data) => {
      this.event = new CalendarEventVO();
    });

    this.subscribers.X = this.dialog.XAction.subscribe((data) => {
      this.event = new CalendarEventVO();
    });

    this.subscribers.salva = this.dialog.salvaAction.subscribe((data) => {
      this.subscribeSaveError();
      this.loaded = false;
      this.saveEvent.emit(this.event);
    });
  }
  unsbscribeDialogEvent() {
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "closeE");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "XE");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "salvaE");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "X");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "salva");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "saveError");
  }

  //ALERT
  showMessage: boolean;
  message: string;
  type: string;
  subscribeSaveError() {
    this.subscribers.saveError = this.saveError.subscribe((s) => {
      if (s != null) {
        this.showMessage = true;
        this.message = s.message;
        this.type = TypeAlert.DANGER;
        this.loaded = true;
      } else {
        this.showMessage = false;
        this.loaded = true;
      }
    });
  }

  constructor(
    private utilSubscribersService: UtilSubscribersService,
    private userService: UserService
  ) {}
  ngOnDestroy() {}
}
