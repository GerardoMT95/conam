import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";

@Component({
    selector: 'pregresso-eliminazione',
    templateUrl: './pregresso-eliminazione.component.html',
    styleUrls: ['./pregresso-eliminazione.component.scss']
})
export class PregressoEliminazioneComponent implements OnInit, OnDestroy {

    constructor(
        private logger: LoggerService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.logger.init(PregressoEliminazioneComponent.name);
    }

    goToHome() {
        this.router.navigateByUrl(Routing.HOME);
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoEliminazioneComponent.name);
    }

}