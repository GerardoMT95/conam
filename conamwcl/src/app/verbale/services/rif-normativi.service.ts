import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NormaVO, ArticoloVO, CommaVO, LetteraVO, StatoVerbaleVO, AmbitoVO } from "../../commons/vo/select-vo";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";

@Injectable()
export class RifNormativiService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(RifNormativiService.name);
    }

    getAmbitiByIdEnte(idEnte: number, filterDataValidita: boolean): Observable<Array<AmbitoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/ambitiByIdEnte';
        let params = new HttpParams().set('idEnte', idEnte.toString()).set('filterDataValidita', filterDataValidita.toString());
        return this.http.get<Array<AmbitoVO>>(url, { params: params });
    }

    /*getNormeByIdEnte(idEnte: number, filterDataValidita: boolean): Observable<Array<NormaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/normativeByIdEnte';
        let params = new HttpParams().set('idEnte', idEnte.toString()).set('filterDataValidita', filterDataValidita.toString());
        return this.http.get<Array<NormaVO>>(url, { params: params });
    }*/

    getNormeByIdAmbitoAndIdEnte(idAmbito: number,idEnte: number, filterDataValidita: boolean): Observable<Array<NormaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/getNormeByIdAmbitoAndIdEnte';
        let params = new HttpParams().set('idAmbito', idAmbito.toString()).set('idEnte', idEnte.toString()).set('filterDataValidita', filterDataValidita.toString());;
        return this.http.get<Array<NormaVO>>(url, { params: params });
    }

    getArticoliByIdNormaAndIdEnte(idNorma: number, idEnte: number, filterDataValidita: boolean): Observable<Array<ArticoloVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/getArticoliByIdNormaAndIdEnte';
        let params = new HttpParams().set('idNorma', idNorma.toString()).set('idEnte', idEnte.toString()).set('filterDataValidita', filterDataValidita.toString());;
        return this.http.get<Array<ArticoloVO>>(url, { params: params });
    }

    getCommaByIdArticolo(idComma: number, filterDataValidita: boolean): Observable<Array<CommaVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/commaByIdArticolo';
        let params = new HttpParams().set('idArticolo', idComma.toString()).set('filterDataValidita', filterDataValidita.toString());;
        return this.http.get<Array<CommaVO>>(url, { params: params });
    }

    getLetteraByIdComma(idLettera: number, filterDataValidita: boolean): Observable<Array<LetteraVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/rifNormativi/letteraByIdComma';
        let params = new HttpParams().set('idComma', idLettera.toString()).set('filterDataValidita', filterDataValidita.toString());;
        return this.http.get<Array<LetteraVO>>(url, { params: params });
    }

}