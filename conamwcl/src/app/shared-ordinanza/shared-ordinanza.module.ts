import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { SharedOrdinanzaRicercaComponent } from './component/shared-ordinanza-ricerca/shared-ordinanza-ricerca.component';
import { SharedOrdinanzaDettaglio } from './component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component';
import { SharedOrdinanzaDettaglioSoggetti } from './component/shared-ordinanza-dettaglio-soggetti/shared-ordinanza-dettaglio-soggetti.component';
import { SharedOrdinanzaConfigService } from './service/shared-ordinanza-config.service';
import { SharedOrdinanzaService } from './service/shared-ordinanza.service';
import { SharedOrdinanzaDettaglioDocumenti } from './component/shared-ordinanza-dettaglio-documenti/shared-ordinanza-dettaglio-documenti.component';
import { SharedOrdinanzaRiepilogoComponent } from './component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component';
import { SharedOrdinanzaSentenza } from './component/shared-ordinanza-sentenza/shared-ordinanza-sentenza.component';
import { SharedSoggettoRicercaComponent } from './component/shared-sogetto-ricerca/shared-soggetto-ricerca.component';

@NgModule({
    imports: [CommonModule, RouterModule, FormsModule, SharedModule],
    exports: [
        SharedOrdinanzaDettaglio,
        SharedOrdinanzaDettaglioSoggetti,
        SharedOrdinanzaRicercaComponent,
        SharedOrdinanzaDettaglioDocumenti,
        SharedOrdinanzaRiepilogoComponent,
        SharedOrdinanzaSentenza,
        SharedSoggettoRicercaComponent
    ],
    declarations: [
        SharedOrdinanzaDettaglio,
        SharedOrdinanzaDettaglioSoggetti,
        SharedOrdinanzaRicercaComponent,
        SharedOrdinanzaDettaglioDocumenti,
        SharedOrdinanzaRiepilogoComponent,
        SharedOrdinanzaSentenza,
        SharedSoggettoRicercaComponent
    ],
    providers: [SharedOrdinanzaConfigService, SharedOrdinanzaService],
})

export class SharedOrdinanzaModule { }