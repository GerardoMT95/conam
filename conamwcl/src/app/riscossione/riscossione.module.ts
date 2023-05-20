import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { RiscossioneRoutingModule } from "./riscossione.routing.module";
import { RiscossioneService } from "./services/riscossione.service";
import { RiscossioneSollecitoRicercaComponent } from "./components/riscossione-sollecito-ricerca/riscossione-sollecito-ricerca.component";
import { RiscossioneSollecitoDettaglioComponent } from "./components/riscossione-sollecito-dettaglio/riscossione-sollecito-dettaglio.component";
import { SharedOrdinanzaModule } from "../shared-ordinanza/shared-ordinanza.module";
import { SharedRiscossioneModule } from "../shared-riscossione/shared-riscossione.module";
import { RiscossioneSollecitoTemplateComponent } from "./components/riscossione-sollecito-template/riscossione-sollecito-template.component";
import { TemplateModule } from "../template/template.module";
import { SharedTemplateModule } from "../shared-template/template.module";
import { RiscossioneSollecitoInsNotificaComponent } from "./components/riscossione-sollecito-ins-notifica/riscossione-sollecito-ins-notifica.component";
import { SharedNotificaModule } from "../shared-notifica/shared-notifica.module";
import { RiscossioneSollecitoViewNotificaComponent } from "./components/riscossione-sollecito-view-notifica/riscossione-sollecito-view-notifica.component";
import { SharedRiscossioneService } from "../shared-riscossione/services/shared-riscossione.service";
import { RiscossioneRiscossioneCoattivaRicercaComponent } from "./components/riscossione-riscossione-coattiva-ricerca/riscossione-riscossione-coattiva-ricerca.component";
import { RiscossioneRiscossioneCoattivaElencoComponent } from "./components/riscossione-riscossione-coattiva-elenco/riscossione-riscossione-coattiva-elenco.component";
import { RiscossioneSollecitoRateRicercaComponent } from "./components/ricossione-sollecito-rate-ricerca/ricossione-sollecito-rate-ricerca.component";
import { RiscossioneSollecitoRateDettaglioComponent } from "./components/riscossione-sollecito-rate-dettaglio/riscossione-sollecito-rate-dettaglio.component";
import { SharedPagamentiModule } from "../shared-pagamenti/shared-pagamenti.module";
import { RiscossioneSollecitoRateTemplateComponent } from "./components/riscossione-sollecito-rate-template/riscossione-sollecito-rate-template.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        RiscossioneRoutingModule,
        SharedOrdinanzaModule,
        SharedRiscossioneModule,
        SharedModule,
        TemplateModule,
        SharedTemplateModule,
        SharedNotificaModule,
        SharedPagamentiModule,
        
    ],
    exports: [
        RiscossioneSollecitoRicercaComponent,
        RiscossioneSollecitoRateRicercaComponent,
        RiscossioneSollecitoDettaglioComponent,
        RiscossioneSollecitoTemplateComponent,
        RiscossioneSollecitoRateTemplateComponent,
        RiscossioneSollecitoInsNotificaComponent,
        RiscossioneSollecitoRateDettaglioComponent,
        RiscossioneSollecitoViewNotificaComponent,
        RiscossioneRiscossioneCoattivaRicercaComponent,
        RiscossioneRiscossioneCoattivaElencoComponent,
     

    ],
    declarations: [
        RiscossioneSollecitoRicercaComponent,
        RiscossioneSollecitoRateRicercaComponent,
        RiscossioneSollecitoDettaglioComponent,
        RiscossioneSollecitoTemplateComponent,
        RiscossioneSollecitoRateTemplateComponent,
        RiscossioneSollecitoInsNotificaComponent,
        RiscossioneSollecitoRateDettaglioComponent,
        RiscossioneSollecitoViewNotificaComponent,
        RiscossioneRiscossioneCoattivaRicercaComponent,
        RiscossioneRiscossioneCoattivaElencoComponent,
       
       
    ],
    providers: [RiscossioneService, SharedRiscossioneService,]
})
export class RiscossioneModule { }