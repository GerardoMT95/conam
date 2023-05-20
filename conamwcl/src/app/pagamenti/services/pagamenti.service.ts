import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs/Observable";
import { RicercaPianoRateizzazioneRequest } from "../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { MinPianoRateizzazioneVO } from "../../commons/vo/piano-rateizzazione/min-piano-rateizzazione-vo";
import { TableSoggettiOrdinanza } from "../../commons/table/table-soggetti-ordinanza";
import { StatoPianoVO } from "../../commons/vo/select-vo";
import { PianoRateizzazioneVO } from "../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { AllegatoUtilsSharedService } from "../../shared/service/allegato-utils-shared.service";
import { RataVO } from "../../commons/vo/piano-rateizzazione/rata-vo";
import { SollecitoVO } from "../../commons/vo/riscossione/sollecito-vo";
import { RiconciliaSollecitoResponse } from "../../commons/response/pagamenti/riconcilia-sollecito-response";


@Injectable()
export class PagamentiService {

    constructor(private http: HttpClient,
        private config: ConfigService, private logger: LoggerService,
        private allegatoUtilsSharedService: AllegatoUtilsSharedService) {
        this.logger.createService(PagamentiService.name);
    }

    //sogg per cui creare il piano
    public soggettiCreaPiano: Array<TableSoggettiOrdinanza>

    ricercaSoggettiPiano(request: RicercaPianoRateizzazioneRequest): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/ricercaSoggetti';
        return this.http.post<Array<SoggettoVO>>(url, request);
    }

    ricercaPiani(request: RicercaPianoRateizzazioneRequest): Observable<Array<MinPianoRateizzazioneVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/ricercaPiani';
        return this.http.post<Array<MinPianoRateizzazioneVO>>(url, request);
    }

    getStatiPiano(): Observable<Array<StatoPianoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/getStatiPiano';
        return this.http.get<Array<StatoPianoVO>>(url);
    }

    precompilaPiano(body: Array<number>): Observable<PianoRateizzazioneVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/precompilaPiano';
        return this.http.post<PianoRateizzazioneVO>(url, body);
    }

    calcolaRata(body: PianoRateizzazioneVO): Observable<PianoRateizzazioneVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/calcolaRata';
        return this.http.post<PianoRateizzazioneVO>(url, body);
    }

    ricalcolaRata(body: PianoRateizzazioneVO): Observable<PianoRateizzazioneVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/ricalcolaRate';
        return this.http.post<PianoRateizzazioneVO>(url, body);
    }

    salvaPiano(body: PianoRateizzazioneVO): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/salvaPiano';
        return this.http.post<number>(url, body);
    }

    creaPiano(body: number): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/creaPiano';
        return this.http.post<number>(url, body);
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

    //usato base 64 per gestire eventuali errori rest specifici
    downloadBollettini(idPiano: number): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/downloadBollettini';
        let params: HttpParams = new HttpParams().set('idPiano', idPiano.toString());
        return this.http.get(url, { params: params }).map(res => {
            if (res["file"] != null) {
                return this.allegatoUtilsSharedService.b64toBlob(res["file"], 'application/pdf;base64')
            }
        }
        );
    }

    riconciliaRata(rata: RataVO): Observable<RataVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazione/riconciliaRata';
        return this.http.post<RataVO>(url, rata);
    }

    riconciliaSollecito(sollecito: SollecitoVO): Observable<RiconciliaSollecitoResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecito/riconciliaSollecito';
        return this.http.post<RiconciliaSollecitoResponse>(url, sollecito);
    }
    getMessaggioManualeByidOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto: number): Observable<any>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getMessaggioManualeByIdOrdinanzaVerbaleSoggetto';
        let params = new HttpParams().set('IdOrdinanzaVerbaleSoggetto', idOrdinanzaVerbaleSoggetto.toString());
        return this.http.get<any>(url, { params: params });
    }

}