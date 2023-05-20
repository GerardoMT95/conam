import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { RiscossioneSollecitoRicercaComponent } from "./components/riscossione-sollecito-ricerca/riscossione-sollecito-ricerca.component";
import { RiscossioneSollecitoDettaglioComponent } from "./components/riscossione-sollecito-dettaglio/riscossione-sollecito-dettaglio.component";
import { RiscossioneSollecitoTemplateComponent } from "./components/riscossione-sollecito-template/riscossione-sollecito-template.component";
import { RiscossioneSollecitoInsNotificaComponent } from "./components/riscossione-sollecito-ins-notifica/riscossione-sollecito-ins-notifica.component";
import { RiscossioneSollecitoViewNotificaComponent } from "./components/riscossione-sollecito-view-notifica/riscossione-sollecito-view-notifica.component";
import { RiscossioneRiscossioneCoattivaRicercaComponent } from "./components/riscossione-riscossione-coattiva-ricerca/riscossione-riscossione-coattiva-ricerca.component";
import { RiscossioneRiscossioneCoattivaElencoComponent } from "./components/riscossione-riscossione-coattiva-elenco/riscossione-riscossione-coattiva-elenco.component";
import { RiscossioneSollecitoRateRicercaComponent } from "./components/ricossione-sollecito-rate-ricerca/ricossione-sollecito-rate-ricerca.component";
import { RiscossioneSollecitoRateDettaglioComponent } from "./components/riscossione-sollecito-rate-dettaglio/riscossione-sollecito-rate-dettaglio.component";
import { RiscossioneSollecitoRateTemplateComponent } from "./components/riscossione-sollecito-rate-template/riscossione-sollecito-rate-template.component";

const routes: Routes = [
    {
        path: 'riscossione', children: [
            { path: 'sollecito-pagamento-ricerca', component: RiscossioneSollecitoRicercaComponent },
            { path: 'sollecito-pagamento-dettaglio', component: RiscossioneSollecitoDettaglioComponent },
            { path: 'sollecito-pagamento-rate-ricerca', component: RiscossioneSollecitoRateRicercaComponent },
            { path: 'sollecito-pagamento-rate-dettaglio', component: RiscossioneSollecitoRateDettaglioComponent },         
           
            { path: 'sollecito-pagamento-template/:id', component: RiscossioneSollecitoTemplateComponent },
            { path: 'sollecito-pagamento-rate-template/:id', component: RiscossioneSollecitoRateTemplateComponent },
            { path: 'sollecito-pagamento-ins-notifica/:idSollecito', component: RiscossioneSollecitoInsNotificaComponent },
            { path: 'sollecito-pagamento-view-notifica/:idSollecito', component: RiscossioneSollecitoViewNotificaComponent },
            { path: 'riscossione-coattiva-ricerca', component: RiscossioneRiscossioneCoattivaRicercaComponent },
            { path: 'riscossione-coattiva-elenco', component: RiscossioneRiscossioneCoattivaElencoComponent },

        ]
    }]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class RiscossioneRoutingModule { }