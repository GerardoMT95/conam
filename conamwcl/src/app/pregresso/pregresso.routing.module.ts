import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { PregressoDatiComponent } from "./components/pregresso-dati/pregresso-dati.component";
import { PregressoSoggettoComponent } from "./components/pregresso-soggetto/pregresso-soggetto.component";
import { PregressoAllegatoComponent } from "./components/pregresso-allegato/pregresso-allegato.component";
import { PregressoRiepilogoComponent } from "./components/pregresso-riepilogo/pregresso-riepilogo.component";
import { PregressoRicercaComponent } from "./components/pregresso-ricerca/pregresso-ricerca.component";
import { PregressoEliminazioneComponent } from "./components/pregresso-eliminazione/pregresso-eliminazione.component";
import { PregressoInserimentoComponent } from "./components/pregresso-inserimento/pregresso-inserimento.component";
import { PregressoInserimentoManualeComponent } from "./components/pregresso-inserimento-manuale/pregresso-inserimento-manuale.component";
import { PregressoInserimentoActaComponent } from "./components/pregresso-inserimento-acta/pregresso-inserimento-acta.component";
import { PregressoOrdinanzeRiepilogoComponent } from "./components/pregresso-ordinanze-riepilogo/pregresso-ordinanze-riepilogo.component";
import { PregressoPianiRiepilogoComponent } from "./components/pregresso-piani-riepilogo/pregresso-piani-riepilogo.component";
import { PregressoSollecitiRiepilogoComponent } from "./components/pregresso-solleciti-riepilogo/pregresso-solleciti-riepilogo.component";
import { PregressoDisposizioniRiepilogoComponent } from "./components/pregresso-disposizioni-riepilogo/pregresso-disposizioni-riepilogo.component";
import { PregressoRicevuteRiepilogoComponent } from "./components/pregresso-ricevute-riepilogo/pregresso-ricevute-riepilogo.component";


const routes: Routes = [
    { path: 'pregresso', children:[
        { path: '', redirectTo: 'dati', pathMatch: 'full' },
        { path: 'dati', component: PregressoDatiComponent, data: { breadcrumb: "Dati"} },
        { path: 'dati/:id', component: PregressoDatiComponent, data: { breadcrumb: "Dati"} },
        { path: 'soggetto/:id', component: PregressoSoggettoComponent, data: { breadcrumb: "Soggetto"} },
        { path: 'allegato/:id', component: PregressoAllegatoComponent, data: { breadcrumb: "Allegato"} },
        { path: 'riepilogo/:id', component: PregressoRiepilogoComponent, data: { breadcrumb: "Riepilogo"} },
        { path: 'riepilogo-ordinanze/:id', component: PregressoOrdinanzeRiepilogoComponent, data: { breadcrumb: "Riepilogo Ordinanze"} },
        { path: 'riepilogo-piani/:id', component: PregressoPianiRiepilogoComponent, data: { breadcrumb: "Riepilogo Piani"} },
        { path: 'riepilogo-solleciti/:id', component: PregressoSollecitiRiepilogoComponent, data: { breadcrumb: "Riepilogo Solleciti"} },
        { path: 'riepilogo-disposizioni/:id', component: PregressoDisposizioniRiepilogoComponent, data: { breadcrumb: "Riepilogo Disposizioni"} },
        { path: 'riepilogo-ricevute/:id', component: PregressoRicevuteRiepilogoComponent, data: { breadcrumb: "Riepilogo Ricevute"} },
        { path: 'ricerca', component: PregressoRicercaComponent, data: { breadcrumb: "Ricerca"} },
        { path: 'eliminazione', component: PregressoEliminazioneComponent, data: { breadcrumb: "Eliminazione"} },
        { path: 'inserimento', component: PregressoInserimentoComponent, data: { breadcrumb: "Inerimento Pregresso"} },
        { path: 'inserimento-manuale', component: PregressoInserimentoManualeComponent, data: { breadcrumb: "Inerimento Pregresso"} },
        { path: 'inserimento-acta', component: PregressoInserimentoActaComponent, data: { breadcrumb: "Inerimento Pregresso"} },
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
export class PregressoRoutingModule { }