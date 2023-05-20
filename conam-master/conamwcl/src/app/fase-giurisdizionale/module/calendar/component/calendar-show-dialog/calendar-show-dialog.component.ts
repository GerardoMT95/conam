import { Component, Input, OnInit, Output, EventEmitter } from "@angular/core";
import { CalendarEventVO } from "../commons/constants-calendar";
import { DestroySubscribers } from "../../../../../core/decorator/destroy-subscribers";

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
  selector: "calendar-show-dialog",
  templateUrl: "./calendar-show-dialog.component.html",
  styleUrls: ["./calendar-show-dialog.component.css"],
})
@DestroySubscribers()
export class CalendarShowDialogComponent implements OnInit {
  @Input() event: Array<CalendarEventVO>;
  @Output() openEvent: EventEmitter<CalendarEventVO> = new EventEmitter();

  ngOnInit(): void {
    if (!this.event) this.event = new Array<CalendarEventVO>();
  }

  //APRE
  public open() {
    $("#showModal").modal("show");
  }
  //CHIUDE
  public close() {
    $("#showModal").modal("hide");
  }

  closeX(): void {
    this.close();
  }

  openDetailEvent(e: CalendarEventVO) {
    this.closeX();
    this.openEvent.emit(e);
  }

  ngOnDestroy() {}

  constructor() {}
}
