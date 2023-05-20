import { Component, Input, OnInit } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { Subject } from "rxjs";
import { FormBuilder, Validators } from "@angular/forms";
import {
  CalendarEventVO,
  CalendarConstants,
  Color,
} from "../commons/constants-calendar";
import { CalendarUtils } from "../commons/calendar-utils";
import { DestroySubscribers } from "../../../../../core/decorator/destroy-subscribers";
import {
  RegioneVO,
  ProvinciaVO,
  ComuneVO,
  SelectVO,
} from "../../../../../commons/vo/select-vo";
import { LoggerService } from "../../../../../core/services/logger/logger.service";
import { UserService } from "../../../../../core/services/user.service";
import { LuoghiService } from "../../../../../core/services/luoghi.service";
import { E } from "@angular/core/src/render3";

//Con bootstap va fatto cosi
declare var jquery: any;
declare var $: any;

/*
* IMPORTARE COMPONENTE IN PAGINA
* AGGIUNGERE
* @ViewChild(IrbaDialog) irbaDialog: IrbaDialog;
* AZIONE DI SALVATAGGIO
* this.irbaDialog.salva.subscribe(data=>{
     
    });
* COME APRIRLO
  open() {
    this.irbaDialog.open();
  }
*/
@Component({
  selector: "calendar-dialog",
  templateUrl: "./calendar-dialog.component.html",
  styleUrls: ["./calendar-dialog.component.css"],
})
@DestroySubscribers()
export class CalendarDialogComponent implements OnInit {
  @Input() titolo: string;
  @Input() buttonConfirmText: string;
  @Input() buttonAnnullaText: string;
  @Input() event: CalendarEventVO;

  public subscribers: any = {};
  public cfProprietario: string;

  color: Array<Color> = CalendarConstants.colorList;
  public loaderRegioni: boolean;
  public loaderProvince: boolean;
  public loaderComuni: boolean;
  public regioneModel: Array<RegioneVO> = new Array<RegioneVO>();
  public provinceModel: Array<ProvinciaVO> = new Array<ProvinciaVO>();
  public comuniModel: Array<ComuneVO> = new Array<ComuneVO>();
  public flagButtonAnnulla: boolean;
  public reminderData: any;
  public documentazioneAdvanceDay: number;
  public udienzaAdvanceDay: number;
  public defaultDocumentazioneAdvanceDay: number;
  public defaultUdienzaAdvanceDay: number;

  insertEditEventForm = this.fb.group({
    dataInizio: [{ value: "dataInizio", disabled: false }, Validators.required],
    dataFine: [{ value: "dataFine", disabled: false }, Validators.required],
    tribunale: [{ value: "tribunale", disabled: true }, Validators.required],
    nomeGiudice: [
      { value: "nomeGiudice", disabled: true },
      Validators.required,
    ],
    cognomeGiudice: [
      { value: "cognomeGiudice", disabled: true },
      Validators.required,
    ],
    nomeFunzionarioSanzionatore: [
      { value: "", disabled: true },
      Validators.required,
    ],
    cognomeFunzionarioSanzionatore: [
      { value: "", disabled: true },
      Validators.required,
    ],
    regione: [{ value: "regione", disabled: true }, Validators.required],
    provincia: [{ value: "provincia", disabled: true }, Validators.required],
    comune: [{ value: "comune", disabled: true }, Validators.required],
    cap: [{ value: "cap", disabled: true }, Validators.required],
    via: [{ value: "via", disabled: true }, Validators.required],
    civico: [{ value: "civico", disabled: true }, Validators.required],
    sendPromemoriaUdienza: [
      { value: "sendPromemoriaUdienza", disabled: false },
      Validators.required,
    ],
    sendPromemoriaDocumentazione: [
      { value: "sendPromemoriaDocumentazione", disabled: false },
      Validators.required,
    ],
    emailGiudice: [
      { value: "emailGiudice", disabled: false },
      Validators.required,
    ],
    documentazioneAdvanceDay: [
      { value: "dateDepositoDocumenti", disabled: false },
      Validators.required,
    ],
    udienzaAdvanceDay: [
      { value: "udienzaAdvanceDay", disabled: false },
      Validators.required,
    ],
    note: [{ value: "note", disabled: true }],
    color: [{ value: "color", disabled: true }, Validators.required],
  });

  ngOnInit(): void {
    this.loadRegions();
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (data) => {
        if (data != null) {
          this.cfProprietario = data.codiceFiscale;
        }
      }
    );
    this.loaderProvince = true;
    this.loaderComuni = true;
    if (!this.titolo) this.titolo = "";
    if (!this.buttonAnnullaText) this.buttonAnnullaText = "No";
    if (!this.buttonConfirmText) this.buttonConfirmText = "Si";
    if (!this.event) {
      this.event = new CalendarEventVO();
      this.event.sendPromemoriaDocumentazione = false;
      this.event.sendPromemoriaUdienza = false;
    }

    if (!this.event.documentazioneAdvanceDay && !this.event.udienzaAdvanceDay) {
      const ctrlemailGiudice = this.insertEditEventForm.get("emailGiudice");
      const ctrludienzaAdvanceDay = this.insertEditEventForm.get(
        "udienzaAdvanceDay"
      );
      const ctrldocumentazioneAdvanceDay = this.insertEditEventForm.get(
        "documentazioneAdvanceDay"
      );
      ctrlemailGiudice.disable();
      ctrludienzaAdvanceDay.disable();
      ctrldocumentazioneAdvanceDay.disable();
    }

    if (!this.event.documentazioneAdvanceDay || !this.event.udienzaAdvanceDay) {
      this.luoghiService.getReminderData().subscribe((data) => {
        this.defaultDocumentazioneAdvanceDay = data.giorniMailDocumentazione;
        this.defaultUdienzaAdvanceDay = data.giorniMailUdienza;
      });
    }
  }

  checkValue() {
    if (this.event) {
      if (
        this.event.sendPromemoriaDocumentazione === undefined ||
        this.event.sendPromemoriaDocumentazione === undefined
      )
        this.event.sendPromemoriaDocumentazione = false;
      if (
        this.event.sendPromemoriaUdienza === undefined ||
        this.event.sendPromemoriaUdienza === undefined
      )
        this.event.sendPromemoriaUdienza = false;
      const ctrldataInizio = this.insertEditEventForm.get("dataInizio");
      const ctrldataFine = this.insertEditEventForm.get("dataFine");
      const ctrltribunale = this.insertEditEventForm.get("tribunale");
      const ctrlnomeGiudice = this.insertEditEventForm.get("nomeGiudice");
      const ctrlcognomeGiudice = this.insertEditEventForm.get("cognomeGiudice");
      const ctrlregione = this.insertEditEventForm.get("regione");
      const ctrlprovincia = this.insertEditEventForm.get("provincia");
      const ctrlcomune = this.insertEditEventForm.get("comune");
      const ctrlcap = this.insertEditEventForm.get("cap");
      const ctrlvia = this.insertEditEventForm.get("via");
      const ctrlcivico = this.insertEditEventForm.get("civico");
      const ctrlnote = this.insertEditEventForm.get("note");
      const ctrlcolor = this.insertEditEventForm.get("color");

      if (this.event.codiceFiscaleProprietario != this.cfProprietario) {
        ctrltribunale.disable();
        ctrlnomeGiudice.disable();
        ctrlcognomeGiudice.disable();
        ctrlregione.disable();
        ctrlcap.disable();
        ctrlvia.disable();
        ctrlcivico.disable();
        ctrlnote.disable();
        ctrlcolor.disable();
      } else {
        ctrltribunale.enable();
        ctrlnomeGiudice.enable();
        ctrlcognomeGiudice.enable();
        ctrlregione.enable();
        ctrlcap.enable();
        ctrlvia.enable();
        ctrlcivico.enable();
        ctrlnote.enable();
        ctrlcolor.enable();
      }

      if (
        this.event.regione != null &&
        this.event.codiceFiscaleProprietario == this.cfProprietario
      ) {
        ctrlprovincia.setValidators(Validators.required);
        ctrlprovincia.enable();
      } else {
        ctrlprovincia.disable();
      }

      if (
        this.event.provincia != null &&
        this.event.codiceFiscaleProprietario == this.cfProprietario
      ) {
        ctrlcomune.setValidators(Validators.required);
        ctrlcomune.enable();
      } else {
        ctrlcomune.disable();
      }
    }
  }

  //APRE
  public open() {
    $("#myModal").modal("show");
  }
  //CHIUDE
  public close() {
    this.provinceModel = new Array<ProvinciaVO>();
    this.comuniModel = new Array<ComuneVO>();
    $("#myModal").modal("hide");
  }

  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private saveSubject = new Subject<Boolean>();
  public salvaAction: Observable<Boolean> = this.saveSubject.asObservable();

  private saveS(): void {
    this.event.dataInizio = CalendarUtils.convertToDate(this.dateStart);
    this.event.dataFine = CalendarUtils.convertToDate(this.dateEnd);
    this.close();
    this.saveSubject.next(true);
  }

  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private closeSubject = new Subject<Boolean>();
  public closeAction: Observable<Boolean> = this.closeSubject.asObservable();

  closeS(): void {
    this.close();
    this.closeSubject.next(true);
  }

  private XSubject = new Subject<Boolean>();
  public XAction: Observable<Boolean> = this.XSubject.asObservable();

  closeX(): void {
    this.close();
    this.XSubject.next(true);
  }

  //DATEPICKER
  ngAfterViewChecked() {
    this.manageDatePicker();
  }

  manageDatePicker() {
    if ($("#datetimepicker").length > 0) {
      $("#datetimepicker").datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
        stepping: 15,
        locale: "it",
      });
    }
    if ($("#datetimepicker1").length > 0) {
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
        stepping: 15,
        locale: "it",
      });
    }
  }

  dateStart: string;
  dateEnd: string;

  ngOnChanges() {
    if (this.event != null) {
      if (this.event.dataInizio != null) {
        this.dateStart = CalendarUtils.removeMillisec(this.event.dataInizio);
      }
      if (this.event.dataFine != null) {
        this.dateEnd = CalendarUtils.removeMillisec(this.event.dataFine);
      } else this.dateEnd = null;

      this.checkValue();

      if (this.event.id) this.flagButtonAnnulla = false;
      else this.flagButtonAnnulla = true;
    }
  }

  setDate(date: string, param) {
    if (param == "start") {
      this.dateStart = date;
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
      });
    } else if (param == "end") {
      this.dateEnd = date;
      $("#datetimepicker").datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
      });
    }

    let dEnd: Date = CalendarUtils.convertToDate(this.dateEnd);
    let dStart: Date = CalendarUtils.convertToDate(this.dateStart);
    if (dEnd == null || dStart == null) return;
    if (
      dStart.getFullYear() == dEnd.getFullYear() &&
      dStart.getDate() == dEnd.getDate() &&
      dEnd.getMonth() == dStart.getMonth()
    )
      return;
    else {
      if (param == "end") {
        dEnd.setHours(9, 1);
        this.dateStart = CalendarUtils.removeMillisec(dEnd);
      } else if (param == "start") {
        dStart.setHours(23, 59);
        this.dateEnd = CalendarUtils.removeMillisec(dStart);
      }
    }
  }

  loadRegions() {
    this.loaderRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.regioneModel = data;
        this.loaderRegioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  loadProvince(id: number) {
    this.insertEditEventForm.get("provincia").setValue(null);
    this.loaderProvince = false;
    this.subscribers.provinceByRegione = this.luoghiService
      .getProvinciaByIdRegione(id)
      .subscribe(
        (data) => {
          this.comuniModel = null;
          this.checkValue();
          this.provinceModel = data;
          this.loaderProvince = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle province");
        }
      );
  }

  loadComuni(id: number) {
    this.insertEditEventForm.get("comune").setValue(null);
    this.loaderComuni = false;
    this.subscribers.comuniByProvince = this.luoghiService
      .getComuneByIdProvincia(id)
      .subscribe(
        (data) => {
          this.comuniModel = data;
          this.loaderComuni = true;
          this.checkValue();
        },
        (err) => {
          this.logger.error("Errore nel recupero delle comuni");
        }
      );
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  manageLuoghi(event) {
    if (event.regione != null && event.regione.id != null) {
      this.loadProvince(event.regione.id);
      if (event.provincia != null && event.provincia.id != null)
        this.loadComuni(event.provincia.id);
    }
  }

  managePromemoriaUdienza(value: boolean) {
    const ctrlemailGiudice = this.insertEditEventForm.get("emailGiudice");
    const ctrludienzaAdvanceDay = this.insertEditEventForm.get(
      "udienzaAdvanceDay"
    );
    if (!this.event.udienzaAdvanceDay) {
      this.event.udienzaAdvanceDay = this.defaultUdienzaAdvanceDay;
    }
    if (value) {
      ctrlemailGiudice.setValidators(Validators.required);
      ctrlemailGiudice.enable();
      ctrludienzaAdvanceDay.setValidators(Validators.required);
      ctrludienzaAdvanceDay.enable();
      ctrludienzaAdvanceDay.setValue(this.event.udienzaAdvanceDay);
    } else {
      ctrlemailGiudice.setValue(null);
      ctrlemailGiudice.disable();
      ctrludienzaAdvanceDay.setValue(null);
      ctrludienzaAdvanceDay.disable();
    }
  }
  managePromemoriaDocumentazione(value: boolean) {
    const ctrldocumentazioneAdvanceDay = this.insertEditEventForm.get(
      "documentazioneAdvanceDay"
    );
    if (!this.event.documentazioneAdvanceDay) {
      this.event.documentazioneAdvanceDay = this.defaultDocumentazioneAdvanceDay;
    }
    if (value) {
      ctrldocumentazioneAdvanceDay.setValidators(Validators.required);
      ctrldocumentazioneAdvanceDay.enable();
      ctrldocumentazioneAdvanceDay.setValue(
        this.event.documentazioneAdvanceDay
      );
    } else {
      ctrldocumentazioneAdvanceDay.disable();
      ctrldocumentazioneAdvanceDay.setValue(null);
    }
  }

  ngOnDestroy() {}

  constructor(
    private fb: FormBuilder,
    private luoghiService: LuoghiService,
    private logger: LoggerService,
    private userService: UserService
  ) {}
}
