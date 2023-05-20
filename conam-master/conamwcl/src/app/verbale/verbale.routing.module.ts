import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { VerbaleDatiComponent } from "./components/verbale-dati/verbale-dati.component";
import { VerbaleSoggettoComponent } from "./components/verbale-soggetto/verbale-soggetto.component";
import { VerbaleAllegatoComponent } from "./components/verbale-allegato/verbale-allegato.component";
import { VerbaleRiepilogoComponent } from "./components/verbale-riepilogo/verbale-riepilogo.component";
import { VerbaleRicercaComponent } from "./components/verbale-ricerca/verbale-ricerca.component";
import { VerbaleEliminazioneComponent } from "./components/verbale-eliminazione/verbale-eliminazione.component";
import { VerbaleRicercaScrittiDifensiviComponent } from "./components/verbale-ricerca-scritti-difensivi/verbale-ricerca-scritti-difensivi.component";
import { VerbaleScrittoDifensivoComponent } from "./components/verbale-scritto-difensivo/verbale-scritto-difensivo.component";


const routes: Routes = [
    { path: 'verbale', children:[
        { path: '', redirectTo: 'dati', pathMatch: 'full' },
        { path: 'dati', component: VerbaleDatiComponent, data: { breadcrumb: "Dati"} },
        { path: 'dati/:id', component: VerbaleDatiComponent, data: { breadcrumb: "Dati"} },
        { path: 'soggetto/:id', component: VerbaleSoggettoComponent, data: { breadcrumb: "Soggetto"} },
        { path: 'allegato/:id', component: VerbaleAllegatoComponent, data: { breadcrumb: "Allegato"} },
        { path: 'riepilogo/:id', component: VerbaleRiepilogoComponent, data: { breadcrumb: "Riepilogo"} },
        { path: 'ricerca', component: VerbaleRicercaComponent, data: { breadcrumb: "Ricerca"} },
        { path: 'eliminazione', component: VerbaleEliminazioneComponent, data: { breadcrumb: "Eliminazione"} },
        { path: 'scritto-difensivo/:id', component: VerbaleScrittoDifensivoComponent, data: { breadcrumb: "scritto-difensivo"} },
        { path: 'inserimento-scritti-difensivi', component: VerbaleScrittoDifensivoComponent, data: { breadcrumb: "inserimento-scritti-difensivi"} },
        { path: 'ricerca-scritti-difensivi', component: VerbaleRicercaScrittiDifensiviComponent, data: { breadcrumb: "ricerca-scritti-difensivi"} },]
    }]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class VerbaleRoutingModule { }