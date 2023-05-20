/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-03 11:12:18  
 */
import { Component, OnInit, EventEmitter, Output, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { LoggerService } from '../../services/logger/logger.service';


@Component({
  selector: 'app-error',
  templateUrl: './error.component.html'
})
@DestroySubscribers()
export class ErrorComponent implements OnInit {

  public subscribers: any = {};
  public message: string;
  public cod: string;



  constructor(private route: ActivatedRoute, private router: Router, private location: Location, private logger: LoggerService) {

  }


  ngOnInit() {
    this.subscribers.router = this.route.queryParams.subscribe(queryParams => {
      this.cod = queryParams["err"];
      this.message = queryParams["message"];
      if (!this.cod && !this.message) {
        this.cod = "404";
        this.message = "L'url di richiesta " + this.location.path() + " non è stato trovato sul server. Questo è quello che so."
      }
    });

    this.subscribers.route = this.route.data.subscribe(data => {
      if (data["err"] != null) this.cod = data["err"];
      if (data["message"] != null) this.message = data["message"];
    });


  }



}
