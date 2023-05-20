import { NgModule } from "@angular/core";
import { FascicoloRoutingModule } from "./fascicolo.routing.module";
import { FascicoloAllegatoDaActaComponent } from "./components/fascicolo-allegato-da-acta/fascicolo-allegato-da-acta.component";

import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { PregressoModule } from "../pregresso/pregresso.module";
import { FascicoloService } from "./services/fascicolo.service";
import { VerbaleService } from "../verbale/services/verbale.service";
import { SharedVerbaleModule } from "../shared-verbale/shared-verbale.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        FascicoloRoutingModule,
        SharedModule,
        PregressoModule,
        SharedVerbaleModule,
    ],
    exports: [
        FascicoloAllegatoDaActaComponent
    ],
    declarations: [
        FascicoloAllegatoDaActaComponent
    ],
    providers: [VerbaleService,FascicoloService]
})
export class FascicoloModule { }