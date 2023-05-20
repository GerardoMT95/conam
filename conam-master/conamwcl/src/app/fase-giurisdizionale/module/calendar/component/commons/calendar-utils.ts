import { CalendarConstants, CalendarElement } from "./constants-calendar";

export class CalendarUtils {


    public static convertToDate(date: string): Date {
        return this.convertToDateFormat(date, null);
    }

    public static convertToDateFormat(date: string, format:string): Date {
        if (date == null || date.length == 0)
            return null;
        let arr: Array<string> = date.split('/');
        let month: number;
        let year: number;
        let day: number;
        let hour: number;
        let minutes: number;

        day = Number.parseInt(arr[0]);
        month = Number.parseInt(arr[1]);
        let yearArr = arr[2].split(',');
        year = Number.parseInt(yearArr[0]);
        if(yearArr.length>1){
            let hoursArr = yearArr[1].split(':');
            hour = Number.parseInt(hoursArr[0]);
            minutes = Number.parseInt(hoursArr[1]);
        }

        if(format == null)
            return new Date(year, month - 1, day, hour, minutes);
        else
            return new Date(year, month - 1, day);
    }

    public static convertToString(date: Date): string {
        let d: string = date.getDate().toString();
        let m: string;
        for (let i = 0; i < CalendarConstants.months.length; i++)
            if (CalendarConstants.months[i].id == date.getMonth())
                m = (i + 1).toString();

        let min: string = date.getMinutes().toString();
        let hours: string = date.getHours().toString();
        if (d.length == 1) d = "0" + d;
        if (m.length == 1) m = "0" + m
        if (min.length == 1) min = "0" + min;
        if (hours.length == 1) hours = "0" + hours;
        return d + "/" + m + "/" + date.getFullYear() + ", " + hours + ":" + min;
    }

    public static before(da: string, a: string):boolean{
        if (da == null || da.length == 0 || a == null || a.length == 0)
             return false;
        let dateDa: Date = this.convertToDateFormat(da, "DD/MM/YYYY");
        let dateA: Date = this.convertToDateFormat(a, "DD/MM/YYYY");

        if(dateDa > dateA){
            return false;
        }else{
            return true; 
        }

    }

    public static removeMillisec(d: Date): string {
        if (d == null) return;
        else {
            return d.toLocaleString().substring(0, d.toLocaleString().length - 3);
        }
    }

    public static calcolaGiorniMese(dStart: Date): Array<Number> {
        let allDay = new Array<Number>();
        let getDaysInMonth = new Date(dStart.getFullYear(), dStart.getMonth() + 1, 0).getDate();

        //seleziono il numero di giorni
        let tempDayOfMoth = CalendarConstants.day.slice();
        let lenght = tempDayOfMoth.length;
        while (getDaysInMonth < lenght) {
            lenght--;
            tempDayOfMoth.pop();
        }

        //costruisco il giorno di partenza
        let firstDayCalendar = 1;
        let dayOfWeek = CalendarConstants.weekday[dStart.getDay()];
        while (dayOfWeek.id != firstDayCalendar) {
            allDay.push(null);
            if (firstDayCalendar == 7) firstDayCalendar = 0;
            else firstDayCalendar++;
        }

        return allDay.concat(tempDayOfMoth);
    }

    public static calcolaGiorniSettimana(dActual: Date): Array<Date> {
        let allDateOfWeekPostDay: Array<Date> = new Array<Date>();
        let allDateOfWeekPrecDay: Array<Date> = new Array<Date>();
        let dActualTmp = new Date(dActual);

        allDateOfWeekPostDay.push(new Date(dActualTmp));
        while (CalendarConstants.weekday[dActualTmp.getDay()].id != 0) {
            dActualTmp.setDate(dActualTmp.getDate() + 1);
            allDateOfWeekPostDay.push(new Date(dActualTmp));
        }

        if (allDateOfWeekPostDay.length != 7) {
            dActualTmp = new Date(dActual);
            while (CalendarConstants.weekday[dActualTmp.getDay()].id != 1 && (allDateOfWeekPostDay.length + allDateOfWeekPrecDay.length) != 7) {
                dActualTmp.setDate(dActualTmp.getDate() - 1);
                allDateOfWeekPrecDay.push(new Date(dActualTmp));
            }
        }

        return allDateOfWeekPrecDay.concat(allDateOfWeekPostDay).sort((a, b) => { return a.getTime() - b.getTime() });
    };
}