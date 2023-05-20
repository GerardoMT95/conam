import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { FaseGiurisdizionaleCalendarioUdienzeComponent } from "./components/fase-giurisdizionale-calendario-udienze/fase-giurisdizionale-calendario-udienze.component";
import { FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-dettaglio-ordinanza/fase-giurisdizionale-ricorso-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-ricerca-ordinanza/fase-giurisdizionale-ricorso-ricerca-ordinanza.component";
import { FaseGiurisdizionaleSentenzaRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-ricerca-ordinanza/fase-giurisdizionale-sentenza-ricerca-ordinanza.component";
import { FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-dettaglio-ordinanza/fase-giurisdizionale-sentenza-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-allegato-ordinanza/fase-giurisdizionale-sentenza-allegato-ordinanza.component";
import { FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-allegato-ordinanza/fase-giurisdizionale-ricorso-allegato-ordinanza.component";
import { FaseGiurisdizionaleCancelleriaRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-ricerca-ordinanza/fase-giurisdizionale-cancelleria-ricerca-ordinanza.component";
import { FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-dettaglio-ordinanza/fase-giurisdizionale-cancelleria-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-allegato-ordinanza/fase-giurisdizionale-cancelleria-allegato-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-ricerca-ordinanza/fase-giurisdizionale-rateizzazione-ricerca-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-dettaglio-ordinanza/fase-giurisdizionale-rateizzazione-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-allegato-ordinanza/fase-giurisdizionale-rateizzazione-allegato-ordinanza.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaRicercaComponent } from "./components/fase-giurisdizionale-comparsa-verbale-ricerca/fase-giurisdizionale-comparsa-verbale-ricerca.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent } from "./components/fase-giurisdizionale-comparsa-verbale-riepilogo/fase-giurisdizionale-comparsa-verbale-riepilogo.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent } from "./components/fase-giurisdizionale-comparsa-verbale-allegato/fase-giurisdizionale-comparsa-verbale-allegato.component";

const routes: Routes = [
    {
        path: 'fase-giurisdizionale', children: [
            { path: 'cancelleria-ricerca-ordinanza', component: FaseGiurisdizionaleCancelleriaRicercaOrdinanzaComponent },
            { path: 'cancelleria-dettaglio-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent },
            { path: 'cancelleria-allegato-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent },
            { path: 'rateizzazione-ricerca-ordinanza', component: FaseGiurisdizionaleRateizzazioneRicercaOrdinanzaComponent },
            { path: 'rateizzazione-dettaglio-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent },
            { path: 'rateizzazione-allegato-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent },
            { path: 'ricorso-ricerca-ordinanza', component: FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent },
            { path: 'ricorso-dettaglio-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent },
            { path: 'ricorso-allegato-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent },
            { path: 'sentenza-ricerca-ordinanza', component: FaseGiurisdizionaleSentenzaRicercaOrdinanzaComponent },
            { path: 'sentenza-dettaglio-ordinanza/:idOrdinanza', component: FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent },
            { path: 'sentenza-allegato-ordinanza', component: FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent },
            { path: 'calendario-udienze', component: FaseGiurisdizionaleCalendarioUdienzeComponent },
            { path: 'comparsa-verbale-ricerca', component: FaseGiurisdizionaleComparsaCostituzioneRispostaRicercaComponent },
            { path: 'comparsa-verbale-riepilogo/:id', component: FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent },
            { path: 'comparsa-verbale-allegato/:id', component: FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent }
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
export class FaseGiurisdizionaleRoutingModule { }