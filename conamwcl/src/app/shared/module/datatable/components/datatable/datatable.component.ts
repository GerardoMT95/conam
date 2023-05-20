import { Component, OnInit, Input, Output, EventEmitter, OnChanges, OnDestroy, SimpleChanges, Inject } from '@angular/core';
import { Config } from '../../classes/config';
import { UtilsService } from '../../services/utilities';
import { SelectionType } from '../../classes/selection-type';



@Component({
    selector: 'datatable',
    templateUrl: './datatable.component.html',
    styleUrls: ['./datatable.component.css']
})
export class DataTableComponent implements OnInit, OnChanges, OnDestroy {

    @Input() public data: any[]; // dati da mostrare in tabella
    @Input() public config: Config;  // Definizione colonne da visualizzare e altre opzioni tabella


    @Input() public dataSelected: any[];

    // Evento con trigger la selezione di un record
    @Output('selected') public selected = new EventEmitter<any | Array<any>>();

    @Output('dettaglio') public dettaglio = new EventEmitter<any | Array<any>>();

    @Output('download') public download = new EventEmitter<any>();

    @Output('onDelete') public onDelete = new EventEmitter<any>();

    @Output('onEdit') public onEdit = new EventEmitter<any>();

    @Output('onInfo') public onInfo = new EventEmitter<any>();

    public order: any;
    public _origData: any[];

    public searchText: string;

    private selectedRowsIndexes: Array<number>;

    // ID univoco componente
    private _id: string;
    public get id() {
        return this._id;
    }

    // Gestione paginazione
    public pagination: any = {
        page: 1,
        perPage: 10,
        startIndex: 0,
        endIndex: 9,
        mRange: 1,
        perPages: [5, 10, 20, 50],
        loading: false,
        next: (): void => this.pagination.change(this.pagination.page + 1)
        ,
        previous: (): void => this.pagination.change(this.pagination.page - 1)
        ,
        first: () => this.pagination.change(1),
        last: () => this.pagination.change(this.pagination.getTotalPages()),
        change: i => {
            var isNew = false;
            if (!i)
                i = this.pagination.page;

            if (i != this.pagination.page) {
                isNew = true;
            }
            this.pagination.page = i;
            if (this.pagination.perPage < 1) this.pagination.perPage = 1;
            this.pagination.startIndex = (this.pagination.page - 1) * this.pagination.perPage;
            this.pagination.endIndex = this.pagination.page * this.pagination.perPage - 1;
            if (this.pagination.endIndex > this.pagination.getSourceLength())
                this.pagination.endIndex = this.pagination.getSourceLength() - 1;
            if (isNew) {
                this.pagination.pageChangeCallback();
            }
        },
        showIndex: i => i >= this.pagination.startIndex && i <= this.pagination.endIndex
        ,
        range: (startAt: number = 0, endAt: number = 0) => Array(endAt - startAt + 1).fill(0).map((i, index) => startAt + index)
        ,
        getPages: (totalNumber?) =>
            this.pagination.range(-this.pagination.mRange, this.pagination.mRange)
                .map(off => this.pagination.getCurrentPage() + off)
                .filter(page => this.pagination.isValidPage(page))
        ,
        isValidPage: page => page > 0 && page <= this.pagination.getTotalPages()
        ,
        getTotalPages: (totalNumber?) => {
            if (!totalNumber)
                totalNumber = this.pagination.getSourceLength();
            if (totalNumber < 1)
                return 0;
            return Math.ceil(Math.abs(totalNumber / this.pagination.perPage));
        },
        allowNext: (totalNumber: number): boolean => {
            if (!totalNumber) totalNumber = this.pagination.getSourceLength();
            return (this.pagination.page < (Math.abs(totalNumber / this.pagination.perPage)));
        },
        allowPrevious: (): boolean => this.pagination.page > 1
        ,
        isNeeded: totalNumber => {
            if (!this.config.pagination || !this.config.pagination.enable) return false;
            if (!totalNumber) totalNumber = this.pagination.getSourceLength();
            return totalNumber;
        },
        perPageChanged: (): void => {
            if (this.pagination.perPage < 1) this.pagination.perPage = 1;
            this.pagination.change(1);
        },
        getSourceLength: (): number => {
            var c = this.pagination.getSource();
            if (c === null || c === undefined) return 0;
            return c.length || 0;
        },
        hasNoElements: (): boolean => this.pagination.getSourceLength() === 0
        ,
        pageChangeCallback: (): void => {
            this.pagination.loading = true;
            setTimeout(() => this.pagination.loading = false, 300);
        },
        getCurrentPage: () => this.pagination.page
        ,
        getSource: () => this.data
    };

    // Gestione selezione singola/multipla
    public selection: any = {
        onSelection: (selected: any): void => {
            if (selected == null)
                return;
            this.selection._select(selected);
            this.selection._emit();
        },
        isSelected: (el: any): boolean => {
            let uid = this.selection._getUID(el);
            if (undefined === uid) return false;
            return this.selectedRowsIndexes.indexOf(uid) > -1;
        },
        hasSelected: (): boolean => {
            if (this.config.selection && this.config.selection.enable) {
                if (this.config.selection.selectionType == 0)
                    return this.selectedRowsIndexes.length === 1;
                else if (this.config.selection.selectionType == 1)
                    return this.selectedRowsIndexes.length >= 1;        //aggiunto da Matteo per Conam
            }
            return false;
        },
        dettaglio: () => {
            this.dettaglio.emit(this.selection._getSelectedRows());
        },
        checkAll: () => {
            if (this.config.pagination && this.config.pagination.enable) {
                this.data.filter((e, i) => this.pagination.showIndex(i))
            } else
                this.data.map((e, i) => i);
        },

        _getUID: (e: any): number => e.__id,
        _setUID: e => {
            let $id = this.selection._getUID(e);
            if (undefined === $id) {
                $id = this.utils.spawnNewUID();
                Object.defineProperty(e, '__id', {
                    value: $id,
                    enumerable: false
                });
            }
            return $id;
        },
        _emit: () => {
            this.selected.emit(this.selection._getSelectedRows());
        },
        _select: (el: any): void => {
            let $id = this.selection._setUID(el);
            if (this.config.selection.selectionType === SelectionType.MULTIPLE) {
                if (this.selection.isSelected(el))
                    this.selectedRowsIndexes.splice(this.selectedRowsIndexes.indexOf($id), 1);
                else
                    this.selectedRowsIndexes.push($id);
            } else {
                this.selectedRowsIndexes = [$id];
            }
        },
        _getSelectedRows: (): any | Array<any> => {
            let selected: any | Array<any>;
            if (this.config.selection && this.config.selection.enable && this.config.selection.selectionType === SelectionType.SINGLE) {
                selected = null;
                this.selectedRowsIndexes.forEach(v => selected = this.data.find(e => e.__id === v));
                //if (selected)
                //    delete selected.__id;
            } else {
                selected = [];
                this.selectedRowsIndexes.forEach(v => selected.push(this.data.find(e => e.__id === v)));
                //selected.forEach(e => delete e.__id);
            }
            return selected;
        },
        _resetSelection: (): void => {
            this.data
                .filter(e => e.__id !== undefined)
                .forEach(e => delete e.__id);
            this.selectedRowsIndexes = new Array<number>();
        }
    };
    sortChanged($event): void {
        this.config.columns.forEach(el => {
            if (el.columnName === $event.columnName)
                el.order = $event.order;
            else
                el.order = 0
        });
        this.order = $event;
    }

    filter($event): void {
        let items = this.data;
        this.selection._resetSelection();
        this.selected.emit(null);
        if (!this._origData) {
            this.data = [];
            return;
        }
        let searchText = this.searchText;
        let lengthChange = this._origData.length !== this.data.length;
        if (!searchText) {
            this.data = this._origData;
            if (lengthChange)
                this.pagination.change(1);
            return;
        }
        searchText = searchText.toLowerCase();
        this.data = this._origData.filter(it => {
            return this.check(it, searchText, null);
        });
        // Ritorna a pagina 1
        if (lengthChange)
            this.pagination.change(1);
    }

    check(it, searchText: string, previousProperty: string): boolean {
        let res = false;
        for (let s in it) {
            let el = it[s]; // Proprieta' s dell'oggetto it potrebbe essere a sua volta un oggetto
            if (typeof el === 'object') {
                res = this.check(el, searchText, previousProperty != null ? previousProperty.concat("." + s) : s);
                if (res)
                    return res;
            }

            // Controllo aggiunto per filtrare solo sui campi visibili in tabella (Matteo)
            let flag = false;
            this.config.columns.forEach(col => {
                if (col.columnName == (previousProperty != null ? previousProperty.concat("." + s) : s)) flag = true;
            });
            if (!flag) continue;
            //

            if (!el.toLowerCase)
                el = String(el);
            res = el.toLowerCase().includes(searchText);
            if (res)
                break;
        }
        return res;
    }

    delete(el: any) {
        if (this.config.delete.enable) {
            if (!this.config.delete.internal) {
                this.onDelete.emit(el);
            } else {
                if (this._origData) {
                    this._origData.splice(this._origData.indexOf(el), 1);
                    this.data = this._origData;
                } else {
                    this.data.splice(this.data.indexOf(el), 1);
                }
            }
        }
        if (!this.selectedRowsIndexes)
            this.selection._resetSelection();
    }

    edit(el: any) {
        if (this.config.edit.enable) {
            this.onEdit.emit(el);
        }
        if (!this.selectedRowsIndexes)
            this.selection._resetSelection();
    }

    mostraMotivazione(el: any) {
        if (this.config.info.enable) {
            this.onInfo.emit(el);
        }
    }

    trackByFn(index, item) {
        return item.id || index;
    }

    byString(o: any, s: string) {
        return this.utils.byString(o, s);
    }

    goDownload(el: any) {
        this.download.emit(el);
    }

    hasInfo(el: any): boolean {
        if (this.config.info.hasInfo)
            return this.config.info.hasInfo(el);
    }

    isSelectable(el: any): boolean {
        if (this.config.selection.isSelectable)
            return this.config.selection.isSelectable(el);
        else
            return true;
    }

    isDeletable(el: any): boolean {
        if (this.config.delete.isDeletable)
            return this.config.delete.isDeletable(el);
        else
            return true;
    }

    isEditable(el: any): boolean {
        if (this.config.edit.isEditable)
            return this.config.edit.isEditable(el);
        else
            return true;
    }

    showDetail(): boolean {
        if (this.config.buttonSelection.showDetail) {
            let obj = this.selection._getSelectedRows();
            if (obj != null && this.config.buttonSelection.showDetail)
                return this.config.buttonSelection.showDetail(obj);
            else return false;
        }
        return true;
    }


    ngOnInit() {
        this._id = this.utils.spawnId();

        // ENG per selezionare gli item sfuttiamo un metodo apposito
        this._setSelected();

      
        // Copia dati originali

        this._recheckDefaults();

        if (this.config && this.config.filter && this.config.filter.enable)
            this._origData = this.data;

        // Condizione default del sort
        this.order = {
            'columnName': null,
            'order': 0
        }
    }

	// ENG per per selezionare dall'esterno
    _setSelected(){
        if(this.config.comparatorField && this.dataSelected && this.dataSelected.length){

            let intersection = this.data.filter(a => this.dataSelected.some(b => a[this.config.comparatorField] === b[this.config.comparatorField]));  

            intersection.forEach(
                item=>{
                    this.selection._select(item);
                }
            )
        }
    }
    

    ngOnChanges(changes: SimpleChanges) {
        if (this.config && this.config.selection && this.config.selection.enable) {
            if (changes['data']) {
                this._origData = this.data;
                this.selection._resetSelection();
            }
			
			// ENG per per deselezionare dall'esterno
            if (changes['dataSelected']) {
             
                const current:any[] = changes['dataSelected'].currentValue as any[]; 
                const prev:any[] = changes['dataSelected'].previousValue as any[];
                if(current && prev && current.length<prev.length){
                    // deselezione in corso da esterno 
                    let differences = prev.filter(x => !current.includes(x));
                    let intersection = this.data.filter(a => differences.some(b => a[this.config.comparatorField] === b[this.config.comparatorField]));  
                    intersection.forEach(
                        item=>{
                            // se selezionato deseleziono
                            if (this.selection.isSelected(item)){
                                this.selection._select(item);
                            }
                        }
                    )
                }
            }
        }
        if (changes['config'])
            this._recheckDefaults();
    }

    _recheckDefaults() {
        if (!this.config)
            return;
        if (!this.config.buttonSelection)
            this.config.buttonSelection = { label: 'Dettaglio' };
        if (null == this.config.buttonSelection.enable)
            this.config.buttonSelection.enable = false;
        if (null == this.config.filter)
            this.config.filter = {};
        if (null == this.config.filter.enable)
            this.config.filter.enable = false;
        if (null == this.config.pagination)
            this.config.pagination = {};
        if (null == this.config.pagination.enable)
            this.config.pagination.enable = false;
        if (null == this.config.selection)
            this.config.selection = {};
        if (null == this.config.selection.enable)
            this.config.selection.enable = false;
        if (null == this.config.delete)
            this.config.delete = {};
        if (null == this.config.delete.enable)
            this.config.delete.enable = false;
        if (null == this.config.edit)
            this.config.edit = {};
        if (null == this.config.edit.enable)
            this.config.edit.enable = false;

        // Ricezione impostazioni opzionali di configurazione paginazione
        if (this.config.pagination && this.config.pagination.enable) {
            if (null != this.config.pagination.perPages  // impostato?
                && undefined !== this.config.pagination.perPages.length // è un oggetto con proprietà 'length'
                && 0 !== this.config.pagination.perPages.length) {
                this.pagination.perPages = this.config.pagination.perPages;
                this.pagination.perPage = this.config.pagination.perPages[1] || this.config.pagination.perPages[0];
                this.pagination.endIndex = this.pagination.perPage - 1;
            }
            if (undefined !== this.config.pagination.range && typeof this.config.pagination.range === 'number') {
                this.pagination.mRange = this.config.pagination.range;
            }
        }
        if (isNaN(+this.config.selection.selectionType) || (+this.config.selection.selectionType !== SelectionType.SINGLE && +this.config.selection.selectionType !== SelectionType.MULTIPLE)) {
            this.config.selection.selectionType = SelectionType.SINGLE;
        }
        if (!this.selectedRowsIndexes)
            this.selection._resetSelection();
    }

    ngOnDestroy() { 
    }

    constructor(
        public utils: UtilsService) { }
}