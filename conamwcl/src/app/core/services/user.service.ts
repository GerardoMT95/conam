import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { ConfigService } from './config.service';
import { LoggerService } from './logger/logger.service';
import { HttpClient } from "@angular/common/http";
import { ProfiloVO } from '../../commons/vo/profilo-vo';
import { UseCase } from '../../commons/class/use-case';


@Injectable()
export class UserService {

    private profilo = new BehaviorSubject<ProfiloVO>(null);
    profilo$: Observable<ProfiloVO> = this.profilo.asObservable();

    getProfilo() {
        var url: string = this.config.getBEServer() + '/restfacade/user/getProfilo';
        this.http.get<ProfiloVO>(url).subscribe(data => {
            this.profilo.next(data);
        }, (err) => {
            this.logger.error("ERRORE GET PROFILATURA");
        });
    }

    logOut() {
        var url: string = this.config.getBEServer() + '/restfacade/user/localLogout';
        return this.http.get(url);
    }

    constructor(private logger: LoggerService, private config: ConfigService, private http: HttpClient) {
        this.logger.createService(UserService.name);
    }
}