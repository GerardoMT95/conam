import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { AmbitoVO } from "../../commons/vo/select-vo";
import { Observable } from "rxjs/Observable";
import { RicercaLeggeProntuarioRequest } from "../../commons/request/prontuario/ricerca-legge-prontuario-request";
import { ProntuarioVO } from "../../commons/vo/prontuario/prontuario-vo";

@Injectable()
export class ProntuarioService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(ProntuarioService.name);
    }

    getAmbiti(): Observable<Array<AmbitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/getAmbiti';
        return this.http.get<Array<AmbitoVO>>(url);
    }

    getAmbitiEliminabili(): Observable<Array<AmbitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/getAmbitiEliminabili';
        return this.http.get<Array<AmbitoVO>>(url);
    }

    salvaAmbito(nuovoAmbito: AmbitoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/salvaAmbito';
        let body = nuovoAmbito;
        return this.http.post<Response>(url, body);
    }

    eliminaAmbito(idAmbito: number) {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/eliminaAmbito';
        let params = new HttpParams().set('idAmbito', idAmbito.toString());
        return this.http.delete(url, { params: params });
    }

    ricercaLeggeProntuario(ricercaLeggeProntuario: RicercaLeggeProntuarioRequest): Observable<Array<ProntuarioVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/ricercaLeggeProntuario';
        let body = ricercaLeggeProntuario;
        return this.http.post<Array<ProntuarioVO>>(url, body);
    }

    salvaLeggeProntuario(nuovoRiferimentoNormativo: ProntuarioVO): Observable<ProntuarioVO> {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/salvaLeggeProntuario';
        let body = nuovoRiferimentoNormativo;
        return this.http.post<ProntuarioVO>(url, body);
    }

    eliminaLeggeProntuario(idLettera: number) {
        var url: string = this.config.getBEServer() + '/restfacade/prontuario/eliminaLeggeProntuario';
        let params = new HttpParams().set('idLettera', idLettera.toString());
        return this.http.delete(url, { params: params });
    }
}