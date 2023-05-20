import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { VerbaleVO } from "../../commons/vo/verbale/verbale-vo";
import { SalvaAllegatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-verbale-request";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import { MinSoggettoVO } from "../../commons/vo/verbale/min-soggetto-vo";
import { SoggettoPregressiVO } from "../../commons/vo/verbale/soggetto-pregressi-vo";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { StatoOrdinanzaVO, StatoPianoVO, StatoSoggettoOrdinanzaVO, StatoSollecitoVO, TipoAllegatoVO } from "../../commons/vo/select-vo";
import { SalvaStatoVerbaleRequest } from "../../commons/request/verbale/salva-stato-verbale.request";
import { StatiVerbaleResponse } from "../../commons/response/verbale/stati-verbale-response";
import { SalvaAllegatoProtocollatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-protocollato-verbale-request";
import { MessageVO } from "../../commons/vo/messageVO";
import { ExceptionVO } from "../../commons/vo/exceptionVO";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { AzioneVerbaleResponse } from "../../commons/response/verbale/azione-verbale-response";
import { RiepilogoAllegatoVO } from "../../commons/vo/verbale/riepilogo-allegato-vo";
import { SalvaOrdinanzaPregressiRequest } from "../../commons/request/pregresso/salva-ordinanza-pregressi-request";
import { SalvaOrdinanzaPregressiResponse } from "../../commons/response/pregresso/salva-ordinanza-pregressi-response";
import { AzioneVerbalePregressiResponse } from "../../commons/response/pregresso/azione-verbale-pregressi-response";
import { RiepilogoVerbaleVO } from "../../commons/vo/verbale/riepilogo-verbale-vo";
import { RicercaOrdinanzaRequest } from "../../commons/request/ordinanza/ricerca-ordinanza-request";
import { OrdinanzaVO } from "../../commons/vo/ordinanza/ordinanza-vo";
import { SalvaStatoOrdinanzaPregressiRequest } from "../../commons/request/pregresso/salva-stato-ordinanza-pregressi.request";
import { StatiOrdinanzaPregressiResponse } from "../../commons/response/pregresso/stati-ordinanza-response";
import { AzioneOrdinanzaPregressiResponse } from "../../commons/response/pregresso/azione-ordinanza-pregressi-response";
import { AzioneOrdinanzaRequest } from "../../commons/request/ordinanza/azione-ordinanza-request";
import { PianoRateizzazioneVO } from "../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { SalvaPianoPregressiRequest } from "../../commons/request/pregresso/salva-piano-pregressi-request";
import { SalvaPianoPregressiResponse } from "../../commons/response/pregresso/salva-piano-pregressi-response";
import { RicercaPianoRateizzazioneRequest } from "../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { SalvaAllegatoProtocollatoOrdinanzaRequest } from "../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-request";
import { RicercaSoggettiOrdinanzaRequest } from "../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { SalvaSollecitoPregressiRequest } from "../../commons/request/pregresso/salva-sollecito-pregressi-request";
import { SalvaSollecitoPregressiResponse } from "../../commons/response/pregresso/salva-sollecito-pregressi-response";
import { SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest } from "../../commons/request/ordinanza/salva-allegato-protocollato-ordinanza-soggetto-request";
import { SalvaAllegatoOrdinanzaRequest } from "../../commons/request/ordinanza/salva-allegato-ordinanza-request";
import { SollecitoVO } from "../../commons/vo/riscossione/sollecito-vo";
import { SalvaAllegatoOrdinanzaVerbaleSoggettoRequest } from "../../commons/request/ordinanza/salva-allegato-ordinanza-verbale-soggetto-request";
import { SentenzaVO } from "../../commons/vo/ordinanza/sentenza-vo";
import { RicevutePagamentoVO } from "../../commons/vo/ordinanza/ricevute-pagamento-vo";
import { SalvaConvocazioneAudizioneRequest } from "../../commons/request/pregresso/salva-convocazione-audizione-request";
import { SalvaVerbaleAudizioneRequest } from "../../commons/request/pregresso/salva-verbale-audizione-request";

@Injectable()
export class PregressoVerbaleService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(PregressoVerbaleService.name);
    }

    salvaVerbale(verbale: VerbaleVO): Observable<number> {
        let url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaVerbale';
        let body = verbale;
        return this.http.post<number>(url, body);
    }
    
    salvaAllegatoVerbale(input: SalvaAllegatoVerbaleRequest): Observable<AllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaAllegatoVerbale';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", input.file)
        return this.http.post<AllegatoVO>(url, form);
    }

    ricercaSoggetto(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/ricercaSoggetto';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        return this.http.post<SoggettoPregressiVO>(url, minSoggetto);
    }

    ricercaSoggettoPerPIva(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/ricercaSoggettoPerPIva';
        return this.http.post<SoggettoPregressiVO>(url, minSoggetto);
    }

    salvaSoggetto(soggetto: SoggettoVO, idVerbale: number): Observable<SoggettoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaSoggetto';
        let body = { "idVerbale": idVerbale, "soggetto": soggetto };
        return this.http.post<SoggettoVO>(url, body);
    }

    getVerbaleById(idVerbale: number): Observable<VerbaleVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/dettaglioVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<VerbaleVO>(url, { params: params });
    }
    
    getTipologiaAllegatiAllegabiliVerbale(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/getTipologiaAllegatiAllegabiliVerbale';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    getTipologiaAllegatiAllegabiliByOrdinanza(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getTipologiaAllegatiAllegabiliByOrdinanza';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }


    getStatiVerbaleById(idVerbale: number): Observable<StatiVerbaleResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/getStatiVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<StatiVerbaleResponse>(url, { params: params });
    }
    
    getStatiSollecito(idSollecito:number): Observable<Array<StatoOrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecitoPregressi/getStatiSollecito';
        let params = new HttpParams();
        if(idSollecito!=-1){
            params = new HttpParams().set('idSollecito', idSollecito.toString());
        }
        return this.http.get<Array<StatoOrdinanzaVO>>(url, { params: params });
    }
    

    salvaStatoVerbale(salvaStatoVerbaleRequest: SalvaStatoVerbaleRequest): Observable<MessageVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaStatoVerbale';
        return this.http.post<MessageVO>(url, salvaStatoVerbaleRequest);
    }

    salvaAllegatoProtocollato(salvaAllegatoProtocollatoVerbaleRequest: SalvaAllegatoProtocollatoVerbaleRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaAllegatoProtocollatoVerbale';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoVerbaleRequest);
    }

    checkDatiVerbale(verbale: VerbaleVO): Observable<ExceptionVO> {
        let url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/checkDatiVerbale';
        let body = verbale;
        return this.http.post<ExceptionVO>(url, body);
    }
    
    getAzioniVerbale(idVerbale: number): Observable<AzioneVerbalePregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/getAzioniVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<AzioneVerbalePregressiResponse>(url, { params: params });
    }

    getAllegatiByIdVerbale(idVerbale: number): Observable<RiepilogoAllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/getAllegatiByIdVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<RiepilogoAllegatoVO>(url, { params: params });
    }

    getTipologiaAllegatiCreaOrdinanza(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getTipologiaAllegatiCreaOrdinanza';
        return this.http.get<Array<TipoAllegatoVO>>(url);
    }

    getStatiOrdinanzaSoggettoInCreazioneOrdinanza(): Observable<Array<StatoSoggettoOrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getStatiOrdinanzaSoggettoInCreazioneOrdinanza';
        return this.http.get<Array<StatoSoggettoOrdinanzaVO>>(url);
    }

    getSoggettiByIdVerbale(idVerbale: number, includiControlloUtenteProprietario: Boolean): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/getSoggettiByIdVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString()).set('controlloUtente', includiControlloUtenteProprietario.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    salvaOrdinanza(salvaOrdinanzaPregressiRequest: SalvaOrdinanzaPregressiRequest): Observable<SalvaOrdinanzaPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/salvaOrdinanza';
        return this.http.post<SalvaOrdinanzaPregressiResponse>(url, salvaOrdinanzaPregressiRequest);
    }
    
    riepilogoVerbale(idVerbale: number): Observable<RiepilogoVerbaleVO> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/riepilogo';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<RiepilogoVerbaleVO>(url, { params: params });
    }
    
    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest): Observable<Array<OrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/ricercaOrdinanza';
        return this.http.post<Array<OrdinanzaVO>>(url, ricercaOrdinanzaRequest);
    }

    getStatiOrdinanzaById(idOrdinanza: number): Observable<StatiOrdinanzaPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getStatiOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<StatiOrdinanzaPregressiResponse>(url, { params: params });
    }
    
    salvaStatoOrdinanza(salvaStatoOrdinanzaPregressiRequest: SalvaStatoOrdinanzaPregressiRequest): Observable<MessageVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/salvaStatoOrdinanza';
        return this.http.post<MessageVO>(url, salvaStatoOrdinanzaPregressiRequest);
    }
    
    azioneOrdinanza(request: AzioneOrdinanzaRequest): Observable<AzioneOrdinanzaPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/azioneOrdinanza';
        return this.http.post<AzioneOrdinanzaPregressiResponse>(url, request);
    }

    getSoggettiOrdinanza(idOrdinanza: number): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getSoggettiByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<SoggettoVO>>(url, { params: params });
    }

    salvaPiano(salvaPianoPregressiRequest: SalvaPianoPregressiRequest): Observable<SalvaPianoPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazionePregressi/salvaPiano';
        return this.http.post<SalvaPianoPregressiResponse>(url, salvaPianoPregressiRequest);
    }

    precompilaPiano(body: Array<number>): Observable<PianoRateizzazioneVO> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazionePregressi/precompilaPiano';
        return this.http.post<PianoRateizzazioneVO>(url, body);
    }

    getPianiByOrdinanza(idOrdinanza: number): Observable<Array<PianoRateizzazioneVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazionePregressi/getPianiByOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<PianoRateizzazioneVO>>(url, { params: params });
    }

    ricercaSoggettiPiano(request: RicercaPianoRateizzazioneRequest): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazionePregressi/ricercaSoggetti';
        return this.http.post<Array<SoggettoVO>>(url, request);
    }

    getStatiPiano(idPiano: number): Observable<Array<StatoPianoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/pianoRateizzazionePregressi/getStatiPiano';
        let params = new HttpParams().set('idPiano', idPiano.toString());
        return this.http.get<Array<StatoPianoVO>>(url, { params: params });
    }

    salvaAllegatoProtocollatoOrdinanza(salvaAllegatoProtocollatoOrdinanzaRequest: SalvaAllegatoProtocollatoOrdinanzaRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/salvaAllegatoProtocollatoOrdinanza';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoOrdinanzaRequest);
    }

    ricercaSoggettiOrdinanza(request: RicercaSoggettiOrdinanzaRequest): Observable<Array<SoggettoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/ricercaSoggetti';
        return this.http.post<Array<SoggettoVO>>(url, request);
    }

    salvaSollecito(salvaSollecitoPregressiRequest: SalvaSollecitoPregressiRequest): Observable<SalvaSollecitoPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecitoPregressi/salvaSollecito';
        return this.http.post<SalvaSollecitoPregressiResponse>(url, salvaSollecitoPregressiRequest);
    }

    checkSalvaSollecito(salvaSollecitoPregressiRequest: SalvaSollecitoPregressiRequest): Observable<SalvaSollecitoPregressiResponse> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecitoPregressi/checkSalvaSollecito';
        return this.http.post<SalvaSollecitoPregressiResponse>(url, salvaSollecitoPregressiRequest);
    }
    
    getSollecitiByOrdinanza(idOrdinanza: number): Observable<Array<SollecitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/sollecitoPregressi/getSollecitiByOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<SollecitoVO>>(url, { params: params });
    }

    salvaAllegatoProtocollatoOrdinanzaSoggetto(salvaAllegatoProtocollatoOrdinanzaSoggettoRequest: SalvaAllegatoProtocollatoOrdinanzaSoggettoRequest): Observable<Array<AllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/salvaAllegatoProtocollatoOrdinanzaSoggetto';
        return this.http.post<Array<AllegatoVO>>(url, salvaAllegatoProtocollatoOrdinanzaSoggettoRequest);
    }

    salvaAllegatoOrdinanza(input: SalvaAllegatoOrdinanzaRequest): Observable<AllegatoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/salvaAllegatoOrdinanza';
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
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi//salvaAllegatoOrdinanzaSoggetto';
        let form = new FormData();
        form.append("data", JSON.stringify(input, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", input.file)
        return this.http.post<AllegatoVO>(url, form);
    }
    
    getDatiSentenzaByIdOrdinanza(idOrdinanza: number): Observable<Array<SentenzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getDatiSentenzaByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<SentenzaVO>>(url, { params: params });
    }

    getRicevutePagamentoByIdOrdinanza(idOrdinanza: number): Observable<Array<RicevutePagamentoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanzaPregressi/getRicevutePagamentoByIdOrdinanza';
        let params = new HttpParams().set('idOrdinanza', idOrdinanza.toString());
        return this.http.get<Array<RicevutePagamentoVO>>(url, { params: params });
    }

    salvaConvocazioneAudizione(salvaConvocazioneAudizioneRequest: SalvaConvocazioneAudizioneRequest): Observable<any> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaConvocazioneAudizione';
        return this.http.post<Array<AllegatoVO>>(url, salvaConvocazioneAudizioneRequest);
    }

    salvaVerbaleAudizione(salvaVerbaleAudizioneRequest: SalvaVerbaleAudizioneRequest): Observable<any> {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/salvaVerbaleAudizione';
        return this.http.post<Array<AllegatoVO>>(url, salvaVerbaleAudizioneRequest);
    }

    eliminaSoggettoByIdVerbaleSoggetto(idVerbaleSoggetto: number) {
        var url: string = this.config.getBEServer() + '/restfacade/verbalePregressi/eliminaSoggettoByIdVerbaleSoggetto';
        let params = new HttpParams().set('idVerbaleSoggetto', idVerbaleSoggetto.toString());
        return this.http.get(url, { params: params });
    }
    
    
}