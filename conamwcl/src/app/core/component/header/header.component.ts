import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { Component } from "@angular/core";




@Component({
  selector: 'header-core',
  templateUrl: './header.component.html'
})
@DestroySubscribers()
export class HeaderComponent {

  public subscribers: any = {};

  constructor() {
  }

}