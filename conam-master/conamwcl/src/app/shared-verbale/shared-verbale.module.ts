import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedVerbaleRicercaComponent } from './component/shared-verbale-ricerca/shared-verbale-ricerca.component';
import { SharedVerbaleRiepilogoComponent } from './component/shared-verbale-riepilogo/shared-verbale-riepilogo.component';
import { FormsModule } from '@angular/forms';
import { SharedVerbaleAllegatoTabellaComponent } from './component/shared-verbale-allegato-tabella/shared-verbale-allegato-tabella.component';
import { SharedModule } from '../shared/shared.module';
import { SharedVerbaleService } from './service/shared-verbale.service';
import { SharedVerbaleDettaglioSoggettiComponent } from './component/shared-verbale-dettaglio-soggetti/shared-verbale-dettaglio-soggetti.component';
import { SharedVerbaleConfigService } from './service/shared-verbale-config.service';
import { SharedVerbaleScrittoDifensivoSoggettoComponent } from './component/shared-verbale-scritto-difensivo-soggetto/shared-verbale-scritto-difensivo-soggetto.component';
import { SharedVerbaleScrittoDifensivoDatiVerbaleComponent } from './component/shared-verbale-scritto-difensivo-dati-verbale/shared-verbale-scritto-difensivo-dati-verbale.component';
import { SharedVerbaleRicercaScrittoDifensivoComponent } from './component/shared-verbale-ricerca-scritto-difensivo/shared-verbale-ricerca-scritto-difensivo.component';

@NgModule({
    imports: [CommonModule, RouterModule, FormsModule, SharedModule],
    exports: [
        SharedVerbaleRicercaComponent,
        SharedVerbaleRicercaScrittoDifensivoComponent,
        SharedVerbaleRiepilogoComponent,
        SharedVerbaleAllegatoTabellaComponent,
        SharedVerbaleDettaglioSoggettiComponent,
        SharedVerbaleScrittoDifensivoDatiVerbaleComponent,
        SharedVerbaleScrittoDifensivoSoggettoComponent
    ],
    declarations: [
        SharedVerbaleRicercaComponent,
        SharedVerbaleRicercaScrittoDifensivoComponent,
        SharedVerbaleRiepilogoComponent,
        SharedVerbaleDettaglioSoggettiComponent,
        SharedVerbaleAllegatoTabellaComponent,
        SharedVerbaleScrittoDifensivoDatiVerbaleComponent,
        SharedVerbaleScrittoDifensivoSoggettoComponent
    ],
    providers: [SharedVerbaleService, SharedVerbaleConfigService],
})
export class SharedVerbaleModule { }