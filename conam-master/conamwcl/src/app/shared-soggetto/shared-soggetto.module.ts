import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SharedSoggettoRiepilogoComponent } from "./components/shared-soggetto-riepilogo/shared-soggetto-riepilogo.component";
import { SharedSoggettoDati } from "./components/shared-soggetto-dati/shared-soggetto-dati";
import { SharedSoggettoDatiResidenza } from "./components/shared-soggetto-dati-residenza/shared-soggetto-dati-residenza";
import { SharedSoggettoRuolo } from "./components/shared-soggetto-ruolo/shared-soggetto-ruolo";
import { SharedSoggettoDatiAziendali } from "./components/shared-soggetto-dati-aziendali/shared-soggetto-dati-aziendali";
import { SharedSoggettoRecidivita } from "./components/shared-soggetto-recidivita/shared-soggetto-recidivita";
import { SharedSoggettoNotificaRelata } from "./components/shared-soggetto-notifica-relata/shared-soggetto-notifica-relata";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
    ],
    exports: [
        SharedSoggettoRiepilogoComponent,
        SharedSoggettoDati,
        SharedSoggettoDatiResidenza,
        SharedSoggettoRuolo,
        SharedSoggettoDatiAziendali,
        SharedSoggettoRecidivita,
        SharedSoggettoNotificaRelata
    ],
    declarations: [
        SharedSoggettoRiepilogoComponent,
        SharedSoggettoDati,
        SharedSoggettoDatiResidenza,
        SharedSoggettoRuolo,
        SharedSoggettoDatiAziendali,
        SharedSoggettoRecidivita,
        SharedSoggettoNotificaRelata
    ],
    providers: []
})
export class SharedSoggettoModule { }