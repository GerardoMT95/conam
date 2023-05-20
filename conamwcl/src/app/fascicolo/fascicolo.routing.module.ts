import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { FascicoloAllegatoDaActaComponent } from "./components/fascicolo-allegato-da-acta/fascicolo-allegato-da-acta.component";

const routes: Routes = [
    { path: 'fascicolo', children:[
        { path: 'allegato-da-acta/:id', component: FascicoloAllegatoDaActaComponent, data: { breadcrumb: "Allegato da ACTA"} }
    ]
}]
@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class FascicoloRoutingModule { }