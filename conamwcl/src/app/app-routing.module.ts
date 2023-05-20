import { NgModule } from '@angular/core';
import { RouterModule, Routes, ExtraOptions } from '@angular/router';
import { HomeComponent } from './home/component/home/home.component';
import { ErrorComponent } from './core/component/error/error.component';


const appRoutes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'errorrouting', component: ErrorComponent, data: { disableHeaderFooter: "" } },
  { path: 'errorrest', component: ErrorComponent }, 
  { path: 'todo', component: ErrorComponent, data: { err:"Sezione Non ancora disponibile" , message:"La sezione a cui si è cercato di accedere non è ancora disponibile"} },
  { path: '**', redirectTo: 'errorrouting', pathMatch: 'full' }
];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      configRouter
    ),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }