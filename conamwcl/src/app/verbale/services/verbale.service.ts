import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { RuoloVO, TipoAllegatoVO, IstruttoreVO } from "../../commons/vo/select-vo";
import { VerbaleVO } from "../../commons/vo/verbale/verbale-vo";
import { MinSoggettoVO } from "../../commons/vo/verbale/min-soggetto-vo";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { RiepilogoAllegatoVO } from "../../commons/vo/verbale/riepilogo-allegato-vo";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import { DocumentoProtocollatoVO } from "../../commons/vo/verbale/documento-protocollato-vo";
import { SalvaAllegatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-verbale-request";
import { SalvaAzioneRequest } from "../../commons/request/verbale/salva-azione.request";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { RicercaProtocolloRequest } from "../../commons/request/verbale/ricerca-protocollo-request";
import { RecidivoVo } from "../../commons/vo/verbale/recidivo-vo";
import { UtenteVO } from "../../commons/vo/utente-vo";
import { SalvaStatoRequest } from "../../commons/request/verbale/salva-stato-request";
import { SalvaScrittoDifensivoRequest } from "../../commons/request/verbale/salva-scritto-difensivo-request";
import { verbaliRecidivoVO } from "../../commons/vo/verbale/verbali-recidivo-vo";
import { ScrittoDifensivoVO } from "../../commons/vo/verbale/scritto-difensivo-vo";
import { RicercaScrittoDifensivoRequest } from "../../commons/request/verbale/ricerca-scritto-difensivo-request";
import { SalvaScrittoDifensivoResponse } from "../../commons/response/verbale/salva-scritto-difensivo-response";

@Injectable()
export class VerbaleService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(VerbaleService.name);
    }

    salvaVerbale(verbale: VerbaleVO): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaVerbale';
        let body = verbale;
        return this.http.post<number>(url, body);
    }

    getVerbaleById(idVerbale: number): Observable<VerbaleVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/dettaglioVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<VerbaleVO>(url, { params: params });
    }

    eliminaVerbale(idVerbale: number) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/eliminaVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get(url, { params: params });
    }

    ricercaSoggetto(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ricercaSoggetto';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        return this.http.post<SoggettoVO>(url, minSoggetto);
    }

    ricercaSoggettoPerPIva(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ricercaSoggettoPerPIva';
        return this.http.post<SoggettoVO>(url, minSoggetto);
    }

    ruoliSoggetto(): Observable<Array<RuoloVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ruoliSoggetto';
        return this.http.get<Array<RuoloVO>>(url);
    }

    salvaSoggetto(soggetto: SoggettoVO, idVerbale: number): Observable<SoggettoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaSoggetto';
        let body = { "idVerbale": idVerbale, "soggetto": soggetto };
        return this.http.post<SoggettoVO>(url, body);
    }

    eliminaSoggettoByIdVerbaleSoggetto(idVerbaleSoggetto: number) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/eliminaSoggettoByIdVerbaleSoggetto';
        let params = new HttpParams().set('idVerbaleSoggetto', idVerbaleSoggetto.toString());
        return this.http.get(url, { params: params });
    }

    getAllegatiByIdVerbale(idVerbale: number): Observable<RiepilogoAllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getAllegatiByIdVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<RiepilogoAllegatoVO>(url, { params: params });
    }

    getTipologiaAllegatiAllegabiliVerbale(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getTipologiaAllegatiAllegabiliVerbale';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    getIstruttoreByIdVerbale(idVerbale: number): Observable<Array<IstruttoreVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getIstruttoreByVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<Array<IstruttoreVO>>(url, { params: params });
    }


    salvaAzioneVerbale(salvaAzioneRequest: SalvaAzioneRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAzioneVerbale';
        return this.http.post(url, salvaAzioneRequest);
    }

    getUtenteRuolo(idVerbale: number) : Observable<UtenteVO>{
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getUtenteRuolo';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<UtenteVO>(url, { params: params });
    }

    listaStati(): Observable<Array<any>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getStatiManuali';
        return this.http.get<Array<any>>(url);
    }

    salvaStatoVerbale(salavaStatoFascicolo: SalvaStatoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaStatoManuale';
        return this.http.post(url, salavaStatoFascicolo);
    }

    getVerbaleSoggettoByIdSoggetto(idSogg: number, recidivo?: boolean): Observable<RecidivoVo> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto';
        let params = new HttpParams().set('idSoggetto', idSogg.toString());
        if(recidivo){
            params =  new HttpParams().set('idSoggetto', idSogg.toString()).set('recidivo',recidivo.toString());    
        }
        return this.http.get<RecidivoVo>(url, { params: params });
    }
    
    setRecidivoVerbaleSoggetto (setRecidivoVerbaleSoggetto : any) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/setRecidivoVerbaleSoggetto';
        return this.http.post(url, setRecidivoVerbaleSoggetto );
    }
     
    salvaScrittoDifensivo(salvaScrittoDifensivoRequest: SalvaScrittoDifensivoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/salvaScrittoDifensivo';
        let form = new FormData();
        form.append("data", JSON.stringify(salvaScrittoDifensivoRequest, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        if(salvaScrittoDifensivoRequest.file){
            form.append("file", salvaScrittoDifensivoRequest.file)
        }
        return this.http.post<SalvaScrittoDifensivoResponse>(url, form);
    }

    getScrittoDifensivoById(idScrittoDifensivo: number): Observable<ScrittoDifensivoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/dettaglioScrittoDifensivo';
        let params = new HttpParams().set('idScrittoDifensivo', idScrittoDifensivo.toString());
        return this.http.get<ScrittoDifensivoVO>(url, { params: params });
    }

    ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest: RicercaScrittoDifensivoRequest): Observable<Array<ScrittoDifensivoVO>>{
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/ricercaScrittoDifensivo';
        return this.http.post<Array<ScrittoDifensivoVO>>(url, ricercaScrittoDifensivoRequest);
    }

}