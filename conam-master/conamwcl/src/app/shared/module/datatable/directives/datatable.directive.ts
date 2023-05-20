import { Directive, Input, ViewContainerRef, Renderer2, OnInit, HostListener, Output, EventEmitter, ElementRef, OnChanges, SimpleChanges } from "@angular/core";
import { UtilsService } from "../services/utilities";

@Directive({
  selector: '[appDatatable]'
})
export class DatatableDirective implements OnInit, OnChanges {

  private _DEF_CLASS: string = "glyphicon-sort";
  private _ASC_CLASS: string = "glyphicon-sort-by-attributes";
  private _DESC_CLASS: string = "glyphicon-sort-by-attributes-alt";

  @Input() col: string;
  @Input() order: number;
  @Output() sort: EventEmitter<{ columnName: string, order: number }> = new EventEmitter();

  ngOnInit() {
    this.renderer.addClass(this.element.nativeElement, "glyphicon");
    this.renderer.addClass(this.element.nativeElement, this._DEF_CLASS);
  }
  ngOnChanges(changes: SimpleChanges) {
    this.setOrder(this.order);
  }

  private setOrder(order: number) {
    if (order === 0) {
      this.renderer.addClass(this.element.nativeElement, this._DEF_CLASS);
      this.renderer.removeClass(this.element.nativeElement, this._DESC_CLASS);
      this.renderer.removeClass(this.element.nativeElement, this._ASC_CLASS);
    } else if (order === 1) {
      this.renderer.removeClass(this.element.nativeElement, this._DEF_CLASS);
      this.renderer.removeClass(this.element.nativeElement, this._DESC_CLASS);
      this.renderer.addClass(this.element.nativeElement, this._ASC_CLASS);
    } else {
      this.renderer.removeClass(this.element.nativeElement, this._DEF_CLASS);
      this.renderer.addClass(this.element.nativeElement, this._DESC_CLASS);
      this.renderer.removeClass(this.element.nativeElement, this._ASC_CLASS);
    }
  }

  constructor(private element: ElementRef, private renderer: Renderer2) { }

  @HostListener('click') onClick() {
    let order = 0;
    if (this.order === 0) {
      order = 1;
      this.setOrder(order);
    }
    else if (this.order === 1) {
      order = -1;
      this.setOrder(order);
    } else {
      order = 0;
      this.setOrder(order);
    }
    this.sort.emit({
      'columnName': this.col,
      'order': order
    });
  }
}
