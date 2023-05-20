/*Angular module */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './component/home/home.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';





@NgModule({
  imports: [CommonModule, FormsModule, RouterModule, SharedModule],
  exports: [HomeComponent],
  declarations: [HomeComponent],
  providers: []
})
export class HomeModule { }
