import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
  OnChanges,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ModalitaNotificaVO, SelectVO } from "../../../commons/vo/select-vo";
import { NotificaVO } from "../../../commons/vo/notifica/notifica-vo";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { SharedNotificaService } from "../../services/shared-notifica.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";

declare var $: any;

@Component({
  selector: "shared-inserimento-notifica",
  templateUrl: "./shared-inserimento-notifica.component.html",
})
export class SharedInserimentoNotificaComponent
  implements OnInit, OnDestroy, OnChanges
{
  public subscribers: any = {};

  public loadedModalita: boolean;
  public loaded: boolean = true;

  public notifica: NotificaVO = new NotificaVO();
  public modalitaModel: Array<ModalitaNotificaVO>;

  //JIRA - Gestione Notifica
  private modalitaModelAll: Array<ModalitaNotificaVO>;
  private makeNotifyTRUE = [1, 2];

  public notificata: boolean;

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;

  @Input()
  idOrdinanza: number;
  @Input()
  idPiano: number;
  @Input()
  idSollecito: number;

  @Input()
  piano: PianoRateizzazioneVO;

  @Output()
  save: EventEmitter<number> = new EventEmitter<number>();

  @Output()
  importNotificaInserito: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(
    private logger: LoggerService,
    private sharedNotificaService: SharedNotificaService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    //if (this.idOrdinanza == null && this.idPiano == null && this.idSollecito == null) throw new Error("idOrdinanza - idPiano -idSollecito non valorizzato");
    this.logger.init(SharedInserimentoNotificaComponent.name);
    this.logger.error("idOrdinanza - idPiano -idSollecito non valorizzato");
    this.loadNotifica();
    this.loadModalita();
  }

  isToVisualize(): boolean {
    return this.idOrdinanza == null &&
      this.idPiano == null &&
      this.idSollecito == null
      ? false
      : true;
  }

  //JIRA - Gestione Notifica
  //---------------------------
  settaComboMadiltaByCodeModalitaNotifica(): void {
    if (this.modalitaModelAll != null || this.modalitaModelAll != undefined) {
      if (this.notifica == null || this.notifica == undefined) {
        this.modalitaModel = this.modalitaModelAll.filter(
          (modalita) => !this.makeNotifyTRUE.includes(modalita.id)
        );
        let notifivaVuota = new ModalitaNotificaVO();
        this.modalitaModel.push(notifivaVuota);
        this.notifica.modalita =
          this.modalitaModel[this.modalitaModel.length - 1];
      } else if (
        this.notifica.dataNotifica == undefined ||
        this.notifica.dataNotifica == ""
      ) {
        this.modalitaModel = this.modalitaModelAll.filter(
          (modalita) => !this.makeNotifyTRUE.includes(modalita.id)
        );
        let notifivaVuota = new ModalitaNotificaVO();
        this.modalitaModel.push(notifivaVuota);
        this.notifica.modalita =
          this.modalitaModel[this.modalitaModel.length - 1];
      } else {
        this.modalitaModel = this.modalitaModelAll.filter((modalita) =>
          this.makeNotifyTRUE.includes(modalita.id)
        );
      }
    }
  }

  loadNotifica() {
    this.loaded = false;
    if (this.idOrdinanza || this.idPiano || this.idSollecito) {
      this.subscribers.getModalitaNotifica = this.sharedNotificaService
        .getNotificaBy(this.idOrdinanza, this.idPiano, this.idSollecito)
        .subscribe(
          (data) => {
            this.notifica = data;
            this.loaded = true;
            if (this.notifica != null || this.notifica != undefined) {
              if (
                this.notifica.modalita != null ||
                this.notifica.modalita != undefined
              ) {
                if (
                  this.notifica.importoSpeseNotifica != null ||
                  this.notifica.importoSpeseNotifica != undefined
                ) {
                  this.notificata = true;
                } else {
                  this.notificata = false;
                }
              } else {
                if (
                  this.notifica.modalita &&
                  (this.notifica.modalita.id == 1 ||
                    this.notifica.modalita.id == 2)
                ) {
                  this.notificata = true;
                } else {
                  this.notificata = false;
                }
              }
            } else {
              this.notifica = new NotificaVO();
              this.notificata = false;
            }
            this.settaComboMadiltaByCodeModalitaNotifica();
          },
          (err) => {
            this.logger.error("Errore durante il recupero delle notifiche");
          }
        );
    } else {
      this.loaded = true;
    }
  }
  //--------------------------

  loadModalita() {
    this.loadedModalita = false;
    this.subscribers.getModalitaNotificaOrdinanza = this.sharedNotificaService
      .getModalitaNotifica()
      .subscribe(
        (data) => {
          this.modalitaModel = data;
          this.loadedModalita = true;

          //JIRA - Gestione Notifica
          //------------------------------------
          this.modalitaModelAll = data;
          this.settaComboMadiltaByCodeModalitaNotifica();
          //---------------------------------
        },
        (err) => {
          this.logger.error(
            "Errore nel recupero delle getModalitaNotificaOrdinanza"
          );
        }
      );
  }

  changeCheck() {
    if (!this.notificata) this.notifica.dataNotifica = null;
    else this.notifica.modalita = null;
  }

  getNotificaObject(): NotificaVO {
    return this.notifica;
  }

  ngAfterViewChecked() {
    this.manageDatePicker(null, 1, "DD/MM/YYYY");
    this.manageDatePicker(null, 2, "DD/MM/YYYY");
  }

  manageDatePicker(event: any, i: number, format: string) {
    var str: string = "#datetimepicker_" + i.toString();
    if ($(str).length && format != null) {
      $(str).datetimepicker({
        format: format,
      });
    }
    if (event != null && event.srcElement.value != null) {
      switch (i) {
        case 1:
          this.notifica.dataNotifica = event.srcElement.value;
          //JIRA - Gestione Notifica
          if (
            this.notifica.dataNotifica == undefined ||
            this.notifica.dataNotifica == ""
          ) {
            this.modalitaModel = this.modalitaModelAll.filter(
              (modalita) => !this.makeNotifyTRUE.includes(modalita.id)
            );
            let notifivaVuota = new ModalitaNotificaVO();
            this.modalitaModel.push(notifivaVuota);
            this.notifica.modalita =
              this.modalitaModel[this.modalitaModel.length - 1];
          } else {
            this.modalitaModel = this.modalitaModelAll.filter((modalita) =>
              this.makeNotifyTRUE.includes(modalita.id)
            );
            this.notifica.modalita = this.modalitaModel[0];
          }
          break;
        case 2:
          this.notifica.dataSpedizione = event.srcElement.value;
          break;
      }
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  salvaNotifica() {
    this.notifica.idOrdinanza = this.idOrdinanza;
    this.notifica.idPiano = this.idPiano;
    this.notifica.idSollecito = this.idSollecito;
    this.loaded = false;
    this.subscribers.regioni = this.sharedNotificaService
      .salvaNotifica(this.notifica)
      .subscribe(
        (idNotifica) => {
          this.loaded = true;
          this.save.emit(idNotifica);
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.loaded = true;
          this.logger.error("Errore durante il salvataggio");
        }
      );
  }

  //JIRA - Gestione Notifica
  onChangeImportoNotifica(event: Event, value: number) {
    if (value == undefined || value == null) {
      this.importNotificaInserito.emit(true);
    } else {
      this.importNotificaInserito.emit(isNaN(Number(value)));
    }
  }
  //-----------------------------

  isKeyPressed(code: number, label: string): boolean {
    if (label !== "Numero raccomandata") {
      return this.numberUtilsSharedService.numberValid(code);
    } else {
      return  (
        (code >= 48 && code <= 57) ||
        (code >= 96 && code <= 105) ||
        code == 8 ||
        code == 46
      );
    }
  }

  manageMessage(type: string, message: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }
  ngOnChanges() {
    this.loadNotifica();
  }
  ngOnDestroy(): void {
    this.logger.destroy(SharedInserimentoNotificaComponent.name);
  }
}
