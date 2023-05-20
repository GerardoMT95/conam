import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { TipoAllegatoVO, StatoSoggettoOrdinanzaVO } from "../../commons/vo/select-vo";
import { SalvaOrdinanzaRequest } from "../../commons/request/ordinanza/salva-ordinanza-request";


@Injectable()
export class FaseGiurisdizionaleOrdinanzaService implements OnDestroy {
    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(FaseGiurisdizionaleOrdinanzaService.name);
    }


    getTipologiaAllegatiCreaOrdinanza(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getTipologiaAllegatiCreaOrdinanza';
        return this.http.get<Array<TipoAllegatoVO>>(url);
    }
    getTipologiaAllegatiCreaOrdinanzaAnnullamento(id: number): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getTipologiaAllegatiCreaOrdinanzaAnnullamento';
        let params = new HttpParams().set('idOrdinanzaAnnullata', id.toString());
        return this.http.get<Array<TipoAllegatoVO>>(url,{ params: params });
    }

    getStatiOrdinanzaSoggettoInCreazioneOrdinanza(): Observable<Array<StatoSoggettoOrdinanzaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/getStatiOrdinanzaSoggettoInCreazioneOrdinanza';
        return this.http.get<Array<StatoSoggettoOrdinanzaVO>>(url);
    }

    salvaOrdinanza(request: SalvaOrdinanzaRequest): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaOrdinanza';
        let form = new FormData();
        form.append("data", JSON.stringify(request, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", request.file)
        return this.http.post<number>(url, form);
    }
    salvaOrdinanzaAnnullamento(request: SalvaOrdinanzaRequest): Observable<number> {
        var url: string = this.config.getBEServer() + '/restfacade/ordinanza/salvaOrdinanzaAnnullamento ';
        let form = new FormData();
        form.append("data", JSON.stringify(request, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        form.append("files", request.file)
        return this.http.post<number>(url, form);
    }



    ngOnDestroy(): void {
        this.logger.destroyService(FaseGiurisdizionaleOrdinanzaService.name);
    }
}

