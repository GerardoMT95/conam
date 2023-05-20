import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DestroySubscribers } from './core/decorator/destroy-subscribers';
import { LoggerService } from './core/services/logger/logger.service';
import { windowWhen } from 'rxjs/operator/windowWhen';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
@DestroySubscribers()
export class AppComponent implements OnInit {

  public subscribers: any = {};


  constructor(private router: Router, private logger: LoggerService) {
  }

  ngOnInit(): void {
    this.logger.init(AppComponent.name);
  }

  onActivate(event){
    window.scroll(0,0);
  }

  ngOnDestroy(): void {
    this.logger.destroy(AppComponent.name);
  }


}