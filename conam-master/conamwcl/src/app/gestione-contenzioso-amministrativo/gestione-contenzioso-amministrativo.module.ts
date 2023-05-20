import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { GestioneContenziosoAmministrativoRoutingModule } from "./gestione-contenzioso-amministrativo.routing.module";
import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";
import { OrdinanzaRiepilogoGestContAmministrativoComponent } from "./components/ordinanza-riepilogo/ordinanza-riepilogo.component";
import { SharedOrdinanzaModule } from "../shared-ordinanza/shared-ordinanza.module";
import { OrdinanzaInsRicercaVerbaleGestContAmministrativoComponent } from "./components/ordinanza-ins-ricerca-verbale/ordinanza-ins-ricerca-verbale.component";
import { OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent } from "./components/ordinanza-ins-riepilogo-verbale/ordinanza-ins-riepilogo-verbale.component";
import { OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent } from "./components/ordinanza-ins-crea-ordinanza/ordinanza-ins-crea-ordinanza.component";
import { OrdinanzaRicercaGestContAmministrativoComponent } from "./components/ordinanza-ricerca/ordinanza-ricerca.component";
import { OrdinanzaTemplateLetteraGestContAmministrativoComponent } from "./components/ordinanza-template-lettera/ordinanza-template-lettera.component";
import { TemplateModule } from "../template/template.module";
import { SharedTemplateModule } from "../shared-template/template.module";
import { OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent } from "./components/ordinanza-ins-aggiungi-notifica/ordinanza-ins-aggiungi-notifica.component";
import { OrdinanzaViewNotificaGestContAmministrativoComponent } from "./components/ordinanza-view-notifica/ordinanza-view-notifica.component";
import { EmissioneVerbaleAudizioneRicercaComponent } from "./components/emissione-verbale-audizione-ricerca/emissione-verbale-audizione-ricerca.component";
import { EmissioneVerbaleAudizioneRiepilogoComponent } from "./components/emissione-verbale-audizione-riepilogo/emissione-verbale-audizione-riepilogo.component";
import { EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent } from "./components/emissione-verbale-audizione-crea/emissione-verbale-audizione-crea.component";
import { EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent } from "./components/emissione-verbale-audizione-template-lettera/emissione-verbale-audizione-template-lettera.component";
import { FaseGiurisdizionaleVerbaleAudizioneService } from "./service/fase-giurisdizionale-verbale-audizione.service";
import { FaseGiurisdizionaleOrdinanzaService } from "./service/fase-giurisdizionale-ordinanza.service";
import { ControdeduzioniVerbaleRicercaGestContAmministrativoComponent } from "./components/controdeduzioni-verbale-ricerca/controdeduzioni-verbale-ricerca.component";
import { ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent } from "./components/controdeduzioni-verbale-riepilogo/controdeduzioni-verbale-riepilogo.component";
import { ControdeduzioniVerbaleAllegatoComponent } from "./components/controdeduzioni-verbale-allegato/controdeduzioni-verbale-allegato.component";
import { SharedNotificaModule } from "../shared-notifica/shared-notifica.module";
import { ConvocazioneAudizioneCreaGestContAmministrativoComponent } from "./components/convocazione-audizione-crea/convocazione-audizione-crea.component";
import { ConvocazioneAudizioneRicercaComponent } from "./components/convocazione-audizione-ricerca/convocazione-verbale-audizione-ricerca.component";
import { ConvocazioneAudizioneTemplateLetteraGestContAmministrativoComponent } from "./components/convocazione-audizione-template-lettera/convocazione-audizione-template-lettera.component";
import { ConvocazioneAudizioneRiepilogoComponent } from "./components/convocazione-audizione-riepilogo/convocazione-audizione-riepilogo.component";
import { RecidivitaRicercaComponent } from "./components/recidivita-ricerca/recidivita-ricerca.component";
import { RecidivitaDettaglioComponent } from "./components/recidivita-dettaglio/recidivita-dettaglio.component";
import { ConvocazioneAudizioneCreaSoggettoGestContAmministrativoComponent } from "./components/convocazione-audizione-crea-soggetto/convocazione-audizione-crea-soggetto.component";




@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        GestioneContenziosoAmministrativoRoutingModule,
        SharedModule,
        SharedVerbaleModule,
        SharedOrdinanzaModule,
        TemplateModule,
        SharedTemplateModule,
        SharedNotificaModule
    ],
    exports: [
        OrdinanzaInsRicercaVerbaleGestContAmministrativoComponent,
        OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent,
        OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent,
        OrdinanzaRiepilogoGestContAmministrativoComponent,
        OrdinanzaRicercaGestContAmministrativoComponent,
        OrdinanzaTemplateLetteraGestContAmministrativoComponent,
        OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent,
        OrdinanzaViewNotificaGestContAmministrativoComponent,
        EmissioneVerbaleAudizioneRicercaComponent,
        EmissioneVerbaleAudizioneRiepilogoComponent,
        EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent,
        EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent,
        ControdeduzioniVerbaleRicercaGestContAmministrativoComponent,
        ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent,
        ControdeduzioniVerbaleAllegatoComponent,                
        ConvocazioneAudizioneCreaGestContAmministrativoComponent,
        ConvocazioneAudizioneCreaSoggettoGestContAmministrativoComponent,
        ConvocazioneAudizioneRicercaComponent,
        ConvocazioneAudizioneRiepilogoComponent,
        ConvocazioneAudizioneTemplateLetteraGestContAmministrativoComponent,
        RecidivitaRicercaComponent,
        RecidivitaDettaglioComponent,
      

    ],
    declarations: [
        OrdinanzaInsRicercaVerbaleGestContAmministrativoComponent,
        OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent,
        OrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent,
        OrdinanzaRiepilogoGestContAmministrativoComponent,
        OrdinanzaRicercaGestContAmministrativoComponent,
        OrdinanzaTemplateLetteraGestContAmministrativoComponent,
        OrdinanzaInsAggiungiNotificaGestContAmministrativoComponent,
        OrdinanzaViewNotificaGestContAmministrativoComponent,
        EmissioneVerbaleAudizioneRicercaComponent,
        EmissioneVerbaleAudizioneRiepilogoComponent,
        EmissioneVerbaleAudizioneCreaGestContAmministrativoComponent,
        EmissioneVerbaleAudizioneTemplateLetteraGestContAmministrativoComponent,
        ControdeduzioniVerbaleRicercaGestContAmministrativoComponent,
        ControdeduzioniVerbaleRiepilogoGestContAmministrativComponent,
        ControdeduzioniVerbaleAllegatoComponent,                
        ConvocazioneAudizioneCreaGestContAmministrativoComponent,
        ConvocazioneAudizioneCreaSoggettoGestContAmministrativoComponent,
        ConvocazioneAudizioneRicercaComponent,
        ConvocazioneAudizioneRiepilogoComponent,
        ConvocazioneAudizioneTemplateLetteraGestContAmministrativoComponent,
        RecidivitaRicercaComponent,
        RecidivitaDettaglioComponent,
       
    ],
    providers: [FaseGiurisdizionaleOrdinanzaService, FaseGiurisdizionaleVerbaleAudizioneService]
})
export class GestioneContenziosoAmministrativoModule { }