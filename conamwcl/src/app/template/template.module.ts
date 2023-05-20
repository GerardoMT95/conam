import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { TemplateRoutingModule } from "./template.routing.module";
import { Template04RateizzazioneComponent } from "./components/template-04-rateizzazione/template-04-rateizzazione.component";
import { TemplateService } from "./services/template.service";
import { SharedModule } from "../shared/shared.module";
import { Template01ConvocazioneAudizioneComponent } from "./components/template-01-convocazione-audizione/template-01-convocazione-audizione.component";
import { Template02VerbaleAudizioneComponent } from "./components/template-02-verbale-audizione/template-02-verbale-audizione.component";
import { Template03LetteraAccompagnamentoComponent } from "./components/template-03-lettera-accompagnamento/template-03-lettera-accompagnamento.component";
import { Template05SollecitoPagamentoComponent } from "./components/template-05-sollecito-pagamento/template-05-sollecito-pagamento.component";
import { TemplateContainerComponent } from "./components/template-container/template-container.component";
import { SharedTemplateModule } from "../shared-template/template.module";
import { Template06ConvocazioneAudizioneComponent } from "./components/template-06-convocazione-audizione/template-06-convocazione-audizione.component";
import { Template09LetteraAccompagnamentoAnnullamentoComponent } from "./components/template-09-lettera-accompagnamento-annullamento/template-09-lettera-accompagnamento-annullamento.component";
import { Template10LetteraAccompagnamentoArchiviazioneComponent } from "./components/template-10-lettera-accompagnamento-archiviazione/template-10-lettera-accompagnamento-archiviazione.component";
import { Template11SollecitoPagamentoRateComponent } from "./components/template-11-lettera-sollecito-pagamento-rate/template-11-lettera-sollecito-pagamento-rate.component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
        TemplateRoutingModule,
        SharedTemplateModule
    ],
    exports: [
        TemplateContainerComponent,
        Template01ConvocazioneAudizioneComponent,
        Template02VerbaleAudizioneComponent,
        Template03LetteraAccompagnamentoComponent,
        Template09LetteraAccompagnamentoAnnullamentoComponent,
        Template10LetteraAccompagnamentoArchiviazioneComponent,
        Template04RateizzazioneComponent,
        Template05SollecitoPagamentoComponent,
        Template06ConvocazioneAudizioneComponent,
        Template11SollecitoPagamentoRateComponent
    ],
    declarations: [
        TemplateContainerComponent,
        Template01ConvocazioneAudizioneComponent,
        Template02VerbaleAudizioneComponent,
        Template03LetteraAccompagnamentoComponent,
        Template10LetteraAccompagnamentoArchiviazioneComponent,
        Template09LetteraAccompagnamentoAnnullamentoComponent,
        Template04RateizzazioneComponent,
        Template05SollecitoPagamentoComponent,
        Template06ConvocazioneAudizioneComponent,
        Template11SollecitoPagamentoRateComponent
    ],
    providers: [TemplateService]
})
export class TemplateModule { }