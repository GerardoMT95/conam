import { NgModule, ModuleWithProviders } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { DataTableComponent } from './components/datatable/datatable.component';
import { DatatableDirective } from './directives/datatable.directive';
import { ArraySortPipe } from './pipes/pipe.sort';
import { UtilsService } from './services/utilities';
import { DatatableService } from './services/datatable-service';

/**
 * Modulo per datatable custom. 
 * Importare con nell' array di imports di angular usando DataTableModule.forRoot()
 * ### Example 
 * ```javascript
 * imports: [BrowserModule, FormsModule, DataTableModule.forRoot()],
 * ```
 * per utilizzarla, scrivere nella pagina html
 * ```html
 * <datatable [data]="model" [config]="dtConfig"></datatable>
 * ```
 * dove "model" sarà l'array di oggetti da visualizzare in tabella
 * e dtConfig dovrà essere un oggetto di tipo `Config` che 
 * indicherà le varie proprietà da visualizzare nelle colonne in tabella
 * e se questa utilizzerà la paginazione, il sort, ecc. ecc.
 * Indicativamente, aggiungere nella *.component.ts
 * ```javascript
 * this.dtConfig = {
 *  pagination : {
 *    enable : true
 *  },
 *  columns:[
 *    {columnName:'prop1', displayName:'Nome'},
 *    {columnName:'prop2', displayName:'Cognome'},
 *    {columnName:'prop3', displayName:'Soprannome'},
 *  ]
 * }
 * ```
 */
@NgModule({
  declarations: [
    DataTableComponent,
    DatatableDirective,
    ArraySortPipe
  ],
  imports: [BrowserModule, FormsModule],
  exports: [DataTableComponent],
  providers: [UtilsService],
})
export class DataTableModule { 
  static forRoot(): ModuleWithProviders {
    return {
      ngModule : DataTableModule,
      providers : [DatatableService]
    }
  }
}

export { DatatableService } from './services/datatable-service';
export { SelectionType } from './classes/selection-type';