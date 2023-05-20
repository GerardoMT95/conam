import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { TableSoggettiOrdinanza } from "../../commons/table/table-soggetti-ordinanza";
import { LoggerService } from "../../core/services/logger/logger.service";
import { SollecitoVO } from "../../commons/vo/riscossione/sollecito-vo";
import { Observable } from "rxjs";
import { ConfigService } from "../../core/services/config.service";
import { AllegatoUtilsSharedService } from "../../shared/service/allegato-utils-shared.service";
import { RicercaSoggettiRiscossioneCoattivaRequest } from "../../commons/request/riscossione/ricerca-soggetti-riscossione-coattiva-request";
import { SoggettiRiscossioneCoattivaVO } from "../../commons/vo/riscossione/soggetti-riscossione-coattiva-vo";
import { SalvaSoggettiRiscossioneCoattivaRequest } from "../../commons/request/riscossione/salva-soggetti-riscossione-coattiva-request";
import { RicercaStoricaSoggettiRiscossioneCoattivaRequest } from "../../commons/request/riscossione/ricerca-storica-soggetti-riscossione-coattiva-request";
import { InvioMassivoVO } from "../../commons/vo/riscossione/invio-massivo-vo";
import { SelectVO } from "../../commons/vo/select-vo";

//JIRA - Gestione Notifica
import { SalvaSollecitoRequest } from "../../commons/request/riscossione/salva-sollecito-request";
import { PianoRateizzazioneVO } from "../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { SalvaSollecitoRateRequest } from "../../commons/request/riscossione/salva-sollecito-rate-request";


@Injectable()
export class RiscossioneService {


    public soggettoSollecito: TableSoggettiOrdinanza;

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService,
        private allegatoUtilsSharedService: AllegatoUtilsSharedService) {
        this.logger.createService(RiscossioneService.name);
    }

    //salvaSollecito(body: SollecitoVO): Observable<number> {
    salvaSollecito(body: SalvaSollecitoRequest): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/salvaSollecito';
        return this.http.post<number>(url, body);
    }
    //salvaSollecito(body: SollecitoVO): Observable<number> {
    salvaSollecitoRate(body: SalvaSollecitoRateRequest): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/salvaSollecitoRate';
        return this.http.post<number>(url, body);
    }


    eliminaSollecito(id: number) {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/eliminaSollecito';
        let params = new HttpParams().set('idSollecito', id.toString());
        return this.http.delete(url, { params: params });
    }

    getSollecitoById(idSollecito: number): any {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/getSollecitoById';
        let params = new HttpParams().set('idSollecito', idSollecito.toString());
        return this.http.get<SollecitoVO>(url, { params: params });
    }

    inviaRichiestaBollettiniSollecito(idSollecito: number): any {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/inviaRichiestaBollettiniSollecito/' + idSollecito;
        return this.http.post(url, {});
    }

    //usato base 64 per gestire eventuali errori rest specifici
    downloadBollettini(idSollecito: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/downloadBollettini';
        let params: HttpParams = new HttpParams().set('idSollecito', idSollecito.toString());
        return this.http.get(url, { params: params }).map(res => {
            if (res["file"] != null) {
                return this.allegatoUtilsSharedService.b64toBlob(res["file"], 'application/pdf;base64')
            }
        }
        );
    }

    downloadLettera(idSollecito: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/downloadLettera';
        let params: HttpParams = new HttpParams().set('idSollecito', idSollecito.toString());
        return this.http.get(url, { params: params, responseType: 'blob' }).map(res => new Blob([res]));
    }

    ricercaSoggettiRiscossioneCoattiva(request: RicercaSoggettiRiscossioneCoattivaRequest): Observable<Array<SoggettiRiscossioneCoattivaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/ricercaOrdinanzaSoggetti';
        return this.http.post<Array<SoggettiRiscossioneCoattivaVO>>(url, request);
    }

    aggiungiInListaRiscossione(idSoggettoOrdinanzas: Array<number>): Observable<Array<SoggettiRiscossioneCoattivaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/aggiungiInListaRiscossione';
        return this.http.post<Array<SoggettiRiscossioneCoattivaVO>>(url, idSoggettoOrdinanzas);
    }

    getSoggettiRiscossioneBozza(): Observable<Array<SoggettiRiscossioneCoattivaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/getSoggettiRiscossioneBozza';
        return this.http.get<Array<SoggettiRiscossioneCoattivaVO>>(url);
    }

    getstatiRiscossioneCoattiva():Observable<Array<SelectVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/getstatiRiscossioneCoattiva';
        return this.http.get<Array<SelectVO>>(url);
    }

    getSoggettiRiscossioneNoBozza(id:number): Observable<Array<SoggettiRiscossioneCoattivaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/getSoggettiRiscossioneNoBozza';
        return this.http.post<Array<SoggettiRiscossioneCoattivaVO>>(url, id);
    }

    getSoggettiStoriciRiscossione(request: RicercaStoricaSoggettiRiscossioneCoattivaRequest): Observable<Array<SoggettiRiscossioneCoattivaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/getSoggettiStoriciRiscossione';
        return this.http.post<Array<SoggettiRiscossioneCoattivaVO>>(url, request);
    }


    salvaSoggettoRiscossione(data: SalvaSoggettiRiscossioneCoattivaRequest): Observable<SoggettiRiscossioneCoattivaVO> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/salvaSoggettoRiscossione';
        return this.http.post<SoggettiRiscossioneCoattivaVO>(url, data);
    }

    deleteSoggettoRiscossione(id: number): Observable<void> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/deleteSoggettoRiscossione';
        let params: HttpParams = new HttpParams().set('idRiscossione', id.toString());
        return this.http.delete<void>(url, { params: params });
    }

    invioMassivoRiscossione() : Observable<InvioMassivoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/riscossione/invioSoggettiInRiscossione';
        return this.http.post<InvioMassivoVO>(url, null);
    }
    getMessaggioManualeByidOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto: number): Observable<any>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getMessaggioManualeByIdOrdinanzaVerbaleSoggetto';
        let params = new HttpParams().set('IdOrdinanzaVerbaleSoggetto', idOrdinanzaVerbaleSoggetto.toString());
        return this.http.get<any>(url, { params: params });
    }
    getDettaglioPianoById(idPiano: number, flagModifica: boolean): Observable<PianoRateizzazioneVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/dettaglioPianoById';
        let params: HttpParams = new HttpParams().set('idPiano', idPiano.toString()).set('flagModifica', flagModifica.toString());
        return this.http.get<PianoRateizzazioneVO>(url, { params: params });
    }
    inviaRichiestaBollettini(idPiano: number) {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/inviaRichiestaBollettiniPiano/' + idPiano.toString();
        return this.http.post(url, null);
    }
    

}