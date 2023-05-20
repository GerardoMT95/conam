import { Component, OnInit, OnDestroy, Output, EventEmitter, Input, ViewChild } from "@angular/core";
import { MinSoggettoVO } from "../../../../commons/vo/verbale/min-soggetto-vo";
import { SoggettoVO } from "../../../../commons/vo/verbale/soggetto-vo";
import { NgForm } from "@angular/forms";
import { DestroySubscribers } from "../../../../core/decorator/destroy-subscribers";



declare var $: any;

@Component({
    selector: 'verbale-soggetto-ricerca-giuridica',
    templateUrl: './verbale-soggetto-ricerca-giuridica.component.html'
})
@DestroySubscribers()
export class VerbaleSoggettoRicercaGiuridicaComponent implements OnInit {

    @Output()
    ricerca: EventEmitter<MinSoggettoVO> = new EventEmitter<MinSoggettoVO>();
    @Output()
    pulisci: EventEmitter<MinSoggettoVO> = new EventEmitter<MinSoggettoVO>();

    @Input()
    soggetto: SoggettoVO;
    @Input()
    modalita: string; //R o E


    @Output()
    formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
    @ViewChild('soggettiGiuridico')
    private soggettiGiuridico: NgForm;

    public subscribers: any = {};

    //utilizzato per validazione
    copySoggetto: SoggettoVO;

    constructor() { }

    ngOnInit(): void {
        if (!this.modalita) this.modalita = "R";

        //VALIDAZIONE FORM
        if (this.modalita == 'E') {
            this.subscribers.sog = this.soggettiGiuridico.valueChanges.subscribe(data => {
                if (this.soggettiGiuridico.status == "DISABLED") this.formValid.next(true);
                else
                    this.formValid.next(this.soggettiGiuridico.valid);
            });
        }
    }

    emitRicerca() {
        this.ricerca.emit(this.soggetto);
    }

    emitPulisci() {
        this.pulisci.emit(this.soggetto);
    }

    isDisable(field: string) {
        let s = this.soggetto;
        if (this.modalita == "R") {
            if (field == "RS")
                return !(this.isEmpty(s.codiceFiscale) && this.isEmpty(s.partitaIva));
            if (field == "CF") {
                return !(this.isEmpty(s.partitaIva) && this.isEmpty(s.ragioneSociale));
            }
            if (field == "PI") {
                return !(this.isEmpty(s.codiceFiscale) && this.isEmpty(s.ragioneSociale));
            }
        }
        if (this.modalita == "E") {
            if (s.idStas) return true;
            if (s.id) return true;
            else {
                let copy = this.copySoggetto;
                if (!this.isEmpty(copy.codiceFiscale)) {
                    if (field == "RS" || field == "PI") {
                        return false;
                    }
                    else return true;
                }
                if (!this.isEmpty(copy.partitaIva)) {
                    if (field == "RS" || field == "CF") {
                        return false;
                    }
                    else return true;
                }
                if (!this.isEmpty(copy.ragioneSociale)) {
                    if (field == "PI" || field == "CF") {
                        return false;
                    }
                    else return true;
                }
            }
        }
    }

    ngOnChanges() {
        this.copySoggetto = JSON.parse(JSON.stringify(this.soggetto));
    }

    isEmpty(str) {
        return (!str || 0 === str.length);
    }


}