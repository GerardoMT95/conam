import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { PagamentiRiconciliaVerbaleRicercaComponent } from "./components/pagamenti-riconcilia-verbale-ricerca/pagamenti-riconcilia-verbale-ricerca.component";
import { PagamentiRiconciliaVerbaleRiepilogoComponent } from "./components/pagamenti-riconcilia-verbale-riepilogo/pagamenti-riconcilia-verbale-riepilogo.component";
import { PagamentiAnteprimaOrdinanzaComponent } from "./components/pagamenti-anteprima-ordinanza/pagamenti-anteprima-ordinanza.component";
import { PagamentiRiconciliaVerbaleAllegatoComponent } from "./components/pagamenti-riconcilia-verbale-allegato/pagamenti-riconcilia-verbale-allegato.component";
import { PagamentiPianoCreaComponent } from "./components/pagamenti-piano-crea/pagamenti-piano-crea.component";
import { PagamentiPianoRicercaComponent } from "./components/pagamenti-piano-ricerca/pagamenti-piano-ricerca.component";
import { PagamentiPianoInsModComponent } from "./components/pagamenti-piano-ins-mod/pagamenti-piano-ins-mod.component";
import { PagamentiPianoViewComponent } from "./components/pagamenti-piano-view/pagamenti-piano-view.component";
import { PagamentiTemplateComponent } from "./components/pagamenti-template/pagamenti-template.component";
import { PagamentiRiconciliaRicercaOrdinanzaComponent } from "./components/pagamenti-riconcilia-ordinanza-ricerca/pagamenti-riconcilia-ordinanza-ricerca.component";
import { PagamentiRiconciliaOrdinanzaRiepilogoComponent } from "./components/pagamenti-riconcilia-ordinanza-riepilogo/pagamenti-riconcilia-ordinanza-riepilogo.component";
import { PagamentiRiconciliaOrdinanzaAllegatoComponent } from "./components/pagamenti-riconcilia-ordinanza-allegato/pagamenti-riconcilia-ordinanza-allegato.component";
import { PagamentiRiconciliaPianoComponent } from "./components/pagamenti-riconcilia-piano-ricerca/pagamenti-riconcilia-piano-ricerca.component";
import { PagamentiRiconciliaPianoDettaglioComponent } from "./components/pagamenti-riconcilia-piano-dettaglio/pagamenti-riconcilia-piano-dettaglio.component";
import { PagamentiPianoInsNotificaComponent } from "./components/pagamenti-piano-ins-notifica/pagamenti-piano-ins-notifica.component";
import { PagamentiPianoViewNotificaComponent } from "./components/pagamenti-piano-view-notifica/pagamenti-piano-view-notifica.component";
import { PagamentiRiconciliaSollecitoRicercaComponent } from "./components/pagamenti-riconcilia-sollecito-ricerca/pagamenti-riconcilia-sollecito-ricerca.component";
import { PagamentiRiconciliaSollecitoDettaglioComponent } from "./components/pagamenti-riconcilia-sollecito-dettaglio/pagamenti-riconcilia-sollecito-dettaglio.component";
import { PagamentiRiconciliaSollecitoRateRicercaComponent } from "./components/pagamenti-riconcilia-sollecito-rate-ricerca/pagamenti-riconcilia-sollecito-rate-ricerca.component";
import { PagamentiRiconciliaSollecitoRateDettaglioComponent } from "./components/pagamenti-riconcilia-sollecito-rate-dettaglio/pagamenti-riconcilia-sollecito-rate-dettaglio.component";
import { pagamentiRiconciliaManualePagamentoOrdinanzaComponent } from "./components/pagamenti-riconcilia-manuale-pagamento-ordinanza/pagamenti-riconcilia-manuale-pagamento-ordinanza.component";
import { pagamentiRiconciliaManualePagamentoOrdinanzaDettaglioComponent } from "./components/pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio/pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio.component";

const routes: Routes = [
    {
        path: 'pagamenti', children: [
            //{ path: '', redirectTo: 'dati', pathMatch: 'full' },
            { path: 'piano-crea', component: PagamentiPianoCreaComponent, data: { breadcrumb: "Crea Piano" } },
            { path: 'piano-inserimento', component: PagamentiPianoInsModComponent, data: { breadcrumb: "Inserisci Piano" } },
            { path: 'piano-modifica/:idPiano', component: PagamentiPianoInsModComponent, data: { breadcrumb: "Modifica Piano" } },
            { path: 'piano-ricerca', component: PagamentiPianoRicercaComponent, data: { breadcrumb: "Ricerca Piano" } },
            { path: 'piano-view/:idPiano', component: PagamentiPianoViewComponent, data: { breadcrumb: "Dettaglio Piano" } },
            { path: 'piano-ins-notifica/:idPiano', component: PagamentiPianoInsNotificaComponent },
            { path: 'piano-view-notifica/:idPiano', component: PagamentiPianoViewNotificaComponent },

            { path: 'riconcilia-verbale-ricerca', component: PagamentiRiconciliaVerbaleRicercaComponent, data: { breadcrumb: "Riconcilia verbale ricerca" } },
            { path: 'riconcilia-verbale-riepilogo/:id', component: PagamentiRiconciliaVerbaleRiepilogoComponent, data: { breadcrumb: "Riconcilia verbale riepilogo" } },
            { path: 'riconcilia-verbale-allegato/:idVerbale', component: PagamentiRiconciliaVerbaleAllegatoComponent, data: { breadcrumb: "Allegato Pagamento" } },
            { path: 'anteprima-ordinanza', component: PagamentiAnteprimaOrdinanzaComponent, data: { breadcrumb: "Anteprima ordinanza" } },
            { path: 'template/:id', component: PagamentiTemplateComponent },

            { path: 'riconcilia-ordinanza-ricerca', component: PagamentiRiconciliaRicercaOrdinanzaComponent },
            { path: 'riconcilia-ordinanza-riepilogo/:id', component: PagamentiRiconciliaOrdinanzaRiepilogoComponent },
            { path: 'riconcilia-ordinanza-allegato/id', component: PagamentiRiconciliaOrdinanzaAllegatoComponent },

            { path: 'riconcilia-piano-ricerca', component: PagamentiRiconciliaPianoComponent },
            { path: 'riconcilia-piano-dettaglio/:idPiano', component: PagamentiRiconciliaPianoDettaglioComponent },

            { path: 'riconcilia-sollecito-ricerca', component: PagamentiRiconciliaSollecitoRicercaComponent },
            { path: 'riconcilia-sollecito-dettaglio', component: PagamentiRiconciliaSollecitoDettaglioComponent },
            { path: 'riconcilia-sollecito-rate-ricerca', component: PagamentiRiconciliaSollecitoRateRicercaComponent },
            { path: 'riconcilia-sollecito-rate-dettaglio', component: PagamentiRiconciliaSollecitoRateDettaglioComponent },
            { path: 'pagamenti-riconcilia-manuale-pagamento-ordinanza', component: pagamentiRiconciliaManualePagamentoOrdinanzaComponent },
            { path: 'pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio/:id', component: pagamentiRiconciliaManualePagamentoOrdinanzaDettaglioComponent }

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
export class PagamentiRoutingModule { }