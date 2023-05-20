export interface Column extends Configurable {
  /**
   * Nome proprietà dell' oggetto da stampare in tabella
   */
  columnName: string;
  /**
   * Nome header
   */
  displayName: string;
  /**
   * Ordinamento iniziale.
   * Valori validi:
   * 
   * - 0 : senza ordinamento
   * - 1 : ascendente
   * - -1 : discendente
   */
  order?: number;
  /**
   * Opzione per gestire valore come link `<a>` o `<button>`.
   */
  link?: Link;


  /**
   * gestione css da applicare alla colonna`.
   */
  class?: string;

}

/**
 * Opzioni per trasformare la colonna in un link `<a>` o `<button>` per download di un file
 */
export interface Link extends Configurable {
  /**
   * Nome proprietà da cui recuperare il nome del file
   */
  fileName?: string;
  /**
   * Nome proprietà da cui ottenere l'url per scaricare il file.
   * Detto `obj` l'oggetto che rappresenta una riga in tabella, se il valore di `obj[url] == null`
   * allora al click del link di download la datatable emetterà un evento `download` con argomento l'oggetto `obj`.
   * Utilizzare ad esempio:
   * ```html
   * <datatable [data]="data" [config]="config" (download)="doDownload($event)"></datatable>
   * ```
   * per gestire il click sul link.
   * 
   * Può essere creato un url da un oggetto di tipo `File` chiamando il metodo creaUrl definito nel service DatatableService
   * già disponibile all'import del modulo di datatable:
   * ```javascript
   * // ad esempio in *.component.ts
   * let url = this.datatableService.creaUrl(file);
   * ...
   * // costruttore del componente
   * constructor(private datatableService : DatatableService) { }
   * ```
   */
  url?: string;
}

/**
 * Opzioni di paginazione
 */
export interface PaginationOptions extends Configurable {
  /**
   * lista di possibili scelte di quanti record visualizzare in ogni pagina della tabella
   * ### Example
   * ```javascript
   *  pagination : {
   *    enable : true,
   *    perPages : [5,15,25,50]
   *  }
   * ```
   */
  perPages?: number[];
  /**
   * quanti link di pagina 'successive' o 'precedenti' visualizzare rispettivamente a destra e a sinistra della pagina corrente
   */
  range?: number;
}

/**
 * Opzioni di filtro globale
 */
export interface FilterOptions extends Configurable {

}

/**
 * Opzioni per sort delle colonne
 */
export interface SortOptions extends Configurable {

}

/**
 * Opzioni per la selezione singola / multipla dei record in tabella.
 * Default è selezione singola. Scegliere esplicitamente il tipo di selezione richiesta
 * attravero la proprietà `selectionType`
 */
export interface SelectionOptions extends Configurable {
  /** 
   * Tipo di selezione:
   * - 0 per singola (default)
   * - 1 per multipla
   * 
   * Preferire la enum `SelectionType` per indicare il tipo di selezione
   */
  selectionType?: number;
  /** 
   * fa comparire o scoparire il radio button o checkbox
   */
  isSelectable?: (any) => boolean;
}

/** 
 * Tipo di eliminazione:
 * -internal = true -> viene gestita dalla datatable
 * -internal = false -> va gestita dalla componente chiamante
 */
export interface DeleteOptions extends Configurable {
  internal?: boolean;
  isDeletable?: (any) => boolean;
}

/**
 * Opzioni per il pulsante info
 */
export interface InfoOptions extends Configurable {
  hasInfo?: (any) => boolean;
}

/**
 * Configurazione per il bottone 'dettaglio' cliccabile se selezionato una e una sola riga
 */
export interface ButtonDataTable extends Configurable {
  /*abilita o disabilita il button in base a funzione */
  showDetail?: (any) => boolean;
  /** Label da dare al bottone */
  label: string;
}

export interface Configurable {
  /** Abilita all'utilizzo della configurazione */
  enable?: boolean;
}

/** 
* Edit Option
*/
export interface EditOptions extends Configurable {
  isEditable?: (any) => boolean;
}