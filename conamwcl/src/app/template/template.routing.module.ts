import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { TemplateContainerComponent } from "./components/template-container/template-container.component";

const routes: Routes = [
    {
        path: 'template', children: [
            { path: 'template-container/:id', component: TemplateContainerComponent, data: { breadcrumb: "Prova" } }, //MOCK, RIMUOVERE
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
export class TemplateRoutingModule { }