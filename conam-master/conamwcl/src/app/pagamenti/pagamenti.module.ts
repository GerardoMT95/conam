import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { PagamentiRoutingModule } from "./pagamenti.routing.module";
import { PagamentiRiconciliaVerbaleRicercaComponent } from "./components/pagamenti-riconcilia-verbale-ricerca/pagamenti-riconcilia-verbale-ricerca.component";
import { PagamentiRiconciliaVerbaleRiepilogoComponent } from "./components/pagamenti-riconcilia-verbale-riepilogo/pagamenti-riconcilia-verbale-riepilogo.component";
import { PagamentiAnteprimaOrdinanzaComponent } from "./components/pagamenti-anteprima-ordinanza/pagamenti-anteprima-ordinanza.component";
import { PagamentiRiconciliaVerbaleAllegatoComponent } from "./components/pagamenti-riconcilia-verbale-allegato/pagamenti-riconcilia-verbale-allegato.component";
import { PagamentiPianoCreaComponent } from "./components/pagamenti-piano-crea/pagamenti-piano-crea.component";
import { PagamentiPianoInsModComponent } from "./components/pagamenti-piano-ins-mod/pagamenti-piano-ins-mod.component";
import { PagamentiPianoRicercaComponent } from "./components/pagamenti-piano-ricerca/pagamenti-piano-ricerca.component";
import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";
import { SharedPagamentiModule } from "../shared-pagamenti/shared-pagamenti.module";
import { SharedOrdinanzaModule } from "../shared-ordinanza/shared-ordinanza.module";
import { PagamentiService } from "./services/pagamenti.service";
import { PagamentiPianoViewComponent } from "./components/pagamenti-piano-view/pagamenti-piano-view.component";
import { PagamentiTemplateComponent } from "./components/pagamenti-template/pagamenti-template.component";
import { TemplateModule } from "../template/template.module";
import { SharedTemplateModule } from "../shared-template/template.module";
import { PagamentiRiconciliaRicercaOrdinanzaComponent } from "./components/pagamenti-riconcilia-ordinanza-ricerca/pagamenti-riconcilia-ordinanza-ricerca.component";
import { PagamentiRiconciliaOrdinanzaRiepilogoComponent } from "./components/pagamenti-riconcilia-ordinanza-riepilogo/pagamenti-riconcilia-ordinanza-riepilogo.component";
import { PagamentiRiconciliaOrdinanzaAllegatoComponent } from "./components/pagamenti-riconcilia-ordinanza-allegato/pagamenti-riconcilia-ordinanza-allegato.component";
import { PagamentiRiconciliaPianoComponent } from "./components/pagamenti-riconcilia-piano-ricerca/pagamenti-riconcilia-piano-ricerca.component";
import { PagamentiRiconciliaPianoDettaglioComponent } from "./components/pagamenti-riconcilia-piano-dettaglio/pagamenti-riconcilia-piano-dettaglio.component";
import { PagamentiUtilService } from "./services/pagamenti-util.serivice";
import { PagamentiPianoInsNotificaComponent } from "./components/pagamenti-piano-ins-notifica/pagamenti-piano-ins-notifica.component";
import { SharedNotificaModule } from "../shared-notifica/shared-notifica.module";
import { PagamentiPianoViewNotificaComponent } from "./components/pagamenti-piano-view-notifica/pagamenti-piano-view-notifica.component";
import { PagamentiRiconciliaSollecitoRicercaComponent } from "./components/pagamenti-riconcilia-sollecito-ricerca/pagamenti-riconcilia-sollecito-ricerca.component";
import { PagamentiRiconciliaSollecitoDettaglioComponent } from "./components/pagamenti-riconcilia-sollecito-dettaglio/pagamenti-riconcilia-sollecito-dettaglio.component";
import { SharedRiscossioneModule } from "../shared-riscossione/shared-riscossione.module";
import { SharedRiscossioneService } from "../shared-riscossione/services/shared-riscossione.service";
import { PagamentiRiconciliaSollecitoRateRicercaComponent } from "./components/pagamenti-riconcilia-sollecito-rate-ricerca/pagamenti-riconcilia-sollecito-rate-ricerca.component";
import { PagamentiRiconciliaSollecitoRateDettaglioComponent } from "./components/pagamenti-riconcilia-sollecito-rate-dettaglio/pagamenti-riconcilia-sollecito-rate-dettaglio.component";
import { pagamentiRiconciliaManualePagamentoOrdinanzaComponent } from "./components/pagamenti-riconcilia-manuale-pagamento-ordinanza/pagamenti-riconcilia-manuale-pagamento-ordinanza.component";
import { pagamentiRiconciliaManualePagamentoOrdinanzaDettaglioComponent } from "./components/pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio/pagamenti-riconcilia-manuale-pagamento-ordinanza-dettaglio.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        PagamentiRoutingModule,
        SharedModule,
        SharedVerbaleModule,
        SharedPagamentiModule,
        SharedOrdinanzaModule,
        SharedRiscossioneModule,
        TemplateModule,
        SharedTemplateModule,
        SharedNotificaModule
    ],
    exports: [
        PagamentiRiconciliaVerbaleRicercaComponent,
        PagamentiRiconciliaVerbaleRiepilogoComponent,
        PagamentiRiconciliaVerbaleAllegatoComponent,
        PagamentiPianoCreaComponent,
        PagamentiPianoInsModComponent,
        PagamentiPianoRicercaComponent,
        PagamentiAnteprimaOrdinanzaComponent,
        PagamentiPianoViewComponent,
        PagamentiTemplateComponent,
        PagamentiRiconciliaRicercaOrdinanzaComponent,
        PagamentiRiconciliaOrdinanzaRiepilogoComponent,
        PagamentiRiconciliaOrdinanzaAllegatoComponent,
        PagamentiRiconciliaPianoComponent,
        PagamentiRiconciliaPianoDettaglioComponent,
        PagamentiPianoInsNotificaComponent,
        PagamentiPianoViewNotificaComponent,
        PagamentiRiconciliaSollecitoRicercaComponent,
        PagamentiRiconciliaSollecitoDettaglioComponent,
        PagamentiRiconciliaSollecitoRateRicercaComponent,
        PagamentiRiconciliaSollecitoRateDettaglioComponent,
        pagamentiRiconciliaManualePagamentoOrdinanzaComponent,
        pagamentiRiconciliaManualePagamentoOrdinanzaDettaglioComponent
    ],
    declarations: [
        PagamentiRiconciliaVerbaleRicercaComponent,
        PagamentiRiconciliaVerbaleRiepilogoComponent,
        PagamentiRiconciliaVerbaleAllegatoComponent,
        PagamentiPianoCreaComponent,
        PagamentiPianoInsModComponent,
        PagamentiPianoRicercaComponent,
        PagamentiAnteprimaOrdinanzaComponent,
        PagamentiPianoViewComponent,
        PagamentiTemplateComponent,
        PagamentiRiconciliaRicercaOrdinanzaComponent,
        PagamentiRiconciliaOrdinanzaRiepilogoComponent,
        PagamentiRiconciliaOrdinanzaAllegatoComponent,
        PagamentiRiconciliaPianoComponent,
        PagamentiRiconciliaPianoDettaglioComponent,
        PagamentiPianoInsNotificaComponent,
        PagamentiPianoViewNotificaComponent,
        PagamentiRiconciliaSollecitoRicercaComponent,
        PagamentiRiconciliaSollecitoDettaglioComponent,
        PagamentiRiconciliaSollecitoRateRicercaComponent,
        PagamentiRiconciliaSollecitoRateDettaglioComponent,
        pagamentiRiconciliaManualePagamentoOrdinanzaComponent,
        pagamentiRiconciliaManualePagamentoOrdinanzaDettaglioComponent
    ],
    providers: [PagamentiService, PagamentiUtilService, SharedRiscossioneService]
})
export class PagamentiModule { }