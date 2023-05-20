import {
  Directive,
  ViewContainerRef,
  Component,
  Input,
  ViewChild,
  OnInit,
  Output,
  EventEmitter,
} from "@angular/core";
import { Observable } from "rxjs/Observable";
import { Subject } from "rxjs";
import { DestroySubscribers } from "../../../core/decorator/destroy-subscribers";
import { LoggerService } from "../../../core/services/logger/logger.service";

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
  selector: "shared-dialog",
  templateUrl: "./shared-dialog.component.html",
})
@DestroySubscribers()
export class SharedDialogComponent implements OnInit {
  @Input() testo: string;
  @Input() titolo: string;
  @Input() buttonConfirmText: string;
  @Input() buttonAnnullaText: string;
  @Input() subMessages: Array<string>;
  @Input() subLinks: Array<any>;
  @Input() hideButton: boolean;
  @Input() id: string = "myModal";
  @Output()
  clickLinkEvent: EventEmitter<any> = new EventEmitter<any>();

  ngOnInit(): void {
    if (!this.titolo) this.titolo = "titolo non definito";
    if (!this.buttonAnnullaText) this.buttonAnnullaText = "No";
    if (!this.buttonConfirmText) this.buttonConfirmText = "Si";
  }

  //APRE
  public open() {
    $("#" + this.id).modal("show");
  }
  //CHIUDE
  public close() {
    $("#" + this.id).modal("hide");
  }

  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private saveSubject = new Subject<Boolean>();
  public salvaAction: Observable<Boolean> = this.saveSubject.asObservable();

  private saveS(): void {
    this.close();
    this.saveSubject.next(true);
  }

  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private closeSubject = new Subject<Boolean>();
  public closeAction: Observable<Boolean> = this.closeSubject.asObservable();

  private closeS(): void {
    this.close();
    this.closeSubject.next(true);
  }

  private XSubject = new Subject<Boolean>();
  public XAction: Observable<Boolean> = this.XSubject.asObservable();

  private closeX(): void {
    this.close();
    this.XSubject.next(true);
  }

  public clickLink(l: any): void {
    this.clickLinkEvent.emit(l);
  }
}
