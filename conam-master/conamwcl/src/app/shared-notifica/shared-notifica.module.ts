import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SharedInserimentoNotificaComponent } from "./components/shared-inserimento-notifica/shared-inserimento-notifica.component";
import { SharedNotificaService } from "./services/shared-notifica.service";
import { SharedViewNotificaComponent } from "./components/shared-view-notifica/shared-view-notifica.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
    ],
    exports: [
        SharedInserimentoNotificaComponent,
        SharedViewNotificaComponent
    ],
    declarations: [
        SharedInserimentoNotificaComponent,
        SharedViewNotificaComponent
    ],
    providers: [SharedNotificaService]
})
export class SharedNotificaModule { }