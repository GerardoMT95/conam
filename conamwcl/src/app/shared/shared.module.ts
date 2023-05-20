import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedSpinnerComponent } from './component/shared-spinner/shared-spinner.component';
import { SharedDialogComponent } from './component/shared-dialog/shared-dialog.component';
import { SharedAlertComponent } from './component/shared-alert/shared-alert.component';
import { SharedSpinnerFormComponent } from './component/shared-spinner-form/shared-spinner-form.component';
import { SharedBreadcrumbsComponent } from './component/shared-breadcrumbs/shared-breadcrumbs.component';
import { FormsModule } from '@angular/forms';
import { DataTableModule } from './module/datatable/datatable.module';
import { AllegatoSharedService } from './service/allegato-shared.service';
import { ConfigSharedService } from './service/config-shared.service';
import { DecimalDirective } from './directive/decimal.directive';
import { NumeroRateDirective } from './directive/numero-rate.directive';
import { SharedAllegatoMetadatiInserimentoComponent } from './component/shared-allegato-metadati-inserimento/shared-allegato-metadati-inserimento.component';
import { SharedAllegatoRicercaProtocolloComponent } from './component/shared-allegato-form-ricerca-protocollo/shared-allegato-form-ricerca-protocollo.component';
import { NumberUtilsSharedService } from './service/number-utils-shared.service';
import { AllegatoUtilsSharedService } from './service/allegato-utils-shared.service';
import { FileSelectDirective } from 'ng2-file-upload';
import { SharedAllegatoComponent } from './component/shared-allegato/shared-allegato.component';
import { SharedAllegatoDocumentoProtocollatoComponent } from './component/shared-allegato-documento-protocollato/shared-allegato-documento-protocollato.component';



@NgModule({
    imports: [CommonModule, RouterModule, DataTableModule.forRoot(), FormsModule],
    exports: [
        SharedSpinnerComponent,
        SharedDialogComponent,
        SharedSpinnerFormComponent,
        SharedAlertComponent,
        SharedBreadcrumbsComponent,
        DataTableModule,
        DecimalDirective,
        NumeroRateDirective,
        SharedAllegatoMetadatiInserimentoComponent,
        SharedAllegatoRicercaProtocolloComponent,
        SharedAllegatoComponent,
        SharedAllegatoDocumentoProtocollatoComponent,
        FileSelectDirective
    ],
    declarations: [SharedSpinnerComponent,
        SharedDialogComponent,
        SharedSpinnerFormComponent,
        SharedAlertComponent,
        SharedBreadcrumbsComponent,
        DecimalDirective,
        NumeroRateDirective,
        SharedAllegatoMetadatiInserimentoComponent,
        SharedAllegatoRicercaProtocolloComponent, 
        FileSelectDirective,
        SharedAllegatoComponent,
        SharedAllegatoDocumentoProtocollatoComponent
    ],
    providers: [AllegatoSharedService, ConfigSharedService, AllegatoUtilsSharedService, NumberUtilsSharedService],
})
export class SharedModule { }