import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ProntuarioUtilityNormeComponent } from "./components/prontuario-utility-norme/prontuario-utility-norme.component";

const routes: Routes = [
    {
        path: 'prontuario', children: [
            { path: '', redirectTo: 'utility', pathMatch: 'full' },
            { path: 'utility', component: ProntuarioUtilityNormeComponent, data: { breadcrumb: "Utility Norme" } }]
    }]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class ProntuarioRoutingModule { }