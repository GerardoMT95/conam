import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";

@Component({
    selector: 'template-container',
    templateUrl: './template-container.component.html'
})
export class TemplateContainerComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;

    public id: number;

    constructor(
        private logger: LoggerService,
        //private router: Router,
        private activatedRoute: ActivatedRoute,
        //private userService: UserService,
    ) { }

    ngOnInit(): void {
        this.logger.init(TemplateContainerComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.id = +params['id'];

            this.loaded = true;
        });
    }

    //

    ngOnDestroy(): void {
        this.logger.destroy(TemplateContainerComponent.name);
    }

}