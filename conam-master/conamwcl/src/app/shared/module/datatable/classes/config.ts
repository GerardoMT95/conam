import { Column, PaginationOptions, SelectionOptions, SortOptions, FilterOptions, ButtonDataTable, DeleteOptions, InfoOptions, EditOptions } from "./settings";

/**
 * Oggetto di configurazione datatable.
 * Permette di definire le colonne da visualizzare in tabella 
 * abilitare le funzione di paginazione, sort, filtro globale ecc., ecc.
 * 
 * ### Example 
 * ```javascript
 * this.config = {
 *  sort : {
 *    enable : true
 *  },
 *  selection : {
 *    enable : true,
 *    selectionType : SelectionType.MULTIPLE
 *  }
 *  columns : [{
 *    columnName : 'prop1',
 *    displayName: 'Nome'
 *  }, {
 *    columnName : 'prop2',
 *    displayName: 'Cognome'
 *  }]
 * }
 * ```
 */
export interface Config {
  /**
   * Array delle colonne da visualizzare in tabella
   */
  columns: Array<Column>,
  /**
   * Configurazione della funzione di paginazione. Default : disabilitata
   */
  pagination?: PaginationOptions,
  /**
   * Configurazione della funzione di selezione singola/multipla. Default : disabilitata
   */
  selection?: SelectionOptions,
  /**
  * Configurazione della funzione di eliminazione. Default : disabilitata
  */
  delete?: DeleteOptions,
  /**
   * Configurazione della funzione di info. Default : disabilitata
   */
  info?: InfoOptions,
  /**
   * Configurazione della funzione di sort delle colonne. Default : disabilitata
   */
  sort?: SortOptions,
  /**
   * Configurazione della funzione di filtro globale. Default : disabilitata
   */
  filter?: FilterOptions,
  /**
   * Configurazione della funzione di bottone di 'dettaglio' di record selezionato dall'utente. Default : disabilitata
   */
  buttonSelection?: ButtonDataTable,
  /**
   * Configurazione della funzione di bottone di 'modifica' di record selezionato dall'utente. Default : disabilitata
   */
  edit?: EditOptions
  /**
   * Configurazione del campo che viene utilizzato per comparare con i selezionati mandati alla tabella da esterno della componente. Default : nessuna comparatore
   */
  comparatorField?: string;
}