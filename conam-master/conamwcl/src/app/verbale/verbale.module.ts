import { NgModule } from "@angular/core";
import { VerbaleRoutingModule } from "./verbale.routing.module";
import { VerbaleAllegatoComponent } from "./components/verbale-allegato/verbale-allegato.component";
import { VerbaleSoggettoComponent } from "./components/verbale-soggetto/verbale-soggetto.component";
import { VerbaleDatiComponent } from "./components/verbale-dati/verbale-dati.component";
import { VerbaleRiepilogoComponent } from "./components/verbale-riepilogo/verbale-riepilogo.component";
import { VerbaleRicercaComponent } from "./components/verbale-ricerca/verbale-ricerca.component";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { RifNormativiService } from "./services/rif-normativi.service";
import { VerbaleService } from "./services/verbale.service";
import { VerbaleEliminazioneComponent } from "./components/verbale-eliminazione/verbale-eliminazione.component";
import { VerbaleSoggettoRicercaFisicaComponent } from "./components/verbale-soggetto/verbale-soggetto-ricerca-fisica/verbale-soggetto-ricerca-fisica.component";
import { VerbaleSoggettoRicercaGiuridicaComponent } from "./components/verbale-soggetto/verbale-soggetto-ricerca-giuridica/verbale-soggetto-ricerca-giuridica.component";
import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";
import { VerbaleRicercaScrittiDifensiviComponent } from "./components/verbale-ricerca-scritti-difensivi/verbale-ricerca-scritti-difensivi.component";
import { VerbaleScrittoDifensivoComponent } from "./components/verbale-scritto-difensivo/verbale-scritto-difensivo.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        VerbaleRoutingModule,
        SharedModule,
        SharedVerbaleModule,
    ],
    exports: [
        VerbaleAllegatoComponent,
        VerbaleSoggettoComponent,
        VerbaleRicercaComponent,
        VerbaleDatiComponent,
        VerbaleRiepilogoComponent,
        VerbaleEliminazioneComponent,
        VerbaleRicercaScrittiDifensiviComponent,
        VerbaleScrittoDifensivoComponent
    ],
    declarations: [
        VerbaleAllegatoComponent,
        VerbaleSoggettoComponent,
        VerbaleRicercaComponent,
        VerbaleDatiComponent,
        VerbaleRiepilogoComponent,
        VerbaleEliminazioneComponent,
        VerbaleSoggettoRicercaGiuridicaComponent,
        VerbaleSoggettoRicercaFisicaComponent,
        VerbaleRicercaScrittiDifensiviComponent,
        VerbaleScrittoDifensivoComponent
    ],
    providers: [RifNormativiService, VerbaleService]
})
export class VerbaleModule { }