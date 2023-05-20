import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SharedRiscossioneService } from "./services/shared-riscossione.service";
import { SharedRiscossioneRicercaComponent } from "./components/shared-riscossione-ricerca/shared-riscossione-ricerca.component";
import { SharedRiscossioneSollecitoDettaglioComponent } from "./components/shared-riscossione-sollecito-dettaglio/shared-riscossione-sollecito-dettaglio.component";
import { SharedRiscossioneConfigService } from "./services/shared-riscossione-config.service";
import { SharedNotificaModule } from "../shared-notifica/shared-notifica.module";
import { SharedRiscossioneRicercaRateComponent } from "./components/shared-riscossione-ricerca-rate/shared-riscossione-ricerca-rate.component";
import { SharedRiscossioneSollecitoDettaglioRateComponent } from "./components/shared-riscossione-sollecito-dettaglio-rate/shared-riscossione-sollecito-dettaglio-rate.component";



@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
        SharedNotificaModule        
    ],
    exports: [
        SharedRiscossioneRicercaComponent,
        SharedRiscossioneRicercaRateComponent,
        SharedRiscossioneSollecitoDettaglioComponent,
        SharedRiscossioneSollecitoDettaglioRateComponent
    ],
    declarations: [
        SharedRiscossioneRicercaComponent,
        SharedRiscossioneRicercaRateComponent,
        SharedRiscossioneSollecitoDettaglioComponent,
        SharedRiscossioneSollecitoDettaglioRateComponent        
    ],
    providers: [SharedRiscossioneService, SharedRiscossioneConfigService]
})
export class SharedRiscossioneModule { }