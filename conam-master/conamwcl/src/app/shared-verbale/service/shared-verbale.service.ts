import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { MinVerbaleVO } from "../../commons/vo/verbale/min-verbale-vo";
import { RicercaVerbaleRequest } from "../../commons/request/verbale/ricerca-verbale-request";
import { Observable } from "rxjs";
import { StatoVerbaleVO, TipoAllegatoVO } from "../../commons/vo/select-vo";
import { RiepilogoVerbaleVO } from "../../commons/vo/verbale/riepilogo-verbale-vo";
import { AzioneVerbaleResponse } from "../../commons/response/verbale/azione-verbale-response";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { SalvaAllegatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-verbale-request";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import { SalvaAllegatoMultipleRequest } from "../../commons/request/salva-allegato-multiple-request";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";
import { RicercaProtocolloRequest } from "../../commons/request/verbale/ricerca-protocollo-request";
import { DocumentoProtocollatoVO } from "../../commons/vo/verbale/documento-protocollato-vo";
import { SalvaAllegatoProtocollatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-protocollato-verbale-request";
import { RicercaProtocolloResponse } from "../../commons/response/verbale/ricerca-protocollo-response";


@Injectable()
export class SharedVerbaleService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(SharedVerbaleService.name);
    }

    ricercaVerbale(ricercaVerbaleRequest: RicercaVerbaleRequest): Observable<Array<MinVerbaleVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ricercaVerbale';
        return this.http.post<Array<MinVerbaleVO>>(url, ricercaVerbaleRequest);
    }

    getStatiRicercaVerbale(): Observable<Array<StatoVerbaleVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getStatiRicercaVerbale';
        return this.http.get<Array<StatoVerbaleVO>>(url);
    }

    riepilogoVerbale(idVerbale: number): Observable<RiepilogoVerbaleVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/riepilogo';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<RiepilogoVerbaleVO>(url, { params: params });
    }

    riepilogoVerbaleAudizione(idVerbale: number): Observable<RiepilogoVerbaleVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/riepilogoVerbaleAudizione';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<RiepilogoVerbaleVO>(url, { params: params });
    }

    eliminaAllegato(idAllegato: number, idVerbale: number) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/eliminaAllegato';
        let params = new HttpParams().set('idAllegato', idAllegato.toString()).set('idVerbale', idVerbale.toString());
        return this.http.get(url, { params: params });
    }

    getAzioniVerbale(idVerbale: number): Observable<AzioneVerbaleResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getAzioniVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<AzioneVerbaleResponse>(url, { params: params });
    }

    getSoggettiByIdVerbale(idVerbale: number, includiControlloUtenteProprietario: Boolean): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getSoggettiByIdVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString()).set('controlloUtente', includiControlloUtenteProprietario.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    getSoggettiByIdVerbaleConvocazione(idVerbale: number, includiControlloUtenteProprietario: Boolean): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getSoggettiByIdVerbaleConvocazione';
        let params = new HttpParams().set('idVerbale', idVerbale.toString()).set('controlloUtente', includiControlloUtenteProprietario.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    getSoggettiByIdVerbaleAudizione(idAllegatoVerbaleAudizione: number): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getSoggettiByIdVerbaleAudizione';
        let params = new HttpParams().set('idAllegatoVerbaleAudizione', idAllegatoVerbaleAudizione.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    getTipologiaAllegatiAllegabiliVerbale(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getTipologiaAllegatiAllegabiliVerbale';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    getTipologiaAllegatiVerbale(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getTipologiaAllegatiVerbale';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    salvaAllegatoVerbale(input: SalvaAllegatoVerbaleRequest): Observable<AllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAllegatoVerbale';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", input.file)
        return this.http.post<AllegatoVO>(url, form);
    }

    
    salvaAllegatoOrdinanzaMaster (input: SalvaAllegatoMultipleRequest): Observable<AllegatoVO> { 
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoOrdinanzaMaster';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
       
        input.allegati.forEach(e => {
            let name = null;
            if (e.file != null) {

                name = e.file.name;
                form.append("files", e.file, name)
            }
        }
        );

        return this.http.post<AllegatoVO>(url, form);
    }

    salvaAllegatoVerbaleMaster (input: SalvaAllegatoMultipleRequest): Observable<AllegatoVO> { 
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAllegatoVerbaleMaster';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
       
        input.allegati.forEach(e => {
            let name = null;
            if (e.file != null) {

                name = e.file.name;
                form.append("files", e.file, name)
            }
        }
        );

        return this.http.post<AllegatoVO>(url, form);
    }

    creaAllegatoVerbale(input: SalvaAllegatoVerbaleRequest): Observable<AllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAllegatoVerbale';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", null)
        return this.http.post<AllegatoVO>(url, form);
    }


    isTipoAllegatoAllegabile(idVerbale: number, codiceDocumento: string): Observable<boolean> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/isTipoAllegatoAllegabile';
        let params = new HttpParams().set('idVerbale', idVerbale.toString()).set('codiceDocumento', codiceDocumento);
        return this.http.get<boolean>(url, { params: params });
    }

    isEnableCaricaVerbaleAudizione(idVerbale: number): Observable<boolean>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/isEnableCaricaVerbaleAudizione';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    ricercaProtocolloSuACTA(ricerca: RicercaProtocolloRequest) {
        if(!('flagPregresso' in ricerca)){
            ricerca.flagPregresso = false;
        }
        var url: string = this.config.getBEServer() + '/restfacade/allegato/ricercaProtocolloSuACTA';
        let params;
        if( ricerca.idVerbale){
            params = new HttpParams().set('idVerbale', ricerca.idVerbale.toString()).set('protocollo',ricerca.numeroProtocollo).set('flagPregresso',ricerca.flagPregresso.toString());
        }else{
            params = new HttpParams().set('protocollo',ricerca.numeroProtocollo).set('flagPregresso',ricerca.flagPregresso.toString());
        }
        return this.http.get<RicercaProtocolloResponse>(url, { params: params });
    }

    salvaAllegatoProtocollato(salvaAllegatoProtocollatoVerbaleRequest: SalvaAllegatoProtocollatoVerbaleRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAllegatoProtocollatoVerbale';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoVerbaleRequest);
    }

    getVerbaleByIdOrdinanza(idOrdinanza: number): Observable<MinVerbaleVO>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getVerbaleByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<MinVerbaleVO>(url, { params: params });
    }
    getMessaggioManualeByIdVerbale(idVerbale: number): Observable<any>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getMessaggioManualeByIdVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<any>(url, { params: params });
    }
}