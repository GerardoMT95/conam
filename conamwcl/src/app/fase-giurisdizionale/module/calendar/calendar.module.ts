import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CalendarComponent } from './component/calendar/calendar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CalendarDialogComponent } from './component/calendar-dialog/calendar-dialog.component';
import { HourPipe } from './component/pipe/hour-pipe';
import { CalendarShowDialogComponent } from './component/calendar-show-dialog/calendar-show-dialog.component';
import { SharedModule } from '../../../shared/shared.module';




@NgModule({
    imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, SharedModule],
    exports: [CalendarComponent],
    declarations: [CalendarComponent, CalendarDialogComponent, HourPipe, CalendarShowDialogComponent],
    providers: [],
})
export class CalendarModule { }