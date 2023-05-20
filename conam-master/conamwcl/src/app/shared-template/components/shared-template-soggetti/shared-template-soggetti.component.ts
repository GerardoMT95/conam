import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

@Component({
    selector: 'shared-template-soggetti',
    templateUrl: './shared-template-soggetti.component.html'
})
export class SharedTemplateSoggettiComponent implements OnInit, OnDestroy {


    @Input()
    listaSoggetti: Array<SoggettoVO>;
    //Variabili template
    public signor: string;

    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        this.logger.init(SharedTemplateSoggettiComponent.name);
        this.gestisciSingolariPlurali();
    }

    gestisciSingolariPlurali() {
        if (this.listaSoggetti != null) {
            let personeFisiche: Array<SoggettoVO> = this.listaSoggetti.filter(x =>
                x.personaFisica
            );
            let personeGiuridiche: Array<SoggettoVO> = this.listaSoggetti.filter(x =>
                !x.personaFisica
            );
            let size: number = this.listaSoggetti.length;
            let isPersonaFisica: Boolean = personeFisiche.length == 1 && personeFisiche.length == size;
            let isPersonaGiuridica: Boolean = personeGiuridiche.length == 1 && personeGiuridiche.length == size;

            if (isPersonaFisica)
                this.signor = this.listaSoggetti[0].sesso == "M" ? "Al Signor" : "Alla Sig.ra";
            else if (isPersonaGiuridica)
                this.signor = "All'Azienda";
            else if (personeFisiche.length == size)
                this.signor = "Ai Signori"
            else if (personeGiuridiche.length == size)
                this.signor = "Alle Aziende"
            else
                this.signor = "Ai Soggetti"
        }
        else
            this.listaSoggetti = new Array<SoggettoVO>();
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedTemplateSoggettiComponent.name);
    }

}