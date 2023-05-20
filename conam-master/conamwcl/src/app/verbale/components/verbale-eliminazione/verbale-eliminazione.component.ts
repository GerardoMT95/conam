import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";

@Component({
    selector: 'verbale-eliminazione',
    templateUrl: './verbale-eliminazione.component.html',
    styleUrls: ['./verbale-eliminazione.component.scss']
})
export class VerbaleEliminazioneComponent implements OnInit, OnDestroy {

    constructor(
        private logger: LoggerService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.logger.init(VerbaleEliminazioneComponent.name);
    }

    goToHome() {
        this.router.navigateByUrl(Routing.HOME);
    }

    ngOnDestroy(): void {
        this.logger.destroy(VerbaleEliminazioneComponent.name);
    }

}