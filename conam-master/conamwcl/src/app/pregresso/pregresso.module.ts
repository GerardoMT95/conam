import { NgModule } from "@angular/core";
import { PregressoRoutingModule } from "./pregresso.routing.module";
import { PregressoAllegatoComponent } from "./components/pregresso-allegato/pregresso-allegato.component";
import { PregressoSoggettoComponent } from "./components/pregresso-soggetto/pregresso-soggetto.component";
import { PregressoDatiComponent } from "./components/pregresso-dati/pregresso-dati.component";
import { PregressoRiepilogoComponent } from "./components/pregresso-riepilogo/pregresso-riepilogo.component";
import { PregressoRicercaComponent } from "./components/pregresso-ricerca/pregresso-ricerca.component";
import { PregressoInserimentoComponent } from "./components/pregresso-inserimento/pregresso-inserimento.component";
import { PregressoInserimentoManualeComponent } from "./components/pregresso-inserimento-manuale/pregresso-inserimento-manuale.component";
import { PregressoInserimentoActaComponent } from "./components/pregresso-inserimento-acta/pregresso-inserimento-acta.component";
import { PregressoStatoComponent } from "./components/pregresso-stato/pregresso-stato.component";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { RifNormativiService } from "./services/rif-normativi.service";
import { VerbaleService } from "./services/verbale.service";
import { PregressoVerbaleService } from "./services/pregresso-verbale.service";
import { PregressoEliminazioneComponent } from "./components/pregresso-eliminazione/pregresso-eliminazione.component";
import { PregressoSoggettoRicercaFisicaComponent } from "./components/pregresso-soggetto/pregresso-soggetto-ricerca-fisica/pregresso-soggetto-ricerca-fisica.component";
import { PregressoSoggettoRicercaGiuridicaComponent } from "./components/pregresso-soggetto/pregresso-soggetto-ricerca-giuridica/pregresso-soggetto-ricerca-giuridica.component";
import { PregressoOrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent } from "./components/pregresso-ordinanza-ins-crea-ordinanza/pregresso-ordinanza-ins-crea-ordinanza.component";
import { PregressoOrdinanzeRiepilogoComponent } from "./components/pregresso-ordinanze-riepilogo/pregresso-ordinanze-riepilogo.component";
import { PregressoPianiRiepilogoComponent } from "./components/pregresso-piani-riepilogo/pregresso-piani-riepilogo.component";
import { PregressoSollecitiRiepilogoComponent } from "./components/pregresso-solleciti-riepilogo/pregresso-solleciti-riepilogo.component";
import { PregressoDisposizioniRiepilogoComponent } from "./components/pregresso-disposizioni-riepilogo/pregresso-disposizioni-riepilogo.component";
import { PregressoRicevuteRiepilogoComponent } from "./components/pregresso-ricevute-riepilogo/pregresso-ricevute-riepilogo.component";
import { PregressoPagamentiPianoInsModComponent } from "./components/pregresso-pagamenti-piano-ins-mod/pregresso-pagamenti-piano-ins-mod.component";
import { PregressoPagamentiPianoInsModDettComponent } from "./components/pregresso-pagamenti-piano-ins-mod-dett/pregresso-pagamenti-piano-ins-mod-dett.component";
import { PregressoRiscossioneSollecitoDettaglioInsComponent } from "./components/pregresso-riscossione-sollecito-dettaglio-ins/pregresso-riscossione-sollecito-dettaglio-ins.component";

import { SharedOrdinanzaModule } from "../shared-ordinanza/shared-ordinanza.module";
import { SharedNotificaModule } from "../shared-notifica/shared-notifica.module";

import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";
import { PagamentiService } from "../pagamenti/services/pagamenti.service";
import { PagamentiModule } from "../pagamenti/pagamenti.module";
import { SharedRiscossioneModule } from "../shared-riscossione/shared-riscossione.module";
import { PregressoDisposizioneGiudiceInsComponent } from "./components/pregresso-disposizione-giudice-ins/pregresso-disposizione-giudice-ins.component";
import { PregressoRicevutaPagamentoOrdinanzaInsComponent } from "./components/pregresso-ricevuta-pagamento-ordinanza-ins/pregresso-ricevuta-pagamento-ordinanza-ins.component";
import { PregressoSelezionaSoggettiComponent } from "./components/pregresso-seleziona-soggetti/pregresso-seleziona-soggetti.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        PregressoRoutingModule,
        SharedModule,
        SharedOrdinanzaModule,
        SharedNotificaModule,
        SharedVerbaleModule,
        PagamentiModule,
        SharedRiscossioneModule
    ],
    exports: [
        PregressoAllegatoComponent,
        PregressoSoggettoComponent,
        PregressoRicercaComponent,
        PregressoDatiComponent,
        PregressoRiepilogoComponent,
        PregressoEliminazioneComponent,
        PregressoInserimentoComponent,
        PregressoInserimentoManualeComponent,
        PregressoInserimentoActaComponent,
        PregressoStatoComponent,
        PregressoOrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent,
        PregressoOrdinanzeRiepilogoComponent,
        PregressoPianiRiepilogoComponent,
        PregressoSollecitiRiepilogoComponent,
        PregressoDisposizioniRiepilogoComponent,
        PregressoRicevuteRiepilogoComponent,
        PregressoPagamentiPianoInsModComponent,
        PregressoRiscossioneSollecitoDettaglioInsComponent,
        PregressoDisposizioneGiudiceInsComponent,
        PregressoRicevutaPagamentoOrdinanzaInsComponent,
        PregressoSelezionaSoggettiComponent,
        PregressoPagamentiPianoInsModDettComponent,
      
    ],
    declarations: [
        PregressoAllegatoComponent,
        PregressoSoggettoComponent,
        PregressoRicercaComponent,
        PregressoDatiComponent,
        PregressoRiepilogoComponent,
        PregressoEliminazioneComponent,
        PregressoInserimentoComponent,
        PregressoInserimentoManualeComponent,
        PregressoInserimentoActaComponent,
        PregressoSoggettoRicercaGiuridicaComponent,
        PregressoSoggettoRicercaFisicaComponent,
        PregressoStatoComponent,
        PregressoOrdinanzaInsCreaOrdinanzaGestContAmministrativoComponent,
        PregressoOrdinanzeRiepilogoComponent,
        PregressoPianiRiepilogoComponent,
        PregressoSollecitiRiepilogoComponent,
        PregressoDisposizioniRiepilogoComponent,
        PregressoRicevuteRiepilogoComponent,
        PregressoPagamentiPianoInsModComponent,
        PregressoRiscossioneSollecitoDettaglioInsComponent,
        PregressoDisposizioneGiudiceInsComponent,
        PregressoRicevutaPagamentoOrdinanzaInsComponent,
        PregressoSelezionaSoggettiComponent,
        PregressoPagamentiPianoInsModDettComponent
    ],
    providers: [RifNormativiService, VerbaleService,PregressoVerbaleService]
})
export class PregressoModule { }