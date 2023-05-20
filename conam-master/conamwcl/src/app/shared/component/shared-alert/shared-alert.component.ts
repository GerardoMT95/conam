/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-06 11:19:52  */
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { LoggerService } from '../../../core/services/logger/logger.service';


@Component({
  selector: 'shared-alert',
  templateUrl: './shared-alert.component.html',
  styleUrls: ['./shared-alert.component.css']
})
export class SharedAlertComponent implements OnInit, OnDestroy {

  @Input('type') type: string;
  @Input('message') message: string;

  classSpan: string;
  class: string;
  esito: string;

  ngOnInit(): void {
    this.loadMessaggi();
  }


  ngOnDestroy() {

  }

  ngAfterViewChecked(){
    this.loadMessaggi();
  }

  loadMessaggi(){
    if (this.type) {
      if (this.type.toUpperCase() == TypeAlert.SUCCESS) {
        this.esito = "Success";
        this.class = "alert-success";
        this.classSpan = "glyphicon glyphicon-ok-sign";
      }
      else if (this.type.toUpperCase() == TypeAlert.DANGER) {
        this.esito = "Errore";
        this.class = "alert-danger";
        this.classSpan = "glyphicon glyphicon-exclamation-sign"; 
      }
      else if (this.type.toUpperCase() == TypeAlert.INFO) {
        this.esito = "Info";
        this.class = "alert-info "
        this.classSpan = "glyphicon glyphicon-info-sign";
      }
      else if (this.type.toUpperCase() == TypeAlert.WARNING) {
        this.esito = "Attenzione";
        this.class = "alert-warning"
        this.classSpan = "glyphicon glyphicon-warning-sign";
      }
      else
        throw new Error("type sconosciuto");
    }
    else
      throw new Error("type sconosciuto");
  }

}

  

export enum TypeAlert {
  SUCCESS = "SUCCESS",
  DANGER = "DANGER",
  INFO = "INFO",
  WARNING = "WARNING",
}