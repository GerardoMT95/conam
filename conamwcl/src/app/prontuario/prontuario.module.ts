import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ProntuarioRoutingModule } from "./prontuario.routing.module";
import { ProntuarioUtilityNormeComponent } from "./components/prontuario-utility-norme/prontuario-utility-norme.component";
import { SharedModule } from "../shared/shared.module";
import { ProntuarioService } from "./services/prontuario.service";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        ProntuarioRoutingModule,
        SharedModule
    ],
    exports: [
        ProntuarioUtilityNormeComponent
    ],
    declarations: [
        ProntuarioUtilityNormeComponent
    ],
    providers: [ProntuarioService]
})
export class ProntuarioModule { }