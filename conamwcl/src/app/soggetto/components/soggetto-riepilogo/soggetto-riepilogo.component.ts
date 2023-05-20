import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Location } from '@angular/common';
import { SharedSoggettoRiepilogoComponent } from "../../../shared-soggetto/components/shared-soggetto-riepilogo/shared-soggetto-riepilogo.component";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { SoggettoService } from "../../services/soggetto.service";
@Component({
    selector: 'soggetto-riepilogo',
    templateUrl: './soggetto-riepilogo.component.html',
})
export class SoggettoRiepilogoComponent implements OnInit, OnDestroy {

    @ViewChild(SharedSoggettoRiepilogoComponent)
    sharedSoggettoRiepilogo: SharedSoggettoRiepilogoComponent;

    public subscribers: any = {};
    public loaded: boolean = false;
    public soggetto: SoggettoVO;
    public ruolo: string;
    public nota: string;
    public idVerbale: number;
    constructor(
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private soggettoService: SoggettoService,
        private location: Location,
    ) { }

    ngOnInit(): void {
        let id: number;
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            id = +params["id"];
            this.ruolo= this.activatedRoute.snapshot.paramMap.get('ruolo');
            this.nota = this.activatedRoute.snapshot.paramMap.get('nota');
            this.idVerbale = +this.activatedRoute.snapshot.paramMap.get('idVerbale');
            this.getSoggettoById(id,this.idVerbale);
        })
        
    }
    getSoggettoById(id: number, idVerbale:number ) {
        this.subscribers.soggetto = this.soggettoService.getSoggettoById(id,idVerbale).subscribe((data) => {
            this.soggetto = data;
            this.loaded = true;
        });
    }

    goBack(): void {
        this.location.back();
    }

    ngOnDestroy(): void {
        this.logger.destroy(SoggettoRiepilogoComponent.name);
    }

}