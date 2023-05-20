import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ConfigService } from './services/config.service';
import { HomeModule } from '../home/home.module';
import { RouterModule } from '@angular/router';
import { ErrorComponent } from './component/error/error.component';
import { LoggerService } from './services/logger/logger.service';
import { ConsoleLoggerService } from './services/logger/console-logger.service';
import { XsrfInterceptor } from './interceptor/httpXSRFInterceptor';
import { HandleExceptionInterceptor } from './interceptor/handleExceptionInterceptor';
import { FooterComponent } from './component/footer/footer.component';
import { HeaderComponent } from './component/header/header.component';
import { Navbar } from './component/navbar/navbar.component';
import { UtilSubscribersService } from './services/util-subscribers-service';
import { VerbaleModule } from '../verbale/verbale.module';
import { FaseGiurisdizionaleModule } from '../fase-giurisdizionale/fase-giurisdizionale.module';
import { PagamentiModule } from '../pagamenti/pagamenti.module';
import { UserService } from './services/user.service';
import { LuoghiService } from './services/luoghi.service';
import { ProntuarioModule } from '../prontuario/prontuario.module';
import { TemplateModule } from '../template/template.module';
import { GestioneContenziosoAmministrativoModule } from '../gestione-contenzioso-amministrativo/gestione-contenzioso-amministrativo.module';
import { RiscossioneModule } from '../riscossione/riscossione.module';
import { SoggettoModule } from '../soggetto/soggetto.module';
import { FascicoloModule} from '../fascicolo/fascicolo.module';
import { PregressoModule} from '../pregresso/pregresso.module';

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule,
        RouterModule,
        HomeModule,
        VerbaleModule,
        ProntuarioModule,
        FaseGiurisdizionaleModule,
        PagamentiModule,
        TemplateModule,
        GestioneContenziosoAmministrativoModule,
        RiscossioneModule,
        SoggettoModule,
        FascicoloModule,
        PregressoModule
    ],
    exports: [
        ErrorComponent,
        FooterComponent,
        HeaderComponent,
        Navbar],
    declarations: [
        ErrorComponent,
        FooterComponent,
        HeaderComponent,
        Navbar],
    providers: [ConfigService, UtilSubscribersService, UserService, LuoghiService,
        { provide: LoggerService, useClass: ConsoleLoggerService },
        { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: HandleExceptionInterceptor, multi: true }]
})
export class CoreModule { }