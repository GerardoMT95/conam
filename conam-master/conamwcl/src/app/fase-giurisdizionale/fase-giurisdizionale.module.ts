import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { FaseGiurisdizionaleRoutingModule } from "./fase-giurisdizionale.routing.module";
import { CalendarModule } from "./module/calendar/calendar.module";
import { FaseGiurisdizionaleCalendarioUdienzeComponent } from "./components/fase-giurisdizionale-calendario-udienze/fase-giurisdizionale-calendario-udienze.component";
import { CalendarService } from "./services/calendar.service";
import { FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-ricerca-ordinanza/fase-giurisdizionale-ricorso-ricerca-ordinanza.component";
import { FaseGiurisdizionaleSentenzaRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-ricerca-ordinanza/fase-giurisdizionale-sentenza-ricerca-ordinanza.component";
import { FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-allegato-ordinanza/fase-giurisdizionale-sentenza-allegato-ordinanza.component";
import { FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-allegato-ordinanza/fase-giurisdizionale-ricorso-allegato-ordinanza.component";
import { FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-ricorso-dettaglio-ordinanza/fase-giurisdizionale-ricorso-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-sentenza-dettaglio-ordinanza/fase-giurisdizionale-sentenza-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleUtilService } from "./services/fase-giurisdizionale-util.serivice";
import { SharedOrdinanzaModule } from "../shared-ordinanza/shared-ordinanza.module";
import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";
import { FaseGiurisdizionaleCancelleriaRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-ricerca-ordinanza/fase-giurisdizionale-cancelleria-ricerca-ordinanza.component";
import { FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-dettaglio-ordinanza/fase-giurisdizionale-cancelleria-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent } from "./components/fase-giurisdizionale-cancelleria-allegato-ordinanza/fase-giurisdizionale-cancelleria-allegato-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneRicercaOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-ricerca-ordinanza/fase-giurisdizionale-rateizzazione-ricerca-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-dettaglio-ordinanza/fase-giurisdizionale-rateizzazione-dettaglio-ordinanza.component";
import { FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent } from "./components/fase-giurisdizionale-rateizzazione-allegato-ordinanza/fase-giurisdizionale-rateizzazione-allegato-ordinanza.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaRicercaComponent } from "./components/fase-giurisdizionale-comparsa-verbale-ricerca/fase-giurisdizionale-comparsa-verbale-ricerca.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent } from "./components/fase-giurisdizionale-comparsa-verbale-riepilogo/fase-giurisdizionale-comparsa-verbale-riepilogo.component";
import { FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent } from "./components/fase-giurisdizionale-comparsa-verbale-allegato/fase-giurisdizionale-comparsa-verbale-allegato.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        FaseGiurisdizionaleRoutingModule,
        SharedOrdinanzaModule,
        CalendarModule,
        SharedModule,
        SharedVerbaleModule
    ],
    exports: [
        FaseGiurisdizionaleCancelleriaRicercaOrdinanzaComponent,
        FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneRicercaOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent,
        FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent,
        FaseGiurisdizionaleCalendarioUdienzeComponent,
        FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent,
        FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent,
        FaseGiurisdizionaleSentenzaRicercaOrdinanzaComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaRicercaComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent
    ],
    declarations: [
      
        FaseGiurisdizionaleCancelleriaRicercaOrdinanzaComponent,
        FaseGiurisdizionaleCancelleriaDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleAllegatoCancelleriaOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneRicercaOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleRateizzazioneAllegatoOrdinanzaComponent,
        FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent,
        FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent,
        FaseGiurisdizionaleRicorsoDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleSentenzaDettaglioOrdinanzaComponent,
        FaseGiurisdizionaleCalendarioUdienzeComponent,
        FaseGiurisdizionaleSentenzaAllegatoOrdinanzaComponent,
        FaseGiurisdizionaleSentenzaRicercaOrdinanzaComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaRicercaComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent,
        FaseGiurisdizionaleComparsaCostituzioneRispostaAllegatoComponent,
    ],
    providers: [CalendarService, FaseGiurisdizionaleUtilService]
})
export class FaseGiurisdizionaleModule { }