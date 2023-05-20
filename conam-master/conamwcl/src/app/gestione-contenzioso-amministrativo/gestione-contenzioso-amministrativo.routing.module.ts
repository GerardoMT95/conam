import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { OrdinanzaInsRicercaVerbaleGestContAmministrativoComponent } from "./components/ordinanza-ins-ricerca-verbale/ordinanza-ins-ricerca-verbale.component";
import { OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent } from "./components/ordinanza-ins-riepilogo-verbale/ordinanza-ins-riepilogo-verbale.component";
import { OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent } from "./components/ordinanza-ins-crea-ordinanza/ordinanza-ins-crea-ordinanza.component";
import { OrdinanzaRiepilogoGestContAmministrativoComponent } from "./components/ordinanza-riepilogo/ordinanza-riepilogo.component";
import { OrdinanzaRicercaGestContAmministrativoComponent } from "./components/ordinanza-ricerca/ordinanza-ricerca.component";
import { OrdinanzaTemplateLetteraGestContAmministrativoComponent } from "./components/ordinanza-template-lettera/ordinanza-template-lettera.component";
import { OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent } from "./components/ordinanza-ins-aggiungi-notifica/ordinanza-ins-aggiungi-notifica.component";
import { OrdinanzaViewNotificaGestContAmministrativoComponent } from "./components/ordinanza-view-notifica/ordinanza-view-notifica.component";
import { EmissioneVerbaleAudizioneRicercaComponent } from "./components/emissione-verbale-audizione-ricerca/emissione-verbale-audizione-ricerca.component";
import { EmissioneVerbaleAudizioneRiepilogoComponent } from "./components/emissione-verbale-audizione-riepilogo/emissione-verbale-audizione-riepilogo.component";
import { EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent } from "./components/emissione-verbale-audizione-crea/emissione-verbale-audizione-crea.component";
import { EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent } from "./components/emissione-verbale-audizione-template-lettera/emissione-verbale-audizione-template-lettera.component";
import { ControdeduzioniVerbaleRicercaGestContAmministrativoComponent } from "./components/controdeduzioni-verbale-ricerca/controdeduzioni-verbale-ricerca.component";
import { ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent } from "./components/controdeduzioni-verbale-riepilogo/controdeduzioni-verbale-riepilogo.component";
import { ControdeduzioniVerbaleAllegatoComponent } from "./components/controdeduzioni-verbale-allegato/controdeduzioni-verbale-allegato.component";
import { ConvocazioneAudizioneRicercaComponent } from "./components/convocazione-audizione-ricerca/convocazione-verbale-audizione-ricerca.component";
import { ConvocazioneAudizioneRiepilogoComponent } from "./components/convocazione-audizione-riepilogo/convocazione-audizione-riepilogo.component";
import { ConvocazioneAudizioneCreaGestContAmministrativoComponent } from "./components/convocazione-audizione-crea/convocazione-audizione-crea.component";
import { ConvocazioneAudizioneTemplateLetteraGestContAmministrativoComponent } from "./components/convocazione-audizione-template-lettera/convocazione-audizione-template-lettera.component";
import { RecidivitaRicercaComponent } from "./components/recidivita-ricerca/recidivita-ricerca.component";
import { RecidivitaDettaglioComponent } from "./components/recidivita-dettaglio/recidivita-dettaglio.component";
import { ConvocazioneAudizioneCreaSoggettoGestContAmministrativoComponent } from "./components/convocazione-audizione-crea-soggetto/convocazione-audizione-crea-soggetto.component";

const routes: Routes = [
    {
        path: 'gest-cont-amministrativo', children: [
            //{ path: '', redirectTo: 'dati', pathMatch: 'full' },
            { path: 'ordinanza-ins-ricerca-verbale', component: OrdinanzaInsRicercaVerbaleGestContAmministrativoComponent },
            { path: 'ordinanza-ins-riepilogo-verbale/:id', component: OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent },
            { path: 'ordinanza-ins-crea-ordinanza/:id', component: OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent },
            { path: 'ordinanza-ins-aggiungi-notifica/:id', component: OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent },
            { path: 'ordinanza-view-notifica/:id', component: OrdinanzaViewNotificaGestContAmministrativoComponent },
            { path: 'ordinanza-riepilogo/:id', component: OrdinanzaRiepilogoGestContAmministrativoComponent, },
            { path: 'ordinanza-ricerca', component: OrdinanzaRicercaGestContAmministrativoComponent },
            { path: 'ordinanza-template-lettera/:id', component: OrdinanzaTemplateLetteraGestContAmministrativoComponent },

            { path: 'emissione-verbale-audizione-ricerca', component: EmissioneVerbaleAudizioneRicercaComponent },
            { path: 'emissione-verbale-audizione-riepilogo/:id', component: EmissioneVerbaleAudizioneRiepilogoComponent },
            { path: 'emissione-verbale-audizione-crea/:id', component: EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent },
            { path: 'emissione-verbale-audizione-allega/:flag/:id', component: EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent },
            { path: 'emissione-verbale-audizione-template-lettera/:id', component: EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent },

            { path: 'controdeduzioni-verbale-ricerca', component: ControdeduzioniVerbaleRicercaGestContAmministrativoComponent },
            { path: 'controdeduzioni-verbale-riepilogo/:id', component: ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent },
            { path: 'controdeduzioni-verbale-allegato/:id', component: ControdeduzioniVerbaleAllegatoComponent },            

            { path: 'convocazione-audizione-ricerca', component: ConvocazioneAudizioneRicercaComponent },
            { path: 'convocazione-audizione-riepilogo/:id', component: ConvocazioneAudizioneRiepilogoComponent },
            { path: 'convocazione-audizione-crea/:id', component: ConvocazioneAudizioneCreaGestContAmministrativoComponent },
            { path: 'convocazione-audizione-crea-soggetto/:id', component: ConvocazioneAudizioneCreaSoggettoGestContAmministrativoComponent },
            { path: 'convocazione-audizione-template-lettera/:id', component: ConvocazioneAudizioneTemplateLetteraGestContAmministrativoComponent },
            { path: 'recidivita-ricerca', component: RecidivitaRicercaComponent },
            { path: 'recidivita-dettaglio/:id', component: RecidivitaDettaglioComponent },

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
export class GestioneContenziosoAmministrativoRoutingModule { }