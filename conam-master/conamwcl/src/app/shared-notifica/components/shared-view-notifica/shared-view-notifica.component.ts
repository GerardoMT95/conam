import { Component, OnInit, OnDestroy, Input } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { NotificaVO } from "../../../commons/vo/notifica/notifica-vo";
import { ActivatedRoute } from "@angular/router";
import { SharedNotificaService } from "../../services/shared-notifica.service";
import { timer } from "rxjs/observable/timer";
import { DestroySubscribers } from "../../../core/decorator/destroy-subscribers";


declare var $: any;

@Component({
    selector: 'shared-view-notifica',
    templateUrl: './shared-view-notifica.component.html'
})
@DestroySubscribers()
export class SharedViewNotificaComponent implements OnInit, OnDestroy {

    @Input()
    public idOrdinanza: number;
    @Input()
    public idPiano: number;
    @Input()
    public idSollecito: number;

    public subscribers: any = {};
    public loaded: boolean;

    public notifica: NotificaVO = new NotificaVO();

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;

    constructor(
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,
        private sharedNotificaService: SharedNotificaService,
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedViewNotificaComponent.name);
        if (this.idOrdinanza == null && this.idPiano == null && this.idSollecito == null) throw new Error("idOrdinanza-idPiano-idSollecito non valorizzato");
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            if (this.activatedRoute.snapshot.paramMap.get('action') == 'creazione')
                this.manageMessageTop("Notifica creata con successo", 'SUCCESS');
            this.loadNotifica();
        });

    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        const source = timer(1000, 1000).take(31);
        this.subscribers.timer = source.subscribe(val => {
            if (val == 30)
                this.resetMessageTop();
        });
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
    }

    loadNotifica() {
        this.loaded = false;
        this.subscribers.getModalitaNotifica = this.sharedNotificaService.getNotificaBy(this.idOrdinanza, this.idPiano, this.idSollecito).subscribe(data => {
            this.notifica = data;
            this.loaded = true;
        }, err => {
            this.logger.error("Errore durante il recupero delle notifiche");
        });
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedViewNotificaComponent.name);
    }

    realoadNotifca(){
        this.loadNotifica();
    }

} 