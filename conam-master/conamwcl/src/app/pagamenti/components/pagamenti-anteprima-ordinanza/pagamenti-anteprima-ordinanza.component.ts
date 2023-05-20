import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";

@Component({
    selector: 'pagamenti-anteprima-ordinanza',
    templateUrl: './pagamenti-anteprima-ordinanza.component.html'
})
export class PagamentiAnteprimaOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};


    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiAnteprimaOrdinanzaComponent.name);


        /*this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            this.loaded = false;
            this.show = false;

            if (this.activatedRoute.snapshot.paramMap.get('action') == 'prova')
                this.manageMessage();

            let seconds: number = 1;
            this.intervalId2 = window.setInterval(() => {
                seconds -= 1;
                if (seconds === 0) {
                    this.loaded = true;
                }
            }, 1000);
        });*/
    }



    ngOnDestroy(): void {
        this.logger.destroy(PagamentiAnteprimaOrdinanzaComponent.name);
    }

}