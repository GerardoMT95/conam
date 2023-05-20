import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SoggettoRoutingModule } from "./soggetto.routing.module";
import { SoggettoRiepilogoComponent } from "./components/soggetto-riepilogo/soggetto-riepilogo.component";
import { SharedSoggettoModule } from "../shared-soggetto/shared-soggetto.module";
import { SoggettoService } from "./services/soggetto.service";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SoggettoRoutingModule,
        SharedModule,
        SharedSoggettoModule,
    ],
    exports: [
        SoggettoRiepilogoComponent,
    ],
    declarations: [
        SoggettoRiepilogoComponent,
    ],
    providers: [SoggettoService]
})
export class SoggettoModule { }