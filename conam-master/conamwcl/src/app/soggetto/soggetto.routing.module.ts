import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SoggettoRiepilogoComponent } from "./components/soggetto-riepilogo/soggetto-riepilogo.component";


const routes: Routes = [
    { path: 'soggetto', children:[
        { path: '', redirectTo: 'riepilogo', pathMatch: 'full' },
        { path: 'riepilogo', component: SoggettoRiepilogoComponent, data: { breadcrumb: "Riepilogo"} },
        { path: 'riepilogo/:id', component: SoggettoRiepilogoComponent, data: { breadcrumb: "Riepilogo"} },
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
export class SoggettoRoutingModule { }