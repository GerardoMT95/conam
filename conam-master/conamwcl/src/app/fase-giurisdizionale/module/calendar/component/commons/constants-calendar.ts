import { ComuneVO, ProvinciaVO, RegioneVO } from "../../../../../commons/vo/select-vo";

export class CalendarElement {
    constructor(public id: number, public descrizione: string) {
    }
}
export class Color {
    constructor(public nome: string, public valore: string) { }
}

export class ViewOption {
    constructor(public id: number,
        public descrizione: string) { }
}
export class CalendarConstants {
    public static weekday: Array<CalendarElement> = [
        new CalendarElement(0, "Domenica"),
        new CalendarElement(1, "Lunedì"),
        new CalendarElement(2, "Martedì"),
        new CalendarElement(3, "Mercoledì"),
        new CalendarElement(4, "Giovedì"),
        new CalendarElement(5, "Venerdì"),
        new CalendarElement(6, "Sabato")];

    public static months: Array<CalendarElement> =
        [
            new CalendarElement(0, "Gennaio"),
            new CalendarElement(1, "Febbraio"),
            new CalendarElement(2, "Marzo"),
            new CalendarElement(3, "Aprile"),
            new CalendarElement(4, "Maggio"),
            new CalendarElement(5, "Giugno"),
            new CalendarElement(6, "Luglio"),
            new CalendarElement(7, "Agosto"),
            new CalendarElement(8, "Settembre"),
            new CalendarElement(9, "Ottobre"),
            new CalendarElement(10, "Novembre"),
            new CalendarElement(11, "Dicembre")
        ];

    public static day: Array<Number> =
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31];

    public static hour: Array<Number> = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];

    public static MONTH_VIEW: ViewOption = new ViewOption(1, "Mese");
    public static DAY_VIEW: ViewOption = new ViewOption(2, "Giorno");
    public static WEEK_VIEW: ViewOption = new ViewOption(3, "Settimana");


    public static colorList: Array<Color> = [
        new Color("Rosso", "#f70500"),
        new Color("Blu", "#2c93ff"),
        new Color("Verde", "#adff2f"),
        new Color("Giallo", "#ffff00"),
        new Color("Arancio", "#ffa500"),
        new Color("Viola", "#cb2dff"),
        new Color("Rosa", "#ffcfee")];
};



export class CalendarEventVO {
    dataInizio: any;
    dataFine: any;
    tribunale: string;
    nomeGiudice: string;
    cognomeGiudice: string;
    nomeFunzionarioSanzionatore: string;
    cognomeFunzionarioSanzionatore: string;
    regione: RegioneVO;
    provincia: ProvinciaVO;
    comune: ComuneVO;
    cap: string;
    via: string;
    civico: string;
    sendPromemoriaUdienza: boolean;
    sendPromemoriaDocumentazione: boolean;
    emailGiudice: string;
    note: string;
    color: string;
    id: number;
    codiceFiscaleProprietario: string;
    udienzaAdvanceDay: any
    documentazioneAdvanceDay: any
    
}