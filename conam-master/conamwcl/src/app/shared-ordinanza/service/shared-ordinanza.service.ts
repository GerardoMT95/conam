import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { OrdinanzaVO } from "../../commons/vo/ordinanza/ordinanza-vo";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { RicercaOrdinanzaRequest } from "../../commons/request/ordinanza/ricerca-ordinanza-request";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { TipoAllegatoVO, StatoOrdinanzaVO } from "../../commons/vo/select-vo";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import { AzioneOrdinanzaRequest } from "../../commons/request/ordinanza/azione-ordinanza-request";
import { AzioneOrdinanzaResponse } from "../../commons/response/ordinanza/azione-ordinanza-response";
import { AllegatoUtilsSharedService } from "../../shared/service/allegato-utils-shared.service";
import { RicercaSoggettiOrdinanzaRequest } from "../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { TipologiaAllegabiliOrdinanzaSoggettoRequest } from "../../commons/request/ordinanza/tipologia-allegabili-ordinanza-soggetto-request";
import { SalvaAllegatoOrdinanzaRequest } from "../../commons/request/ordinanza/salva-allegato-ordinanza-request";
import { SalvaAllegatoOrdinanzaVerbaleSoggettoRequest } from "../../commons/request/ordinanza/salva-allegato-ordinanza-verbale-soggetto-request";
import { DatiSentenzaResponse } from "../../commons/response/ordinanza/dati-sentenza-response";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";
import { SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest } from "../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-soggetto-request";
import { SalvaAllegatoProtocollatoOrdinanzaRequest } from "../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-request";
import { MinVerbaleVO } from "../../commons/vo/verbale/min-verbale-vo";
import { AccontoOrdinanzaRequest } from "../../commons/request/ordinanza/acconto-ordinanza-request";

@Injectable()
export class SharedOrdinanzaService implements OnDestroy {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService,
        private allegatoUtilsSharedService: AllegatoUtilsSharedService) {
        this.logger.createService(SharedOrdinanzaService.name);
    }

    getDettaglioOrdinanza(idOrdinanza: number): Observable<OrdinanzaVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/dettaglioOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<OrdinanzaVO>(url, { params: params });
    }

    getDettaglioOrdinanzaByIdSoggettoOrdinanza(idOrdinanzaSoggetti: Array<number>): Observable<OrdinanzaVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/dettaglioOrdinanzaByIdOrdinanzaSoggetto';
        return this.http.post<OrdinanzaVO>(url, idOrdinanzaSoggetti);
    }

    getSoggettiOrdinanza(idOrdinanza: number): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getSoggettiByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest): Observable<Array<OrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/ricercaOrdinanza';
        return this.http.post<Array<OrdinanzaVO>>(url, ricercaOrdinanzaRequest);
    }
    ricercaOrdinanzaAnnullamento(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest): Observable<Array<OrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/ricercaOrdinanzaPerAnnullamento';
        return this.http.post<Array<OrdinanzaVO>>(url, ricercaOrdinanzaRequest);
    }
    ricercaOrdinanzaNonPagata(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest): Observable<Array<OrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/ricercaOrdinanzaNonPagata';
        return this.http.post<Array<OrdinanzaVO>>(url, ricercaOrdinanzaRequest);
    }
    getDocumentiOrdinanza(idOrdinanza: number): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getAllegatiByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<AllegatoVO>>(url, { params: params });
    }

    getAllegatiByIdOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto : Array<number>): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getAllegatiByIdOrdinanzaVerbaleSoggetto';
        return this.http.post<Array<AllegatoVO>>(url, idOrdinanzaVerbaleSoggetto);
    }


    azioneOrdinanza(request: AzioneOrdinanzaRequest): Observable<AzioneOrdinanzaResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/azioneOrdinanza';
        return this.http.post<AzioneOrdinanzaResponse>(url, request);
    }

    inviaRichiestaBollettiniOrdinanza(idOrdinanza: number): any {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/inviaRichiestaBollettiniOrdinanza/' + idOrdinanza;
        return this.http.post(url, {});
    }
    getVerbaleByIdOrdinanza(idOrdinanza: number): Observable<MinVerbaleVO>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getVerbaleByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<MinVerbaleVO>(url, { params: params });
    }

    //usato base 64 per gestire eventuali errori rest specifici
    downloadBollettini(idOrdinanza: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/downloadBollettini';
        let params: HttpParams = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get(url, { params: params }).map(res => {
            if (res["file"] != null) {
                return this.allegatoUtilsSharedService.b64toBlob(res["file"], 'application/pdf;base64')
            }
        }
        );
    }

    downloadLettera(idOrdinanza: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/downloadLettera';
        let params: HttpParams = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get(url, { params: params, responseType: 'blob' }).map(res => new Blob([res]));
    }

    ricercaSoggettiOrdinanza(request: RicercaSoggettiOrdinanzaRequest): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/ricercaSoggetti';
        return this.http.post<Array<SoggettoVO>>(url, request);
    }

    getTipologiaAllegatiAllegabiliByOrdinanza(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getTipologiaAllegatiAllegabiliByOrdinanza';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto(request: TipologiaAllegabiliOrdinanzaSoggettoRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    azioneOrdinanzaSoggetto(request: AzioneOrdinanzaRequest): Observable<AzioneOrdinanzaResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/azioneOrdinanzaSoggetto';
        return this.http.post<AzioneOrdinanzaResponse>(url, request);
    }

    salvaAllegatoOrdinanza(input: SalvaAllegatoOrdinanzaRequest): Observable<AllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoOrdinanza';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", input.file)
        return this.http.post<AllegatoVO>(url, form);
    }

    salvaAllegatoOrdinanzaVerbaleSoggetto(input: SalvaAllegatoOrdinanzaVerbaleSoggettoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoOrdinanzaSoggetto';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", input.file)
        return this.http.post<AllegatoVO>(url, form);
    }

    salvaAllegatoProtocollatoOrdinanzaSoggetto(salvaAllegatoProtocollatoOrdinanzaSoggettoRequest: SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoProtocollatoOrdinanzaSoggetto';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoOrdinanzaSoggettoRequest);
    }

    salvaAllegatoProtocollatoOrdinanza(salvaAllegatoProtocollatoOrdinanzaRequest: SalvaAllegatoProtocollatoOrdinanzaRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoProtocollatoOrdinanza';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoOrdinanzaRequest);
    }

    creallegatoOrdinanzaVerbaleSoggetto(input: SalvaAllegatoOrdinanzaVerbaleSoggettoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAllegatoOrdinanzaSoggetto';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", null)
        return this.http.post<AllegatoVO>(url, form);
    }

    getStatiOrdinanza(): Observable<Array<StatoOrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getStatiOrdinanza';
        return this.http.get<Array<StatoOrdinanzaVO>>(url);
    }

    getDatiSentenza(id: number): Observable<DatiSentenzaResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getDatiSentenzaByIdOrdinanzaVerbaleSoggetto';
        let params = new HttpParams().set('idOrdinanzaVerbaleSoggetto', id.toString());
        return this.http.get<DatiSentenzaResponse>(url, { params: params });
    }
    
    isLetteraSaved(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/isLetteraSaved';
        return this.http.post<IsCreatedVO>(url, request);
    }

    salvaAcconto(body: AccontoOrdinanzaRequest): Observable<any> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaAcconto';
        let form = new FormData();
        form.append("data", JSON.stringify(body, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        if(body.file){
        form.append("files", body.file)
        }
        return this.http.post<any>(url, form);
    
    }
    ngOnDestroy(): void {
        this.logger.destroyService(SharedOrdinanzaService.name);
    }
}

